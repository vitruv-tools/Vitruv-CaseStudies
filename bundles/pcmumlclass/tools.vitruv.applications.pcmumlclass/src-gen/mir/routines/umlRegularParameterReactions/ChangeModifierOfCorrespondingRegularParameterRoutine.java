package mir.routines.umlRegularParameterReactions;

import java.io.IOException;
import mir.routines.umlRegularParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeModifierOfCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private ChangeModifierOfCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public void update0Element(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      ParameterModifier matchingModifier = ParameterModifier.NONE;
      ParameterModifier[] _values = ParameterModifier.values();
      for (final ParameterModifier modifier : _values) {
        ParameterDirectionKind _matchingParameterDirection = PcmUmlClassHelper.getMatchingParameterDirection(modifier);
        ParameterDirectionKind _direction = umlParameter.getDirection();
        boolean _tripleEquals = (_matchingParameterDirection == _direction);
        if (_tripleEquals) {
          matchingModifier = modifier;
        }
      }
      pcmParameter.setModifier__Parameter(matchingModifier);
    }
    
    public String getRetrieveTag1(final Parameter umlParameter) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getCorrepondenceSourcePcmParameter(final Parameter umlParameter) {
      return umlParameter;
    }
  }
  
  public ChangeModifierOfCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRegularParameterReactions.ChangeModifierOfCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;
  }
  
  private Parameter umlParameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeModifierOfCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    
    org.palladiosimulator.pcm.repository.Parameter pcmParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmParameter(umlParameter), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParameter), 
    	false // asserted
    	);
    if (pcmParameter == null) {
    	return false;
    }
    registerObjectUnderModification(pcmParameter);
    // val updatedElement userExecution.getElement1(umlParameter, pcmParameter);
    userExecution.update0Element(umlParameter, pcmParameter);
    
    postprocessElements();
    
    return true;
  }
}
