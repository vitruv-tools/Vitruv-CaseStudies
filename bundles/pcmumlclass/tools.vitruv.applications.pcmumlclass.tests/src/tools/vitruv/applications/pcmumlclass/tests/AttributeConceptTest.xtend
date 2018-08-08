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
import org.eclipse.uml2.uml.Type
import org.palladiosimulator.pcm.repository.DataType
import org.eclipse.emf.ecore.util.EcoreUtil

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



	def private Repository createRepository(){
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

	
	private def void testCreateAttributeConcept_PCM(Repository inPcmRepository, DataType pcmType) {
		var pcmRepository = inPcmRepository
		var pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository)

		var pcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmAttribute.entityName = TEST_ATTRIBUTE
		pcmAttribute.datatype_InnerDeclaration = pcmType
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmAttribute
		
		saveAndSynchronizeChanges(pcmAttribute)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository)
		
		pcmAttribute = pcmCompositeType.innerDeclaration_CompositeDataType.findFirst[it.entityName == TEST_ATTRIBUTE]
		assertNotNull(pcmAttribute)
		checkAttributeConcept(pcmAttribute)
		assertTrue(EcoreUtil.equals(pcmAttribute.datatype_InnerDeclaration, helper.getModifiableInstance(pcmType)))
	}


	@Test @Ignore
	def void testCreateAttributeConcept_PCM_primitiveType() {
		var pcmRepository = createRepository()
		testCreateAttributeConcept_PCM(pcmRepository, helper.PCM_INT)
	}
	
	@Test
	def void testCreateAttributeConcept_PCM_compositeType() {
		var pcmRepository = createRepository()
		//innerDeclaration with same type as outer CompositeDataType doesn't trigger change event
		testCreateAttributeConcept_PCM(pcmRepository, helper.getPcmCompositeDataType_2(pcmRepository))
	}
	
	@Test
	def void testCreateAttributeConcept_PCM_collectionType() {
		var pcmRepository = createRepository()
		testCreateAttributeConcept_PCM(pcmRepository, helper.getPcmCollectionDataType(pcmRepository))
	}
	

	private def void testCreateAttributeConcept_UML(Repository inPcmRepository, Type umlType, int lower, int upper) {
		var pcmRepository = inPcmRepository
		var umlCompositeTypeClass = helper.getUmlCompositeDataTypeClass(pcmRepository)
		startRecordingChanges(umlCompositeTypeClass)
		
		var umlAttribute = umlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, null)
		umlAttribute.type = umlType
		umlAttribute.lower = lower
		umlAttribute.upper = upper
		
		saveAndSynchronizeChanges(umlAttribute)
		reloadResourceAndReturnRoot(umlAttribute)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		umlAttribute = helper.getUmlCompositeDataTypeClass(pcmRepository).ownedAttributes.findFirst[it.name == TEST_ATTRIBUTE]
		assertNotNull(umlAttribute)
		checkAttributeConcept(umlAttribute)
		assertTrue(EcoreUtil.equals(umlAttribute.type, helper.getModifiableInstance(umlType)))
		assertTrue(umlAttribute.lower == lower)
		assertTrue(umlAttribute.upper == upper)
	}
	
	@Test @Ignore
	def void testCreateAttributeConcept_UML_primitiveType() {
		var pcmRepository = createRepository()
		testCreateAttributeConcept_UML(pcmRepository, helper.UML_INT, 1, 1)
	}
	
	@Test
	def void testCreateAttributeConcept_UML_compositeType() {
		var pcmRepository = createRepository()
		testCreateAttributeConcept_UML(pcmRepository, helper.getUmlCompositeDataTypeClass(pcmRepository), 1, 1)
	}
	
	@Test
	def void testCreateAttributeConcept_UML_collectionType() {
		var pcmRepository = createRepository()
		testCreateAttributeConcept_UML(pcmRepository, helper.getUmlCompositeDataTypeClass_2(pcmRepository), 0, LiteralUnlimitedNatural.UNLIMITED)
	}
	
}
