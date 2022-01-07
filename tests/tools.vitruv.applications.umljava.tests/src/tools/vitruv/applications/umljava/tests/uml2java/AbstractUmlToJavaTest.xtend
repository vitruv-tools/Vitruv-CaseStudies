package tools.vitruv.applications.umljava.tests.uml2java

import tools.vitruv.applications.umljava.tests.UmlJavaTransformationTest
import org.eclipse.uml2.uml.Model
import org.junit.jupiter.api.BeforeEach
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import org.eclipse.uml2.uml.PackageableElement

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

	private def void addPackageableElementToRootPackage(PackageableElement uninitializedElement, String name) {
		changeUmlModel [
			packagedElements += uninitializedElement => [
				it.name = name
				it.visibility = VisibilityKind.PUBLIC_LITERAL
			]
		]
	}

	private def void addPackageableElement(String packageName, PackageableElement uninitializedElement, String name) {
		createPackageInRootPackage(packageName)
		changeUmlModel [
			claimPackage(packageName).packagedElements += uninitializedElement => [
				it.name = name
				it.visibility = VisibilityKind.PUBLIC_LITERAL
			]
		]
	}

	protected def void createPackageInRootPackage(String packageName) {
		changeUmlModel [
			packagedElements += UMLFactory.eINSTANCE.createPackage => [
				it.name = packageName
			]
		]
	}

	protected def void createEnumInRootPackage(String enumName) {
		addPackageableElementToRootPackage(UMLFactory.eINSTANCE.createEnumeration, enumName)
		assertEnumWithNameInRootPackage(enumName)
	}

	protected def void createEnumInPackage(String packageName, String enumName) {
		packageName.addPackageableElement(UMLFactory.eINSTANCE.createEnumeration, enumName)
		assertEnumWithNameInPackage(packageName, enumName)
	}

	protected def void createInterfaceInRootPackage(String interfaceName) {
		addPackageableElementToRootPackage(UMLFactory.eINSTANCE.createInterface, interfaceName)
		assertInterfaceWithNameInRootPackage(interfaceName)
	}

	protected def void createInterfaceInPackage(String packageName, String interfaceName) {
		packageName.addPackageableElement(UMLFactory.eINSTANCE.createInterface, interfaceName)
		assertInterfaceWithNameInPackage(packageName, interfaceName)
	}

	protected def void createClassInRootPackage(String className) {
		addPackageableElementToRootPackage(UMLFactory.eINSTANCE.createClass, className)
		assertClassWithNameInRootPackage(className)
	}

	protected def void createClassInPackage(String packageName, String className) {
		packageName.addPackageableElement(UMLFactory.eINSTANCE.createClass, className)
		assertClassWithNameInPackage(packageName, className)
	}

	protected def void createDataTypeInRootPackage(String dataTypeName) {
		addPackageableElementToRootPackage(UMLFactory.eINSTANCE.createDataType, dataTypeName)
		assertDataTypeWithNameInRootPackage(dataTypeName)
	}

}
