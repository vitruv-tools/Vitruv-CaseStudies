package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;
import static tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper.isPackageFor;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Objects;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.emftext.language.java.containers.ContainersPackage;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTestHelper;

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::Repository
 * with its corresponding uml::Packages.
 * <br><br>
 * Related files: PcmRepository.reactions, UmlRepositoryAndSystemPackage.reactions
 */
public class RepositoryConceptTest extends PcmUmlJavaTransitiveChangeTest {

	protected void checkRepositoryConcept(
		Repository pcmRepo,
		Package umlRepositoryPkg,
		Package umlContractsPkg,
		Package umlDatatypesPkg
	) {
		// correspondence constraints
		assertTrue(corresponds(pcmRepo, umlRepositoryPkg, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE));
		assertTrue(corresponds(pcmRepo, umlContractsPkg, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE));
		assertTrue(corresponds(pcmRepo, umlDatatypesPkg, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE));
		// containment constraints
		assertTrue(EcoreUtil.equals(umlContractsPkg.getNestingPackage(), umlRepositoryPkg));
		assertTrue(EcoreUtil.equals(umlDatatypesPkg.getNestingPackage(), umlRepositoryPkg));
		// attribute constraints
		assertTrue(isPackageFor(umlRepositoryPkg, pcmRepo));
		assertTrue(Objects.equals(toFirstUpper(umlRepositoryPkg.getName()), pcmRepo.getEntityName()));
		assertTrue(Objects.equals(umlContractsPkg.getName(), DefaultLiterals.CONTRACTS_PACKAGE_NAME));
		assertTrue(Objects.equals(umlDatatypesPkg.getName(), DefaultLiterals.DATATYPES_PACKAGE_NAME));
	}

	protected void checkUmlRepositoryPackage(Package umlRepositoryPkg) {
		assertTrue(umlRepositoryPkg != null);
		Repository pcmRepository = helper.getModifiableCorr(umlRepositoryPkg, Repository.class,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE);
		assertTrue(pcmRepository != null);
		checkPcmRepository(pcmRepository);
		checkJavaRepositoryPackage(umlRepositoryPkg);
	}

	protected void checkPcmRepository(Repository pcmRepository) {
		assertTrue(pcmRepository != null);
		Package umlRepositoryPkg = helper.getModifiableCorr(pcmRepository, Package.class,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE);
		Package umlContractsPkg = helper.getModifiableCorr(pcmRepository, Package.class,
			TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE);
		Package umlDatatypesPkg = helper.getModifiableCorr(pcmRepository, Package.class,
			TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE);
		assertTrue(umlRepositoryPkg != null);
		assertTrue(umlContractsPkg != null);
		assertTrue(umlDatatypesPkg != null);
		checkRepositoryConcept(pcmRepository, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg);
		checkJavaRepositoryPackage(umlRepositoryPkg);
	}

	/**
	 * Checks whether the Java package structure fits to the UML package structure (assumes UML is correct)
	 */
	protected void checkJavaRepositoryPackage(Package umlRepositoryPackage) {
		checkJavaPackage(umlRepositoryPackage);
		umlRepositoryPackage.getNestedPackages().forEach(this::checkJavaPackage);

		var javaRepositoryPackage = getFirstCorrespondingObject(umlRepositoryPackage,
			org.emftext.language.java.containers.Package.class);
		checkUmlPackage(javaRepositoryPackage);
	}

	@Test
	public void testCreateRepositoryConcept_UML() {
		Model umlModel = UMLFactory.eINSTANCE.createModel();
		umlModel.setName("umlModel");
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE))).getContents()
			.add(umlModel);
		propagate();
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);

		var umlRepositoryPkg = umlModel.createNestedPackage("testCbsRepository");

		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY);
		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getSelection());
		propagate();
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);

		umlRepositoryPkg = Iterables.getFirst(clearResourcesAndReloadRoot(umlModel).getNestedPackages(), null);
		assertTrue(Objects.equals(umlRepositoryPkg.getName(), "testCbsRepository"));

		checkUmlRepositoryPackage(umlRepositoryPkg);
	}

	@Test
	public void testCreateRepositoryConcept_PCM() {
		Repository pcmRepository = RepositoryFactory.eINSTANCE.createRepository();

		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(pcmRepository);
		propagate();
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);

		checkPcmRepository(clearResourcesAndReloadRoot(pcmRepository));
	}

	@Test
	public void testRenameRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository();
		final var initialRepository = pcmRepository;

		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getSelection());
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(initialRepository);
		propagate();

		pcmRepository.setEntityName("Pcm2UmlNameChange");
		propagate();
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		final var newName = "pcm2UmlNameChange_2"; // should be synchronized to upper case
		pcmRepository.setEntityName(newName);
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getSelection());
		propagate();
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		assertTrue(Objects.equals(pcmRepository.getEntityName(), toFirstUpper(newName)));
		checkPcmRepository(pcmRepository);

		// There should be no Java packages:
		var umlRepositoryPackage = helper.getModifiableCorr(pcmRepository, Package.class,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE);
		var umlModel = umlRepositoryPackage.getNestingPackage();
		checkNumberOfJavaPackages(umlModel);
	}

	@Test
	public void testDeleteRepositoryConcept_PCM() {
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository();
		pcmRepository.setEntityName("testCbsRepository"); // has to be capitalized via round-trip
		final var initialRepository = pcmRepository;
		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(initialRepository);
		propagate();
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);

		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		assertTrue(Objects.equals(pcmRepository.getEntityName(), "TestCbsRepository"));
		checkPcmRepository(pcmRepository);

		var umlRepositoryPackage = helper.getModifiableCorr(pcmRepository, Package.class,
			TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE);
		var umlModel = umlRepositoryPackage.getNestingPackage();

		getUserInteraction().addNextConfirmationInput(true);
		propagate(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)), resource -> {
			try {
				resource.delete(null);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});

		assertModelNotExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		// before the following reload, the mUmlModel instance will be out of synch with the vsum
		assertFalse(umlModel.getPackagedElements().isEmpty());
		umlModel = clearResourcesAndReloadRoot(umlModel);
		assertTrue(umlModel.getPackagedElements().isEmpty());

		// There should be no Java packages:
		var allJavaPackages = getCorrespondingEObjects(ContainersPackage.Literals.PACKAGE, org.emftext.language.java.containers.Package.class);
		assertEquals(umlModel.getPackagedElements().size(), Iterables.size(allJavaPackages),
			"Too many Java packages: " + allJavaPackages);
	}
}
