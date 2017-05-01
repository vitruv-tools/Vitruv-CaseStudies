package tools.vitruv.applications.pcmumlcomp.pcm2uml

import org.junit.Test
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl.InternalSettingDelegateSingle

class CollectionTypesTest extends AbstractPcmUmlTest {
	
	@Test
	def void firstTest() {
		val collectionType = RepositoryFactory.eINSTANCE.createCollectionDataType()
		collectionType.entityName = "BoolCollection"
		collectionType.innerType_CollectionDataType = getPrimitiveType(PrimitiveTypeEnum.BOOL)
		rootElement.dataTypes__Repository += collectionType
		
		/*val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface()
		pcmInterface.entityName = "i1"
		val pcmOperation = RepositoryFactory.eINSTANCE.createOperationSignature()
		pcmOperation.entityName = "o1"
		pcmOperation.returnType__OperationSignature = collectionType
		pcmInterface.signatures__OperationInterface += pcmOperation
		rootElement.interfaces__Repository += pcmInterface*/
		
		val compositeType = RepositoryFactory.eINSTANCE.createCompositeDataType()
		compositeType.entityName = "foo"
		val innerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration()
		innerDeclaration.entityName = "bar"
		innerDeclaration.datatype_InnerDeclaration = getPrimitiveType(PrimitiveTypeEnum.STRING)
		compositeType.innerDeclaration_CompositeDataType += innerDeclaration
		val innerDeclaration2 = RepositoryFactory.eINSTANCE.createInnerDeclaration()
		innerDeclaration2.entityName = "baz"
		innerDeclaration2.datatype_InnerDeclaration = getPrimitiveType(PrimitiveTypeEnum.BOOL)
		compositeType.innerDeclaration_CompositeDataType += innerDeclaration2
		val innerDeclaration3 = RepositoryFactory.eINSTANCE.createInnerDeclaration()
		innerDeclaration3.entityName = "top"
		innerDeclaration3.datatype_InnerDeclaration = collectionType
		compositeType.innerDeclaration_CompositeDataType += innerDeclaration3
		/*val innerDeclaration4 = RepositoryFactory.eINSTANCE.createInnerDeclaration()
		innerDeclaration4.entityName = "kek"
		innerDeclaration4.datatype_InnerDeclaration = collectionType
		compositeType.innerDeclaration_CompositeDataType += innerDeclaration4
		rootElement.dataTypes__Repository += compositeType
		
		saveAndSynchronizeChanges(rootElement) 
		
		innerDeclaration4.datatype_InnerDeclaration = getPrimitiveType(PrimitiveTypeEnum.INT)*/
		rootElement.dataTypes__Repository += compositeType
		saveAndSynchronizeChanges(rootElement)
		
		/*val ref = EcoreUtil.UsageCrossReferencer.find(collectionType, rootElement)
		ref.forEach[t | println(t)]*/
	}
	
	@Test
	def void basicTest() {
		
		val pT = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		pT.type = PrimitiveTypeEnum.BYTE
		rootElement.dataTypes__Repository += pT
		
		val cT = RepositoryFactory.eINSTANCE.createCollectionDataType()
		cT.innerType_CollectionDataType = pT
		cT.entityName = "ByteCollection"
		rootElement.dataTypes__Repository += cT
		
		val compositeType = RepositoryFactory.eINSTANCE.createCompositeDataType()
		compositeType.entityName = "foo"
		
		val innerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration()
		innerDeclaration.entityName = "bar"
		innerDeclaration.datatype_InnerDeclaration = cT
		
		compositeType.innerDeclaration_CompositeDataType += innerDeclaration
		
		rootElement.dataTypes__Repository += compositeType
		saveAndSynchronizeChanges(rootElement)
		
		innerDeclaration.datatype_InnerDeclaration = pT
		saveAndSynchronizeChanges(innerDeclaration)
	}
	
	@Test
	def void usageReferenceTest() {
		val pT = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		pT.type = PrimitiveTypeEnum.BYTE
		rootElement.dataTypes__Repository += pT
		
		val cT = RepositoryFactory.eINSTANCE.createCollectionDataType()
		cT.innerType_CollectionDataType = pT
		cT.entityName = "ByteCollection"
		rootElement.dataTypes__Repository += cT
		
		val compositeType = RepositoryFactory.eINSTANCE.createCompositeDataType()
		compositeType.entityName = "foo"
		
		val innerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration()
		innerDeclaration.entityName = "bar"
		innerDeclaration.datatype_InnerDeclaration = cT
		
		compositeType.innerDeclaration_CompositeDataType += innerDeclaration
		
		rootElement.dataTypes__Repository += compositeType
		
		val interf = RepositoryFactory.eINSTANCE.createOperationInterface()
		interf.entityName = "interface1"
		
		val sig = RepositoryFactory.eINSTANCE.createOperationSignature()
		sig.entityName = "baz"
		sig.returnType__OperationSignature = cT
		interf.signatures__OperationInterface += sig
		
		rootElement.interfaces__Repository += interf
		
		saveAndSynchronizeChanges(rootElement)
		
		val pT2 = RepositoryFactory.eINSTANCE.createPrimitiveDataType()
		pT2.type = PrimitiveTypeEnum.CHAR
		rootElement.dataTypes__Repository += pT2
		cT.innerType_CollectionDataType = pT2
		
		saveAndSynchronizeChanges(rootElement)
		saveAndSynchronizeChanges(cT)
		
		/*val crossReferences = EcoreUtil.UsageCrossReferencer.find(cT, rootElement)
		for (reference : crossReferences) {
			println("> reference")
			println(reference.class.name)
			println(reference.EObject)
			println(reference.EStructuralFeature)
			reference.EObject.eSet(reference.EStructuralFeature, pT)
		}
		saveAndSynchronizeChanges(rootElement)*/
		
	}
	
}