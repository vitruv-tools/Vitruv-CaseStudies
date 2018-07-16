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

// A small 'm' prefix will signal that the eObject is loaded from the resourceSet an therefore modifiable.
// Working only on modifiable instances would theoretically allow for the comparison via identity.
// This would be cleaner for checking composition constraints, as equality [equals(target.container, source)] does not ensure the correct containment relation.
// For now stick with equality.

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

	def private Repository createRepositoryConcept(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var mPcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		mPcmRepository.entityName = "testCbsRepository"
		createAndSynchronizeModel(PCM_MODEL_FILE, mPcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(mPcmRepository) as Repository 
	}
	
	def private Package getDatatypesPackage(Repository mPcmRepository){
		return getModifiableCorr(mPcmRepository, Package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
	}

	@Test
//	@Ignore
	def void testCreateCompositeDataTypeConcept_UML() {
		var mPcmRepository = createRepositoryConcept()
		var mUmlDatatypesPkg = getDatatypesPackage(mPcmRepository)
		startRecordingChanges(mUmlDatatypesPkg)
		
		var mUmlCompositeTypeClass = mUmlDatatypesPkg.createOwnedClass(tools.vitruv.applications.pcmumlclass.tests.CompositeDataTypeConceptTest.TEST_COMPOSITE_DATATYPE, false)
		saveAndSynchronizeChanges(mUmlDatatypesPkg)
		
		reloadResourceAndReturnRoot(mUmlDatatypesPkg)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mUmlDatatypesPkg = getDatatypesPackage(mPcmRepository)
		
		mUmlCompositeTypeClass = mUmlDatatypesPkg.packagedElements.findFirst[it.name == tools.vitruv.applications.pcmumlclass.tests.CompositeDataTypeConceptTest.TEST_COMPOSITE_DATATYPE] as Class
		assertNotNull(mUmlCompositeTypeClass)
		checkCompositeDataTypeConcept(mUmlCompositeTypeClass)
	}
	
	@Test
	def void testCreateCompositeDataTypeConcept_PCM() {
		var mPcmRepository = createRepositoryConcept()
		var mUmlDatatypesPkg = getDatatypesPackage(mPcmRepository)
//		startRecordingChanges(mUmlDatatypesPkg)

		var mPcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		mPcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
		mPcmRepository.dataTypes__Repository += mPcmCompositeType
		saveAndSynchronizeChanges(mPcmCompositeType)
		
		reloadResourceAndReturnRoot(mUmlDatatypesPkg)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mUmlDatatypesPkg = getDatatypesPackage(mPcmRepository)
		
		mPcmCompositeType = mPcmRepository.dataTypes__Repository.head as CompositeDataType
		assertNotNull(mPcmCompositeType)
		checkCompositeDataTypeConcept(mPcmCompositeType)
	}
	
	@Test
	def void testCreateCompositeDataType_withParent_UML() {
		var mPcmRepository = createRepositoryConcept()
		var mUmlDatatypesPkg = getDatatypesPackage(mPcmRepository)
		startRecordingChanges(mUmlDatatypesPkg)
		
		var mUmlCompositeTypeClass = mUmlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE, false)
		var mUmlCompositeTypeParentClass = mUmlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE_PARENT, false)
		mUmlCompositeTypeClass.createGeneralization(mUmlCompositeTypeParentClass)
		saveAndSynchronizeChanges(mUmlDatatypesPkg)
		
		reloadResourceAndReturnRoot(mUmlDatatypesPkg)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mUmlDatatypesPkg = getDatatypesPackage(mPcmRepository)
		
		mUmlCompositeTypeClass = mUmlDatatypesPkg.packagedElements.findFirst[it.name == TEST_COMPOSITE_DATATYPE] as Class	
		assertNotNull(mUmlCompositeTypeClass)
		checkCompositeDataTypeConcept(mUmlCompositeTypeClass)
		mUmlCompositeTypeParentClass = mUmlDatatypesPkg.packagedElements.findFirst[it.name == TEST_COMPOSITE_DATATYPE_PARENT] as Class
		assertNotNull(mUmlCompositeTypeParentClass)
		checkCompositeDataTypeConcept(mUmlCompositeTypeParentClass)
	}
	
	@Test
	def void testCreateCompositeDataType_withParent_PCM() {
		var mPcmRepository = createRepositoryConcept()

		var mPcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		mPcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
		mPcmRepository.dataTypes__Repository += mPcmCompositeType
		var mPcmCompositeTypeParent = RepositoryFactory.eINSTANCE.createCompositeDataType
		mPcmCompositeTypeParent.entityName = TEST_COMPOSITE_DATATYPE_PARENT
		mPcmRepository.dataTypes__Repository += mPcmCompositeTypeParent
		mPcmCompositeType.parentType_CompositeDataType += mPcmCompositeTypeParent
		saveAndSynchronizeChanges(mPcmCompositeType)
		
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		
		mPcmCompositeType = mPcmRepository.dataTypes__Repository.filter(CompositeDataType)
			.findFirst[it.entityName == TEST_COMPOSITE_DATATYPE] as CompositeDataType
		assertNotNull(mPcmCompositeType)
		checkCompositeDataTypeConcept(mPcmCompositeType)
	}
	
	
	
	
	
	
}
