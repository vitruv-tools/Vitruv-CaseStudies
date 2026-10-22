package tools.vitruv.applications.cbs.commonalities.tests.oo.java

import org.emftext.language.java.containers.ContainersFactory
import tools.vitruv.applications.cbs.commonalities.tests.oo.PackageTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase

class JavaPackageTestModels extends JavaTestModelsBase implements PackageTest.DomainModels {

	private static def newRoot1JavaPackage() {
		return ContainersFactory.eINSTANCE.createPackage => [
			name = ROOT1_PACKAGE_NAME
		]
	}

	private static def newRoot2JavaPackage() {
		return ContainersFactory.eINSTANCE.createPackage => [
			name = ROOT2_PACKAGE_NAME
		]
	}

	private static def newRoot1Sub1JavaPackage() {
		return ContainersFactory.eINSTANCE.createPackage => [
			namespaces += #[ROOT1_PACKAGE_NAME]
			name = SUB1_PACKAGE_NAME
		]
	}

	private static def newRoot1Sub2JavaPackage() {
		return ContainersFactory.eINSTANCE.createPackage => [
			namespaces += #[ROOT1_PACKAGE_NAME]
			name = SUB2_PACKAGE_NAME
		]
	}

	private static def newRoot2Sub1JavaPackage() {
		return ContainersFactory.eINSTANCE.createPackage => [
			namespaces += #[ROOT2_PACKAGE_NAME]
			name = SUB1_PACKAGE_NAME
		]
	}

	private static def newRoot2Sub2JavaPackage() {
		return ContainersFactory.eINSTANCE.createPackage => [
			namespaces += #[ROOT2_PACKAGE_NAME]
			name = SUB2_PACKAGE_NAME
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override singleRootPackageCreation() {
		return newModel [
			return #[
				newRoot1JavaPackage
			]
		]
	}

	override multiRootPackageCreation() {
		return newModel [
			return #[
				newRoot1JavaPackage,
				newRoot2JavaPackage
			]
		]
	}

	override subPackagesCreation() {
		return newModel [
			return #[
				newRoot1JavaPackage,
				newRoot1Sub1JavaPackage,
				newRoot1Sub2JavaPackage,
				newRoot2JavaPackage,
				newRoot2Sub1JavaPackage,
				newRoot2Sub2JavaPackage
			]
		]
	}
}
