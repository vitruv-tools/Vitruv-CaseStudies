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
public class ChangeNameOfCorrespondingInnerDeclarationRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingInnerDeclarationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final String newName, final InnerDeclaration pcmInnerDeclaration) {
      return pcmInnerDeclaration;
    }
    
    public void update0Element(final Property umlProperty, final String newName, final InnerDeclaration pcmInnerDeclaration) {
      pcmInnerDeclaration.setEntityName(newName);
    }
    
    public EObject getCorrepondenceSourcePcmInnerDeclaration(final Property umlProperty, final String newName) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final String newName) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
  }
  
  public ChangeNameOfCorrespondingInnerDeclarationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInnerDeclarationPropertyReactions.ChangeNameOfCorrespondingInnerDeclarationRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.newName = newName;
  }
  
  private Property umlProperty;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingInnerDeclarationRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   newName: " + this.newName);
    
    org.palladiosimulator.pcm.repository.InnerDeclaration pcmInnerDeclaration = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInnerDeclaration(umlProperty, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.InnerDeclaration.class,
    	(org.palladiosimulator.pcm.repository.InnerDeclaration _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, newName), 
    	false // asserted
    	);
    if (pcmInnerDeclaration == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInnerDeclaration);
    // val updatedElement userExecution.getElement1(umlProperty, newName, pcmInnerDeclaration);
    userExecution.update0Element(umlProperty, newName, pcmInnerDeclaration);
    
    postprocessElements();
    
    return true;
  }
}
