package tools.vitruv.applications.c4cbse2joana.tests.c4c2joana;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.ipd.sdq.metamodels.confidentiality4cbse.Confidentiality4cbseFactory;
import edu.kit.ipd.sdq.metamodels.confidentiality4cbse.ConfidentialitySpecification;
import edu.kit.ipd.sdq.metamodels.joana.JOANARoot;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.C4C2joanaChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.JaMoPP2joanaChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.Joana2c4cChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.PCM2c4cChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.tests.util.MetamodelSetup;
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.applications.util.temporary.java.JavaSetup;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.testutils.integration.TestViewFactory;
import tools.vitruv.framework.testutils.integration.ViewBasedVitruvApplicationTest;

public class C4C2joanaTest extends ViewBasedVitruvApplicationTest {

	@BeforeAll
	public static void registerCaseStudyMetamodels() {
		MetamodelSetup.registerMetamodels();
	}
	
	protected TestViewFactory viewFactory;

	protected static final String MODEL_FOLDER_NAME = "model";

	protected static final String MODEL_FILE_EXTENSION = "confidentiality4cbse";

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
		EcorePlugin.ExtensionProcessor.process(null);
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
		JavaSetup.prepareFactories();
	}
	
	
	
	protected ConfidentialitySpecification getC4CRoot(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof ConfidentialitySpecification) return ((ConfidentialitySpecification) it);
		}
		return null;
	}
	
	protected CommittableView getC4CViewWithC4CRoot() {
		CommittableView view = getCombinedPCMC4CView();
		// only add a new root if none is already present
		if (getC4CRoot(view) != null) return view;
		return createAndCommitC4CRoot(view);
	}

	private CommittableView createAndCommitC4CRoot(CommittableView view) {
		var root = Confidentiality4cbseFactory.eINSTANCE.createConfidentialitySpecification();
		root.setEntityName("MyRoot");
		view.registerRoot(root, getUri(getProjectModelPath(MODEL_FILE_NAME, MODEL_FILE_EXTENSION, MODEL_FOLDER_NAME)));
		view.commitChanges();
		return getCombinedPCMC4CView();
	}
	
	protected CommittableView getView(String name, List<Class<?>> clazz) {
		return viewFactory.createViewOfElements(name, clazz).withChangeRecordingTrait();
	}

	protected Path getProjectModelPath(String modelName, String modelExtension, String modelFolder) {
		return Path.of(modelFolder).resolve(modelName + "." + modelExtension);
	}

	//-------------Joana-------------
	
	protected JOANARoot getJoanaRoot(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof JOANARoot) return ((JOANARoot) it);
		}
		return null;
	}
	
	//-------------Java-------------
	
	protected Package getJavaRoot(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof Package && ((Package) it).getName().equals("myRoot")) {
				return ((Package) it);
			}
		}
		return null;
	}
	
	protected List<CompilationUnit> getCompilationUnits(View view) {
		return List.copyOf(view.getRootObjects(CompilationUnit.class));
	}
	
	protected Package getJavaContracts(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof Package && ((Package) it).getName().equals("contracts")) {
				return ((Package) it);
			}
		}
		return null;
	}
	
	//------------PCM------------------
	
	protected Repository getPCMRoot(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof Repository) return ((Repository) it);
		}
		return null;
	}
	
	protected CommittableView getPCMViewWithPCMRoot() {
		CommittableView view = getCombinedPCMC4CView();
		// only add a new root if none is already present
		if (view.getRootObjects().size() == 1) return view;
		createAndCommitPCMRoot(view);
		return getCombinedPCMC4CView();
	}

	private void createAndCommitPCMRoot(CommittableView view) {
		var root = RepositoryFactory.eINSTANCE.createRepository();
		root.setEntityName("MyRoot");
		view.registerRoot(root, getUri(getProjectModelPath(MODEL_FILE_NAME, "repository", MODEL_FOLDER_NAME)));
		view.commitChanges();
	}
	
	
	
	
	protected CommittableView getCombinedJavaJoanaView() {
		return getView("combined", List.of(JOANARoot.class, Package.class, CompilationUnit.class));
	}
	
	protected CommittableView getCombinedPCMC4CView() {
		return getView("combined", List.of(ConfidentialitySpecification.class, Repository.class));
	}
}
