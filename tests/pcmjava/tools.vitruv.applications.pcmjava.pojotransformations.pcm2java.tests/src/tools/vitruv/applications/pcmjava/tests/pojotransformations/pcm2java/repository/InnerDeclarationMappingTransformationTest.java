package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.junit.Test;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class InnerDeclarationMappingTransformationTest extends Pcm2JavaTransformationTest {

    @Test
    public void testAddInnerDeclaration() throws Throwable {
        final InnerDeclaration innerDec = this.createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration();

        this.assertInnerDeclaration(innerDec);
    }

    @Test
    public void testRenameInnerDeclaration() throws Throwable {
        final InnerDeclaration innerDec = this.createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration();

        innerDec.setEntityName(Pcm2JavaTestUtils.INNER_DEC_NAME + Pcm2JavaTestUtils.RENAME);
        super.saveAndSynchronizeChanges(innerDec);

        this.assertInnerDeclaration(innerDec);
    }

    @Test
    public void testChangeInnerDeclarationType() throws Throwable {
        final InnerDeclaration innerDec = this.createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration();

        final Repository repo = innerDec.getCompositeDataType_InnerDeclaration().getRepository__DataType();
        final PrimitiveDataType newPDT = createPrimitiveDataType(PrimitiveTypeEnum.STRING, repo);
        innerDec.setDatatype_InnerDeclaration(newPDT);
        super.saveAndSynchronizeChanges(innerDec);

        this.assertInnerDeclaration(innerDec);
    }

}
