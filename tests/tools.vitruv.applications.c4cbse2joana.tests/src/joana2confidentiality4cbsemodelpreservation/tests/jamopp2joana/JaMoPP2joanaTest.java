package joana2confidentiality4cbsemodelpreservation.tests.jamopp2joana;

import java.nio.file.Path;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import tools.mdsd.jamopp.model.java.containers.CompilationUnit;
import tools.mdsd.jamopp.model.java.containers.ContainersFactory;
import tools.mdsd.jamopp.resource.JavaResource2Factory;
import tools.mdsd.jamopp.model.java.JavaPackage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import edu.kit.ipd.sdq.metamodels.joana.JOANARoot;
import tools.vitruv.applications.joana2c4cbsemodelpreservation.changepropagationspecification.C4C2joanaChangePropagationSpecification;
import tools.vitruv.applications.joana2c4cbsemodelpreservation.changepropagationspecification.JaMoPP2joanaChangePropagationSpecification;
import tools.vitruv.applications.joana2c4cbsemodelpreservation.changepropagationspecification.Joana2c4cChangePropagationSpecification;
import tools.vitruv.applications.joana2c4cbsemodelpreservation.changepropagationspecification.PCM2c4cChangePropagationSpecification;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.testutils.TestViewFactory;
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest;
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper;
import tools.vitruv.applications.util.temporary.java.JavaSetup;

import tools.mdsd.jamopp.model.java.containers.Package;

public class JaMoPP2joanaTest extends ViewBasedVitruvApplicationTest {
	
	TestViewFactory viewFactory;

	@Accessors(AccessorType.PROTECTED_GETTER)
	private static String MODEL_FOLDER_NAME = "model";

	@Accessors(AccessorType.PROTECTED_GETTER)
	private static String MODEL_FILE_EXTENSION = "java";

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
	
	@BeforeAll
	public static void setupJavaFactories() {
		JavaSetup.prepareFactories();
	}
	
	@BeforeAll
	public static void registerMetamodels() {
		EPackage.Registry.INSTANCE.put(JavaPackage.eNS_URI, JavaPackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(MODEL_FILE_EXTENSION, new JavaResource2Factory());
	}
	
	@BeforeEach
	public final void setupJavaClasspath() {
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
	}

	@BeforeEach
	protected void setup() {
		viewFactory = new TestViewFactory(getVirtualModel());
	}
	
	@Override
	protected boolean enableTransitiveCyclicChangePropagation() {
		return false;
	}
	
	protected CommittableView getJavaView() {
		return getView("java", List.of(Package.class, CompilationUnit.class));
	}
	
	protected Package getJavaRoot(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof Package) return ((Package) it);
		}
		return null;
	}
	
	protected CommittableView getJavaViewWithJavaRoot() {
		CommittableView view = getJavaView();
		return createAndCommitJavaRoot(view);
	}

	private CommittableView createAndCommitJavaRoot(CommittableView view) {
		var root = ContainersFactory.eINSTANCE.createPackage();
		
		root.setName("MyRoot");
		var path = JavaPersistenceHelper.buildJavaFilePath(root);
		view.registerRoot(root, getUri(Path.of(path)));
		view.commitChanges();
		return view;
	}
	
	protected CommittableView getView(String name, List<Class<?>> clazz) {
		return viewFactory.createViewOfElements(name, clazz).withChangeRecordingTrait();
	}


	protected Path getProjectModelPath(String modelName, String modelExtension, String modelFolder) {
		return Path.of(modelFolder).resolve(modelName + "." + modelExtension);
	}

	//-------------Joana-------------
	
	protected CommittableView getJoanaView() {
		return getView("joana", List.of(JOANARoot.class));
	}
	
	protected JOANARoot getJoanaRoot(View view) {
		for(var it :view.getRootObjects()) {
			if(it instanceof JOANARoot) return ((JOANARoot) it);
		}
		return null;
	}
	
	protected CommittableView getCombinedJavaJoanaView() {
		return getView("combined", List.of(JOANARoot.class, Package.class));
	}
	
	protected List<CompilationUnit> getCompilationUnits(Package package1) {
		return package1.getClassifiers().stream().map(it -> it.getContainingCompilationUnit()).toList();
	}

}
