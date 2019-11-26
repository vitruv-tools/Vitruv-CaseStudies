package tools.vitruv.applications.pcmumlclass.mapping.tests.cases

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Class
import org.junit.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassApplicationTestHelper
import tools.vitruv.applications.pcmumlclass.mapping.tests.PcmUmlClassTest
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil

import static org.junit.Assert.*
import org.junit.Ignore

class DatatypesTest extends PcmUmlClassTest{
		private static val TEST_COMPOSITE_DATATYPE = "TestCompositeType"
	private static val TEST_COMPOSITE_DATATYPE_PARENT = "TestCompositeTypeParent"
	
	def public static checkCompositeDataTypeConcept(CorrespondenceModel cm, 
			CompositeDataType pcmCompositeType, 
			Class umlClass
	) {
		assertTrue(corresponds(cm, pcmCompositeType, umlClass))
		assertTrue(pcmCompositeType.entityName == umlClass.name)
		//Repository should correspond to the datatypes package
		assertTrue(corresponds(cm, pcmCompositeType.repository__DataType, umlClass.package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE))
		// check that parent compositedatatypes and parent classes correspond
		val umlParentCorrespondences = pcmCompositeType.parentType_CompositeDataType
				.map[pcmParent | CorrespondenceModelUtil.getCorrespondingEObjectsByType(cm, pcmParent, Class).head].toList
		assertFalse(umlParentCorrespondences.contains(null))
		assertFalse(
			umlParentCorrespondences
				.map[umlParent | umlClass.generalizations.exists[gen | EcoreUtil.equals(gen.general, umlParent)]]
				.exists[it == false]
		)
	}
	def protected checkCompositeDataTypeConcept(CompositeDataType pcmCompositeType) {
		val umlClass = helper.getModifiableCorr(pcmCompositeType, Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
		assertNotNull(umlClass)
		checkCompositeDataTypeConcept(correspondenceModel, pcmCompositeType, umlClass)
	}
	def protected checkCompositeDataTypeConcept(Class umlClass) {
		val pcmCompositeType = helper.getModifiableCorr(umlClass, CompositeDataType, TagLiterals.COMPOSITE_DATATYPE__CLASS)
		assertNotNull(pcmCompositeType)
		checkCompositeDataTypeConcept(correspondenceModel, pcmCompositeType, umlClass)
	}

	/**
	 * Initialize a pcm::Repository and its corresponding uml-counterparts.
	 */
	def private Repository createRepositoryConcept() {
		val pcmRepository = helper.createRepository
		
		userInteractor.addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}

	@Test
	def void testCreateCompositeDataTypeConcept_UML() {
		var pcmRepository = createRepositoryConcept()
		var umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)
		startRecordingChanges(umlDatatypesPkg)
		
		var umlCompositeTypeClass = umlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE, false)
		saveAndSynchronizeChanges(umlDatatypesPkg)
		
		reloadResourceAndReturnRoot(umlDatatypesPkg)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
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
		saveAndSynchronizeChanges(pcmCompositeType)
		
		reloadResourceAndReturnRoot(umlDatatypesPkg)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)
		
		pcmCompositeType = pcmRepository.dataTypes__Repository.head as CompositeDataType
		assertNotNull(pcmCompositeType)
		checkCompositeDataTypeConcept(pcmCompositeType)
	}
	
	@Ignore("Not working properly yet")
	@Test
	def void testCreateCompositeDataType_withParent_UML() {
		var pcmRepository = createRepositoryConcept()
		var umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)
		startRecordingChanges(umlDatatypesPkg)
		
		var umlCompositeTypeClass = umlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE, false)
		var umlCompositeTypeParentClass = umlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE_PARENT, false)
		umlCompositeTypeClass.createGeneralization(umlCompositeTypeParentClass)
		saveAndSynchronizeChanges(umlDatatypesPkg)
		
		reloadResourceAndReturnRoot(umlDatatypesPkg)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository)
		
		umlCompositeTypeClass = umlDatatypesPkg.packagedElements.findFirst[it.name == TEST_COMPOSITE_DATATYPE] as Class	
		assertNotNull(umlCompositeTypeClass)
		checkCompositeDataTypeConcept(umlCompositeTypeClass)
		umlCompositeTypeParentClass = umlDatatypesPkg.packagedElements.findFirst[it.name == TEST_COMPOSITE_DATATYPE_PARENT] as Class
		assertNotNull(umlCompositeTypeParentClass)
		checkCompositeDataTypeConcept(umlCompositeTypeParentClass)
	}
	
	@Ignore("Not working properly yet")
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
		saveAndSynchronizeChanges(pcmCompositeType)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		pcmCompositeType = pcmRepository.dataTypes__Repository.filter(CompositeDataType)
			.findFirst[it.entityName == TEST_COMPOSITE_DATATYPE] as CompositeDataType
		assertNotNull(pcmCompositeType)
		checkCompositeDataTypeConcept(pcmCompositeType)
	}
}