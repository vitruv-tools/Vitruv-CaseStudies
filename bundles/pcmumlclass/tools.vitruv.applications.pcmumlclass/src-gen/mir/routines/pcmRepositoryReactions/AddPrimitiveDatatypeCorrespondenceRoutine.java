package mir.routines.pcmRepositoryReactions;

import java.io.IOException;
import mir.routines.pcmRepositoryReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PrimitiveType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddPrimitiveDatatypeCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private AddPrimitiveDatatypeCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final PrimitiveDataType pcmPrimitiveType, final PrimitiveType umlPrimitiveType) {
      return pcmPrimitiveType;
    }
    
    public EObject getCorrepondenceSource1(final PrimitiveDataType pcmPrimitiveType, final PrimitiveType umlPrimitiveType) {
      return pcmPrimitiveType;
    }
    
    public EObject getCorrepondenceSource2(final PrimitiveDataType pcmPrimitiveType, final PrimitiveType umlPrimitiveType) {
      return umlPrimitiveType;
    }
    
    public String getRetrieveTag1(final PrimitiveDataType pcmPrimitiveType, final PrimitiveType umlPrimitiveType) {
      return TagLiterals.DATATYPE__TYPE;
    }
    
    public String getRetrieveTag2(final PrimitiveDataType pcmPrimitiveType, final PrimitiveType umlPrimitiveType) {
      return TagLiterals.DATATYPE__TYPE;
    }
    
    public EObject getElement2(final PrimitiveDataType pcmPrimitiveType, final PrimitiveType umlPrimitiveType) {
      return umlPrimitiveType;
    }
    
    public String getTag1(final PrimitiveDataType pcmPrimitiveType, final PrimitiveType umlPrimitiveType) {
      return TagLiterals.DATATYPE__TYPE;
    }
  }
  
  public AddPrimitiveDatatypeCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PrimitiveDataType pcmPrimitiveType, final PrimitiveType umlPrimitiveType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryReactions.AddPrimitiveDatatypeCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmPrimitiveType = pcmPrimitiveType;this.umlPrimitiveType = umlPrimitiveType;
  }
  
  private PrimitiveDataType pcmPrimitiveType;
  
  private PrimitiveType umlPrimitiveType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddPrimitiveDatatypeCorrespondenceRoutine with input:");
    getLogger().debug("   pcmPrimitiveType: " + this.pcmPrimitiveType);
    getLogger().debug("   umlPrimitiveType: " + this.umlPrimitiveType);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmPrimitiveType, umlPrimitiveType), // correspondence source supplier
    	org.eclipse.uml2.uml.PrimitiveType.class,
    	(org.eclipse.uml2.uml.PrimitiveType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmPrimitiveType, umlPrimitiveType)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmPrimitiveType, umlPrimitiveType), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.PrimitiveDataType.class,
    	(org.palladiosimulator.pcm.repository.PrimitiveDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmPrimitiveType, umlPrimitiveType)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmPrimitiveType, umlPrimitiveType), userExecution.getElement2(pcmPrimitiveType, umlPrimitiveType), userExecution.getTag1(pcmPrimitiveType, umlPrimitiveType));
    
    postprocessElements();
    
    return true;
  }
}
