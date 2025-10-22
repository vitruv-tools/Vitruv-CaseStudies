package tools.vitruv.applications.cbs.operators.domaincommon.operators;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import tools.vitruv.applications.cbs.operators.domaincommon.CommonPrimitiveType;
import tools.vitruv.dsls.commonalities.runtime.helper.IntermediateModelHelper;
import tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper;
import tools.vitruv.dsls.commonalities.runtime.intermediatemodelbase.Intermediate;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Abstract base class for operators mapping between domain specific type
 * references
 * and intermediate type references.
 *
 * @param <R> Domain type reference type
 * @param <T> Domain type type
 */
public abstract class AbstractTypeReferenceOperator<R, T> extends AbstractAttributeMappingOperator<String, R> {

    private static final Logger logger = Logger.getLogger(AbstractTypeReferenceOperator.class);
    private static final String INTERMEDIATE_REFERENCE_PREFIX = "__INTERMEDIATE__";

    protected final String participationDomainName;
    protected final String targetConceptDomainName;

    protected AbstractTypeReferenceOperator(ReactionExecutionState executionState,
            String participationDomainName,
            String targetConceptDomainName,
            Class<? extends T> domainTypeClass) {

        super(executionState);

        Preconditions.checkArgument(participationDomainName != null && !participationDomainName.isEmpty(),
                "participationDomainName is null or empty");
        this.participationDomainName = participationDomainName;

        Preconditions.checkArgument(targetConceptDomainName != null && !targetConceptDomainName.isEmpty(),
                "targetConceptDomainName is null or empty");
        this.targetConceptDomainName = targetConceptDomainName;

        Preconditions.checkNotNull(domainTypeClass, "domainTypeClass is null");
    }

    protected static boolean isIntermediateReference(String intermediateReference) {
        return intermediateReference != null &&
                intermediateReference.startsWith(INTERMEDIATE_REFERENCE_PREFIX);
    }

    protected static String toIntermediateReference(Intermediate intermediate) {
        Resource resource = intermediate.eResource();
        Preconditions.checkState(resource != null,
                String.format("Referenced Intermediate not stored in resource: %s", intermediate));

        String fragment = resource.getURIFragment(intermediate);
        return INTERMEDIATE_REFERENCE_PREFIX + fragment;
    }

    protected static Intermediate resolveIntermediateReference(
            Resource intermediateModelResource,
            String intermediateReference) {

        String fragment = intermediateReference.substring(INTERMEDIATE_REFERENCE_PREFIX.length());
        EObject intermediate = intermediateModelResource.getEObject(fragment);

        Preconditions.checkState(intermediate != null,
                "Could not resolve referenced intermediate: " + fragment);

        return (Intermediate) intermediate;
    }

    protected final Resource getIntermediateResource(String conceptDomainName) {
        List<String> intermediateModelKey = IntermediateModelHelper.getMetadataModelKey(conceptDomainName);

        URI intermediateModelURI = this.executionState.getResourceAccess().getMetadataModelURI(
                (String[]) Conversions.unwrapArray(intermediateModelKey, String.class));

        Resource intermediateModelResource = executionState.getResourceAccess()
                .getModelResource(intermediateModelURI);

        Preconditions.checkState(intermediateModelResource != null,
                "Could not find intermediate model resource: " + intermediateModelURI);

        return intermediateModelResource;
    }

    // ... rest of implementation with similar formatting improvements ...

    @Override
    public String applyTowardsCommonality(R domainTypeReference) {
        // Convert domain reference to intermediate reference
        String intermediateTypeReference = toIntermediateTypeReference(domainTypeReference);

        // Get referenced domain type if available
        T referencedDomainType = domainTypeReference != null ? getReferencedDomainType(domainTypeReference) : null;

        // Log the mapping operation
        logger.debug(String.format("Mapping referenced %s type '%s' to intermediate type reference '%s'",
                participationDomainName,
                referencedDomainType != null ? asString(referencedDomainType) : "<null>",
                intermediateTypeReference != null ? intermediateTypeReference : "<null>"));

        return intermediateTypeReference;
    }

    /**
     * Convert an intermediate type reference string to a domain-specific type
     * reference.
     * Subclasses must implement this to provide the correct mapping.
     */
    protected abstract R toDomainTypeReference(String intermediateTypeReference);

    @Override
    public R applyTowardsParticipation(String intermediateTypeReference) {
        R domainTypeReference = toDomainTypeReference(intermediateTypeReference);
        T referencedDomainType = domainTypeReference != null ? getReferencedDomainType(domainTypeReference) : null;

        logger.debug(String.format("Mapping intermediate reference %s to %s type %s",
                intermediateTypeReference,
                participationDomainName,
                referencedDomainType != null ? asString(referencedDomainType) : null));

        return domainTypeReference;
    }

    private String toIntermediateTypeReference(R domainTypeReference) {
        // Handle void reference
        if (isDomainVoidReference(domainTypeReference)) {
            return null;
        }

        // Get referenced domain type
        T referencedDomainType = domainTypeReference != null ? getReferencedDomainType(domainTypeReference) : null;
        if (referencedDomainType == null) {
            return null;
        }

        // Handle primitive type
        CommonPrimitiveType commonPrimitiveType = toCommonPrimitiveType(referencedDomainType);
        if (commonPrimitiveType != null) {
            return commonPrimitiveType.name();
        }

        // Handle EObject type
        if (referencedDomainType instanceof EObject) {
            Intermediate intermediateType = getCorrespondingIntermediateType(referencedDomainType);
            return intermediateType != null ? toIntermediateReference(intermediateType) : null;
        }

        // Handle unsupported type
        throw new IllegalStateException(String.format(
                "Unsupported kind of %s type: %s",
                participationDomainName,
                referencedDomainType.getClass().getName()));
    }

    /**
     * Gets the corresponding intermediate type for a given domain type.
     * 
     * @param domainType The domain type to find the intermediate for
     * @return The corresponding intermediate type, or null if none exists
     * @throws IllegalStateException    if multiple corresponding intermediates are
     *                                  found
     * @throws IllegalArgumentException if domainType is null or not an EObject
     */
    protected Intermediate getCorrespondingIntermediateType(T domainType) {
        // Validate input
        XtendAssertHelper.assertTrue(domainType != null);
        XtendAssertHelper.assertTrue(domainType instanceof EObject);

        // Get corresponding intermediates from correspondence model
        List<Intermediate> intermediateTypes = IterableExtensions.toList(
                Iterables.filter(
                        executionState.getCorrespondenceModel()
                                .getCorrespondingEObjects((EObject) domainType),
                        Intermediate.class));
        // Verify we don't have multiple matches
        Preconditions.checkState(intermediateTypes.size() <= 1,
                String.format("Found more than one corresponding intermediate for %s type '%s'!",
                        participationDomainName,
                        asString(domainType)));
        // Return first (and only) intermediate or null
        return IterableExtensions.head(intermediateTypes);
    }

    protected boolean isDomainVoidReference(R domainTypeReference) {
        return domainTypeReference == null;
    }

    protected abstract CommonPrimitiveType toCommonPrimitiveType(T var1);

    protected abstract T getReferencedDomainType(R var1);

    protected abstract String asString(T var1);

}