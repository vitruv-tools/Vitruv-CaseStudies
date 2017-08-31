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
  
  public boolean createRepositorySubPackages(final Repository repository) {
    mir.routines.pcm2depInjectJava.CreateRepositorySubPackagesRoutine effect = new mir.routines.pcm2depInjectJava.CreateRepositorySubPackagesRoutine(this.executionState, calledBy, repository);
    return effect.applyRoutine();
  }
  
  public boolean renamePackageForRepository(final Repository repository) {
    mir.routines.pcm2depInjectJava.RenamePackageForRepositoryRoutine effect = new mir.routines.pcm2depInjectJava.RenamePackageForRepositoryRoutine(this.executionState, calledBy, repository);
    return effect.applyRoutine();
  }
  
  public boolean createImplementationForSystem(final org.palladiosimulator.pcm.system.System system) {
    mir.routines.pcm2depInjectJava.CreateImplementationForSystemRoutine effect = new mir.routines.pcm2depInjectJava.CreateImplementationForSystemRoutine(this.executionState, calledBy, system);
    return effect.applyRoutine();
  }
  
  public boolean changeSystemImplementationName(final org.palladiosimulator.pcm.system.System system) {
    mir.routines.pcm2depInjectJava.ChangeSystemImplementationNameRoutine effect = new mir.routines.pcm2depInjectJava.ChangeSystemImplementationNameRoutine(this.executionState, calledBy, system);
    return effect.applyRoutine();
  }
  
  public boolean addAssemblyContextToComposedStructure(final ComposedStructure composedStructure, final AssemblyContext assemblyContext) {
    mir.routines.pcm2depInjectJava.AddAssemblyContextToComposedStructureRoutine effect = new mir.routines.pcm2depInjectJava.AddAssemblyContextToComposedStructureRoutine(this.executionState, calledBy, composedStructure, assemblyContext);
    return effect.applyRoutine();
  }
  
  public boolean addConnector(final AssemblyConnector assemblyConnector) {
    mir.routines.pcm2depInjectJava.AddConnectorRoutine effect = new mir.routines.pcm2depInjectJava.AddConnectorRoutine(this.executionState, calledBy, assemblyConnector);
    return effect.applyRoutine();
  }
  
  public boolean addedProvidedDelegationConnector(final ProvidedDelegationConnector providedDelegationConnector, final ComposedStructure pcmSystem) {
    mir.routines.pcm2depInjectJava.AddedProvidedDelegationConnectorRoutine effect = new mir.routines.pcm2depInjectJava.AddedProvidedDelegationConnectorRoutine(this.executionState, calledBy, providedDelegationConnector, pcmSystem);
    return effect.applyRoutine();
  }
  
  public boolean createComponentImplementation(final RepositoryComponent component) {
    mir.routines.pcm2depInjectJava.CreateComponentImplementationRoutine effect = new mir.routines.pcm2depInjectJava.CreateComponentImplementationRoutine(this.executionState, calledBy, component);
    return effect.applyRoutine();
  }
  
  public boolean createImplementationForComponent(final RepositoryComponent component) {
    mir.routines.pcm2depInjectJava.CreateImplementationForComponentRoutine effect = new mir.routines.pcm2depInjectJava.CreateImplementationForComponentRoutine(this.executionState, calledBy, component);
    return effect.applyRoutine();
  }
  
  public boolean renameComponentPackageAndClass(final RepositoryComponent component) {
    mir.routines.pcm2depInjectJava.RenameComponentPackageAndClassRoutine effect = new mir.routines.pcm2depInjectJava.RenameComponentPackageAndClassRoutine(this.executionState, calledBy, component);
    return effect.applyRoutine();
  }
  
  public boolean renameComponentClass(final RepositoryComponent component) {
    mir.routines.pcm2depInjectJava.RenameComponentClassRoutine effect = new mir.routines.pcm2depInjectJava.RenameComponentClassRoutine(this.executionState, calledBy, component);
    return effect.applyRoutine();
  }
  
  public boolean createInterfaceImplementation(final Interface interf) {
    mir.routines.pcm2depInjectJava.CreateInterfaceImplementationRoutine effect = new mir.routines.pcm2depInjectJava.CreateInterfaceImplementationRoutine(this.executionState, calledBy, interf);
    return effect.applyRoutine();
  }
  
  public boolean renameInterface(final OperationInterface interf) {
    mir.routines.pcm2depInjectJava.RenameInterfaceRoutine effect = new mir.routines.pcm2depInjectJava.RenameInterfaceRoutine(this.executionState, calledBy, interf);
    return effect.applyRoutine();
  }
  
  public boolean createCompositeDataTypeImplementation(final CompositeDataType dataType) {
    mir.routines.pcm2depInjectJava.CreateCompositeDataTypeImplementationRoutine effect = new mir.routines.pcm2depInjectJava.CreateCompositeDataTypeImplementationRoutine(this.executionState, calledBy, dataType);
    return effect.applyRoutine();
  }
  
  public boolean renameCompositeDataType(final CompositeDataType compositeDataType) {
    mir.routines.pcm2depInjectJava.RenameCompositeDataTypeRoutine effect = new mir.routines.pcm2depInjectJava.RenameCompositeDataTypeRoutine(this.executionState, calledBy, compositeDataType);
    return effect.applyRoutine();
  }
  
  public boolean createCollectionDataTypeImplementation(final CollectionDataType dataType) {
    mir.routines.pcm2depInjectJava.CreateCollectionDataTypeImplementationRoutine effect = new mir.routines.pcm2depInjectJava.CreateCollectionDataTypeImplementationRoutine(this.executionState, calledBy, dataType);
    return effect.applyRoutine();
  }
  
  public boolean addSuperTypeToDataType(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    mir.routines.pcm2depInjectJava.AddSuperTypeToDataTypeRoutine effect = new mir.routines.pcm2depInjectJava.AddSuperTypeToDataTypeRoutine(this.executionState, calledBy, dataType, innerTypeReference, superTypeQualifiedName);
    return effect.applyRoutine();
  }
  
  public boolean renameCollectionDataType(final CollectionDataType collectionDataType) {
    mir.routines.pcm2depInjectJava.RenameCollectionDataTypeRoutine effect = new mir.routines.pcm2depInjectJava.RenameCollectionDataTypeRoutine(this.executionState, calledBy, collectionDataType);
    return effect.applyRoutine();
  }
  
  public boolean createInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2depInjectJava.CreateInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2depInjectJava.CreateInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    return effect.applyRoutine();
  }
  
  public boolean addInnerDeclarationToCompositeDataType(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    mir.routines.pcm2depInjectJava.AddInnerDeclarationToCompositeDataTypeRoutine effect = new mir.routines.pcm2depInjectJava.AddInnerDeclarationToCompositeDataTypeRoutine(this.executionState, calledBy, dataType, innerDeclaration, dataTypeReference);
    return effect.applyRoutine();
  }
  
  public boolean renameInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2depInjectJava.RenameInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2depInjectJava.RenameInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    return effect.applyRoutine();
  }
  
  public boolean changeTypeOfInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2depInjectJava.ChangeTypeOfInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2depInjectJava.ChangeTypeOfInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    return effect.applyRoutine();
  }
  
  public boolean changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    mir.routines.pcm2depInjectJava.ChangeInnerDeclarationTypeRoutine effect = new mir.routines.pcm2depInjectJava.ChangeInnerDeclarationTypeRoutine(this.executionState, calledBy, innerDeclaration, newTypeReference);
    return effect.applyRoutine();
  }
  
  public boolean createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.pcm2depInjectJava.CreateJavaPackageRoutine effect = new mir.routines.pcm2depInjectJava.CreateJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, parentPackage, packageName, newTag);
    return effect.applyRoutine();
  }
  
  public boolean renameJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2depInjectJava.RenameJavaPackageRoutine effect = new mir.routines.pcm2depInjectJava.RenameJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, parentPackage, packageName, expectedTag);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2depInjectJava.DeleteJavaPackageRoutine effect = new mir.routines.pcm2depInjectJava.DeleteJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, packageName, expectedTag);
    return effect.applyRoutine();
  }
  
  public boolean createJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2depInjectJava.CreateJavaClassRoutine effect = new mir.routines.pcm2depInjectJava.CreateJavaClassRoutine(this.executionState, calledBy, sourceElementMappedToClass, containingPackage, className);
    return effect.applyRoutine();
  }
  
  public boolean createJavaInterface(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2depInjectJava.CreateJavaInterfaceRoutine effect = new mir.routines.pcm2depInjectJava.CreateJavaInterfaceRoutine(this.executionState, calledBy, sourceElementMappedToClass, containingPackage, className);
    return effect.applyRoutine();
  }
  
  public boolean createCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    mir.routines.pcm2depInjectJava.CreateCompilationUnitRoutine effect = new mir.routines.pcm2depInjectJava.CreateCompilationUnitRoutine(this.executionState, calledBy, sourceElementMappedToClass, classifier, containingPackage);
    return effect.applyRoutine();
  }
  
  public boolean renameJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2depInjectJava.RenameJavaClassifierRoutine effect = new mir.routines.pcm2depInjectJava.RenameJavaClassifierRoutine(this.executionState, calledBy, classSourceElement, containingPackage, className);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaClassifier(final NamedElement sourceElement) {
    mir.routines.pcm2depInjectJava.DeleteJavaClassifierRoutine effect = new mir.routines.pcm2depInjectJava.DeleteJavaClassifierRoutine(this.executionState, calledBy, sourceElement);
    return effect.applyRoutine();
  }
  
  public boolean addProvidedRole(final OperationProvidedRole providedRole) {
    mir.routines.pcm2depInjectJava.AddProvidedRoleRoutine effect = new mir.routines.pcm2depInjectJava.AddProvidedRoleRoutine(this.executionState, calledBy, providedRole);
    return effect.applyRoutine();
  }
  
  public boolean removeProvidedRole(final ProvidedRole providedRole) {
    mir.routines.pcm2depInjectJava.RemoveProvidedRoleRoutine effect = new mir.routines.pcm2depInjectJava.RemoveProvidedRoleRoutine(this.executionState, calledBy, providedRole);
    return effect.applyRoutine();
  }
  
  public boolean addRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2depInjectJava.AddRequiredRoleRoutine effect = new mir.routines.pcm2depInjectJava.AddRequiredRoleRoutine(this.executionState, calledBy, requiredRole);
    return effect.applyRoutine();
  }
  
  public boolean addParameterAndAssignmentToConstructor(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName) {
    mir.routines.pcm2depInjectJava.AddParameterAndAssignmentToConstructorRoutine effect = new mir.routines.pcm2depInjectJava.AddParameterAndAssignmentToConstructorRoutine(this.executionState, calledBy, parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName);
    return effect.applyRoutine();
  }
  
  public boolean removeRequiredRole(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    mir.routines.pcm2depInjectJava.RemoveRequiredRoleRoutine effect = new mir.routines.pcm2depInjectJava.RemoveRequiredRoleRoutine(this.executionState, calledBy, requiredRole, requiringEntity);
    return effect.applyRoutine();
  }
  
  public boolean removeParameterToFieldAssignmentFromConstructor(final Constructor ctor, final String fieldName) {
    mir.routines.pcm2depInjectJava.RemoveParameterToFieldAssignmentFromConstructorRoutine effect = new mir.routines.pcm2depInjectJava.RemoveParameterToFieldAssignmentFromConstructorRoutine(this.executionState, calledBy, ctor, fieldName);
    return effect.applyRoutine();
  }
  
  public boolean removeCorrespondingParameterFromConstructor(final Constructor ctor, final NamedElement correspondenceSource) {
    mir.routines.pcm2depInjectJava.RemoveCorrespondingParameterFromConstructorRoutine effect = new mir.routines.pcm2depInjectJava.RemoveCorrespondingParameterFromConstructorRoutine(this.executionState, calledBy, ctor, correspondenceSource);
    return effect.applyRoutine();
  }
  
  public boolean reinitializeOperationRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2depInjectJava.ReinitializeOperationRequiredRoleRoutine effect = new mir.routines.pcm2depInjectJava.ReinitializeOperationRequiredRoleRoutine(this.executionState, calledBy, requiredRole);
    return effect.applyRoutine();
  }
  
  public boolean createMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2depInjectJava.CreateMethodForOperationSignatureRoutine effect = new mir.routines.pcm2depInjectJava.CreateMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean renameMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2depInjectJava.RenameMethodForOperationSignatureRoutine effect = new mir.routines.pcm2depInjectJava.RenameMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean changeReturnTypeOfMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2depInjectJava.ChangeReturnTypeOfMethodForOperationSignatureRoutine effect = new mir.routines.pcm2depInjectJava.ChangeReturnTypeOfMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean changeInterfaceMethodReturnType(final InterfaceMethod interfaceMethod, final DataType returnType) {
    mir.routines.pcm2depInjectJava.ChangeInterfaceMethodReturnTypeRoutine effect = new mir.routines.pcm2depInjectJava.ChangeInterfaceMethodReturnTypeRoutine(this.executionState, calledBy, interfaceMethod, returnType);
    return effect.applyRoutine();
  }
  
  public boolean deleteMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2depInjectJava.DeleteMethodForOperationSignatureRoutine effect = new mir.routines.pcm2depInjectJava.DeleteMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean createParameter(final Parameter parameter) {
    mir.routines.pcm2depInjectJava.CreateParameterRoutine effect = new mir.routines.pcm2depInjectJava.CreateParameterRoutine(this.executionState, calledBy, parameter);
    return effect.applyRoutine();
  }
  
  public boolean renameParameter(final Parameter parameter) {
    mir.routines.pcm2depInjectJava.RenameParameterRoutine effect = new mir.routines.pcm2depInjectJava.RenameParameterRoutine(this.executionState, calledBy, parameter);
    return effect.applyRoutine();
  }
  
  public boolean changeParameterType(final Parameter parameter) {
    mir.routines.pcm2depInjectJava.ChangeParameterTypeRoutine effect = new mir.routines.pcm2depInjectJava.ChangeParameterTypeRoutine(this.executionState, calledBy, parameter);
    return effect.applyRoutine();
  }
  
  public boolean deleteParameter(final OperationSignature signature, final Parameter parameter) {
    mir.routines.pcm2depInjectJava.DeleteParameterRoutine effect = new mir.routines.pcm2depInjectJava.DeleteParameterRoutine(this.executionState, calledBy, signature, parameter);
    return effect.applyRoutine();
  }
  
  public boolean createSEFF(final ServiceEffectSpecification seff) {
    mir.routines.pcm2depInjectJava.CreateSEFFRoutine effect = new mir.routines.pcm2depInjectJava.CreateSEFFRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
  
  public boolean changeMethodForSeff(final ResourceDemandingSEFF seff) {
    mir.routines.pcm2depInjectJava.ChangeMethodForSeffRoutine effect = new mir.routines.pcm2depInjectJava.ChangeMethodForSeffRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
  
  public boolean updateSEFFImplementingMethodName(final ServiceEffectSpecification seff) {
    mir.routines.pcm2depInjectJava.UpdateSEFFImplementingMethodNameRoutine effect = new mir.routines.pcm2depInjectJava.UpdateSEFFImplementingMethodNameRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
  
  public boolean deleteMethodForSeff(final ServiceEffectSpecification seff) {
    mir.routines.pcm2depInjectJava.DeleteMethodForSeffRoutine effect = new mir.routines.pcm2depInjectJava.DeleteMethodForSeffRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
}
