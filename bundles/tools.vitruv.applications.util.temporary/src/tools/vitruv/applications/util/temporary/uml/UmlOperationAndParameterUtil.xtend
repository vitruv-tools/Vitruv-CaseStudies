package tools.vitruv.applications.util.temporary.uml

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.List
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind

import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.setName

/**
 * A util class for uml operations and parameters
 *
 * @author Fei
 */
@Utility
class UmlOperationAndParameterUtil {

    /**
     * Creats a simple uml operation.
     *
     * @param name the name of the new operation
     * @return new public Operation with name; no return, params or modifier
     */
    def static Operation createSimpleUmlOperation(String name) {
        return createUmlOperation(name, null, VisibilityKind.PUBLIC_LITERAL, false, false, null)
    }

    /**
     * Creates an uml operation that acts as InterfaceMethod. (public, not static, abstract)
     */
    def static Operation createUmlInterfaceOperation(String name, Type returnType, List<Parameter> params) {
        return createUmlOperation(name, returnType, VisibilityKind.PUBLIC_LITERAL, true, false, params)
    }

    /**
     * Creates a new uml operation with the given properties.
     * return and params can be null if there is no return / no parameters
     *
     * @param name the name of the operation
     * @param returnType the return type of the operation
     * @param visibility the visibility of the operation
     * @param abstr if the operation is abstract
     * @param stat if the operation is static
     * @param params parameter list for the operation
     * @return the new operation
     */
    def static Operation createUmlOperation(String name, Type returnType, VisibilityKind visibility, boolean abstr, boolean stat, List<Parameter> params) {
        val uOperation = UMLFactory.eINSTANCE.createOperation
        setName(uOperation, name)
        if (returnType !== null) {
            uOperation.type = returnType
        }
        uOperation.visibility = visibility
        uOperation.isAbstract = abstr
        uOperation.isStatic = stat
        if (!params.nullOrEmpty) {
            uOperation.ownedParameters.addAll(params)
        }
        return uOperation
    }

    /**
     * If name == null, it becomes a return type (direction: return)
     * Otherwise it beacomes a normal parameter (direction: in)
     */
    def static Parameter createUmlParameter(String name, Type type) {
        val param = UMLFactory.eINSTANCE.createParameter
        param.type = type
        if (name === null) {
            param.direction = ParameterDirectionKind.RETURN_LITERAL
        } else {
            param.name = name
            param.direction = ParameterDirectionKind.IN_LITERAL
        }
        return param
    }
}
