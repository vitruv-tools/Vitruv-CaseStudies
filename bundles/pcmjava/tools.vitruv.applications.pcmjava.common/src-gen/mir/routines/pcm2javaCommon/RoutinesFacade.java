package mir.routines.pcm2javaCommon;

import mir.routines.pcm2javaCommon.AddAssemblyContextToComposedStructureRoutine;
import mir.routines.pcm2javaCommon.AddInnerDeclarationToCompositeDataTypeRoutine;
import mir.routines.pcm2javaCommon.AddParameterAndAssignmentToConstructorRoutine;
import mir.routines.pcm2javaCommon.AddProvidedRoleRoutine;
import mir.routines.pcm2javaCommon.AddRequiredRoleRoutine;
import mir.routines.pcm2javaCommon.AddSuperTypeToDataTypeRoutine;
import mir.routines.pcm2javaCommon.ChangeInnerDeclarationTypeRoutine;
import mir.routines.pcm2javaCommon.ChangeInterfaceMethodReturnTypeRoutine;
import mir.routines.pcm2javaCommon.ChangeMethodForSeffRoutine;
import mir.routines.pcm2javaCommon.ChangeParameterTypeRoutine;
import mir.routines.pcm2javaCommon.ChangeReturnTypeOfMethodForOperationSignatureRoutine;
import mir.routines.pcm2javaCommon.ChangeSystemImplementationNameRoutine;
import mir.routines.pcm2javaCommon.ChangeTypeOfInnerDeclarationImplementationRoutine;
import mir.routines.pcm2javaCommon.CreateCollectionDataTypeImplementationRoutine;
import mir.routines.pcm2javaCommon.CreateCompilationUnitRoutine;
import mir.routines.pcm2javaCommon.CreateComponentImplementationRoutine;
import mir.routines.pcm2javaCommon.CreateCompositeDataTypeImplementationRoutine;
import mir.routines.pcm2javaCommon.CreateImplementationForComponentRoutine;
import mir.routines.pcm2javaCommon.CreateImplementationForSystemRoutine;
import mir.routines.pcm2javaCommon.CreateInnerDeclarationImplementationRoutine;
import mir.routines.pcm2javaCommon.CreateInterfaceImplementationRoutine;
import mir.routines.pcm2javaCommon.CreateJavaClassRoutine;
import mir.routines.pcm2javaCommon.CreateJavaInterfaceRoutine;
import mir.routines.pcm2javaCommon.CreateJavaPackageRoutine;
import mir.routines.pcm2javaCommon.CreateMethodForOperationSignatureRoutine;
import mir.routines.pcm2javaCommon.CreateParameterRoutine;
import mir.routines.pcm2javaCommon.CreateRepositorySubPackagesRoutine;
import mir.routines.pcm2javaCommon.CreateSEFFRoutine;
import mir.routines.pcm2javaCommon.DeleteJavaClassifierRoutine;
import mir.routines.pcm2javaCommon.DeleteJavaPackageRoutine;
import mir.routines.pcm2javaCommon.DeleteMethodForOperationSignatureRoutine;
import mir.routines.pcm2javaCommon.DeleteMethodForSeffRoutine;
import mir.routines.pcm2javaCommon.DeleteParameterRoutine;
import mir.routines.pcm2javaCommon.ReinitializeOperationRequiredRoleRoutine;
import mir.routines.pcm2javaCommon.RemoveCorrespondingParameterFromConstructorRoutine;
import mir.routines.pcm2javaCommon.RemoveParameterToFieldAssignmentFromConstructorRoutine;
import mir.routines.pcm2javaCommon.RemoveProvidedRoleRoutine;
import mir.routines.pcm2javaCommon.RemoveRequiredRoleRoutine;
import mir.routines.pcm2javaCommon.RenameCollectionDataTypeRoutine;
import mir.routines.pcm2javaCommon.RenameComponentClassRoutine;
import mir.routines.pcm2javaCommon.RenameComponentPackageAndClassRoutine;
import mir.routines.pcm2javaCommon.RenameCompositeDataTypeRoutine;
import mir.routines.pcm2javaCommon.RenameInnerDeclarationImplementationRoutine;
import mir.routines.pcm2javaCommon.RenameInterfaceRoutine;
import mir.routines.pcm2javaCommon.RenameJavaClassifierRoutine;
import mir.routines.pcm2javaCommon.RenameJavaPackageRoutine;
import mir.routines.pcm2javaCommon.RenameMethodForOperationSignatureRoutine;
import mir.routines.pcm2javaCommon.RenamePackageForRepositoryRoutine;
import mir.routines.pcm2javaCommon.RenameParameterRoutine;
import mir.routines.pcm2javaCommon.UpdateSEFFImplementingMethodNameRoutine;
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
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createRepositorySubPackages(final Repository repository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateRepositorySubPackagesRoutine routine = new CreateRepositorySubPackagesRoutine(_routinesFacade, _reactionExecutionState, _caller, repository);
    return routine.applyRoutine();
  }
  
  public boolean renamePackageForRepository(final Repository repository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenamePackageForRepositoryRoutine routine = new RenamePackageForRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, repository);
    return routine.applyRoutine();
  }
  
  public boolean createImplementationForSystem(final org.palladiosimulator.pcm.system.System system) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateImplementationForSystemRoutine routine = new CreateImplementationForSystemRoutine(_routinesFacade, _reactionExecutionState, _caller, system);
    return routine.applyRoutine();
  }
  
  public boolean changeSystemImplementationName(final org.palladiosimulator.pcm.system.System system) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeSystemImplementationNameRoutine routine = new ChangeSystemImplementationNameRoutine(_routinesFacade, _reactionExecutionState, _caller, system);
    return routine.applyRoutine();
  }
  
  public boolean addAssemblyContextToComposedStructure(final ComposedStructure composedStructure, final AssemblyContext assemblyContext) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddAssemblyContextToComposedStructureRoutine routine = new AddAssemblyContextToComposedStructureRoutine(_routinesFacade, _reactionExecutionState, _caller, composedStructure, assemblyContext);
    return routine.applyRoutine();
  }
  
  public boolean createComponentImplementation(final RepositoryComponent component) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateComponentImplementationRoutine routine = new CreateComponentImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, component);
    return routine.applyRoutine();
  }
  
  public boolean createImplementationForComponent(final RepositoryComponent component) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateImplementationForComponentRoutine routine = new CreateImplementationForComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, component);
    return routine.applyRoutine();
  }
  
  public boolean renameComponentPackageAndClass(final RepositoryComponent component) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameComponentPackageAndClassRoutine routine = new RenameComponentPackageAndClassRoutine(_routinesFacade, _reactionExecutionState, _caller, component);
    return routine.applyRoutine();
  }
  
  public boolean renameComponentClass(final RepositoryComponent component) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameComponentClassRoutine routine = new RenameComponentClassRoutine(_routinesFacade, _reactionExecutionState, _caller, component);
    return routine.applyRoutine();
  }
  
  public boolean createInterfaceImplementation(final Interface interf) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateInterfaceImplementationRoutine routine = new CreateInterfaceImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, interf);
    return routine.applyRoutine();
  }
  
  public boolean renameInterface(final OperationInterface interf) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameInterfaceRoutine routine = new RenameInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, interf);
    return routine.applyRoutine();
  }
  
  public boolean createCompositeDataTypeImplementation(final CompositeDataType compositeDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCompositeDataTypeImplementationRoutine routine = new CreateCompositeDataTypeImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, compositeDataType);
    return routine.applyRoutine();
  }
  
  public boolean renameCompositeDataType(final CompositeDataType compositeDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameCompositeDataTypeRoutine routine = new RenameCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, compositeDataType);
    return routine.applyRoutine();
  }
  
  public boolean createCollectionDataTypeImplementation(final CollectionDataType dataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCollectionDataTypeImplementationRoutine routine = new CreateCollectionDataTypeImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType);
    return routine.applyRoutine();
  }
  
  public boolean addSuperTypeToDataType(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddSuperTypeToDataTypeRoutine routine = new AddSuperTypeToDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType, innerTypeReference, superTypeQualifiedName);
    return routine.applyRoutine();
  }
  
  public boolean renameCollectionDataType(final CollectionDataType collectionDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameCollectionDataTypeRoutine routine = new RenameCollectionDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, collectionDataType);
    return routine.applyRoutine();
  }
  
  public boolean createInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateInnerDeclarationImplementationRoutine routine = new CreateInnerDeclarationImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, innerDeclaration);
    return routine.applyRoutine();
  }
  
  public boolean addInnerDeclarationToCompositeDataType(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddInnerDeclarationToCompositeDataTypeRoutine routine = new AddInnerDeclarationToCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType, innerDeclaration, dataTypeReference);
    return routine.applyRoutine();
  }
  
  public boolean renameInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameInnerDeclarationImplementationRoutine routine = new RenameInnerDeclarationImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, innerDeclaration);
    return routine.applyRoutine();
  }
  
  public boolean changeTypeOfInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeTypeOfInnerDeclarationImplementationRoutine routine = new ChangeTypeOfInnerDeclarationImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, innerDeclaration);
    return routine.applyRoutine();
  }
  
  public boolean changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeInnerDeclarationTypeRoutine routine = new ChangeInnerDeclarationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, innerDeclaration, newTypeReference);
    return routine.applyRoutine();
  }
  
  public boolean createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaPackageRoutine routine = new CreateJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, sourceElementMappedToPackage, parentPackage, packageName, newTag);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameJavaPackageRoutine routine = new RenameJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, sourceElementMappedToPackage, parentPackage, packageName, expectedTag);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaPackageRoutine routine = new DeleteJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, sourceElementMappedToPackage, packageName, expectedTag);
    return routine.applyRoutine();
  }
  
  public boolean createJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaClassRoutine routine = new CreateJavaClassRoutine(_routinesFacade, _reactionExecutionState, _caller, sourceElementMappedToClass, containingPackage, className);
    return routine.applyRoutine();
  }
  
  public boolean createJavaInterface(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaInterfaceRoutine routine = new CreateJavaInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, sourceElementMappedToClass, containingPackage, className);
    return routine.applyRoutine();
  }
  
  public boolean createCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCompilationUnitRoutine routine = new CreateCompilationUnitRoutine(_routinesFacade, _reactionExecutionState, _caller, sourceElementMappedToClass, classifier, containingPackage);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameJavaClassifierRoutine routine = new RenameJavaClassifierRoutine(_routinesFacade, _reactionExecutionState, _caller, classSourceElement, containingPackage, className);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaClassifier(final NamedElement sourceElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaClassifierRoutine routine = new DeleteJavaClassifierRoutine(_routinesFacade, _reactionExecutionState, _caller, sourceElement);
    return routine.applyRoutine();
  }
  
  public boolean addProvidedRole(final OperationProvidedRole providedRole) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddProvidedRoleRoutine routine = new AddProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, providedRole);
    return routine.applyRoutine();
  }
  
  public boolean removeProvidedRole(final ProvidedRole providedRole) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveProvidedRoleRoutine routine = new RemoveProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, providedRole);
    return routine.applyRoutine();
  }
  
  public boolean addRequiredRole(final OperationRequiredRole requiredRole) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddRequiredRoleRoutine routine = new AddRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, requiredRole);
    return routine.applyRoutine();
  }
  
  public boolean addParameterAndAssignmentToConstructor(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddParameterAndAssignmentToConstructorRoutine routine = new AddParameterAndAssignmentToConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName);
    return routine.applyRoutine();
  }
  
  public boolean removeRequiredRole(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveRequiredRoleRoutine routine = new RemoveRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, requiredRole, requiringEntity);
    return routine.applyRoutine();
  }
  
  public boolean removeParameterToFieldAssignmentFromConstructor(final Constructor ctor, final String fieldName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveParameterToFieldAssignmentFromConstructorRoutine routine = new RemoveParameterToFieldAssignmentFromConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, ctor, fieldName);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingParameterFromConstructor(final Constructor ctor, final NamedElement correspondenceSource) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingParameterFromConstructorRoutine routine = new RemoveCorrespondingParameterFromConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, ctor, correspondenceSource);
    return routine.applyRoutine();
  }
  
  public boolean reinitializeOperationRequiredRole(final OperationRequiredRole requiredRole) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ReinitializeOperationRequiredRoleRoutine routine = new ReinitializeOperationRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, requiredRole);
    return routine.applyRoutine();
  }
  
  public boolean createMethodForOperationSignature(final OperationSignature operationSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateMethodForOperationSignatureRoutine routine = new CreateMethodForOperationSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, operationSignature);
    return routine.applyRoutine();
  }
  
  public boolean renameMethodForOperationSignature(final OperationSignature operationSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameMethodForOperationSignatureRoutine routine = new RenameMethodForOperationSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, operationSignature);
    return routine.applyRoutine();
  }
  
  public boolean changeReturnTypeOfMethodForOperationSignature(final OperationSignature operationSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeReturnTypeOfMethodForOperationSignatureRoutine routine = new ChangeReturnTypeOfMethodForOperationSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, operationSignature);
    return routine.applyRoutine();
  }
  
  public boolean changeInterfaceMethodReturnType(final InterfaceMethod interfaceMethod, final DataType returnType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeInterfaceMethodReturnTypeRoutine routine = new ChangeInterfaceMethodReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, interfaceMethod, returnType);
    return routine.applyRoutine();
  }
  
  public boolean deleteMethodForOperationSignature(final OperationSignature operationSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteMethodForOperationSignatureRoutine routine = new DeleteMethodForOperationSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, operationSignature);
    return routine.applyRoutine();
  }
  
  public boolean createParameter(final Parameter parameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateParameterRoutine routine = new CreateParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, parameter);
    return routine.applyRoutine();
  }
  
  public boolean renameParameter(final Parameter parameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameParameterRoutine routine = new RenameParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, parameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterType(final Parameter parameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeParameterTypeRoutine routine = new ChangeParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, parameter);
    return routine.applyRoutine();
  }
  
  public boolean deleteParameter(final OperationSignature signature, final Parameter parameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteParameterRoutine routine = new DeleteParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, signature, parameter);
    return routine.applyRoutine();
  }
  
  public boolean createSEFF(final ServiceEffectSpecification seff) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateSEFFRoutine routine = new CreateSEFFRoutine(_routinesFacade, _reactionExecutionState, _caller, seff);
    return routine.applyRoutine();
  }
  
  public boolean changeMethodForSeff(final ResourceDemandingSEFF seff) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeMethodForSeffRoutine routine = new ChangeMethodForSeffRoutine(_routinesFacade, _reactionExecutionState, _caller, seff);
    return routine.applyRoutine();
  }
  
  public boolean updateSEFFImplementingMethodName(final ServiceEffectSpecification seff) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateSEFFImplementingMethodNameRoutine routine = new UpdateSEFFImplementingMethodNameRoutine(_routinesFacade, _reactionExecutionState, _caller, seff);
    return routine.applyRoutine();
  }
  
  public boolean deleteMethodForSeff(final ServiceEffectSpecification seff) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteMethodForSeffRoutine routine = new DeleteMethodForSeffRoutine(_routinesFacade, _reactionExecutionState, _caller, seff);
    return routine.applyRoutine();
  }
}
