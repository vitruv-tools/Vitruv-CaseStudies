package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java;

import org.emftext.language.java.classifiers.Classifier;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.tests.util.pcm2java.Pcm2JavaTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;

public class DataTypeCorrespondenceHelperTest extends Pcm2JavaTransformationTest {

	@Test
	public void testCorrespondenceForCompositeDataType() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);

		// Create and sync CompositeDataType
		final CompositeDataType cdt = this.createAndSyncCompositeDataType(repo);

		assertEquals(cdt.getEntityName(), claimOne(getCorrespondingEObjects(cdt, Classifier.class)).getName(),
				"Name of composite data type does not equals name of classifier");
	}

	@Test
	public void testCorrespondenceForCollectionDataType() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);

		// Create and sync CollectionDataType
		this.getUserInteraction().addNextSingleSelection(0);
		final CollectionDataType collectionDataType = this.addCollectionDatatypeAndSync(repo,
				Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME, null);

		assertEquals(collectionDataType.getEntityName(),
				claimOne(getCorrespondingEObjects(collectionDataType, Classifier.class)).getName(),
				"Name of composite data type does not equals name of classifier");
	}

}
