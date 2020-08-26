package tools.vitruv.applications.cbs.commonalities.tests.cbs.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.BasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaBasicComponentTestModels extends JavaTestModelsBase implements BasicComponentTest.DomainModels {

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

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyBasicComponentCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val repositoryPackage = javaRepositoryModel.repositoryPackage

			val componentPackage = repositoryPackage.insertJavaPackage(newJavaComponentPackage)

			val componentClass = newJavaComponentClass
			val compilationUnit = componentPackage.newCompilationUnit(componentClass)

			return (javaRepositoryModel.rootObjects + #[
				componentPackage,
				compilationUnit
			]).toList
		]
	}
}
