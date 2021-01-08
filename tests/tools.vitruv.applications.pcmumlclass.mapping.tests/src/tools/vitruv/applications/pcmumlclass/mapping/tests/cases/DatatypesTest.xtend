package tools.vitruv.applications.pcmumlclass.mapping.tests.cases

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Class
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals
import static tools.vitruv.applications.pcmumlclass.mapping.DefaultLiterals.*
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassTest
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import java.nio.file.Path

class DatatypesTest extends PcmUmlClassTest {
	static val TEST_COMPOSITE_DATATYPE = "TestCompositeType"
	static val TEST_COMPOSITE_DATATYPE_PARENT = "TestCompositeTypeParent"

	def static checkCompositeDataTypeConcept(
		CorrespondenceModel cm,
		CompositeDataType pcmCompositeType,
		Class umlClass
	) {
		assertTrue(corresponds(cm, pcmCompositeType, umlClass))
		assertTrue(pcmCompositeType.entityName == umlClass.name)
		// Repository should correspond to the datatypes package
		assertTrue(
			corresponds(cm, pcmCompositeType.repository__DataType, umlClass.package,
				TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE))
		// check that parent compositedatatypes and parent classes correspond
		val umlParentCorrespondences = pcmCompositeType.parentType_CompositeDataType.map [ pcmParent |
			CorrespondenceModelUtil.getCorrespondingEObjectsByType(cm, pcmParent, Class).head
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
		assertNotNull(umlClass)
		checkCompositeDataTypeConcept(correspondenceModel, pcmCompositeType, umlClass)
	}

	def protected checkCompositeDataTypeConcept(Class umlClass) {
		val pcmCompositeType = helper.getModifiableCorr(umlClass, CompositeDataType,
			TagLiterals.COMPOSITE_DATATYPE__CLASS)
		assertNotNull(pcmCompositeType)
		checkCompositeDataTypeConcept(correspondenceModel, pcmCompositeType, umlClass)
	}

	/**
	 * Initialize a pcm::Repository and its corresponding uml-counterparts.
	 */
	def private Repository createRepositoryConcept() {
		val pcmRepository = helper.createRepository

		userInteraction.addNextTextInput(UML_MODEL_FILE)
		resourceAt(Path.of(PCM_MODEL_FILE)).startRecordingChanges => [
			contents += pcmRepository
		]
		propagate
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return pcmRepository.clearResourcesAndReloadRoot
	}

	@Test
	def void testCreateCompositeDataTypeConcept_UML() {
		var pcmRepository = createRepositoryConcept()
		var umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)
		startRecordingChanges(umlDatatypesPkg)

		var umlCompositeTypeClass = umlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE, false)
		propagate

		umlDatatypesPkg.clearResourcesAndReloadRoot
		pcmRepository = pcmRepository.clearResourcesAndReloadRoot
		umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)

		umlCompositeTypeClass = umlDatatypesPkg.packagedElements.findFirst[it.name == TEST_COMPOSITE_DATATYPE] as Class
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

	@Disabled("Not working properly yet")
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

	@Disabled("Not working properly yet")
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
