package mir.routines.pcm2javaDepInject;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectsFacade;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractEffectsFacade {
  public RoutinesFacade(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  public void callCreateRepositorySubPackages(final Repository repository) {
    mir.routines.pcm2javaDepInject.CreateRepositorySubPackagesEffect effect = new mir.routines.pcm2javaDepInject.CreateRepositorySubPackagesEffect(this.executionState, calledBy,
    	repository);
    effect.applyRoutine();
  }
  
  public void callCreateImplementationForSystem(final org.palladiosimulator.pcm.system.System system) {
    mir.routines.pcm2javaDepInject.CreateImplementationForSystemEffect effect = new mir.routines.pcm2javaDepInject.CreateImplementationForSystemEffect(this.executionState, calledBy,
    	system);
    effect.applyRoutine();
  }
  
  public void callCreateImplementationForComponent(final RepositoryComponent component) {
    mir.routines.pcm2javaDepInject.CreateImplementationForComponentEffect effect = new mir.routines.pcm2javaDepInject.CreateImplementationForComponentEffect(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void callRenameComponentPackageAndClass(final RepositoryComponent component) {
    mir.routines.pcm2javaDepInject.RenameComponentPackageAndClassEffect effect = new mir.routines.pcm2javaDepInject.RenameComponentPackageAndClassEffect(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void callRenameComponentClass(final RepositoryComponent component) {
    mir.routines.pcm2javaDepInject.RenameComponentClassEffect effect = new mir.routines.pcm2javaDepInject.RenameComponentClassEffect(this.executionState, calledBy,
    	component);
    effect.applyRoutine();
  }
  
  public void callRenameInterface(final OperationInterface interf) {
    mir.routines.pcm2javaDepInject.RenameInterfaceEffect effect = new mir.routines.pcm2javaDepInject.RenameInterfaceEffect(this.executionState, calledBy,
    	interf);
    effect.applyRoutine();
  }
  
  public void callRenameCompositeDataType(final CompositeDataType compositeDataType) {
    mir.routines.pcm2javaDepInject.RenameCompositeDataTypeEffect effect = new mir.routines.pcm2javaDepInject.RenameCompositeDataTypeEffect(this.executionState, calledBy,
    	compositeDataType);
    effect.applyRoutine();
  }
  
  public void callAddSuperTypeToDataType(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    mir.routines.pcm2javaDepInject.AddSuperTypeToDataTypeEffect effect = new mir.routines.pcm2javaDepInject.AddSuperTypeToDataTypeEffect(this.executionState, calledBy,
    	dataType, innerTypeReference, superTypeQualifiedName);
    effect.applyRoutine();
  }
  
  public void callRenameCollectionDataType(final CollectionDataType collectionDataType) {
    mir.routines.pcm2javaDepInject.RenameCollectionDataTypeEffect effect = new mir.routines.pcm2javaDepInject.RenameCollectionDataTypeEffect(this.executionState, calledBy,
    	collectionDataType);
    effect.applyRoutine();
  }
  
  public void callAddInnerDeclarationToCompositeDataType(final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    mir.routines.pcm2javaDepInject.AddInnerDeclarationToCompositeDataTypeEffect effect = new mir.routines.pcm2javaDepInject.AddInnerDeclarationToCompositeDataTypeEffect(this.executionState, calledBy,
    	compositeDataType, innerDeclaration, dataTypeReference);
    effect.applyRoutine();
  }
  
  public void callChangeInnerDeclarationType(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    mir.routines.pcm2javaDepInject.ChangeInnerDeclarationTypeEffect effect = new mir.routines.pcm2javaDepInject.ChangeInnerDeclarationTypeEffect(this.executionState, calledBy,
    	innerDeclaration, newTypeReference);
    effect.applyRoutine();
  }
  
  public void callCreateJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.pcm2javaDepInject.CreateJavaPackageEffect effect = new mir.routines.pcm2javaDepInject.CreateJavaPackageEffect(this.executionState, calledBy,
    	sourceElementMappedToPackage, parentPackage, packageName, newTag);
    effect.applyRoutine();
  }
  
  public void callRenameJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2javaDepInject.RenameJavaPackageEffect effect = new mir.routines.pcm2javaDepInject.RenameJavaPackageEffect(this.executionState, calledBy,
    	sourceElementMappedToPackage, parentPackage, packageName, expectedTag);
    effect.applyRoutine();
  }
  
  public void callDeleteJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    mir.routines.pcm2javaDepInject.DeleteJavaPackageEffect effect = new mir.routines.pcm2javaDepInject.DeleteJavaPackageEffect(this.executionState, calledBy,
    	sourceElementMappedToPackage, packageName, expectedTag);
    effect.applyRoutine();
  }
  
  public void callCreateJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2javaDepInject.CreateJavaClassEffect effect = new mir.routines.pcm2javaDepInject.CreateJavaClassEffect(this.executionState, calledBy,
    	sourceElementMappedToClass, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void callCreateJavaInterface(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2javaDepInject.CreateJavaInterfaceEffect effect = new mir.routines.pcm2javaDepInject.CreateJavaInterfaceEffect(this.executionState, calledBy,
    	sourceElementMappedToClass, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void callCreateCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    mir.routines.pcm2javaDepInject.CreateCompilationUnitEffect effect = new mir.routines.pcm2javaDepInject.CreateCompilationUnitEffect(this.executionState, calledBy,
    	sourceElementMappedToClass, classifier, containingPackage);
    effect.applyRoutine();
  }
  
  public void callRenameJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2javaDepInject.RenameJavaClassifierEffect effect = new mir.routines.pcm2javaDepInject.RenameJavaClassifierEffect(this.executionState, calledBy,
    	classSourceElement, containingPackage, className);
    effect.applyRoutine();
  }
  
  public void callDeleteJavaClassifier(final NamedElement sourceElement) {
    mir.routines.pcm2javaDepInject.DeleteJavaClassifierEffect effect = new mir.routines.pcm2javaDepInject.DeleteJavaClassifierEffect(this.executionState, calledBy,
    	sourceElement);
    effect.applyRoutine();
  }
  
  public void callAddProvidedRole(final OperationProvidedRole providedRole) {
    mir.routines.pcm2javaDepInject.AddProvidedRoleEffect effect = new mir.routines.pcm2javaDepInject.AddProvidedRoleEffect(this.executionState, calledBy,
    	providedRole);
    effect.applyRoutine();
  }
  
  public void callRemoveProvidedRole(final ProvidedRole providedRole) {
    mir.routines.pcm2javaDepInject.RemoveProvidedRoleEffect effect = new mir.routines.pcm2javaDepInject.RemoveProvidedRoleEffect(this.executionState, calledBy,
    	providedRole);
    effect.applyRoutine();
  }
  
  public void callAddRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2javaDepInject.AddRequiredRoleEffect effect = new mir.routines.pcm2javaDepInject.AddRequiredRoleEffect(this.executionState, calledBy,
    	requiredRole);
    effect.applyRoutine();
  }
  
  public void callAddParameterAndAssignmentToConstructor(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName) {
    mir.routines.pcm2javaDepInject.AddParameterAndAssignmentToConstructorEffect effect = new mir.routines.pcm2javaDepInject.AddParameterAndAssignmentToConstructorEffect(this.executionState, calledBy,
    	parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName);
    effect.applyRoutine();
  }
  
  public void callRemoveRequiredRole(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    mir.routines.pcm2javaDepInject.RemoveRequiredRoleEffect effect = new mir.routines.pcm2javaDepInject.RemoveRequiredRoleEffect(this.executionState, calledBy,
    	requiredRole, requiringEntity);
    effect.applyRoutine();
  }
  
  public void callRemoveCorrespondingParameterFromConstructor(final Constructor ctor, final NamedElement correspondenceSource) {
    mir.routines.pcm2javaDepInject.RemoveCorrespondingParameterFromConstructorEffect effect = new mir.routines.pcm2javaDepInject.RemoveCorrespondingParameterFromConstructorEffect(this.executionState, calledBy,
    	ctor, correspondenceSource);
    effect.applyRoutine();
  }
  
  public void callReinitializeOperationRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2javaDepInject.ReinitializeOperationRequiredRoleEffect effect = new mir.routines.pcm2javaDepInject.ReinitializeOperationRequiredRoleEffect(this.executionState, calledBy,
    	requiredRole);
    effect.applyRoutine();
  }
  
  public void callChangeInterfaceMethodReturnType(final InterfaceMethod interfaceMethod, final DataType returnType) {
    mir.routines.pcm2javaDepInject.ChangeInterfaceMethodReturnTypeEffect effect = new mir.routines.pcm2javaDepInject.ChangeInterfaceMethodReturnTypeEffect(this.executionState, calledBy,
    	interfaceMethod, returnType);
    effect.applyRoutine();
  }
  
  public void callChangeParameterType(final OrdinaryParameter javaParameter, final DataType parameterType) {
    mir.routines.pcm2javaDepInject.ChangeParameterTypeEffect effect = new mir.routines.pcm2javaDepInject.ChangeParameterTypeEffect(this.executionState, calledBy,
    	javaParameter, parameterType);
    effect.applyRoutine();
  }
  
  public void callCreateSEFF(final ServiceEffectSpecification seff) {
    mir.routines.pcm2javaDepInject.CreateSEFFEffect effect = new mir.routines.pcm2javaDepInject.CreateSEFFEffect(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
  
  public void callUpdateSEFFImplementingMethodName(final ServiceEffectSpecification seff) {
    mir.routines.pcm2javaDepInject.UpdateSEFFImplementingMethodNameEffect effect = new mir.routines.pcm2javaDepInject.UpdateSEFFImplementingMethodNameEffect(this.executionState, calledBy,
    	seff);
    effect.applyRoutine();
  }
}
