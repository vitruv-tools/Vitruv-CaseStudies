package mir.routines.class2comp;

import mir.routines.class2comp.AddDataTypeOperationRoutine;
import mir.routines.class2comp.AddDataTypePropertyRoutine;
import mir.routines.class2comp.AssignNewPackageRoutine;
import mir.routines.class2comp.ChangeCorrespondingVisibilityRoutine;
import mir.routines.class2comp.ChangeDataTypeAttributeTypeRoutine;
import mir.routines.class2comp.ChangeDataTypeOperationRoutine;
import mir.routines.class2comp.ChangeDataTypePropertyRoutine;
import mir.routines.class2comp.CreateCompInterfaceRoutine;
import mir.routines.class2comp.CreateComponentModelRoutine;
import mir.routines.class2comp.CreateDataTypeForClassRoutine;
import mir.routines.class2comp.CreateDataTypeRoutine;
import mir.routines.class2comp.CreateDataTypeSelfCorrespondenceRoutine;
import mir.routines.class2comp.CreateInterfaceRealizationRoutine;
import mir.routines.class2comp.CreateModelSelfCorrespondenceRoutine;
import mir.routines.class2comp.CreatePrimitiveDataTypeRoutine;
import mir.routines.class2comp.CreatePrimitiveDataTypeSelfCorrespondenceRoutine;
import mir.routines.class2comp.CreateProvidedRoleRoutine;
import mir.routines.class2comp.CreateRequiredAndProvidedRoleRoutine;
import mir.routines.class2comp.CreateRequiredRoleRoutine;
import mir.routines.class2comp.CreateUmlComponentForClassRoutine;
import mir.routines.class2comp.CreateUsageRoutine;
import mir.routines.class2comp.CreatedPackageRoutine;
import mir.routines.class2comp.DeleteCompDataTypeOperationRoutine;
import mir.routines.class2comp.DeleteCompDataTypePropertyRoutine;
import mir.routines.class2comp.DeleteComponentRoutine;
import mir.routines.class2comp.MovedClassToDifferentPackageRoutine;
import mir.routines.class2comp.RemoveCorrespondenceRoutine;
import mir.routines.class2comp.RemoveInterfaceRealizationRoutine;
import mir.routines.class2comp.RemoveInterfaceRoutine;
import mir.routines.class2comp.RemoveUsageRoutine;
import mir.routines.class2comp.RenameComponentModelForClassModelRoutine;
import mir.routines.class2comp.RenameComponentRoutine;
import mir.routines.class2comp.RenameElementRoutine;
import mir.routines.class2comp.RoutineCreatedUmlClassRoutine;
import mir.routines.class2comp.RoutinePackageDeletedRoutine;
import mir.routines.class2comp.RoutinePackageRenamedRoutine;
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
  
  public boolean createModelSelfCorrespondence(final Model umlCompModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateModelSelfCorrespondenceRoutine routine = new CreateModelSelfCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlCompModel);
    return routine.applyRoutine();
  }
  
  public boolean createComponentModel(final Model umlClassModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateComponentModelRoutine routine = new CreateComponentModelRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassModel);
    return routine.applyRoutine();
  }
  
  public boolean renameComponentModelForClassModel(final Model umlClassModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameComponentModelForClassModelRoutine routine = new RenameComponentModelForClassModelRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassModel);
    return routine.applyRoutine();
  }
  
  public boolean renameElement(final NamedElement classElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameElementRoutine routine = new RenameElementRoutine(_routinesFacade, _reactionExecutionState, _caller, classElement);
    return routine.applyRoutine();
  }
  
  public boolean routineCreatedUmlClass(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RoutineCreatedUmlClassRoutine routine = new RoutineCreatedUmlClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, classPackage);
    return routine.applyRoutine();
  }
  
  public boolean createUmlComponentForClass(final org.eclipse.uml2.uml.Class umlClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlComponentForClassRoutine routine = new CreateUmlComponentForClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean createDataTypeForClass(final org.eclipse.uml2.uml.Class umlClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateDataTypeForClassRoutine routine = new CreateDataTypeForClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean renameComponent(final org.eclipse.uml2.uml.Class umlClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameComponentRoutine routine = new RenameComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean deleteComponent(final org.eclipse.uml2.uml.Class umlClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteComponentRoutine routine = new DeleteComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean createPrimitiveDataTypeSelfCorrespondence(final PrimitiveType classType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatePrimitiveDataTypeSelfCorrespondenceRoutine routine = new CreatePrimitiveDataTypeSelfCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, classType);
    return routine.applyRoutine();
  }
  
  public boolean createPrimitiveDataType(final PrimitiveType classType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatePrimitiveDataTypeRoutine routine = new CreatePrimitiveDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, classType);
    return routine.applyRoutine();
  }
  
  public boolean createDataTypeSelfCorrespondence(final DataType classType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateDataTypeSelfCorrespondenceRoutine routine = new CreateDataTypeSelfCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, classType);
    return routine.applyRoutine();
  }
  
  public boolean createDataType(final DataType classType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateDataTypeRoutine routine = new CreateDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, classType);
    return routine.applyRoutine();
  }
  
  public boolean addDataTypeProperty(final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddDataTypePropertyRoutine routine = new AddDataTypePropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, dataTypeClass, classProperty);
    return routine.applyRoutine();
  }
  
  public boolean changeDataTypeProperty(final Property classProperty) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeDataTypePropertyRoutine routine = new ChangeDataTypePropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, classProperty);
    return routine.applyRoutine();
  }
  
  public boolean deleteCompDataTypeProperty(final Property classProperty) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCompDataTypePropertyRoutine routine = new DeleteCompDataTypePropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, classProperty);
    return routine.applyRoutine();
  }
  
  public boolean changeDataTypeAttributeType(final Property classProperty, final DataType classType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeDataTypeAttributeTypeRoutine routine = new ChangeDataTypeAttributeTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, classProperty, classType);
    return routine.applyRoutine();
  }
  
  public boolean addDataTypeOperation(final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddDataTypeOperationRoutine routine = new AddDataTypeOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, dataTypeClass, classOperation);
    return routine.applyRoutine();
  }
  
  public boolean changeDataTypeOperation(final Operation classOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeDataTypeOperationRoutine routine = new ChangeDataTypeOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, classOperation);
    return routine.applyRoutine();
  }
  
  public boolean deleteCompDataTypeOperation(final Operation classOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCompDataTypeOperationRoutine routine = new DeleteCompDataTypeOperationRoutine(_routinesFacade, _reactionExecutionState, _caller, classOperation);
    return routine.applyRoutine();
  }
  
  public boolean createdPackage(final org.eclipse.uml2.uml.Package classPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatedPackageRoutine routine = new CreatedPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, classPackage);
    return routine.applyRoutine();
  }
  
  public boolean assignNewPackage(final org.eclipse.uml2.uml.Package newPackage, final Component umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AssignNewPackageRoutine routine = new AssignNewPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, newPackage, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean movedClassToDifferentPackage(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MovedClassToDifferentPackageRoutine routine = new MovedClassToDifferentPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, oldPackage, newPackage);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondence(final Classifier classObject, final Classifier compObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondenceRoutine routine = new RemoveCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, classObject, compObject);
    return routine.applyRoutine();
  }
  
  public boolean routinePackageRenamed(final org.eclipse.uml2.uml.Package classPackage, final String newName, final String oldName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RoutinePackageRenamedRoutine routine = new RoutinePackageRenamedRoutine(_routinesFacade, _reactionExecutionState, _caller, classPackage, newName, oldName);
    return routine.applyRoutine();
  }
  
  public boolean routinePackageDeleted(final org.eclipse.uml2.uml.Package classPackage, final Model classModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RoutinePackageDeletedRoutine routine = new RoutinePackageDeletedRoutine(_routinesFacade, _reactionExecutionState, _caller, classPackage, classModel);
    return routine.applyRoutine();
  }
  
  public boolean changeCorrespondingVisibility(final NamedElement classElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeCorrespondingVisibilityRoutine routine = new ChangeCorrespondingVisibilityRoutine(_routinesFacade, _reactionExecutionState, _caller, classElement);
    return routine.applyRoutine();
  }
  
  public boolean createRequiredAndProvidedRole(final Interface classInterface, final InterfaceRealization classIFRealizationReq, final org.eclipse.uml2.uml.Package interfacePackage, final org.eclipse.uml2.uml.Package classPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateRequiredAndProvidedRoleRoutine routine = new CreateRequiredAndProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, classInterface, classIFRealizationReq, interfacePackage, classPackage);
    return routine.applyRoutine();
  }
  
  public boolean createCompInterface(final Interface classInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCompInterfaceRoutine routine = new CreateCompInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, classInterface);
    return routine.applyRoutine();
  }
  
  public boolean createProvidedRole(final InterfaceRealization classIFRealization, final Interface classInterface, final org.eclipse.uml2.uml.Package affectedPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateProvidedRoleRoutine routine = new CreateProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization, classInterface, affectedPackage);
    return routine.applyRoutine();
  }
  
  public boolean createInterfaceRealization(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateInterfaceRealizationRoutine routine = new CreateInterfaceRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization, umlComp, compInterface);
    return routine.applyRoutine();
  }
  
  public boolean createRequiredRole(final InterfaceRealization classIFRealization, final Interface classInterface, final org.eclipse.uml2.uml.Package affectedPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateRequiredRoleRoutine routine = new CreateRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization, classInterface, affectedPackage);
    return routine.applyRoutine();
  }
  
  public boolean createUsage(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUsageRoutine routine = new CreateUsageRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization, umlComp, compInterface);
    return routine.applyRoutine();
  }
  
  public boolean removeInterface(final Interface classInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveInterfaceRoutine routine = new RemoveInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, classInterface);
    return routine.applyRoutine();
  }
  
  public boolean removeInterfaceRealization(final InterfaceRealization classIFRealization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveInterfaceRealizationRoutine routine = new RemoveInterfaceRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization);
    return routine.applyRoutine();
  }
  
  public boolean removeUsage(final InterfaceRealization classIFRealization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveUsageRoutine routine = new RemoveUsageRoutine(_routinesFacade, _reactionExecutionState, _caller, classIFRealization);
    return routine.applyRoutine();
  }
}
