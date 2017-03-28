package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.applications.pcmumlcomp.pcm2uml.PcmToUmlUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCollectionDataTypeTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCollectionDataTypeTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlType(final CollectionDataType pcmDataType, final DataType pcmInnerType) {
      return pcmDataType;
    }
    
    public EObject getElement1(final CollectionDataType pcmDataType, final DataType pcmInnerType, final org.eclipse.uml2.uml.DataType umlType, final org.eclipse.uml2.uml.DataType umlInnerType, final Property umlProperty) {
      return umlType;
    }
    
    public EObject getCorrepondenceSourceUmlInnerType(final CollectionDataType pcmDataType, final DataType pcmInnerType, final org.eclipse.uml2.uml.DataType umlType) {
      return pcmInnerType;
    }
    
    public void updateUmlPropertyElement(final CollectionDataType pcmDataType, final DataType pcmInnerType, final org.eclipse.uml2.uml.DataType umlType, final org.eclipse.uml2.uml.DataType umlInnerType, final Property umlProperty) {
      umlProperty.setName(PcmToUmlUtil.CollectionTypeAttributeName);
      umlProperty.setType(umlInnerType);
    }
    
    public void update0Element(final CollectionDataType pcmDataType, final DataType pcmInnerType, final org.eclipse.uml2.uml.DataType umlType, final org.eclipse.uml2.uml.DataType umlInnerType, final Property umlProperty) {
      EList<Property> _ownedAttributes = umlType.getOwnedAttributes();
      _ownedAttributes.add(umlProperty);
    }
  }
  
  public CreateCollectionDataTypeTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType pcmDataType, final DataType pcmInnerType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateCollectionDataTypeTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmDataType = pcmDataType;this.pcmInnerType = pcmInnerType;
  }
  
  private CollectionDataType pcmDataType;
  
  private DataType pcmInnerType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCollectionDataTypeTypeRoutine with input:");
    getLogger().debug("   CollectionDataType: " + this.pcmDataType);
    getLogger().debug("   DataType: " + this.pcmInnerType);
    
    org.eclipse.uml2.uml.DataType umlType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlType(pcmDataType, pcmInnerType), // correspondence source supplier
    	org.eclipse.uml2.uml.DataType.class,
    	(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    	null);
    if (umlType == null) {
    	return;
    }
    registerObjectUnderModification(umlType);
    org.eclipse.uml2.uml.DataType umlInnerType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInnerType(pcmDataType, pcmInnerType, umlType), // correspondence source supplier
    	org.eclipse.uml2.uml.DataType.class,
    	(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    	null);
    if (umlInnerType == null) {
    	return;
    }
    registerObjectUnderModification(umlInnerType);
    Property umlProperty = UMLFactoryImpl.eINSTANCE.createProperty();
    userExecution.updateUmlPropertyElement(pcmDataType, pcmInnerType, umlType, umlInnerType, umlProperty);
    
    // val updatedElement userExecution.getElement1(pcmDataType, pcmInnerType, umlType, umlInnerType, umlProperty);
    userExecution.update0Element(pcmDataType, pcmInnerType, umlType, umlInnerType, umlProperty);
    
    postprocessElements();
  }
}
