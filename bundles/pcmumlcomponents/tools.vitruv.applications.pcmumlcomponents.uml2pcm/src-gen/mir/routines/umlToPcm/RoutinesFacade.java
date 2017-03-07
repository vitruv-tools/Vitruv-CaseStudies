package mir.routines.umlToPcm;

import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void renameElement(final NamedElement umlElement) {
    mir.routines.umlToPcm.RenameElementRoutine effect = new mir.routines.umlToPcm.RenameElementRoutine(this.executionState, calledBy,
    	umlElement);
    effect.applyRoutine();
  }
  
  public void deleteElement(final Element umlElement) {
    mir.routines.umlToPcm.DeleteElementRoutine effect = new mir.routines.umlToPcm.DeleteElementRoutine(this.executionState, calledBy,
    	umlElement);
    effect.applyRoutine();
  }
  
  public void createPcmRepository(final Model umlModel) {
    mir.routines.umlToPcm.CreatePcmRepositoryRoutine effect = new mir.routines.umlToPcm.CreatePcmRepositoryRoutine(this.executionState, calledBy,
    	umlModel);
    effect.applyRoutine();
  }
  
  public void createPrimitiveDataType(final PrimitiveType umlType) {
    mir.routines.umlToPcm.CreatePrimitiveDataTypeRoutine effect = new mir.routines.umlToPcm.CreatePrimitiveDataTypeRoutine(this.executionState, calledBy,
    	umlType);
    effect.applyRoutine();
  }
  
  public void createDataType(final DataType umlType) {
    mir.routines.umlToPcm.CreateDataTypeRoutine effect = new mir.routines.umlToPcm.CreateDataTypeRoutine(this.executionState, calledBy,
    	umlType);
    effect.applyRoutine();
  }
  
  public void createInnerDeclarationOffProperty(final Property property) {
    mir.routines.umlToPcm.CreateInnerDeclarationOffPropertyRoutine effect = new mir.routines.umlToPcm.CreateInnerDeclarationOffPropertyRoutine(this.executionState, calledBy,
    	property);
    effect.applyRoutine();
  }
  
  public void changePropertyType(final Property umlProperty, final DataType umlType) {
    mir.routines.umlToPcm.ChangePropertyTypeRoutine effect = new mir.routines.umlToPcm.ChangePropertyTypeRoutine(this.executionState, calledBy,
    	umlProperty, umlType);
    effect.applyRoutine();
  }
  
  public void createInterface(final Interface umlInterface) {
    mir.routines.umlToPcm.CreateInterfaceRoutine effect = new mir.routines.umlToPcm.CreateInterfaceRoutine(this.executionState, calledBy,
    	umlInterface);
    effect.applyRoutine();
  }
  
  public void createInterfaceOperation(final Operation umlOperation) {
    mir.routines.umlToPcm.CreateInterfaceOperationRoutine effect = new mir.routines.umlToPcm.CreateInterfaceOperationRoutine(this.executionState, calledBy,
    	umlOperation);
    effect.applyRoutine();
  }
  
  public void changeInterfaceOperationType(final Operation umlOperation, final Type umlType) {
    mir.routines.umlToPcm.ChangeInterfaceOperationTypeRoutine effect = new mir.routines.umlToPcm.ChangeInterfaceOperationTypeRoutine(this.executionState, calledBy,
    	umlOperation, umlType);
    effect.applyRoutine();
  }
}
