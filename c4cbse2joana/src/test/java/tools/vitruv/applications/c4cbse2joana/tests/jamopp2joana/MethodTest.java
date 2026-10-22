package tools.vitruv.applications.c4cbse2joana.tests.jamopp2joana;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.parameters.Parameter;
import org.junit.jupiter.api.Test;

import tools.vitruv.framework.views.CommittableView;

public class MethodTest extends JaMoPP2joanaTestSetup {
	
	@Test
	void MethodDeletionTest() {

		assertEquals(2, getJoanaRoot(getJoanaView()).getAnnotation().size());
		assertNotNull(getJoanaRoot(getJoanaView()).getFlowspecification().get(0).getEntrypoint());
		
		CommittableView view = getCombinedJavaJoanaView();
		getCompilationUnits(view).iterator().next().getClassifiers().iterator().next().getMembers().remove(0);
		view.commitChanges();
		
		assertEquals(0, getJoanaRoot(getJoanaView()).getAnnotation().size());
		assertNull(getJoanaRoot(getJoanaView()).getFlowspecification().get(0).getEntrypoint());
	}
	
	@Test
	void ParameterDeletionTest() {
		
		CommittableView view = getCombinedJavaJoanaView();
		Parameter para = ((Interface) getCompilationUnits(view).get(0).getClassifiers().get(0)).getMethods().get(0).getParameters().get(0);
		getJoanaRoot(view).getAnnotation().get(0).setAnnotatedParameter(para);
		getJoanaRoot(view).getAnnotation().get(1).setAnnotatedParameter(para);
		view.commitChanges();
		
		assertEquals(2, getJoanaRoot(getJoanaView()).getAnnotation().size());
		assertNotNull(getJoanaRoot(getJoanaView()).getFlowspecification().get(0).getEntrypoint());
		
		view = getJavaView();
		getCompilationUnits(view).iterator().next().getClassifiers().iterator().next().getMethods().iterator().next().getParameters().remove(0);
		view.commitChanges();
		
		assertEquals(0, getJoanaRoot(getJoanaView()).getAnnotation().size());
		assertNull(getJoanaRoot(getJoanaView()).getFlowspecification().get(0).getEntrypoint());
	}

}
