package tools.vitruv.applications.util.temporary.uml;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import java.util.List;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * A utility class for UML operations and parameters.
 *
 * @author Fei
 */
@Utility
@SuppressWarnings("all")
public final class UmlOperationAndParameterUtil {

    /**
     * Creates a simple UML operation.
     *
     * @param name the name of the new operation
     * @return a new public operation with the given name; no return type, parameters, or modifiers
     */
    public static Operation createSimpleUmlOperation(final String name) {
        return UmlOperationAndParameterUtil.createUmlOperation(name, null, VisibilityKind.PUBLIC_LITERAL, false, false, null);
    }

    /**
     * Creates a UML operation that acts as an InterfaceMethod. (public, not static, abstract)
     *
     * @param name the name of the operation
     * @param returnType the return type of the operation
     * @param params the list of parameters for the operation
     * @return the new public, abstract operation
     */
    public static Operation createUmlInterfaceOperation(final String name, final Type returnType, final List<Parameter> params) {
        return UmlOperationAndParameterUtil.createUmlOperation(name, returnType, VisibilityKind.PUBLIC_LITERAL, true, false, params);
    }

    /**
     * Creates a new UML operation with the given properties.
     * Return type and parameters can be null if there is no return type or no parameters.
     *
     * @param name the name of the operation
     * @param returnType the return type of the operation
     * @param visibility the visibility of the operation
     * @param abstr if the operation is abstract
     * @param stat if the operation is static
     * @param params the list of parameters for the operation
     * @return the new operation
     */
    public static Operation createUmlOperation(final String name, final Type returnType, final VisibilityKind visibility,
                                               final boolean abstr, final boolean stat, final List<Parameter> params) {
        final Operation uOperation = UMLFactory.eINSTANCE.createOperation();
        setName(uOperation, name);

        if (returnType != null) {
            uOperation.setType(returnType);
        }

        uOperation.setVisibility(visibility);
        uOperation.setIsAbstract(abstr);
        uOperation.setIsStatic(stat);

        if (!IterableExtensions.isNullOrEmpty(params)) {
            uOperation.getOwnedParameters().addAll(params);
        }

        return uOperation;
    }

    /**
     * Creates a UML parameter. If name is null, the parameter becomes a return type (direction: return).
     * Otherwise, it becomes a normal parameter (direction: in).
     *
     * @param name the name of the parameter (can be null for return type)
     * @param type the type of the parameter
     * @return the created parameter
     */
    public static Parameter createUmlParameter(final String name, final Type type) {
        final Parameter param = UMLFactory.eINSTANCE.createParameter();
        param.setType(type);

        if (name == null) {
            param.setDirection(ParameterDirectionKind.RETURN_LITERAL);
        } else {
            param.setName(name);
            param.setDirection(ParameterDirectionKind.IN_LITERAL);
        }

        return param;
    }

    /**
     * Sets the name of the given UML element.
     *
     * @param element the UML element
     * @param name the name to set
     */
    private static void setName(final org.eclipse.uml2.uml.NamedElement element, final String name) {
        if (element != null && name != null) {
            element.setName(name);
        }
    }

    private UmlOperationAndParameterUtil() {
        // Prevent instantiation
    }
}
