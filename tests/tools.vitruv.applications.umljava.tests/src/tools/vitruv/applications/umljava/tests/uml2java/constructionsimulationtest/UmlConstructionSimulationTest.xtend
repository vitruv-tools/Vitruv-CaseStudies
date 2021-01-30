package tools.vitruv.applications.umljava.tests.uml2java.constructionsimulationtest

import org.junit.jupiter.api.Test
import org.eclipse.emf.common.util.URI
import tools.vitruv.applications.umljava.tests.uml2java.UmlToJavaTransformationTest
import org.junit.jupiter.api.Disabled
import org.eclipse.emf.ecore.EObject
import java.nio.file.Path
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

class UmlConstructionSimulationTest extends UmlToJavaTransformationTest {

	@Test
	def void testModel1() {
		// selections are needed when collection data types are created in java
		// due to 0..* associations. The user chooses a collection datatype.
		testUmlModel("resources/model1.uml")
	// Check manually in the JUnit-workspace.
	}

	/**
	 * Tests the uml model from "myproject" by suresh519
	 * https://repository.genmymodel.com/suresh519/MyProject (12.5.2017)
	 * 
	 */
	@Test
	def void testMyProject() {
		testUmlModel("resources/MyProject.uml")
	// Check manually in the JUnit-workspace.
	}

	/** 
	 * Tests the (generated) uml model from the logger project by orhan obut
	 * https://github.com/orhanobut/logger (12.5.2017)
	 * 
	 */
	@Test
	@Disabled("Potentially exceeds Java heap space")
	def void testRootModelName() {
		testUmlModel("resources/rootModelName.uml")
	// Check manually in the JUnit-workspace.
	}

	def private void testUmlModel(String modelPath) {
		for (i : 1..2) {
			userInteraction.acknowledgeNotification [message.contains("Only predefined uml::PrimitiveTypes will be mapped")]
		}
		
		val originalResource = new ResourceSetImpl().getResource(URI.createFileURI(modelPath), true)
		resourceAt(Path.of("model/model.uml")).startRecordingChanges => [
			contents += EObject.from(originalResource)
		]
		propagate
	}
}
