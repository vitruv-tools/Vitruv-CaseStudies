package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.applications.cbs.commonalities.tests.TestConstants.UML
import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.*

@Accessors
class UmlRepositoryModel {
	val Model model = newUmlModel
	val Package repositoryPackage = UMLFactory.eINSTANCE.createPackage => [
		model.packagedElements += it
		name = UML.REPOSITORY_PACKAGE_NAME
	]
	val Package datatypesPackage = UMLFactory.eINSTANCE.createPackage => [
		repositoryPackage.packagedElements += it
		name = UML.DATATYPES_PACKAGE_NAME
	]
	val Package contractsPackage = UMLFactory.eINSTANCE.createPackage => [
		repositoryPackage.packagedElements += it
		name = UML.CONTRACTS_PACKAGE_NAME
	]
}
