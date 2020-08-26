package tools.vitruv.applications.cbs.commonalities.tests.cbs.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.ProvidedRoleTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase
import tools.vitruv.domains.java.util.JavaModificationUtil

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaProvidedRoleTestModels extends JavaTestModelsBase implements ProvidedRoleTest.DomainModels {

	private static def newJavaComponentPackage() {
		return ContainersFactory.eINSTANCE.createPackage => [
			name = COMPONENT_NAME.toFirstLower
		]
	}

	private static def newJavaComponentClass() {
		return ClassifiersFactory.eINSTANCE.createClass => [
			name = COMPONENT_NAME + 'Impl'
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
		]
	}

	private static def newJavaInterface1() {
		return ClassifiersFactory.eINSTANCE.createInterface => [
			name = INTERFACE_1_NAME
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
		]
	}

	private static def newJavaInterface2() {
		return newJavaInterface1 => [
			name = INTERFACE_2_NAME
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override componentWithProvidedRoleCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage
			val repositoryPackage = javaRepositoryModel.repositoryPackage

			val interface1 = newJavaInterface1
			val compilationUnitInterface1 = contractsPackage.newCompilationUnit(interface1)

			val componentPackage = repositoryPackage.insertJavaPackage(newJavaComponentPackage)
			val componentClass = newJavaComponentClass => [
				implements += JavaModificationUtil.createNamespaceClassifierReference(interface1)
			]
			val compilationUnitClass = componentPackage.newCompilationUnit(componentClass)

			return (javaRepositoryModel.rootObjects + #[
				compilationUnitInterface1,
				componentPackage,
				compilationUnitClass
			]).toList
		]
	}

	override componentWithMultipleProvidedRolesCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage
			val repositoryPackage = javaRepositoryModel.repositoryPackage

			val interface1 = newJavaInterface1
			val compilationUnitInterface1 = contractsPackage.newCompilationUnit(interface1)

			val interface2 = newJavaInterface2
			val compilationUnitInterface2 = contractsPackage.newCompilationUnit(interface2)

			val componentPackage = repositoryPackage.insertJavaPackage(newJavaComponentPackage)
			val componentClass = newJavaComponentClass => [
				implements += JavaModificationUtil.createNamespaceClassifierReference(interface1)
				implements += JavaModificationUtil.createNamespaceClassifierReference(interface2)
			]
			val compilationUnitClass = componentPackage.newCompilationUnit(componentClass)

			return (javaRepositoryModel.rootObjects + #[
				compilationUnitInterface1,
				compilationUnitInterface2,
				componentPackage,
				compilationUnitClass
			]).toList
		]
	}
}
