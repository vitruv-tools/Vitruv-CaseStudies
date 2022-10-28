package tools.vitruv.applications.pcmjava.tests.pcm2java

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.palladiosimulator.pcm.core.composition.AssemblyContext
import org.palladiosimulator.pcm.core.composition.CompositionFactory
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.Signature
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF
import org.palladiosimulator.pcm.seff.SeffFactory

@Utility
class PcmCreatorsUtil {
	
	// === core.composition ===
	
	static def AssemblyContext createAssemblyContext((AssemblyContext)=> void intialization) {
		var assemblyContext = CompositionFactory.eINSTANCE.createAssemblyContext
		intialization.apply(assemblyContext)
		return assemblyContext
	}
	
	static def createAssemblyContext(RepositoryComponent encapsulatedComponent, String name) {
		createAssemblyContext[
			entityName = name
			encapsulatedComponent__AssemblyContext = encapsulatedComponent
		]
	}
	
	// === repository ===
	
	static def BasicComponent createBasicComponent((BasicComponent)=> void initalization) {
		var basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent
		initalization.apply(basicComponent)
		return basicComponent
	}
	
	static def BasicComponent createBasicComponent(String name) {
		createBasicComponent [
			entityName = name
		]
	}
	
	static def CompositeComponent createCompositeComponent((CompositeComponent)=> void intialization) {
		var compositeComponent = RepositoryFactory.eINSTANCE.createCompositeComponent
		intialization.apply(compositeComponent)
		return compositeComponent
	}
	
	static def CompositeComponent createCompositeComponent(String name) {
		createCompositeComponent[
			entityName = name
		]
	}
	
	static def OperationInterface createOperationInterface((OperationInterface)=> void intialization) {
		var operationInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		intialization.apply(operationInterface)
		return operationInterface
	}
	
	static def createOperationInterface(String name) {
		createOperationInterface[
			entityName = name
		]
	}
	
	static def OperationSignature createOperationSignature((OperationSignature)=> void intialization) {
		var operationSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		intialization.apply(operationSignature)
		return operationSignature
	}
	
	static def createOperationSignature(String name) {
		createOperationSignature [
			entityName = name
		]
	}
	
	static def Parameter createParameter((Parameter)=> void initalization) {
		var parameter =  RepositoryFactory.eINSTANCE.createParameter
		initalization.apply(parameter)
		return parameter
	}
	
	// === repository -> roles ===
	
	static def OperationProvidedRole createOperationProvidedRole((OperationProvidedRole)=> void intialization) {
		var operationProvidedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole
		intialization.apply(operationProvidedRole)
		return operationProvidedRole
	}
	
	static def createOperationProvidedRole(OperationInterface providedInterface, InterfaceProvidingEntity providingEntity) {
		createOperationProvidedRole[
			entityName = providingEntity.getEntityName() + "_provides_" + providedInterface.getEntityName()
			providedInterface__OperationProvidedRole = providedInterface
		]
	}
	
	static def OperationRequiredRole createOperationRequiredRole((OperationRequiredRole)=> void intialization) {
		var operationRequiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole
		intialization.apply(operationRequiredRole)
		return operationRequiredRole
	}
	
	static def createOperationRequiredRole(OperationInterface providedInterface, InterfaceRequiringEntity requiringEntity) {
		createOperationRequiredRole[
			entityName = requiringEntity.getEntityName() + "_requires_" + providedInterface.getEntityName()
			requiredInterface__OperationRequiredRole = providedInterface
		]
	}
	
	// === repository -> datatypes ===
	
	static def PrimitiveDataType createPrimitiveDataType((PrimitiveDataType)=> void initalization) {
		var dataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
		initalization.apply(dataType)
		return dataType;
	}
	
	static def PrimitiveDataType createPrimitiveDataType(PrimitiveTypeEnum primitiveType) {
        createPrimitiveDataType [
			type = primitiveType
		]
	}
	
	static def CollectionDataType createCollectionDataType((CollectionDataType)=> void initalization) {
		var collectionDataType = RepositoryFactory.eINSTANCE.createCollectionDataType
		initalization.apply(collectionDataType)
		return collectionDataType
	}
	
	static def CollectionDataType createCollectionDataType(String name, DataType innerType) {
		createCollectionDataType [
			entityName = name
			if(null !== innerType){
				innerType_CollectionDataType = innerType
			}
		]
	}
	
	static def CompositeDataType createCompositeDataType((CompositeDataType)=> void initalization) {
		var compositeDataType = RepositoryFactory.eINSTANCE.createCompositeDataType
		initalization.apply(compositeDataType)
		return compositeDataType
	}
	
	static def CompositeDataType createCompositeDataType(String name) {
		createCompositeDataType[
			entityName = name
		]
	}
	
	static def InnerDeclaration createInnerDeclaration((InnerDeclaration)=> void initalization) {
		var innerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration
		initalization.apply(innerDeclaration)
		return innerDeclaration
	}
	
	static def InnerDeclaration createInnerDeclaration(DataType innerType, String name) {
		createInnerDeclaration [
			datatype_InnerDeclaration = innerType
			entityName = name
		]
	}
	
	// === seff ===
	
	static def ResourceDemandingSEFF createResourceDemandingSEFF((ResourceDemandingSEFF)=> void initialization) {
		val rdSEFF = SeffFactory.eINSTANCE.createResourceDemandingSEFF()
		initialization.apply(rdSEFF)
		return rdSEFF
	}
	
	static def ResourceDemandingSEFF createResourceDemandingSEFF(Signature describedService){
		createResourceDemandingSEFF [
			describedService__SEFF = describedService
		]
	}
}
