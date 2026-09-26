package tools.vitruv.applications.cbs.operators.oo.operators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.applications.cbs.operators.domaincommon.CommonPrimitiveType;
import tools.vitruv.applications.cbs.operators.domaincommon.operators.AbstractTypeReferenceOperator;
import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.assertTrue;
import tools.vitruv.dsls.commonalities.runtime.intermediatemodelbase.Intermediate;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "ooTypeReference", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = false, type = String.class))
public class OOTypeReferenceOperator extends AbstractTypeReferenceOperator<String, Object> {

    public OOTypeReferenceOperator(ReactionExecutionState executionState, String targetConceptDomainName) {
        // Note: The participation domain name also matches the concept domain name
        super(executionState, "ObjectOrientedDesign", targetConceptDomainName, Intermediate.class);
    }

    @Override
    protected Object getReferencedDomainType(String domainTypeReference) {
        assertTrue(domainTypeReference != null);
        if (isIntermediateReference(domainTypeReference)) {
            // Intermediate reference:
            Resource intermediateModelResource = getIntermediateResource(participationDomainName);
            return resolveIntermediateReference(intermediateModelResource, domainTypeReference);
        } else {
            // Primitive:
            return CommonPrimitiveType.byName(domainTypeReference); // returns null for empty String
        }
    }

    protected String getDomainTypeReference(Object domainType) {
        assertTrue(domainType != null);
        if (domainType instanceof Intermediate intermediate) {
            return toIntermediateReference(intermediate);
        } else if (domainType instanceof CommonPrimitiveType commonPrimitiveType) {
            return commonPrimitiveType.name();
        } else {
            throw new IllegalStateException("Unexpected intermediate type: " + domainType);
        }
    }

    @Override
    protected String asString(Object domainType) {
        if (domainType instanceof Intermediate intermediate) {
            // Check if we find a 'name' feature:
            EStructuralFeature nameFeature = intermediate.eClass().getEStructuralFeatures().stream()
                    .filter(f -> "name".equals(f.getName()))
                    .findFirst()
                    .orElse(null);
            if (nameFeature != null && String.class.equals(nameFeature.getEType().getInstanceClass())) {
                Object nameObj = intermediate.eGet(nameFeature);
                String name = nameObj == null ? null : nameObj.toString();
                if (name != null && !name.isEmpty()) {
                    return name;
                }
            }
        } else if (domainType instanceof CommonPrimitiveType commonPrimitiveType) {
            return commonPrimitiveType.name();
        }
        return domainType == null ? null : domainType.toString();
    }

    @Override
    protected CommonPrimitiveType toCommonPrimitiveType(Object domainType) {
        assertTrue(domainType != null);
        if (domainType instanceof CommonPrimitiveType commonPrimitiveType) {
            return commonPrimitiveType;
        }
        return null;
    }

    protected String toPrimitiveDomainTypeReference(CommonPrimitiveType commonPrimitiveType) {
        // OO and CBS use the same common primitive types:
        return commonPrimitiveType.name();
    }

    @Override
    protected String toDomainTypeReference(String intermediateTypeReference) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDomainTypeReference'");
    }
}