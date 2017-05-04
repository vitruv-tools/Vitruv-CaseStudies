package tools.vitruv.applications.umljava.java2uml

import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import static org.junit.Assert.*;
import org.eclipse.uml2.uml.Model
import tools.vitruv.framework.tests.util.TestUtil
import org.apache.log4j.PropertyConfigurator
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.EList
import org.emftext.language.java.classifiers.Interface
import static tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil.*
import org.emftext.language.java.members.Method
import org.emftext.language.java.members.Field
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import tools.vitruv.applications.umljava.testutil.AbstractUmlJavaTest
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Property
import org.apache.log4j.Logger

class Java2UmlTransformationTest extends AbstractUmlJavaTest {
    private static val logger = Logger.getLogger(Java2UmlTransformationTest.simpleName)
    private static val UMLMODELPATH = "rootModelDirectory" //Directory of the Uml Model Path used in the java2uml tests
    private static val UMLMODELNAME = "rootModelName" //Name of the Uml Model used in the java2uml tests
	
	
	override protected cleanup() {

    }
    
    override protected setup() {
        PropertyConfigurator.configure("log4j.properties")
        userInteractor.addNextSelections(UMLMODELNAME, UMLMODELPATH)
    }
	
	override protected createChangePropagationSpecifications() {
		return #[new JavaToUmlChangePropagationSpecification()]; 
	}
	
    def protected createJavaClassWithCompilationUnit(String cName, JavaVisibility vis, boolean abstr, boolean fin) {
        val cu = createCompilationUnitAsModel(cName);
        val cls = createJavaClass(cName, vis, abstr, fin)
        cu.classifiers += cls;
        saveAndSynchronizeChanges(cu);
        //this.changeRecorder.beginRecording(VURI.getInstance(cls.eResource), #[cls.eResource])
        return cls;
    }
    
    def protected createSimpleJavaClassWithCompilationUnit(String name) {
        return createJavaClassWithCompilationUnit(name, JavaVisibility.PUBLIC, false, false);
    }
    
    def protected createSimpleJavaInterfaceWithCompilationUnit(String name) {
        return createJavaInterfaceWithCompilationUnit (name, null);
    }
    
    /**
     * Sichtbarkeit wird automatisch auf Public gesetzt
     */
    def protected createJavaInterfaceWithCompilationUnit (String name, EList<Interface> superInterfaces) {
        val cu = createCompilationUnitAsModel(name);
        val jI = createJavaInterface(name, superInterfaces)
        cu.classifiers += jI;
        saveAndSynchronizeChanges(cu);
        return jI;
    }

    /**
     * @param Name ohne .java
     */
    def private createCompilationUnitAsModel(String name) {
        val cu = createEmptyCompilationUnit(name)
        createAndSynchronizeModel(buildJavaFilePath(cu), cu)
        return cu
    }
    
    def protected createJavaPackageAsModel(String name, org.emftext.language.java.containers.Package superPackage) {
        val jPackage = createJavaPackage(name, superPackage)
        createAndSynchronizeModel(buildJavaFilePath(jPackage), jPackage)
        return jPackage
    }


    def protected getUmlPackagedElementsbyName(String modelPath, Class<? extends org.eclipse.uml2.uml.PackageableElement> type, String elementName) {
        val model = getUmlRootModel(modelPath)
        return model.packagedElements.filter(type).filter[it.name == elementName].toList;
    }
    
    def protected getCorrespondingClass(org.emftext.language.java.classifiers.Class jClass) {
        return getFirstCorrespondingObjectWithClass(jClass, org.eclipse.uml2.uml.Class)
    }
    
    def protected getCorrespondingInterface(org.emftext.language.java.classifiers.Interface jInterface) {
        return getFirstCorrespondingObjectWithClass(jInterface, org.eclipse.uml2.uml.Interface)
    }
    def protected getCorrespondingEnum(org.emftext.language.java.classifiers.Enumeration jEnum) {
        return getFirstCorrespondingObjectWithClass(jEnum, org.eclipse.uml2.uml.Enumeration)
    }
    def protected getCorrespondingMethod(Method jMethod) {
        return getFirstCorrespondingObjectWithClass(jMethod, Operation)
    }
    def protected getCorrespondingAttribute(Field jAttribute) {
        return getFirstCorrespondingObjectWithClass(jAttribute, Property)
    }
    def protected getCorrespondingParameter(org.emftext.language.java.parameters.OrdinaryParameter jParam) {
        return getFirstCorrespondingObjectWithClass(jParam, org.eclipse.uml2.uml.Parameter)
    }
    def protected getCorrespondingPackage(org.emftext.language.java.containers.Package jPackage) {
        return getFirstCorrespondingObjectWithClass(jPackage, org.eclipse.uml2.uml.Package)
    }
    
    def protected getUmlRootModel(String modelPath) {
        val model = getModelResource(modelPath).allContents.head
        if (!(model instanceof Model)) {
            logger.warn("No Uml Rootmodel found.")
            return null
        }
        return model as Model
    }
    

}
