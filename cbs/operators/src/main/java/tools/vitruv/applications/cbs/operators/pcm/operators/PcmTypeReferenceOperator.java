package tools.vitruv.applications.cbs.operators.pcm.operators;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;

import tools.vitruv.applications.cbs.operators.domaincommon.CommonPrimitiveType;
import tools.vitruv.applications.cbs.operators.domaincommon.operators.AbstractTypeReferenceOperator;
import tools.vitruv.applications.cbs.operators.pcm.PcmPrimitiveDataType;
import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.assertTrue;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Maps between references to PCM types ({@link DataType}s, but also types such
 * as {@link OperationInterface}) and an intermediate representation of these
 * type references.
 * <p>
 * Since there is no common PCM type for the types of objects this operator
 * supports, it operates on plain {@link EObject}s.
 */
@AttributeMappingOperator(name = "pcmTypeReference", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = false, type = EObject.class))
public class PcmTypeReferenceOperator extends AbstractTypeReferenceOperator<EObject, EObject> {

    /**
     * Creates a new PCM type reference operator.
     * 
     * @param executionState          the reactions execution state
     * @param targetConceptDomainName the name of the target concept domain
     */
    public PcmTypeReferenceOperator(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, "PCM", targetConceptDomainName, DataType.class);
    }

    @Override
    protected EObject getReferencedDomainType(EObject domainTypeReference) {
        // PCM does not use a separate type to represent type references:
        return domainTypeReference;
    }

    protected EObject getDomainTypeReference(EObject domainType) {
        // PCM does not use a separate type to represent type references:
        return domainType;
    }

    @Override
    protected String asString(EObject domainType) {
        if (domainType instanceof PrimitiveDataType) {
            return ((PrimitiveDataType) domainType).getType().name();
        } else if (domainType instanceof NamedElement) {
            return ((NamedElement) domainType).getEntityName();
        } else {
            return domainType.toString();
        }
    }

    private CommonPrimitiveType toCommonPrimitiveType(PrimitiveDataType primitiveDataType) {
        assertTrue(primitiveDataType != null);
        switch (primitiveDataType.getType()) {
            case BOOL:
                return CommonPrimitiveType.BOOLEAN;
            case INT:
                return CommonPrimitiveType.INTEGER;
            case DOUBLE:
                return CommonPrimitiveType.DOUBLE;
            case STRING:
                return CommonPrimitiveType.STRING;
            default:
                throw new IllegalArgumentException("Unsupported PCM primitive datatype: " +
                        primitiveDataType.getType());
        }
    }

    @Override
    protected CommonPrimitiveType toCommonPrimitiveType(EObject domainType) {
        assertTrue(domainType != null);
        if (domainType instanceof PrimitiveDataType) {
            return toCommonPrimitiveType((PrimitiveDataType) domainType);
        }
        return null;
    }

    protected EObject toPrimitiveDomainTypeReference(CommonPrimitiveType commonPrimitiveType) {
        assertTrue(commonPrimitiveType != null);
        switch (commonPrimitiveType) {
            case BOOLEAN:
                return PcmPrimitiveDataType.BOOL.getPcmType();
            case INTEGER:
                return PcmPrimitiveDataType.INT.getPcmType();
            case DOUBLE:
                return PcmPrimitiveDataType.DOUBLE.getPcmType();
            case STRING:
                return PcmPrimitiveDataType.STRING.getPcmType();
            default:
                throw new IllegalArgumentException("Unsupported common primitive type: " +
                        commonPrimitiveType.name());
        }
    }

    @Override
    protected EObject toDomainTypeReference(String intermediateTypeReference) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDomainTypeReference'");
    }
}