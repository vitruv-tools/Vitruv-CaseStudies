package mir.routines.umlToPcm;

import mir.routines.umlToPcm.AddInterfaceOperationParameterRoutine;
import mir.routines.umlToPcm.AddOperationParameterRoutine;
import mir.routines.umlToPcm.ChangeInterfaceOperationTypeRoutine;
import mir.routines.umlToPcm.ChangeParameterDirectionRoutine;
import mir.routines.umlToPcm.ChangeParameterNameRoutine;
import mir.routines.umlToPcm.ChangeParameterTypeRoutine;
import mir.routines.umlToPcm.ChangePropertyTypeRoutine;
import mir.routines.umlToPcm.ChangeProvidedInterfaceRoutine;
import mir.routines.umlToPcm.ChangeRequiredInterfaceRoutine;
import mir.routines.umlToPcm.CreateBasicComponentRoutine;
import mir.routines.umlToPcm.CreateCollectionDataTypeRoutine;
import mir.routines.umlToPcm.CreateCompositeComponentRoutine;
import mir.routines.umlToPcm.CreateCompositeDataTypeRoutine;
import mir.routines.umlToPcm.CreateDataTypeRoutine;
import mir.routines.umlToPcm.CreateInnerDeclarationOffPropertyRoutine;
import mir.routines.umlToPcm.CreateInterfaceOperationRoutine;
import mir.routines.umlToPcm.CreateInterfaceRoutine;
import mir.routines.umlToPcm.CreatePcmComponentRoutine;
import mir.routines.umlToPcm.CreatePcmRepositoryRoutine;
import mir.routines.umlToPcm.CreatePrimitiveDataTypeRoutine;
import mir.routines.umlToPcm.CreateProvidedRoleRoutine;
import mir.routines.umlToPcm.CreateRequiredRoleRoutine;
import mir.routines.umlToPcm.DeleteDataTypeRoutine;
import mir.routines.umlToPcm.DeleteElementRoutine;
import mir.routines.umlToPcm.DeleteInnerDeclarationOfPropertyRoutine;
import mir.routines.umlToPcm.DeleteParameterRoutine;
import mir.routines.umlToPcm.EnsureUmlModelCorrespondenceExistsRoutine;
import mir.routines.umlToPcm.RenameCollectionTypeRoutine;
import mir.routines.umlToPcm.RenameElementRoutine;
import mir.routines.umlToPcm.RenameNamedElementRoutine;
import mir.routines.umlToPcm.UnsetInterfaceOperationTypeRoutine;
import mir.routines.umlToPcm.UnsetParameterTypeRoutine;
import mir.routines.umlToPcm.UpdateMultiplicityTypeRoutine;
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
  
  public boolean renameElement(final NamedElement umlElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameElementRoutine routine = new RenameElementRoutine(_routinesFacade, _reactionExecutionState, _caller, umlElement);
    return routine.applyRoutine();
  }
  
  public boolean renameNamedElement(final NamedElement umlElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameNamedElementRoutine routine = new RenameNamedElementRoutine(_routinesFacade, _reactionExecutionState, _caller, umlElement);
    return routine.applyRoutine();
  }
  
  public boolean renameCollectionType(final NamedElement umlElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameCollectionTypeRoutine routine = new RenameCollectionTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlElement);
    return routine.applyRoutine();
  }
  
  public boolean deleteElement(final Element umlElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteElementRoutine routine = new DeleteElementRoutine(_routinesFacade, _reactionExecutionState, _caller, umlElement);
    return routine.applyRoutine();
  }
  
  public boolean updateMultiplicityType(final MultiplicityElement umlElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateMultiplicityTypeRoutine routine = new UpdateMultiplicityTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlElement);
    return routine.applyRoutine();
  }
  
  public boolean createPcmRepository(final Model umlModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatePcmRepositoryRoutine routine = new CreatePcmRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, umlModel);
    return routine.applyRoutine();
  }
  
  public boolean ensureUmlModelCorrespondenceExists(final Model newModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnsureUmlModelCorrespondenceExistsRoutine routine = new EnsureUmlModelCorrespondenceExistsRoutine(_routinesFacade, _reactionExecutionState, _caller, newModel);
    return routine.applyRoutine();
  }
  
  public boolean createPrimitiveDataType(final PrimitiveType umlType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatePrimitiveDataTypeRoutine routine = new CreatePrimitiveDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlType);
    return routine.applyRoutine();
  }
  
  public boolean createDataType(final DataType umlType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateDataTypeRoutine routine = new CreateDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlType);
    return routine.applyRoutine();
  }
  
  public boolean createCompositeDataType(final DataType umlType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCompositeDataTypeRoutine routine = new CreateCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlType);
    return routine.applyRoutine();
  }
  
  public boolean createCollectionDataType(final DataType umlType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCollectionDataTypeRoutine routine = new CreateCollectionDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlType);
    return routine.applyRoutine();
  }
  
  public boolean createInnerDeclarationOffProperty(final Property property) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateInnerDeclarationOffPropertyRoutine routine = new CreateInnerDeclarationOffPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, property);
    return routine.applyRoutine();
  }
  
  public boolean deleteInnerDeclarationOfProperty(final Property umlProperty) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteInnerDeclarationOfPropertyRoutine routine = new DeleteInnerDeclarationOfPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty);
    return routine.applyRoutine();
  }
  
  public boolean changePropertyType(final Property umlProperty, final DataType umlType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangePropertyTypeRoutine routine = new ChangePropertyTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlType);
    return routine.applyRoutine();
  }
  
  public boolean deleteDataType(final DataType umlDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteDataTypeRoutine routine = new DeleteDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlDataType);
    return routine.applyRoutine();
  }
  
  public boolean createInterface(final Interface umlInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateInterfaceRoutine routine = new CreateInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean createInterfaceOperation(final Operation umlOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateInterfaceOperationRoutine routine = new CreateInterfaceOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation);
    return routine.applyRoutine();
  }
  
  public boolean addOperationParameter(final Operation umlOperation, final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddOperationParameterRoutine routine = new AddOperationParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean unsetInterfaceOperationType(final Operation umlOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UnsetInterfaceOperationTypeRoutine routine = new UnsetInterfaceOperationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation);
    return routine.applyRoutine();
  }
  
  public boolean addInterfaceOperationParameter(final Operation umlOperation, final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddInterfaceOperationParameterRoutine routine = new AddInterfaceOperationParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeInterfaceOperationType(final Operation umlOperation, final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeInterfaceOperationTypeRoutine routine = new ChangeInterfaceOperationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterType(final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeParameterTypeRoutine routine = new ChangeParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean unsetParameterType(final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UnsetParameterTypeRoutine routine = new UnsetParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterName(final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeParameterNameRoutine routine = new ChangeParameterNameRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterDirection(final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeParameterDirectionRoutine routine = new ChangeParameterDirectionRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean deleteParameter(final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteParameterRoutine routine = new DeleteParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean createPcmComponent(final Component umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatePcmComponentRoutine routine = new CreatePcmComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean createCompositeComponent(final Component umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCompositeComponentRoutine routine = new CreateCompositeComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean createBasicComponent(final Component umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateBasicComponentRoutine routine = new CreateBasicComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean createRequiredRole(final Component umlComponent, final Usage umlUsage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateRequiredRoleRoutine routine = new CreateRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComponent, umlUsage);
    return routine.applyRoutine();
  }
  
  public boolean changeRequiredInterface(final Usage umlUsage, final Interface umlInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeRequiredInterfaceRoutine routine = new ChangeRequiredInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlUsage, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean createProvidedRole(final Component umlComponent, final InterfaceRealization interfaceRealization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateProvidedRoleRoutine routine = new CreateProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComponent, interfaceRealization);
    return routine.applyRoutine();
  }
  
  public boolean changeProvidedInterface(final InterfaceRealization interfaceRealization, final Interface umlInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeProvidedInterfaceRoutine routine = new ChangeProvidedInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, interfaceRealization, umlInterface);
    return routine.applyRoutine();
  }
}
