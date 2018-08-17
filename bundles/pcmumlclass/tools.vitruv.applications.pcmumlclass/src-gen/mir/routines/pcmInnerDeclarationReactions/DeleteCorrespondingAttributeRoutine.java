package mir.routines.pcmInnerDeclarationReactions;

import java.io.IOException;
import mir.routines.pcmInnerDeclarationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingAttributeRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
      return pcmAttribute;
    }
    
    public String getRetrieveTag1(final InnerDeclaration pcmAttribute) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public EObject getElement2(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
      return umlAttribute;
    }
    
    public EObject getElement3(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
      return umlAttribute;
    }
    
    public String getTag1(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlAttribute(final InnerDeclaration pcmAttribute) {
      return pcmAttribute;
    }
  }
  
  public DeleteCorrespondingAttributeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration pcmAttribute) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInnerDeclarationReactions.DeleteCorrespondingAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAttribute = pcmAttribute;
  }
  
  private InnerDeclaration pcmAttribute;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingAttributeRoutine with input:");
    getLogger().debug("   pcmAttribute: " + this.pcmAttribute);
    
    org.eclipse.uml2.uml.Property umlAttribute = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlAttribute(pcmAttribute), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmAttribute), 
    	false // asserted
    	);
    if (umlAttribute == null) {
    	return false;
    }
    registerObjectUnderModification(umlAttribute);
    removeCorrespondenceBetween(userExecution.getElement1(pcmAttribute, umlAttribute), userExecution.getElement2(pcmAttribute, umlAttribute), userExecution.getTag1(pcmAttribute, umlAttribute));
    
    deleteObject(userExecution.getElement3(pcmAttribute, umlAttribute));
    
    postprocessElements();
    
    return true;
  }
}
