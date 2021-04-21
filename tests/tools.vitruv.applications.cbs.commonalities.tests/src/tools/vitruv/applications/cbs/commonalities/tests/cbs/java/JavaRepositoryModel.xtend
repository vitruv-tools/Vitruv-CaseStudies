package tools.vitruv.applications.cbs.commonalities.tests.cbs.java

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.emftext.language.java.containers.Package
import tools.vitruv.applications.cbs.commonalities.tests.TestConstants.Java

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

@Accessors
class JavaRepositoryModel {
	val Package repositoryPackage = newJavaPackage(null, Java.REPOSITORY_PACKAGE_NAME)
	val Package datatypesPackage = repositoryPackage.newJavaPackage(Java.DATATYPES_PACKAGE_NAME)
	val Package contractsPackage = repositoryPackage.newJavaPackage(Java.CONTRACTS_PACKAGE_NAME)

	def List<? extends EObject> getRootObjects() {
		List.of(repositoryPackage, datatypesPackage, contractsPackage)
	}
}
