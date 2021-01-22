package tools.vitruv.applications.cbs.commonalities.java.operators

import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.commons.NamedElement
import org.emftext.language.java.types.Boolean
import org.emftext.language.java.types.Double
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.Type
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory
import org.emftext.language.java.types.Void
import tools.vitruv.applications.cbs.commonalities.domaincommon.CommonPrimitiveType
import tools.vitruv.applications.cbs.commonalities.domaincommon.operators.AbstractTypeReferenceOperator
import tools.vitruv.domains.java.util.JavaModificationUtil
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static tools.vitruv.framework.util.XtendAssertHelper.*

/**
 * Maps between Java's {@link TypeReference} and an intermediate representation
 * of these type references.
 * <p>
 * Note: An unset (<code>null</code>) intermediate type reference gets mapped
 * to {@link Void} in Java.
 */
@AttributeMappingOperator(
	name='javaTypeReference',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=false, type=TypeReference)
)
class JavaTypeReferenceOperator extends AbstractTypeReferenceOperator<TypeReference, Type> {

	/**
	 * @param executionState the reactions execution state
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String targetConceptDomainName) {
		super(executionState, 'Java', targetConceptDomainName, Type)
	}

	override protected isDomainVoidReference(TypeReference domainTypeReference) {
		if (domainTypeReference === null) return true
		if (domainTypeReference instanceof Void) return true

		// Check for wrapper Void type:
		val domainType = domainTypeReference.referencedDomainType
		if (domainType instanceof Class) {
			if (domainType.unWrapPrimitiveType instanceof Void) {
				return true
			}
		}

		return false
	}

	override protected TypeReference getDomainVoidReference() {
		return TypesFactory.eINSTANCE.createVoid
	}

	override protected getReferencedDomainType(TypeReference domainTypeReference) {
		return domainTypeReference.target
	}

	override protected getDomainTypeReference(Type domainType) {
		assertTrue(domainType !== null)
		if (domainType instanceof PrimitiveType) {
			return domainType
		} else if (domainType instanceof ConcreteClassifier) {
			// TODO If possible, use a regular ClassifierReference + import instead?
			return JavaModificationUtil.createNamespaceClassifierReference(domainType)
		} else {
			throw new IllegalStateException('''Unhandled Java type: «domainType.class.name»''')
		}
	}

	override protected asString(Type domainType) {
		if (domainType instanceof ConcreteClassifier) {
			return domainType.qualifiedName
		} else if (domainType instanceof NamedElement) {
			return domainType.name
		} else {
			return domainType.class.name
		}
	}

	private def toCommonPrimitiveType(PrimitiveType javaPrimitiveType) {
		assertTrue(javaPrimitiveType !== null)
		assertTrue(!(javaPrimitiveType instanceof Void)) // Void is handled separately
		switch (javaPrimitiveType) {
			Boolean:
				return CommonPrimitiveType.BOOLEAN
			Int:
				return CommonPrimitiveType.INTEGER
			Double:
				return CommonPrimitiveType.DOUBLE
			default: {
				throw new IllegalArgumentException("Unsupported Java primitive type: " + javaPrimitiveType.asString)
			}
		}
	}

	override protected toCommonPrimitiveType(Type domainType) {
		assertTrue(domainType !== null)
		assertTrue(!(domainType instanceof Void)) // Void is handled separately
		if (domainType instanceof PrimitiveType) {
			return domainType.toCommonPrimitiveType
		} else if (domainType instanceof Class) {
			// Check for wrapped primitive types:
			val javaPrimitiveType = domainType.unWrapPrimitiveType
			if (javaPrimitiveType !== null) {
				return javaPrimitiveType.toCommonPrimitiveType
			}

			// Check for String type:
			if (domainType.qualifiedName == String.name) {
				return CommonPrimitiveType.STRING
			}
		}
		return null
	}

	override protected toPrimitiveDomainTypeReference(CommonPrimitiveType commonPrimitiveType) {
		assertTrue(commonPrimitiveType !== null)
		switch (commonPrimitiveType) {
			case BOOLEAN:
				return TypesFactory.eINSTANCE.createBoolean
			case INTEGER:
				return TypesFactory.eINSTANCE.createInt
			case DOUBLE:
				return TypesFactory.eINSTANCE.createDouble
			case STRING: {
				// TODO If possible, use a regular ClassifierReference + import instead?
				return JavaModificationUtil.createNamespaceClassifierReferenceForName(String.name)
			}
			default: {
				throw new IllegalArgumentException("Unsupported common primitive type: " + commonPrimitiveType.name)
			}
		}
	}
}
