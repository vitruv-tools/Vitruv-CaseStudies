package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmumlcomp.pcm2uml.PcmToUmlUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInnerDeclarationTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeInnerDeclarationTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlType(final InnerDeclaration innerDeclaration, final DataType pcmDataType, final Property umlProperty) {
      return pcmDataType;
    }
    
    public EObject getElement1(final InnerDeclaration innerDeclaration, final DataType pcmDataType, final Property umlProperty, final org.eclipse.uml2.uml.DataType umlType) {
      return umlProperty;
    }
    
    public void update0Element(final InnerDeclaration innerDeclaration, final DataType pcmDataType, final Property umlProperty, final org.eclipse.uml2.uml.DataType umlType) {
      if ((pcmDataType == null)) {
        umlProperty.setType(null);
      } else {
        if ((umlType == null)) {
          umlProperty.setType(PcmToUmlUtil.retrieveUmlType(this.correspondenceModel, pcmDataType));
        } else {
          umlProperty.setType(umlType);
        }
        PcmToUmlUtil.updateMultiplicity(umlProperty, Boolean.valueOf((pcmDataType instanceof CollectionDataType)));
      }
    }
    
    public EObject getCorrepondenceSourceUmlProperty(final InnerDeclaration innerDeclaration, final DataType pcmDataType) {
      return innerDeclaration;
    }
  }
  
  public ChangeInnerDeclarationTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration, final DataType pcmDataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.ChangeInnerDeclarationTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.innerDeclaration = innerDeclaration;this.pcmDataType = pcmDataType;
  }
  
  private InnerDeclaration innerDeclaration;
  
  private DataType pcmDataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInnerDeclarationTypeRoutine with input:");
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    getLogger().debug("   DataType: " + this.pcmDataType);
    
    Property umlProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlProperty(innerDeclaration, pcmDataType), // correspondence source supplier
    	Property.class,
    	(Property _element) -> true, // correspondence precondition checker
    	null);
    if (umlProperty == null) {
    	return;
    }
    registerObjectUnderModification(umlProperty);
    org.eclipse.uml2.uml.DataType umlType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlType(innerDeclaration, pcmDataType, umlProperty), // correspondence source supplier
    	org.eclipse.uml2.uml.DataType.class,
    	(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(umlType);
    // val updatedElement userExecution.getElement1(innerDeclaration, pcmDataType, umlProperty, umlType);
    userExecution.update0Element(innerDeclaration, pcmDataType, umlProperty, umlType);
    
    postprocessElements();
  }
}
