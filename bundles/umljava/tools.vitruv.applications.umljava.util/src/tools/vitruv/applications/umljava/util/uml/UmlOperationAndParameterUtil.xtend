package tools.vitruv.applications.umljava.util.uml

import java.util.List
import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import static tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil.setName

class UmlOperationAndParameterUtil {
    private static val logger = Logger.getLogger(UmlOperationAndParameterUtil.simpleName)
    
    /**
     * Prevents instantiation
     */
    private new () {}
    
    /**
     * @return public Operation with name; no return, params or modifier
     */
    def static Operation createSimpleUmlOperation(String name) {
        return createUmlOperation(name, null, VisibilityKind.PUBLIC_LITERAL, false, false, null)
    }
    
    /**
     * Creates an uml operation that acts as InterfaceMethod. (public, not static, not abstract)
     */
    def protected Operation createUmlInterfaceOperation(String name, Type returnType, List<Parameter> params) {
        return createUmlOperation(name, returnType, VisibilityKind.PUBLIC_LITERAL, false, false, params);
    }
    
    /**
     * 
     * return and params can be null
     * @throws IllegalArgumentException if name or visibility is null
     */
    def static Operation createUmlOperation(String name, Type returnType, VisibilityKind visibility, boolean abstr, boolean stat, List<Parameter> params) {
        val uOperation = UMLFactory.eINSTANCE.createOperation;
        setName(uOperation, name)
        if (returnType !== null) {
            uOperation.type = returnType;
        }
        uOperation.visibility = visibility;
        uOperation.isAbstract = abstr;
        uOperation.isStatic = stat;
        if (!params.nullOrEmpty) {
            uOperation.ownedParameters.addAll(params);
        }
        return uOperation;
    }
    
    /**
     * If name == null, it becomes a return type
     */
    def static Parameter createUmlParameter(String name, Type type) {
        val param = UMLFactory.eINSTANCE.createParameter;
        param.type = type;
        if (name === null) {
            param.direction = ParameterDirectionKind.RETURN_LITERAL;
        } else {
            param.name = name;
            param.direction = ParameterDirectionKind.IN_LITERAL;
        }
        return param;
    }
}