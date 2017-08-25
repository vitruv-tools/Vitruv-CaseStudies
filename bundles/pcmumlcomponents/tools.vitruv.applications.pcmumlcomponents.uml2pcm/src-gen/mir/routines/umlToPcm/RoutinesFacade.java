package mir.routines.umlToPcm;

import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
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
  
  public void renameElement(final NamedElement umlElement) {
    mir.routines.umlToPcm.RenameElementRoutine effect = new mir.routines.umlToPcm.RenameElementRoutine(this.executionState, calledBy, umlElement);
    effect.applyRoutine();
  }
  
  public void deleteElement(final Element umlElement) {
    mir.routines.umlToPcm.DeleteElementRoutine effect = new mir.routines.umlToPcm.DeleteElementRoutine(this.executionState, calledBy, umlElement);
    effect.applyRoutine();
  }
  
  public void updateMultiplicityType(final MultiplicityElement umlElement) {
    mir.routines.umlToPcm.UpdateMultiplicityTypeRoutine effect = new mir.routines.umlToPcm.UpdateMultiplicityTypeRoutine(this.executionState, calledBy, umlElement);
    effect.applyRoutine();
  }
  
  public void createPcmRepository(final Model umlModel) {
    mir.routines.umlToPcm.CreatePcmRepositoryRoutine effect = new mir.routines.umlToPcm.CreatePcmRepositoryRoutine(this.executionState, calledBy, umlModel);
    effect.applyRoutine();
  }
  
  public void createPrimitiveDataType(final PrimitiveType umlType) {
    mir.routines.umlToPcm.CreatePrimitiveDataTypeRoutine effect = new mir.routines.umlToPcm.CreatePrimitiveDataTypeRoutine(this.executionState, calledBy, umlType);
    effect.applyRoutine();
  }
  
  public void createDataType(final DataType umlType) {
    mir.routines.umlToPcm.CreateDataTypeRoutine effect = new mir.routines.umlToPcm.CreateDataTypeRoutine(this.executionState, calledBy, umlType);
    effect.applyRoutine();
  }
  
  public void createCompositeDataType(final DataType umlType) {
    mir.routines.umlToPcm.CreateCompositeDataTypeRoutine effect = new mir.routines.umlToPcm.CreateCompositeDataTypeRoutine(this.executionState, calledBy, umlType);
    effect.applyRoutine();
  }
  
  public void createCollectionDataType(final DataType umlType) {
    mir.routines.umlToPcm.CreateCollectionDataTypeRoutine effect = new mir.routines.umlToPcm.CreateCollectionDataTypeRoutine(this.executionState, calledBy, umlType);
    effect.applyRoutine();
  }
  
  public void createInnerDeclarationOffProperty(final Property property) {
    mir.routines.umlToPcm.CreateInnerDeclarationOffPropertyRoutine effect = new mir.routines.umlToPcm.CreateInnerDeclarationOffPropertyRoutine(this.executionState, calledBy, property);
    effect.applyRoutine();
  }
  
  public void deleteInnerDeclarationOfProperty(final Property umlProperty) {
    mir.routines.umlToPcm.DeleteInnerDeclarationOfPropertyRoutine effect = new mir.routines.umlToPcm.DeleteInnerDeclarationOfPropertyRoutine(this.executionState, calledBy, umlProperty);
    effect.applyRoutine();
  }
  
  public void changePropertyType(final Property umlProperty, final DataType umlType) {
    mir.routines.umlToPcm.ChangePropertyTypeRoutine effect = new mir.routines.umlToPcm.ChangePropertyTypeRoutine(this.executionState, calledBy, umlProperty, umlType);
    effect.applyRoutine();
  }
  
  public void deleteDataType(final DataType umlDataType) {
    mir.routines.umlToPcm.DeleteDataTypeRoutine effect = new mir.routines.umlToPcm.DeleteDataTypeRoutine(this.executionState, calledBy, umlDataType);
    effect.applyRoutine();
  }
  
  public void createInterface(final Interface umlInterface) {
    mir.routines.umlToPcm.CreateInterfaceRoutine effect = new mir.routines.umlToPcm.CreateInterfaceRoutine(this.executionState, calledBy, umlInterface);
    effect.applyRoutine();
  }
  
  public void createInterfaceOperation(final Operation umlOperation) {
    mir.routines.umlToPcm.CreateInterfaceOperationRoutine effect = new mir.routines.umlToPcm.CreateInterfaceOperationRoutine(this.executionState, calledBy, umlOperation);
    effect.applyRoutine();
  }
  
  public void addOperationParameter(final Operation umlOperation, final Parameter umlParameter) {
    mir.routines.umlToPcm.AddOperationParameterRoutine effect = new mir.routines.umlToPcm.AddOperationParameterRoutine(this.executionState, calledBy, umlOperation, umlParameter);
    effect.applyRoutine();
  }
  
  public void unsetInterfaceOperationType(final Operation umlOperation) {
    mir.routines.umlToPcm.UnsetInterfaceOperationTypeRoutine effect = new mir.routines.umlToPcm.UnsetInterfaceOperationTypeRoutine(this.executionState, calledBy, umlOperation);
    effect.applyRoutine();
  }
  
  public void addInterfaceOperationParameter(final Operation umlOperation, final Parameter umlParameter) {
    mir.routines.umlToPcm.AddInterfaceOperationParameterRoutine effect = new mir.routines.umlToPcm.AddInterfaceOperationParameterRoutine(this.executionState, calledBy, umlOperation, umlParameter);
    effect.applyRoutine();
  }
  
  public void changeInterfaceOperationType(final Operation umlOperation, final Parameter umlParameter) {
    mir.routines.umlToPcm.ChangeInterfaceOperationTypeRoutine effect = new mir.routines.umlToPcm.ChangeInterfaceOperationTypeRoutine(this.executionState, calledBy, umlOperation, umlParameter);
    effect.applyRoutine();
  }
  
  public void changeParameterType(final Parameter umlParameter) {
    mir.routines.umlToPcm.ChangeParameterTypeRoutine effect = new mir.routines.umlToPcm.ChangeParameterTypeRoutine(this.executionState, calledBy, umlParameter);
    effect.applyRoutine();
  }
  
  public void unsetParameterType(final Parameter umlParameter) {
    mir.routines.umlToPcm.UnsetParameterTypeRoutine effect = new mir.routines.umlToPcm.UnsetParameterTypeRoutine(this.executionState, calledBy, umlParameter);
    effect.applyRoutine();
  }
  
  public void changeParameterName(final Parameter umlParameter) {
    mir.routines.umlToPcm.ChangeParameterNameRoutine effect = new mir.routines.umlToPcm.ChangeParameterNameRoutine(this.executionState, calledBy, umlParameter);
    effect.applyRoutine();
  }
  
  public void changeParameterDirection(final Parameter umlParameter) {
    mir.routines.umlToPcm.ChangeParameterDirectionRoutine effect = new mir.routines.umlToPcm.ChangeParameterDirectionRoutine(this.executionState, calledBy, umlParameter);
    effect.applyRoutine();
  }
  
  public void deleteParameter(final Parameter umlParameter) {
    mir.routines.umlToPcm.DeleteParameterRoutine effect = new mir.routines.umlToPcm.DeleteParameterRoutine(this.executionState, calledBy, umlParameter);
    effect.applyRoutine();
  }
  
  public void createPcmComponent(final Component umlComponent) {
    mir.routines.umlToPcm.CreatePcmComponentRoutine effect = new mir.routines.umlToPcm.CreatePcmComponentRoutine(this.executionState, calledBy, umlComponent);
    effect.applyRoutine();
  }
  
  public void createCompositeComponent(final Component umlComponent) {
    mir.routines.umlToPcm.CreateCompositeComponentRoutine effect = new mir.routines.umlToPcm.CreateCompositeComponentRoutine(this.executionState, calledBy, umlComponent);
    effect.applyRoutine();
  }
  
  public void createBasicComponent(final Component umlComponent) {
    mir.routines.umlToPcm.CreateBasicComponentRoutine effect = new mir.routines.umlToPcm.CreateBasicComponentRoutine(this.executionState, calledBy, umlComponent);
    effect.applyRoutine();
  }
  
  public void createRequiredRole(final Component umlComponent, final Usage umlUsage) {
    mir.routines.umlToPcm.CreateRequiredRoleRoutine effect = new mir.routines.umlToPcm.CreateRequiredRoleRoutine(this.executionState, calledBy, umlComponent, umlUsage);
    effect.applyRoutine();
  }
  
  public void changeRequiredInterface(final Usage umlUsage, final Interface umlInterface) {
    mir.routines.umlToPcm.ChangeRequiredInterfaceRoutine effect = new mir.routines.umlToPcm.ChangeRequiredInterfaceRoutine(this.executionState, calledBy, umlUsage, umlInterface);
    effect.applyRoutine();
  }
  
  public void createProvidedRole(final Component umlComponent, final InterfaceRealization interfaceRealization) {
    mir.routines.umlToPcm.CreateProvidedRoleRoutine effect = new mir.routines.umlToPcm.CreateProvidedRoleRoutine(this.executionState, calledBy, umlComponent, interfaceRealization);
    effect.applyRoutine();
  }
  
  public void changeProvidedInterface(final InterfaceRealization interfaceRealization, final Interface umlInterface) {
    mir.routines.umlToPcm.ChangeProvidedInterfaceRoutine effect = new mir.routines.umlToPcm.ChangeProvidedInterfaceRoutine(this.executionState, calledBy, interfaceRealization, umlInterface);
    effect.applyRoutine();
  }
}
