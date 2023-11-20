package joana2confidentiality4cbsemodelpreservation.tests.jamopp2joana;

import static org.junit.jupiter.api.Assertions.assertEquals;

import tools.mdsd.jamopp.model.java.JavaClasspath;
import tools.mdsd.jamopp.model.java.classifiers.ClassifiersFactory;
import tools.mdsd.jamopp.model.java.classifiers.Interface;
import tools.mdsd.jamopp.model.java.containers.CompilationUnit;
import tools.mdsd.jamopp.model.java.containers.ContainersFactory;
import tools.mdsd.jamopp.model.java.containers.Package;
import tools.mdsd.jamopp.model.java.members.InterfaceMethod;
import tools.mdsd.jamopp.model.java.members.MembersFactory;
import tools.mdsd.jamopp.model.java.members.Method;
import tools.mdsd.jamopp.model.java.parameters.Parameter;
import tools.mdsd.jamopp.model.java.parameters.ParametersFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.ipd.sdq.metamodels.joana.EntryPoint;
import edu.kit.ipd.sdq.metamodels.joana.FlowSpecification;
import edu.kit.ipd.sdq.metamodels.joana.JOANARoot;
import edu.kit.ipd.sdq.metamodels.joana.JoanaFactory;
import edu.kit.ipd.sdq.metamodels.joana.Sink;
import edu.kit.ipd.sdq.metamodels.joana.Source;
import tools.vitruv.framework.views.CommittableView;

public class JaMoPP2joanaTestSetup extends JaMoPP2joanaTest {
	
	@Test
	public void testCreateJavaRootPackage() {
		assertEquals(1, getJoanaView().getRootObjects().size());
		assertEquals(3, getJavaView().getRootObjects().size());
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
		getJavaRoot(javaView).getCompilationUnits().add(compUnit);
		getUserInteraction().addNextSingleSelection(0);
		InterfaceMethod method = MembersFactory.eINSTANCE.createInterfaceMethod();
		method.setName("testJava");
		Interface javaInterface = ClassifiersFactory.eINSTANCE.createInterface();
		javaInterface.setName("testInterface");
		compUnit.getClassifiers().add(javaInterface);
		javaInterface.getMembers().add(method);
		Parameter para = ParametersFactory.eINSTANCE.createOrdinaryParameter();
		para.setName("TestParameter");
		getJavaRoot(javaView).getCompilationUnits().iterator().next().getClassifiers().iterator().next().getMethods().iterator().next().getParameters().add(para);
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
		Method method = getJavaRoot(getJavaView()).getCompilationUnits().iterator().next().getClassifiers().iterator().next().getMethods().iterator().next();
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
