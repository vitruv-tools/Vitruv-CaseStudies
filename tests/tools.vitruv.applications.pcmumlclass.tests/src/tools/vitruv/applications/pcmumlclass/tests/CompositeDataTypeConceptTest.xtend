package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Class
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.TagLiterals
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

import static org.junit.jupiter.api.Assertions.assertFalse
import java.nio.file.Path

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::CompositeDataType in a pcm::Repository
 * with its corresponding uml::Class (implementation) in an uml::Package (the datatypes package corresponding to the repository).
 * <br><br>
 * Related files: 
 * 		PcmCompositeDataType.reactions,
 * 		UmlCompositeDataTypeClass.reactions, 
 * 		UmlCompositeDataTypeGeneralization.reactions
 */
class CompositeDataTypeConceptTest extends PcmUmlClassApplicationTest {

	static val TEST_COMPOSITE_DATATYPE = "TestCompositeType"
	static val TEST_COMPOSITE_DATATYPE_PARENT = "TestCompositeTypeParent"

	def checkCompositeDataTypeConcept(
		CompositeDataType pcmCompositeType,
		Class umlClass
	) {
		assertTrue(corresponds(pcmCompositeType, umlClass))
		assertTrue(pcmCompositeType.entityName == umlClass.name)
		// Repository should correspond to the datatypes package
		assertTrue(
			corresponds(pcmCompositeType.repository__DataType, umlClass.package,
				TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE))
		// check that parent compositedatatypes and parent classes correspond
		val umlParentCorrespondences = pcmCompositeType.parentType_CompositeDataType.map [ pcmParent |
			getCorrespondingEObjects(pcmParent, Class).head
		].toList
		assertFalse(umlParentCorrespondences.contains(null))
		assertFalse(
			umlParentCorrespondences.map [ umlParent |
				umlClass.generalizations.exists[gen|EcoreUtil.equals(gen.general, umlParent)]
			].exists[it == false]
		)
	}

	def protected checkCompositeDataTypeConcept(CompositeDataType pcmCompositeType) {
		val umlClass = helper.getModifiableCorr(pcmCompositeType, Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
		checkCompositeDataTypeConcept(pcmCompositeType, umlClass)
	}

	def protected checkCompositeDataTypeConcept(Class umlClass) {
		val pcmCompositeType = helper.getModifiableCorr(umlClass, CompositeDataType,
			TagLiterals.COMPOSITE_DATATYPE__CLASS)
		checkCompositeDataTypeConcept(pcmCompositeType, umlClass)
	}

	/**
	 * Initialize a pcm::Repository and its corresponding uml-counterparts.
	 */
	def private Repository createRepositoryConcept() {
		val pcmRepository = helper.createRepository

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
	def void testCreateCompositeDataTypeConcept_UML() {
		var pcmRepository = createRepositoryConcept()
		var umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)
		startRecordingChanges(umlDatatypesPkg)

		var umlCompositeTypeClass = umlDatatypesPkg.createOwnedClass(
			CompositeDataTypeConceptTest.TEST_COMPOSITE_DATATYPE, false)
		propagate

		umlDatatypesPkg.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)

		umlCompositeTypeClass = umlDatatypesPkg.packagedElements.findFirst [
			it.name == CompositeDataTypeConceptTest.TEST_COMPOSITE_DATATYPE
		] as Class
		assertNotNull(umlCompositeTypeClass)
		checkCompositeDataTypeConcept(umlCompositeTypeClass)
	}

	@Test
	def void testCreateCompositeDataTypeConcept_PCM() {
		var pcmRepository = createRepositoryConcept()
		var umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)

		var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
		pcmRepository.dataTypes__Repository += pcmCompositeType
		propagate

		umlDatatypesPkg.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)

		pcmCompositeType = pcmRepository.dataTypes__Repository.head as CompositeDataType
		assertNotNull(pcmCompositeType)
		checkCompositeDataTypeConcept(pcmCompositeType)
	}

	@Test
	def void testCreateCompositeDataType_withParent_UML() {
		var pcmRepository = createRepositoryConcept()
		var umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)
		startRecordingChanges(umlDatatypesPkg)

		var umlCompositeTypeClass = umlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE, false)
		var umlCompositeTypeParentClass = umlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE_PARENT, false)
		umlCompositeTypeClass.createGeneralization(umlCompositeTypeParentClass)
		propagate

		umlDatatypesPkg.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)

		umlCompositeTypeClass = umlDatatypesPkg.packagedElements.findFirst[it.name == TEST_COMPOSITE_DATATYPE] as Class
		assertNotNull(umlCompositeTypeClass)
		checkCompositeDataTypeConcept(umlCompositeTypeClass)
		umlCompositeTypeParentClass = umlDatatypesPkg.packagedElements.findFirst [
			it.name == TEST_COMPOSITE_DATATYPE_PARENT
		] as Class
		assertNotNull(umlCompositeTypeParentClass)
		checkCompositeDataTypeConcept(umlCompositeTypeParentClass)
	}

	@Test
	def void testCreateCompositeDataType_withParent_PCM() {
		var pcmRepository = createRepositoryConcept()

		var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
		pcmRepository.dataTypes__Repository += pcmCompositeType

		var pcmCompositeTypeParent = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeTypeParent.entityName = TEST_COMPOSITE_DATATYPE_PARENT
		pcmRepository.dataTypes__Repository += pcmCompositeTypeParent
		pcmCompositeType.parentType_CompositeDataType += pcmCompositeTypeParent
		propagate

		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		pcmCompositeType = pcmRepository.dataTypes__Repository.filter(CompositeDataType).findFirst [
			it.entityName == TEST_COMPOSITE_DATATYPE
		]
		assertNotNull(pcmCompositeType)
		checkCompositeDataTypeConcept(pcmCompositeType)
	}
}
