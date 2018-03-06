package mir.routines.java2PcmMethod;

import mir.routines.java2PcmMethod.ChangeInnerDeclarationTypeRoutine;
import mir.routines.java2PcmMethod.ChangeParameterNameRoutine;
import mir.routines.java2PcmMethod.ChangeReturnTypeRoutine;
import mir.routines.java2PcmMethod.CreateAssemblyContextRoutine;
import mir.routines.java2PcmMethod.CreateInnerDeclarationRoutine;
import mir.routines.java2PcmMethod.CreateOperationRequiredRoleCorrespondingToFieldRoutine;
import mir.routines.java2PcmMethod.CreatePCMSignatureRoutine;
import mir.routines.java2PcmMethod.CreateParameterRoutine;
import mir.routines.java2PcmMethod.CreateSEFFRoutine;
import mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfaceRoutine;
import mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfacesRoutine;
import mir.routines.java2PcmMethod.DeleteParameterRoutine;
import mir.routines.java2PcmMethod.FieldCreatedCorrespondingToOperationInterfaceRoutine;
import mir.routines.java2PcmMethod.FieldCreatedCorrespondingToRepositoryComponentRoutine;
import mir.routines.java2PcmMethod.RenameNamedElementRoutine;
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
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean renameNamedElement(final NamedElement javaElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameNamedElementRoutine routine = new RenameNamedElementRoutine(_routinesFacade, _reactionExecutionState, _caller, javaElement);
    return routine.applyRoutine();
  }
  
  public boolean createParameter(final OrdinaryParameter javaParameter, final Parametrizable javaMethod) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateParameterRoutine routine = new CreateParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, javaParameter, javaMethod);
    return routine.applyRoutine();
  }
  
  public boolean deleteParameter(final OrdinaryParameter javaParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteParameterRoutine routine = new DeleteParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, javaParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterName(final String newName, final Parameter javaParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeParameterNameRoutine routine = new ChangeParameterNameRoutine(_routinesFacade, _reactionExecutionState, _caller, newName, javaParameter);
    return routine.applyRoutine();
  }
  
  public boolean createInnerDeclaration(final ConcreteClassifier classifier, final Field javaField) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateInnerDeclarationRoutine routine = new CreateInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean createAssemblyContext(final ConcreteClassifier classifier, final Field javaField) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateAssemblyContextRoutine routine = new CreateAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean fieldCreatedCorrespondingToOperationInterface(final Classifier classifier, final Field javaField) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    FieldCreatedCorrespondingToOperationInterfaceRoutine routine = new FieldCreatedCorrespondingToOperationInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean fieldCreatedCorrespondingToRepositoryComponent(final Classifier classifier, final Field javaField) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    FieldCreatedCorrespondingToRepositoryComponentRoutine routine = new FieldCreatedCorrespondingToRepositoryComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean createOperationRequiredRoleCorrespondingToField(final Field javaField, final OperationInterface operationInterface, final RepositoryComponent repoComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateOperationRequiredRoleCorrespondingToFieldRoutine routine = new CreateOperationRequiredRoleCorrespondingToFieldRoutine(_routinesFacade, _reactionExecutionState, _caller, javaField, operationInterface, repoComponent);
    return routine.applyRoutine();
  }
  
  public boolean changeInnerDeclarationType(final TypeReference typeReference, final Field javaField) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeInnerDeclarationTypeRoutine routine = new ChangeInnerDeclarationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, typeReference, javaField);
    return routine.applyRoutine();
  }
  
  public boolean createSeffFromImplementingInterfaces(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateSeffFromImplementingInterfacesRoutine routine = new CreateSeffFromImplementingInterfacesRoutine(_routinesFacade, _reactionExecutionState, _caller, classMethod, javaClass);
    return routine.applyRoutine();
  }
  
  public boolean createSeffFromImplementingInterface(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass, final Interface javaInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateSeffFromImplementingInterfaceRoutine routine = new CreateSeffFromImplementingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, classMethod, javaClass, javaInterface);
    return routine.applyRoutine();
  }
  
  public boolean createSEFF(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateSEFFRoutine routine = new CreateSEFFRoutine(_routinesFacade, _reactionExecutionState, _caller, javaMethod, javaClass, classMethod);
    return routine.applyRoutine();
  }
  
  public boolean createPCMSignature(final InterfaceMethod interfaceMethod) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatePCMSignatureRoutine routine = new CreatePCMSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, interfaceMethod);
    return routine.applyRoutine();
  }
  
  public boolean changeReturnType(final Method javaMethod, final TypeReference typeReference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeReturnTypeRoutine routine = new ChangeReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, javaMethod, typeReference);
    return routine.applyRoutine();
  }
}
