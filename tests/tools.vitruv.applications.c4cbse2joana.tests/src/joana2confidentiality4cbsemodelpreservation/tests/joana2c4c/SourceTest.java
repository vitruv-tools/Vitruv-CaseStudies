package joana2confidentiality4cbsemodelpreservation.tests.joana2c4c;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.ParametersFactory;
import org.junit.jupiter.api.Test;

import edu.kit.kastel.scbs.pcm2java4joana.joana.Annotation;
import edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaFactory;
import edu.kit.kastel.scbs.pcm2java4joana.joana.SecurityLevel;
import edu.kit.kastel.scbs.pcm2java4joana.joana.Source;
import tools.vitruv.framework.views.CommittableView;

public class SourceTest extends Joana2c4cTestSetup{
	
	private static final String MY_PARAMETER = "MyParameter";
	private static final String LEVEL_HIGH = "HighLevel";
	private static final String TEST_METHOD = "testJava";
	private static final String MY_METHOD = "MyMethod";
	private static final String MY_INTERFACE = "TestInterface";
	
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
		getJoanaRoot(view).getAnnotation().get(1).getSecuritylevel().add(newLevel);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		assertEquals(1, getJoanaRoot(view).getAnnotation().get(0).getSecuritylevel().size());
		assertEquals(1, getC4CRoot(getC4CView()).getInformationFlows().get(0).getInformation().get(0).getDataTargets().size());
		assertTrue(getC4CRoot(getC4CView()).getInformationFlows().get(0).getInformation().get(0).getDataTargets().get(0).getEntityName().matches(LEVEL_HIGH));
	}
	
	@Test
	void levelRemoved() {
		CommittableView view = getCombinedJavaJoanaView();
		SecurityLevel removedLevel = getJoanaRoot(view).getSecuritylevel().get(0);
		getJoanaRoot(view).getAnnotation().get(1).getSecuritylevel().remove(removedLevel);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		assertEquals(1, getJoanaRoot(view).getSecuritylevel().size());
		assertEquals(0, getJoanaRoot(view).getAnnotation().size());
		assertEquals(0, getC4CRoot(getC4CView()).getInformationFlows().get(0).getInformation().size());
	}
	
	@Test
	void methodChanged() {
		CommittableView view = getCombinedJavaJoanaView();
		InterfaceMethod method = MembersFactory.eINSTANCE.createInterfaceMethod();
		method.setName(MY_METHOD);
		getJavaRoot(view).getCompilationUnits().get(0).getClassifiers().get(0).getMembers().add(method);
		view.commitChanges();
		method = (InterfaceMethod) getJavaRoot(view).getCompilationUnits().get(0).getClassifiers().get(0).getMethods().get(1);
		assertTrue(method.getName().matches(MY_METHOD));
		assertEquals(2, getJoanaRoot(view).getAnnotation().size());
		for(Annotation anno : getJoanaRoot(view).getAnnotation()) {
			if(anno instanceof Source) {
				
				anno.setAnnotatedMethod(method);
			}
		}
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		assertEquals(2, getJoanaRoot(view).getAnnotation().size());
		assertTrue(getJoanaRoot(view).getAnnotation().get(0).getAnnotatedMethod().getName().matches(MY_METHOD));
		assertTrue(getJoanaRoot(view).getAnnotation().get(1).getAnnotatedMethod().getName().matches(MY_METHOD));
		assertEquals(1, getC4CRoot(getC4CView()).getInformationFlows().get(1).getInformation().size());
	}
	
	@Test
	void parameterChanged() {
		CommittableView view = getCombinedJavaJoanaView();
		OrdinaryParameter secondPara = ParametersFactory.eINSTANCE.createOrdinaryParameter();
		secondPara.setName(MY_PARAMETER);
		((InterfaceMethod) getJavaRoot(view).getCompilationUnits().get(0).getClassifiers().get(0).getMembers().get(0)).getParameters().add(secondPara);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		secondPara = (OrdinaryParameter) ((InterfaceMethod) getJavaRoot(view).getCompilationUnits().get(0).getClassifiers().get(0).getMethods().get(0)).getParameters().get(1);
		for(Annotation anno : getJoanaRoot(view).getAnnotation()) {
			if(anno instanceof Source) {
				
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
	void sourceDeletionTest() {
		CommittableView view = getCombinedJavaJoanaView();
		
		getJoanaRoot(view).getAnnotation().remove(1);
		view.commitChanges();
		assertEquals(0, getJoanaRoot(getJoanaView()).getAnnotation().size());
		assertEquals(0, getC4CRoot(getC4CView()).getInformationFlows().get(0).getInformation().size());
	}
	
	@Test
	void sourceCreationTest() {
		addNewSource();

		assertEquals(4, getJoanaRoot(getJoanaView()).getAnnotation().size());
		assertEquals(2, getC4CRoot(getC4CView()).getInformationFlows().get(0).getInformation().size());
	}
	
	@Test
	void sourceTagTest() {
		CommittableView view = getCombinedJavaJoanaView();
		assertTrue(getJoanaRoot(view).getAnnotation().get(1).getTag().get(0).matches(MY_INTERFACE + "." + TEST_METHOD));
	}

}
