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
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createRepositorySubPackages(final Repository repository) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createRepositorySubPackages(repository);
  }
  
  public boolean renamePackageForRepository(final Repository repository) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.renamePackageForRepository(repository);
  }
  
  public boolean createImplementationForSystem(final org.palladiosimulator.pcm.system.System system) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createImplementationForSystem(system);
  }
  
  public boolean changeSystemImplementationName(final org.palladiosimulator.pcm.system.System system) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.changeSystemImplementationName(system);
  }
  
  public boolean addAssemblyContextToComposedStructure(final ComposedStructure composedStructure, final AssemblyContext assemblyContext) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.addAssemblyContextToComposedStructure(composedStructure, assemblyContext);
  }
  
  public boolean createComponentImplementation(final RepositoryComponent component) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createComponentImplementation(component);
  }
  
  public boolean createImplementationForComponent(final RepositoryComponent component) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createImplementationForComponent(component);
  }
  
  public boolean renameComponentPackageAndClass(final RepositoryComponent component) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.renameComponentPackageAndClass(component);
  }
  
  public boolean renameComponentClass(final RepositoryComponent component) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.renameComponentClass(component);
  }
  
  public boolean createInterfaceImplementation(final Interface interf) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createInterfaceImplementation(interf);
  }
  
  public boolean renameInterface(final OperationInterface interf) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.renameInterface(interf);
  }
  
  public boolean createCompositeDataTypeImplementation(final CompositeDataType compositeDataType) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createCompositeDataTypeImplementation(compositeDataType);
  }
  
  public boolean renameCompositeDataType(final CompositeDataType compositeDataType) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.renameCompositeDataType(compositeDataType);
  }
  
  public boolean createCollectionDataTypeImplementation(final CollectionDataType dataType) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createCollectionDataTypeImplementation(dataType);
  }
  
  public boolean addSuperTypeToDataType(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.addSuperTypeToDataType(dataType, innerTypeReference, superTypeQualifiedName);
  }
  
  public boolean renameCollectionDataType(final CollectionDataType collectionDataType) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.renameCollectionDataType(collectionDataType);
  }
  
  public boolean createInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createInnerDeclarationImplementation(innerDeclaration);
  }
  
  public boolean addInnerDeclarationToCompositeDataType(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.addInnerDeclarationToCompositeDataType(dataType, innerDeclaration, dataTypeReference);
  }
  
  public boolean renameInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.renameInnerDeclarationImplementation(innerDeclaration);
  }
  
  public boolean changeTypeOfInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.changeTypeOfInnerDeclarationImplementation(innerDeclaration);
  }
  
  public boolean changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.changeInnerDeclarationType(innerDeclaration, newTypeReference);
  }
  
  public boolean createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createJavaPackage(sourceElementMappedToPackage, parentPackage, packageName, newTag);
  }
  
  public boolean renameJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.renameJavaPackage(sourceElementMappedToPackage, parentPackage, packageName, expectedTag);
  }
  
  public boolean deleteJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.deleteJavaPackage(sourceElementMappedToPackage, packageName, expectedTag);
  }
  
  public boolean createJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createJavaClass(sourceElementMappedToClass, containingPackage, className);
  }
  
  public boolean createJavaInterface(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createJavaInterface(sourceElementMappedToClass, containingPackage, className);
  }
  
  public boolean createCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createCompilationUnit(sourceElementMappedToClass, classifier, containingPackage);
  }
  
  public boolean renameJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.renameJavaClassifier(classSourceElement, containingPackage, className);
  }
  
  public boolean deleteJavaClassifier(final NamedElement sourceElement) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.deleteJavaClassifier(sourceElement);
  }
  
  public boolean addProvidedRole(final OperationProvidedRole providedRole) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.addProvidedRole(providedRole);
  }
  
  public boolean removeProvidedRole(final ProvidedRole providedRole) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.removeProvidedRole(providedRole);
  }
  
  public boolean addRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.addRequiredRole(requiredRole);
  }
  
  public boolean addParameterAndAssignmentToConstructor(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.addParameterAndAssignmentToConstructor(parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName);
  }
  
  public boolean removeRequiredRole(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.removeRequiredRole(requiredRole, requiringEntity);
  }
  
  public boolean removeParameterToFieldAssignmentFromConstructor(final Constructor ctor, final String fieldName) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.removeParameterToFieldAssignmentFromConstructor(ctor, fieldName);
  }
  
  public boolean removeCorrespondingParameterFromConstructor(final Constructor ctor, final NamedElement correspondenceSource) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.removeCorrespondingParameterFromConstructor(ctor, correspondenceSource);
  }
  
  public boolean reinitializeOperationRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.reinitializeOperationRequiredRole(requiredRole);
  }
  
  public boolean createMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createMethodForOperationSignature(operationSignature);
  }
  
  public boolean renameMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.renameMethodForOperationSignature(operationSignature);
  }
  
  public boolean changeReturnTypeOfMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.changeReturnTypeOfMethodForOperationSignature(operationSignature);
  }
  
  public boolean changeInterfaceMethodReturnType(final InterfaceMethod interfaceMethod, final DataType returnType) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.changeInterfaceMethodReturnType(interfaceMethod, returnType);
  }
  
  public boolean deleteMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.deleteMethodForOperationSignature(operationSignature);
  }
  
  public boolean createParameter(final Parameter parameter) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createParameter(parameter);
  }
  
  public boolean renameParameter(final Parameter parameter) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.renameParameter(parameter);
  }
  
  public boolean changeParameterType(final Parameter parameter) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.changeParameterType(parameter);
  }
  
  public boolean deleteParameter(final OperationSignature signature, final Parameter parameter) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.deleteParameter(signature, parameter);
  }
  
  public boolean createSEFF(final ServiceEffectSpecification seff) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.createSEFF(seff);
  }
  
  public boolean changeMethodForSeff(final ResourceDemandingSEFF seff) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.changeMethodForSeff(seff);
  }
  
  public boolean updateSEFFImplementingMethodName(final ServiceEffectSpecification seff) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.updateSEFFImplementingMethodName(seff);
  }
  
  public boolean deleteMethodForSeff(final ServiceEffectSpecification seff) {
    mir.routines.pcm2javaCommon.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcm2javaCommon")));
    return _routinesFacade.deleteMethodForSeff(seff);
  }
}
