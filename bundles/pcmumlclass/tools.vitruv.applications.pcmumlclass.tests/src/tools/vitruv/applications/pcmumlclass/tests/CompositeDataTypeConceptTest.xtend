package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Package
import org.junit.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil

import static org.junit.Assert.*

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

    protected static val final Logger logger = Logger.getLogger(typeof(CompositeDataTypeConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	
	private static val TEST_COMPOSITE_DATATYPE = "TestCompositeType"
	private static val TEST_COMPOSITE_DATATYPE_PARENT = "TestCompositeTypeParent"
	
	def public static checkCompositeDataTypeConcept(CorrespondenceModel cm, 
			CompositeDataType pcmCompositeType, 
			Class umlClass
	){
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
	def protected checkCompositeDataTypeConcept(CompositeDataType pcmCompositeType){
		val umlClass = getModifiableCorr(pcmCompositeType, Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
		checkCompositeDataTypeConcept(correspondenceModel, pcmCompositeType, umlClass)
	}
	def protected checkCompositeDataTypeConcept(Class umlClass){
		val pcmCompositeType = getModifiableCorr(umlClass, CompositeDataType, TagLiterals.COMPOSITE_DATATYPE__CLASS)
		checkCompositeDataTypeConcept(correspondenceModel, pcmCompositeType, umlClass)
	}

	/**
	 * Initialize a pcm::Repository and its corresponding uml-counterparts.
	 */
	def private Repository createRepositoryConcept(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = "testCbsRepository"
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}
	
	def private Package getDatatypesPackage(Repository pcmRepository){
		return getModifiableCorr(pcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
	}

	@Test
//	@Ignore
	def void testCreateCompositeDataTypeConcept_UML() {
		var pcmRepository = createRepositoryConcept()
		var umlDatatypesPkg = getDatatypesPackage(pcmRepository)
		startRecordingChanges(umlDatatypesPkg)
		
		var umlCompositeTypeClass = umlDatatypesPkg.createOwnedClass(tools.vitruv.applications.pcmumlclass.tests.CompositeDataTypeConceptTest.TEST_COMPOSITE_DATATYPE, false)
		saveAndSynchronizeChanges(umlDatatypesPkg)
		
		reloadResourceAndReturnRoot(umlDatatypesPkg)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlDatatypesPkg = getDatatypesPackage(pcmRepository)
		
		umlCompositeTypeClass = umlDatatypesPkg.packagedElements.findFirst[it.name == tools.vitruv.applications.pcmumlclass.tests.CompositeDataTypeConceptTest.TEST_COMPOSITE_DATATYPE] as Class
		assertNotNull(umlCompositeTypeClass)
		checkCompositeDataTypeConcept(umlCompositeTypeClass)
	}
	
	@Test
	def void testCreateCompositeDataTypeConcept_PCM() {
		var pcmRepository = createRepositoryConcept()
		var umlDatatypesPkg = getDatatypesPackage(pcmRepository)

		var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
		pcmRepository.dataTypes__Repository += pcmCompositeType
		saveAndSynchronizeChanges(pcmCompositeType)
		
		reloadResourceAndReturnRoot(umlDatatypesPkg)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlDatatypesPkg = getDatatypesPackage(pcmRepository)
		
		pcmCompositeType = pcmRepository.dataTypes__Repository.head as CompositeDataType
		assertNotNull(pcmCompositeType)
		checkCompositeDataTypeConcept(pcmCompositeType)
	}
	
	@Test
	def void testCreateCompositeDataType_withParent_UML() {
		var pcmRepository = createRepositoryConcept()
		var umlDatatypesPkg = getDatatypesPackage(pcmRepository)
		startRecordingChanges(umlDatatypesPkg)
		
		var umlCompositeTypeClass = umlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE, false)
		var umlCompositeTypeParentClass = umlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE_PARENT, false)
		umlCompositeTypeClass.createGeneralization(umlCompositeTypeParentClass)
		saveAndSynchronizeChanges(umlDatatypesPkg)
		
		reloadResourceAndReturnRoot(umlDatatypesPkg)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		umlDatatypesPkg = getDatatypesPackage(pcmRepository)
		
		umlCompositeTypeClass = umlDatatypesPkg.packagedElements.findFirst[it.name == TEST_COMPOSITE_DATATYPE] as Class	
		assertNotNull(umlCompositeTypeClass)
		checkCompositeDataTypeConcept(umlCompositeTypeClass)
		umlCompositeTypeParentClass = umlDatatypesPkg.packagedElements.findFirst[it.name == TEST_COMPOSITE_DATATYPE_PARENT] as Class
		assertNotNull(umlCompositeTypeParentClass)
		checkCompositeDataTypeConcept(umlCompositeTypeParentClass)
	}
	
	@Test
	def void testCreateCompositeDataType_withParent_PCM() {
		var pcmRepository = createRepositoryConcept()

		var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
		pcmRepository.dataTypes__Repository += pcmCompositeType
		var mPcmCompositeTypeParent = RepositoryFactory.eINSTANCE.createCompositeDataType
		mPcmCompositeTypeParent.entityName = TEST_COMPOSITE_DATATYPE_PARENT
		pcmRepository.dataTypes__Repository += mPcmCompositeTypeParent
		pcmCompositeType.parentType_CompositeDataType += mPcmCompositeTypeParent
		saveAndSynchronizeChanges(pcmCompositeType)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		pcmCompositeType = pcmRepository.dataTypes__Repository.filter(CompositeDataType)
			.findFirst[it.entityName == TEST_COMPOSITE_DATATYPE] as CompositeDataType
		assertNotNull(pcmCompositeType)
		checkCompositeDataTypeConcept(pcmCompositeType)
	}
	
	
	
	
	
	
}
