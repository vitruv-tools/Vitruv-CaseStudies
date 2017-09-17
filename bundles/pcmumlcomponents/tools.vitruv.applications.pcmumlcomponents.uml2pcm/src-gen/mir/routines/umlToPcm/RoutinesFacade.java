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
  
  public boolean renameElement(final NamedElement umlElement) {
    mir.routines.umlToPcm.RenameElementRoutine effect = new mir.routines.umlToPcm.RenameElementRoutine(this.executionState, calledBy, umlElement);
    return effect.applyRoutine();
  }
  
  public boolean renameNamedElement(final NamedElement umlElement) {
    mir.routines.umlToPcm.RenameNamedElementRoutine effect = new mir.routines.umlToPcm.RenameNamedElementRoutine(this.executionState, calledBy, umlElement);
    return effect.applyRoutine();
  }
  
  public boolean renameCollectionType(final NamedElement umlElement) {
    mir.routines.umlToPcm.RenameCollectionTypeRoutine effect = new mir.routines.umlToPcm.RenameCollectionTypeRoutine(this.executionState, calledBy, umlElement);
    return effect.applyRoutine();
  }
  
  public boolean deleteElement(final Element umlElement) {
    mir.routines.umlToPcm.DeleteElementRoutine effect = new mir.routines.umlToPcm.DeleteElementRoutine(this.executionState, calledBy, umlElement);
    return effect.applyRoutine();
  }
  
  public boolean updateMultiplicityType(final MultiplicityElement umlElement) {
    mir.routines.umlToPcm.UpdateMultiplicityTypeRoutine effect = new mir.routines.umlToPcm.UpdateMultiplicityTypeRoutine(this.executionState, calledBy, umlElement);
    return effect.applyRoutine();
  }
  
  public boolean createPcmRepository(final Model umlModel) {
    mir.routines.umlToPcm.CreatePcmRepositoryRoutine effect = new mir.routines.umlToPcm.CreatePcmRepositoryRoutine(this.executionState, calledBy, umlModel);
    return effect.applyRoutine();
  }
  
  public boolean createPrimitiveDataType(final PrimitiveType umlType) {
    mir.routines.umlToPcm.CreatePrimitiveDataTypeRoutine effect = new mir.routines.umlToPcm.CreatePrimitiveDataTypeRoutine(this.executionState, calledBy, umlType);
    return effect.applyRoutine();
  }
  
  public boolean createDataType(final DataType umlType) {
    mir.routines.umlToPcm.CreateDataTypeRoutine effect = new mir.routines.umlToPcm.CreateDataTypeRoutine(this.executionState, calledBy, umlType);
    return effect.applyRoutine();
  }
  
  public boolean createCompositeDataType(final DataType umlType) {
    mir.routines.umlToPcm.CreateCompositeDataTypeRoutine effect = new mir.routines.umlToPcm.CreateCompositeDataTypeRoutine(this.executionState, calledBy, umlType);
    return effect.applyRoutine();
  }
  
  public boolean createCollectionDataType(final DataType umlType) {
    mir.routines.umlToPcm.CreateCollectionDataTypeRoutine effect = new mir.routines.umlToPcm.CreateCollectionDataTypeRoutine(this.executionState, calledBy, umlType);
    return effect.applyRoutine();
  }
  
  public boolean createInnerDeclarationOffProperty(final Property property) {
    mir.routines.umlToPcm.CreateInnerDeclarationOffPropertyRoutine effect = new mir.routines.umlToPcm.CreateInnerDeclarationOffPropertyRoutine(this.executionState, calledBy, property);
    return effect.applyRoutine();
  }
  
  public boolean deleteInnerDeclarationOfProperty(final Property umlProperty) {
    mir.routines.umlToPcm.DeleteInnerDeclarationOfPropertyRoutine effect = new mir.routines.umlToPcm.DeleteInnerDeclarationOfPropertyRoutine(this.executionState, calledBy, umlProperty);
    return effect.applyRoutine();
  }
  
  public boolean changePropertyType(final Property umlProperty, final DataType umlType) {
    mir.routines.umlToPcm.ChangePropertyTypeRoutine effect = new mir.routines.umlToPcm.ChangePropertyTypeRoutine(this.executionState, calledBy, umlProperty, umlType);
    return effect.applyRoutine();
  }
  
  public boolean deleteDataType(final DataType umlDataType) {
    mir.routines.umlToPcm.DeleteDataTypeRoutine effect = new mir.routines.umlToPcm.DeleteDataTypeRoutine(this.executionState, calledBy, umlDataType);
    return effect.applyRoutine();
  }
  
  public boolean createInterface(final Interface umlInterface) {
    mir.routines.umlToPcm.CreateInterfaceRoutine effect = new mir.routines.umlToPcm.CreateInterfaceRoutine(this.executionState, calledBy, umlInterface);
    return effect.applyRoutine();
  }
  
  public boolean createInterfaceOperation(final Operation umlOperation) {
    mir.routines.umlToPcm.CreateInterfaceOperationRoutine effect = new mir.routines.umlToPcm.CreateInterfaceOperationRoutine(this.executionState, calledBy, umlOperation);
    return effect.applyRoutine();
  }
  
  public boolean addOperationParameter(final Operation umlOperation, final Parameter umlParameter) {
    mir.routines.umlToPcm.AddOperationParameterRoutine effect = new mir.routines.umlToPcm.AddOperationParameterRoutine(this.executionState, calledBy, umlOperation, umlParameter);
    return effect.applyRoutine();
  }
  
  public boolean unsetInterfaceOperationType(final Operation umlOperation) {
    mir.routines.umlToPcm.UnsetInterfaceOperationTypeRoutine effect = new mir.routines.umlToPcm.UnsetInterfaceOperationTypeRoutine(this.executionState, calledBy, umlOperation);
    return effect.applyRoutine();
  }
  
  public boolean addInterfaceOperationParameter(final Operation umlOperation, final Parameter umlParameter) {
    mir.routines.umlToPcm.AddInterfaceOperationParameterRoutine effect = new mir.routines.umlToPcm.AddInterfaceOperationParameterRoutine(this.executionState, calledBy, umlOperation, umlParameter);
    return effect.applyRoutine();
  }
  
  public boolean changeInterfaceOperationType(final Operation umlOperation, final Parameter umlParameter) {
    mir.routines.umlToPcm.ChangeInterfaceOperationTypeRoutine effect = new mir.routines.umlToPcm.ChangeInterfaceOperationTypeRoutine(this.executionState, calledBy, umlOperation, umlParameter);
    return effect.applyRoutine();
  }
  
  public boolean changeParameterType(final Parameter umlParameter) {
    mir.routines.umlToPcm.ChangeParameterTypeRoutine effect = new mir.routines.umlToPcm.ChangeParameterTypeRoutine(this.executionState, calledBy, umlParameter);
    return effect.applyRoutine();
  }
  
  public boolean unsetParameterType(final Parameter umlParameter) {
    mir.routines.umlToPcm.UnsetParameterTypeRoutine effect = new mir.routines.umlToPcm.UnsetParameterTypeRoutine(this.executionState, calledBy, umlParameter);
    return effect.applyRoutine();
  }
  
  public boolean changeParameterName(final Parameter umlParameter) {
    mir.routines.umlToPcm.ChangeParameterNameRoutine effect = new mir.routines.umlToPcm.ChangeParameterNameRoutine(this.executionState, calledBy, umlParameter);
    return effect.applyRoutine();
  }
  
  public boolean changeParameterDirection(final Parameter umlParameter) {
    mir.routines.umlToPcm.ChangeParameterDirectionRoutine effect = new mir.routines.umlToPcm.ChangeParameterDirectionRoutine(this.executionState, calledBy, umlParameter);
    return effect.applyRoutine();
  }
  
  public boolean deleteParameter(final Parameter umlParameter) {
    mir.routines.umlToPcm.DeleteParameterRoutine effect = new mir.routines.umlToPcm.DeleteParameterRoutine(this.executionState, calledBy, umlParameter);
    return effect.applyRoutine();
  }
  
  public boolean createPcmComponent(final Component umlComponent) {
    mir.routines.umlToPcm.CreatePcmComponentRoutine effect = new mir.routines.umlToPcm.CreatePcmComponentRoutine(this.executionState, calledBy, umlComponent);
    return effect.applyRoutine();
  }
  
  public boolean createCompositeComponent(final Component umlComponent) {
    mir.routines.umlToPcm.CreateCompositeComponentRoutine effect = new mir.routines.umlToPcm.CreateCompositeComponentRoutine(this.executionState, calledBy, umlComponent);
    return effect.applyRoutine();
  }
  
  public boolean createBasicComponent(final Component umlComponent) {
    mir.routines.umlToPcm.CreateBasicComponentRoutine effect = new mir.routines.umlToPcm.CreateBasicComponentRoutine(this.executionState, calledBy, umlComponent);
    return effect.applyRoutine();
  }
  
  public boolean createRequiredRole(final Component umlComponent, final Usage umlUsage) {
    mir.routines.umlToPcm.CreateRequiredRoleRoutine effect = new mir.routines.umlToPcm.CreateRequiredRoleRoutine(this.executionState, calledBy, umlComponent, umlUsage);
    return effect.applyRoutine();
  }
  
  public boolean changeRequiredInterface(final Usage umlUsage, final Interface umlInterface) {
    mir.routines.umlToPcm.ChangeRequiredInterfaceRoutine effect = new mir.routines.umlToPcm.ChangeRequiredInterfaceRoutine(this.executionState, calledBy, umlUsage, umlInterface);
    return effect.applyRoutine();
  }
  
  public boolean createProvidedRole(final Component umlComponent, final InterfaceRealization interfaceRealization) {
    mir.routines.umlToPcm.CreateProvidedRoleRoutine effect = new mir.routines.umlToPcm.CreateProvidedRoleRoutine(this.executionState, calledBy, umlComponent, interfaceRealization);
    return effect.applyRoutine();
  }
  
  public boolean changeProvidedInterface(final InterfaceRealization interfaceRealization, final Interface umlInterface) {
    mir.routines.umlToPcm.ChangeProvidedInterfaceRoutine effect = new mir.routines.umlToPcm.ChangeProvidedInterfaceRoutine(this.executionState, calledBy, interfaceRealization, umlInterface);
    return effect.applyRoutine();
  }
}
