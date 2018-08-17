package mir.routines.pcmDataTypePropagationReactions;

import java.io.IOException;
import mir.routines.pcmDataTypePropagationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.TypedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCollectionTypeCorrespondenceFromParameterOrPropertyRoutine extends AbstractRepairRoutineRealization {
  private RemoveCollectionTypeCorrespondenceFromParameterOrPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CollectionDataType oldCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      return umlElement;
    }
    
    public void update0Element(final CollectionDataType oldCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      umlElement.setType(null);
    }
    
    public EObject getElement4(final CollectionDataType oldCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      return umlElement;
    }
    
    public EObject getElement2(final CollectionDataType oldCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      return umlMultiplicity;
    }
    
    public EObject getElement3(final CollectionDataType oldCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      return oldCollectionDataType;
    }
    
    public String getTag1(final CollectionDataType oldCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      return tag;
    }
    
    public void update1Element(final CollectionDataType oldCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      umlMultiplicity.setLower(1);
      umlMultiplicity.setUpper(1);
    }
  }
  
  public RemoveCollectionTypeCorrespondenceFromParameterOrPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType oldCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmDataTypePropagationReactions.RemoveCollectionTypeCorrespondenceFromParameterOrPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.oldCollectionDataType = oldCollectionDataType;this.umlElement = umlElement;this.umlMultiplicity = umlMultiplicity;this.tag = tag;
  }
  
  private CollectionDataType oldCollectionDataType;
  
  private TypedElement umlElement;
  
  private MultiplicityElement umlMultiplicity;
  
  private String tag;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCollectionTypeCorrespondenceFromParameterOrPropertyRoutine with input:");
    getLogger().debug("   oldCollectionDataType: " + this.oldCollectionDataType);
    getLogger().debug("   umlElement: " + this.umlElement);
    getLogger().debug("   umlMultiplicity: " + this.umlMultiplicity);
    getLogger().debug("   tag: " + this.tag);
    
    // val updatedElement userExecution.getElement1(oldCollectionDataType, umlElement, umlMultiplicity, tag);
    userExecution.update0Element(oldCollectionDataType, umlElement, umlMultiplicity, tag);
    
    // val updatedElement userExecution.getElement2(oldCollectionDataType, umlElement, umlMultiplicity, tag);
    userExecution.update1Element(oldCollectionDataType, umlElement, umlMultiplicity, tag);
    
    removeCorrespondenceBetween(userExecution.getElement3(oldCollectionDataType, umlElement, umlMultiplicity, tag), userExecution.getElement4(oldCollectionDataType, umlElement, umlMultiplicity, tag), userExecution.getTag1(oldCollectionDataType, umlElement, umlMultiplicity, tag));
    
    postprocessElements();
    
    return true;
  }
}
