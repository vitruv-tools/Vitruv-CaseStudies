package joana2confidentiality4cbsemodelpreservation.tests.c4c2joana;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;

import edu.kit.ipd.sdq.metamodels.confidentiality4cbse.repository.SignatureInformationFlow;
import tools.vitruv.framework.views.CommittableView;

public class SignatureInformationFlowTest extends C4C2joanaTestSetup {
	
	private static final String MY_OPERATION = "MyOperation";
	private static final String SECOND_OPSIG = "SecondOpSig";
	
	@Override
	protected boolean enableTransitiveCyclicChangePropagation() {
		return true;
	}
	
	@Test
	void flowCreationTest() {
		assertNotNull(getJavaContracts(getCombinedJavaJoanaView()).getCompilationUnits().get(0).getClassifiers().get(0).getMembers());
		assertNotNull(getJoanaRoot(getCombinedJavaJoanaView()).getFlowspecification().get(0).getEntrypoint());
	}
	
	@Test
	void flowDeletionTest() {
		CommittableView view = getCombinedPCMC4CView();
		SignatureInformationFlow flow = (SignatureInformationFlow) getC4CRoot(view).getInformationFlows().get(0);
		EcoreUtil.remove(flow);
		view.commitChanges();
		assertEquals(0, getJoanaRoot(getCombinedJavaJoanaView()).getFlowspecification().size());
	}
	
	@Disabled("Test is disabled because for some reason emf converts any of the three possible values (affectedEObject, newValue or oldValue to a proxy)")
	@Test
	void methodChangeTest() {
		addSecondOperationSignature();
		CommittableView view = getCombinedPCMC4CView();
		var informationflows = getC4CRoot(view).getInformationFlows();
		OperationInterface inter = (OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0);
		var interfaces = inter.getSignatures__OperationInterface();
		OperationSignature opSig = inter.getSignatures__OperationInterface().get(1);
		OperationSignature opSigOld = inter.getSignatures__OperationInterface().get(0);
		
		((SignatureInformationFlow) getC4CRoot(view).getInformationFlows().get(0)).setAppliedTo(opSig);
		
		view.commitChanges();
		assertEquals(getJoanaRoot(getCombinedJavaJoanaView()).getFlowspecification().get(0).getEntrypoint().getAnnotatedMethod().getName(), (MY_OPERATION));
	}
	
	@Test
	void informationAddedToFlowTest() {
		addSecondSignatureFlow();
		assertTrue(getJoanaRoot(getCombinedJavaJoanaView()).getFlowspecification().get(1).getEntrypoint().getAnnotatedMethod().getName().matches(SECOND_OPSIG));
	}

}
