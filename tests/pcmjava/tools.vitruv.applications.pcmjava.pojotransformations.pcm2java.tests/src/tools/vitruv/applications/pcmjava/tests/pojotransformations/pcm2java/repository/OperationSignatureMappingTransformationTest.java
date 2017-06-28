package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;
import tools.vitruv.applications.pcmjava.util.pcm2java.DataTypeCorrespondenceHelper;

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
        super.saveAndSynchronizeChanges(opSig);

        this.assertOperationSignatureCorrespondence(opSig);
    }

    @Test
    public void testChangeOperationSignatureReturnType() throws Throwable {
        final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();

        final Repository repo = opSig.getInterface__OperationSignature().getRepository__Interface();
        final PrimitiveDataType pdt = createPrimitiveDataType(PrimitiveTypeEnum.STRING, repo);
        opSig.setReturnType__OperationSignature(pdt);
        super.saveAndSynchronizeChanges(opSig);

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
        super.saveAndSynchronizeChanges(opInterface);

        // assert the signature
        this.assertOperationSignatureCorrespondence(opSig);
    }

    private InterfaceMethod assertOperationSignatureCorrespondence(final OperationSignature opSig) throws Throwable {
        final InterfaceMethod intMethod = (InterfaceMethod) this.assertSingleCorrespondence(opSig,
                InterfaceMethod.class, opSig.getEntityName());
        final TypeReference tr = DataTypeCorrespondenceHelper.claimUniqueCorrespondingJaMoPPDataTypeReference(
                opSig.getReturnType__OperationSignature(), this.getCorrespondenceModel());
        this.assertEqualsTypeReference(intMethod.getTypeReference(), tr);

        return intMethod;
    }

}
