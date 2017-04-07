package mir.routines.class2comp;

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
  
  public void routineCreatedUmlClass(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.RoutineCreatedUmlClassRoutine effect = new mir.routines.class2comp.RoutineCreatedUmlClassRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void createUmlComponent(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.CreateUmlComponentRoutine effect = new mir.routines.class2comp.CreateUmlComponentRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void createUmlComponentAndPackage(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.class2comp.CreateUmlComponentAndPackageRoutine effect = new mir.routines.class2comp.CreateUmlComponentAndPackageRoutine(this.executionState, calledBy,
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
  
  public void deleteComponent(final org.eclipse.uml2.uml.Class umlClass, final String packageName, final String expectedTag) {
    mir.routines.class2comp.DeleteComponentRoutine effect = new mir.routines.class2comp.DeleteComponentRoutine(this.executionState, calledBy,
    	umlClass, packageName, expectedTag);
    effect.applyRoutine();
  }
  
  public void renameElement(final NamedElement classElement) {
    mir.routines.class2comp.RenameElementRoutine effect = new mir.routines.class2comp.RenameElementRoutine(this.executionState, calledBy,
    	classElement);
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
  
  public void createCompAttribute(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute) {
    mir.routines.class2comp.CreateCompAttributeRoutine effect = new mir.routines.class2comp.CreateCompAttributeRoutine(this.executionState, calledBy,
    	umlClass, classAttribute);
    effect.applyRoutine();
  }
  
  public void renameComponentAttribute(final Property classAttribute) {
    mir.routines.class2comp.RenameComponentAttributeRoutine effect = new mir.routines.class2comp.RenameComponentAttributeRoutine(this.executionState, calledBy,
    	classAttribute);
    effect.applyRoutine();
  }
  
  public void deleteComponentAttribute(final Property classAttribute) {
    mir.routines.class2comp.DeleteComponentAttributeRoutine effect = new mir.routines.class2comp.DeleteComponentAttributeRoutine(this.executionState, calledBy,
    	classAttribute);
    effect.applyRoutine();
  }
  
  public void changePropertyType(final Property classProperty, final DataType classType) {
    mir.routines.class2comp.ChangePropertyTypeRoutine effect = new mir.routines.class2comp.ChangePropertyTypeRoutine(this.executionState, calledBy,
    	classProperty, classType);
    effect.applyRoutine();
  }
  
  public void movedClassToDifferentPackage(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage) {
    mir.routines.class2comp.MovedClassToDifferentPackageRoutine effect = new mir.routines.class2comp.MovedClassToDifferentPackageRoutine(this.executionState, calledBy,
    	umlClass, oldPackage, newPackage);
    effect.applyRoutine();
  }
  
  public void createInterface(final Interface classInterface) {
    mir.routines.class2comp.CreateInterfaceRoutine effect = new mir.routines.class2comp.CreateInterfaceRoutine(this.executionState, calledBy,
    	classInterface);
    effect.applyRoutine();
  }
  
  public void createInterfaceRealization(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface) {
    mir.routines.class2comp.CreateInterfaceRealizationRoutine effect = new mir.routines.class2comp.CreateInterfaceRealizationRoutine(this.executionState, calledBy,
    	umlClass, classInterface);
    effect.applyRoutine();
  }
  
  public void deleteInterfaceRealization(final InterfaceRealization classInterface) {
    mir.routines.class2comp.DeleteInterfaceRealizationRoutine effect = new mir.routines.class2comp.DeleteInterfaceRealizationRoutine(this.executionState, calledBy,
    	classInterface);
    effect.applyRoutine();
  }
  
  public void addRequiredRole(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface) {
    mir.routines.class2comp.AddRequiredRoleRoutine effect = new mir.routines.class2comp.AddRequiredRoleRoutine(this.executionState, calledBy,
    	umlClass, classInterface);
    effect.applyRoutine();
  }
}
