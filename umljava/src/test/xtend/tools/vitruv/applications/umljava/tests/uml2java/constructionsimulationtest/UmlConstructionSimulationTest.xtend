package tools.vitruv.applications.umljava.tests.uml2java.constructionsimulationtest

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Model
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import tools.vitruv.applications.umljava.tests.uml2java.AbstractUmlToJavaTest

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getFirstRootEObject

class UmlConstructionSimulationTest extends AbstractUmlToJavaTest {
	static val RESOURCES_FOLDER = "src/test/resources/"

	override void setup() {}

	@ParameterizedTest(name="for file: {0}")
	@DisplayName("simulate construction of existing model")
	@ValueSource(strings=#[
		"synthetic/model1",
		"synthetic/model2",
		"suresh519/uml/MyProject", // UML model from "myproject" by suresh519: https://repository.genmymodel.com/suresh519/MyProject (12.5.2017)
		"orhanobut/uml/model" // UML model from the logger project by orhan obut:  https://github.com/orhanobut/logger (12.5.2017)
	])
	def void testCompleteModel(String modelFileName) {
		transformUmlModelAndValidateJavaCode(RESOURCES_FOLDER + modelFileName + "." + MODEL_FILE_EXTENSION)
	}

	def private void transformUmlModelAndValidateJavaCode(String modelPath) {
		val resourceSet = new ResourceSetImpl()
		val model = resourceSet.getResource(URI.createFileURI(modelPath), true).firstRootEObject as Model => [
			name = UML_MODEL_NAME
		]
		EcoreUtil.resolveAll(model)
		changeJavaView [
			createAndRegisterRoot(model, UML_MODEL_NAME.projectModelPath.uri)
		]
		for (class : model.packagedElements.filter(org.eclipse.uml2.uml.Class).toList) {
			assertClassWithNameInRootPackage(class.name)
		}
		for (interface : model.packagedElements.filter(org.eclipse.uml2.uml.Interface).toList) {
			assertInterfaceWithNameInRootPackage(interface.name)
		}
		for (enum : model.packagedElements.filter(org.eclipse.uml2.uml.Enumeration).toList) {
			assertEnumWithNameInRootPackage(enum.name)
		}
		resourceSet.resources.forEach[unload()]
		resourceSet.resources.clear()
	}

	static class BidirectionalTest extends UmlConstructionSimulationTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}

}
