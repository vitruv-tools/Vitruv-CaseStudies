package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Class
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmUserSelection
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.applications.pcmumlclass.tests.PcmUmlClassApplicationTestHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil

import static org.junit.jupiter.api.Assertions.*
import java.nio.file.Path
import org.eclipse.uml2.uml.VisibilityKind

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::CompositeDataType in a pcm::Repository
 * with its corresponding uml::Class (implementation) in an uml::Package (the datatypes package corresponding to the repository).
 * <br><br>
 * Related files: 
 * 		PcmCompositeDataType.reactions,
 * 		UmlCompositeDataTypeClass.reactions, 
 * 		UmlCompositeDataTypeGeneralization.reactions
 */
class CompositeDataTypeConceptTest extends PcmUmlJavaTransitiveChangeTest {

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
		val umlClass = helper.getCorr(pcmCompositeType, Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
		checkCompositeDataTypeConcept(correspondenceModel, pcmCompositeType, umlClass)
	}

	def protected checkCompositeDataTypeConcept(Class umlClass) {
		val pcmCompositeType = helper.getCorr(umlClass, CompositeDataType,
			TagLiterals.COMPOSITE_DATATYPE__CLASS)
		checkCompositeDataTypeConcept(correspondenceModel, pcmCompositeType, umlClass)
	}

	def protected checkJavaCompositeDataTypeConcept(Class umlClass, CompositeDataType pcmCompositeType) {
		checkJavaType(umlClass)
		umlClass.generalizations.forEach[it|it.checkJavaGeneralization]
		umlClass.generalizations.forEach[it|it.general.checkJavaType]
		umlClass.generalizations.forEach[it|it.specific.checkJavaType]
		// Created before test cases, should be still there:
		val umlPackage = helper.getUmlRepositoryPackage(pcmCompositeType.repository__DataType)
		umlPackage.checkJavaPackage
		umlPackage.nestedPackages.forEach[checkJavaPackage]
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
		umlCompositeTypeClass.visibility = VisibilityKind.PUBLIC_LITERAL
		userInteraction.addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.selection)
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
		userInteraction.addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.selection)
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
		umlCompositeTypeClass.visibility = VisibilityKind.PUBLIC_LITERAL
		var umlCompositeTypeParentClass = umlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE_PARENT, false)
		umlCompositeTypeParentClass.visibility = VisibilityKind.PUBLIC_LITERAL
		umlCompositeTypeClass.createGeneralization(umlCompositeTypeParentClass)
		userInteraction.addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.selection)
		userInteraction.addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.selection)
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
		userInteraction.addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.selection)
		userInteraction.addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.selection)
		propagate

		pcmRepository = pcmRepository.clearResourcesAndReloadRoot

		pcmCompositeType = pcmRepository.dataTypes__Repository.filter(CompositeDataType).findFirst [
			it.entityName == TEST_COMPOSITE_DATATYPE
		]
		assertNotNull(pcmCompositeType)
		checkCompositeDataTypeConcept(pcmCompositeType)
	}
}
