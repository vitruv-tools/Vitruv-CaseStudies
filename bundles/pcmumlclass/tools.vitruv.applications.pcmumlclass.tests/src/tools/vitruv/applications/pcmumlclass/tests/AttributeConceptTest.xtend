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
		if (pcmAttribute.datatype_InnerDeclaration ===null){
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



	def private Repository createRepositoryWithCompositeDataType(){
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var mPcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		mPcmRepository.entityName = "testCbsRepository"
		
		var mPcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		mPcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
		mPcmRepository.dataTypes__Repository += mPcmCompositeType
		
		createAndSynchronizeModel(PCM_MODEL_FILE, mPcmRepository)
		
		assertModelExists(PCM_MODEL_FILE)
		assertModelExists(UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(mPcmRepository) as Repository 
	}
	
	def private CompositeDataType getCompositeDatatype(Repository mPcmRepository){
		return mPcmRepository.dataTypes__Repository.filter(CompositeDataType)
			.findFirst[it.entityName == TEST_COMPOSITE_DATATYPE] as CompositeDataType
	}
	
	def private Class getCompositeDatatypeClass(Repository mPcmRepository){
		return getModifiableCorr(getCompositeDatatype(mPcmRepository), Class, TagLiterals.COMPOSITE_DATATYPE__CLASS)
	}

	@Test
	def void testCreateAttributeConcept_UML() {
		var mPcmRepository = createRepositoryWithCompositeDataType()
		var mUmlCompositeTypeClass = getCompositeDatatypeClass(mPcmRepository)
		startRecordingChanges(mUmlCompositeTypeClass)
		
		var mUmlAttribute = mUmlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, UML_INT)
		saveAndSynchronizeChanges(mUmlAttribute)
		
		reloadResourceAndReturnRoot(mUmlAttribute)
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mUmlCompositeTypeClass = getCompositeDatatypeClass(mPcmRepository)
		
		mUmlAttribute = mUmlCompositeTypeClass.ownedAttributes.findFirst[it.name == TEST_ATTRIBUTE]
		assertNotNull(mUmlAttribute)
		checkAttributeConcept(mUmlAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_PCM() {
		var mPcmRepository = createRepositoryWithCompositeDataType()
		var mPcmCompositeType = getCompositeDatatype(mPcmRepository)

		var mPcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		mPcmAttribute.entityName = TEST_ATTRIBUTE
		mPcmAttribute.datatype_InnerDeclaration = PCM_INT
		mPcmCompositeType.innerDeclaration_CompositeDataType += mPcmAttribute
		saveAndSynchronizeChanges(mPcmAttribute)
		
		mPcmRepository = reloadResourceAndReturnRoot(mPcmRepository) as Repository
		mPcmCompositeType = getCompositeDatatype(mPcmRepository)
		
		mPcmAttribute = mPcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(mPcmAttribute)
		checkAttributeConcept(mPcmAttribute)
	}

	// test compositeType and collection types
}
