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
public class ChangeNameOfCorrespondingAttributeRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InnerDeclaration pcmAttribute, final String newName, final Property umlAttribute) {
      return umlAttribute;
    }
    
    public void update0Element(final InnerDeclaration pcmAttribute, final String newName, final Property umlAttribute) {
      umlAttribute.setName(newName);
    }
    
    public String getRetrieveTag1(final InnerDeclaration pcmAttribute, final String newName) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlAttribute(final InnerDeclaration pcmAttribute, final String newName) {
      return pcmAttribute;
    }
  }
  
  public ChangeNameOfCorrespondingAttributeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration pcmAttribute, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInnerDeclarationReactions.ChangeNameOfCorrespondingAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAttribute = pcmAttribute;this.newName = newName;
  }
  
  private InnerDeclaration pcmAttribute;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingAttributeRoutine with input:");
    getLogger().debug("   pcmAttribute: " + this.pcmAttribute);
    getLogger().debug("   newName: " + this.newName);
    
    org.eclipse.uml2.uml.Property umlAttribute = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlAttribute(pcmAttribute, newName), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmAttribute, newName), 
    	false // asserted
    	);
    if (umlAttribute == null) {
    	return false;
    }
    registerObjectUnderModification(umlAttribute);
    // val updatedElement userExecution.getElement1(pcmAttribute, newName, umlAttribute);
    userExecution.update0Element(pcmAttribute, newName, umlAttribute);
    
    postprocessElements();
    
    return true;
  }
}
