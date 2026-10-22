package tools.vitruv.applications.c4cbse2joana.tests.pcm2c4c;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.ipd.sdq.metamodels.confidentiality4cbse.ConfidentialitySpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.C4C2joanaChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.JaMoPP2joanaChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.Joana2c4cChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.PCM2c4cChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.tests.util.MetamodelSetup;
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.testutils.integration.TestViewFactory;
import tools.vitruv.framework.testutils.integration.ViewBasedVitruvApplicationTest;

public class PCM2c4cTest extends ViewBasedVitruvApplicationTest {

	@BeforeAll
	public static void registerCaseStudyMetamodels() {
		MetamodelSetup.registerMetamodels();
	}
	
	TestViewFactory viewFactory;

	protected static final String MODEL_FOLDER_NAME = "model";

	protected static final String MODEL_FILE_EXTENSION = "pcm";

	protected static final String MODEL_FILE_NAME = "test";
	
	@Override
	protected Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(new Java2PcmChangePropagationSpecification(), 
				new Joana2c4cChangePropagationSpecification(), 
				new C4C2joanaChangePropagationSpecification(), 
				new Pcm2JavaChangePropagationSpecification(),
				new PCM2c4cChangePropagationSpecification(), 
				new JaMoPP2joanaChangePropagationSpecification());
	}

	@BeforeEach
	protected void setup() {
		viewFactory = new TestViewFactory(getVirtualModel());
	}
	
	@Test
	public void testCreatePCMRootRepository() {
		CommittableView view =getPCMViewWithPCMRoot();
		assertEquals(1, getC4CView().getRootObjects().size());
		assertEquals(1, view.getRootObjects().size());
	}
	
	protected CommittableView getPCMViewWithPCMRoot() {
		CommittableView view = getPCMView();
		// only add a new root if none is already present
		if (view.getRootObjects().size() == 1) return view;
		return createAndCommitPCMRoot(view);
	}

	private CommittableView createAndCommitPCMRoot(CommittableView view) {
		var root = RepositoryFactory.eINSTANCE.createRepository();
		root.setEntityName("MyRoot");
		view.registerRoot(root, getUri(getProjectModelPath(MODEL_FILE_NAME, MODEL_FILE_EXTENSION, MODEL_FOLDER_NAME)));
		view.commitChanges();
		return getPCMView();
	}

	protected CommittableView getPCMView() {
		return getView("PCM", List.of(Repository.class));
	}

	protected CommittableView getView(String name, List<Class<?>> clazz) {
		return viewFactory.createViewOfElements(name, clazz).withChangeRecordingTrait();
	}

	protected Path getProjectModelPath(String modelName, String modelExtension, String modelFolder) {
		return Path.of(modelFolder).resolve(modelName + "." + modelExtension);
	}

	protected Repository getPCMRoot(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof Repository) return ((Repository) it);
		}
		return null;
	}
	
	//-------------Confidentiality--------------
	
	protected CommittableView getC4CView() {
		return getView("c4c", List.of(ConfidentialitySpecification.class));
	}
	
	protected ConfidentialitySpecification getC4CRoot(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof ConfidentialitySpecification) return ((ConfidentialitySpecification) it);
		}
		return null;
	}
	
	protected CommittableView getCombinedPCMC4CView() {
		return getView("combined", List.of(ConfidentialitySpecification.class, Repository.class));
	}

}
