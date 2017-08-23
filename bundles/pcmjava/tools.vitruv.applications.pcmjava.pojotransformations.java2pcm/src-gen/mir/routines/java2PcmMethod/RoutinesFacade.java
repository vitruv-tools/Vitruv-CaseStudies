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
  
  public void renameNamedElement(final NamedElement javaElement) {
    mir.routines.java2PcmMethod.RenameNamedElementRoutine effect = new mir.routines.java2PcmMethod.RenameNamedElementRoutine(this.executionState, calledBy, javaElement);
    effect.applyRoutine();
  }
  
  public void createParameter(final OrdinaryParameter jaMoPPParam, final Parametrizable javaMethod) {
    mir.routines.java2PcmMethod.CreateParameterRoutine effect = new mir.routines.java2PcmMethod.CreateParameterRoutine(this.executionState, calledBy, jaMoPPParam, javaMethod);
    effect.applyRoutine();
  }
  
  public void deleteParameter(final OrdinaryParameter jParam) {
    mir.routines.java2PcmMethod.DeleteParameterRoutine effect = new mir.routines.java2PcmMethod.DeleteParameterRoutine(this.executionState, calledBy, jParam);
    effect.applyRoutine();
  }
  
  public void changeParameterName(final String newName, final Parameter parameter) {
    mir.routines.java2PcmMethod.ChangeParameterNameRoutine effect = new mir.routines.java2PcmMethod.ChangeParameterNameRoutine(this.executionState, calledBy, newName, parameter);
    effect.applyRoutine();
  }
  
  public void createInnerDeclaration(final ConcreteClassifier classifier, final Field field) {
    mir.routines.java2PcmMethod.CreateInnerDeclarationRoutine effect = new mir.routines.java2PcmMethod.CreateInnerDeclarationRoutine(this.executionState, calledBy, classifier, field);
    effect.applyRoutine();
  }
  
  public void createAssemblyContext(final ConcreteClassifier classifier, final Field field) {
    mir.routines.java2PcmMethod.CreateAssemblyContextRoutine effect = new mir.routines.java2PcmMethod.CreateAssemblyContextRoutine(this.executionState, calledBy, classifier, field);
    effect.applyRoutine();
  }
  
  public void fieldCreatedCorrespondingToOperationInterface(final Classifier classifier, final Field field) {
    mir.routines.java2PcmMethod.FieldCreatedCorrespondingToOperationInterfaceRoutine effect = new mir.routines.java2PcmMethod.FieldCreatedCorrespondingToOperationInterfaceRoutine(this.executionState, calledBy, classifier, field);
    effect.applyRoutine();
  }
  
  public void fieldCreatedCorrespondingToRepositoryComponent(final Classifier classifier, final Field field) {
    mir.routines.java2PcmMethod.FieldCreatedCorrespondingToRepositoryComponentRoutine effect = new mir.routines.java2PcmMethod.FieldCreatedCorrespondingToRepositoryComponentRoutine(this.executionState, calledBy, classifier, field);
    effect.applyRoutine();
  }
  
  public void createOperationRequiredRoleCorrespondingToField(final Field field, final OperationInterface operationInterface, final RepositoryComponent repoComponent) {
    mir.routines.java2PcmMethod.CreateOperationRequiredRoleCorrespondingToFieldRoutine effect = new mir.routines.java2PcmMethod.CreateOperationRequiredRoleCorrespondingToFieldRoutine(this.executionState, calledBy, field, operationInterface, repoComponent);
    effect.applyRoutine();
  }
  
  public void changeInnerDeclarationType(final TypeReference typeReference, final Field javaField) {
    mir.routines.java2PcmMethod.ChangeInnerDeclarationTypeRoutine effect = new mir.routines.java2PcmMethod.ChangeInnerDeclarationTypeRoutine(this.executionState, calledBy, typeReference, javaField);
    effect.applyRoutine();
  }
  
  public void createSeffFromImplementingInterfaces(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class cls) {
    mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfacesRoutine effect = new mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfacesRoutine(this.executionState, calledBy, classMethod, cls);
    effect.applyRoutine();
  }
  
  public void createSeffFromImplementingInterface(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class cls, final Interface iface) {
    mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfaceRoutine effect = new mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfaceRoutine(this.executionState, calledBy, classMethod, cls, iface);
    effect.applyRoutine();
  }
  
  public void createSEFF(final Method method, final org.emftext.language.java.classifiers.Class cls, final ClassMethod classMethod) {
    mir.routines.java2PcmMethod.CreateSEFFRoutine effect = new mir.routines.java2PcmMethod.CreateSEFFRoutine(this.executionState, calledBy, method, cls, classMethod);
    effect.applyRoutine();
  }
  
  public void createPCMSignature(final InterfaceMethod method) {
    mir.routines.java2PcmMethod.CreatePCMSignatureRoutine effect = new mir.routines.java2PcmMethod.CreatePCMSignatureRoutine(this.executionState, calledBy, method);
    effect.applyRoutine();
  }
  
  public void changeReturnType(final Method jMeth, final TypeReference jType) {
    mir.routines.java2PcmMethod.ChangeReturnTypeRoutine effect = new mir.routines.java2PcmMethod.ChangeReturnTypeRoutine(this.executionState, calledBy, jMeth, jType);
    effect.applyRoutine();
  }
}
