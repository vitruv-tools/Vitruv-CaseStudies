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
	
	protected def void assertJavaCompilationUnitCount(int number) {
		createJavaClassesView() => [
			assertThat("expected number of compilation units must exist", rootObjects.size, is(number))
		]
	}
	
	protected def void createPackageInRootPackage(String packageName) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createPackage => [
				it.name = packageName
			]
		]
	}

	protected def void createInterfaceInRootPackage(String interfaceName) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createInterface => [
				it.name = interfaceName
				it.visibility = VisibilityKind.PUBLIC_LITERAL
			]
		]
		createUmlAndJavaClassesView() => [
			val umlInterface = defaultUmlModel.claimInterface(interfaceName)
			val javaInterface = claimJavaInterface(interfaceName)
			assertJavaFileExists(interfaceName, #[])
			assertElementsEqual(umlInterface, javaInterface)
		]
	}
	
	protected def void createInterfaceInPackage(String packageName, String interfaceName) {
		createPackageInRootPackage(packageName)
		changeUmlModel [
			claimPackage(packageName) => [
				packagedElements += UMLFactory.eINSTANCE.createInterface => [
					it.name = interfaceName
					it.visibility = VisibilityKind.PUBLIC_LITERAL
				]
			]
		]
		createUmlAndJavaClassesView() => [
			val umlInterface = defaultUmlModel.claimPackage(packageName).claimInterface(interfaceName)
			val javaInterface = claimJavaInterface(interfaceName)
			assertJavaFileExists(interfaceName, #[packageName])
			assertElementsEqual(umlInterface, javaInterface)
		]
	}
	
	protected def void createClassInRootPackage(String className) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createClass => [
				it.name = className
				it.visibility = VisibilityKind.PUBLIC_LITERAL
			]
		]
		assertJavaFileExists(className, #[])
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimClass(className)
			val javaClass = claimJavaClass(className)
			assertElementsEqual(umlClass, javaClass)
		]
	}
	
	protected def void createClassInPackage(String packageName, String className) {
		createPackageInRootPackage(packageName)
		changeUmlModel [
			claimPackage(packageName) => [
				packagedElements += UMLFactory.eINSTANCE.createClass => [
					it.name = className
					it.visibility = VisibilityKind.PUBLIC_LITERAL
				]
			]
		]
		assertJavaFileExists(className, #[packageName])
		createUmlAndJavaClassesView() => [
			val umlClass = defaultUmlModel.claimPackage(packageName).claimClass(className)
			val javaClass = claimJavaClass(className)
			assertElementsEqual(umlClass, javaClass)
		]
	}

	protected def void createDataTypeInRootPackage(String dataTypeName) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createDataType => [
				it.name = dataTypeName
			]
		]
		createUmlAndJavaClassesView() => [
			val umlDataType = defaultUmlModel.claimDataType(dataTypeName)
			val javaClass = claimJavaClass(umlDataType.name)
			assertJavaFileExists(umlDataType.name, #[])
			assertThat("Data types must have the same name", dataTypeName, is(javaClass.name))
		]
	}
}
