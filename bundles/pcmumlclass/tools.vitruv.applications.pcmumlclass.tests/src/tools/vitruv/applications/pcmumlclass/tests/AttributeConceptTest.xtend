package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Property
import org.junit.Ignore
import org.junit.Test
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
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
		val umlAttribute = helper.getModifiableCorr(pcmAttribute, Property, TagLiterals.INNER_DECLARATION__PROPERTY)
		checkAttributeConcept(correspondenceModel, pcmAttribute, umlAttribute)
	}
	def protected checkAttributeConcept(Property umlAttribute){
		val pcmAttribute = helper.getModifiableCorr(umlAttribute, InnerDeclaration, TagLiterals.INNER_DECLARATION__PROPERTY)
		checkAttributeConcept(correspondenceModel, pcmAttribute, umlAttribute)
	}



	def private Repository createRepository_CompositeDataType_CompositeDataType2(){
		
		val pcmRepository = helper.createRepository()
		helper.createCompositeDataType(pcmRepository)
		val pcmCompositeType_2 = helper.createCompositeDataType_2(pcmRepository)
		helper.createCollectionDataType(pcmRepository, pcmCompositeType_2)
		
		userInteractor.addNextSelections(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)
		createAndSynchronizeModel(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE, pcmRepository)
		assertModelExists(PcmUmlClassApplicationTestHelper.PCM_MODEL_FILE)
		assertModelExists(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE)

		return reloadResourceAndReturnRoot(pcmRepository) as Repository 
	}
	
//	TODO For some reason, Primitive(Data)Type [pcm::PDT/uml::PT] correspondences can only be resolved once/in one direction. Every round-trip fails:
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
		var pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository)

		var pcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmAttribute.entityName = TEST_ATTRIBUTE
		pcmAttribute.datatype_InnerDeclaration = helper.PCM_INT
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmAttribute
		saveAndSynchronizeChanges(pcmAttribute)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository)
		
		pcmAttribute = pcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(pcmAttribute)
		checkAttributeConcept(pcmAttribute)
	}
	
	@Test
	@Ignore
	def void testCreateAttributeConcept_UML_primitiveType() {
		var pcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var umlCompositeTypeClass = helper.getUmlCompositeDataTypeClass(pcmRepository)
		startRecordingChanges(umlCompositeTypeClass)
		
		var umlAttribute = umlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, helper.UML_INT)
		
		saveAndSynchronizeChanges(umlAttribute)
		reloadResourceAndReturnRoot(umlAttribute)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlAttribute = helper.getUmlCompositeDataTypeClass(pcmRepository).ownedAttributes.findFirst[it.name == TEST_ATTRIBUTE]
		assertNotNull(umlAttribute)
		checkAttributeConcept(umlAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_PCM_compositeType() {
		var pcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository)

		var pcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmAttribute.entityName = TEST_ATTRIBUTE
		pcmAttribute.datatype_InnerDeclaration = helper.getPcmCompositeDataType_2(pcmRepository)
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmAttribute
		saveAndSynchronizeChanges(pcmAttribute)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository)
		
		pcmAttribute = pcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(pcmAttribute)
		checkAttributeConcept(pcmAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_UML_compositeType() {
		var pcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var umlCompositeTypeClass = helper.getUmlCompositeDataTypeClass(pcmRepository)
		startRecordingChanges(umlCompositeTypeClass)
		
		var umlAttribute = umlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, helper.getUmlCompositeDataTypeClass_2(pcmRepository))
		
		saveAndSynchronizeChanges(umlAttribute)
		reloadResourceAndReturnRoot(umlAttribute)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlAttribute = helper.getUmlCompositeDataTypeClass(pcmRepository).ownedAttributes.findFirst[it.name == TEST_ATTRIBUTE]
		assertNotNull(umlAttribute)
		checkAttributeConcept(umlAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_PCM_collectionType() {
		var pcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository)

		var pcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmAttribute.entityName = TEST_ATTRIBUTE
		pcmAttribute.datatype_InnerDeclaration = helper.getPcmCollectionDataType(pcmRepository)
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmAttribute
		saveAndSynchronizeChanges(pcmAttribute)
		
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository)
		
		pcmAttribute = pcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(pcmAttribute)
		checkAttributeConcept(pcmAttribute)
	}
	
	@Test
	def void testCreateAttributeConcept_UML_collectionType() {
		var pcmRepository = createRepository_CompositeDataType_CompositeDataType2()
		var umlCompositeTypeClass = helper.getUmlCompositeDataTypeClass(pcmRepository)
		startRecordingChanges(umlCompositeTypeClass)
		
		// the attribute.type needs to be set to the innerType of the Collection type
		var umlAttribute = umlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, helper.getUmlCompositeDataTypeClass_2(pcmRepository))
		umlAttribute.lower = 0
		umlAttribute.upper = LiteralUnlimitedNatural.UNLIMITED
		
		saveAndSynchronizeChanges(umlAttribute)
		reloadResourceAndReturnRoot(umlAttribute)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlAttribute = helper.getUmlCompositeDataTypeClass(pcmRepository).ownedAttributes.findFirst[it.name == TEST_ATTRIBUTE]
		assertNotNull(umlAttribute)
		assertTrue(umlAttribute.upper == LiteralUnlimitedNatural.UNLIMITED)
		checkAttributeConcept(umlAttribute)
	}
	
}
