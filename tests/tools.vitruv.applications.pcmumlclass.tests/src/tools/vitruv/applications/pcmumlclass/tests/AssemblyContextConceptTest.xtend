package tools.vitruv.applications.pcmumlclass.tests

import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.core.composition.CompositionFactory
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMCompositeComponentBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMRepositoryBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLClassBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLPackageBuilder

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::AssemblyContext 
 * in a pcm::ComposedProvidingRequiringEntity (CPRE) with a uml::Property in an uml::Class (the implementation class to the CPRE). 
 * <br><br>
 * Related files: PcmAssemblyContext.reactions, UmlAssemblyContextProperty.reactions
 */
class AssemblyContextConceptTest extends PcmUmlClassApplicationTest {
	static val PROPERTY_NAME = "testAssemblyContextField"

	private def void createRepositoryWithTwoComponents() {
		initPCMRepository()

		changePcmView[
			PcmUmlClassApplicationTestHelper.createComponent(defaultPcmRepository)
			PcmUmlClassApplicationTestHelper.createComponent_2(defaultPcmRepository)
		]
	}

	@Test
	def void testCreateAssemblyContextConcept_PCM() {
		createRepositoryWithTwoComponents()

		changePcmView[
			val pcmComponent1 = PcmUmlClassApplicationTestHelper.getPcmComponent(defaultPcmRepository)
			val pcmComponent2 = PcmUmlClassApplicationTestHelper.getPcmComponent_2(defaultPcmRepository)

			var pcmAssemblyContext = CompositionFactory.eINSTANCE.createAssemblyContext
			pcmAssemblyContext.entityName = PROPERTY_NAME
			// TODO setting the same component as container and encapsulated doesn't seem to trigger change event
			pcmAssemblyContext.encapsulatedComponent__AssemblyContext = pcmComponent2
			pcmComponent1.assemblyContexts__ComposedStructure += pcmAssemblyContext
		]

		validateUmlView[
			val component2Class = new FluentUMLClassBuilder(PcmUmlClassApplicationTestHelper.COMPONENT_NAME_2_USC +
				PcmUmlClassApplicationTestHelper.IMPL_SUFFIX, true).addDefaultConstructor.build
			val component1Class = new FluentUMLClassBuilder(PcmUmlClassApplicationTestHelper.COMPONENT_NAME_USC +
				PcmUmlClassApplicationTestHelper.IMPL_SUFFIX, true).addDefaultConstructor.addAttribute(PROPERTY_NAME,
				component2Class).build
			val component1Package = new FluentUMLPackageBuilder(PcmUmlClassApplicationTestHelper.COMPONENT_NAME_LSC).
				addPackagedElement(component1Class).build
			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel,
				String.join(".", PACKAGE_NAME, PcmUmlClassApplicationTestHelper.COMPONENT_NAME_LSC), component1Package)
		]
	}

	@Test
	def void testCreateAssemblyContextConcept_UML() {
		createRepositoryWithTwoComponents()

		changeUmlView[
			val umlComponent1Class = PcmUmlClassApplicationTestHelper.claimClass(defaultUmlModel,
				PcmUmlClassApplicationTestHelper.COMPONENT_NAME_USC + PcmUmlClassApplicationTestHelper.IMPL_SUFFIX)
			val umlComponent2Class = PcmUmlClassApplicationTestHelper.claimClass(defaultUmlModel,
				PcmUmlClassApplicationTestHelper.COMPONENT_NAME_2_USC + PcmUmlClassApplicationTestHelper.IMPL_SUFFIX)

			umlComponent1Class.createOwnedAttribute(PROPERTY_NAME, umlComponent2Class)
		]

		validatePcmView[
			val pcmComponent2 = new FluentPCMCompositeComponentBuilder(
				PcmUmlClassApplicationTestHelper.COMPONENT_NAME_2_USC).build
			val pcmComponent1 = new FluentPCMCompositeComponentBuilder(
				PcmUmlClassApplicationTestHelper.COMPONENT_NAME_USC).addAssemblyContext(PROPERTY_NAME, pcmComponent2).
				build
			val expectedRepository = new FluentPCMRepositoryBuilder(PACKAGE_NAME_FIRST_UPPER).addComponent(
				pcmComponent1).addComponent(pcmComponent2).build

			assertEqualityOfPcmRepository(defaultPcmRepository, expectedRepository)
		]
	}
}
