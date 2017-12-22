package tools.vitruv.applications.umlclassumlcomponents.comp2class.tests

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.umlclassumlcomponents.comp2class.UmlComp2UmlClassChangePropagation
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.testutils.VitruviusApplicationTest

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.UserInteractionTestUtil.*

abstract class AbstractComp2ClassTest extends VitruviusApplicationTest {
	
	protected def Model getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as Model
	}

	//Hack for handling of one singular UML model instead of two
	override protected getVitruvDomains() {
		return #[new UmlDomainProvider().domain]
	}
		
	override protected createChangePropagationSpecifications() {
		return #[new UmlComp2UmlClassChangePropagation()]
	}
	
	//Hack for handling of one singular UML model instead of two
	override protected getCorrespondenceModel() {
		return this.getVirtualModel().getCorrespondenceModel() 
	}
		
	//SaveAndSynchronize & commit all pending userInteractions
	protected def saveAndSynchronizeWithInteractions(EObject object) {
		sendCollectedUserInteractionSelections(this.userInteractor)
		saveAndSynchronizeChanges(object)
	}
	
	override protected cleanup() {
	}
	
	override protected setup() {
		initializeTestModel()
	}

	protected def initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel()
		umlModel.name = MODEL_NAME
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, umlModel)
	}
	
	private def String getProjectModelPath(String modelName) {
		FOLDER_NAME + modelName + "." + MODEL_FILE_EXTENSION
	}
	
				
	/*****************
	*Creation Helper:*
	******************/		
		
	protected def Component createComponent(String name) {
		val umlComponent = UMLFactory.eINSTANCE.createComponent()
		umlComponent.name = name
		rootElement.packagedElements += umlComponent
		return umlComponent
	}
	
		
	/***************
	*Assert Helper:*
	****************/	

	protected def Class assertClass(PackageableElement compElement, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compElement]).flatten.filter(Class)
		assertEquals(1, correspondingElements.size)
		val umlClass = correspondingElements.get(0)
		assertTypeAndName(umlClass, Class, name)
		return umlClass
	}
	
	protected def Class assertClassAndPackage(Component umlComp, String name) {
		return assertClassAndPackage(umlComp, name, name + PACKAGE_SUFFIX, true)
	}
	
	protected def Class assertClassAndPackage(PackageableElement compElement, String name, String packageName, Boolean packageCorrCheck) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compElement]).flatten
		
		//Check Class
		val umlClass = correspondingElements.filter(Class).get(0)
		
		assertTypeAndName(umlClass, Class, name)		
		//Check Package
		val classPackage = (umlClass as Class).package
		assertEquals(packageName, classPackage.name)
		val packageOwnedTypes = classPackage.ownedTypes
		assertTrue(packageOwnedTypes.size >= 1)
		val classFromPackage = packageOwnedTypes.get(0)
		assertTypeAndName(classFromPackage, Class, name)
		
		if (packageCorrCheck) {
			//Check Package correspondence 
			val classPackageFromCorrespondence = correspondingElements.filter(Package).get(0)		
			assertTypeAndName(classPackageFromCorrespondence, Package, name + PACKAGE_SUFFIX)		
		}
		return umlClass
	}	
	
}

