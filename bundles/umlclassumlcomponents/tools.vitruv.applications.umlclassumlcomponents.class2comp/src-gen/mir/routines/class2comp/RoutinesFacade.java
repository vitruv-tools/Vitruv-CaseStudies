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
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public boolean createModelSelfCorrespondence(final Model umlCompModel) {
    mir.routines.class2comp.CreateModelSelfCorrespondenceRoutine effect = new mir.routines.class2comp.CreateModelSelfCorrespondenceRoutine(this.executionState, calledBy, umlCompModel);
    return effect.applyRoutine();
  }
  
  public boolean createComponentModel(final Model umlClassModel) {
    mir.routines.class2comp.CreateComponentModelRoutine effect = new mir.routines.class2comp.CreateComponentModelRoutine(this.executionState, calledBy, umlClassModel);
    return effect.applyRoutine();
  }
  
  public boolean renameComponentModelForClassModel(final Model umlClassModel) {
    mir.routines.class2comp.RenameComponentModelForClassModelRoutine effect = new mir.routines.class2comp.RenameComponentModelForClassModelRoutine(this.executionState, calledBy, umlClassModel);
    return effect.applyRoutine();
  }
  
  public boolean renameElement(final NamedElement classElement) {
    mir.routines.class2comp.RenameElementRoutine effect = new mir.routines.class2comp.RenameElementRoutine(this.executionState, calledBy, classElement);
    return effect.applyRoutine();
  }
  
  public boolean routineCreatedUmlClass(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage) {
    mir.routines.class2comp.RoutineCreatedUmlClassRoutine effect = new mir.routines.class2comp.RoutineCreatedUmlClassRoutine(this.executionState, calledBy, umlClass, classPackage);
    return effect.applyRoutine();
  }
  
  public boolean createUmlComponentForClass(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.CreateUmlComponentForClassRoutine effect = new mir.routines.class2comp.CreateUmlComponentForClassRoutine(this.executionState, calledBy, umlClass);
    return effect.applyRoutine();
  }
  
  public boolean createDataTypeForClass(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.CreateDataTypeForClassRoutine effect = new mir.routines.class2comp.CreateDataTypeForClassRoutine(this.executionState, calledBy, umlClass);
    return effect.applyRoutine();
  }
  
  public boolean renameComponent(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.RenameComponentRoutine effect = new mir.routines.class2comp.RenameComponentRoutine(this.executionState, calledBy, umlClass);
    return effect.applyRoutine();
  }
  
  public boolean deleteComponent(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.DeleteComponentRoutine effect = new mir.routines.class2comp.DeleteComponentRoutine(this.executionState, calledBy, umlClass);
    return effect.applyRoutine();
  }
  
  public boolean createPrimitiveDataTypeSelfCorrespondence(final PrimitiveType classType) {
    mir.routines.class2comp.CreatePrimitiveDataTypeSelfCorrespondenceRoutine effect = new mir.routines.class2comp.CreatePrimitiveDataTypeSelfCorrespondenceRoutine(this.executionState, calledBy, classType);
    return effect.applyRoutine();
  }
  
  public boolean createPrimitiveDataType(final PrimitiveType classType) {
    mir.routines.class2comp.CreatePrimitiveDataTypeRoutine effect = new mir.routines.class2comp.CreatePrimitiveDataTypeRoutine(this.executionState, calledBy, classType);
    return effect.applyRoutine();
  }
  
  public boolean createDataTypeSelfCorrespondence(final DataType classType) {
    mir.routines.class2comp.CreateDataTypeSelfCorrespondenceRoutine effect = new mir.routines.class2comp.CreateDataTypeSelfCorrespondenceRoutine(this.executionState, calledBy, classType);
    return effect.applyRoutine();
  }
  
  public boolean createDataType(final DataType classType) {
    mir.routines.class2comp.CreateDataTypeRoutine effect = new mir.routines.class2comp.CreateDataTypeRoutine(this.executionState, calledBy, classType);
    return effect.applyRoutine();
  }
  
  public boolean addDataTypeProperty(final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
    mir.routines.class2comp.AddDataTypePropertyRoutine effect = new mir.routines.class2comp.AddDataTypePropertyRoutine(this.executionState, calledBy, dataTypeClass, classProperty);
    return effect.applyRoutine();
  }
  
  public boolean changeDataTypeProperty(final Property classProperty) {
    mir.routines.class2comp.ChangeDataTypePropertyRoutine effect = new mir.routines.class2comp.ChangeDataTypePropertyRoutine(this.executionState, calledBy, classProperty);
    return effect.applyRoutine();
  }
  
  public boolean deleteCompDataTypeProperty(final Property classProperty) {
    mir.routines.class2comp.DeleteCompDataTypePropertyRoutine effect = new mir.routines.class2comp.DeleteCompDataTypePropertyRoutine(this.executionState, calledBy, classProperty);
    return effect.applyRoutine();
  }
  
  public boolean changeDataTypeAttributeType(final Property classProperty, final DataType classType) {
    mir.routines.class2comp.ChangeDataTypeAttributeTypeRoutine effect = new mir.routines.class2comp.ChangeDataTypeAttributeTypeRoutine(this.executionState, calledBy, classProperty, classType);
    return effect.applyRoutine();
  }
  
  public boolean addDataTypeOperation(final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
    mir.routines.class2comp.AddDataTypeOperationRoutine effect = new mir.routines.class2comp.AddDataTypeOperationRoutine(this.executionState, calledBy, dataTypeClass, classOperation);
    return effect.applyRoutine();
  }
  
  public boolean changeDataTypeOperation(final Operation classOperation) {
    mir.routines.class2comp.ChangeDataTypeOperationRoutine effect = new mir.routines.class2comp.ChangeDataTypeOperationRoutine(this.executionState, calledBy, classOperation);
    return effect.applyRoutine();
  }
  
  public boolean deleteCompDataTypeOperation(final Operation classOperation) {
    mir.routines.class2comp.DeleteCompDataTypeOperationRoutine effect = new mir.routines.class2comp.DeleteCompDataTypeOperationRoutine(this.executionState, calledBy, classOperation);
    return effect.applyRoutine();
  }
  
  public boolean createdPackage(final org.eclipse.uml2.uml.Package classPackage) {
    mir.routines.class2comp.CreatedPackageRoutine effect = new mir.routines.class2comp.CreatedPackageRoutine(this.executionState, calledBy, classPackage);
    return effect.applyRoutine();
  }
  
  public boolean assignNewPackage(final org.eclipse.uml2.uml.Package newPackage, final Component umlComponent) {
    mir.routines.class2comp.AssignNewPackageRoutine effect = new mir.routines.class2comp.AssignNewPackageRoutine(this.executionState, calledBy, newPackage, umlComponent);
    return effect.applyRoutine();
  }
  
  public boolean movedClassToDifferentPackage(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage) {
    mir.routines.class2comp.MovedClassToDifferentPackageRoutine effect = new mir.routines.class2comp.MovedClassToDifferentPackageRoutine(this.executionState, calledBy, umlClass, oldPackage, newPackage);
    return effect.applyRoutine();
  }
  
  public boolean removeCorrespondence(final Classifier classObject, final Classifier compObject) {
    mir.routines.class2comp.RemoveCorrespondenceRoutine effect = new mir.routines.class2comp.RemoveCorrespondenceRoutine(this.executionState, calledBy, classObject, compObject);
    return effect.applyRoutine();
  }
  
  public boolean routinePackageRenamed(final org.eclipse.uml2.uml.Package classPackage, final String newName, final String oldName) {
    mir.routines.class2comp.RoutinePackageRenamedRoutine effect = new mir.routines.class2comp.RoutinePackageRenamedRoutine(this.executionState, calledBy, classPackage, newName, oldName);
    return effect.applyRoutine();
  }
  
  public boolean routinePackageDeleted(final org.eclipse.uml2.uml.Package classPackage, final Model classModel) {
    mir.routines.class2comp.RoutinePackageDeletedRoutine effect = new mir.routines.class2comp.RoutinePackageDeletedRoutine(this.executionState, calledBy, classPackage, classModel);
    return effect.applyRoutine();
  }
  
  public boolean changeCorrespondingVisibility(final NamedElement classElement) {
    mir.routines.class2comp.ChangeCorrespondingVisibilityRoutine effect = new mir.routines.class2comp.ChangeCorrespondingVisibilityRoutine(this.executionState, calledBy, classElement);
    return effect.applyRoutine();
  }
  
  public boolean createRequiredAndProvidedRole(final Interface classInterface, final InterfaceRealization classIFRealizationReq, final org.eclipse.uml2.uml.Package interfacePackage, final org.eclipse.uml2.uml.Package classPackage) {
    mir.routines.class2comp.CreateRequiredAndProvidedRoleRoutine effect = new mir.routines.class2comp.CreateRequiredAndProvidedRoleRoutine(this.executionState, calledBy, classInterface, classIFRealizationReq, interfacePackage, classPackage);
    return effect.applyRoutine();
  }
  
  public boolean createCompInterface(final Interface classInterface) {
    mir.routines.class2comp.CreateCompInterfaceRoutine effect = new mir.routines.class2comp.CreateCompInterfaceRoutine(this.executionState, calledBy, classInterface);
    return effect.applyRoutine();
  }
  
  public boolean createProvidedRole(final InterfaceRealization classIFRealization, final Interface classInterface, final org.eclipse.uml2.uml.Package affectedPackage) {
    mir.routines.class2comp.CreateProvidedRoleRoutine effect = new mir.routines.class2comp.CreateProvidedRoleRoutine(this.executionState, calledBy, classIFRealization, classInterface, affectedPackage);
    return effect.applyRoutine();
  }
  
  public boolean createInterfaceRealization(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface) {
    mir.routines.class2comp.CreateInterfaceRealizationRoutine effect = new mir.routines.class2comp.CreateInterfaceRealizationRoutine(this.executionState, calledBy, classIFRealization, umlComp, compInterface);
    return effect.applyRoutine();
  }
  
  public boolean createRequiredRole(final InterfaceRealization classIFRealization, final Interface classInterface, final org.eclipse.uml2.uml.Package affectedPackage) {
    mir.routines.class2comp.CreateRequiredRoleRoutine effect = new mir.routines.class2comp.CreateRequiredRoleRoutine(this.executionState, calledBy, classIFRealization, classInterface, affectedPackage);
    return effect.applyRoutine();
  }
  
  public boolean createUsage(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface) {
    mir.routines.class2comp.CreateUsageRoutine effect = new mir.routines.class2comp.CreateUsageRoutine(this.executionState, calledBy, classIFRealization, umlComp, compInterface);
    return effect.applyRoutine();
  }
  
  public boolean removeInterface(final Interface classInterface) {
    mir.routines.class2comp.RemoveInterfaceRoutine effect = new mir.routines.class2comp.RemoveInterfaceRoutine(this.executionState, calledBy, classInterface);
    return effect.applyRoutine();
  }
  
  public boolean removeInterfaceRealization(final InterfaceRealization classIFRealization) {
    mir.routines.class2comp.RemoveInterfaceRealizationRoutine effect = new mir.routines.class2comp.RemoveInterfaceRealizationRoutine(this.executionState, calledBy, classIFRealization);
    return effect.applyRoutine();
  }
  
  public boolean removeUsage(final InterfaceRealization classIFRealization) {
    mir.routines.class2comp.RemoveUsageRoutine effect = new mir.routines.class2comp.RemoveUsageRoutine(this.executionState, calledBy, classIFRealization);
    return effect.applyRoutine();
  }
}
