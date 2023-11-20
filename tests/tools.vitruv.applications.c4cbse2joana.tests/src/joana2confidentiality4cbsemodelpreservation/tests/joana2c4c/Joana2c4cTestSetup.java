package joana2confidentiality4cbsemodelpreservation.tests.joana2c4c;

import static org.junit.Assert.assertEquals;
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
import tools.mdsd.jamopp.model.java.parameters.OrdinaryParameter;
import tools.mdsd.jamopp.model.java.parameters.Parameter;
import tools.mdsd.jamopp.model.java.parameters.ParametersFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.ipd.sdq.metamodels.joana.EntryPoint;
import edu.kit.ipd.sdq.metamodels.joana.FlowSpecification;
import edu.kit.ipd.sdq.metamodels.joana.JoanaFactory;
import edu.kit.ipd.sdq.metamodels.joana.SecurityLevel;
import edu.kit.ipd.sdq.metamodels.joana.Sink;
import edu.kit.ipd.sdq.metamodels.joana.Source;
import tools.vitruv.framework.views.CommittableView;

public class Joana2c4cTestSetup extends Joana2c4cTest {
	
	private static final String MY_SINK = "MySink";
	private static final String LEVEL_LOW = "LowLevel";
	private static final String MY_SOURCE = "MySource";
	private static final String TEST_PARAMETER = "TestParameter";
	private static final String MY_PARAMETER = "MyParameter";
	private static final String MY_INTERFACE = "TestInterface";
	private static final String MY_ROOT = "MyRoot";
	private static final String MY_METHOD = "testJava";
	
	@Test
	public void testCreateJoanaRoot() {
		CommittableView view = getJoanaViewWithJOANARoot();
		assertEquals(1, getJoanaView().getRootObjects().size());
		//The EcoreUtil Copier prints a second PCM Root here, but it doesn´t cause problems.
		assertEquals(2, getC4CView().getRootObjects().size());
	}
	
	@BeforeEach
	void setupJava() {
		Package javaRoot = ContainersFactory.eINSTANCE.createPackage();
		javaRoot.setName(MY_ROOT);
		CommittableView javaView = getCombinedJavaJoanaView();
		javaView.registerRoot(javaRoot, getUri(getProjectModelPath("javaRoot", "jamopp", "model/")));
		JavaClasspath.get().registerClassifier(javaRoot.getName(), "", getUri(getProjectModelPath("javaRoot", "jamopp", "model/")));
		javaView.commitChanges();
//
		javaView = getCombinedJavaJoanaView();
		CompilationUnit compUnit = ContainersFactory.eINSTANCE.createCompilationUnit();
		compUnit.setName("TestJava.java");
		compUnit.getNamespaces().add(MY_ROOT);
		getJavaRoot(javaView).getCompilationUnits().add(compUnit);
		getUserInteraction().addNextSingleSelection(0);
		javaView.commitChanges();
		
		javaView = getCombinedJavaJoanaView();
		compUnit = getJavaRoot(javaView).getCompilationUnits().get(0);
		Interface javaInterface = ClassifiersFactory.eINSTANCE.createInterface();
		javaInterface.setName(MY_INTERFACE);
		compUnit.getClassifiers().add(javaInterface);
		javaView.commitChanges();
		
		javaView = getCombinedJavaJoanaView();
		compUnit = getJavaRoot(javaView).getCompilationUnits().get(0);
		javaInterface = (Interface) compUnit.getClassifiers().get(0);
		InterfaceMethod method = MembersFactory.eINSTANCE.createInterfaceMethod();
		method.setName(MY_METHOD);
		javaInterface.getMembers().add(method);
		javaView.commitChanges();
		
		javaView = getCombinedJavaJoanaView();
		compUnit = getJavaRoot(javaView).getCompilationUnits().get(0);
		javaInterface = (Interface) compUnit.getClassifiers().get(0);
		method = (InterfaceMethod) javaInterface.getMembers().get(0);
		Parameter para = ParametersFactory.eINSTANCE.createOrdinaryParameter();
		para.setName(TEST_PARAMETER);
		method.getParameters().add(para);
		javaView.commitChanges();
		
		assertEquals(1, getJoanaView().getRootObjects().size());
		setupJoana();
	}
	
	void setupJoana() {
		CommittableView view = getCombinedJavaJoanaView();
		
		Interface javaInterface = (Interface) getJavaRoot(view).getCompilationUnits().iterator().next().getClassifiers().iterator().next();
		Method method = (InterfaceMethod) javaInterface.getMethods().iterator().next();
		OrdinaryParameter para = (OrdinaryParameter) method.getParameters().iterator().next();
		SecurityLevel level = JoanaFactory.eINSTANCE.createSecurityLevel();
		level.setName(LEVEL_LOW);
		getJoanaRoot(view).getSecuritylevel().add(level);
		getJoanaRoot(view).setLattice(JoanaFactory.eINSTANCE.createLattice());
		Sink sink = JoanaFactory.eINSTANCE.createSink();
		sink.setName(MY_SINK);
		sink.setAnnotatedMethod(method);
		sink.setAnnotatedParameter(para);
		sink.getSecuritylevel().add(level);
		getJoanaRoot(view).getAnnotation().add(sink);
		view.commitChanges();
	}
	
	void addEntryPoint() {
		CommittableView view = getCombinedJavaJoanaView();
		InterfaceMethod method = MembersFactory.eINSTANCE.createInterfaceMethod();
		method.setName("secondMethod");
		getJavaRoot(view).getCompilationUnits().get(0).getClassifiers().get(0).getMembers().add(method);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		method = (InterfaceMethod) getJavaRoot(view).getCompilationUnits().get(0).getClassifiers().get(0).getMembers().get(1);
		EntryPoint point = JoanaFactory.eINSTANCE.createEntryPoint();
		FlowSpecification flow = JoanaFactory.eINSTANCE.createFlowSpecification();
		flow.setEntrypoint(point);
		getJoanaRoot(view).getFlowspecification().add(flow);
		point.setAnnotatedMethod(method);
		view.commitChanges();
		assertEquals(2, getJavaRoot(getJavaView()).getCompilationUnits().get(0).getClassifiers().get(0).getMembers().size());
	}
	
	void addNewSource() {
		CommittableView view = getCombinedJavaJoanaView();
		Interface javaInterface = (Interface) getJavaRoot(view).getCompilationUnits().iterator().next().getClassifiers().iterator().next();
		Method method = (InterfaceMethod) javaInterface.getMethods().iterator().next();
		Parameter para = ParametersFactory.eINSTANCE.createOrdinaryParameter();
		para.setName(MY_PARAMETER);
		method.getParameters().add(para);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		javaInterface = (Interface) getJavaRoot(view).getCompilationUnits().iterator().next().getClassifiers().iterator().next();
		method = javaInterface.getMethods().get(0);
		para = method.getParameters().get(1);
		Source source = JoanaFactory.eINSTANCE.createSource();
		source.setName(MY_SOURCE);
		source.getTag().add(method.getName());
		source.getSecuritylevel().add(getJoanaRoot(view).getSecuritylevel().get(0));
		source.setAnnotatedMethod(method);
		source.setAnnotatedParameter(para);
		getJoanaRoot(view).getAnnotation().add(source);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
	}

}
