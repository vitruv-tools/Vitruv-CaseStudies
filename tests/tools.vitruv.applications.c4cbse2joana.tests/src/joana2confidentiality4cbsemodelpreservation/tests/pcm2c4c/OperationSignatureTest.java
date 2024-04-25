package joana2confidentiality4cbsemodelpreservation.tests.pcm2c4c;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.framework.views.CommittableView;

public class OperationSignatureTest extends PCM2c4cTestSetup {

	@Test
	void operationSignatureDeletion() {

		CommittableView view = getCombinedPCMC4CView();
		assertEquals(1, getC4CRoot(view).getInformationFlows().size());
		OperationSignature opSig = ((OperationInterface) getPCMRoot(view).getInterfaces__Repository().iterator().next())
				.getSignatures__OperationInterface().get(0);
		EcoreUtil.delete(opSig);
		view.commitChanges();
		assertEquals(1, getC4CRoot(getCombinedPCMC4CView()).getInformationFlows().size());
	}

	@Test
	void parameterDeletion() {

		CommittableView view = getCombinedPCMC4CView();
		assertEquals(1, getC4CRoot(view).getInformationFlows().get(0).getInformation().size());
		EcoreUtil.delete(((OperationInterface) getPCMRoot(view).getInterfaces__Repository().iterator().next())
				.getSignatures__OperationInterface().get(0).getParameters__OperationSignature().get(0));
		view.commitChanges();
		assertEquals(1, getC4CRoot(getCombinedPCMC4CView()).getInformationFlows().get(0).getInformation().size());
	}

}
