package mir.routines.comp2class;

import mir.routines.comp2class.AddClassDataTypePropertyRoutine;
import mir.routines.comp2class.AddClassInterfaceRealizationToClassRoutine;
import mir.routines.comp2class.AddDataTypeOperationRoutine;
import mir.routines.comp2class.ChangeClassDataTypeOperationRoutine;
import mir.routines.comp2class.ChangeClassDataTypePropertyRoutine;
import mir.routines.comp2class.ChangeCorrespondingVisibilityRoutine;
import mir.routines.comp2class.CreateClassForDataTypeRoutine;
import mir.routines.comp2class.CreateClassInterfaceRealizationRoutine;
import mir.routines.comp2class.CreateClassInterfaceRoutine;
import mir.routines.comp2class.CreateClassModelRoutine;
import mir.routines.comp2class.CreateClassWithPackageRoutine;
import mir.routines.comp2class.CreateDataTypeForDataTypeRoutine;
import mir.routines.comp2class.CreateDataTypePackageRoutine;
import mir.routines.comp2class.CreateDataTypeSelfCorrespondenceRoutine;
import mir.routines.comp2class.CreateModelSelfCorrespondenceRoutine;
import mir.routines.comp2class.DeleteClassRoutine;
import mir.routines.comp2class.RemoveInterfaceRealizationForInterfaceRealizationRoutine;
import mir.routines.comp2class.RemoveInterfaceRealizationForUsageRoutine;
import mir.routines.comp2class.RenameClassAndPackageRoutine;
import mir.routines.comp2class.RenameClassModelForComponentModelRoutine;
import mir.routines.comp2class.RenameElementRoutine;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
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
  
  public boolean createModelSelfCorrespondence(final Model compModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateModelSelfCorrespondenceRoutine routine = new CreateModelSelfCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, compModel);
    return routine.applyRoutine();
  }
  
  public boolean createClassModel(final Model compModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateClassModelRoutine routine = new CreateClassModelRoutine(_routinesFacade, _reactionExecutionState, _caller, compModel);
    return routine.applyRoutine();
  }
  
  public boolean createDataTypePackage(final Model compModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateDataTypePackageRoutine routine = new CreateDataTypePackageRoutine(_routinesFacade, _reactionExecutionState, _caller, compModel);
    return routine.applyRoutine();
  }
  
  public boolean renameClassModelForComponentModel(final Model compModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameClassModelForComponentModelRoutine routine = new RenameClassModelForComponentModelRoutine(_routinesFacade, _reactionExecutionState, _caller, compModel);
    return routine.applyRoutine();
  }
  
  public boolean renameElement(final NamedElement compElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameElementRoutine routine = new RenameElementRoutine(_routinesFacade, _reactionExecutionState, _caller, compElement);
    return routine.applyRoutine();
  }
  
  public boolean changeCorrespondingVisibility(final NamedElement compElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeCorrespondingVisibilityRoutine routine = new ChangeCorrespondingVisibilityRoutine(_routinesFacade, _reactionExecutionState, _caller, compElement);
    return routine.applyRoutine();
  }
  
  public boolean createClassWithPackage(final Component umlComp) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateClassWithPackageRoutine routine = new CreateClassWithPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComp);
    return routine.applyRoutine();
  }
  
  public boolean renameClassAndPackage(final Component umlComp, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameClassAndPackageRoutine routine = new RenameClassAndPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComp, newName);
    return routine.applyRoutine();
  }
  
  public boolean deleteClass(final Component umlComp) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteClassRoutine routine = new DeleteClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComp);
    return routine.applyRoutine();
  }
  
  public boolean createDataTypeForDataType(final DataType compType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateDataTypeForDataTypeRoutine routine = new CreateDataTypeForDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, compType);
    return routine.applyRoutine();
  }
  
  public boolean createDataTypeSelfCorrespondence(final DataType compType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateDataTypeSelfCorrespondenceRoutine routine = new CreateDataTypeSelfCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, compType);
    return routine.applyRoutine();
  }
  
  public boolean createClassForDataType(final DataType compType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateClassForDataTypeRoutine routine = new CreateClassForDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, compType);
    return routine.applyRoutine();
  }
  
  public boolean addClassDataTypeProperty(final Property compProperty, final DataType compDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddClassDataTypePropertyRoutine routine = new AddClassDataTypePropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, compProperty, compDataType);
    return routine.applyRoutine();
  }
  
  public boolean changeClassDataTypeProperty(final Property compProperty) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeClassDataTypePropertyRoutine routine = new ChangeClassDataTypePropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, compProperty);
    return routine.applyRoutine();
  }
  
  public boolean addDataTypeOperation(final Operation compOperation, final DataType compDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddDataTypeOperationRoutine routine = new AddDataTypeOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, compOperation, compDataType);
    return routine.applyRoutine();
  }
  
  public boolean changeClassDataTypeOperation(final Operation compOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeClassDataTypeOperationRoutine routine = new ChangeClassDataTypeOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, compOperation);
    return routine.applyRoutine();
  }
  
  public boolean createClassInterface(final Interface compInterface, final Component umlComp) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateClassInterfaceRoutine routine = new CreateClassInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, compInterface, umlComp);
    return routine.applyRoutine();
  }
  
  public boolean createClassInterfaceRealization(final NamedElement iFRealizationOrUsage, final Component umlComp) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateClassInterfaceRealizationRoutine routine = new CreateClassInterfaceRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, iFRealizationOrUsage, umlComp);
    return routine.applyRoutine();
  }
  
  public boolean addClassInterfaceRealizationToClass(final NamedElement iFRealizationOrUsage, final Interface compInterface, final Component umlComp) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddClassInterfaceRealizationToClassRoutine routine = new AddClassInterfaceRealizationToClassRoutine(_routinesFacade, _reactionExecutionState, _caller, iFRealizationOrUsage, compInterface, umlComp);
    return routine.applyRoutine();
  }
  
  public boolean removeInterfaceRealizationForInterfaceRealization(final InterfaceRealization compIFRealization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveInterfaceRealizationForInterfaceRealizationRoutine routine = new RemoveInterfaceRealizationForInterfaceRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, compIFRealization);
    return routine.applyRoutine();
  }
  
  public boolean removeInterfaceRealizationForUsage(final Usage compUsage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveInterfaceRealizationForUsageRoutine routine = new RemoveInterfaceRealizationForUsageRoutine(_routinesFacade, _reactionExecutionState, _caller, compUsage);
    return routine.applyRoutine();
  }
}
