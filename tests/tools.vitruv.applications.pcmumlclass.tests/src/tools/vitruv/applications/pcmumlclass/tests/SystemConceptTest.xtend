package tools.vitruv.applications.pcmumlclass.tests

import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.system.SystemFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLClassBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLPackageBuilder

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
	val SYSTEM_NAME_UC = "TestSystem"
	val SYSTEM_NAME_LC = "testSystem"

	@Test
	def void testCreateSystemConcept_PCM() {
		createSystem(SYSTEM_NAME_UC)

		validateUmlView[
			val expectedSystemClass = new FluentUMLClassBuilder(SYSTEM_NAME_UC +
				PcmUmlClassApplicationTestHelper.IMPL_SUFFIX, true).addDefaultConstructor.build
			val expectedPackage = new FluentUMLPackageBuilder(SYSTEM_NAME_LC).addPackagedElement(expectedSystemClass).
				build
			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel, SYSTEM_NAME_LC, expectedPackage)
		]
	}

	@Test
	def void testCreateSystemConcept_UML() {
		changeUmlView[
			userInteraction.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__SYSTEM)
			userInteraction.addNextTextInput(PCM_MODEL_FILE)
			defaultUmlModel.createNestedPackage(SYSTEM_NAME_UC)
		]

		validateUmlAndPcmSystemView[
			val actualSystem = PcmQueryUtil.claimPcmSystem(it)
			val expectedSystem = SystemFactory.eINSTANCE.createSystem
			expectedSystem.entityName = SYSTEM_NAME_UC

			assertEqualityOfPcmSystem(actualSystem, expectedSystem)
		]
	}

}
