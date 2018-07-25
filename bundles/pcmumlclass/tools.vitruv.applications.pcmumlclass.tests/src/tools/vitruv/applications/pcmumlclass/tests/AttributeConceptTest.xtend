package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Property
import org.junit.Ignore
import org.junit.Test
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::InnerDeclaration with 
 * its corresponding uml::Property and test the propagation of type and multiplicity changes.
 * <br><br>
 * Related files: PcmInnerDeclaration.reactions, UmlInnerDeclarationProperty.reactions 
 */
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
		assertTrue(isCorrect_DataType_Property_Correspondence(cm, pcmAttribute.datatype_InnerDeclaration, umlAttribute))
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
	
	def private CompositeDataType getPcmCompositeDatatype(Repository mcmRepository){
		return mcmRepository.dataTypes__Repository.filter(CompositeDataType)
			.findFirst[it.entityName == TEST_COMPOSITE_DATATYPE] as CompositeDataType
	}
	
	def private Class getUmlCompositeDatatypeClass(Repository mcmRepository){
		return getModifiableCorr(getPcmCompositeDatatype(mcmRepository), Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
	}

	def private CompositeDataType getPcmCompositeDatatype_2(Repository mcmRepository){
		return mcmRepository.dataTypes__Repository.filter(CompositeDataType)
			.findFirst[it.entityName == TEST_COMPOSITE_DATATYPE_2] as CompositeDataType
	}
	
	def private Class getUmlCompositeDatatypeClass_2(Repository mcmRepository){
		return getModifiableCorr(getPcmCompositeDatatype_2(mcmRepository), Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
	}
	
	def private getPcmCollectionDatatype(Repository mcmRepository){
		return mcmRepository.dataTypes__Repository.filter(CollectionDataType)
			.findFirst[it.entityName == TEST_COLLECTION_DATATYPE] 
	}
	
	def private getUmlCollectiondatatype_Property(Repository mcmRepository){
		return getModifiableCorr(getPcmCollectionDatatype(mcmRepository), Class, TagLiterals.COLLECTION_DATATYPE__PROPERTY)
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
		var pcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var pcmCompositeType = getPcmCompositeDatatype(pcmRepository)

		var pcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmAttribute.entityName = TEST_ATTRIBUTE
		pcmAttribute.datatype_InnerDeclaration = PCM_INT
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmAttribute
		saveAndSynchronizeChanges(pcmAttribute)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmCompositeType = getPcmCompositeDatatype(pcmRepository)
		
		pcmAttribute = pcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(pcmAttribute)
		checkAttributeConcept(pcmAttribute)
	}
	
	@Test
	@Ignore
	def void testCreateAttributeConcept_UML_primitiveType() {
		var pcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var umlCompositeTypeClass = getUmlCompositeDatatypeClass(pcmRepository)
		startRecordingChanges(umlCompositeTypeClass)
		
		var umlAttribute = umlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, UML_INT)
		
		saveAndSynchronizeChanges(umlAttribute)
		reloadResourceAndReturnRoot(umlAttribute)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlAttribute = getUmlCompositeDatatypeClass(pcmRepository).ownedAttributes.findFirst[it.name == TEST_ATTRIBUTE]
		assertNotNull(umlAttribute)
		checkAttributeConcept(umlAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_PCM_compositeType() {
		var pcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var pcmCompositeType = getPcmCompositeDatatype(pcmRepository)

		var pcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmAttribute.entityName = TEST_ATTRIBUTE
		pcmAttribute.datatype_InnerDeclaration = getPcmCompositeDatatype_2(pcmRepository)
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmAttribute
		saveAndSynchronizeChanges(pcmAttribute)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmCompositeType = getPcmCompositeDatatype(pcmRepository)
		
		pcmAttribute = pcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(pcmAttribute)
		checkAttributeConcept(pcmAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_UML_compositeType() {
		var pcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var umlCompositeTypeClass = getUmlCompositeDatatypeClass(pcmRepository)
		startRecordingChanges(umlCompositeTypeClass)
		
		var umlAttribute = umlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, getUmlCompositeDatatypeClass_2(pcmRepository))
		
		saveAndSynchronizeChanges(umlAttribute)
		reloadResourceAndReturnRoot(umlAttribute)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlAttribute = getUmlCompositeDatatypeClass(pcmRepository).ownedAttributes.findFirst[it.name == TEST_ATTRIBUTE]
		assertNotNull(umlAttribute)
		checkAttributeConcept(umlAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_PCM_collectionType() {
		var pcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var pcmCompositeType = getPcmCompositeDatatype(pcmRepository)

		var pcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmAttribute.entityName = TEST_ATTRIBUTE
		pcmAttribute.datatype_InnerDeclaration = getPcmCollectionDatatype(pcmRepository)
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmAttribute
		saveAndSynchronizeChanges(pcmAttribute)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmCompositeType = getPcmCompositeDatatype(pcmRepository)
		
		pcmAttribute = pcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(pcmAttribute)
		checkAttributeConcept(pcmAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_UML_collectionType() {
		var pcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var umlCompositeTypeClass = getUmlCompositeDatatypeClass(pcmRepository)
		startRecordingChanges(umlCompositeTypeClass)
		
		var umlAttribute = umlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, getUmlCompositeDatatypeClass_2(pcmRepository))
		umlAttribute.lower = 0
		umlAttribute.upper = LiteralUnlimitedNatural.UNLIMITED
		
		saveAndSynchronizeChanges(umlAttribute)
		reloadResourceAndReturnRoot(umlAttribute)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlAttribute = getUmlCompositeDatatypeClass(pcmRepository).ownedAttributes.findFirst[it.name == TEST_ATTRIBUTE]
		assertNotNull(umlAttribute)
		assertTrue(umlAttribute.upper == LiteralUnlimitedNatural.UNLIMITED)
		checkAttributeConcept(umlAttribute)
	}
	
}
