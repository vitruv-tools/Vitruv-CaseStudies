package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCollectionDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCollectionDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CollectionDataType dataType, final Model umlModel, final DataType umlInnerType, final Property umlProperty, final DataType umlType) {
      return umlModel;
    }
    
    public EObject getCorrepondenceSourceUmlInnerType(final CollectionDataType dataType, final Model umlModel) {
      org.palladiosimulator.pcm.repository.DataType _innerType_CollectionDataType = dataType.getInnerType_CollectionDataType();
      return _innerType_CollectionDataType;
    }
    
    public void updateUmlPropertyElement(final CollectionDataType dataType, final Model umlModel, final DataType umlInnerType, final Property umlProperty) {
      umlProperty.setType(umlInnerType);
      umlProperty.setName("innerType");
    }
    
    public void update0Element(final CollectionDataType dataType, final Model umlModel, final DataType umlInnerType, final Property umlProperty, final DataType umlType) {
      EList<PackageableElement> _packagedElements = umlModel.getPackagedElements();
      _packagedElements.add(umlType);
    }
    
    public EObject getElement2(final CollectionDataType dataType, final Model umlModel, final DataType umlInnerType, final Property umlProperty, final DataType umlType) {
      return dataType;
    }
    
    public EObject getElement3(final CollectionDataType dataType, final Model umlModel, final DataType umlInnerType, final Property umlProperty, final DataType umlType) {
      return umlType;
    }
    
    public EObject getCorrepondenceSourceUmlModel(final CollectionDataType dataType) {
      Repository _repository__DataType = dataType.getRepository__DataType();
      return _repository__DataType;
    }
    
    public void updateUmlTypeElement(final CollectionDataType dataType, final Model umlModel, final DataType umlInnerType, final Property umlProperty, final DataType umlType) {
      String _entityName = dataType.getEntityName();
      umlType.setName(_entityName);
      EList<Property> _ownedAttributes = umlType.getOwnedAttributes();
      _ownedAttributes.add(umlProperty);
    }
  }
  
  public CreateCollectionDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType dataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateCollectionDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.dataType = dataType;
  }
  
  private CollectionDataType dataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCollectionDataTypeRoutine with input:");
    getLogger().debug("   CollectionDataType: " + this.dataType);
    
    Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(dataType), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlModel == null) {
    	return;
    }
    initializeRetrieveElementState(umlModel);
    DataType umlInnerType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInnerType(dataType, umlModel), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	null);
    if (umlInnerType == null) {
    	return;
    }
    initializeRetrieveElementState(umlInnerType);
    Property umlProperty = UMLFactoryImpl.eINSTANCE.createProperty();
    initializeCreateElementState(umlProperty);
    userExecution.updateUmlPropertyElement(dataType, umlModel, umlInnerType, umlProperty);
    
    DataType umlType = UMLFactoryImpl.eINSTANCE.createDataType();
    initializeCreateElementState(umlType);
    userExecution.updateUmlTypeElement(dataType, umlModel, umlInnerType, umlProperty, umlType);
    
    // val updatedElement userExecution.getElement1(dataType, umlModel, umlInnerType, umlProperty, umlType);
    userExecution.update0Element(dataType, umlModel, umlInnerType, umlProperty, umlType);
    
    addCorrespondenceBetween(userExecution.getElement2(dataType, umlModel, umlInnerType, umlProperty, umlType), userExecution.getElement3(dataType, umlModel, umlInnerType, umlProperty, umlType), "");
    
    postprocessElementStates();
  }
}
