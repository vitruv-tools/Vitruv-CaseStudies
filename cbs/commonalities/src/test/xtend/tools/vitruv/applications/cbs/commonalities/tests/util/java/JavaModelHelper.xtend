package tools.vitruv.applications.cbs.commonalities.tests.util.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Arrays
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package

import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaFilePathHelper.*

@Utility
class JavaModelHelper {

	static def Iterable<String> getFullPackageNamespaces(Package javaPackage) {
		return javaPackage.namespaces + Arrays.asList(javaPackage.name) // TODO duplication with JavaPersistenceHelper
	}

	static def newJavaPackage(Package parentPackage, String packageName) {
		return ContainersFactory.eINSTANCE.createPackage => [
			parentPackage.insertJavaPackage(it)
			name = packageName
		]
	}

	static def insertJavaPackage(Package parentPackage, Package subPackage) {
		subPackage.namespaces.clear
		if (parentPackage !== null) {
			subPackage.namespaces += parentPackage.fullPackageNamespaces
		}
		return subPackage
	}

	static def newCompilationUnit(Package parentPackage, String fileName) {
		return ContainersFactory.eINSTANCE.createCompilationUnit => [
			parentPackage.insertCompilationUnit(it)
			name = getCompilationUnitName(namespaces, fileName)
		]
	}

	static def insertCompilationUnit(Package parentPackage, CompilationUnit compilationUnit) {
		compilationUnit.namespaces.clear
		if (parentPackage !== null) {
			compilationUnit.namespaces += parentPackage.fullPackageNamespaces
		}
		return compilationUnit
	}

	static def <C extends ConcreteClassifier> newCompilationUnit(Package parentPackage, C classifier) {
		return parentPackage.newCompilationUnit(classifier.name) => [
			classifiers += classifier
		]
	}
}
