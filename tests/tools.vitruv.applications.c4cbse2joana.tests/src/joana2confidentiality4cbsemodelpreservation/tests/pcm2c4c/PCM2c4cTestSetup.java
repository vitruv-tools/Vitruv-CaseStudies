package joana2confidentiality4cbsemodelpreservation.tests.pcm2c4c;

import org.junit.jupiter.api.BeforeEach;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.kastel.scbs.confidentiality.information.InformationFactory;
import edu.kit.kastel.scbs.confidentiality.information.ParameterInformation;
import edu.kit.kastel.scbs.confidentiality.repository.SignatureInformationFlow;
import tools.vitruv.framework.views.CommittableView;

public class PCM2c4cTestSetup extends PCM2c4cTest {
	
	@BeforeEach
	void setupPCM() {
		CommittableView view = getPCMViewWithPCMRoot();
		OperationInterface interf = RepositoryFactory.eINSTANCE.createOperationInterface();
		interf.setRepository__Interface(getPCMRoot(view));
		view.commitChanges();
		view = getCombinedPCMC4CView();
		interf = (OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0);
		OperationSignature opSig = RepositoryFactory.eINSTANCE.createOperationSignature();
		opSig.setInterface__OperationSignature(interf);
		interf.getSignatures__OperationInterface().add(opSig);
		opSig.setEntityName("testSignature");
		view.commitChanges();
		view = getCombinedPCMC4CView();
		interf = (OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0);
		opSig = interf.getSignatures__OperationInterface().get(0);
		Parameter para = RepositoryFactory.eINSTANCE.createParameter();
		opSig.getParameters__OperationSignature().add(para);
		para.setOperationSignature__Parameter(opSig);
		para.setParameterName("testParameter");
		getPCMRoot(view).getInterfaces__Repository().add(interf);
		view.commitChanges();
		setupC4C();
	}

	void setupC4C() {
		CommittableView view = getCombinedPCMC4CView();
		SignatureInformationFlow flow = edu.kit.kastel.scbs.confidentiality.repository.RepositoryFactory.eINSTANCE
				.createSignatureInformationFlow();
		ParameterInformation info = InformationFactory.eINSTANCE.createParameterInformation();
		flow.getInformation().add(info);
		getC4CRoot(view).getInformationFlows().add(flow);
		view.commitChanges();
		view = getCombinedPCMC4CView();
	}
}
