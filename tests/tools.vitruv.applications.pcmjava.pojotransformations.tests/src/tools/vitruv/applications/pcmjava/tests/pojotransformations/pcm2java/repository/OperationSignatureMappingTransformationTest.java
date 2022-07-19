package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.Void;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.pcm2java.Pcm2JavaTestUtils;
import tools.vitruv.applications.pcmjava.util.pcm2java.DataTypeCorrespondenceHelper;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;

import org.eclipse.emf.ecore.util.EcoreUtil;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class OperationSignatureMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	public void testAddOperationSignature() throws Throwable {
		final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();

		this.assertOperationSignatureCorrespondence(opSig);
	}

	@Test
	public void testRenameOperationSignature() throws Throwable {
		final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();

		opSig.setEntityName(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME + Pcm2JavaTestUtils.RENAME);
		propagate();

		this.assertOperationSignatureCorrespondence(opSig);
	}

	@Test
	public void testChangeOperationSignatureReturnType() throws Throwable {
		final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();

		final Repository repo = opSig.getInterface__OperationSignature().getRepository__Interface();
		final PrimitiveDataType pdt = createPrimitiveDataType(PrimitiveTypeEnum.STRING, repo);
		opSig.setReturnType__OperationSignature(pdt);
		propagate();

		this.assertOperationSignatureCorrespondence(opSig);
	}

	@Test
	public void testCreateOperationSignatureWithReturnType() throws Throwable {
		// create
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
		final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
				Pcm2JavaTestUtils.INTERFACE_NAME);

		// prepare OperationSignature with return type at creation
		final OperationSignature opSig = RepositoryFactory.eINSTANCE.createOperationSignature();
		opSig.setEntityName(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME);
		final PrimitiveDataType pdt = createPrimitiveDataType(PrimitiveTypeEnum.STRING, repo);
		opSig.setReturnType__OperationSignature(pdt);
		opSig.setInterface__OperationSignature(opInterface);
		// trigger synchronization execution
		propagate();

		// assert the signature
		this.assertOperationSignatureCorrespondence(opSig);
	}

	private InterfaceMethod assertOperationSignatureCorrespondence(final OperationSignature opSig) throws Throwable {
		final InterfaceMethod intMethod = (InterfaceMethod) this.assertSingleCorrespondence(opSig,
				InterfaceMethod.class, opSig.getEntityName());
		if (opSig.getReturnType__OperationSignature() == null) {
			assertThat(intMethod.getTypeReference(), instanceOf(Void.class));
		} else {
			Type type;
			if (opSig.getReturnType__OperationSignature() instanceof PrimitiveDataType) {
				type = DataTypeCorrespondenceHelper.claimJaMoPPTypeForPrimitiveDataType((PrimitiveDataType)opSig.getReturnType__OperationSignature());
			} else {
				type = claimOne(getCorrespondingEObjects(opSig.getReturnType__OperationSignature(), Classifier.class));
			}
			EcoreUtil.equals(intMethod.getTypeReference().getTarget(), type);
		}
		return intMethod;
	}

}
