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
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean renameNamedElement(final NamedElement javaElement) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.RenameNamedElementRoutine routine = new mir.routines.java2PcmMethod.RenameNamedElementRoutine(_routinesFacade, _reactionExecutionState, _caller, javaElement);
    return routine.applyRoutine();
  }
  
  public boolean createParameter(final OrdinaryParameter javaParameter, final Parametrizable javaMethod) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateParameterRoutine routine = new mir.routines.java2PcmMethod.CreateParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, javaParameter, javaMethod);
    return routine.applyRoutine();
  }
  
  public boolean deleteParameter(final OrdinaryParameter javaParameter) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.DeleteParameterRoutine routine = new mir.routines.java2PcmMethod.DeleteParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, javaParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterName(final String newName, final Parameter javaParameter) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.ChangeParameterNameRoutine routine = new mir.routines.java2PcmMethod.ChangeParameterNameRoutine(_routinesFacade, _reactionExecutionState, _caller, newName, javaParameter);
    return routine.applyRoutine();
  }
  
  public boolean createInnerDeclaration(final ConcreteClassifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateInnerDeclarationRoutine routine = new mir.routines.java2PcmMethod.CreateInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean createAssemblyContext(final ConcreteClassifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateAssemblyContextRoutine routine = new mir.routines.java2PcmMethod.CreateAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean fieldCreatedCorrespondingToOperationInterface(final Classifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.FieldCreatedCorrespondingToOperationInterfaceRoutine routine = new mir.routines.java2PcmMethod.FieldCreatedCorrespondingToOperationInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean fieldCreatedCorrespondingToRepositoryComponent(final Classifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.FieldCreatedCorrespondingToRepositoryComponentRoutine routine = new mir.routines.java2PcmMethod.FieldCreatedCorrespondingToRepositoryComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean createOperationRequiredRoleCorrespondingToField(final Field javaField, final OperationInterface operationInterface, final RepositoryComponent repoComponent) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateOperationRequiredRoleCorrespondingToFieldRoutine routine = new mir.routines.java2PcmMethod.CreateOperationRequiredRoleCorrespondingToFieldRoutine(_routinesFacade, _reactionExecutionState, _caller, javaField, operationInterface, repoComponent);
    return routine.applyRoutine();
  }
  
  public boolean changeInnerDeclarationType(final TypeReference typeReference, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.ChangeInnerDeclarationTypeRoutine routine = new mir.routines.java2PcmMethod.ChangeInnerDeclarationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, typeReference, javaField);
    return routine.applyRoutine();
  }
  
  public boolean createSeffFromImplementingInterfaces(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfacesRoutine routine = new mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfacesRoutine(_routinesFacade, _reactionExecutionState, _caller, classMethod, javaClass);
    return routine.applyRoutine();
  }
  
  public boolean createSeffFromImplementingInterface(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass, final Interface javaInterface) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfaceRoutine routine = new mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, classMethod, javaClass, javaInterface);
    return routine.applyRoutine();
  }
  
  public boolean createSEFF(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateSEFFRoutine routine = new mir.routines.java2PcmMethod.CreateSEFFRoutine(_routinesFacade, _reactionExecutionState, _caller, javaMethod, javaClass, classMethod);
    return routine.applyRoutine();
  }
  
  public boolean createPCMSignature(final InterfaceMethod interfaceMethod) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreatePCMSignatureRoutine routine = new mir.routines.java2PcmMethod.CreatePCMSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, interfaceMethod);
    return routine.applyRoutine();
  }
  
  public boolean changeReturnType(final Method javaMethod, final TypeReference typeReference) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.ChangeReturnTypeRoutine routine = new mir.routines.java2PcmMethod.ChangeReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, javaMethod, typeReference);
    return routine.applyRoutine();
  }
}
