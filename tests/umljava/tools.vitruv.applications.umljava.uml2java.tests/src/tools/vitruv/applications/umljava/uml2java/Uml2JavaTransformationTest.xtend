package tools.vitruv.applications.umljava.uml2java

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.UMLFactory

import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import org.eclipse.uml2.uml.Operation
import tools.vitruv.applications.umljava.testutil.AbstractUmlJavaTest

/**
 * Abstract super class for uml to java test cases.
 * Initializes a uml rootmodel.
 * 
 * @author Fei
 */
abstract class Uml2JavaTransformationTest extends AbstractUmlJavaTest {
    protected static val final Logger logger = Logger.getLogger(typeof(Uml2JavaTransformationTest).simpleName);
    
	private static val MODEL_FILE_EXTENSION = "uml";
	private static val MODEL_NAME = "model"; //name of the uml rootmodel

	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Model getRootElement() {
		return getFirstRootElement(MODEL_NAME.projectModelPath) as Model;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new UmlToJavaChangePropagationSpecification()]; 
	}

	override protected cleanup() {
        
    }
    
    override protected setup() {
        val umlModel = UMLFactory.eINSTANCE.createModel();
        umlModel.name = MODEL_NAME;
        createAndSynchronizeModel(MODEL_NAME.projectModelPath, umlModel);
    }

	def protected assertJavaFileExists(String fileName, String[] namespaces) {
	    assertModelExists(buildJavaFilePath(fileName + ".java", namespaces));
	}
    def protected assertJavaFileNotExists(String fileName, String[] namespaces) {
        assertModelNotExists(buildJavaFilePath(fileName + ".java", namespaces));
    }
    
    /**
     * Retrieves the first corresponding java field for a given uml property
     */
    def protected getCorrespondingAttribute(Property uAttribute) {
    	return getFirstCorrespondingObjectWithClass(uAttribute, org.emftext.language.java.members.Field)
    }
    
    /**
     * Retrieves the first corresponding java class method for a given uml operation
     */
    def protected getCorrespondingClassMethod(Operation uOperation) {
    	return getFirstCorrespondingObjectWithClass(uOperation, org.emftext.language.java.members.ClassMethod)
    }
    
    /**
     * Retrieves the first corresponding java interface method for a given uml operation
     */
    def protected getCorrespondingInterfaceMethod(Operation uOperation) {
    	return getFirstCorrespondingObjectWithClass(uOperation, org.emftext.language.java.members.InterfaceMethod)
    }
    
    /**
     * Retrieves the first corresponding java class for a given uml class
     */
    def protected getCorrespondingClass(org.eclipse.uml2.uml.Classifier uClass) {
    	return getFirstCorrespondingObjectWithClass(uClass, org.emftext.language.java.classifiers.Class)
    }
    
    /**
     * Retrieves the first corresponding java compilationunit for a given uml class
     */
    def protected getCorrespondingCompilationUnit(org.eclipse.uml2.uml.Class uClass) {
        return getFirstCorrespondingObjectWithClass(uClass, org.emftext.language.java.containers.CompilationUnit)
    }
    
    /**
     * Retrieves the first corresponding java interface for a given uml interface
     */
    def protected getCorrespondingInterface(org.eclipse.uml2.uml.Interface uInterface) {
    	return getFirstCorrespondingObjectWithClass(uInterface, org.emftext.language.java.classifiers.Interface)
    }
    
    /**
     * Retrieves the first corresponding java enumeration for a given uml enumeration
     */
    def protected getCorrespondingEnum(org.eclipse.uml2.uml.Enumeration uEnumeration) {
    	return getFirstCorrespondingObjectWithClass(uEnumeration, org.emftext.language.java.classifiers.Enumeration)
    }
    
    /**
     * Retrieves the first corresponding java ordinary parameter for a given uml parameter
     */
    def protected getCorrespondingParameter(org.eclipse.uml2.uml.Parameter uParam) {
    	return getFirstCorrespondingObjectWithClass(uParam, org.emftext.language.java.parameters.OrdinaryParameter)
    }
    
    /**
     * Retrieves the first corresponding java package for a given uml package
     */
    def protected getCorrespondingPackage(org.eclipse.uml2.uml.Package uPackage) {
        return getFirstCorrespondingObjectWithClass(uPackage, org.emftext.language.java.containers.Package)
    }
    
    /**
     * Retrieves the first corresponding java constructor for a given uml operation
     */
    def protected getCorrespondingConstructor(org.eclipse.uml2.uml.Operation uOperation) {
        return getFirstCorrespondingObjectWithClass(uOperation, org.emftext.language.java.members.Constructor)
    }
    
    
    
}
