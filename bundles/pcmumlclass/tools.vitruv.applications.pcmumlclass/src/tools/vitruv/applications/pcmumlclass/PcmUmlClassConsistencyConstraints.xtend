package tools.vitruv.applications.pcmumlclass

import org.eclipse.uml2.uml.Class

import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Property
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.Interface
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.core.composition.AssemblyContext
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil

class PcmUmlClassConsistencyConstraints {
	private new() {
	}
	
	def private static corresponds(CorrespondenceModel cm, EObject a, EObject b){
		return cm.getCorrespondingEObjects(#[a]).exists( corrSet | corrSet.head === b)
	}
	def private static corresponds(CorrespondenceModel cm, EObject a, EObject b, String tag){
		return b === ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, a,
				tag, b.class).head
	}

//	def public static boolean primitiveDataType2PrimitiveType(PrimitiveDataType pcmType, PrimitiveType umlType){
//		return pcmType.type.getName == umlType.name 
//	}
	def public static boolean checkRepositoryConcept(CorrespondenceModel cm, Repository pcmRepo,
			Package umlRepositoryPkg, Package umlContractsPkg,
			Package umlDatatypesPkg
	){
		val correspondenceConstraints = 
			corresponds(cm, pcmRepo, umlRepositoryPkg, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE) 
			&& corresponds(cm, pcmRepo, umlContractsPkg, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
			&& corresponds(cm, pcmRepo, umlDatatypesPkg, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		val containmentConstraints = 
			umlContractsPkg.nestingPackage === umlRepositoryPkg
			&& umlDatatypesPkg.nestingPackage === umlRepositoryPkg
		val attributeConstraints = 
			umlRepositoryPkg.name == pcmRepo.entityName.toFirstLower 
			&& umlRepositoryPkg.name.toFirstUpper == pcmRepo.entityName
			&& umlContractsPkg.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME
			&& umlDatatypesPkg.name == DefaultLiterals.DATATYPES_PACKAGE_NAME

		return correspondenceConstraints && containmentConstraints && attributeConstraints
	}

	def public static boolean checkInterfaceConcept(CorrespondenceModel cm, 
			Interface pcmInterface, 
			org.eclipse.uml2.uml.Interface umlInterface
	){
		val correspondenceConstraint = corresponds(cm, pcmInterface, umlInterface,	TagLiterals.INTERFACE_TO_INTERFACE)
		val attributeConstraint = pcmInterface.entityName == umlInterface.name
		// should be contained in corresponding repository and contracts package respectively
		val containmentConstraint = 
			corresponds(cm, pcmInterface.repository__Interface, umlInterface.package, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE)
			//parent interfaces should correspond
			&& (pcmInterface.parentInterfaces__Interface
				.map[pcmParent | CorrespondenceModelUtil.getCorrespondingEObjectsByType(cm, pcmParent, org.eclipse.uml2.uml.Interface)]
				.map[umlParent | umlInterface.generalizations.exists[gen | gen.general === umlParent]]
				.reduce[p1, p2| p1 && p2]
			)
		return correspondenceConstraint && attributeConstraint && containmentConstraint
	}

	def public static boolean checkSignatureConcept(
			CorrespondenceModel cm, 
			OperationSignature pcmSignature, 
			Operation umlOperation
	){
		val returnParam = umlOperation.ownedParameters.findFirst[param | param.direction === ParameterDirectionKind.RETURN_LITERAL]
		val correspondenceConstraint = 
			corresponds(cm, pcmSignature, umlOperation, TagLiterals.SIGNATURE__OPERATION)
			&& corresponds(cm, pcmSignature, returnParam, TagLiterals.SIGNATURE__OPERATION)
		val attributeConstraint = pcmSignature.entityName == umlOperation.name
		//return types of both model elements should correspond to each other if they are set
		val returnTypeConstraint = 
			(pcmSignature.returnType__OperationSignature === null && umlOperation.type === null)
			//TODO update for SIGNATURE__RETURN_PARAM correspondence || (corresponds(cm, pcmSignature.returnType__OperationSignature, umlOperation.type))
		// should both be contained in corresponding interfaces
		val containmentConstraint = corresponds(cm, pcmSignature.interface__OperationSignature, umlOperation.interface, TagLiterals.INTERFACE_TO_INTERFACE)	
		return correspondenceConstraint && attributeConstraint && returnTypeConstraint && containmentConstraint
	}
	
	def private static boolean checkParameterModifiers(ParameterModifier pcmModifier, ParameterDirectionKind umlDirection){
		return switch(pcmModifier){
				case IN: umlDirection == ParameterDirectionKind.IN_LITERAL
				case OUT: umlDirection == ParameterDirectionKind.OUT_LITERAL
//				case INOUT,
//				case NONE,
				default: umlDirection == ParameterDirectionKind.INOUT_LITERAL}
	}
	
	def public static boolean checkParameterConcept(CorrespondenceModel cm, 
			Parameter pcmParam, 
			org.eclipse.uml2.uml.Parameter umlParam
	){
		val correspondenceConstraint = corresponds(cm, pcmParam, umlParam)
		val attributeConstraint = 
			pcmParam.parameterName == umlParam.name 
			&& checkParameterModifiers(pcmParam.modifier__Parameter, umlParam.direction)
		val containmentConstraint = corresponds(cm, pcmParam.operationSignature__Parameter, umlParam.operation)
		return correspondenceConstraint && attributeConstraint && containmentConstraint
	}
	
	def public static boolean ckeckPrimitiveDatatypeConcept(){
		//TODO maping to pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml
		//UML:	Bool, Int, String, Real, UnlimitedNatural
		//PCM:	Bool, Int, String, Double, Byte, Char
		//Reaction: onPrimitiveCreate -> Warning=UsePredefinedTypes
		return false;
	} 
	
	def public static boolean ckeckCollectionDatatypeConcept(){
		//TODO CollectionType <1--*> {Parameter or Property} extends TypedElement? 
		// 		if(p.type ~_corr CollType) p.multiplicity = 0..*
		// easily implemented via reaction, but hard to test for?	
		return false;
	}
	
	def public static boolean checkComplexDatatypeConcept(CorrespondenceModel cm, 
			CompositeDataType pcmComplexType, 
			Class umlClass
	){
		val correspondenceConstraint = corresponds(cm, pcmComplexType, umlClass)
		val attributeConstraint = pcmComplexType.entityName == umlClass.name
		//Repository should correspond to the datatypes package
		val containmentConstraint = corresponds(cm, pcmComplexType.repository__DataType, umlClass.package, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE)
		return correspondenceConstraint && attributeConstraint && containmentConstraint
	}
	
	def public static boolean checkAttributeConcept(CorrespondenceModel cm, 
			InnerDeclaration pcmAttribute, 
			Property umlAttribute
	){
		val correspondenceConstraint = corresponds(cm, pcmAttribute, umlAttribute)
		val attributeConstraint = pcmAttribute.entityName == umlAttribute.name
		val containmentConstraint = 
			//parent CompositeType should correspond to parent uml::Class
			corresponds(cm, pcmAttribute.compositeDataType_InnerDeclaration, umlAttribute.class_)
			//types should correspond
			&& corresponds(cm, pcmAttribute.datatype_InnerDeclaration, umlAttribute.type)
		return correspondenceConstraint && attributeConstraint && containmentConstraint
	}
	
	def public static boolean checkComponentConcept(CorrespondenceModel cm, 
			RepositoryComponent pcmComponent, 
			org.eclipse.uml2.uml.Package umlComponentPkg,
			org.eclipse.uml2.uml.Class umlComponentImpl,
			org.eclipse.uml2.uml.Operation umlComponentConstructor
	){
		val correspondenceConstraint = 
			corresponds(cm, pcmComponent, umlComponentPkg, TagLiterals.REPOSITORY_COMPONENT__PACKAGE)
			&& corresponds(cm, pcmComponent, umlComponentImpl, TagLiterals.IPRE__IMPLEMENTATION)
			&& corresponds(cm, pcmComponent, umlComponentConstructor, TagLiterals.IPRE__CONSTRUCTOR)
		val attributeConstraint = 
			pcmComponent.entityName.toFirstLower == umlComponentPkg.name 
			&& pcmComponent.entityName == umlComponentPkg.name.toFirstUpper
			&& pcmComponent.entityName + "Impl" == umlComponentImpl.name
			&& pcmComponent.entityName + "Impl" == umlComponentConstructor.name
//			&& umlComponentConstructor.type === umlComponentImpl // decided against explicit constructor return type, because it's a common convention
			&& umlComponentImpl.isFinalSpecialization 
			&& umlComponentImpl.visibility === VisibilityKind.PUBLIC_LITERAL
			&& umlComponentImpl.package === umlComponentPkg
		val containmentConstraint = 
			//component repository should correspond to the parent package of the component package
			corresponds(cm, pcmComponent.repository__RepositoryComponent, umlComponentPkg.nestingPackage, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE)
		return correspondenceConstraint && attributeConstraint && containmentConstraint
	}
	
	def public static boolean checkRequiredRoleConcept(
			CorrespondenceModel cm,
			OperationRequiredRole pcmRequired,
			org.eclipse.uml2.uml.Property umlRequiredInstance,
			org.eclipse.uml2.uml.Parameter umlRequiredParameter
	){
		val correspondenceConstraint = 
			corresponds(cm, pcmRequired, umlRequiredInstance, TagLiterals.REQUIRED_ROLE__PROPERTY)
			&& corresponds(cm, pcmRequired, umlRequiredParameter, TagLiterals.REQUIRED_ROLE__PARAMETER)
		val containmentConstraint = 
			//the respective type references have to correspond
			corresponds(cm, pcmRequired.requiredInterface__OperationRequiredRole, umlRequiredInstance.type)
			&& corresponds(cm, pcmRequired.requiredInterface__OperationRequiredRole, umlRequiredParameter.type)
			// the owning component and component implementation have to correspond
			&& corresponds(cm, pcmRequired.requiringEntity_RequiredRole, umlRequiredInstance.class_, TagLiterals.IPRE__IMPLEMENTATION)
			&& corresponds(cm, pcmRequired.requiringEntity_RequiredRole, umlRequiredParameter.operation?.class_, TagLiterals.IPRE__IMPLEMENTATION)
		return correspondenceConstraint && containmentConstraint
	}
	
	def public static boolean checkProvidedRoleConcept(
			CorrespondenceModel cm,
			OperationProvidedRole pcmProvided,
			org.eclipse.uml2.uml.Generalization umlImplementedInterface
	){
		val correspondenceConstraint = corresponds(cm, pcmProvided, umlImplementedInterface)
		val containmentConstraint = 
			//the respective type references have to correspond
			corresponds(cm, pcmProvided.providedInterface__OperationProvidedRole, umlImplementedInterface.general)
			// the owning component and component implementation have to correspond
			&& corresponds(cm, pcmProvided.providingEntity_ProvidedRole, umlImplementedInterface.specific, TagLiterals.IPRE__IMPLEMENTATION)
		return correspondenceConstraint && containmentConstraint
	}
	
	def public static boolean checkAssemblyContextConcept(
			CorrespondenceModel cm,
			AssemblyContext pcmContext,
			org.eclipse.uml2.uml.Property umlContextInstance
	){
		val correspondenceConstraint = corresponds(cm, pcmContext, umlContextInstance)
		val containmentConstraint = 
			//the respective type references have to correspond
			corresponds(cm, pcmContext.parentStructure__AssemblyContext, umlContextInstance) // no tag because it could be a System or a RepositoryComponent
			// the owning component and component implementation have to correspond
			&& corresponds(cm, pcmContext.encapsulatedComponent__AssemblyContext, umlContextInstance.type)
		return correspondenceConstraint && containmentConstraint
	}
	
	def public static boolean checkSystemConcept(
			CorrespondenceModel cm,
			org.palladiosimulator.pcm.system.System pcmSystem,
			org.eclipse.uml2.uml.Package umlSystemPkg,
			org.eclipse.uml2.uml.Class umlSystemImpl
	){
		val correspondenceConstraint = 
			corresponds(cm, pcmSystem, umlSystemPkg, TagLiterals.SYSTEM__SYSTEM_PACKAGE)
			&& corresponds(cm, pcmSystem, umlSystemImpl, TagLiterals.IPRE__IMPLEMENTATION)
		val attributeConstraint = 
			pcmSystem.entityName.toFirstLower == umlSystemPkg.name 
			&& pcmSystem.entityName == umlSystemPkg.name.toFirstUpper
			&& pcmSystem.entityName + "Impl" == umlSystemImpl.name
			&& umlSystemImpl.isFinalSpecialization 
			&& umlSystemImpl.visibility === VisibilityKind.PUBLIC_LITERAL
			&& umlSystemImpl.package === umlSystemPkg
		return correspondenceConstraint && attributeConstraint
	}
	
	
	
	
	
	
	
	
	
	
	
}
