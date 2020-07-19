package tools.vitruv.applications.cbs.commonalities.tests.cbs.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractBasicComponentTest
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaBasicComponentTestModels extends JavaTestModelsBase implements AbstractBasicComponentTest.DomainModels {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyBasicComponentCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val repositoryPackage = javaRepositoryModel.repositoryPackage

			val componentPackage = repositoryPackage.newJavaPackage(COMPONENT_NAME.toFirstLower)

			val componentClassName = COMPONENT_NAME + 'Impl'
			val componentClass = ClassifiersFactory.eINSTANCE.createClass => [
				name = componentClassName
				annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
			]
			val compilationUnit = componentPackage.newCompilationUnit(componentClass)

			return (javaRepositoryModel.rootObjects + #[
				componentPackage,
				compilationUnit
			]).toList
		]
	}
}
