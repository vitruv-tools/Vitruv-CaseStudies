package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import org.palladiosimulator.pcm.system.System
import org.palladiosimulator.pcm.system.SystemFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import java.nio.file.Path

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::System
 * with its corresponding uml::Package and uml::Class (implementation).
 * <br><br>
 * Related files: 
 * 		PcmSystem.reactions, 
 * 		UmlRepositoryAndSystemPackage.reactions,
 * 		UmlIPREClass.reactions,
 * 		UmlIPREConstructorOperation.reactions
 */
class SystemConceptTest extends PcmUmlClassApplicationTest {

	static val PCM_MODEL_FILE = "model/System.system"
	static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
		DefaultLiterals.UML_EXTENSION

	val MODEL_NAME = "testRootModel"
	val SYSTEM_NAME = "TestSystem"

	def protected static checkSystemConcept(
		CorrespondenceModel cm,
		System pcmSystem,
		Package umlSystemPkg,
		Class umlSystemImpl,
		Operation umlSystemConstructor
	) {
		assertNotNull(pcmSystem)
		assertNotNull(umlSystemPkg)
		assertNotNull(umlSystemImpl)
		assertNotNull(umlSystemConstructor)
		assertTrue(corresponds(cm, pcmSystem, umlSystemPkg, TagLiterals.SYSTEM__SYSTEM_PACKAGE))
		assertTrue(corresponds(cm, pcmSystem, umlSystemImpl, TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(pcmSystem.entityName.toFirstLower == umlSystemPkg.name)
		assertTrue(pcmSystem.entityName == umlSystemPkg.name.toFirstUpper)
		assertTrue(pcmSystem.entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX == umlSystemImpl.name)
		assertTrue(umlSystemImpl.isFinalSpecialization)
		assertTrue(umlSystemImpl.visibility === VisibilityKind.PUBLIC_LITERAL)
		assertTrue(umlSystemImpl.package === umlSystemPkg)
	}

	def protected checkSystemConcept(Package umlSystemPkg) {
		assertNotNull(umlSystemPkg)
		val pcmSystem = helper.getModifiableCorr(umlSystemPkg, System, TagLiterals.SYSTEM__SYSTEM_PACKAGE)
		assertNotNull(pcmSystem)
		checkSystemConcept(pcmSystem)
	}

	def protected checkSystemConcept(System pcmSystem) {
		assertNotNull(pcmSystem)
		val umlSystemPkg = helper.getModifiableCorr(pcmSystem, Package, TagLiterals.SYSTEM__SYSTEM_PACKAGE)
		val umlSystemImpl = helper.getModifiableCorr(pcmSystem, Class, TagLiterals.IPRE__IMPLEMENTATION)
		val umlSystemConstructor = helper.getModifiableCorr(pcmSystem, Operation, TagLiterals.IPRE__CONSTRUCTOR)
		checkSystemConcept(correspondenceModel, pcmSystem, umlSystemPkg, umlSystemImpl, umlSystemConstructor)
	}

	@Test
	def void testCreateSystemConcept_PCM() {
		val pcmSystem = SystemFactory.eINSTANCE.createSystem => [
			entityName = SYSTEM_NAME
		]

		userInteraction.addNextTextInput(UML_MODEL_FILE)
		resourceAt(Path.of(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmSystem
		]
		propagate

		val reloadedPcmSystem = pcmSystem.clearResourcesAndReloadRoot
		checkSystemConcept(reloadedPcmSystem)
		assertTrue(reloadedPcmSystem.entityName == SYSTEM_NAME)
	}

	@Test
	def void testCreateSystemConcept_UML() {
		val umlModel = UMLFactory.eINSTANCE.createModel => [
			name = MODEL_NAME
		]

		userInteraction.addNextTextInput(PCM_MODEL_FILE)
		resourceAt(Path.of(UML_MODEL_FILE)).startRecordingChanges => [
			contents += umlModel
		]
		propagate

		var umlSystemPkg = umlModel.createNestedPackage(SYSTEM_NAME)

		userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__SYSTEM)
		propagate

		umlSystemPkg = umlModel.clearResourcesAndReloadRoot.nestedPackages.findFirst [
			it.name == SYSTEM_NAME.toFirstLower
		]
		assertNotNull(umlSystemPkg)
		checkSystemConcept(umlSystemPkg)
	}

}
