package tools.vitruv.applications.cbs.operators.domaincommon.operators;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.applications.cbs.operators.domaincommon.CommonPrimitiveType;
import tools.vitruv.dsls.commonalities.runtime.helper.IntermediateModelHelper;
import tools.vitruv.dsls.commonalities.runtime.intermediatemodelbase.Intermediate;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

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
 * referenced intermediates.<br>
 * Even though operators do not know anything about the structure of the
 * referenced intermediate objects, they can use the correspondence model to
 * find the corresponding domain specific objects which represents the same
 * types.
 * </ul>
 *
 * @param <R> The base type of objects which represent type references in the
 *            domain. For domains which don't use a separate type to represent
 *            type references, this matches the base type of objects which
 *            represent types in this domain.
 * @param <T> The base type of objects which represent types in the domain.
 */
public abstract class AbstractTypeReferenceOperator<R, T> extends AbstractAttributeMappingOperator<String, R> {

    private static final Logger logger = Logger.getLogger(AbstractTypeReferenceOperator.class);
    private static final String INTERMEDIATE_REFERENCE_PREFIX = "__INTERMEDIATE__";

    /**
     * This is for example used in log and debug messages.
     */
    protected final String participationDomainName;
    protected final String targetConceptDomainName;
    /**
     * This is used when querying the correspondence model for objects
     * corresponding to a given non-primitive type.
     */
    private final Class<? extends T> domainTypeClass;

    /**
     * @param executionState          the reactions execution state
     * @param participationDomainName the name of the participation domain
     * @param targetConceptDomainName the name of the target concept domain
     * @param domainTypeClass         the class of the domain's non-primitive
     *                                types
     */
    protected AbstractTypeReferenceOperator(ReactionExecutionState executionState, String participationDomainName,
            String targetConceptDomainName, Class<? extends T> domainTypeClass) {
        super(executionState);
        checkArgument(participationDomainName != null && !participationDomainName.isEmpty(),
                "participationDomainName is null or empty");
        this.participationDomainName = participationDomainName;

        checkArgument(targetConceptDomainName != null && !targetConceptDomainName.isEmpty(),
                "targetConceptDomainName is null or empty");
        this.targetConceptDomainName = targetConceptDomainName;

        checkNotNull(domainTypeClass, "domainTypeClass is null");
        this.domainTypeClass = domainTypeClass;
    }

    // Intermediate type reference representation:

    protected static boolean isIntermediateReference(String intermediateReference) {
        return intermediateReference != null && intermediateReference.startsWith(INTERMEDIATE_REFERENCE_PREFIX);
    }

    protected static String toIntermediateReference(Intermediate intermediate) {
        Resource resource = intermediate.eResource();
        checkState(resource != null, "Referenced Intermediate is not stored within a resource: %s", intermediate);
        String fragment = resource.getURIFragment(intermediate);
        return INTERMEDIATE_REFERENCE_PREFIX + fragment;
    }

    protected static Intermediate resolveIntermediateReference(Resource intermediateModelResource,
            String intermediateReference) {
        String fragment = intermediateReference.substring(INTERMEDIATE_REFERENCE_PREFIX.length());
        EObject intermediate = intermediateModelResource.getEObject(fragment);
        checkState(intermediate != null, "Could not resolve referenced intermediate: %s", fragment);
        return (Intermediate) intermediate;
    }

    protected final Resource getIntermediateResource(String conceptDomainName) {
        List<String> intermediateModelKey = IntermediateModelHelper.getMetadataModelKey(conceptDomainName);
        URI intermediateModelUri = executionState.getResourceAccess()
                .getMetadataModelURI(intermediateModelKey.toArray(new String[0]));
        Resource intermediateModelResource = executionState.getResourceAccess().getModelResource(intermediateModelUri);
        checkState(intermediateModelResource != null, "Could not find intermediate model resource: %s",
                intermediateModelUri);
        return intermediateModelResource;
    }

    private Intermediate resolveTargetIntermediateReference(String intermediateReference) {
        Resource intermediateModelResource = getIntermediateResource(targetConceptDomainName);
        return resolveIntermediateReference(intermediateModelResource, intermediateReference);
    }

    /**
     * Returns the referenced {@link Intermediate} or {@link CommonPrimitiveType},
     * or <code>null</code> if the reference is <code>null</code> or unknown.
     */
    protected final Object getReferencedIntermediateType(String intermediateTypeReference) {
        // Intermediate reference:
        if (isIntermediateReference(intermediateTypeReference)) {
            return resolveTargetIntermediateReference(intermediateTypeReference);
        }

        // Primitive type:
        return CommonPrimitiveType.byName(intermediateTypeReference); // can return null
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
    protected boolean isDomainVoidReference(R domainTypeReference) {
        return domainTypeReference == null;
    }

    /**
     * Returns a domain specific representation for the absence of a type
     * reference (eg. 'void' in Java).
     * <p>
     * By default this simply returns <code>null</code>.
     */
    protected R getDomainVoidReference() {
        return null;
    }

    /**
     * Gets the referenced domain type.
     */
    protected abstract T getReferencedDomainType(R domainTypeReference);

    /**
     * Creates a reference to the given domain type.
     */
    protected abstract R getDomainTypeReference(T domainType);

    /**
     * Gets a String representation for the given domain type.
     * <p>
     * This is for example used in log and debug messages.
     */
    protected abstract String asString(T domainType);

    /**
     * Checks if the given domain type is a primitive and then returns the
     * corresponding {@link CommonPrimitiveType common primitive type}.
     * <p>
     * Otherwise this returns <code>null</code>.
     */
    protected abstract CommonPrimitiveType toCommonPrimitiveType(T domainType);

    /**
     * Creates a reference to the domain type for the specified common
     * primitive type.
     */
    protected abstract R toPrimitiveDomainTypeReference(CommonPrimitiveType commonPrimitiveType);

    /**
     * Retrieves the (unique) intermediate object for the given domain type
     * object from the correspondence model.
     * <p>
     * Returns <code>null</code> if there is no corresponding intermediate
     * object.
     */
    protected Intermediate getCorrespondingIntermediateType(T domainType) {
        assertTrue(domainType != null);
        assertTrue(domainType instanceof EObject);
        // We get the corresponding intermediate type via the correspondence model:
        List<Intermediate> intermediateTypes = correspondingObjects((EObject) domainType, Intermediate.class);
        checkState(intermediateTypes.size() <= 1, "Found more than one corresponding intermediate for %s type '%s'!",
                participationDomainName, asString(domainType));
        return intermediateTypes.isEmpty() ? null : intermediateTypes.get(0);
    }

    /**
     * Retrieves the (unique) domain object for the given intermediate type
     * object from the correspondence model.
     * <p>
     * Returns <code>null</code> if there is no corresponding domain object.
     */
    protected T getCorrespondingDomainType(Intermediate intermediateType) {
        assertTrue(intermediateType != null);
        // We get the corresponding domain type via the correspondence model:
        List<? extends T> domainTypes = correspondingObjects(intermediateType, domainTypeClass);
        checkState(domainTypes.size() <= 1, "Found more than one corresponding %s type for intermediate type '%s': %s",
                participationDomainName, intermediateType, domainTypes);
        return domainTypes.isEmpty() ? null : domainTypes.get(0);
    }

    private <C> List<C> correspondingObjects(EObject object, Class<C> type) {
        return executionState.getCorrespondenceModel().getCorrespondingEObjects(object).stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }

    // Operator:

    private String toIntermediateTypeReference(R domainTypeReference) {
        if (isDomainVoidReference(domainTypeReference)) {
            return null; // Represented as null inside the intermediate model
        }

        // Get the referenced domain type and handle null:
        T referencedDomainType = domainTypeReference == null ? null : getReferencedDomainType(domainTypeReference);
        if (referencedDomainType == null) {
            return null;
        }

        // Check if the domain type represents a common primitive type:
        CommonPrimitiveType commonPrimitiveType = toCommonPrimitiveType(referencedDomainType);
        if (commonPrimitiveType != null) {
            return commonPrimitiveType.name();
        }

        // Handle non-primitive types:
        if (referencedDomainType instanceof EObject) {
            Intermediate intermediateType = getCorrespondingIntermediateType(referencedDomainType);
            if (intermediateType == null) {
                // There is no corresponding intermediate type object yet:
                return null;
            } else {
                return toIntermediateReference(intermediateType);
            }
        } else {
            throw new IllegalStateException("Unsupported kind of " + participationDomainName + " type: "
                    + referencedDomainType.getClass().getName());
        }
    }

    // intermediateTypeReference can be null
    protected R toDomainTypeReference(String intermediateTypeReference) {
        Object referencedIntermediateType = getReferencedIntermediateType(intermediateTypeReference);
        if (referencedIntermediateType == null) {
            return getDomainVoidReference();
        } else if (referencedIntermediateType instanceof CommonPrimitiveType commonPrimitiveType) {
            return toPrimitiveDomainTypeReference(commonPrimitiveType);
        } else if (referencedIntermediateType instanceof Intermediate intermediate) {
            T domainType = getCorrespondingDomainType(intermediate);
            if (domainType == null) {
                // There is no corresponding domain type object yet:
                // This can for example happen if in a sequence of processed model changes the creation of the
                // referenced intermediate has not been propagated yet.
                return getDomainVoidReference();
            } else {
                return getDomainTypeReference(domainType);
            }
        } else {
            throw new IllegalStateException(
                    "Unsupported kind of intermediate type: " + referencedIntermediateType.getClass().getName());
        }
    }

    // domain type reference -> intermediate type reference
    @Override
    public String applyTowardsCommonality(R domainTypeReference) {
        String intermediateTypeReference = toIntermediateTypeReference(domainTypeReference);
        T referencedDomainType = domainTypeReference == null ? null : getReferencedDomainType(domainTypeReference);
        logger.debug("Mapping referenced " + participationDomainName + " type "
                + (referencedDomainType == null ? null : asString(referencedDomainType))
                + " to intermediate type reference " + intermediateTypeReference + ".");
        return intermediateTypeReference;
    }

    // intermediate type reference -> domain type reference
    @Override
    public R applyTowardsParticipation(String intermediateTypeReference) {
        R domainTypeReference = toDomainTypeReference(intermediateTypeReference);
        T referencedDomainType = domainTypeReference == null ? null : getReferencedDomainType(domainTypeReference);
        logger.debug("Mapping intermediate type reference " + intermediateTypeReference + " to referenced "
                + participationDomainName + " type "
                + (referencedDomainType == null ? null : asString(referencedDomainType)) + ".");
        return domainTypeReference;
    }
}
