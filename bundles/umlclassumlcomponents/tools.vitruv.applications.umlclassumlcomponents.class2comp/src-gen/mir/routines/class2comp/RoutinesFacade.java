package mir.routines.class2comp;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createModelSelfCorrespondence(final Model umlCompModel) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateModelSelfCorrespondenceRoutine routine = new mir.routines.class2comp.CreateModelSelfCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlCompModel);
    return routine.applyRoutine();
  }
  
  public boolean createComponentModel(final Model umlClassModel) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateComponentModelRoutine routine = new mir.routines.class2comp.CreateComponentModelRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassModel);
    return routine.applyRoutine();
  }
  
  public boolean renameComponentModelForClassModel(final Model umlClassModel) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.RenameComponentModelForClassModelRoutine routine = new mir.routines.class2comp.RenameComponentModelForClassModelRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassModel);
    return routine.applyRoutine();
  }
  
  public boolean renameElement(final NamedElement classElement) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.RenameElementRoutine routine = new mir.routines.class2comp.RenameElementRoutine(_routinesFacade, _reactionExecutionState, _caller, classElement);
    return routine.applyRoutine();
  }
  
  public boolean routineCreatedUmlClass(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.RoutineCreatedUmlClassRoutine routine = new mir.routines.class2comp.RoutineCreatedUmlClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, classPackage);
    return routine.applyRoutine();
  }
  
  public boolean createUmlComponentForClass(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateUmlComponentForClassRoutine routine = new mir.routines.class2comp.CreateUmlComponentForClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean createDataTypeForClass(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateDataTypeForClassRoutine routine = new mir.routines.class2comp.CreateDataTypeForClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean renameComponent(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.RenameComponentRoutine routine = new mir.routines.class2comp.RenameComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean deleteComponent(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.DeleteComponentRoutine routine = new mir.routines.class2comp.DeleteComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean createPrimitiveDataTypeSelfCorrespondence(final PrimitiveType classType) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreatePrimitiveDataTypeSelfCorrespondenceRoutine routine = new mir.routines.class2comp.CreatePrimitiveDataTypeSelfCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, classType);
    return routine.applyRoutine();
  }
  
  public boolean createPrimitiveDataType(final PrimitiveType classType) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreatePrimitiveDataTypeRoutine routine = new mir.routines.class2comp.CreatePrimitiveDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, classType);
    return routine.applyRoutine();
  }
  
  public boolean createDataTypeSelfCorrespondence(final DataType classType) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateDataTypeSelfCorrespondenceRoutine routine = new mir.routines.class2comp.CreateDataTypeSelfCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, classType);
    return routine.applyRoutine();
  }
  
  public boolean createDataType(final DataType classType) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateDataTypeRoutine routine = new mir.routines.class2comp.CreateDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, classType);
    return routine.applyRoutine();
  }
  
  public boolean addDataTypeProperty(final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.AddDataTypePropertyRoutine routine = new mir.routines.class2comp.AddDataTypePropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, dataTypeClass, classProperty);
    return routine.applyRoutine();
  }
  
  public boolean changeDataTypeProperty(final Property classProperty) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.ChangeDataTypePropertyRoutine routine = new mir.routines.class2comp.ChangeDataTypePropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, classProperty);
    return routine.applyRoutine();
  }
  
  public boolean deleteCompDataTypeProperty(final Property classProperty) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.DeleteCompDataTypePropertyRoutine routine = new mir.routines.class2comp.DeleteCompDataTypePropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, classProperty);
    return routine.applyRoutine();
  }
  
  public boolean changeDataTypeAttributeType(final Property classProperty, final DataType classType) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.ChangeDataTypeAttributeTypeRoutine routine = new mir.routines.class2comp.ChangeDataTypeAttributeTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, classProperty, classType);
    return routine.applyRoutine();
  }
  
  public boolean addDataTypeOperation(final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.AddDataTypeOperationRoutine routine = new mir.routines.class2comp.AddDataTypeOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, dataTypeClass, classOperation);
    return routine.applyRoutine();
  }
  
  public boolean changeDataTypeOperation(final Operation classOperation) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.ChangeDataTypeOperationRoutine routine = new mir.routines.class2comp.ChangeDataTypeOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, classOperation);
    return routine.applyRoutine();
  }
  
  public boolean deleteCompDataTypeOperation(final Operation classOperation) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.DeleteCompDataTypeOperationRoutine routine = new mir.routines.class2comp.DeleteCompDataTypeOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, classOperation);
    return routine.applyRoutine();
  }
  
  public boolean createdPackage(final org.eclipse.uml2.uml.Package classPackage) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreatedPackageRoutine routine = new mir.routines.class2comp.CreatedPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, classPackage);
    return routine.applyRoutine();
  }
  
  public boolean assignNewPackage(final org.eclipse.uml2.uml.Package newPackage, final Component umlComponent) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.AssignNewPackageRoutine routine = new mir.routines.class2comp.AssignNewPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, newPackage, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean movedClassToDifferentPackage(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.MovedClassToDifferentPackageRoutine routine = new mir.routines.class2comp.MovedClassToDifferentPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, oldPackage, newPackage);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondence(final Classifier classObject, final Classifier compObject) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.RemoveCorrespondenceRoutine routine = new mir.routines.class2comp.RemoveCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, classObject, compObject);
    return routine.applyRoutine();
  }
  
  public boolean routinePackageRenamed(final org.eclipse.uml2.uml.Package classPackage, final String newName, final String oldName) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.RoutinePackageRenamedRoutine routine = new mir.routines.class2comp.RoutinePackageRenamedRoutine(_routinesFacade, _reactionExecutionState, _caller, classPackage, newName, oldName);
    return routine.applyRoutine();
  }
  
  public boolean routinePackageDeleted(final org.eclipse.uml2.uml.Package classPackage, final Model classModel) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.RoutinePackageDeletedRoutine routine = new mir.routines.class2comp.RoutinePackageDeletedRoutine(_routinesFacade, _reactionExecutionState, _caller, classPackage, classModel);
    return routine.applyRoutine();
  }
  
  public boolean changeCorrespondingVisibility(final NamedElement classElement) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.ChangeCorrespondingVisibilityRoutine routine = new mir.routines.class2comp.ChangeCorrespondingVisibilityRoutine(_routinesFacade, _reactionExecutionState, _caller, classElement);
    return routine.applyRoutine();
  }
  
  public boolean createRequiredAndProvidedRole(final Interface classInterface, final InterfaceRealization classIFRealizationReq, final org.eclipse.uml2.uml.Package interfacePackage, final org.eclipse.uml2.uml.Package classPackage) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateRequiredAndProvidedRoleRoutine routine = new mir.routines.class2comp.CreateRequiredAndProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, classInterface, classIFRealizationReq, interfacePackage, classPackage);
    return routine.applyRoutine();
  }
  
  public boolean createCompInterface(final Interface classInterface) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateCompInterfaceRoutine routine = new mir.routines.class2comp.CreateCompInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, classInterface);
    return routine.applyRoutine();
  }
  
  public boolean createProvidedRole(final InterfaceRealization classIFRealization, final Interface classInterface, final org.eclipse.uml2.uml.Package affectedPackage) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateProvidedRoleRoutine routine = new mir.routines.class2comp.CreateProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization, classInterface, affectedPackage);
    return routine.applyRoutine();
  }
  
  public boolean createInterfaceRealization(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateInterfaceRealizationRoutine routine = new mir.routines.class2comp.CreateInterfaceRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization, umlComp, compInterface);
    return routine.applyRoutine();
  }
  
  public boolean createRequiredRole(final InterfaceRealization classIFRealization, final Interface classInterface, final org.eclipse.uml2.uml.Package affectedPackage) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateRequiredRoleRoutine routine = new mir.routines.class2comp.CreateRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization, classInterface, affectedPackage);
    return routine.applyRoutine();
  }
  
  public boolean createUsage(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.CreateUsageRoutine routine = new mir.routines.class2comp.CreateUsageRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization, umlComp, compInterface);
    return routine.applyRoutine();
  }
  
  public boolean removeInterface(final Interface classInterface) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.RemoveInterfaceRoutine routine = new mir.routines.class2comp.RemoveInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, classInterface);
    return routine.applyRoutine();
  }
  
  public boolean removeInterfaceRealization(final InterfaceRealization classIFRealization) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.RemoveInterfaceRealizationRoutine routine = new mir.routines.class2comp.RemoveInterfaceRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization);
    return routine.applyRoutine();
  }
  
  public boolean removeUsage(final InterfaceRealization classIFRealization) {
    mir.routines.class2comp.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.class2comp.RemoveUsageRoutine routine = new mir.routines.class2comp.RemoveUsageRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization);
    return routine.applyRoutine();
  }
}
