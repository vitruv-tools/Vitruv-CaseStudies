package tools.vitruv.applications.cbs.operators.uml.operators;

import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;

import tools.vitruv.applications.cbs.operators.domaincommon.CommonPrimitiveType;
import tools.vitruv.applications.cbs.operators.domaincommon.operators.AbstractTypeReferenceOperator;
import tools.vitruv.applications.cbs.operators.uml.UmlPrimitiveType;
import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.assertTrue;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Maps between references to UML {@link Type}s and an intermediate
 * representation of these type references.
 */
@AttributeMappingOperator(name = "umlTypeReference", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = false, type = Type.class))
public class UmlTypeReferenceOperator extends AbstractTypeReferenceOperator<Type, Type> {

    public UmlTypeReferenceOperator(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, "UML", targetConceptDomainName, Type.class);
    }

    @Override
    protected Type getReferencedDomainType(Type domainTypeReference) {
        // UML does not use a separate type to represent type references:
        return domainTypeReference;
    }

    @Override
    protected Type getDomainTypeReference(Type domainType) {
        // UML does not use a separate type to represent type references:
        return domainType;
    }

    @Override
    protected String asString(Type domainType) {
        return domainType.getQualifiedName();
    }

    private CommonPrimitiveType toCommonPrimitiveType(PrimitiveType primitiveType) {
        assertTrue(primitiveType != null);
        String name = primitiveType.getName();
        if (UmlPrimitiveType.BOOLEAN.getUmlTypeName().equals(name)) {
            return CommonPrimitiveType.BOOLEAN;
        } else if (UmlPrimitiveType.INTEGER.getUmlTypeName().equals(name)) {
            return CommonPrimitiveType.INTEGER;
        } else if (UmlPrimitiveType.REAL.getUmlTypeName().equals(name)) {
            return CommonPrimitiveType.DOUBLE;
        } else if (UmlPrimitiveType.STRING.getUmlTypeName().equals(name)) {
            return CommonPrimitiveType.STRING;
        } else {
            throw new IllegalArgumentException("Unsupported UML primitive type: " + name);
        }
    }

    @Override
    protected CommonPrimitiveType toCommonPrimitiveType(Type domainType) {
        assertTrue(domainType != null);
        if (domainType instanceof PrimitiveType primitiveType) {
            return toCommonPrimitiveType(primitiveType);
        }
        return null;
    }

    @Override
    protected Type toPrimitiveDomainTypeReference(CommonPrimitiveType commonPrimitiveType) {
        assertTrue(commonPrimitiveType != null);
        switch (commonPrimitiveType) {
            case BOOLEAN -> {
                return UmlPrimitiveType.BOOLEAN.getUmlType();
            }
            case INTEGER -> {
                return UmlPrimitiveType.INTEGER.getUmlType();
            }
            case DOUBLE -> {
                return UmlPrimitiveType.REAL.getUmlType();
            }
            case STRING -> {
                return UmlPrimitiveType.STRING.getUmlType();
            }
            default ->
                throw new IllegalArgumentException("Unsupported common primitive type: " + commonPrimitiveType.name());
        }
    }
}

