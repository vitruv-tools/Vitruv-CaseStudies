package tools.vitruv.applications.c4cbse2joana.tests.jamopp2joana;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emftext.language.java.JavaClasspath;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.ParametersFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.ipd.sdq.metamodels.joana.EntryPoint;
import edu.kit.ipd.sdq.metamodels.joana.FlowSpecification;
import edu.kit.ipd.sdq.metamodels.joana.JOANARoot;
import edu.kit.ipd.sdq.metamodels.joana.JoanaFactory;
import edu.kit.ipd.sdq.metamodels.joana.Sink;
import edu.kit.ipd.sdq.metamodels.joana.Source;
import tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper;
import tools.vitruv.framework.views.CommittableView;

public class JaMoPP2joanaTestSetup extends JaMoPP2joanaTest {
	
	@Test
	public void testCreateJavaRootPackage() {
		assertEquals(1, getJoanaView().getRootObjects().size());
		assertEquals(3, getJavaView().getRootObjects(Package.class).size());
	}
	
	@BeforeEach
	void setupJava() {
		Package javaRoot = ContainersFactory.eINSTANCE.createPackage();
		javaRoot.setName("MyRoot");
		CommittableView javaView = getJavaView();
		javaView.registerRoot(javaRoot, getUri(getProjectModelPath("javaRoot", "jamopp", "model/")));
		JavaClasspath.get().registerClassifier(javaRoot.getName(), "", getUri(getProjectModelPath("javaRoot", "jamopp", "model/")));
		CompilationUnit compUnit = ContainersFactory.eINSTANCE.createCompilationUnit();
		compUnit.setName("TestJava.java");
		compUnit.getNamespaces().add("MyRoot");
		javaView.registerRoot(compUnit, getUri(Path.of(JavaPersistenceHelper.buildJavaFilePath(compUnit))));
		getUserInteraction().addNextSingleSelection(0);
		InterfaceMethod method = MembersFactory.eINSTANCE.createInterfaceMethod();
		method.setName("testJava");
		Interface javaInterface = ClassifiersFactory.eINSTANCE.createInterface();
		javaInterface.setName("testInterface");
		compUnit.getClassifiers().add(javaInterface);
		javaInterface.getMembers().add(method);
		Parameter para = ParametersFactory.eINSTANCE.createOrdinaryParameter();
		para.setName("TestParameter");
		getCompilationUnits(javaView).iterator().next().getClassifiers().iterator().next().getMethods().iterator().next().getParameters().add(para);
		javaView.commitChanges();
		setupJoana();
	}

	void setupJoana() {
		CommittableView joanaView = getCombinedJavaJoanaView();
		Sink sink = JoanaFactory.eINSTANCE.createSink();
		Source source = JoanaFactory.eINSTANCE.createSource();
		EntryPoint point = JoanaFactory.eINSTANCE.createEntryPoint();
		FlowSpecification flow = JoanaFactory.eINSTANCE.createFlowSpecification();
		flow.setEntrypoint(point);
		Method method = getCompilationUnits(getJavaView()).iterator().next().getClassifiers().iterator().next().getMethods().iterator().next();
		sink.setAnnotatedMethod(method);
		source.setAnnotatedMethod(method);
		point.setAnnotatedMethod(method);
		
		JOANARoot root = getJoanaRoot(joanaView);
		root.getFlowspecification().add(flow);
		root.getAnnotation().add(source);
		root.getAnnotation().add(sink);
		joanaView.commitChanges();
	}
	
	

}
