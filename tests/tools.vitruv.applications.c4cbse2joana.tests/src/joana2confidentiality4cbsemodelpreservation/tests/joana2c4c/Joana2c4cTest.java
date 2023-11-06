package joana2confidentiality4cbsemodelpreservation.tests.joana2c4c;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.util.RepositoryResourceFactoryImpl;

import edu.kit.kastel.scbs.confidentiality.ConfidentialityPackage;
import edu.kit.kastel.scbs.confidentiality.ConfidentialitySpecification;
import edu.kit.kastel.scbs.pcm2java4joana.joana.JOANARoot;
import edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaFactory;
import edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage;
import joana2c4cbsemodelpreservation.changepropagationspecification.C4C2joanaChangePropagationSpecification;
import joana2c4cbsemodelpreservation.changepropagationspecification.JaMoPP2joanaChangePropagationSpecification;
import joana2c4cbsemodelpreservation.changepropagationspecification.Joana2c4cChangePropagationSpecification;
import joana2c4cbsemodelpreservation.changepropagationspecification.PCM2c4cChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.applications.util.temporary.java.JavaSetup;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.testutils.TestViewFactory;
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest;

import org.emftext.language.java.JavaPackage;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.resource.java.mopp.JavaResourceFactory;

public class Joana2c4cTest extends ViewBasedVitruvApplicationTest {

	protected TestViewFactory viewFactory;

	@Accessors(AccessorType.PROTECTED_GETTER)
	private static String MODEL_FOLDER_NAME = "model";

	@Accessors(AccessorType.PROTECTED_GETTER)
	private static String MODEL_FILE_EXTENSION = "joana";

	@Accessors(AccessorType.PROTECTED_GETTER)
	private static String MODEL_FILE_NAME = "test";
	
	@Override
	protected Iterable<? extends ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(new Java2PcmChangePropagationSpecification(), 
				new Joana2c4cChangePropagationSpecification(), 
				new C4C2joanaChangePropagationSpecification(), 
				new Pcm2JavaChangePropagationSpecification(),  
				new PCM2c4cChangePropagationSpecification(), 
				new JaMoPP2joanaChangePropagationSpecification());
	}
	
	@Override
	protected boolean enableTransitiveCyclicChangePropagation() {
		return true;
	}
	
	@BeforeAll
	public static void setupJavaFactories() {
		JavaSetup.prepareFactories();
	}
	
	@BeforeAll
	public static void registerMetamodels() {
		EPackage.Registry.INSTANCE.put(JavaPackage.eNS_URI, JavaPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(ConfidentialityPackage.eNS_URI, ConfidentialityPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(JoanaPackage.eNS_URI, JoanaPackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("java", new JavaResourceFactory());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("repository", new RepositoryResourceFactoryImpl());

	}
	
	@BeforeEach
	public final void setupJavaClasspath() {
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
	}

	@BeforeEach
	protected void setup() {
		viewFactory = new TestViewFactory(getVirtualModel());
	}

	/**
	 * Create a JOANARoot element and add it to the virtual model.
	 */
	protected CommittableView getJoanaViewWithJOANARoot() {
		CommittableView view = getJoanaView();
		// only add a new root if none is already present
		if (view.getRootObjects().size() == 1) return view;
		return createAndCommitJoanaRoot(view);
	}
	
//	protected CommittableView getCombinedView

	private CommittableView createAndCommitJoanaRoot(CommittableView view) {
		var root = JoanaFactory.eINSTANCE.createJOANARoot();
		root.setName("MyRoot");
		view.registerRoot(root, getUri(getProjectModelPath(MODEL_FILE_NAME, MODEL_FILE_EXTENSION, MODEL_FOLDER_NAME)));
		view.commitChanges();
		return getJoanaView();
	}

	protected CommittableView getJoanaView() {
		return getView("joana", List.of(JOANARoot.class));
	}
	
	protected JOANARoot claimJoanaRoot() {
		return claimJoanaRoot(getJoanaView());
	}

	protected JOANARoot claimJoanaRoot(CommittableView view) {
		if (view.getRootObjects().size() != 1)
			throw new IllegalArgumentException(
					"Given view has " + view.getRootObjects().size() + " elements instead of 1 JOANARoot!");
		return (JOANARoot) view.getRootObjects().iterator().next();
	}

	protected CommittableView getView(String name, List<Class<?>> clazz) {
		return viewFactory.createViewOfElements(name, clazz).withChangeRecordingTrait();
	}

	protected Path getProjectModelPath(String modelName, String modelExtension, String modelFolder) {
		return Path.of(modelFolder).resolve(modelName + "." + modelExtension);
	}

	protected JOANARoot getJoanaRoot(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof JOANARoot) return ((JOANARoot) it);
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
	
	//------------Java---------------------
	
	protected CommittableView getJavaView() {
		return getView("java", List.of(Package.class));
	}
	
	protected Package getJavaRoot(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof Package && ((Package) it).getName().equals("MyRoot")) {
				return ((Package) it);
			}
		}
		return null;
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
	
	protected CommittableView getPCMView() {
		return getView("pcm", List.of(Repository.class));
	}
	
	protected Repository getPCMRoot(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof Repository) return ((Repository) it);
		}
		return null;
	}
	
	protected CommittableView getCombinedJavaJoanaView() {
		return getView("combined", List.of(JOANARoot.class, Package.class));
	}
	
	protected CommittableView getCombinedPCMC4CView() {
		return getView("combined", List.of(ConfidentialitySpecification.class, Repository.class));
	}

}
