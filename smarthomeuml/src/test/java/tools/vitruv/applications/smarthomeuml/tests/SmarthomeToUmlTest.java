package tools.vitruv.applications.smarthomeuml.tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.exists;
import static tools.vitruv.change.testutils.views.ChangePublishingTestView.createDefaultChangePublishingTestView;

import edu.kit.ipd.sdq.metamodels.smarthome.SmartHomeSystem;
import edu.kit.ipd.sdq.metamodels.smarthome.SmarthomeFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.applications.smarthomeuml.SmarthomeToUmlChangePropagationSpecification;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.change.testutils.views.TestView;

@ExtendWith({TestLogging.class, TestProjectManager.class, RegisterMetamodelsInStandalone.class})
public class SmarthomeToUmlTest {
	private static final Path SMARTHOME_MODEL = Path.of("model/home.smarthome");
	private static final Path UML_MODEL = Path.of("model/home.smarthome.uml");

	private TestView testView;

	@BeforeEach
	public void prepare(@TestProject Path testProjectPath) throws IOException {
		testView = createDefaultChangePublishingTestView(testProjectPath,
				List.of(new SmarthomeToUmlChangePropagationSpecification()));
	}

	@AfterEach
	public void cleanup() throws Exception {
		testView.close();
	}

	private void createSmartHomeSystem() {
		testView.propagate(testView.resourceAt(SMARTHOME_MODEL),
				resource -> resource.getContents().add(SmarthomeFactory.eINSTANCE.createSmartHomeSystem()));
	}

	private Model getUmlModel() {
		Resource umlModel = testView.resourceAt(UML_MODEL);
		assertThat(umlModel, exists());
		assertEquals(1, umlModel.getContents().size());
		assertThat(umlModel.getContents().get(0), instanceOf(Model.class));
		return (Model) umlModel.getContents().get(0);
	}

	@Test
	public void testCreateSmartHomeSystem() {
		createSmartHomeSystem();
		Model model = getUmlModel();
		assertEquals(2, model.getPackagedElements().size());
		model.getPackagedElements().forEach(element -> assertThat(element, instanceOf(Interface.class)));
	}

	@Test
	public void testInsertDeviceAndServer() {
		createSmartHomeSystem();
		testView.propagate(testView.from(SmartHomeSystem.class, SMARTHOME_MODEL), system -> {
			system.getComponents().add(SmarthomeFactory.eINSTANCE.createDevice());
			system.getComponents().add(SmarthomeFactory.eINSTANCE.createServer());
		});
		Model model = getUmlModel();
		assertEquals(4, model.getPackagedElements().size());
		assertEquals(2, model.getPackagedElements().stream()
				.filter(element -> element instanceof org.eclipse.uml2.uml.Class).count());
	}
}
