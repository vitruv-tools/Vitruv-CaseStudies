package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteInnerDeclarationOfPropertyRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteInnerDeclarationOfPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceInnerDeclaration(final Property umlProperty) {
      return umlProperty;
    }
    
    public EObject getElement1(final Property umlProperty, final InnerDeclaration innerDeclaration) {
      return innerDeclaration;
    }
  }
  
  public DeleteInnerDeclarationOfPropertyRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.DeleteInnerDeclarationOfPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlProperty = umlProperty;
  }
  
  private Property umlProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteInnerDeclarationOfPropertyRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    
    org.palladiosimulator.pcm.repository.InnerDeclaration innerDeclaration = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInnerDeclaration(umlProperty), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.InnerDeclaration.class,
    	(org.palladiosimulator.pcm.repository.InnerDeclaration _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (innerDeclaration == null) {
    	return false;
    }
    registerObjectUnderModification(innerDeclaration);
    deleteObject(userExecution.getElement1(umlProperty, innerDeclaration));
    
    postprocessElements();
    
    return true;
  }
}
