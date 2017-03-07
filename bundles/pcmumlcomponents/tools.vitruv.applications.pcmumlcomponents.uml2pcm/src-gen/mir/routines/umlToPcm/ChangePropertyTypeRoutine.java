package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangePropertyTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangePropertyTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final DataType umlType, final InnerDeclaration pcmDeclaration, final org.palladiosimulator.pcm.repository.DataType pcmType) {
      return pcmDeclaration;
    }
    
    public void update0Element(final Property umlProperty, final DataType umlType, final InnerDeclaration pcmDeclaration, final org.palladiosimulator.pcm.repository.DataType pcmType) {
      pcmDeclaration.setDatatype_InnerDeclaration(pcmType);
    }
    
    public EObject getCorrepondenceSourcePcmDeclaration(final Property umlProperty, final DataType umlType) {
      return umlProperty;
    }
    
    public EObject getCorrepondenceSourcePcmType(final Property umlProperty, final DataType umlType, final InnerDeclaration pcmDeclaration) {
      return umlType;
    }
  }
  
  public ChangePropertyTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final DataType umlType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.ChangePropertyTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlType = umlType;
  }
  
  private Property umlProperty;
  
  private DataType umlType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangePropertyTypeRoutine with input:");
    getLogger().debug("   Property: " + this.umlProperty);
    getLogger().debug("   DataType: " + this.umlType);
    
    InnerDeclaration pcmDeclaration = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmDeclaration(umlProperty, umlType), // correspondence source supplier
    	InnerDeclaration.class,
    	(InnerDeclaration _element) -> true, // correspondence precondition checker
    	null);
    if (pcmDeclaration == null) {
    	return;
    }
    initializeRetrieveElementState(pcmDeclaration);
    org.palladiosimulator.pcm.repository.DataType pcmType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmType(umlProperty, umlType, pcmDeclaration), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.DataType.class,
    	(org.palladiosimulator.pcm.repository.DataType _element) -> true, // correspondence precondition checker
    	null);
    if (pcmType == null) {
    	return;
    }
    initializeRetrieveElementState(pcmType);
    // val updatedElement userExecution.getElement1(umlProperty, umlType, pcmDeclaration, pcmType);
    userExecution.update0Element(umlProperty, umlType, pcmDeclaration, pcmType);
    
    postprocessElementStates();
  }
}
