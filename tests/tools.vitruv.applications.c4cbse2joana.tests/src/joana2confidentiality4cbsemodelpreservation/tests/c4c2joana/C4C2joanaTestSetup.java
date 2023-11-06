package joana2confidentiality4cbsemodelpreservation.tests.c4c2joana;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.kastel.scbs.confidentiality.data.DataFactory;
import edu.kit.kastel.scbs.confidentiality.data.DataSet;
import edu.kit.kastel.scbs.confidentiality.information.InformationFactory;
import edu.kit.kastel.scbs.confidentiality.information.ParameterInformation;
import edu.kit.kastel.scbs.confidentiality.repository.SignatureInformationFlow;
import tools.vitruv.applications.util.temporary.java.JavaSetup;
import tools.vitruv.framework.views.CommittableView;


public class C4C2joanaTestSetup extends C4C2joanaTest {

	private static final String MY_SET = "MySet";
	private static final String MY_OPERATION = "MyOperation";
	private static final String MY_PARAMETER = "MyParameter";
	private static final String SECOND_PARAMETER = "SecondParameter";
	private static final String SECOND_OPSIG = "SecondOpSig";

	@Override
	protected boolean enableTransitiveCyclicChangePropagation() {
		return true;
	}
	
	@Test
	public void testCreateJoanaRoot() {
		getC4CViewWithC4CRoot();
		assertEquals(4, getCombinedJavaJoanaView().getRootObjects().size());
		assertEquals(2, getCombinedPCMC4CView().getRootObjects().size());
	}

	@BeforeEach
	void setupPCM() {
		EcorePlugin.ExtensionProcessor.process(null);
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
		JavaSetup.prepareFactories();
		CommittableView view = getPCMViewWithPCMRoot();
		view = getCombinedPCMC4CView();
		OperationInterface interf = RepositoryFactory.eINSTANCE.createOperationInterface();
		interf.setRepository__Interface(getPCMRoot(view));
		getPCMRoot(view).getInterfaces__Repository().add(interf);
		view.commitChanges();
		view = getCombinedPCMC4CView();
		interf = (OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0);
		OperationSignature opSig = RepositoryFactory.eINSTANCE.createOperationSignature();
		opSig.setInterface__OperationSignature(interf);
		opSig.setEntityName(MY_OPERATION);
		interf.getSignatures__OperationInterface().add(opSig);
		view.commitChanges();
		view = getCombinedPCMC4CView();
		interf = (OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0);
		opSig = interf.getSignatures__OperationInterface().get(0);
		Parameter para = RepositoryFactory.eINSTANCE.createParameter();
		para.setParameterName(MY_PARAMETER);
		opSig.getParameters__OperationSignature().add(para);
		para.setOperationSignature__Parameter(opSig);
		view.commitChanges();
		view = getCombinedPCMC4CView();
		setupC4C();
	}

	void setupC4C() {
		CommittableView view = getCombinedPCMC4CView();
		SignatureInformationFlow flow = edu.kit.kastel.scbs.confidentiality.repository.RepositoryFactory.eINSTANCE
				.createSignatureInformationFlow();
		getC4CRoot(view).getInformationFlows().add(flow);
		view.commitChanges();
				
		view = getCombinedPCMC4CView();
		OperationSignature opSig = ((OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0))
				.getSignatures__OperationInterface().get(0);
		flow = (SignatureInformationFlow) getC4CRoot(view).getInformationFlows().get(0);
		flow.setAppliedTo(opSig);
		
		view.commitChanges();
		view = getCombinedPCMC4CView();

		view = getCombinedPCMC4CView();
		var opInterface = ((OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0))
				.getSignatures__OperationInterface().get(0);
		Parameter para = opInterface.getParameters__OperationSignature().get(0);
		var sigInfo = (SignatureInformationFlow) getC4CRoot(view).getInformationFlows().get(0);
		ParameterInformation info = InformationFactory.eINSTANCE.createParameterInformation();
		sigInfo.getInformation().add(info);
		info.setAppliedTo(para);
		view.commitChanges();
		
		
		view = getCombinedPCMC4CView();
		info = (ParameterInformation) getC4CRoot(view).getInformationFlows().get(0).getInformation().get(0);
		DataSet set = DataFactory.eINSTANCE.createDataSet();
		set.setEntityName(MY_SET);
		info.getDataTargets().add(set);
		getC4CRoot(view).getDataIdentifier().add(set);

		view.commitChanges();
		view = getCombinedJavaJoanaView();
	}

	void addSecondParameter() {
		CommittableView view = getCombinedPCMC4CView();
		Parameter para = RepositoryFactory.eINSTANCE.createParameter();
		para.setParameterName(SECOND_PARAMETER);
		OperationInterface inter = (OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0);
		OperationSignature opSig = inter.getSignatures__OperationInterface().get(0);
		opSig.getParameters__OperationSignature().add(para);
		view.commitChanges();
	}

	void addSecondOperationSignature() {
		CommittableView view = getCombinedPCMC4CView();
		OperationSignature opSig = RepositoryFactory.eINSTANCE.createOperationSignature();
		opSig.setEntityName(SECOND_OPSIG);
		OperationInterface inter = (OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0);
		inter.getSignatures__OperationInterface().add(opSig);
		view.commitChanges();
	}

	void addSecondSignatureFlow() {
		addSecondOperationSignature();
		CommittableView view = getCombinedPCMC4CView();
		SignatureInformationFlow flow = edu.kit.kastel.scbs.confidentiality.repository.RepositoryFactory.eINSTANCE
				.createSignatureInformationFlow();
		OperationSignature opSig = ((OperationInterface) getPCMRoot(view).getInterfaces__Repository().get(0))
				.getSignatures__OperationInterface().get(1);
		flow.setAppliedTo(opSig);
		getC4CRoot(view).getInformationFlows().add(flow);
		view.commitChanges();
	}

}
