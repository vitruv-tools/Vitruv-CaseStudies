package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.applications.cbs.commonalities.tests.TestConstants.PCM
import static tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelHelper.*

class UmlRepositoryModel {

	@Accessors(PUBLIC_GETTER)
	val Model model
	@Accessors(PUBLIC_GETTER)
	val Package repositoryPackage
	@Accessors(PUBLIC_GETTER)
	val Package datatypesPackage
	@Accessors(PUBLIC_GETTER)
	val Package contractsPackage

	new() {
		// Construct UML model for empty PCM repository:
		model = newUmlModel
		repositoryPackage = UMLFactory.eINSTANCE.createPackage => [
			model.packagedElements += it
			name = PCM.REPOSITORY_NAME
		]
		datatypesPackage = UMLFactory.eINSTANCE.createPackage => [
			repositoryPackage.packagedElements += it
			name = PCM.DATATYPES_PACKAGE_NAME
		]
		contractsPackage = UMLFactory.eINSTANCE.createPackage => [
			repositoryPackage.packagedElements += it
			name = PCM.CONTRACTS_PACKAGE_NAME
		]
	}
}
