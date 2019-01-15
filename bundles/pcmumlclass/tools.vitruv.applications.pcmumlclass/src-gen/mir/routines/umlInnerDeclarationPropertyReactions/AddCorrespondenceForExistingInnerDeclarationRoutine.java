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
public class AddCorrespondenceForExistingInnerDeclarationRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingInnerDeclarationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return pcmInnerDeclaration;
    }
    
    public EObject getCorrepondenceSource1(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return pcmInnerDeclaration;
    }
    
    public EObject getCorrepondenceSource2(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public EObject getElement2(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return umlProperty;
    }
    
    public String getTag1(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
  }
  
  public AddCorrespondenceForExistingInnerDeclarationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInnerDeclarationPropertyReactions.AddCorrespondenceForExistingInnerDeclarationRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.pcmInnerDeclaration = pcmInnerDeclaration;
  }
  
  private Property umlProperty;
  
  private InnerDeclaration pcmInnerDeclaration;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingInnerDeclarationRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   pcmInnerDeclaration: " + this.pcmInnerDeclaration);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlProperty, pcmInnerDeclaration), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, pcmInnerDeclaration)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(umlProperty, pcmInnerDeclaration), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.InnerDeclaration.class,
    	(org.palladiosimulator.pcm.repository.InnerDeclaration _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlProperty, pcmInnerDeclaration)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(umlProperty, pcmInnerDeclaration), userExecution.getElement2(umlProperty, pcmInnerDeclaration), userExecution.getTag1(umlProperty, pcmInnerDeclaration));
    
    postprocessElements();
    
    return true;
  }
}
