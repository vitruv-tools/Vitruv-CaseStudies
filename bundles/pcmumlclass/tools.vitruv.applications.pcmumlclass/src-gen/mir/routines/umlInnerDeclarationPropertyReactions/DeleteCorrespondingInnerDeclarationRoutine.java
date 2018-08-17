package mir.routines.umlInnerDeclarationPropertyReactions;

import java.io.IOException;
import mir.routines.umlInnerDeclarationPropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingInnerDeclarationRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingInnerDeclarationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return pcmInnerDeclaration;
    }
    
    public EObject getCorrepondenceSourcePcmInnerDeclaration(final Property umlProperty) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public EObject getElement2(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return umlProperty;
    }
    
    public EObject getElement3(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return pcmInnerDeclaration;
    }
    
    public String getTag1(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
  }
  
  public DeleteCorrespondingInnerDeclarationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInnerDeclarationPropertyReactions.DeleteCorrespondingInnerDeclarationRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;
  }
  
  private Property umlProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingInnerDeclarationRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    
    org.palladiosimulator.pcm.repository.InnerDeclaration pcmInnerDeclaration = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInnerDeclaration(umlProperty), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.InnerDeclaration.class,
    	(org.palladiosimulator.pcm.repository.InnerDeclaration _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty), 
    	false // asserted
    	);
    if (pcmInnerDeclaration == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInnerDeclaration);
    removeCorrespondenceBetween(userExecution.getElement1(umlProperty, pcmInnerDeclaration), userExecution.getElement2(umlProperty, pcmInnerDeclaration), userExecution.getTag1(umlProperty, pcmInnerDeclaration));
    
    deleteObject(userExecution.getElement3(umlProperty, pcmInnerDeclaration));
    
    postprocessElements();
    
    return true;
  }
}
