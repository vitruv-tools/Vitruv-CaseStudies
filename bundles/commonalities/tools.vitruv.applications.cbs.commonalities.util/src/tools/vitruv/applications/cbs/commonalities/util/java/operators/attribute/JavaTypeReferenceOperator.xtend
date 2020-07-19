package tools.vitruv.applications.cbs.commonalities.util.java.operators.attribute

import java.util.Optional
import org.emftext.language.java.JavaClasspath
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
import tools.vitruv.applications.cbs.commonalities.util.common.CommonPrimitiveType
import tools.vitruv.applications.cbs.commonalities.util.common.operators.attribute.AbstractTypeReferenceOperator
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
		switch (javaPrimitiveType) {
			Boolean:
				return CommonPrimitiveType.BOOLEAN
			Int:
				return CommonPrimitiveType.INTEGER
			Double:
				return CommonPrimitiveType.DOUBLE
			Void:
				return null
			default: {
				throw new IllegalArgumentException("Unsupported Java primitive type: " + javaPrimitiveType.asString)
			}
		}
	}

	override protected toCommonPrimitiveType(Type domainType) {
		assertTrue(domainType !== null)
		if (domainType instanceof PrimitiveType) {
			return Optional.ofNullable(domainType.toCommonPrimitiveType)
		} else if (domainType instanceof Class) {
			// Check for wrapped primitive types:
			val javaPrimitiveType = domainType.unWrapPrimitiveType
			if (javaPrimitiveType !== null) {
				return Optional.ofNullable(javaPrimitiveType.toCommonPrimitiveType)
			}

			// Check for String type:
			if (domainType.qualifiedName == String.name) {
				return Optional.of(CommonPrimitiveType.STRING)
			}
		}
		return null
	}

	override protected toPrimitiveDomainTypeReference(CommonPrimitiveType commonPrimitiveType) {
		if (commonPrimitiveType === null) {
			return TypesFactory.eINSTANCE.createVoid
		}
		switch (commonPrimitiveType) {
			case BOOLEAN:
				return TypesFactory.eINSTANCE.createBoolean
			case INTEGER:
				return TypesFactory.eINSTANCE.createInt
			case DOUBLE:
				return TypesFactory.eINSTANCE.createDouble
			case STRING: {
				// TODO If possible, use a regular ClassifierReference + import instead?
				// Note: We intentionally don't resolve this proxy into a temporary or local ResourceSet,
				// because the UuidGeneratorAndResolver would then complain about not being able to find the object.
				// One alternative could be to resolve it into the test's ResourceSet. But using the proxy works fine as well.
				val javaType = JavaClasspath.get.getClassifier(String.name) as ConcreteClassifier
				return JavaModificationUtil.createNamespaceClassifierReference(javaType)
				// return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang", "String")
			}
			default: {
				throw new IllegalArgumentException("Unsupported common primitive type: " + commonPrimitiveType.name)
			}
		}
	}
}
