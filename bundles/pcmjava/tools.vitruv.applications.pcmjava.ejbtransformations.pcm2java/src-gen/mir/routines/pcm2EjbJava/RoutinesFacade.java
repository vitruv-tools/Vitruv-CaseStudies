package mir.routines.pcm2EjbJava;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;
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
  
  public void createRepositorySubPackages(final Repository repository) {
    mir.routines.pcm2EjbJava.CreateRepositorySubPackagesRoutine effect = new mir.routines.pcm2EjbJava.CreateRepositorySubPackagesRoutine(this.executionState, calledBy, repository);
    effect.applyRoutine();
  }
  
  public void renamePackageForRepository(final Repository repository) {
    mir.routines.pcm2EjbJava.RenamePackageForRepositoryRoutine effect = new mir.routines.pcm2EjbJava.RenamePackageForRepositoryRoutine(this.executionState, calledBy, repository);
    effect.applyRoutine();
  }
  
  public void createComponentImplementation(final RepositoryComponent component) {
    mir.routines.pcm2EjbJava.CreateComponentImplementationRoutine effect = new mir.routines.pcm2EjbJava.CreateComponentImplementationRoutine(this.executionState, calledBy, component);
    effect.applyRoutine();
  }
  
  public void createImplementationForComponent(final RepositoryComponent component) {
    mir.routines.pcm2EjbJava.CreateImplementationForComponentRoutine effect = new mir.routines.pcm2EjbJava.CreateImplementationForComponentRoutine(this.executionState, calledBy, component);
    effect.applyRoutine();
  }
  
  public void renameComponentPackageAndClass(final RepositoryComponent component) {
    mir.routines.pcm2EjbJava.RenameComponentPackageAndClassRoutine effect = new mir.routines.pcm2EjbJava.RenameComponentPackageAndClassRoutine(this.executionState, calledBy, component);
    effect.applyRoutine();
  }
  
  public void renameComponentClass(final RepositoryComponent component) {
    mir.routines.pcm2EjbJava.RenameComponentClassRoutine effect = new mir.routines.pcm2EjbJava.RenameComponentClassRoutine(this.executionState, calledBy, component);
    effect.applyRoutine();
  }
  
  public void createInterfaceImplementation(final Interface interf) {
    mir.routines.pcm2EjbJava.CreateInterfaceImplementationRoutine effect = new mir.routines.pcm2EjbJava.CreateInterfaceImplementationRoutine(this.executionState, calledBy, interf);
    effect.applyRoutine();
  }
  
  public void renameInterface(final OperationInterface interf) {
    mir.routines.pcm2EjbJava.RenameInterfaceRoutine effect = new mir.routines.pcm2EjbJava.RenameInterfaceRoutine(this.executionState, calledBy, interf);
    effect.applyRoutine();
  }
  
  public void createCompositeDataTypeImplementation(final CompositeDataType compositeDataType) {
    mir.routines.pcm2EjbJava.CreateCompositeDataTypeImplementationRoutine effect = new mir.routines.pcm2EjbJava.CreateCompositeDataTypeImplementationRoutine(this.executionState, calledBy, compositeDataType);
    effect.applyRoutine();
  }
  
  public void renameCompositeDataType(final CompositeDataType compositeDataType) {
    mir.routines.pcm2EjbJava.RenameCompositeDataTypeRoutine effect = new mir.routines.pcm2EjbJava.RenameCompositeDataTypeRoutine(this.executionState, calledBy, compositeDataType);
    effect.applyRoutine();
  }
  
  public void createCollectionDataTypeImplementation(final CollectionDataType dataType) {
    mir.routines.pcm2EjbJava.CreateCollectionDataTypeImplementationRoutine effect = new mir.routines.pcm2EjbJava.CreateCollectionDataTypeImplementationRoutine(this.executionState, calledBy, dataType);
    effect.applyRoutine();
  }
  
  public void addSuperTypeToDataType(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    mir.routines.pcm2EjbJava.AddSuperTypeToDataTypeRoutine effect = new mir.routines.pcm2EjbJava.AddSuperTypeToDataTypeRoutine(this.executionState, calledBy, dataType, innerTypeReference, superTypeQualifiedName);
    effect.applyRoutine();
  }
  
  public void renameCollectionDataType(final CollectionDataType collectionDataType) {
    mir.routines.pcm2EjbJava.RenameCollectionDataTypeRoutine effect = new mir.routines.pcm2EjbJava.RenameCollectionDataTypeRoutine(this.executionState, calledBy, collectionDataType);
    effect.applyRoutine();
  }
  
  public void createInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2EjbJava.CreateInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2EjbJava.CreateInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    effect.applyRoutine();
  }
  
  public void addInnerDeclarationToCompositeDataType(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    mir.routines.pcm2EjbJava.AddInnerDeclarationToCompositeDataTypeRoutine effect = new mir.routines.pcm2EjbJava.AddInnerDeclarationToCompositeDataTypeRoutine(this.executionState, calledBy, dataType, innerDeclaration, dataTypeReference);
    effect.applyRoutine();
  }
  
  public void renameInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2EjbJava.RenameInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2EjbJava.RenameInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    effect.applyRoutine();
  }
  
  public void changeTypeOfInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2EjbJava.ChangeTypeOfInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2EjbJava.ChangeTypeOfInnerDeclarationImplementationRoutine(this.executionState, calledBy, innerDeclaration);
    effect.applyRoutine();
  }
  
  public void changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    mir.routines.pcm2EjbJava.ChangeInnerDeclarationTypeRoutine effect = new mir.routines.pcm2EjbJava.ChangeInnerDeclarationTypeRoutine(this.executionState, calledBy, innerDeclaration, newTypeReference);
    effect.applyRoutine();
  }
  
  public void createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.pcm2EjbJava.CreateJavaPackageRoutine effect = new mir.routines.pcm2EjbJava.CreateJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, parentPackage, packageName, newTag);
    effect.applyRoutine();
  }
  
  public void renameJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2EjbJava.RenameJavaPackageRoutine effect = new mir.routines.pcm2EjbJava.RenameJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, parentPackage, packageName, expectedTag);
    effect.applyRoutine();
  }
  
  public void deleteJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2EjbJava.DeleteJavaPackageRoutine effect = new mir.routines.pcm2EjbJava.DeleteJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, packageName, expectedTag);
    effect.applyRoutine();
  }
  
  public void createJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2EjbJava.CreateJavaClassRoutine effect = new mir.routines.pcm2EjbJava.CreateJavaClassRoutine(this.executionState, calledBy, sourceElementMappedToClass, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void createJavaInterface(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2EjbJava.CreateJavaInterfaceRoutine effect = new mir.routines.pcm2EjbJava.CreateJavaInterfaceRoutine(this.executionState, calledBy, sourceElementMappedToClass, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void createCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    mir.routines.pcm2EjbJava.CreateCompilationUnitRoutine effect = new mir.routines.pcm2EjbJava.CreateCompilationUnitRoutine(this.executionState, calledBy, sourceElementMappedToClass, classifier, containingPackage);
    effect.applyRoutine();
  }
  
  public void renameJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2EjbJava.RenameJavaClassifierRoutine effect = new mir.routines.pcm2EjbJava.RenameJavaClassifierRoutine(this.executionState, calledBy, classSourceElement, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void deleteJavaClassifier(final NamedElement sourceElement) {
    mir.routines.pcm2EjbJava.DeleteJavaClassifierRoutine effect = new mir.routines.pcm2EjbJava.DeleteJavaClassifierRoutine(this.executionState, calledBy, sourceElement);
    effect.applyRoutine();
  }
  
  public void addProvidedRole(final OperationProvidedRole providedRole) {
    mir.routines.pcm2EjbJava.AddProvidedRoleRoutine effect = new mir.routines.pcm2EjbJava.AddProvidedRoleRoutine(this.executionState, calledBy, providedRole);
    effect.applyRoutine();
  }
  
  public void removeProvidedRole(final ProvidedRole providedRole) {
    mir.routines.pcm2EjbJava.RemoveProvidedRoleRoutine effect = new mir.routines.pcm2EjbJava.RemoveProvidedRoleRoutine(this.executionState, calledBy, providedRole);
    effect.applyRoutine();
  }
  
  public void addRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2EjbJava.AddRequiredRoleRoutine effect = new mir.routines.pcm2EjbJava.AddRequiredRoleRoutine(this.executionState, calledBy, requiredRole);
    effect.applyRoutine();
  }
  
  public void removeRequiredRole(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    mir.routines.pcm2EjbJava.RemoveRequiredRoleRoutine effect = new mir.routines.pcm2EjbJava.RemoveRequiredRoleRoutine(this.executionState, calledBy, requiredRole, requiringEntity);
    effect.applyRoutine();
  }
  
  public void reinitializeOperationRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2EjbJava.ReinitializeOperationRequiredRoleRoutine effect = new mir.routines.pcm2EjbJava.ReinitializeOperationRequiredRoleRoutine(this.executionState, calledBy, requiredRole);
    effect.applyRoutine();
  }
  
  public void createMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2EjbJava.CreateMethodForOperationSignatureRoutine effect = new mir.routines.pcm2EjbJava.CreateMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    effect.applyRoutine();
  }
  
  public void renameMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2EjbJava.RenameMethodForOperationSignatureRoutine effect = new mir.routines.pcm2EjbJava.RenameMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    effect.applyRoutine();
  }
  
  public void changeReturnTypeOfMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2EjbJava.ChangeReturnTypeOfMethodForOperationSignatureRoutine effect = new mir.routines.pcm2EjbJava.ChangeReturnTypeOfMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    effect.applyRoutine();
  }
  
  public void changeInterfaceMethodReturnType(final InterfaceMethod interfaceMethod, final DataType returnType) {
    mir.routines.pcm2EjbJava.ChangeInterfaceMethodReturnTypeRoutine effect = new mir.routines.pcm2EjbJava.ChangeInterfaceMethodReturnTypeRoutine(this.executionState, calledBy, interfaceMethod, returnType);
    effect.applyRoutine();
  }
  
  public void deleteMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2EjbJava.DeleteMethodForOperationSignatureRoutine effect = new mir.routines.pcm2EjbJava.DeleteMethodForOperationSignatureRoutine(this.executionState, calledBy, operationSignature);
    effect.applyRoutine();
  }
  
  public void createParameter(final Parameter parameter) {
    mir.routines.pcm2EjbJava.CreateParameterRoutine effect = new mir.routines.pcm2EjbJava.CreateParameterRoutine(this.executionState, calledBy, parameter);
    effect.applyRoutine();
  }
  
  public void renameParameter(final Parameter parameter) {
    mir.routines.pcm2EjbJava.RenameParameterRoutine effect = new mir.routines.pcm2EjbJava.RenameParameterRoutine(this.executionState, calledBy, parameter);
    effect.applyRoutine();
  }
  
  public void changeParameterType(final Parameter parameter) {
    mir.routines.pcm2EjbJava.ChangeParameterTypeRoutine effect = new mir.routines.pcm2EjbJava.ChangeParameterTypeRoutine(this.executionState, calledBy, parameter);
    effect.applyRoutine();
  }
  
  public void deleteParameter(final OperationSignature signature, final Parameter parameter) {
    mir.routines.pcm2EjbJava.DeleteParameterRoutine effect = new mir.routines.pcm2EjbJava.DeleteParameterRoutine(this.executionState, calledBy, signature, parameter);
    effect.applyRoutine();
  }
  
  public void createSEFF(final ServiceEffectSpecification seff) {
    mir.routines.pcm2EjbJava.CreateSEFFRoutine effect = new mir.routines.pcm2EjbJava.CreateSEFFRoutine(this.executionState, calledBy, seff);
    effect.applyRoutine();
  }
  
  public void changeMethodForSeff(final ResourceDemandingSEFF seff) {
    mir.routines.pcm2EjbJava.ChangeMethodForSeffRoutine effect = new mir.routines.pcm2EjbJava.ChangeMethodForSeffRoutine(this.executionState, calledBy, seff);
    effect.applyRoutine();
  }
  
  public void updateSEFFImplementingMethodName(final ServiceEffectSpecification seff) {
    mir.routines.pcm2EjbJava.UpdateSEFFImplementingMethodNameRoutine effect = new mir.routines.pcm2EjbJava.UpdateSEFFImplementingMethodNameRoutine(this.executionState, calledBy, seff);
    effect.applyRoutine();
  }
  
  public void deleteMethodForSeff(final ServiceEffectSpecification seff) {
    mir.routines.pcm2EjbJava.DeleteMethodForSeffRoutine effect = new mir.routines.pcm2EjbJava.DeleteMethodForSeffRoutine(this.executionState, calledBy, seff);
    effect.applyRoutine();
  }
}
