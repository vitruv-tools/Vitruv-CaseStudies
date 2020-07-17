package tools.vitruv.applications.cbs.commonalities.tests.util.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Arrays
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package

@Utility
class JavaModelHelper {

	static def Iterable<String> getFullPackageNamespaces(Package javaPackage) {
		return javaPackage.namespaces + Arrays.asList(javaPackage.name) // TODO duplication with JavaPersistenceHelper
	}

	static def createJavaPackage(Package parentPackage, String packageName) {
		return ContainersFactory.eINSTANCE.createPackage => [
			if (parentPackage !== null) {
				namespaces += parentPackage.fullPackageNamespaces
			}
			name = packageName
		]
	}
}
