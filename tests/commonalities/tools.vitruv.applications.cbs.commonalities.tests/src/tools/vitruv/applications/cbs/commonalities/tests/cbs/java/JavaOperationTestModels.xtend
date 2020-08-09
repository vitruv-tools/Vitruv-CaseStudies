package tools.vitruv.applications.cbs.commonalities.tests.cbs.java

import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.members.MembersFactory
import tools.vitruv.applications.cbs.commonalities.tests.cbs.OperationTest
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.*

class JavaOperationTestModels extends JavaTestModelsBase implements OperationTest.DomainModels {

	private static def newJavaInterface() {
		return ClassifiersFactory.eINSTANCE.createInterface => [
			name = INTERFACE_NAME
		]
	}

	private static def newJavaInterfaceMethod() {
		return MembersFactory.eINSTANCE.createInterfaceMethod => [
			name = OPERATION_NAME
		]
	}

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyOperationCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			val contractsPackage = javaRepositoryModel.contractsPackage

			val compilationUnit = contractsPackage.newCompilationUnit(newJavaInterface => [
				members += newJavaInterfaceMethod
			])

			return (javaRepositoryModel.rootObjects + #[
				compilationUnit
			]).toList
		]
	}
}
