package joana2confidentiality4cbsemodelpreservation.tests.c4c2joana;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Parameter;
import edu.kit.ipd.sdq.metamodels.confidentiality4cbse.information.ParameterInformation;
import tools.vitruv.framework.views.CommittableView;

public class ParameterInformationTest extends C4C2joanaTestSetup{
	
	private static final String SECOND_PARAMETER = "SecondParameter";
	
	@Override
	protected boolean enableTransitiveCyclicChangePropagation() {
		return true;
	}
	
	@Test
	void dataSetAddedToInfoTest() {
		assertEquals(1, getJoanaRoot(getCombinedJavaJoanaView()).getAnnotation().get(0).getSecuritylevel().size());
	}
	
	@Test
	void dataSetRemovedFromInfoTest() {
		CommittableView view = getCombinedPCMC4CView();
		getC4CRoot(view).getInformationFlows().get(0).getInformation().get(0).getDataTargets().remove(0);
		view.commitChanges();
		assertEquals(0, getJoanaRoot(getCombinedJavaJoanaView()).getAnnotation().size());
	}
	
	@Disabled("Test is disabled because for some reason emf converts any of the three possible values (affectedEObject, newValue or oldValue to a proxy)")
	@Test
	void parameterChangeTest() {
		addSecondParameter();
		CommittableView view = getCombinedPCMC4CView();
		ParameterInformation info = (ParameterInformation) getC4CRoot(view).getInformationFlows().get(0).getInformation().get(0);
		OperationInterface inter = (OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0);
		Parameter para = inter.getSignatures__OperationInterface().get(0).getParameters__OperationSignature().get(1);
		assertTrue(para.getParameterName().matches(SECOND_PARAMETER));
		info.setAppliedTo(para);
		var affected = (ParameterInformation) getC4CRoot(view).getInformationFlows().get(0).getInformation().get(0);
		var old = ((OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0)).getSignatures__OperationInterface().get(0).getParameters__OperationSignature().get(0);
		var news = ((OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0)).getSignatures__OperationInterface().get(0).getParameters__OperationSignature().get(1);
		view.commitChanges();
		view = getCombinedJavaJoanaView();
		assertTrue(getJoanaRoot(view).getAnnotation().get(1).getAnnotatedParameter().getName().matches(SECOND_PARAMETER));
	}
	
	@Test
	void parameterInformationDeletionTest() {
		CommittableView view = getCombinedPCMC4CView();
		ParameterInformation info = (ParameterInformation) getC4CRoot(view).getInformationFlows().get(0).getInformation().get(0);
		EcoreUtil.remove(info);
		view.commitChanges();
		assertEquals(0, getJoanaRoot(getCombinedJavaJoanaView()).getAnnotation().size());
	}
	
	@Test
	void parameterInformationCreationTest() {
		assertEquals(2, getJoanaRoot(getCombinedJavaJoanaView()).getAnnotation().size());
	}
	

}
