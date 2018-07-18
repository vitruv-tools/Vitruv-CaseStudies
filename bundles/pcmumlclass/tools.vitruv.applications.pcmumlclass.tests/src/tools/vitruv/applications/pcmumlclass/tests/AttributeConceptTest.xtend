package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Property
import org.junit.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.palladiosimulator.pcm.repository.DataType
import org.junit.Ignore
import org.palladiosimulator.pcm.repository.CollectionDataType

// A small 'm' prefix will signal that the eObject is loaded from the resourceSet an therefore modifiable.
// Working only on modifiable instances would theoretically allow for the comparison via identity.
// This would be cleaner for checking composition constraints, as equality [equals(target.container, source)] does not ensure the correct containment relation.
// For now stick with equality.

class AttributeConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(AttributeConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/Repository.repository"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	
	private static val TEST_COMPOSITE_DATATYPE = "TestCompositeType"
	private static val TEST_COMPOSITE_DATATYPE_2 = "TestCompositeType_2"
	private static val TEST_COLLECTION_DATATYPE = "TestCollectionType"
	private static val TEST_ATTRIBUTE = "testAttribute"
	
	def public static void checkAttributeConcept(CorrespondenceModel cm, 
			InnerDeclaration pcmAttribute, 
			Property umlAttribute
	){
		assertNotNull(pcmAttribute)
		assertNotNull(umlAttribute)
		assertTrue(corresponds(cm, pcmAttribute, umlAttribute, TagLiterals.INNER_DECLARATION__PROPERTY))
		assertTrue(pcmAttribute.entityName == umlAttribute.name)
			//parent CompositeType should correspond to parent uml::Class
		assertTrue(corresponds(cm, pcmAttribute.compositeDataType_InnerDeclaration, umlAttribute.class_, TagLiterals.COMPOSITE_DATATYPE__CLASS))
			//types should correspond
		if (pcmAttribute.datatype_InnerDeclaration === null || umlAttribute.type === null){
			assertTrue(pcmAttribute.datatype_InnerDeclaration === null && umlAttribute.type === null)
		}
		else {
			val correspondingPrimitiveType = corresponds(cm, pcmAttribute.datatype_InnerDeclaration, umlAttribute.type, TagLiterals.DATATYPE__TYPE)
			val correspondingCompositeType = corresponds(cm, pcmAttribute.datatype_InnerDeclaration, umlAttribute.type, TagLiterals.COMPOSITE_DATATYPE__CLASS)
			val correspondingCollectionType = 
				corresponds(cm, pcmAttribute.datatype_InnerDeclaration, umlAttribute, TagLiterals.COLLECTION_DATATYPE__PROPERTY)
				&& umlAttribute.lower == 0 && umlAttribute.upper == LiteralUnlimitedNatural.UNLIMITED
			assertTrue(correspondingPrimitiveType || correspondingCompositeType || correspondingCollectionType)
		}
	}
	def protected checkAttributeConcept(InnerDeclaration pcmAttribute){
		val umlAttribute = getModifiableCorr(pcmAttribute, Property, TagLiterals.INNER_DECLARATION__PROPERTY)
		checkAttributeConcept(correspondenceModel, pcmAttribute, umlAttribute)
	}
	def protected checkAttributeConcept(Property umlAttribute){
		val pcmAttribute = getModifiableCorr(umlAttribute, InnerDeclaration, TagLiterals.INNER_DECLARATION__PROPERTY)
		checkAttributeConcept(correspondenceModel, pcmAttribute, umlAttribute)
	}



	def private Repository createRepository_CompositeDataType_CompositeDataType2(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository.entityName = "testCbsRepository"
		
		var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
		pcmRepository.dataTypes__Repository += pcmCompositeType
		
		var pcmCompositeType_2 = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmCompositeType_2.entityName = TEST_COMPOSITE_DATATYPE_2
		pcmRepository.dataTypes__Repository += pcmCompositeType_2
		
		var pcmCollectionType = RepositoryFactory.eINSTANCE.createCollectionDataType
		pcmCollectionType.entityName = TEST_COLLECTION_DATATYPE
		pcmCollectionType.innerType_CollectionDataType = pcmCompositeType_2
		pcmRepository.dataTypes__Repository += pcmCollectionType
		
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}
	
	def private CompositeDataType getPcmCompositeDatatype(Repository mPcmRepository){
		return mPcmRepository.dataTypes__Repository.filter(CompositeDataType)
			.findFirst[it.entityName == TEST_COMPOSITE_DATATYPE] as CompositeDataType
	}
	
	def private Class getUmlCompositeDatatypeClass(Repository mPcmRepository){
		return getModifiableCorr(getPcmCompositeDatatype(mPcmRepository), Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
	}

	def private CompositeDataType getPcmCompositeDatatype_2(Repository mPcmRepository){
		return mPcmRepository.dataTypes__Repository.filter(CompositeDataType)
			.findFirst[it.entityName == TEST_COMPOSITE_DATATYPE_2] as CompositeDataType
	}
	
	def private Class getUmlCompositeDatatypeClass_2(Repository mPcmRepository){
		return getModifiableCorr(getPcmCompositeDatatype_2(mPcmRepository), Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
	}
	
	def private getPcmCollectionDatatype(Repository mPcmRepository){
		return mPcmRepository.dataTypes__Repository.filter(CollectionDataType)
			.findFirst[it.entityName == TEST_COLLECTION_DATATYPE] 
	}
	
	def private getUmlCollectiondatatype_Property(Repository mPcmRepository){
		return getModifiableCorr(getPcmCollectionDatatype(mPcmRepository), Class, TagLiterals.COLLECTION_DATATYPE__PROPERTY)
	}

//	For some reason, Primitive(Data)Type [pcm::PDT/uml::PT] correspondences can only be resolved one/in one direction. Every round-trip fails:
//		PCM 							-- 				UML
//		PDT (id=960) ~ PT (id=1003) 	==> 	PT (id=1003)
//			null						<==		PT (id=1003) ~ null 	// no correspondence found
//			null 						==> 		null				
//			null 						<== 		null				// terminates with wrong result		
// --> Exception: Could not save VURI for UMLPrimitiveTypes.library.uml (read-only path)		


	@Test
	@Ignore
	def void testCreateAttributeConcept_PCM_primitiveType() {
		var mPcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var mPcmCompositeType = getPcmCompositeDatatype(mPcmRepository)

		var mPcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		mPcmAttribute.entityName = TEST_ATTRIBUTE
		mPcmAttribute.datatype_InnerDeclaration = PCM_INT
		mPcmCompositeType.innerDeclaration_CompositeDataType += mPcmAttribute
		saveAndSynchronizeChanges(mPcmAttribute)
		
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mPcmCompositeType = getPcmCompositeDatatype(mPcmRepository)
		
		mPcmAttribute = mPcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(mPcmAttribute)
		checkAttributeConcept(mPcmAttribute)
	}
	
	@Test
	@Ignore
	def void testCreateAttributeConcept_UML_primitiveType() {
		var mPcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var mUmlCompositeTypeClass = getUmlCompositeDatatypeClass(mPcmRepository)
		startRecordingChanges(mUmlCompositeTypeClass)
		
		var mUmlAttribute = mUmlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, UML_INT)
		
		saveAndSynchronizeChanges(mUmlAttribute)
		reloadResourceAndReturnRoot(mUmlAttribute)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		
		mUmlAttribute = getUmlCompositeDatatypeClass(mPcmRepository).ownedAttributes.findFirst[it.name == TEST_ATTRIBUTE]
		assertNotNull(mUmlAttribute)
		checkAttributeConcept(mUmlAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_PCM_compositeType() {
		var mPcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var mPcmCompositeType = getPcmCompositeDatatype(mPcmRepository)

		var mPcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		mPcmAttribute.entityName = TEST_ATTRIBUTE
		mPcmAttribute.datatype_InnerDeclaration = getPcmCompositeDatatype_2(mPcmRepository)
		mPcmCompositeType.innerDeclaration_CompositeDataType += mPcmAttribute
		saveAndSynchronizeChanges(mPcmAttribute)
		
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mPcmCompositeType = getPcmCompositeDatatype(mPcmRepository)
		
		mPcmAttribute = mPcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(mPcmAttribute)
		checkAttributeConcept(mPcmAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_UML_compositeType() {
		var mPcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var mUmlCompositeTypeClass = getUmlCompositeDatatypeClass(mPcmRepository)
		startRecordingChanges(mUmlCompositeTypeClass)
		
		var mUmlAttribute = mUmlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, getUmlCompositeDatatypeClass_2(mPcmRepository))
		
		saveAndSynchronizeChanges(mUmlAttribute)
		reloadResourceAndReturnRoot(mUmlAttribute)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		
		mUmlAttribute = getUmlCompositeDatatypeClass(mPcmRepository).ownedAttributes.findFirst[it.name == TEST_ATTRIBUTE]
		assertNotNull(mUmlAttribute)
		checkAttributeConcept(mUmlAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_PCM_collectionType() {
		var mPcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var mPcmCompositeType = getPcmCompositeDatatype(mPcmRepository)

		var mPcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		mPcmAttribute.entityName = TEST_ATTRIBUTE
		mPcmAttribute.datatype_InnerDeclaration = getPcmCollectionDatatype(mPcmRepository)
		mPcmCompositeType.innerDeclaration_CompositeDataType += mPcmAttribute
		saveAndSynchronizeChanges(mPcmAttribute)
		
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mPcmCompositeType = getPcmCompositeDatatype(mPcmRepository)
		
		mPcmAttribute = mPcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(mPcmAttribute)
		checkAttributeConcept(mPcmAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_UML_collectionType() {
		var mPcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var mUmlCompositeTypeClass = getUmlCompositeDatatypeClass(mPcmRepository)
		startRecordingChanges(mUmlCompositeTypeClass)
		
		var mUmlAttribute = mUmlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, getUmlCompositeDatatypeClass_2(mPcmRepository))
		mUmlAttribute.lower = 0
		mUmlAttribute.upper = LiteralUnlimitedNatural.UNLIMITED
		
		saveAndSynchronizeChanges(mUmlAttribute)
		reloadResourceAndReturnRoot(mUmlAttribute)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		
		mUmlAttribute = getUmlCompositeDatatypeClass(mPcmRepository).ownedAttributes.findFirst[it.name == TEST_ATTRIBUTE]
		assertNotNull(mUmlAttribute)
		assertTrue(mUmlAttribute.upper == LiteralUnlimitedNatural.UNLIMITED)
		checkAttributeConcept(mUmlAttribute)
	}
	
	// TODO test compositeType and collection types
}
