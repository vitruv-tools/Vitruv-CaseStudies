package mir.routines.pcm2javaCommon;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
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
    mir.routines.pcm2javaCommon.CreateRepositorySubPackagesRoutine effect = new mir.routines.pcm2javaCommon.CreateRepositorySubPackagesRoutine(this.executionState, calledBy, repository);
    return effect.applyRoutine();
  }
  
  public boolean renamePackageForRepository(final Repository repository) {
    mir.routines.pcm2javaCommon.RenamePackageForRepositoryRoutine effect = new mir.routines.pcm2javaCommon.RenamePackageForRepositoryRoutine(this.executionState, calledBy, repository);
    return effect.applyRoutine();
  }
  
  public boolean renameComponentPackageAndClass(final RepositoryComponent component) {
    mir.routines.pcm2javaCommon.RenameComponentPackageAndClassRoutine effect = new mir.routines.pcm2javaCommon.RenameComponentPackageAndClassRoutine(this.executionState, calledBy, component);
    return effect.applyRoutine();
  }
  
  public boolean renameComponentClass(final RepositoryComponent component) {
    mir.routines.pcm2javaCommon.RenameComponentClassRoutine effect = new mir.routines.pcm2javaCommon.RenameComponentClassRoutine(this.executionState, calledBy, component);
    return effect.applyRoutine();
  }
  
  public boolean renameInterface(final OperationInterface interf) {
    mir.routines.pcm2javaCommon.RenameInterfaceRoutine effect = new mir.routines.pcm2javaCommon.RenameInterfaceRoutine(this.executionState, calledBy, interf);
    return effect.applyRoutine();
  }
  
  public boolean renameCompositeDataType(final CompositeDataType compositeDataType) {
    mir.routines.pcm2javaCommon.RenameCompositeDataTypeRoutine effect = new mir.routines.pcm2javaCommon.RenameCompositeDataTypeRoutine(this.executionState, calledBy, compositeDataType);
    return effect.applyRoutine();
  }
  
  public boolean addSuperTypeToDataType(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    mir.routines.pcm2javaCommon.AddSuperTypeToDataTypeRoutine effect = new mir.routines.pcm2javaCommon.AddSuperTypeToDataTypeRoutine(this.executionState, calledBy, dataType, innerTypeReference, superTypeQualifiedName);
    return effect.applyRoutine();
  }
  
  public boolean renameCollectionDataType(final CollectionDataType collectionDataType) {
    mir.routines.pcm2javaCommon.RenameCollectionDataTypeRoutine effect = new mir.routines.pcm2javaCommon.RenameCollectionDataTypeRoutine(this.executionState, calledBy, collectionDataType);
    return effect.applyRoutine();
  }
  
  public boolean createInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2javaCommon.CreateInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2javaCommon.CreateInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    return effect.applyRoutine();
  }
  
  public boolean addInnerDeclarationToCompositeDataType(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    mir.routines.pcm2javaCommon.AddInnerDeclarationToCompositeDataTypeRoutine effect = new mir.routines.pcm2javaCommon.AddInnerDeclarationToCompositeDataTypeRoutine(this.executionState, calledBy, dataType, innerDeclaration, dataTypeReference);
    return effect.applyRoutine();
  }
  
  public boolean renameInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2javaCommon.RenameInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2javaCommon.RenameInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    return effect.applyRoutine();
  }
  
  public boolean changeTypeOfInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2javaCommon.ChangeTypeOfInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2javaCommon.ChangeTypeOfInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    return effect.applyRoutine();
  }
  
  public boolean changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    mir.routines.pcm2javaCommon.ChangeInnerDeclarationTypeRoutine effect = new mir.routines.pcm2javaCommon.ChangeInnerDeclarationTypeRoutine(this.executionState, calledBy, innerDeclaration, newTypeReference);
    return effect.applyRoutine();
  }
  
  public boolean createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.pcm2javaCommon.CreateJavaPackageRoutine effect = new mir.routines.pcm2javaCommon.CreateJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, parentPackage, packageName, newTag);
    return effect.applyRoutine();
  }
  
  public boolean renameJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2javaCommon.RenameJavaPackageRoutine effect = new mir.routines.pcm2javaCommon.RenameJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, parentPackage, packageName, expectedTag);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2javaCommon.DeleteJavaPackageRoutine effect = new mir.routines.pcm2javaCommon.DeleteJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, packageName, expectedTag);
    return effect.applyRoutine();
  }
  
  public boolean createCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    mir.routines.pcm2javaCommon.CreateCompilationUnitRoutine effect = new mir.routines.pcm2javaCommon.CreateCompilationUnitRoutine(this.executionState, calledBy, sourceElementMappedToClass, classifier, containingPackage);
    return effect.applyRoutine();
  }
  
  public boolean renameJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2javaCommon.RenameJavaClassifierRoutine effect = new mir.routines.pcm2javaCommon.RenameJavaClassifierRoutine(this.executionState, calledBy, classSourceElement, containingPackage, className);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaClassifier(final NamedElement sourceElement) {
    mir.routines.pcm2javaCommon.DeleteJavaClassifierRoutine effect = new mir.routines.pcm2javaCommon.DeleteJavaClassifierRoutine(this.executionState, calledBy, sourceElement);
    return effect.applyRoutine();
  }
  
  public boolean addProvidedRole(final OperationProvidedRole providedRole) {
    mir.routines.pcm2javaCommon.AddProvidedRoleRoutine effect = new mir.routines.pcm2javaCommon.AddProvidedRoleRoutine(this.executionState, calledBy, providedRole);
    return effect.applyRoutine();
  }
  
  public boolean removeProvidedRole(final ProvidedRole providedRole) {
    mir.routines.pcm2javaCommon.RemoveProvidedRoleRoutine effect = new mir.routines.pcm2javaCommon.RemoveProvidedRoleRoutine(this.executionState, calledBy, providedRole);
    return effect.applyRoutine();
  }
  
  public boolean createMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2javaCommon.CreateMethodForOperationSignatureRoutine effect = new mir.routines.pcm2javaCommon.CreateMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean renameMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2javaCommon.RenameMethodForOperationSignatureRoutine effect = new mir.routines.pcm2javaCommon.RenameMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean changeReturnTypeOfMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2javaCommon.ChangeReturnTypeOfMethodForOperationSignatureRoutine effect = new mir.routines.pcm2javaCommon.ChangeReturnTypeOfMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean changeInterfaceMethodReturnType(final InterfaceMethod interfaceMethod, final DataType returnType) {
    mir.routines.pcm2javaCommon.ChangeInterfaceMethodReturnTypeRoutine effect = new mir.routines.pcm2javaCommon.ChangeInterfaceMethodReturnTypeRoutine(this.executionState, calledBy, interfaceMethod, returnType);
    return effect.applyRoutine();
  }
  
  public boolean deleteMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2javaCommon.DeleteMethodForOperationSignatureRoutine effect = new mir.routines.pcm2javaCommon.DeleteMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    return effect.applyRoutine();
  }
  
  public boolean renameParameter(final Parameter parameter) {
    mir.routines.pcm2javaCommon.RenameParameterRoutine effect = new mir.routines.pcm2javaCommon.RenameParameterRoutine(this.executionState, calledBy, parameter);
    return effect.applyRoutine();
  }
  
  public boolean changeParameterType(final Parameter parameter) {
    mir.routines.pcm2javaCommon.ChangeParameterTypeRoutine effect = new mir.routines.pcm2javaCommon.ChangeParameterTypeRoutine(this.executionState, calledBy, parameter);
    return effect.applyRoutine();
  }
  
  public boolean deleteParameter(final OperationSignature signature, final Parameter parameter) {
    mir.routines.pcm2javaCommon.DeleteParameterRoutine effect = new mir.routines.pcm2javaCommon.DeleteParameterRoutine(this.executionState, calledBy, signature, parameter);
    return effect.applyRoutine();
  }
  
  public boolean createSEFF(final ServiceEffectSpecification seff) {
    mir.routines.pcm2javaCommon.CreateSEFFRoutine effect = new mir.routines.pcm2javaCommon.CreateSEFFRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
  
  public boolean changeMethodForSeff(final ResourceDemandingSEFF seff) {
    mir.routines.pcm2javaCommon.ChangeMethodForSeffRoutine effect = new mir.routines.pcm2javaCommon.ChangeMethodForSeffRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
  
  public boolean updateSEFFImplementingMethodName(final ServiceEffectSpecification seff) {
    mir.routines.pcm2javaCommon.UpdateSEFFImplementingMethodNameRoutine effect = new mir.routines.pcm2javaCommon.UpdateSEFFImplementingMethodNameRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
  
  public boolean deleteMethodForSeff(final ServiceEffectSpecification seff) {
    mir.routines.pcm2javaCommon.DeleteMethodForSeffRoutine effect = new mir.routines.pcm2javaCommon.DeleteMethodForSeffRoutine(this.executionState, calledBy, seff);
    return effect.applyRoutine();
  }
}
