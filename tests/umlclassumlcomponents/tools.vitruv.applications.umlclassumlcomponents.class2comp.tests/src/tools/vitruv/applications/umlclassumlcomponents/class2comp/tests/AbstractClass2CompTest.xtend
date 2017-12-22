package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.umlclassumlcomponents.class2comp.UmlClass2UmlCompChangePropagation
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.testutils.VitruviusApplicationTest

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.UserInteractionTestUtil.*

abstract class AbstractClass2CompTest extends VitruviusApplicationTest {

	protected def Model getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as Model
	}
	
	//Hack for handling of one singular UML model instead of two
	override protected getVitruvDomains() {
		return #[new UmlDomainProvider().domain]
	}
		
	override protected createChangePropagationSpecifications() {
		return #[new UmlClass2UmlCompChangePropagation()]
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
	

	/***************
	*Assert Helper:*
	****************/		

	package def Component assertComponentForClass(Class umlClass, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		assertEquals(1, correspondingElements.size)
		val umlComponent = correspondingElements.get(0)
		assertTypeAndName(umlComponent, Component, name)
		return umlComponent as Component
	}	
	
	package def Component assertPackageLinkedToComponent(Package classPackage, String componentName) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classPackage]).flatten.filter(Component)
		assertEquals(1, correspondingElements.size)
		val umlComponent = correspondingElements.get(0)
		assertTypeAndName(umlComponent, Component, componentName)
		return umlComponent
	}
		
	/*****************
	*Creation Helper:*
	******************/		

	package def Class createClassWithoutInteraction(String name) {
		val classPackage = createPackage(name + PACKAGE_SUFFIX)
		return createClassWithoutInteraction(name, classPackage)
	}
	
	package def Class createClassWithoutInteraction(String name, Package classPackage) {
		val umlClass = UMLFactory.eINSTANCE.createClass()
		umlClass.name = name
		classPackage.packagedElements += umlClass
	
		return umlClass
	}
			
	package def Class createClass(String name, int createNoComponent) {
		val classPackage = createPackage(name + PACKAGE_SUFFIX)
		return createClass(name, classPackage, createNoComponent)
	}
	
	package def Class createClass(String name, Package classPackage, int createNoComponent) {
		val umlClass = createClassWithoutInteraction(name, classPackage)		
				
		//Decide whether to create corresponding Component or not:
		queueUserInteractionSelections(createNoComponent) 
		
		return umlClass
	} 
	
	package def Package createPackage(String name) {
		createPackage(name, 1, 0)
	}
	
	package def Package createPackage(String name, int linkToNoComponent, int componentSelection) {
		val classPackage = UMLFactory.eINSTANCE.createPackage()
		classPackage.name = name + PACKAGE_SUFFIX
		rootElement.packagedElements += classPackage
		
		//Decide whether to link this Package to an existing Component
		if (linkToNoComponent == 1)
			queueUserInteractionSelections(linkToNoComponent)
		else
			queueUserInteractionSelections(linkToNoComponent, componentSelection)
		
		return classPackage
	}	
}

