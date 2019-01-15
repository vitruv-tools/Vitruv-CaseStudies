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
public class AddCorrespondenceForExistingAttributeRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
      return pcmAttribute;
    }
    
    public EObject getCorrepondenceSource1(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
      return pcmAttribute;
    }
    
    public EObject getCorrepondenceSource2(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
      return umlAttribute;
    }
    
    public String getRetrieveTag1(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public String getRetrieveTag2(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public EObject getElement2(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
      return umlAttribute;
    }
    
    public String getTag1(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
  }
  
  public AddCorrespondenceForExistingAttributeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration pcmAttribute, final Property umlAttribute) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInnerDeclarationReactions.AddCorrespondenceForExistingAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAttribute = pcmAttribute;this.umlAttribute = umlAttribute;
  }
  
  private InnerDeclaration pcmAttribute;
  
  private Property umlAttribute;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingAttributeRoutine with input:");
    getLogger().debug("   pcmAttribute: " + this.pcmAttribute);
    getLogger().debug("   umlAttribute: " + this.umlAttribute);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmAttribute, umlAttribute), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmAttribute, umlAttribute)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmAttribute, umlAttribute), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.InnerDeclaration.class,
    	(org.palladiosimulator.pcm.repository.InnerDeclaration _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmAttribute, umlAttribute)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmAttribute, umlAttribute), userExecution.getElement2(pcmAttribute, umlAttribute), userExecution.getTag1(pcmAttribute, umlAttribute));
    
    postprocessElements();
    
    return true;
  }
}
