package tools.vitruv.applications.cbs.commonalities.tests.cbs.java

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.emftext.language.java.containers.Package
import tools.vitruv.applications.cbs.commonalities.tests.TestConstants.PCM

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaRepositoryModel {

	@Accessors(PUBLIC_GETTER)
	val Package repositoryPackage
	@Accessors(PUBLIC_GETTER)
	val Package datatypesPackage
	@Accessors(PUBLIC_GETTER)
	val Package contractsPackage

	new() {
		// Construct Java model for empty PCM repository:
		repositoryPackage = newJavaPackage(null, PCM.REPOSITORY_NAME)
		datatypesPackage = repositoryPackage.newJavaPackage(PCM.DATATYPES_PACKAGE_NAME)
		contractsPackage = repositoryPackage.newJavaPackage(PCM.CONTRACTS_PACKAGE_NAME)
	}

	def List<? extends EObject> getRootObjects() {
		return #[
			repositoryPackage,
			datatypesPackage,
			contractsPackage
		]
	}
}
