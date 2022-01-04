package tools.vitruv.applications.umljava.tests.uml2java

import tools.vitruv.applications.umljava.tests.UmlJavaTransformationTest
import org.eclipse.uml2.uml.Model
import org.junit.jupiter.api.BeforeEach
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import static extension tools.vitruv.applications.umljava.tests.util.JavaQueryUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat

abstract class AbstractUmlToJavaTest extends UmlJavaTransformationTest {

	@BeforeEach
	def protected void setup() {
		createUmlModel[name = UML_MODEL_NAME]
	}

	/**
	 * Changes the UML model in the UML view and commits the performed changes.
	 * 
	 * Precondition: Can only be applied if single UML model exists
	 */
	protected def void changeUmlModel((Model)=>void modelModification) {
		changeView(createUmlView) [
			modelModification.apply(defaultUmlModel)
		]
	}

	protected def void createInterfaceInRootPackage(String name) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createInterface => [
				it.name = name
				it.visibility = VisibilityKind.PUBLIC_LITERAL
			]
		]
		createUmlAndJavaClassesView() => [
			val umlInterface = defaultUmlModel.claimInterface(name)
			val javaInterface = claimJavaInterface(name)
			assertJavaFileExists(name, #[])
			assertElementsEqual(umlInterface, javaInterface)
		]
	}
	
	protected def void createClassInRootPackage(String name) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createClass => [
				it.name = name
				it.visibility = VisibilityKind.PUBLIC_LITERAL
			]
		]
		assertJavaFileExists(name, #[])
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimClass(name)
			val javaClass = claimJavaClass(name)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	protected def void createDataTypeInRootPackage(String name) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createDataType => [
				it.name = name
			]
		]
		createUmlAndJavaClassesView() => [
			val umlDataType = defaultUmlModel.claimDataType(name)
			val javaClass = claimJavaClass(umlDataType.name)
			assertJavaFileExists(umlDataType.name, #[])
			assertThat("Data types must have the same name", name, is(javaClass.name))
		]
	}
}
