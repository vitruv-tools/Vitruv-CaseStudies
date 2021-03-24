package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.pcm2java.Pcm2JavaTestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RepositoryMappingTransformaitonTest extends Pcm2JavaTransformationTest {

	@Test
	public void testAddRepository() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);

		this.assertRepositoryCorrespondences(repo);
	}

	@Test
	public void testRepositoryNameChange() throws Throwable {
		// setup
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);

		// Test
		repo.setEntityName(Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME);
		repo.eResource().save(null);
		propagate();

		// check
		this.assertRepositoryCorrespondences(repo);
	}

	@Test
	public void testRepositoryNameChangeWithComponents() throws Throwable {
		// setup
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
		final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

		// Test
		repo.setEntityName(Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME);
		repo.eResource().save(null);
		propagate();

		// check
		this.assertRepositoryCorrespondences(repo);
		this.assertBasicComponentCorrespondences(repo, basicComponent);
	}

	@SuppressWarnings("unchecked")
	private void assertBasicComponentCorrespondences(final Repository repository, final BasicComponent basicComponent)
			throws Throwable {
		this.assertCorrespondnecesAndCompareNames(basicComponent, 3,
				new java.lang.Class[] { CompilationUnit.class, Package.class, Class.class },
				new String[] {
						repository.getEntityName() + "." + basicComponent.getEntityName() + "."
								+ basicComponent.getEntityName() + "Impl",
						basicComponent.getEntityName(), basicComponent.getEntityName() + "Impl" });

	}

	private void assertRepositoryCorrespondences(final Repository repo) throws Throwable {
		final Iterable<Package> jaMoPPPackages = getCorrespondingEObjects(repo, Package.class);
		boolean foundRepositoryPackage = false;
		boolean foundDatatypesPackage = false;
		boolean foundContractsPackage = false;
		for (final Package pack : jaMoPPPackages) {
			if (pack.getName().equals(repo.getEntityName())) {
				foundRepositoryPackage = true;
			}
			if (pack.getName().equals("contracts")) {
				foundContractsPackage = true;
			}
			if (pack.getName().equals("datatypes")) {
				foundDatatypesPackage = true;
			}
		}
		assertTrue(foundRepositoryPackage, "No correspondeing repository package found");
		assertTrue(foundDatatypesPackage, "No correspondeing datatype package found");
		assertTrue(foundContractsPackage, "No correspondeing contracts package found");
	}
}
