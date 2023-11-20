package joana2confidentiality4cbsemodelpreservation.tests.joana2c4c;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import tools.mdsd.jamopp.model.java.members.MembersFactory;
import tools.mdsd.jamopp.model.java.members.Method;
import tools.mdsd.jamopp.model.java.parameters.OrdinaryParameter;
import tools.mdsd.jamopp.model.java.parameters.ParametersFactory;
import tools.mdsd.jamopp.model.java.members.InterfaceMethod;
import org.junit.jupiter.api.Test;

import edu.kit.ipd.sdq.metamodels.joana.JoanaFactory;
import edu.kit.ipd.sdq.metamodels.joana.SecurityLevel;
import edu.kit.ipd.sdq.metamodels.joana.Sink;
import edu.kit.ipd.sdq.metamodels.joana.Annotation;
import tools.vitruv.framework.views.CommittableView;
import org.eclipse.emf.ecore.util.EcoreUtil;



public class SinkTest extends Joana2c4cTestSetup {
	
	private static final String LEVEL_HIGH = "HighLevel";
	private static final String MY_PARAMETER = "MyParameter";
	private static final String MY_METHOD = "MyMethod";
	
	@Override
	protected boolean enableTransitiveCyclicChangePropagation() {
		return true;
	}
	
	@Test
	void levelAdded() {
		CommittableView view = getCombinedJavaJoanaView();
		SecurityLevel newLevel = JoanaFactory.eINSTANCE.createSecurityLevel();
		newLevel.setName(LEVEL_HIGH);
		getJoanaRoot(view).getSecuritylevel().add(newLevel);
		getJoanaRoot(view).getAnnotation().get(0).getSecuritylevel().add(newLevel);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		assertEquals(1, getJoanaRoot(view).getAnnotation().get(0).getSecuritylevel().size());
		assertTrue(getJoanaRoot(view).getAnnotation().get(0).getSecuritylevel().get(0).getName().matches(LEVEL_HIGH));
		assertEquals(1, getC4CRoot(getC4CView()).getInformationFlows().get(0).getInformation().get(0).getDataTargets().size());
	}
	
	@Test
	void levelRemoved() {
		CommittableView view = getCombinedJavaJoanaView();
		SecurityLevel removedLevel = getJoanaRoot(view).getSecuritylevel().get(0);
		getJoanaRoot(view).getAnnotation().get(0).getSecuritylevel().remove(removedLevel);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		assertEquals(1, getJoanaRoot(view).getSecuritylevel().size());
		assertEquals(0, getJoanaRoot(view).getAnnotation().size());
		assertEquals(0, getC4CRoot(getC4CView()).getInformationFlows().get(0).getInformation().size());
	}
	
	@Test
	void methodChangedTest() {
		CommittableView view = getCombinedJavaJoanaView();
		InterfaceMethod method = MembersFactory.eINSTANCE.createInterfaceMethod();
		method.setName(MY_METHOD);
		getJavaRoot(view).getCompilationUnits().get(0).getClassifiers().get(0).getMembers().add(method);
		view.commitChanges();
		method = (InterfaceMethod) getJavaRoot(view).getCompilationUnits().get(0).getClassifiers().get(0).getMethods().get(1);
		assertTrue(method.getName().matches(MY_METHOD));
		assertEquals(2, getJoanaRoot(view).getAnnotation().size());
		assertEquals(1, getC4CRoot(getC4CView()).getInformationFlows().get(0).getInformation().size());
		for(Annotation anno : getJoanaRoot(view).getAnnotation()) {
			if(anno instanceof Sink) {
				
				anno.setAnnotatedMethod(method);
			}
		}
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		assertEquals(2, getJoanaRoot(view).getAnnotation().size());
		assertTrue(getJoanaRoot(view).getAnnotation().get(0).getAnnotatedMethod().getName().matches(MY_METHOD));
		assertTrue(getJoanaRoot(view).getAnnotation().get(1).getAnnotatedMethod().getName().matches(MY_METHOD));
		assertEquals(0, getC4CRoot(getC4CView()).getInformationFlows().iterator().next().getInformation().size());
		assertEquals(1, getC4CRoot(getC4CView()).getInformationFlows().get(1).getInformation().size());
	}
	
	@Test
	void parameterChangedTest() {
		CommittableView view = getCombinedJavaJoanaView();
		OrdinaryParameter secondPara = ParametersFactory.eINSTANCE.createOrdinaryParameter();
		secondPara.setName(MY_PARAMETER);
		((InterfaceMethod) getJavaRoot(view).getCompilationUnits().get(0).getClassifiers().get(0).getMembers().get(0)).getParameters().add(secondPara);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		secondPara = (OrdinaryParameter) ((InterfaceMethod) getJavaRoot(view).getCompilationUnits().get(0).getClassifiers().get(0).getMethods().get(0)).getParameters().get(1);
		for(Annotation anno : getJoanaRoot(view).getAnnotation()) {
			if(anno instanceof Sink) {
				
				anno.setAnnotatedParameter((OrdinaryParameter) secondPara);
			}
		}
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		assertEquals(2, getJoanaRoot(view).getAnnotation().size());
		assertTrue(getJoanaRoot(view).getAnnotation().get(0).getAnnotatedParameter().getName().matches(MY_PARAMETER));
		assertTrue(getJoanaRoot(view).getAnnotation().get(1).getAnnotatedParameter().getName().matches(MY_PARAMETER));
		assertEquals(1, getC4CRoot(getC4CView()).getInformationFlows().iterator().next().getInformation().size());
	}
	
	@Test
	void sinkDeletionTest() {
		CommittableView view = getCombinedJavaJoanaView();
		Sink sink = (Sink) getJoanaRoot(view).getAnnotation().get(0);
		EcoreUtil.remove(sink);

		view.commitChanges();
		assertEquals(0, getJoanaRoot(getJoanaView()).getAnnotation().size());
		assertEquals(0, getC4CRoot(getC4CView()).getInformationFlows().get(0).getInformation().size());
	}
	
	@Test
	void sinkCreationTest() {
		assertEquals(2, getJoanaRoot(getJoanaView()).getAnnotation().size());
		assertEquals(1, getC4CRoot(getCombinedPCMC4CView()).getInformationFlows().size());
	}
	
	@Test
	void sinkCreationWithExistingInformationFlowTest() {
		CommittableView view = getCombinedJavaJoanaView();
		getJoanaRoot(view).getAnnotation().remove(0);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		Sink sink = JoanaFactory.eINSTANCE.createSink();
		getJoanaRoot(view).getAnnotation().add(sink);
		Method method = (Method) getJavaRoot(view).getCompilationUnits().get(0).getClassifiers().get(0).getMembers().get(0);
		sink.setAnnotatedMethod(method);
		view.commitChanges();
		assertEquals(2, getJoanaRoot(getJoanaView()).getAnnotation().size());
		assertEquals(1, getC4CRoot(getCombinedPCMC4CView()).getInformationFlows().size());
	}
	
	@Test
	void sinkTagTest() {
		CommittableView view = getCombinedJavaJoanaView();
		assertTrue(getJoanaRoot(view).getAnnotation().get(0).getTag().isEmpty());
	}

}
