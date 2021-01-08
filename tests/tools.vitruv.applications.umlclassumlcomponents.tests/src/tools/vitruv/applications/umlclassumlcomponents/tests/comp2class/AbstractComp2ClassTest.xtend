package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.umlclassumlcomponents.comp2class.UmlComp2UmlClassChangePropagation

import static tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.tests.util.UserInteractionTestUtil.*
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.testutils.LegacyVitruvApplicationTest
import java.nio.file.Path

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals

abstract class AbstractComp2ClassTest extends LegacyVitruvApplicationTest {

	protected def Model getRootElement() {
		return Model.from(MODEL_NAME.projectModelPath)
	}

	override protected getChangePropagationSpecifications() {
		return #[new UmlComp2UmlClassChangePropagation()]
	}

	// SaveAndSynchronize & commit all pending userInteractions
	protected def saveAndSynchronizeWithInteractions(EObject object) {
		sendCollectedUserInteractionSelections(this.userInteraction)
		propagate
	}

	@BeforeEach
	def protected setup() {
		initializeTestModel()
	}

	protected def void initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel() => [
			name = MODEL_NAME
		]
		resourceAt(MODEL_NAME.projectModelPath).startRecordingChanges => [
			contents += umlModel
		]
		propagate
	}

	private def Path getProjectModelPath(String modelName) {
		Path.of(FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

	/*****************
	 * Creation Helper:*
	 ******************/
	protected def Component createComponent(String name) {
		val umlComponent = UMLFactory.eINSTANCE.createComponent()
		umlComponent.name = name
		rootElement.packagedElements += umlComponent
		return umlComponent
	}

	/***************
	 * Assert Helper:*
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

	protected def Class assertClassAndPackage(PackageableElement compElement, String name, String packageName,
		Boolean packageCorrCheck) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[compElement]).flatten

		// Check Class
		val umlClass = correspondingElements.filter(Class).get(0)

		assertTypeAndName(umlClass, Class, name)
		// Check Package
		val classPackage = umlClass.package
		assertEquals(packageName, classPackage.name)
		val packageOwnedTypes = classPackage.ownedTypes
		assertTrue(packageOwnedTypes.size >= 1)
		val classFromPackage = packageOwnedTypes.get(0)
		assertTypeAndName(classFromPackage, Class, name)

		if (packageCorrCheck) {
			// Check Package correspondence 
			val classPackageFromCorrespondence = correspondingElements.filter(Package).get(0)
			assertTypeAndName(classPackageFromCorrespondence, Package, name + PACKAGE_SUFFIX)
		}
		return umlClass
	}

}
