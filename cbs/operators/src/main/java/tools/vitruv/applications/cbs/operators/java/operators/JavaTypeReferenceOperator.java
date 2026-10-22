package tools.vitruv.applications.cbs.operators.java.operators;

import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.types.Boolean;
import org.emftext.language.java.types.Double;
import org.emftext.language.java.types.Int;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;
import org.emftext.language.java.types.Void;

import tools.vitruv.applications.cbs.operators.domaincommon.CommonPrimitiveType;
import tools.vitruv.applications.cbs.operators.domaincommon.operators.AbstractTypeReferenceOperator;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;
import static tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper.assertTrue;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Maps between Java's {@link TypeReference} and an intermediate representation
 * of these type references.
 *
 * Note: An unset (null) intermediate type reference gets mapped to {@link Void}
 * in Java.
 */
@AttributeMappingOperator(name = "javaTypeReference", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = false, type = TypeReference.class))
public class JavaTypeReferenceOperator extends AbstractTypeReferenceOperator<TypeReference, Type> {

    /**
     * @param executionState          the reactions execution state
     * @param targetConceptDomainName the name of the target concept domain
     */
    public JavaTypeReferenceOperator(ReactionExecutionState executionState, String targetConceptDomainName) {
        super(executionState, "Java", targetConceptDomainName, Type.class);
    }

    @Override
    protected boolean isDomainVoidReference(TypeReference domainTypeReference) {
        if (domainTypeReference == null)
            return true;
        if (domainTypeReference instanceof Void)
            return true;

        // Check for wrapper Void type:
        Type domainType = getReferencedDomainType(domainTypeReference);
        if (domainType instanceof Class aClass) {
            Type unwrapped = null;
            try {
                unwrapped = aClass.unWrapPrimitiveType();
            } catch (Throwable t) {
                // If getter name differs in generated API, fall back to null -> not a Void
                // wrapper
            }
            if (unwrapped instanceof Void) {
                return true;
            }
        }

        return false;
    }

    protected TypeReference getDomainVoidReference() {
        return TypesFactory.eINSTANCE.createVoid();
    }

    @Override
    protected Type getReferencedDomainType(TypeReference domainTypeReference) {
        // Xtend used 'target' property; EMF getter is getTarget()
        try {
            return domainTypeReference.getTarget();
        } catch (Throwable t) {
            // fallback in case API differs
            return null;
        }
    }

    protected TypeReference getDomainTypeReference(Type domainType) {
        assertTrue(domainType != null);
        if (domainType instanceof PrimitiveType) {
            // primitive types are used directly as type references in this operator
            return (TypeReference) domainType;
        } else if (domainType instanceof ConcreteClassifier) {
            // TODO If possible, use a regular ClassifierReference + import instead?
            return JavaModificationUtil.createNamespaceClassifierReference((ConcreteClassifier) domainType);
        } else {
            throw new IllegalStateException("Unhandled Java type: " + domainType.getClass().getName());
        }
    }

    @Override
    protected String asString(Type domainType) {
        if (domainType instanceof ConcreteClassifier concreteClassifier) {
            return concreteClassifier.getQualifiedName();
        } else if (domainType instanceof NamedElement namedElement) {
            return namedElement.getName();
        } else {
            return domainType.getClass().getName();
        }
    }

    private CommonPrimitiveType toCommonPrimitiveType(PrimitiveType javaPrimitiveType) {
        assertTrue(javaPrimitiveType != null);
        assertTrue(!(javaPrimitiveType instanceof Void)); // Void is handled separately

        if (javaPrimitiveType instanceof Boolean) {
            return CommonPrimitiveType.BOOLEAN;
        } else if (javaPrimitiveType instanceof Int) {
            return CommonPrimitiveType.INTEGER;
        } else if (javaPrimitiveType instanceof Double) {
            return CommonPrimitiveType.DOUBLE;
        } else {
            throw new IllegalArgumentException("Unsupported Java primitive type: " + javaPrimitiveType.toString());
        }
    }

    @Override
    protected CommonPrimitiveType toCommonPrimitiveType(Type domainType) {
        assertTrue(domainType != null);
        assertTrue(!(domainType instanceof Void)); // Void is handled separately
        if (domainType instanceof PrimitiveType primitiveType) {
            return toCommonPrimitiveType(primitiveType);
        } else if (domainType instanceof Class aClass) {
            // Check for wrapped primitive types:
            Type javaPrimitiveType = null;
            try {
                javaPrimitiveType = aClass.unWrapPrimitiveType();
            } catch (Throwable t) {
                // ignore, treat as non-primitive wrapper
            }
            if (javaPrimitiveType != null && javaPrimitiveType instanceof PrimitiveType) {
                return toCommonPrimitiveType((PrimitiveType) javaPrimitiveType);
            }

            // Check for String type:
            if (aClass.getQualifiedName().equals(String.class.getName())) {
                return CommonPrimitiveType.STRING;
            }
        }
        return null;
    }

    protected TypeReference toPrimitiveDomainTypeReference(CommonPrimitiveType commonPrimitiveType) {
        assertTrue(commonPrimitiveType != null);
        switch (commonPrimitiveType) {
            case BOOLEAN:
                return TypesFactory.eINSTANCE.createBoolean();
            case INTEGER:
                return TypesFactory.eINSTANCE.createInt();
            case DOUBLE:
                return TypesFactory.eINSTANCE.createDouble();
            case STRING:
                // TODO If possible, use a regular ClassifierReference + import instead?
                return JavaModificationUtil.createNamespaceClassifierReferenceForName(String.class.getName());
            default:
                throw new IllegalArgumentException("Unsupported common primitive type: " + commonPrimitiveType.name());
        }
    }

    @Override
    protected TypeReference toDomainTypeReference(String intermediateTypeReference) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDomainTypeReference'");
    }
}