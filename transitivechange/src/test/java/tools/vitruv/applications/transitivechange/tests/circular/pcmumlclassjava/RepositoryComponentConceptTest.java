package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;
import static tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper.isPackageFor;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Objects;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTestHelper;

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize synchronize a pcm::RepositoryComponent with
 * its corresponding uml::Package, uml::Class (implementation), and uml::Operation (constructor).
 * <br><br>
 * Related files:
 * 		PcmRepositoryComponent.reactions,
 * 		UmlRepositoryComponentPackage.reactions,
 * 		UmlIPREClassReactions.reactions,
 * 		UmlIPREConstructorOperation.reactions
 */
public class RepositoryComponentConceptTest extends PcmUmlJavaTransitiveChangeTest {

	private final String COMPONENT_NAME = "testComponent";

	public void checkRepositoryComponentConcept(
		RepositoryComponent pcmComponent,
		Package umlComponentPkg,
		Class umlComponentImpl,
		Operation umlComponentConstructor
	) {
		assertNotNull(pcmComponent);
		assertNotNull(umlComponentPkg);
		assertNotNull(umlComponentImpl);
		assertNotNull(umlComponentConstructor);
		assertTrue(corresponds(pcmComponent, umlComponentPkg, TagLiterals.REPOSITORY_COMPONENT__PACKAGE));
		assertTrue(corresponds(pcmComponent, umlComponentImpl, TagLiterals.IPRE__IMPLEMENTATION));
		assertTrue(corresponds(pcmComponent, umlComponentConstructor, TagLiterals.IPRE__CONSTRUCTOR));
		assertTrue(isPackageFor(umlComponentPkg, pcmComponent));
		assertTrue(Objects.equals(pcmComponent.getEntityName(), toFirstUpper(umlComponentPkg.getName())));
		assertTrue(Objects.equals(pcmComponent.getEntityName() + DefaultLiterals.IMPLEMENTATION_SUFFIX, umlComponentImpl.getName()));
		assertTrue(Objects.equals(pcmComponent.getEntityName() + DefaultLiterals.IMPLEMENTATION_SUFFIX, umlComponentConstructor.getName()));
		// decided against explicit constructor return type, because it's a common convention
		assertTrue(umlComponentImpl.isFinalSpecialization());
		assertTrue(umlComponentImpl.getVisibility() == VisibilityKind.PUBLIC_LITERAL);
		assertTrue(umlComponentImpl.getPackage() == umlComponentPkg);
		// component repository should correspond to the parent package of the component package
		assertTrue(
			corresponds(pcmComponent.getRepository__RepositoryComponent(), umlComponentPkg.getNestingPackage(),
				TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE));
	}

	protected void checkRepositoryComponentConcept(RepositoryComponent pcmComponent) {
		Package umlComponentPkg = helper.getModifiableCorr(pcmComponent, Package.class, TagLiterals.REPOSITORY_COMPONENT__PACKAGE);
		Class umlComponentImpl = helper.getModifiableCorr(pcmComponent, Class.class, TagLiterals.IPRE__IMPLEMENTATION);
		Operation umlComponentConstructor = helper.getModifiableCorr(pcmComponent, Operation.class, TagLiterals.IPRE__CONSTRUCTOR);
		checkRepositoryComponentConcept(pcmComponent, umlComponentPkg, umlComponentImpl,
			umlComponentConstructor);
		// Check Java model:
		checkJavaConstructor(umlComponentConstructor);
		checkJavaType(umlComponentImpl);
		// Created before test cases, should be still there:
		var umlPackage = helper.getUmlRepositoryPackage(pcmComponent.getRepository__RepositoryComponent());
		checkJavaPackage(umlPackage);
		umlPackage.getNestedPackages().forEach(this::checkJavaPackage); // does also contain umlComponentPkg
	}

	protected void checkRepositoryComponentConcept(Package umlComponentPkg) {
		RepositoryComponent pcmComponent = helper.getModifiableCorr(umlComponentPkg, RepositoryComponent.class,
			TagLiterals.REPOSITORY_COMPONENT__PACKAGE);
		assertNotNull(pcmComponent);
		checkRepositoryComponentConcept(pcmComponent);
	}

	/**
	 * Initialize a pcm::Repository and its corresponding uml-counterparts.
	 */
	private Repository createRepository() {
		Repository pcmRepository = helper.createRepository();

		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(pcmRepository);
		propagate();
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);

		return clearResourcesAndReloadRoot(pcmRepository);
	}

	@Test
	public void testRepositoryComponentConcept_PCM() {
		var pcmRepository = createRepository();

		var pcmComponent = RepositoryFactory.eINSTANCE.createCompositeComponent();
		pcmComponent.setEntityName(COMPONENT_NAME);
		pcmRepository.getComponents__Repository().add(pcmComponent);

		propagate();
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		pcmComponent = (CompositeComponent) Iterables.getFirst(pcmRepository.getComponents__Repository(), null);

		checkRepositoryComponentConcept(pcmComponent);
	}

	@Test
	public void testRepositoryComponentConcept_UML() {
		var pcmRepository = createRepository();
		var umlRepositoryPkg = helper.getUmlRepositoryPackage(pcmRepository);
		startRecordingChanges(umlRepositoryPkg);

		getUserInteraction().addNextSingleSelection(
			DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__COMPOSITE_COMPONENT);
		var umlComponentPkg = umlRepositoryPkg.createNestedPackage(COMPONENT_NAME);

		getUserInteraction().addNextSingleSelection(0);
		propagate();
		clearResourcesAndReloadRoot(umlComponentPkg);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		umlRepositoryPkg = helper.getUmlRepositoryPackage(pcmRepository);

		umlComponentPkg = umlRepositoryPkg.getNestedPackages().stream()
			.filter(it -> Objects.equals(it.getName(), COMPONENT_NAME))
			.findFirst().orElse(null);

		checkRepositoryComponentConcept(umlComponentPkg);
	}
}
