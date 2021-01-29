package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava

import org.eclipse.uml2.uml.Property
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.core.composition.AssemblyContext
import org.palladiosimulator.pcm.core.composition.CompositionFactory
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.applications.pcmumlclass.tests.PcmUmlClassApplicationTestHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.jupiter.api.Assertions.*
import java.nio.file.Path

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::AssemblyContext 
 * in a pcm::ComposedProvidingRequiringEntity (CPRE) with a uml::Property in an uml::Class (the implementation class to the CPRE). 
 * <br><br>
 * Related files: PcmAssemblyContext.reactions, UmlAssemblyContextProperty.reactions
 */
class AssemblyContextConceptTest extends PcmUmlJavaTransitiveChangeTest {

	static val PROPERTY_NAME = "testAssemblyContextField"

	def static void checkAssemblyContextConcept(
		CorrespondenceModel cm,
		AssemblyContext pcmAssemblyContext,
		Property umlAssemblyContextProperty
	) {
		assertNotNull(pcmAssemblyContext)
		assertNotNull(umlAssemblyContextProperty)
		assertTrue(
			corresponds(cm, pcmAssemblyContext, umlAssemblyContextProperty, TagLiterals.ASSEMBLY_CONTEXT__PROPERTY))
		assertTrue(
			corresponds(cm, pcmAssemblyContext.parentStructure__AssemblyContext, umlAssemblyContextProperty.owner,
				TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(
			corresponds(cm, pcmAssemblyContext.encapsulatedComponent__AssemblyContext, umlAssemblyContextProperty.type,
				TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(pcmAssemblyContext.entityName == umlAssemblyContextProperty.name)
	}

	def protected checkAssemblyContextConcept(AssemblyContext pcmAssemblyContext) {
		val umlAssemblyContextProperty = helper.getModifiableCorr(pcmAssemblyContext, Property,
			TagLiterals.ASSEMBLY_CONTEXT__PROPERTY)
		checkAssemblyContextConcept(correspondenceModel, pcmAssemblyContext, umlAssemblyContextProperty)
		checkJavaAssemblyContextConcept(umlAssemblyContextProperty, pcmAssemblyContext)
	}

	def protected checkAssemblyContextConcept(Property umlAssemblyContextProperty) {
		val pcmAssemblyContext = helper.getModifiableCorr(umlAssemblyContextProperty, AssemblyContext,
			TagLiterals.ASSEMBLY_CONTEXT__PROPERTY)
		checkAssemblyContextConcept(correspondenceModel, pcmAssemblyContext, umlAssemblyContextProperty)
		checkJavaAssemblyContextConcept(umlAssemblyContextProperty, pcmAssemblyContext)
	}

	def protected checkJavaAssemblyContextConcept(Property umlProperty, AssemblyContext pcmAssemblyContext) {
		checkJavaAttribute(umlProperty) // check element that was actually created by test
		val pcmRepository = pcmAssemblyContext.encapsulatedComponent__AssemblyContext.repository__RepositoryComponent
		// Created before test cases, should be still there:
		val umlPackage = helper.getUmlRepositoryPackage(pcmRepository)
		checkJavaPackage(umlPackage)
		umlPackage.nestedPackages.forEach[checkJavaPackage]
		helper.getUmlComponentImpl(pcmRepository).checkJavaType
		helper.getUmlComponentImpl_2(pcmRepository).checkJavaType
	}

	/**
	 * Initialize a pcm::Repository with two CompositeComponents and synchronize them with their uml-counterparts.
	 */
	def private Repository createRepository_2Components() {

		val pcmRepository = helper.createRepository
		helper.createComponent(pcmRepository)
		helper.createComponent_2(pcmRepository)

		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		resourceAt(Path.of(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmRepository
		]
		propagate
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		return pcmRepository.clearResourcesAndReloadRoot
	}

	@Test
	def void testCreateAssemblyContextConcept_PCM() {
		var pcmRepository = createRepository_2Components()
		var pcmComponent = helper.getPcmComponent(pcmRepository)

		var pcmAssemblyContext = CompositionFactory.eINSTANCE.createAssemblyContext
		pcmAssemblyContext.entityName = PROPERTY_NAME
		// TODO setting the same component as container and encapsulated doesn't seem to trigger change event
		pcmAssemblyContext.encapsulatedComponent__AssemblyContext = helper.getPcmComponent_2(pcmRepository)
		pcmComponent.assemblyContexts__ComposedStructure += pcmAssemblyContext

		propagate
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		pcmAssemblyContext = helper.getPcmComponent(pcmRepository).assemblyContexts__ComposedStructure.head
		assertNotNull(pcmAssemblyContext)
		checkAssemblyContextConcept(pcmAssemblyContext)
	}

	@Test
	def void testCreateAssemblyContextConcept_UML() {
		var pcmRepository = createRepository_2Components()
		var umlComponent = helper.getUmlComponentImpl(pcmRepository)
		startRecordingChanges(umlComponent)

		var umlAssemblyContextProperty = umlComponent.createOwnedAttribute(PROPERTY_NAME,
			helper.getUmlComponentImpl_2(pcmRepository))

		propagate
		umlAssemblyContextProperty.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		umlAssemblyContextProperty = helper.getUmlComponentImpl(pcmRepository).ownedAttributes.findFirst [
			it.name == PROPERTY_NAME
		]
		assertNotNull(umlAssemblyContextProperty)
		checkAssemblyContextConcept(umlAssemblyContextProperty)
	}

}
