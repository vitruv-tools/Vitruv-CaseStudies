package mir.routines.pcm2ejbJava;

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
    mir.routines.pcm2ejbJava.CreateRepositorySubPackagesRoutine effect = new mir.routines.pcm2ejbJava.CreateRepositorySubPackagesRoutine(this.executionState, calledBy,
    	repository);
    effect.applyRoutine();
  }
  
  public void renamePackageForRepository(final Repository repository) {
    mir.routines.pcm2ejbJava.RenamePackageForRepositoryRoutine effect = new mir.routines.pcm2ejbJava.RenamePackageForRepositoryRoutine(this.executionState, calledBy,
    	repository);
    effect.applyRoutine();
  }
  
  public void createComponentImplementation(final RepositoryComponent component) {
    mir.routines.pcm2ejbJava.CreateComponentImplementationRoutine effect = new mir.routines.pcm2ejbJava.CreateComponentImplementationRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void createImplementationForComponent(final RepositoryComponent component) {
    mir.routines.pcm2ejbJava.CreateImplementationForComponentRoutine effect = new mir.routines.pcm2ejbJava.CreateImplementationForComponentRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void renameComponentPackageAndClass(final RepositoryComponent component) {
    mir.routines.pcm2ejbJava.RenameComponentPackageAndClassRoutine effect = new mir.routines.pcm2ejbJava.RenameComponentPackageAndClassRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void renameComponentClass(final RepositoryComponent component) {
    mir.routines.pcm2ejbJava.RenameComponentClassRoutine effect = new mir.routines.pcm2ejbJava.RenameComponentClassRoutine(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void createInterfaceImplementation(final Interface interf) {
    mir.routines.pcm2ejbJava.CreateInterfaceImplementationRoutine effect = new mir.routines.pcm2ejbJava.CreateInterfaceImplementationRoutine(this.executionState, calledBy,
    	interf);
    effect.applyRoutine();
  }
  
  public void renameInterface(final OperationInterface interf) {
    mir.routines.pcm2ejbJava.RenameInterfaceRoutine effect = new mir.routines.pcm2ejbJava.RenameInterfaceRoutine(this.executionState, calledBy,
    	interf);
    effect.applyRoutine();
  }
  
  public void createCompositeDataTypeImplementation(final CompositeDataType compositeDataType) {
    mir.routines.pcm2ejbJava.CreateCompositeDataTypeImplementationRoutine effect = new mir.routines.pcm2ejbJava.CreateCompositeDataTypeImplementationRoutine(this.executionState, calledBy,
    	compositeDataType);
    effect.applyRoutine();
  }
  
  public void renameCompositeDataType(final CompositeDataType compositeDataType) {
    mir.routines.pcm2ejbJava.RenameCompositeDataTypeRoutine effect = new mir.routines.pcm2ejbJava.RenameCompositeDataTypeRoutine(this.executionState, calledBy,
    	compositeDataType);
    effect.applyRoutine();
  }
  
  public void createCollectionDataTypeImplementation(final CollectionDataType dataType) {
    mir.routines.pcm2ejbJava.CreateCollectionDataTypeImplementationRoutine effect = new mir.routines.pcm2ejbJava.CreateCollectionDataTypeImplementationRoutine(this.executionState, calledBy,
    	dataType);
    effect.applyRoutine();
  }
  
  public void addSuperTypeToDataType(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    mir.routines.pcm2ejbJava.AddSuperTypeToDataTypeRoutine effect = new mir.routines.pcm2ejbJava.AddSuperTypeToDataTypeRoutine(this.executionState, calledBy,
    	dataType, innerTypeReference, superTypeQualifiedName);
    effect.applyRoutine();
  }
  
  public void renameCollectionDataType(final CollectionDataType collectionDataType) {
    mir.routines.pcm2ejbJava.RenameCollectionDataTypeRoutine effect = new mir.routines.pcm2ejbJava.RenameCollectionDataTypeRoutine(this.executionState, calledBy,
    	collectionDataType);
    effect.applyRoutine();
  }
  
  public void createInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2ejbJava.CreateInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2ejbJava.CreateInnerDeclarationImplementationRoutine(this.executionState, calledBy,
    	innerDeclaration);
    effect.applyRoutine();
  }
  
  public void addInnerDeclarationToCompositeDataType(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    mir.routines.pcm2ejbJava.AddInnerDeclarationToCompositeDataTypeRoutine effect = new mir.routines.pcm2ejbJava.AddInnerDeclarationToCompositeDataTypeRoutine(this.executionState, calledBy,
    	dataType, innerDeclaration, dataTypeReference);
    effect.applyRoutine();
  }
  
  public void renameInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2ejbJava.RenameInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2ejbJava.RenameInnerDeclarationImplementationRoutine(this.executionState, calledBy,
    	innerDeclaration);
    effect.applyRoutine();
  }
  
  public void changeTypeOfInnerDeclarationImplementation(final InnerDeclaration innerDeclaration) {
    mir.routines.pcm2ejbJava.ChangeTypeOfInnerDeclarationImplementationRoutine effect = new mir.routines.pcm2ejbJava.ChangeTypeOfInnerDeclarationImplementationRoutine(this.executionState, calledBy,
    	innerDeclaration);
    effect.applyRoutine();
  }
  
  public void changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    mir.routines.pcm2ejbJava.ChangeInnerDeclarationTypeRoutine effect = new mir.routines.pcm2ejbJava.ChangeInnerDeclarationTypeRoutine(this.executionState, calledBy,
    	innerDeclaration, newTypeReference);
    effect.applyRoutine();
  }
  
  public void createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.pcm2ejbJava.CreateJavaPackageRoutine effect = new mir.routines.pcm2ejbJava.CreateJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, parentPackage, packageName, newTag);
    effect.applyRoutine();
  }
  
  public void renameJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2ejbJava.RenameJavaPackageRoutine effect = new mir.routines.pcm2ejbJava.RenameJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, parentPackage, packageName, expectedTag);
    effect.applyRoutine();
  }
  
  public void deleteJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2ejbJava.DeleteJavaPackageRoutine effect = new mir.routines.pcm2ejbJava.DeleteJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, packageName, expectedTag);
    effect.applyRoutine();
  }
  
  public void createJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2ejbJava.CreateJavaClassRoutine effect = new mir.routines.pcm2ejbJava.CreateJavaClassRoutine(this.executionState, calledBy,
    	sourceElementMappedToClass, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void createJavaInterface(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2ejbJava.CreateJavaInterfaceRoutine effect = new mir.routines.pcm2ejbJava.CreateJavaInterfaceRoutine(this.executionState, calledBy,
    	sourceElementMappedToClass, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void createCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    mir.routines.pcm2ejbJava.CreateCompilationUnitRoutine effect = new mir.routines.pcm2ejbJava.CreateCompilationUnitRoutine(this.executionState, calledBy,
    	sourceElementMappedToClass, classifier, containingPackage);
    effect.applyRoutine();
  }
  
  public void renameJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2ejbJava.RenameJavaClassifierRoutine effect = new mir.routines.pcm2ejbJava.RenameJavaClassifierRoutine(this.executionState, calledBy,
    	classSourceElement, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void deleteJavaClassifier(final NamedElement sourceElement) {
    mir.routines.pcm2ejbJava.DeleteJavaClassifierRoutine effect = new mir.routines.pcm2ejbJava.DeleteJavaClassifierRoutine(this.executionState, calledBy,
    	sourceElement);
    effect.applyRoutine();
  }
  
  public void addProvidedRole(final OperationProvidedRole providedRole) {
    mir.routines.pcm2ejbJava.AddProvidedRoleRoutine effect = new mir.routines.pcm2ejbJava.AddProvidedRoleRoutine(this.executionState, calledBy,
    	providedRole);
    effect.applyRoutine();
  }
  
  public void removeProvidedRole(final ProvidedRole providedRole) {
    mir.routines.pcm2ejbJava.RemoveProvidedRoleRoutine effect = new mir.routines.pcm2ejbJava.RemoveProvidedRoleRoutine(this.executionState, calledBy,
    	providedRole);
    effect.applyRoutine();
  }
  
  public void addRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2ejbJava.AddRequiredRoleRoutine effect = new mir.routines.pcm2ejbJava.AddRequiredRoleRoutine(this.executionState, calledBy,
    	requiredRole);
    effect.applyRoutine();
  }
  
  public void removeRequiredRole(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    mir.routines.pcm2ejbJava.RemoveRequiredRoleRoutine effect = new mir.routines.pcm2ejbJava.RemoveRequiredRoleRoutine(this.executionState, calledBy,
    	requiredRole, requiringEntity);
    effect.applyRoutine();
  }
  
  public void reinitializeOperationRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2ejbJava.ReinitializeOperationRequiredRoleRoutine effect = new mir.routines.pcm2ejbJava.ReinitializeOperationRequiredRoleRoutine(this.executionState, calledBy,
    	requiredRole);
    effect.applyRoutine();
  }
  
  public void createMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2ejbJava.CreateMethodForOperationSignatureRoutine effect = new mir.routines.pcm2ejbJava.CreateMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void renameMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2ejbJava.RenameMethodForOperationSignatureRoutine effect = new mir.routines.pcm2ejbJava.RenameMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void changeReturnTypeOfMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2ejbJava.ChangeReturnTypeOfMethodForOperationSignatureRoutine effect = new mir.routines.pcm2ejbJava.ChangeReturnTypeOfMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void changeInterfaceMethodReturnType(final InterfaceMethod interfaceMethod, final DataType returnType) {
    mir.routines.pcm2ejbJava.ChangeInterfaceMethodReturnTypeRoutine effect = new mir.routines.pcm2ejbJava.ChangeInterfaceMethodReturnTypeRoutine(this.executionState, calledBy,
    	interfaceMethod, returnType);
    effect.applyRoutine();
  }
  
  public void deleteMethodForOperationSignature(final OperationSignature operationSignature) {
    mir.routines.pcm2ejbJava.DeleteMethodForOperationSignatureRoutine effect = new mir.routines.pcm2ejbJava.DeleteMethodForOperationSignatureRoutine(this.executionState, calledBy,
    	operationSignature);
    effect.applyRoutine();
  }
  
  public void createParameter(final Parameter parameter) {
    mir.routines.pcm2ejbJava.CreateParameterRoutine effect = new mir.routines.pcm2ejbJava.CreateParameterRoutine(this.executionState, calledBy,
    	parameter);
    effect.applyRoutine();
  }
  
  public void renameParameter(final Parameter parameter) {
    mir.routines.pcm2ejbJava.RenameParameterRoutine effect = new mir.routines.pcm2ejbJava.RenameParameterRoutine(this.executionState, calledBy,
    	parameter);
    effect.applyRoutine();
  }
  
  public void changeParameterType(final Parameter parameter) {
    mir.routines.pcm2ejbJava.ChangeParameterTypeRoutine effect = new mir.routines.pcm2ejbJava.ChangeParameterTypeRoutine(this.executionState, calledBy,
    	parameter);
    effect.applyRoutine();
  }
  
  public void deleteParameter(final OperationSignature signature, final Parameter parameter) {
    mir.routines.pcm2ejbJava.DeleteParameterRoutine effect = new mir.routines.pcm2ejbJava.DeleteParameterRoutine(this.executionState, calledBy,
    	signature, parameter);
    effect.applyRoutine();
  }
  
  public void createMethodForResourceDemandingBehavior(final ResourceDemandingInternalBehaviour behavior) {
    mir.routines.pcm2ejbJava.CreateMethodForResourceDemandingBehaviorRoutine effect = new mir.routines.pcm2ejbJava.CreateMethodForResourceDemandingBehaviorRoutine(this.executionState, calledBy,
    	behavior);
    effect.applyRoutine();
  }
  
  public void renameMethodForResourceDemandingBehavior(final ResourceDemandingInternalBehaviour behavior) {
    mir.routines.pcm2ejbJava.RenameMethodForResourceDemandingBehaviorRoutine effect = new mir.routines.pcm2ejbJava.RenameMethodForResourceDemandingBehaviorRoutine(this.executionState, calledBy,
    	behavior);
    effect.applyRoutine();
  }
  
  public void deleteMethodForResourceDemandingBehavior(final ResourceDemandingInternalBehaviour behavior) {
    mir.routines.pcm2ejbJava.DeleteMethodForResourceDemandingBehaviorRoutine effect = new mir.routines.pcm2ejbJava.DeleteMethodForResourceDemandingBehaviorRoutine(this.executionState, calledBy,
    	behavior);
    effect.applyRoutine();
  }
  
  public void createSEFF(final ServiceEffectSpecification seff) {
    mir.routines.pcm2ejbJava.CreateSEFFRoutine effect = new mir.routines.pcm2ejbJava.CreateSEFFRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
  
  public void changeMethodForSeff(final ResourceDemandingSEFF seff) {
    mir.routines.pcm2ejbJava.ChangeMethodForSeffRoutine effect = new mir.routines.pcm2ejbJava.ChangeMethodForSeffRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
  
  public void updateSEFFImplementingMethodName(final ServiceEffectSpecification seff) {
    mir.routines.pcm2ejbJava.UpdateSEFFImplementingMethodNameRoutine effect = new mir.routines.pcm2ejbJava.UpdateSEFFImplementingMethodNameRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
  
  public void deleteMethodForSeff(final ServiceEffectSpecification seff) {
    mir.routines.pcm2ejbJava.DeleteMethodForSeffRoutine effect = new mir.routines.pcm2ejbJava.DeleteMethodForSeffRoutine(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
}
