package tools.vitruv.applications.cbs.commonalities.domaincommon.operators

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.applications.cbs.commonalities.domaincommon.CommonPrimitiveType
import tools.vitruv.extensions.dslruntime.commonalities.helper.IntermediateModelHelper
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper.*

/**
 * Abstract base class for operators mapping between a domain specific
 * representation of type references to our intermediate representation of type
 * references.
 * <p>
 * The intermediate representation uses {@link String} as attribute type with
 * the content as follows:
 * <ul>
 * <li>References to {@link CommonPrimitiveType common primitive types} are
 * stored by their name.
 * <li>References to other intermediates are stored by their
 * {@link Resource#getURIFragment(EObject) URI fragment}. Since Commonalities
 * are expected to only reference other Commonalities from the same concept
 * domain (whose instances are stored within the same intermediate model), this
 * is sufficient and we do not need to store the full EObject URIs for the
 * referenced intermediates.</br>
 * Even though operators do not know anything about the structure of the
 * referenced intermediate objects, they can use the correspondence model to
 * find the corresponding domain specific objects which represents the same
 * types.
 * </ul>
 * 
 * @param <R> The base type of objects which represent type references in the
 * 		domain. For domains which don't use a separate type to represent type
 * 		references, this matches the base type of objects which represent types
 * 		in this domain.
 * @param <T> The base type of objects which represent types in the domain.
 */
abstract class AbstractTypeReferenceOperator<R, T> extends AbstractAttributeMappingOperator<String, R> {

	static val Logger logger = Logger.getLogger(AbstractTypeReferenceOperator)
	static val INTERMEDIATE_REFERENCE_PREFIX = "__INTERMEDIATE__"

	/**
	 * This is for example used in log and debug messages.
	 */
	protected val String participationDomainName
	protected val String targetConceptDomainName
	/**
	 * This is used when querying the correspondence model for objects
	 * corresponding to a given non-primitive type.
	 */
	val Class<? extends T> domainTypeClass

	/**
	 * @param executionState the reactions execution state
	 * @param participationDomainName the name of the participation domain
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String participationDomainName, String targetConceptDomainName,
		Class<? extends T> domainTypeClass) {
		super(executionState)
		checkArgument(!participationDomainName.nullOrEmpty, "participationDomainName is null or empty")
		this.participationDomainName = participationDomainName

		checkArgument(!targetConceptDomainName.nullOrEmpty, "targetConceptDomainName is null or empty")
		this.targetConceptDomainName = targetConceptDomainName

		checkNotNull(domainTypeClass, "domainTypeClass is null")
		this.domainTypeClass = domainTypeClass
	}

	// Intermediate type reference representation:

	protected static final def boolean isIntermediateReference(String intermediateReference) {
		return (intermediateReference !== null) && intermediateReference.startsWith(INTERMEDIATE_REFERENCE_PREFIX)
	}

	protected static final def String toIntermediateReference(Intermediate intermediate) {
		val resource = intermediate.eResource
		checkState(resource !== null, '''Referenced Intermediate is not stored within a resource: «intermediate»''')
		val fragment = resource.getURIFragment(intermediate)
		return INTERMEDIATE_REFERENCE_PREFIX + fragment
	}

	protected static final def Intermediate resolveIntermediateReference(Resource intermediateModelResource,
		String intermediateReference) {
		val fragment = intermediateReference.substring(INTERMEDIATE_REFERENCE_PREFIX.length)
		val intermediate = intermediateModelResource.getEObject(fragment)
		checkState(intermediate !== null, "Could not resolve referenced intermediate: " + fragment)
		return intermediate as Intermediate
	}

	protected final def Resource getIntermediateResource(String conceptDomainName) {
		val intermediateModelKey = IntermediateModelHelper.getMetadataModelKey(conceptDomainName)
		val intermediateModelURI = resourceAccess.getMetadataModelURI(intermediateModelKey)
		val intermediateModelResource = resourceAccess.getModelResource(intermediateModelURI)
		checkState(intermediateModelResource !== null, "Could not find intermediate model resource: "
			+ intermediateModelURI)
		return intermediateModelResource
	}

	private final def Intermediate resolveTargetIntermediateReference(String intermediateReference) {
		val intermediateModelResource = getIntermediateResource(targetConceptDomainName)
		return intermediateModelResource.resolveIntermediateReference(intermediateReference)
	}

	// intermediateTypeReference can be null
	protected final def Object getReferencedIntermediateType(String intermediateTypeReference) {
		// Intermediate reference:
		if (intermediateTypeReference.isIntermediateReference) {
			val referencedIntermediateType = intermediateTypeReference.resolveTargetIntermediateReference
			return referencedIntermediateType
		}

		// Primitive type:
		val commonPrimitiveType = CommonPrimitiveType.byName(intermediateTypeReference) // can return null
		return commonPrimitiveType
	}

	// Domain <-> Intermediate mapping:

	/**
	 * Some domains have a special representation for the absence of a type
	 * reference (eg. 'void' in Java).
	 * <p>
	 * This method returns <code>true</code> if the given domain type reference
	 * represents such an absence of a referenced type.
	 * <p>
	 * By default this simply checks if the given domain type reference is
	 * <code>null</code>.
	 */
	protected def boolean isDomainVoidReference(R domainTypeReference) {
		return (domainTypeReference === null)
	}

	/**
	 * Returns a domain specific representation for the absence of a type
	 * reference (eg. 'void' in Java).
	 * <p>
	 * By default this simply returns <code>null</code>.
	 */
	protected def R getDomainVoidReference() {
		return null
	}

	/**
	 * Gets the referenced domain type.
	 */
	protected abstract def T getReferencedDomainType(R domainTypeReference)

	/**
	 * Creates a reference to the given domain type.
	 */
	protected abstract def R getDomainTypeReference(T domainType)

	/**
	 * Gets a String representation for the given domain type.
	 * <p>
	 * This is for example used in log and debug messages.
	 */
	protected abstract def String asString(T domainType)

	/**
	 * Checks if the given domain type is a primitive and then returns the
	 * corresponding {@link CommonPrimitiveType common primitive type}.
	 * <p>
	 * Otherwise this returns <code>null</code>.
	 */
	protected abstract def CommonPrimitiveType toCommonPrimitiveType(T domainType)

	/**
	 * Creates a reference to the domain type for the specified common
	 * primitive type.
	 */
	protected abstract def R toPrimitiveDomainTypeReference(CommonPrimitiveType commonPrimitiveType)

	/**
	 * Retrieves the (unique) intermediate object for the given domain type
	 * object from the correspondence model.
	 * <p>
	 * Returns <code>null</code> if there is no corresponding intermediate
	 * object.
	 */
	protected def Intermediate getCorrespondingIntermediateType(T domainType) {
		assertTrue(domainType !== null)
		assertTrue(domainType instanceof EObject)
		// We get the corresponding intermediate type via the correspondence model:
		val intermediateTypes = correspondenceModel.getCorrespondingObjectsOfType(domainType as EObject, null,
			Intermediate).toList
		checkState(intermediateTypes.size <= 1, '''Found more than one corresponding intermediate for «
			participationDomainName» type '«domainType.asString»'!''')
		val intermediateType = intermediateTypes.head // can be null
		return intermediateType
	}

	/**
	 * Retrieves the (unique) domain object for the given intermediate type
	 * object from the correspondence model.
	 * <p>
	 * Returns <code>null</code> if there is no corresponding domain object.
	 */
	protected def T getCorrespondingDomainType(Intermediate intermediateType) {
		assertTrue(intermediateType !== null)
		// We get the corresponding domain type via the correspondence model:
		val domainTypes = correspondenceModel.getCorrespondingObjectsOfType(intermediateType, null,
			domainTypeClass).toList
		checkState(domainTypes.size <= 1, '''Found more than one corresponding «participationDomainName» type for«
			» intermediate type '«intermediateType»': «domainTypes»''')
		val domainType = domainTypes.head // can be null
		return domainType
	}

	// Operator:

	private def String toIntermediateTypeReference(R domainTypeReference) {
		if (domainTypeReference.isDomainVoidReference) {
			return null // Represented as null inside the intermediate model
		}

		// Get the referenced domain type and handle null:
		val referencedDomainType = domainTypeReference?.referencedDomainType
		if (referencedDomainType === null) return null

		// Check if the domain type represents a common primitive type:
		val commonPrimitiveType = referencedDomainType.toCommonPrimitiveType
		if (commonPrimitiveType !== null) {
			return commonPrimitiveType.name
		}

		// Handle non-primitive types:
		if (referencedDomainType instanceof EObject) {
			val intermediateType = getCorrespondingIntermediateType(referencedDomainType)
			if (intermediateType === null) {
				// There is no corresponding intermediate type object yet:
				return null
			} else {
				return intermediateType.toIntermediateReference
			}
		} else {
			throw new IllegalStateException('''Unsupported kind of «participationDomainName» type: «
				referencedDomainType.class.name»''')
		}
	}

	// intermediateTypeReference can be null
	protected def R toDomainTypeReference(String intermediateTypeReference) {
		val referencedIntermediateType = intermediateTypeReference.referencedIntermediateType
		if (referencedIntermediateType === null) {
			return domainVoidReference
		} else if (referencedIntermediateType instanceof CommonPrimitiveType) {
			return referencedIntermediateType.toPrimitiveDomainTypeReference
		} else if (referencedIntermediateType instanceof Intermediate) {
			val domainType = referencedIntermediateType.correspondingDomainType
			if (domainType === null) {
				// There is no corresponding domain type object yet:
				// This can for example happen if in a sequence of processed model changes the creation of the
				// referenced intermediate has not been propagated yet.
				return domainVoidReference
			} else {
				return domainType.domainTypeReference
			}
		} else {
			throw new IllegalStateException('''Unsupported kind of intermediate type: «
				referencedIntermediateType.class.name»''')
		}
	}

	// domain type reference -> intermediate type reference
	override String applyTowardsCommonality(R domainTypeReference) {
		val intermediateTypeReference = domainTypeReference.toIntermediateTypeReference
		val referencedDomainType = domainTypeReference?.referencedDomainType
		logger.debug('''Mapping referenced «participationDomainName» type «referencedDomainType?.asString
			» to intermediate type reference «intermediateTypeReference».''')
		return intermediateTypeReference
	}

	// intermediate type reference -> domain type reference
	override R applyTowardsParticipation(String intermediateTypeReference) {
		val domainTypeReference = intermediateTypeReference.toDomainTypeReference
		val referencedDomainType = domainTypeReference?.referencedDomainType
		logger.debug('''Mapping intermediate type reference «intermediateTypeReference» to referenced «
			participationDomainName» type «referencedDomainType?.asString».''')
		return domainTypeReference
	}
}
