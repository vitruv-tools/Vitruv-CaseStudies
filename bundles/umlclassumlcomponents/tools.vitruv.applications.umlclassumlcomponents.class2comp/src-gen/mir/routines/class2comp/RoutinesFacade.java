package mir.routines.class2comp;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
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
  
  public void createModelSelfCorrespondence(final Model umlCompModel) {
    mir.routines.class2comp.CreateModelSelfCorrespondenceRoutine effect = new mir.routines.class2comp.CreateModelSelfCorrespondenceRoutine(this.executionState, calledBy,
    	umlCompModel);
    effect.applyRoutine();
  }
  
  public void createComponentModel(final Model umlClassModel) {
    mir.routines.class2comp.CreateComponentModelRoutine effect = new mir.routines.class2comp.CreateComponentModelRoutine(this.executionState, calledBy,
    	umlClassModel);
    effect.applyRoutine();
  }
  
  public void renameComponentModelForClassModel(final Model umlClassModel) {
    mir.routines.class2comp.RenameComponentModelForClassModelRoutine effect = new mir.routines.class2comp.RenameComponentModelForClassModelRoutine(this.executionState, calledBy,
    	umlClassModel);
    effect.applyRoutine();
  }
  
  public void renameElement(final NamedElement classElement) {
    mir.routines.class2comp.RenameElementRoutine effect = new mir.routines.class2comp.RenameElementRoutine(this.executionState, calledBy,
    	classElement);
    effect.applyRoutine();
  }
  
  public void routineCreatedUmlClass(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage) {
    mir.routines.class2comp.RoutineCreatedUmlClassRoutine effect = new mir.routines.class2comp.RoutineCreatedUmlClassRoutine(this.executionState, calledBy,
    	umlClass, classPackage);
    effect.applyRoutine();
  }
  
  public void createUmlComponentForClass(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.CreateUmlComponentForClassRoutine effect = new mir.routines.class2comp.CreateUmlComponentForClassRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void createDataTypeForClass(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.CreateDataTypeForClassRoutine effect = new mir.routines.class2comp.CreateDataTypeForClassRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void renameComponent(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.RenameComponentRoutine effect = new mir.routines.class2comp.RenameComponentRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void deleteComponent(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.DeleteComponentRoutine effect = new mir.routines.class2comp.DeleteComponentRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void createPrimitiveDataType(final PrimitiveType classType) {
    mir.routines.class2comp.CreatePrimitiveDataTypeRoutine effect = new mir.routines.class2comp.CreatePrimitiveDataTypeRoutine(this.executionState, calledBy,
    	classType);
    effect.applyRoutine();
  }
  
  public void createDataType(final DataType classType) {
    mir.routines.class2comp.CreateDataTypeRoutine effect = new mir.routines.class2comp.CreateDataTypeRoutine(this.executionState, calledBy,
    	classType);
    effect.applyRoutine();
  }
  
  public void createCompDataTypeAttribute(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute) {
    mir.routines.class2comp.CreateCompDataTypeAttributeRoutine effect = new mir.routines.class2comp.CreateCompDataTypeAttributeRoutine(this.executionState, calledBy,
    	umlClass, classAttribute);
    effect.applyRoutine();
  }
  
  public void renameCompDataTypeAttribute(final Property classAttribute) {
    mir.routines.class2comp.RenameCompDataTypeAttributeRoutine effect = new mir.routines.class2comp.RenameCompDataTypeAttributeRoutine(this.executionState, calledBy,
    	classAttribute);
    effect.applyRoutine();
  }
  
  public void deleteCompDataTypeAttribute(final Property classAttribute) {
    mir.routines.class2comp.DeleteCompDataTypeAttributeRoutine effect = new mir.routines.class2comp.DeleteCompDataTypeAttributeRoutine(this.executionState, calledBy,
    	classAttribute);
    effect.applyRoutine();
  }
  
  public void changeAttributeType(final Property classProperty, final DataType classType) {
    mir.routines.class2comp.ChangeAttributeTypeRoutine effect = new mir.routines.class2comp.ChangeAttributeTypeRoutine(this.executionState, calledBy,
    	classProperty, classType);
    effect.applyRoutine();
  }
  
  public void createdPackage(final org.eclipse.uml2.uml.Package classPackage) {
    mir.routines.class2comp.CreatedPackageRoutine effect = new mir.routines.class2comp.CreatedPackageRoutine(this.executionState, calledBy,
    	classPackage);
    effect.applyRoutine();
  }
  
  public void assignNewPackage(final org.eclipse.uml2.uml.Package newPackage, final Component umlComponent) {
    mir.routines.class2comp.AssignNewPackageRoutine effect = new mir.routines.class2comp.AssignNewPackageRoutine(this.executionState, calledBy,
    	newPackage, umlComponent);
    effect.applyRoutine();
  }
  
  public void movedClassToDifferentPackage(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage) {
    mir.routines.class2comp.MovedClassToDifferentPackageRoutine effect = new mir.routines.class2comp.MovedClassToDifferentPackageRoutine(this.executionState, calledBy,
    	umlClass, oldPackage, newPackage);
    effect.applyRoutine();
  }
  
  public void removeCorrespondence(final Classifier classObject, final Classifier compObject) {
    mir.routines.class2comp.RemoveCorrespondenceRoutine effect = new mir.routines.class2comp.RemoveCorrespondenceRoutine(this.executionState, calledBy,
    	classObject, compObject);
    effect.applyRoutine();
  }
  
  public void routinePackageRenamed(final org.eclipse.uml2.uml.Package classPackage, final String newName, final String oldName) {
    mir.routines.class2comp.RoutinePackageRenamedRoutine effect = new mir.routines.class2comp.RoutinePackageRenamedRoutine(this.executionState, calledBy,
    	classPackage, newName, oldName);
    effect.applyRoutine();
  }
  
  public void routinePackageDeleted(final org.eclipse.uml2.uml.Package classPackage, final Model classModel) {
    mir.routines.class2comp.RoutinePackageDeletedRoutine effect = new mir.routines.class2comp.RoutinePackageDeletedRoutine(this.executionState, calledBy,
    	classPackage, classModel);
    effect.applyRoutine();
  }
  
  public void changeCorrespondingVisibility(final NamedElement classElement) {
    mir.routines.class2comp.ChangeCorrespondingVisibilityRoutine effect = new mir.routines.class2comp.ChangeCorrespondingVisibilityRoutine(this.executionState, calledBy,
    	classElement);
    effect.applyRoutine();
  }
  
  public void createRequiredAndProvidedRole(final Interface classInterface, final InterfaceRealization classIFRealizationReq, final org.eclipse.uml2.uml.Package interfacePackage, final org.eclipse.uml2.uml.Package classPackage) {
    mir.routines.class2comp.CreateRequiredAndProvidedRoleRoutine effect = new mir.routines.class2comp.CreateRequiredAndProvidedRoleRoutine(this.executionState, calledBy,
    	classInterface, classIFRealizationReq, interfacePackage, classPackage);
    effect.applyRoutine();
  }
  
  public void createCompInterface(final Interface classInterface) {
    mir.routines.class2comp.CreateCompInterfaceRoutine effect = new mir.routines.class2comp.CreateCompInterfaceRoutine(this.executionState, calledBy,
    	classInterface);
    effect.applyRoutine();
  }
  
  public void createProvidedRole(final InterfaceRealization classIFRealization, final Interface classInterface, final org.eclipse.uml2.uml.Package affectedPackage) {
    mir.routines.class2comp.CreateProvidedRoleRoutine effect = new mir.routines.class2comp.CreateProvidedRoleRoutine(this.executionState, calledBy,
    	classIFRealization, classInterface, affectedPackage);
    effect.applyRoutine();
  }
  
  public void createInterfaceRealization(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface) {
    mir.routines.class2comp.CreateInterfaceRealizationRoutine effect = new mir.routines.class2comp.CreateInterfaceRealizationRoutine(this.executionState, calledBy,
    	classIFRealization, umlComp, compInterface);
    effect.applyRoutine();
  }
  
  public void createRequiredRole(final InterfaceRealization classIFRealization, final Interface classInterface, final org.eclipse.uml2.uml.Package affectedPackage) {
    mir.routines.class2comp.CreateRequiredRoleRoutine effect = new mir.routines.class2comp.CreateRequiredRoleRoutine(this.executionState, calledBy,
    	classIFRealization, classInterface, affectedPackage);
    effect.applyRoutine();
  }
  
  public void createUsage(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface) {
    mir.routines.class2comp.CreateUsageRoutine effect = new mir.routines.class2comp.CreateUsageRoutine(this.executionState, calledBy,
    	classIFRealization, umlComp, compInterface);
    effect.applyRoutine();
  }
  
  public void removeInterface(final Interface classInterface) {
    mir.routines.class2comp.RemoveInterfaceRoutine effect = new mir.routines.class2comp.RemoveInterfaceRoutine(this.executionState, calledBy,
    	classInterface);
    effect.applyRoutine();
  }
  
  public void removeInterfaceRealization(final InterfaceRealization classIFRealization) {
    mir.routines.class2comp.RemoveInterfaceRealizationRoutine effect = new mir.routines.class2comp.RemoveInterfaceRealizationRoutine(this.executionState, calledBy,
    	classIFRealization);
    effect.applyRoutine();
  }
  
  public void removeUsage(final InterfaceRealization classIFRealization) {
    mir.routines.class2comp.RemoveUsageRoutine effect = new mir.routines.class2comp.RemoveUsageRoutine(this.executionState, calledBy,
    	classIFRealization);
    effect.applyRoutine();
  }
}
