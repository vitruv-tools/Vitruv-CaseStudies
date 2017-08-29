package mir.routines.pcm2java;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
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
    mir.routines.pcm2java.CreateRepositorySubPackagesRoutine effect = new mir.routines.pcm2java.CreateRepositorySubPackagesRoutine(this.executionState, calledBy, repository);
    return effect.applyRoutine();
  }
  
  public boolean renamePackageForRepository(final Repository repository) {
    mir.routines.pcm2java.RenamePackageForRepositoryRoutine effect = new mir.routines.pcm2java.RenamePackageForRepositoryRoutine(this.executionState, calledBy, repository);
    return effect.applyRoutine();
  }
  
  public boolean createImplementationForSystem(final org.palladiosimulator.pcm.system.System system) {
    mir.routines.pcm2java.CreateImplementationForSystemRoutine effect = new mir.routines.pcm2java.CreateImplementationForSystemRoutine(this.executionState, calledBy, system);
    return effect.applyRoutine();
  }
  
  public boolean changeSystemImplementationName(final org.palladiosimulator.pcm.system.System system) {
    mir.routines.pcm2java.ChangeSystemImplementationNameRoutine effect = new mir.routines.pcm2java.ChangeSystemImplementationNameRoutine(this.executionState, calledBy, system);
    return effect.applyRoutine();
  }
  
  public boolean addAssemblyContextToComposedStructure(final ComposedStructure composedStructure, final AssemblyContext assemblyContext) {
    mir.routines.pcm2java.AddAssemblyContextToComposedStructureRoutine effect = new mir.routines.pcm2java.AddAssemblyContextToComposedStructureRoutine(this.executionState, calledBy, composedStructure, assemblyContext);
    return effect.applyRoutine();
  }
  
  public boolean createComponentImplementation(final RepositoryComponent component) {
    mir.routines.pcm2java.CreateComponentImplementationRoutine effect = new mir.routines.pcm2java.CreateComponentImplementationRoutine(this.executionState, calledBy, component);
    return effect.applyRoutine();
  }
  
  public boolean createImplementationForComponent(final RepositoryComponent component) {
    mir.routines.pcm2java.CreateImplementationForComponentRoutine effect = new mir.routines.pcm2java.CreateImplementationForComponentRoutine(this.executionState, calledBy, component);
    return effect.applyRoutine();
  }
  
  public boolean renameComponentPackageAndClass(final RepositoryComponent component) {
    mir.routines.pcm2java.RenameComponentPackageAndClassRoutine effect = new mir.routines.pcm2java.RenameComponentPackageAndClassRoutine(this.executionState, calledBy, component);
    return effect.applyRoutine();
  }
  
  public boolean renameComponentClass(final RepositoryComponent component) {
    mir.routines.pcm2java.RenameComponentClassRoutine effect = new mir.routines.pcm2java.RenameComponentClassRoutine(this.executionState, calledBy, component);
    return effect.applyRoutine();
  }
  
  public boolean createInterfaceImplementation(final Interface interf) {
    mir.routines.pcm2java.CreateInterfaceImplementationRoutine effect = new mir.routines.pcm2java.CreateInterfaceImplementationRoutine(this.executionState, calledBy, interf);
    return effect.applyRoutine();
  }
  
  public boolean renameInterface(final OperationInterface interf) {
    mir.routines.pcm2java.RenameInterfaceRoutine effect = new mir.routines.pcm2java.RenameInterfaceRoutine(this.executionState, calledBy, interf);
    return effect.applyRoutine();
  }
  
  public boolean createCompositeDataTypeImplementation(final CompositeDataType dataType) {
    mir.routines.pcm2java.CreateCompositeDataTypeImplementationRoutine effect = new mir.routines.pcm2java.CreateCompositeDataTypeImplementationRoutine(this.executionState, calledBy, dataType);
    return effect.applyRoutine();
  }
  
  public boolean renameCompositeDataType(final CompositeDataType compositeDataType) {
    mir.routines.pcm2java.RenameCompositeDataTypeRoutine effect = new mir.routines.pcm2java.RenameCompositeDataTypeRoutine(this.executionState, calledBy, compositeDataType);
    return effect.applyRoutine();
  }
  
  public boolean createCollectionDataTypeImplementation(final CollectionDataType dataType) {
    mir.routines.pcm2java.CreateCollectionDataTypeImplementationRoutine effect = new mir.routines.pcm2java.CreateCollectionDataTypeImplementationRoutine(this.executionState, calledBy, dataType);
    return effect.applyRoutine();
  }
  
  public boolean addSuperTypeToDataType(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    mir.routines.pcm2java.AddSuperTypeToDataTypeRoutine effect = new mir.routines.pcm2java.AddSuperTypeToDataTypeRoutine(this.executionState, calledBy, dataType, innerTypeReference, superTypeQualifiedName);
    return effect.applyRoutine();
  }
  
  public boolean renameCollectionDataType(final CollectionDataType collectionDataType) {
    mir.routines.pcm2java.RenameCollectionDataTypeRoutine effect = new mir.routines.pcm2java.RenameCollectionDataTypeRoutine(this.executionState, calledBy, collectionDataType);
    return effect.applyRoutine();
  }
  
  public boolean createInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2java.CreateInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2java.CreateInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    return effect.applyRoutine();
  }
  
  public boolean addInnerDeclarationToCompositeDataType(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    mir.routines.pcm2java.AddInnerDeclarationToCompositeDataTypeRoutine effect = new mir.routines.pcm2java.AddInnerDeclarationToCompositeDataTypeRoutine(this.executionState, calledBy, dataType, innerDeclaration, dataTypeReference);
    return effect.applyRoutine();
  }
  
  public boolean renameInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2java.RenameInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2java.RenameInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    return effect.applyRoutine();
  }
  
  public boolean changeTypeOfInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2java.ChangeTypeOfInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2java.ChangeTypeOfInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    return effect.applyRoutine();
  }
  
  public boolean changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    mir.routines.pcm2java.ChangeInnerDeclarationTypeRoutine effect = new mir.routines.pcm2java.ChangeInnerDeclarationTypeRoutine(this.executionState, calledBy, innerDeclaration, newTypeReference);
    return effect.applyRoutine();
  }
  
  public boolean createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.pcm2java.CreateJavaPackageRoutine effect = new mir.routines.pcm2java.CreateJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, parentPackage, packageName, newTag);
    return effect.applyRoutine();
  }
  
  public boolean renameJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2java.RenameJavaPackageRoutine effect = new mir.routines.pcm2java.RenameJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, parentPackage, packageName, expectedTag);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2java.DeleteJavaPackageRoutine effect = new mir.routines.pcm2java.DeleteJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, packageName, expectedTag);
    return effect.applyRoutine();
  }
  
  public boolean createJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2java.CreateJavaClassRoutine effect = new mir.routines.pcm2java.CreateJavaClassRoutine(this.executionState, calledBy, sourceElementMappedToClass, containingPackage, className);
    return effect.applyRoutine();
  }
  
  public boolean createJavaInterface(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2java.CreateJavaInterfaceRoutine effect = new mir.routines.pcm2java.CreateJavaInterfaceRoutine(this.executionState, calledBy, sourceElementMappedToClass, containingPackage, className);
    return effect.applyRoutine();
  }
  
  public boolean createCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    mir.routines.pcm2java.CreateCompilationUnitRoutine effect = new mir.routines.pcm2java.CreateCompilationUnitRoutine(this.executionState, calledBy, sourceElementMappedToClass, classifier, containingPackage);
    return effect.applyRoutine();
  }
  
  public boolean renameJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2java.RenameJavaClassifierRoutine effect = new mir.routines.pcm2java.RenameJavaClassifierRoutine(this.executionState, calledBy, classSourceElement, containingPackage, className);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaClassifier(final NamedElement sourceElement) {
    mir.routines.pcm2java.DeleteJavaClassifierRoutine effect = new mir.routines.pcm2java.DeleteJavaClassifierRoutine(this.executionState, calledBy, sourceElement);
    return effect.applyRoutine();
  }
  
  public boolean addProvidedRole(final OperationProvidedRole providedRole) {
    mir.routines.pcm2java.AddProvidedRoleRoutine effect = new mir.routines.pcm2java.AddProvidedRoleRoutine(this.executionState, calledBy, providedRole);
    return effect.applyRoutine();
  }
  
  public boolean removeProvidedRole(final ProvidedRole providedRole) {
    mir.routines.pcm2java.RemoveProvidedRoleRoutine effect = new mir.routines.pcm2java.RemoveProvidedRoleRoutine(this.executionState, calledBy, providedRole);
    return effect.applyRoutine();
  }
  
  public boolean addRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2java.AddRequiredRoleRoutine effect = new mir.routines.pcm2java.AddRequiredRoleRoutine(this.executionState, calledBy, requiredRole);
    return effect.applyRoutine();
  }
  
  public boolean addParameterAndAssignmentToConstructor(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName) {
    mir.routines.pcm2java.AddParameterAndAssignmentToConstructorRoutine effect = new mir.routines.pcm2java.AddParameterAndAssignmentToConstructorRoutine(this.executionState, calledBy, parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName);
    return effect.applyRoutine();
  }
  
  public boolean removeRequiredRole(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    mir.routines.pcm2java.RemoveRequiredRoleRoutine effect = new mir.routines.pcm2java.RemoveRequiredRoleRoutine(this.executionState, calledBy, requiredRole, requiringEntity);
    return effect.applyRoutine();
  }
  
  public boolean removeParameterToFieldAssignmentFromConstructor(final Constructor ctor, final String fieldName) {
    mir.routines.pcm2java.RemoveParameterToFieldAssignmentFromConstructorRoutine effect = new mir.routines.pcm2java.RemoveParameterToFieldAssignmentFromConstructorRoutine(this.executionState, calledBy, ctor, fieldName);
    return effect.applyRoutine();
  }
  
  public boolean removeCorrespondingParameterFromConstructor(final Constructor ctor, final NamedElement correspondenceSource) {
    mir.routines.pcm2java.RemoveCorrespondingParameterFromConstructorRoutine effect = new mir.routines.pcm2java.RemoveCorrespondingParameterFromConstructorRoutine(this.executionState, calledBy, ctor, correspondenceSource);
    return effect.applyRoutine();
  }
  
  public boolean reinitializeOperationRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2java.ReinitializeOperationRequiredRoleRoutine effect = new mir.routines.pcm2java.ReinitializeOperationRequiredRoleRoutine(this.executionState, calledBy, requiredRole);
    return effect.applyRoutine();
  }
  
  public boolean createMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2java.CreateMethodForOperationSignatureRoutine effect = new mir.routines.pcm2java.CreateMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean renameMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2java.RenameMethodForOperationSignatureRoutine effect = new mir.routines.pcm2java.RenameMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean changeReturnTypeOfMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2java.ChangeReturnTypeOfMethodForOperationSignatureRoutine effect = new mir.routines.pcm2java.ChangeReturnTypeOfMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean changeInterfaceMethodReturnType(final InterfaceMethod interfaceMethod, final DataType returnType) {
    mir.routines.pcm2java.ChangeInterfaceMethodReturnTypeRoutine effect = new mir.routines.pcm2java.ChangeInterfaceMethodReturnTypeRoutine(this.executionState, calledBy, interfaceMethod, returnType);
    return effect.applyRoutine();
  }
  
  public boolean deleteMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2java.DeleteMethodForOperationSignatureRoutine effect = new mir.routines.pcm2java.DeleteMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean createParameter(final Parameter parameter) {
    mir.routines.pcm2java.CreateParameterRoutine effect = new mir.routines.pcm2java.CreateParameterRoutine(this.executionState, calledBy, parameter);
    return effect.applyRoutine();
  }
  
  public boolean renameParameter(final Parameter parameter) {
    mir.routines.pcm2java.RenameParameterRoutine effect = new mir.routines.pcm2java.RenameParameterRoutine(this.executionState, calledBy, parameter);
    return effect.applyRoutine();
  }
  
  public boolean changeParameterType(final Parameter parameter) {
    mir.routines.pcm2java.ChangeParameterTypeRoutine effect = new mir.routines.pcm2java.ChangeParameterTypeRoutine(this.executionState, calledBy, parameter);
    return effect.applyRoutine();
  }
  
  public boolean deleteParameter(final OperationSignature signature, final Parameter parameter) {
    mir.routines.pcm2java.DeleteParameterRoutine effect = new mir.routines.pcm2java.DeleteParameterRoutine(this.executionState, calledBy, signature, parameter);
    return effect.applyRoutine();
  }
  
  public boolean createSEFF(final ServiceEffectSpecification seff) {
    mir.routines.pcm2java.CreateSEFFRoutine effect = new mir.routines.pcm2java.CreateSEFFRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
  
  public boolean changeMethodForSeff(final ResourceDemandingSEFF seff) {
    mir.routines.pcm2java.ChangeMethodForSeffRoutine effect = new mir.routines.pcm2java.ChangeMethodForSeffRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
  
  public boolean updateSEFFImplementingMethodName(final ServiceEffectSpecification seff) {
    mir.routines.pcm2java.UpdateSEFFImplementingMethodNameRoutine effect = new mir.routines.pcm2java.UpdateSEFFImplementingMethodNameRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
  
  public boolean deleteMethodForSeff(final ServiceEffectSpecification seff) {
    mir.routines.pcm2java.DeleteMethodForSeffRoutine effect = new mir.routines.pcm2java.DeleteMethodForSeffRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
}
