package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;
import static tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper.isPackageFor;

import java.nio.file.Path;
import java.util.Objects;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemFactory;
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::System
 * with its corresponding uml::Package and uml::Class (implementation).
 * <br><br>
 * Related files:
 * 		PcmSystem.reactions,
 * 		UmlRepositoryAndSystemPackage.reactions,
 * 		UmlIPREClass.reactions,
 * 		UmlIPREConstructorOperation.reactions
 */
public class SystemConceptTest extends PcmUmlJavaTransitiveChangeTest {

	private static final String PCM_MODEL_FILE = "model/System.system";
	private static final String UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
		DefaultLiterals.UML_EXTENSION;

	private final String MODEL_NAME = "testRootModel";
	private final String SYSTEM_NAME = "TestSystem";

	protected void checkSystemConcept(
		System pcmSystem,
		Package umlSystemPkg,
		Class umlSystemImpl,
		Operation umlSystemConstructor
	) {
		assertNotNull(pcmSystem);
		assertNotNull(umlSystemPkg);
		assertNotNull(umlSystemImpl);
		assertNotNull(umlSystemConstructor);
		assertTrue(corresponds(pcmSystem, umlSystemPkg, TagLiterals.SYSTEM__SYSTEM_PACKAGE));
		assertTrue(corresponds(pcmSystem, umlSystemImpl, TagLiterals.IPRE__IMPLEMENTATION));
		assertTrue(isPackageFor(umlSystemPkg, pcmSystem));
		assertTrue(Objects.equals(pcmSystem.getEntityName(), toFirstUpper(umlSystemPkg.getName())));
		assertTrue(Objects.equals(pcmSystem.getEntityName() + DefaultLiterals.IMPLEMENTATION_SUFFIX, umlSystemImpl.getName()));
		assertTrue(umlSystemImpl.isFinalSpecialization());
		assertTrue(umlSystemImpl.getVisibility() == VisibilityKind.PUBLIC_LITERAL);
		assertTrue(umlSystemImpl.getPackage() == umlSystemPkg);
	}

	protected void checkSystemConcept(Package umlSystemPkg) {
		assertNotNull(umlSystemPkg);
		System pcmSystem = helper.getModifiableCorr(umlSystemPkg, System.class, TagLiterals.SYSTEM__SYSTEM_PACKAGE);
		assertNotNull(pcmSystem);
		checkSystemConcept(pcmSystem);
	}

	protected void checkSystemConcept(System pcmSystem) {
		assertNotNull(pcmSystem);
		Package umlSystemPkg = helper.getModifiableCorr(pcmSystem, Package.class, TagLiterals.SYSTEM__SYSTEM_PACKAGE);
		Class umlSystemImpl = helper.getModifiableCorr(pcmSystem, Class.class, TagLiterals.IPRE__IMPLEMENTATION);
		Operation umlSystemConstructor = helper.getModifiableCorr(pcmSystem, Operation.class, TagLiterals.IPRE__CONSTRUCTOR);
		checkSystemConcept(pcmSystem, umlSystemPkg, umlSystemImpl, umlSystemConstructor);
		checkJavaPackage(umlSystemPkg);
		checkJavaType(umlSystemImpl);
		checkJavaConstructor(umlSystemConstructor);
	}

	@Test
	public void testCreateSystemConcept_PCM() {
		System pcmSystem = SystemFactory.eINSTANCE.createSystem();
		pcmSystem.setEntityName(SYSTEM_NAME);

		// Always required
		getUserInteraction().addNextTextInput(UML_MODEL_FILE);
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection()); // Also serves as NOTHING in the rarer case
		// Depending on the transformation execution order sometimes (but rarely) required:
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__NOTHING);
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());

		startRecordingChanges(resourceAt(Path.of(PCM_MODEL_FILE))).getContents().add(pcmSystem);
		propagate();
		var reloadedPcmSystem = clearResourcesAndReloadRoot(pcmSystem);

		checkSystemConcept(reloadedPcmSystem);
		assertTrue(Objects.equals(reloadedPcmSystem.getEntityName(), SYSTEM_NAME));
	}

	@Test
	public void testCreateSystemConcept_UML() {
		Model umlModel = UMLFactory.eINSTANCE.createModel();
		umlModel.setName(MODEL_NAME);

		getUserInteraction().addNextTextInput(PCM_MODEL_FILE);
		startRecordingChanges(resourceAt(Path.of(UML_MODEL_FILE))).getContents().add(umlModel);
		propagate();

		var umlSystemPkg = umlModel.createNestedPackage(toFirstLower(SYSTEM_NAME));

		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__SYSTEM);
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__NOTHING); // In the rare case also serves as SYSTEM
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__NOTHING); // In the rare case also serves as SYSTEM
		// In the rare case not needed (depending on the transformation execution order)
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());

		propagate();

		umlSystemPkg = clearResourcesAndReloadRoot(umlModel).getNestedPackages().stream()
			.filter(it -> Objects.equals(it.getName(), toFirstLower(SYSTEM_NAME)))
			.findFirst().orElse(null);
		assertNotNull(umlSystemPkg);
		checkSystemConcept(umlSystemPkg);
	}

}
