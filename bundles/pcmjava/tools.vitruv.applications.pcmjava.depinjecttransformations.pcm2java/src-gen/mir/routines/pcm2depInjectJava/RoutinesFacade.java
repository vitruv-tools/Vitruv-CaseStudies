package mir.routines.pcm2depInjectJava;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createRepositorySubPackages(final Repository repository) {
    mir.routines.pcm2depInjectJava.CreateRepositorySubPackagesRoutine effect = new mir.routines.pcm2depInjectJava.CreateRepositorySubPackagesRoutine(this.executionState, calledBy,
    	repository);
    effect.applyRoutine();
  }
  
  public void renamePackageForRepository(final Repository repository) {
    mir.routines.pcm2depInjectJava.RenamePackageForRepositoryRoutine effect = new mir.routines.pcm2depInjectJava.RenamePackageForRepositoryRoutine(this.executionState, calledBy,
    	repository);
    effect.applyRoutine();
  }
  
  public void createImplementationForSystem(final org.palladiosimulator.pcm.system.System system) {
    mir.routines.pcm2depInjectJava.CreateImplementationForSystemRoutine effect = new mir.routines.pcm2depInjectJava.CreateImplementationForSystemRoutine(this.executionState, calledBy,
    	system);
    effect.applyRoutine();
  }
  
  public void changeSystemImplementationName(final org.palladiosimulator.pcm.system.System system) {
    mir.routines.pcm2depInjectJava.ChangeSystemImplementationNameRoutine effect = new mir.routines.pcm2depInjectJava.ChangeSystemImplementationNameRoutine(this.executionState, calledBy,
    	system);
    effect.applyRoutine();
  }
  
  public void addAssemblyContextToComposedStructure(final ComposedStructure composedStructure, final AssemblyContext assemblyContext) {
    mir.routines.pcm2depInjectJava.AddAssemblyContextToComposedStructureRoutine effect = new mir.routines.pcm2depInjectJava.AddAssemblyContextToComposedStructureRoutine(this.executionState, calledBy,
    	composedStructure, assemblyContext);
    effect.applyRoutine();
  }
  
  public void addConnector(final AssemblyConnector assemblyConnector) {
    mir.routines.pcm2depInjectJava.AddConnectorRoutine effect = new mir.routines.pcm2depInjectJava.AddConnectorRoutine(this.executionState, calledBy,
    	assemblyConnector);
    effect.applyRoutine();
  }
  
  public void addedProvidedDelegationConnector(final ProvidedDelegationConnector providedDelegationConnector, final ComposedStructure pcmSystem) {
    mir.routines.pcm2depInjectJava.AddedProvidedDelegationConnectorRoutine effect = new mir.routines.pcm2depInjectJava.AddedProvidedDelegationConnectorRoutine(this.executionState, calledBy,
    	providedDelegationConnector, pcmSystem);
    effect.applyRoutine();
  }
  
  public void createComponentImplementation(final RepositoryComponent component) {
    mir.routines.pcm2depInjectJava.CreateComponentImplementationRoutine effect = new mir.routines.pcm2depInjectJava.CreateComponentImplementationRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void createImplementationForComponent(final RepositoryComponent component) {
    mir.routines.pcm2depInjectJava.CreateImplementationForComponentRoutine effect = new mir.routines.pcm2depInjectJava.CreateImplementationForComponentRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void renameComponentPackageAndClass(final RepositoryComponent component) {
    mir.routines.pcm2depInjectJava.RenameComponentPackageAndClassRoutine effect = new mir.routines.pcm2depInjectJava.RenameComponentPackageAndClassRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void renameComponentClass(final RepositoryComponent component) {
    mir.routines.pcm2depInjectJava.RenameComponentClassRoutine effect = new mir.routines.pcm2depInjectJava.RenameComponentClassRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void createInterfaceImplementation(final Interface interf) {
    mir.routines.pcm2depInjectJava.CreateInterfaceImplementationRoutine effect = new mir.routines.pcm2depInjectJava.CreateInterfaceImplementationRoutine(this.executionState, calledBy,
    	interf);
    effect.applyRoutine();
  }
  
  public void renameInterface(final OperationInterface interf) {
    mir.routines.pcm2depInjectJava.RenameInterfaceRoutine effect = new mir.routines.pcm2depInjectJava.RenameInterfaceRoutine(this.executionState, calledBy,
    	interf);
    effect.applyRoutine();
  }
  
  public void createCompositeDataTypeImplementation(final CompositeDataType dataType) {
    mir.routines.pcm2depInjectJava.CreateCompositeDataTypeImplementationRoutine effect = new mir.routines.pcm2depInjectJava.CreateCompositeDataTypeImplementationRoutine(this.executionState, calledBy,
    	dataType);
    effect.applyRoutine();
  }
  
  public void renameCompositeDataType(final CompositeDataType compositeDataType) {
    mir.routines.pcm2depInjectJava.RenameCompositeDataTypeRoutine effect = new mir.routines.pcm2depInjectJava.RenameCompositeDataTypeRoutine(this.executionState, calledBy,
    	compositeDataType);
    effect.applyRoutine();
  }
  
  public void createCollectionDataTypeImplementation(final CollectionDataType dataType) {
    mir.routines.pcm2depInjectJava.CreateCollectionDataTypeImplementationRoutine effect = new mir.routines.pcm2depInjectJava.CreateCollectionDataTypeImplementationRoutine(this.executionState, calledBy,
    	dataType);
    effect.applyRoutine();
  }
  
  public void addSuperTypeToDataType(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    mir.routines.pcm2depInjectJava.AddSuperTypeToDataTypeRoutine effect = new mir.routines.pcm2depInjectJava.AddSuperTypeToDataTypeRoutine(this.executionState, calledBy,
    	dataType, innerTypeReference, superTypeQualifiedName);
    effect.applyRoutine();
  }
  
  public void renameCollectionDataType(final CollectionDataType collectionDataType) {
    mir.routines.pcm2depInjectJava.RenameCollectionDataTypeRoutine effect = new mir.routines.pcm2depInjectJava.RenameCollectionDataTypeRoutine(this.executionState, calledBy,
    	collectionDataType);
    effect.applyRoutine();
  }
  
  public void createInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2depInjectJava.CreateInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2depInjectJava.CreateInnerDeclarationImplementationRoutine(this.executionState, calledBy,
    	innerDeclaration);
    effect.applyRoutine();
  }
  
  public void addInnerDeclarationToCompositeDataType(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    mir.routines.pcm2depInjectJava.AddInnerDeclarationToCompositeDataTypeRoutine effect = new mir.routines.pcm2depInjectJava.AddInnerDeclarationToCompositeDataTypeRoutine(this.executionState, calledBy,
    	dataType, innerDeclaration, dataTypeReference);
    effect.applyRoutine();
  }
  
  public void renameInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2depInjectJava.RenameInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2depInjectJava.RenameInnerDeclarationImplementationRoutine(this.executionState, calledBy,
    	innerDeclaration);
    effect.applyRoutine();
  }
  
  public void changeTypeOfInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2depInjectJava.ChangeTypeOfInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2depInjectJava.ChangeTypeOfInnerDeclarationImplementationRoutine(this.executionState, calledBy,
    	innerDeclaration);
    effect.applyRoutine();
  }
  
  public void changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    mir.routines.pcm2depInjectJava.ChangeInnerDeclarationTypeRoutine effect = new mir.routines.pcm2depInjectJava.ChangeInnerDeclarationTypeRoutine(this.executionState, calledBy,
    	innerDeclaration, newTypeReference);
    effect.applyRoutine();
  }
  
  public void createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.pcm2depInjectJava.CreateJavaPackageRoutine effect = new mir.routines.pcm2depInjectJava.CreateJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, parentPackage, packageName, newTag);
    effect.applyRoutine();
  }
  
  public void renameJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2depInjectJava.RenameJavaPackageRoutine effect = new mir.routines.pcm2depInjectJava.RenameJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, parentPackage, packageName, expectedTag);
    effect.applyRoutine();
  }
  
  public void deleteJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2depInjectJava.DeleteJavaPackageRoutine effect = new mir.routines.pcm2depInjectJava.DeleteJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, packageName, expectedTag);
    effect.applyRoutine();
  }
  
  public void createJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2depInjectJava.CreateJavaClassRoutine effect = new mir.routines.pcm2depInjectJava.CreateJavaClassRoutine(this.executionState, calledBy,
    	sourceElementMappedToClass, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void createJavaInterface(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2depInjectJava.CreateJavaInterfaceRoutine effect = new mir.routines.pcm2depInjectJava.CreateJavaInterfaceRoutine(this.executionState, calledBy,
    	sourceElementMappedToClass, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void createCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    mir.routines.pcm2depInjectJava.CreateCompilationUnitRoutine effect = new mir.routines.pcm2depInjectJava.CreateCompilationUnitRoutine(this.executionState, calledBy,
    	sourceElementMappedToClass, classifier, containingPackage);
    effect.applyRoutine();
  }
  
  public void renameJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2depInjectJava.RenameJavaClassifierRoutine effect = new mir.routines.pcm2depInjectJava.RenameJavaClassifierRoutine(this.executionState, calledBy,
    	classSourceElement, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void deleteJavaClassifier(final NamedElement sourceElement) {
    mir.routines.pcm2depInjectJava.DeleteJavaClassifierRoutine effect = new mir.routines.pcm2depInjectJava.DeleteJavaClassifierRoutine(this.executionState, calledBy,
    	sourceElement);
    effect.applyRoutine();
  }
  
  public void addProvidedRole(final OperationProvidedRole providedRole) {
    mir.routines.pcm2depInjectJava.AddProvidedRoleRoutine effect = new mir.routines.pcm2depInjectJava.AddProvidedRoleRoutine(this.executionState, calledBy,
    	providedRole);
    effect.applyRoutine();
  }
  
  public void removeProvidedRole(final ProvidedRole providedRole) {
    mir.routines.pcm2depInjectJava.RemoveProvidedRoleRoutine effect = new mir.routines.pcm2depInjectJava.RemoveProvidedRoleRoutine(this.executionState, calledBy,
    	providedRole);
    effect.applyRoutine();
  }
  
  public void addRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2depInjectJava.AddRequiredRoleRoutine effect = new mir.routines.pcm2depInjectJava.AddRequiredRoleRoutine(this.executionState, calledBy,
    	requiredRole);
    effect.applyRoutine();
  }
  
  public void addParameterAndAssignmentToConstructor(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName) {
    mir.routines.pcm2depInjectJava.AddParameterAndAssignmentToConstructorRoutine effect = new mir.routines.pcm2depInjectJava.AddParameterAndAssignmentToConstructorRoutine(this.executionState, calledBy,
    	parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName);
    effect.applyRoutine();
  }
  
  public void removeRequiredRole(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    mir.routines.pcm2depInjectJava.RemoveRequiredRoleRoutine effect = new mir.routines.pcm2depInjectJava.RemoveRequiredRoleRoutine(this.executionState, calledBy,
    	requiredRole, requiringEntity);
    effect.applyRoutine();
  }
  
  public void removeParameterToFieldAssignmentFromConstructor(final Constructor ctor, final String fieldName) {
    mir.routines.pcm2depInjectJava.RemoveParameterToFieldAssignmentFromConstructorRoutine effect = new mir.routines.pcm2depInjectJava.RemoveParameterToFieldAssignmentFromConstructorRoutine(this.executionState, calledBy,
    	ctor, fieldName);
    effect.applyRoutine();
  }
  
  public void removeCorrespondingParameterFromConstructor(final Constructor ctor, final NamedElement correspondenceSource) {
    mir.routines.pcm2depInjectJava.RemoveCorrespondingParameterFromConstructorRoutine effect = new mir.routines.pcm2depInjectJava.RemoveCorrespondingParameterFromConstructorRoutine(this.executionState, calledBy,
    	ctor, correspondenceSource);
    effect.applyRoutine();
  }
  
  public void reinitializeOperationRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2depInjectJava.ReinitializeOperationRequiredRoleRoutine effect = new mir.routines.pcm2depInjectJava.ReinitializeOperationRequiredRoleRoutine(this.executionState, calledBy,
    	requiredRole);
    effect.applyRoutine();
  }
  
  public void createMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2depInjectJava.CreateMethodForOperationSignatureRoutine effect = new mir.routines.pcm2depInjectJava.CreateMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void renameMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2depInjectJava.RenameMethodForOperationSignatureRoutine effect = new mir.routines.pcm2depInjectJava.RenameMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void changeReturnTypeOfMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2depInjectJava.ChangeReturnTypeOfMethodForOperationSignatureRoutine effect = new mir.routines.pcm2depInjectJava.ChangeReturnTypeOfMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void changeInterfaceMethodReturnType(final InterfaceMethod interfaceMethod, final DataType returnType) {
    mir.routines.pcm2depInjectJava.ChangeInterfaceMethodReturnTypeRoutine effect = new mir.routines.pcm2depInjectJava.ChangeInterfaceMethodReturnTypeRoutine(this.executionState, calledBy,
    	interfaceMethod, returnType);
    effect.applyRoutine();
  }
  
  public void deleteMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2depInjectJava.DeleteMethodForOperationSignatureRoutine effect = new mir.routines.pcm2depInjectJava.DeleteMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void createParameter(final Parameter parameter) {
    mir.routines.pcm2depInjectJava.CreateParameterRoutine effect = new mir.routines.pcm2depInjectJava.CreateParameterRoutine(this.executionState, calledBy,
    	parameter);
    effect.applyRoutine();
  }
  
  public void renameParameter(final Parameter parameter) {
    mir.routines.pcm2depInjectJava.RenameParameterRoutine effect = new mir.routines.pcm2depInjectJava.RenameParameterRoutine(this.executionState, calledBy,
    	parameter);
    effect.applyRoutine();
  }
  
  public void changeParameterType(final Parameter parameter) {
    mir.routines.pcm2depInjectJava.ChangeParameterTypeRoutine effect = new mir.routines.pcm2depInjectJava.ChangeParameterTypeRoutine(this.executionState, calledBy,
    	parameter);
    effect.applyRoutine();
  }
  
  public void deleteParameter(final OperationSignature signature, final Parameter parameter) {
    mir.routines.pcm2depInjectJava.DeleteParameterRoutine effect = new mir.routines.pcm2depInjectJava.DeleteParameterRoutine(this.executionState, calledBy,
    	signature, parameter);
    effect.applyRoutine();
  }
  
  public void createMethodForResourceDemandingBehavior(final ResourceDemandingInternalBehaviour behavior) {
    mir.routines.pcm2depInjectJava.CreateMethodForResourceDemandingBehaviorRoutine effect = new mir.routines.pcm2depInjectJava.CreateMethodForResourceDemandingBehaviorRoutine(this.executionState, calledBy,
    	behavior);
    effect.applyRoutine();
  }
  
  public void renameMethodForResourceDemandingBehavior(final ResourceDemandingInternalBehaviour behavior) {
    mir.routines.pcm2depInjectJava.RenameMethodForResourceDemandingBehaviorRoutine effect = new mir.routines.pcm2depInjectJava.RenameMethodForResourceDemandingBehaviorRoutine(this.executionState, calledBy,
    	behavior);
    effect.applyRoutine();
  }
  
  public void deleteMethodForResourceDemandingBehavior(final ResourceDemandingInternalBehaviour behavior) {
    mir.routines.pcm2depInjectJava.DeleteMethodForResourceDemandingBehaviorRoutine effect = new mir.routines.pcm2depInjectJava.DeleteMethodForResourceDemandingBehaviorRoutine(this.executionState, calledBy,
    	behavior);
    effect.applyRoutine();
  }
  
  public void createSEFF(final ServiceEffectSpecification seff) {
    mir.routines.pcm2depInjectJava.CreateSEFFRoutine effect = new mir.routines.pcm2depInjectJava.CreateSEFFRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
  
  public void changeMethodForSeff(final ResourceDemandingSEFF seff) {
    mir.routines.pcm2depInjectJava.ChangeMethodForSeffRoutine effect = new mir.routines.pcm2depInjectJava.ChangeMethodForSeffRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
  
  public void updateSEFFImplementingMethodName(final ServiceEffectSpecification seff) {
    mir.routines.pcm2depInjectJava.UpdateSEFFImplementingMethodNameRoutine effect = new mir.routines.pcm2depInjectJava.UpdateSEFFImplementingMethodNameRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
  
  public void deleteMethodForSeff(final ServiceEffectSpecification seff) {
    mir.routines.pcm2depInjectJava.DeleteMethodForSeffRoutine effect = new mir.routines.pcm2depInjectJava.DeleteMethodForSeffRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
}
