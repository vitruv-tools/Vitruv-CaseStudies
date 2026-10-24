package tools.vitruv.applications.c4cbse2joana.tests.joana2c4c;

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
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.ParametersFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.ipd.sdq.metamodels.joana.EntryPoint;
import edu.kit.ipd.sdq.metamodels.joana.FlowSpecification;
import edu.kit.ipd.sdq.metamodels.joana.JoanaFactory;
import edu.kit.ipd.sdq.metamodels.joana.SecurityLevel;
import edu.kit.ipd.sdq.metamodels.joana.Sink;
import edu.kit.ipd.sdq.metamodels.joana.Source;
import tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper;
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
		assertEquals(1, getC4CView().getRootObjects().size());
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
		javaView.registerRoot(compUnit, getUri(Path.of(JavaPersistenceHelper.buildJavaFilePath(compUnit))));
		getUserInteraction().addNextSingleSelection(0);
		javaView.commitChanges();
		
		javaView = getCombinedJavaJoanaView();
		compUnit = getCompilationUnits(javaView).get(0);
		Interface javaInterface = ClassifiersFactory.eINSTANCE.createInterface();
		javaInterface.setName(MY_INTERFACE);
		compUnit.getClassifiers().add(javaInterface);
		javaView.commitChanges();
		
		javaView = getCombinedJavaJoanaView();
		compUnit = getCompilationUnits(javaView).get(0);
		javaInterface = (Interface) compUnit.getClassifiers().get(0);
		InterfaceMethod method = MembersFactory.eINSTANCE.createInterfaceMethod();
		method.setName(MY_METHOD);
		javaInterface.getMembers().add(method);
		javaView.commitChanges();
		
		javaView = getCombinedJavaJoanaView();
		compUnit = getCompilationUnits(javaView).get(0);
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
		
		Interface javaInterface = (Interface) getCompilationUnits(view).iterator().next().getClassifiers().iterator().next();
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
		getCompilationUnits(view).get(0).getClassifiers().get(0).getMembers().add(method);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		method = (InterfaceMethod) getCompilationUnits(view).get(0).getClassifiers().get(0).getMembers().get(1);
		EntryPoint point = JoanaFactory.eINSTANCE.createEntryPoint();
		FlowSpecification flow = JoanaFactory.eINSTANCE.createFlowSpecification();
		flow.setEntrypoint(point);
		getJoanaRoot(view).getFlowspecification().add(flow);
		point.setAnnotatedMethod(method);
		view.commitChanges();
		assertEquals(2, getCompilationUnits(getJavaView()).get(0).getClassifiers().get(0).getMembers().size());
	}
	
	void addNewSource() {
		CommittableView view = getCombinedJavaJoanaView();
		Interface javaInterface = (Interface) getCompilationUnits(view).iterator().next().getClassifiers().iterator().next();
		Method method = (InterfaceMethod) javaInterface.getMethods().iterator().next();
		Parameter para = ParametersFactory.eINSTANCE.createOrdinaryParameter();
		para.setName(MY_PARAMETER);
		method.getParameters().add(para);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		javaInterface = (Interface) getCompilationUnits(view).iterator().next().getClassifiers().iterator().next();
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
