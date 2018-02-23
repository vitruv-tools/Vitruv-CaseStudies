package mir.routines.comp2class;

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
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createModelSelfCorrespondence(final Model compModel) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.CreateModelSelfCorrespondenceRoutine routine = new mir.routines.comp2class.CreateModelSelfCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, compModel);
    return routine.applyRoutine();
  }
  
  public boolean createClassModel(final Model compModel) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.CreateClassModelRoutine routine = new mir.routines.comp2class.CreateClassModelRoutine(_routinesFacade, _reactionExecutionState, _caller, compModel);
    return routine.applyRoutine();
  }
  
  public boolean createDataTypePackage(final Model compModel) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.CreateDataTypePackageRoutine routine = new mir.routines.comp2class.CreateDataTypePackageRoutine(_routinesFacade, _reactionExecutionState, _caller, compModel);
    return routine.applyRoutine();
  }
  
  public boolean renameClassModelForComponentModel(final Model compModel) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.RenameClassModelForComponentModelRoutine routine = new mir.routines.comp2class.RenameClassModelForComponentModelRoutine(_routinesFacade, _reactionExecutionState, _caller, compModel);
    return routine.applyRoutine();
  }
  
  public boolean renameElement(final NamedElement compElement) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.RenameElementRoutine routine = new mir.routines.comp2class.RenameElementRoutine(_routinesFacade, _reactionExecutionState, _caller, compElement);
    return routine.applyRoutine();
  }
  
  public boolean changeCorrespondingVisibility(final NamedElement compElement) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.ChangeCorrespondingVisibilityRoutine routine = new mir.routines.comp2class.ChangeCorrespondingVisibilityRoutine(_routinesFacade, _reactionExecutionState, _caller, compElement);
    return routine.applyRoutine();
  }
  
  public boolean createClassWithPackage(final Component umlComp) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.CreateClassWithPackageRoutine routine = new mir.routines.comp2class.CreateClassWithPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComp);
    return routine.applyRoutine();
  }
  
  public boolean renameClassAndPackage(final Component umlComp, final String newName) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.RenameClassAndPackageRoutine routine = new mir.routines.comp2class.RenameClassAndPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComp, newName);
    return routine.applyRoutine();
  }
  
  public boolean deleteClass(final Component umlComp) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.DeleteClassRoutine routine = new mir.routines.comp2class.DeleteClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComp);
    return routine.applyRoutine();
  }
  
  public boolean createDataTypeForDataType(final DataType compType) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.CreateDataTypeForDataTypeRoutine routine = new mir.routines.comp2class.CreateDataTypeForDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, compType);
    return routine.applyRoutine();
  }
  
  public boolean createDataTypeSelfCorrespondence(final DataType compType) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.CreateDataTypeSelfCorrespondenceRoutine routine = new mir.routines.comp2class.CreateDataTypeSelfCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, compType);
    return routine.applyRoutine();
  }
  
  public boolean createClassForDataType(final DataType compType) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.CreateClassForDataTypeRoutine routine = new mir.routines.comp2class.CreateClassForDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, compType);
    return routine.applyRoutine();
  }
  
  public boolean addClassDataTypeProperty(final Property compProperty, final DataType compDataType) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.AddClassDataTypePropertyRoutine routine = new mir.routines.comp2class.AddClassDataTypePropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, compProperty, compDataType);
    return routine.applyRoutine();
  }
  
  public boolean changeClassDataTypeProperty(final Property compProperty) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.ChangeClassDataTypePropertyRoutine routine = new mir.routines.comp2class.ChangeClassDataTypePropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, compProperty);
    return routine.applyRoutine();
  }
  
  public boolean addDataTypeOperation(final Operation compOperation, final DataType compDataType) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.AddDataTypeOperationRoutine routine = new mir.routines.comp2class.AddDataTypeOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, compOperation, compDataType);
    return routine.applyRoutine();
  }
  
  public boolean changeClassDataTypeOperation(final Operation compOperation) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.ChangeClassDataTypeOperationRoutine routine = new mir.routines.comp2class.ChangeClassDataTypeOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, compOperation);
    return routine.applyRoutine();
  }
  
  public boolean createClassInterface(final Interface compInterface, final Component umlComp) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.CreateClassInterfaceRoutine routine = new mir.routines.comp2class.CreateClassInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, compInterface, umlComp);
    return routine.applyRoutine();
  }
  
  public boolean createClassInterfaceRealization(final NamedElement iFRealizationOrUsage, final Component umlComp) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.CreateClassInterfaceRealizationRoutine routine = new mir.routines.comp2class.CreateClassInterfaceRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, iFRealizationOrUsage, umlComp);
    return routine.applyRoutine();
  }
  
  public boolean addClassInterfaceRealizationToClass(final NamedElement iFRealizationOrUsage, final Interface compInterface, final Component umlComp) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.AddClassInterfaceRealizationToClassRoutine routine = new mir.routines.comp2class.AddClassInterfaceRealizationToClassRoutine(_routinesFacade, _reactionExecutionState, _caller, iFRealizationOrUsage, compInterface, umlComp);
    return routine.applyRoutine();
  }
  
  public boolean removeInterfaceRealizationForInterfaceRealization(final InterfaceRealization compIFRealization) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.RemoveInterfaceRealizationForInterfaceRealizationRoutine routine = new mir.routines.comp2class.RemoveInterfaceRealizationForInterfaceRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, compIFRealization);
    return routine.applyRoutine();
  }
  
  public boolean removeInterfaceRealizationForUsage(final Usage compUsage) {
    mir.routines.comp2class.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.comp2class.RemoveInterfaceRealizationForUsageRoutine routine = new mir.routines.comp2class.RemoveInterfaceRealizationForUsageRoutine(_routinesFacade, _reactionExecutionState, _caller, compUsage);
    return routine.applyRoutine();
  }
}
