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
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public boolean createModelSelfCorrespondence(final Model compModel) {
    mir.routines.comp2class.CreateModelSelfCorrespondenceRoutine effect = new mir.routines.comp2class.CreateModelSelfCorrespondenceRoutine(this.executionState, calledBy, compModel);
    return effect.applyRoutine();
  }
  
  public boolean createClassModel(final Model compModel) {
    mir.routines.comp2class.CreateClassModelRoutine effect = new mir.routines.comp2class.CreateClassModelRoutine(this.executionState, calledBy, compModel);
    return effect.applyRoutine();
  }
  
  public boolean createDataTypePackage(final Model compModel) {
    mir.routines.comp2class.CreateDataTypePackageRoutine effect = new mir.routines.comp2class.CreateDataTypePackageRoutine(this.executionState, calledBy, compModel);
    return effect.applyRoutine();
  }
  
  public boolean renameClassModelForComponentModel(final Model compModel) {
    mir.routines.comp2class.RenameClassModelForComponentModelRoutine effect = new mir.routines.comp2class.RenameClassModelForComponentModelRoutine(this.executionState, calledBy, compModel);
    return effect.applyRoutine();
  }
  
  public boolean renameElement(final NamedElement compElement) {
    mir.routines.comp2class.RenameElementRoutine effect = new mir.routines.comp2class.RenameElementRoutine(this.executionState, calledBy, compElement);
    return effect.applyRoutine();
  }
  
  public boolean changeCorrespondingVisibility(final NamedElement compElement) {
    mir.routines.comp2class.ChangeCorrespondingVisibilityRoutine effect = new mir.routines.comp2class.ChangeCorrespondingVisibilityRoutine(this.executionState, calledBy, compElement);
    return effect.applyRoutine();
  }
  
  public boolean createClassWithPackage(final Component umlComp) {
    mir.routines.comp2class.CreateClassWithPackageRoutine effect = new mir.routines.comp2class.CreateClassWithPackageRoutine(this.executionState, calledBy, umlComp);
    return effect.applyRoutine();
  }
  
  public boolean renameClassAndPackage(final Component umlComp, final String newName) {
    mir.routines.comp2class.RenameClassAndPackageRoutine effect = new mir.routines.comp2class.RenameClassAndPackageRoutine(this.executionState, calledBy, umlComp, newName);
    return effect.applyRoutine();
  }
  
  public boolean deleteClass(final Component umlComp) {
    mir.routines.comp2class.DeleteClassRoutine effect = new mir.routines.comp2class.DeleteClassRoutine(this.executionState, calledBy, umlComp);
    return effect.applyRoutine();
  }
  
  public boolean createDataTypeForDataType(final DataType compType) {
    mir.routines.comp2class.CreateDataTypeForDataTypeRoutine effect = new mir.routines.comp2class.CreateDataTypeForDataTypeRoutine(this.executionState, calledBy, compType);
    return effect.applyRoutine();
  }
  
  public boolean createDataTypeSelfCorrespondence(final DataType compType) {
    mir.routines.comp2class.CreateDataTypeSelfCorrespondenceRoutine effect = new mir.routines.comp2class.CreateDataTypeSelfCorrespondenceRoutine(this.executionState, calledBy, compType);
    return effect.applyRoutine();
  }
  
  public boolean createClassForDataType(final DataType compType) {
    mir.routines.comp2class.CreateClassForDataTypeRoutine effect = new mir.routines.comp2class.CreateClassForDataTypeRoutine(this.executionState, calledBy, compType);
    return effect.applyRoutine();
  }
  
  public boolean addClassDataTypeProperty(final Property compProperty, final DataType compDataType) {
    mir.routines.comp2class.AddClassDataTypePropertyRoutine effect = new mir.routines.comp2class.AddClassDataTypePropertyRoutine(this.executionState, calledBy, compProperty, compDataType);
    return effect.applyRoutine();
  }
  
  public boolean changeClassDataTypeProperty(final Property compProperty) {
    mir.routines.comp2class.ChangeClassDataTypePropertyRoutine effect = new mir.routines.comp2class.ChangeClassDataTypePropertyRoutine(this.executionState, calledBy, compProperty);
    return effect.applyRoutine();
  }
  
  public boolean addDataTypeOperation(final Operation compOperation, final DataType compDataType) {
    mir.routines.comp2class.AddDataTypeOperationRoutine effect = new mir.routines.comp2class.AddDataTypeOperationRoutine(this.executionState, calledBy, compOperation, compDataType);
    return effect.applyRoutine();
  }
  
  public boolean changeClassDataTypeOperation(final Operation compOperation) {
    mir.routines.comp2class.ChangeClassDataTypeOperationRoutine effect = new mir.routines.comp2class.ChangeClassDataTypeOperationRoutine(this.executionState, calledBy, compOperation);
    return effect.applyRoutine();
  }
  
  public boolean createClassInterface(final Interface compInterface, final Component umlComp) {
    mir.routines.comp2class.CreateClassInterfaceRoutine effect = new mir.routines.comp2class.CreateClassInterfaceRoutine(this.executionState, calledBy, compInterface, umlComp);
    return effect.applyRoutine();
  }
  
  public boolean createClassInterfaceRealization(final NamedElement iFRealizationOrUsage, final Component umlComp) {
    mir.routines.comp2class.CreateClassInterfaceRealizationRoutine effect = new mir.routines.comp2class.CreateClassInterfaceRealizationRoutine(this.executionState, calledBy, iFRealizationOrUsage, umlComp);
    return effect.applyRoutine();
  }
  
  public boolean addClassInterfaceRealizationToClass(final NamedElement iFRealizationOrUsage, final Interface compInterface, final Component umlComp) {
    mir.routines.comp2class.AddClassInterfaceRealizationToClassRoutine effect = new mir.routines.comp2class.AddClassInterfaceRealizationToClassRoutine(this.executionState, calledBy, iFRealizationOrUsage, compInterface, umlComp);
    return effect.applyRoutine();
  }
  
  public boolean removeInterfaceRealizationForInterfaceRealization(final InterfaceRealization compIFRealization) {
    mir.routines.comp2class.RemoveInterfaceRealizationForInterfaceRealizationRoutine effect = new mir.routines.comp2class.RemoveInterfaceRealizationForInterfaceRealizationRoutine(this.executionState, calledBy, compIFRealization);
    return effect.applyRoutine();
  }
  
  public boolean removeInterfaceRealizationForUsage(final Usage compUsage) {
    mir.routines.comp2class.RemoveInterfaceRealizationForUsageRoutine effect = new mir.routines.comp2class.RemoveInterfaceRealizationForUsageRoutine(this.executionState, calledBy, compUsage);
    return effect.applyRoutine();
  }
}
