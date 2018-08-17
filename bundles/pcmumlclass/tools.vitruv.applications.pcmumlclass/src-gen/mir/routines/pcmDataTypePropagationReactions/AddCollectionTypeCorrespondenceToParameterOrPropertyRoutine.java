package mir.routines.pcmDataTypePropagationReactions;

import java.io.IOException;
import mir.routines.pcmDataTypePropagationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCollectionTypeCorrespondenceToParameterOrPropertyRoutine extends AbstractRepairRoutineRealization {
  private AddCollectionTypeCorrespondenceToParameterOrPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CollectionDataType newCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      return umlMultiplicity;
    }
    
    public void update0Element(final CollectionDataType newCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      umlMultiplicity.setLower(0);
      umlMultiplicity.setUpper(LiteralUnlimitedNatural.UNLIMITED);
    }
    
    public EObject getElement2(final CollectionDataType newCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      return newCollectionDataType;
    }
    
    public EObject getElement3(final CollectionDataType newCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      return umlElement;
    }
    
    public String getTag1(final CollectionDataType newCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      return tag;
    }
    
    public void callRoutine1(final CollectionDataType newCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.replaceTypeOfCorrespondingParameterOrProperty(newCollectionDataType.getInnerType_CollectionDataType(), umlElement);
    }
  }
  
  public AddCollectionTypeCorrespondenceToParameterOrPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType newCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmDataTypePropagationReactions.AddCollectionTypeCorrespondenceToParameterOrPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.newCollectionDataType = newCollectionDataType;this.umlElement = umlElement;this.umlMultiplicity = umlMultiplicity;this.tag = tag;
  }
  
  private CollectionDataType newCollectionDataType;
  
  private TypedElement umlElement;
  
  private MultiplicityElement umlMultiplicity;
  
  private String tag;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCollectionTypeCorrespondenceToParameterOrPropertyRoutine with input:");
    getLogger().debug("   newCollectionDataType: " + this.newCollectionDataType);
    getLogger().debug("   umlElement: " + this.umlElement);
    getLogger().debug("   umlMultiplicity: " + this.umlMultiplicity);
    getLogger().debug("   tag: " + this.tag);
    
    // val updatedElement userExecution.getElement1(newCollectionDataType, umlElement, umlMultiplicity, tag);
    userExecution.update0Element(newCollectionDataType, umlElement, umlMultiplicity, tag);
    
    addCorrespondenceBetween(userExecution.getElement2(newCollectionDataType, umlElement, umlMultiplicity, tag), userExecution.getElement3(newCollectionDataType, umlElement, umlMultiplicity, tag), userExecution.getTag1(newCollectionDataType, umlElement, umlMultiplicity, tag));
    
    userExecution.callRoutine1(newCollectionDataType, umlElement, umlMultiplicity, tag, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
