package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.pcm2java.Pcm2JavaTestUtils;

public class CompositeDataTypeMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	public void testAddCompositeDataType() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);

		final CompositeDataType cdt = this.createAndSyncCompositeDataType(repo);

		this.assertDataTypeCorrespondence(cdt);
	}

	@Test
	public void testRenameCompositeDataType() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
		final CompositeDataType cdt = this.createAndSyncCompositeDataType(repo);

		cdt.setEntityName(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + Pcm2JavaTestUtils.RENAME);
		propagate();

		this.assertDataTypeCorrespondence(cdt);
	}

	@Test
	public void testAddCompositeDataTypeWithInnerTypes() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
		final CompositeDataType cdt = this.createCompositeDataType(repo, Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME);
		propagate();

		final InnerDeclaration innerDec = this.addInnerDeclaration(cdt, repo);
		propagate();

		CompositeDataTypeMappingTransformationTest.this.assertDataTypeCorrespondence(cdt);
		CompositeDataTypeMappingTransformationTest.this.assertInnerDeclaration(innerDec);
	}

	@Test
	public void testAddPrimitiveDataTypeToCompositeDataType() throws Throwable {
		final InnerDeclaration innerDec = this.createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration();

		this.assertInnerDeclaration(innerDec);
	}

	@Test
	public void testAddCompositeDataTypeToCompositeDataType() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
		final CompositeDataType cdt = this.createAndSyncCompositeDataType(repo);
		final CompositeDataType cdt2 = this.createAndSyncCompositeDataType(repo, "InnerCompositeDataTypeTest");

		final InnerDeclaration innerDec = RepositoryFactory.eINSTANCE.createInnerDeclaration();
		innerDec.setDatatype_InnerDeclaration(cdt2);
		innerDec.setCompositeDataType_InnerDeclaration(cdt);
		cdt.getInnerDeclaration_CompositeDataType().add(innerDec);
		propagate();

		this.assertDataTypeCorrespondence(cdt);
	}

}
