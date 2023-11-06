package joana2confidentiality4cbsemodelpreservation.tests.joana2c4c;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Test;

import tools.vitruv.framework.views.CommittableView;

public class EntryPointTest extends Joana2c4cTestSetup {
		
	private static final String TEST_METHOD = "testJava";
	private static final String MY_INTERFACE = "TestInterface";
	
	@Override
	protected boolean enableTransitiveCyclicChangePropagation() {
		return true;
	}
	
	@Test
	void annoToFlowSpecTest() {
		CommittableView view = getCombinedJavaJoanaView();
		assertEquals(1, getJoanaRoot(view).getFlowspecification().get(0).getAnnotation().size());
	}

	@Test
	void entryPointCreationTest() {
		addEntryPoint();
		CommittableView view = getCombinedJavaJoanaView();
		assertEquals(2, getJavaRoot(getJavaView()).getCompilationUnits().get(0).getClassifiers().get(0).getMembers().size());
		assertEquals(2, getJoanaRoot(getJoanaView()).getAnnotation().size());
		assertEquals(2, getJoanaRoot(view).getFlowspecification().size());
		assertEquals(2, getC4CRoot(getC4CView()).getInformationFlows().size());
	}
	
	@Test
	void entryPointDeletionTest() {
		CommittableView view = getCombinedJavaJoanaView();
		EcoreUtil.remove(getJoanaRoot(view).getFlowspecification().get(0).getEntrypoint());
		view.commitChanges();
		assertNull(getJoanaRoot(getJoanaView()).getFlowspecification().get(0).getEntrypoint());
		assertEquals(0, getJoanaRoot(getJoanaView()).getAnnotation().size());
	}
	
	@Test
	void entryPointTagTest() {
		CommittableView view = getCombinedJavaJoanaView();
		assertTrue(getJoanaRoot(view).getFlowspecification().get(0).getEntrypoint().getTag().get(0).matches(MY_INTERFACE + "." + TEST_METHOD));
	}

}
