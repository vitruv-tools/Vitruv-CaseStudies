package mir.routines.umlToPcm;

import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Usage;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean renameElement(final NamedElement umlElement) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.RenameElementRoutine routine = new mir.routines.umlToPcm.RenameElementRoutine(_routinesFacade, _reactionExecutionState, _caller, umlElement);
    return routine.applyRoutine();
  }
  
  public boolean renameNamedElement(final NamedElement umlElement) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.RenameNamedElementRoutine routine = new mir.routines.umlToPcm.RenameNamedElementRoutine(_routinesFacade, _reactionExecutionState, _caller, umlElement);
    return routine.applyRoutine();
  }
  
  public boolean renameCollectionType(final NamedElement umlElement) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.RenameCollectionTypeRoutine routine = new mir.routines.umlToPcm.RenameCollectionTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlElement);
    return routine.applyRoutine();
  }
  
  public boolean deleteElement(final Element umlElement) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.DeleteElementRoutine routine = new mir.routines.umlToPcm.DeleteElementRoutine(_routinesFacade, _reactionExecutionState, _caller, umlElement);
    return routine.applyRoutine();
  }
  
  public boolean updateMultiplicityType(final MultiplicityElement umlElement) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.UpdateMultiplicityTypeRoutine routine = new mir.routines.umlToPcm.UpdateMultiplicityTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlElement);
    return routine.applyRoutine();
  }
  
  public boolean createPcmRepository(final Model umlModel) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreatePcmRepositoryRoutine routine = new mir.routines.umlToPcm.CreatePcmRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, umlModel);
    return routine.applyRoutine();
  }
  
  public boolean createPrimitiveDataType(final PrimitiveType umlType) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreatePrimitiveDataTypeRoutine routine = new mir.routines.umlToPcm.CreatePrimitiveDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlType);
    return routine.applyRoutine();
  }
  
  public boolean createDataType(final DataType umlType) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreateDataTypeRoutine routine = new mir.routines.umlToPcm.CreateDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlType);
    return routine.applyRoutine();
  }
  
  public boolean createCompositeDataType(final DataType umlType) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreateCompositeDataTypeRoutine routine = new mir.routines.umlToPcm.CreateCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlType);
    return routine.applyRoutine();
  }
  
  public boolean createCollectionDataType(final DataType umlType) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreateCollectionDataTypeRoutine routine = new mir.routines.umlToPcm.CreateCollectionDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlType);
    return routine.applyRoutine();
  }
  
  public boolean createInnerDeclarationOffProperty(final Property property) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreateInnerDeclarationOffPropertyRoutine routine = new mir.routines.umlToPcm.CreateInnerDeclarationOffPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, property);
    return routine.applyRoutine();
  }
  
  public boolean deleteInnerDeclarationOfProperty(final Property umlProperty) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.DeleteInnerDeclarationOfPropertyRoutine routine = new mir.routines.umlToPcm.DeleteInnerDeclarationOfPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty);
    return routine.applyRoutine();
  }
  
  public boolean changePropertyType(final Property umlProperty, final DataType umlType) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.ChangePropertyTypeRoutine routine = new mir.routines.umlToPcm.ChangePropertyTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlType);
    return routine.applyRoutine();
  }
  
  public boolean deleteDataType(final DataType umlDataType) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.DeleteDataTypeRoutine routine = new mir.routines.umlToPcm.DeleteDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlDataType);
    return routine.applyRoutine();
  }
  
  public boolean createInterface(final Interface umlInterface) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreateInterfaceRoutine routine = new mir.routines.umlToPcm.CreateInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean createInterfaceOperation(final Operation umlOperation) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreateInterfaceOperationRoutine routine = new mir.routines.umlToPcm.CreateInterfaceOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation);
    return routine.applyRoutine();
  }
  
  public boolean addOperationParameter(final Operation umlOperation, final Parameter umlParameter) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.AddOperationParameterRoutine routine = new mir.routines.umlToPcm.AddOperationParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean unsetInterfaceOperationType(final Operation umlOperation) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.UnsetInterfaceOperationTypeRoutine routine = new mir.routines.umlToPcm.UnsetInterfaceOperationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation);
    return routine.applyRoutine();
  }
  
  public boolean addInterfaceOperationParameter(final Operation umlOperation, final Parameter umlParameter) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.AddInterfaceOperationParameterRoutine routine = new mir.routines.umlToPcm.AddInterfaceOperationParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeInterfaceOperationType(final Operation umlOperation, final Parameter umlParameter) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.ChangeInterfaceOperationTypeRoutine routine = new mir.routines.umlToPcm.ChangeInterfaceOperationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterType(final Parameter umlParameter) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.ChangeParameterTypeRoutine routine = new mir.routines.umlToPcm.ChangeParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean unsetParameterType(final Parameter umlParameter) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.UnsetParameterTypeRoutine routine = new mir.routines.umlToPcm.UnsetParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterName(final Parameter umlParameter) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.ChangeParameterNameRoutine routine = new mir.routines.umlToPcm.ChangeParameterNameRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterDirection(final Parameter umlParameter) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.ChangeParameterDirectionRoutine routine = new mir.routines.umlToPcm.ChangeParameterDirectionRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean deleteParameter(final Parameter umlParameter) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.DeleteParameterRoutine routine = new mir.routines.umlToPcm.DeleteParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean createPcmComponent(final Component umlComponent) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreatePcmComponentRoutine routine = new mir.routines.umlToPcm.CreatePcmComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean createCompositeComponent(final Component umlComponent) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreateCompositeComponentRoutine routine = new mir.routines.umlToPcm.CreateCompositeComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean createBasicComponent(final Component umlComponent) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreateBasicComponentRoutine routine = new mir.routines.umlToPcm.CreateBasicComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean createRequiredRole(final Component umlComponent, final Usage umlUsage) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreateRequiredRoleRoutine routine = new mir.routines.umlToPcm.CreateRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComponent, umlUsage);
    return routine.applyRoutine();
  }
  
  public boolean changeRequiredInterface(final Usage umlUsage, final Interface umlInterface) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.ChangeRequiredInterfaceRoutine routine = new mir.routines.umlToPcm.ChangeRequiredInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlUsage, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean createProvidedRole(final Component umlComponent, final InterfaceRealization interfaceRealization) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.CreateProvidedRoleRoutine routine = new mir.routines.umlToPcm.CreateProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComponent, interfaceRealization);
    return routine.applyRoutine();
  }
  
  public boolean changeProvidedInterface(final InterfaceRealization interfaceRealization, final Interface umlInterface) {
    mir.routines.umlToPcm.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToPcm.ChangeProvidedInterfaceRoutine routine = new mir.routines.umlToPcm.ChangeProvidedInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, interfaceRealization, umlInterface);
    return routine.applyRoutine();
  }
}
