package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.junit.Test;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;

public class CollectionDataTypeMappingTransformationTest extends Pcm2JavaTransformationTest {

    @Test
    public void testAddCollectionDataTypeWithoutInnerType() throws Throwable {
        final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);

        this.getUserInteractor().addNextSelections(0);
        final CollectionDataType collectionDataType = this.addCollectionDatatypeAndSync(repo,
                Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME, null);

        this.assertDataTypeCorrespondence(collectionDataType);
    }

    @Test
    public void testAddCollectionDataTypeWithPrimitiveTypeStringAsInnerType() throws Throwable {
        final PrimitiveTypeEnum pte = PrimitiveTypeEnum.STRING;
        this.testAddCollectionDataTypeWithPrimitiveInnerType(pte);
    }

    @Test
    public void testAddCollectionDataTypeWithPrimitiveTypeIntAsInnerType() throws Throwable {
        this.testAddCollectionDataTypeWithPrimitiveInnerType(PrimitiveTypeEnum.INT);
    }

    @Test
    public void testAddCollectionDataTypeWithComplexInnerType() throws Throwable {
        final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final CompositeDataType compositeDataType = this.createAndSyncCompositeDataType(repo);

        this.getUserInteractor().addNextSelections(0);
        final CollectionDataType collectionDataType = this.addCollectionDatatypeAndSync(repo,
                Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME, compositeDataType);

        this.assertDataTypeCorrespondence(collectionDataType);
    }

    protected void testAddCollectionDataTypeWithPrimitiveInnerType(final PrimitiveTypeEnum pte) throws Throwable {
        final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
        final PrimitiveDataType primitiveType = createPrimitiveDataType(pte, repo);
        
        this.getUserInteractor().addNextSelections(0);
        final CollectionDataType collectionDataType = this.addCollectionDatatypeAndSync(repo,
                Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME, primitiveType);

        this.assertDataTypeCorrespondence(collectionDataType);
    }
    
}
