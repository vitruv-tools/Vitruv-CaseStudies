package mir.routines.java2PcmMethod;

import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.Parametrizable;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public boolean renameNamedElement(final NamedElement javaElement) {
    mir.routines.java2PcmMethod.RenameNamedElementRoutine effect = new mir.routines.java2PcmMethod.RenameNamedElementRoutine(this.executionState, calledBy, javaElement);
    return effect.applyRoutine();
  }
  
  public boolean createParameter(final OrdinaryParameter javaParameter, final Parametrizable javaMethod) {
    mir.routines.java2PcmMethod.CreateParameterRoutine effect = new mir.routines.java2PcmMethod.CreateParameterRoutine(this.executionState, calledBy, javaParameter, javaMethod);
    return effect.applyRoutine();
  }
  
  public boolean deleteParameter(final OrdinaryParameter javaParameter) {
    mir.routines.java2PcmMethod.DeleteParameterRoutine effect = new mir.routines.java2PcmMethod.DeleteParameterRoutine(this.executionState, calledBy, javaParameter);
    return effect.applyRoutine();
  }
  
  public boolean changeParameterName(final String newName, final Parameter javaParameter) {
    mir.routines.java2PcmMethod.ChangeParameterNameRoutine effect = new mir.routines.java2PcmMethod.ChangeParameterNameRoutine(this.executionState, calledBy, newName, javaParameter);
    return effect.applyRoutine();
  }
  
  public boolean createInnerDeclaration(final ConcreteClassifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.CreateInnerDeclarationRoutine effect = new mir.routines.java2PcmMethod.CreateInnerDeclarationRoutine(this.executionState, calledBy, classifier, javaField);
    return effect.applyRoutine();
  }
  
  public boolean createAssemblyContext(final ConcreteClassifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.CreateAssemblyContextRoutine effect = new mir.routines.java2PcmMethod.CreateAssemblyContextRoutine(this.executionState, calledBy, classifier, javaField);
    return effect.applyRoutine();
  }
  
  public boolean fieldCreatedCorrespondingToOperationInterface(final Classifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.FieldCreatedCorrespondingToOperationInterfaceRoutine effect = new mir.routines.java2PcmMethod.FieldCreatedCorrespondingToOperationInterfaceRoutine(this.executionState, calledBy, classifier, javaField);
    return effect.applyRoutine();
  }
  
  public boolean fieldCreatedCorrespondingToRepositoryComponent(final Classifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.FieldCreatedCorrespondingToRepositoryComponentRoutine effect = new mir.routines.java2PcmMethod.FieldCreatedCorrespondingToRepositoryComponentRoutine(this.executionState, calledBy, classifier, javaField);
    return effect.applyRoutine();
  }
  
  public boolean createOperationRequiredRoleCorrespondingToField(final Field javaField, final OperationInterface operationInterface, final RepositoryComponent repoComponent) {
    mir.routines.java2PcmMethod.CreateOperationRequiredRoleCorrespondingToFieldRoutine effect = new mir.routines.java2PcmMethod.CreateOperationRequiredRoleCorrespondingToFieldRoutine(this.executionState, calledBy, javaField, operationInterface, repoComponent);
    return effect.applyRoutine();
  }
  
  public boolean changeInnerDeclarationType(final TypeReference typeReference, final Field javaField) {
    mir.routines.java2PcmMethod.ChangeInnerDeclarationTypeRoutine effect = new mir.routines.java2PcmMethod.ChangeInnerDeclarationTypeRoutine(this.executionState, calledBy, typeReference, javaField);
    return effect.applyRoutine();
  }
  
  public boolean createSeffFromImplementingInterfaces(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass) {
    mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfacesRoutine effect = new mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfacesRoutine(this.executionState, calledBy, classMethod, javaClass);
    return effect.applyRoutine();
  }
  
  public boolean createSeffFromImplementingInterface(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass, final Interface javaInterface) {
    mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfaceRoutine effect = new mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfaceRoutine(this.executionState, calledBy, classMethod, javaClass, javaInterface);
    return effect.applyRoutine();
  }
  
  public boolean createSEFF(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod) {
    mir.routines.java2PcmMethod.CreateSEFFRoutine effect = new mir.routines.java2PcmMethod.CreateSEFFRoutine(this.executionState, calledBy, javaMethod, javaClass, classMethod);
    return effect.applyRoutine();
  }
  
  public boolean createPCMSignature(final InterfaceMethod interfaceMethod) {
    mir.routines.java2PcmMethod.CreatePCMSignatureRoutine effect = new mir.routines.java2PcmMethod.CreatePCMSignatureRoutine(this.executionState, calledBy, interfaceMethod);
    return effect.applyRoutine();
  }
  
  public boolean changeReturnType(final Method javaMethod, final TypeReference typeReference) {
    mir.routines.java2PcmMethod.ChangeReturnTypeRoutine effect = new mir.routines.java2PcmMethod.ChangeReturnTypeRoutine(this.executionState, calledBy, javaMethod, typeReference);
    return effect.applyRoutine();
  }
}
