package tools.vitruv.applications.pcmumlclassjava.tests

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Type
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.applications.pcmumlclass.tests.PcmUmlClassApplicationTestHelper
import tools.vitruv.applications.pcmumlclassjava.LinearTransitiveChangeTest
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.jupiter.api.Assertions.*
import java.nio.file.Path

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::InnerDeclaration with 
 * its corresponding uml::Property and test the propagation of type and multiplicity changes.
 * <br><br>
 * Related files: PcmInnerDeclaration.reactions, UmlInnerDeclarationProperty.reactions 
 */
class AttributeConceptTest extends LinearTransitiveChangeTest {

	static val TEST_ATTRIBUTE = "testAttribute"

	def static void checkAttributeConcept(
		CorrespondenceModel cm,
		InnerDeclaration pcmAttribute,
		Property umlAttribute
	) {
		assertNotNull(pcmAttribute)
		assertNotNull(umlAttribute)
		assertTrue(corresponds(cm, pcmAttribute, umlAttribute, TagLiterals.INNER_DECLARATION__PROPERTY))
		assertTrue(pcmAttribute.entityName == umlAttribute.name)
		// parent CompositeType should correspond to parent uml::Class
		assertTrue(
			corresponds(cm, pcmAttribute.compositeDataType_InnerDeclaration, umlAttribute.class_,
				TagLiterals.COMPOSITE_DATATYPE__CLASS))
		// types should correspond
		assertTrue(isCorrect_DataType_Property_Correspondence(cm, pcmAttribute.datatype_InnerDeclaration, umlAttribute))
	}

	def protected checkAttributeConcept(InnerDeclaration pcmAttribute) {
		val umlAttribute = helper.getModifiableCorr(pcmAttribute, Property, TagLiterals.INNER_DECLARATION__PROPERTY)
		checkAttributeConcept(correspondenceModel, pcmAttribute, umlAttribute)
		checkJavaAttribute(umlAttribute)
	}

	def protected checkAttributeConcept(Property umlAttribute) {
		val pcmAttribute = helper.getModifiableCorr(umlAttribute, InnerDeclaration,
			TagLiterals.INNER_DECLARATION__PROPERTY)
		checkAttributeConcept(correspondenceModel, pcmAttribute, umlAttribute)
		checkJavaAttribute(umlAttribute)
	}

	def private Repository createRepository() {
		val pcmRepository = helper.createRepository()
		helper.createCompositeDataType(pcmRepository)
		val pcmCompositeType_2 = helper.createCompositeDataType_2(pcmRepository)
		helper.createCollectionDataType(pcmRepository, pcmCompositeType_2)
		userInteraction.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		userInteraction.addNextSingleSelection(ARRAY_LIST_SELECTION) // Mock user input
		resourceAt(Path.of(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmRepository
		]
		propagate
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		return pcmRepository.clearResourcesAndReloadRoot
	}

	private def void testCreateAttributeConcept_PCM(Repository inPcmRepository, DataType pcmType) {
		var pcmRepository = inPcmRepository
		var pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository)

		var pcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmAttribute.entityName = TEST_ATTRIBUTE
		pcmAttribute.datatype_InnerDeclaration = pcmType
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmAttribute
		userInteraction.addNextSingleSelection(ARRAY_LIST_SELECTION) // Mock user input
		propagate
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository)

		pcmAttribute = pcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(pcmAttribute)
		checkAttributeConcept(pcmAttribute)
		val reloadedPcmType = helper.getModifiableInstance(pcmType)
		assertNotNull(reloadedPcmType, "The DataType should not be null after reload")
		assertTrue(EcoreUtil.equals(pcmAttribute.datatype_InnerDeclaration, reloadedPcmType))
	}

	@Test
	def void testCreateAttributeConcept_PCM_primitiveType() {
		var pcmRepository = createRepository()
		assertNotNull(helper.PCM_INT, "Initialization of PrimitiveTypes seems to have failed")
		testCreateAttributeConcept_PCM(pcmRepository, helper.PCM_INT)
	}

	@Test
	def void testCreateAttributeConcept_PCM_compositeType() {
		var pcmRepository = createRepository()
		// TODO innerDeclaration with same type as outer CompositeDataType doesn't trigger change event
		testCreateAttributeConcept_PCM(pcmRepository, helper.getPcmCompositeDataType_2(pcmRepository))
	}

	@Test
	def void testCreateAttributeConcept_PCM_collectionType() {
		var pcmRepository = createRepository()
		testCreateAttributeConcept_PCM(pcmRepository, helper.getPcmCollectionDataType(pcmRepository))
	}

	private def void testCreateAttributeConcept_UML(Repository inPcmRepository, Type umlType, int lower, int upper) {
		var pcmRepository = inPcmRepository
		var umlCompositeTypeClass = helper.getUmlCompositeDataTypeClass(pcmRepository)
		startRecordingChanges(umlCompositeTypeClass)

		var umlAttribute = umlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, null)
		// CollectionType propagation only works if the typeSet is propagated first or last, 
		// otherwise it will overwrite one of the multiplicity changes on the back-propagation (pcm -> uml).
		// The failure is explicitly produced here to show case the problem as long as it exists.
		umlAttribute.lower = lower
		umlAttribute.type = umlType
		umlAttribute.upper = upper

		propagate
		umlAttribute.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		umlAttribute = helper.getUmlCompositeDataTypeClass(pcmRepository).ownedAttributes.findFirst [
			it.name == TEST_ATTRIBUTE
		]
		assertNotNull(umlAttribute)
		checkAttributeConcept(umlAttribute)

		val reloadedUmlType = helper.getModifiableInstance(umlType)
		assertNotNull(reloadedUmlType, "The DataType should not be null after reload")
		assertTrue(EcoreUtil.equals(umlAttribute.type, reloadedUmlType))
		assertTrue(umlAttribute.lower == lower)
		assertTrue(umlAttribute.upper == upper)
	}

	@Test
	def void testCreateAttributeConcept_UML_primitiveType() {
		var pcmRepository = createRepository()
		assertNotNull(helper.UML_INT, "Initialization of PrimitiveTypes seems to have failed")
		testCreateAttributeConcept_UML(pcmRepository, helper.UML_INT, 1, 1)
	}

	@Test
	def void testCreateAttributeConcept_UML_compositeType() {
		var pcmRepository = createRepository()
		testCreateAttributeConcept_UML(pcmRepository, helper.getUmlCompositeDataTypeClass(pcmRepository), 1, 1)
	}

}
