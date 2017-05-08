package mir.routines.comp2class;

import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createModelSelfCorrespondence(final Model compModel) {
    mir.routines.comp2class.CreateModelSelfCorrespondenceRoutine effect = new mir.routines.comp2class.CreateModelSelfCorrespondenceRoutine(this.executionState, calledBy,
    	compModel);
    effect.applyRoutine();
  }
  
  public void createClassModel(final Model compModel) {
    mir.routines.comp2class.CreateClassModelRoutine effect = new mir.routines.comp2class.CreateClassModelRoutine(this.executionState, calledBy,
    	compModel);
    effect.applyRoutine();
  }
  
  public void createDataTypePackage(final Model compModel) {
    mir.routines.comp2class.CreateDataTypePackageRoutine effect = new mir.routines.comp2class.CreateDataTypePackageRoutine(this.executionState, calledBy,
    	compModel);
    effect.applyRoutine();
  }
  
  public void renameClassModelForComponentModel(final Model compModel) {
    mir.routines.comp2class.RenameClassModelForComponentModelRoutine effect = new mir.routines.comp2class.RenameClassModelForComponentModelRoutine(this.executionState, calledBy,
    	compModel);
    effect.applyRoutine();
  }
  
  public void renameElement(final NamedElement compElement) {
    mir.routines.comp2class.RenameElementRoutine effect = new mir.routines.comp2class.RenameElementRoutine(this.executionState, calledBy,
    	compElement);
    effect.applyRoutine();
  }
  
  public void createClassWithPackage(final Component umlComp) {
    mir.routines.comp2class.CreateClassWithPackageRoutine effect = new mir.routines.comp2class.CreateClassWithPackageRoutine(this.executionState, calledBy,
    	umlComp);
    effect.applyRoutine();
  }
  
  public void renameClassAndPackage(final Component umlComp, final String newName) {
    mir.routines.comp2class.RenameClassAndPackageRoutine effect = new mir.routines.comp2class.RenameClassAndPackageRoutine(this.executionState, calledBy,
    	umlComp, newName);
    effect.applyRoutine();
  }
  
  public void deleteClass(final Component umlComp) {
    mir.routines.comp2class.DeleteClassRoutine effect = new mir.routines.comp2class.DeleteClassRoutine(this.executionState, calledBy,
    	umlComp);
    effect.applyRoutine();
  }
  
  public void createDataTypeForDataType(final DataType compType) {
    mir.routines.comp2class.CreateDataTypeForDataTypeRoutine effect = new mir.routines.comp2class.CreateDataTypeForDataTypeRoutine(this.executionState, calledBy,
    	compType);
    effect.applyRoutine();
  }
  
  public void createClassForDataType(final DataType compType) {
    mir.routines.comp2class.CreateClassForDataTypeRoutine effect = new mir.routines.comp2class.CreateClassForDataTypeRoutine(this.executionState, calledBy,
    	compType);
    effect.applyRoutine();
  }
  
  public void addClassDataTypeProperty(final Property compProperty, final DataType compDataType) {
    mir.routines.comp2class.AddClassDataTypePropertyRoutine effect = new mir.routines.comp2class.AddClassDataTypePropertyRoutine(this.executionState, calledBy,
    	compProperty, compDataType);
    effect.applyRoutine();
  }
  
  public void changeClassDataTypeProperty(final Property compProperty) {
    mir.routines.comp2class.ChangeClassDataTypePropertyRoutine effect = new mir.routines.comp2class.ChangeClassDataTypePropertyRoutine(this.executionState, calledBy,
    	compProperty);
    effect.applyRoutine();
  }
  
  public void addDataTypeOperation(final Operation compOperation, final DataType compDataType) {
    mir.routines.comp2class.AddDataTypeOperationRoutine effect = new mir.routines.comp2class.AddDataTypeOperationRoutine(this.executionState, calledBy,
    	compOperation, compDataType);
    effect.applyRoutine();
  }
  
  public void changeClassDataTypeOperation(final Operation compOperation) {
    mir.routines.comp2class.ChangeClassDataTypeOperationRoutine effect = new mir.routines.comp2class.ChangeClassDataTypeOperationRoutine(this.executionState, calledBy,
    	compOperation);
    effect.applyRoutine();
  }
  
  public void changeCorrespondingVisibility(final NamedElement compElement) {
    mir.routines.comp2class.ChangeCorrespondingVisibilityRoutine effect = new mir.routines.comp2class.ChangeCorrespondingVisibilityRoutine(this.executionState, calledBy,
    	compElement);
    effect.applyRoutine();
  }
  
  public void createClassInterface(final Interface compInterface, final Component umlComp) {
    mir.routines.comp2class.CreateClassInterfaceRoutine effect = new mir.routines.comp2class.CreateClassInterfaceRoutine(this.executionState, calledBy,
    	compInterface, umlComp);
    effect.applyRoutine();
  }
  
  public void createClassInterfaceRealization(final NamedElement compIFRealization, final Component umlComp) {
    mir.routines.comp2class.CreateClassInterfaceRealizationRoutine effect = new mir.routines.comp2class.CreateClassInterfaceRealizationRoutine(this.executionState, calledBy,
    	compIFRealization, umlComp);
    effect.applyRoutine();
  }
  
  public void addClassInterfaceRealizationToClass(final InterfaceRealization compIFRealization, final Interface compInterface, final Component umlComp) {
    mir.routines.comp2class.AddClassInterfaceRealizationToClassRoutine effect = new mir.routines.comp2class.AddClassInterfaceRealizationToClassRoutine(this.executionState, calledBy,
    	compIFRealization, compInterface, umlComp);
    effect.applyRoutine();
  }
}
