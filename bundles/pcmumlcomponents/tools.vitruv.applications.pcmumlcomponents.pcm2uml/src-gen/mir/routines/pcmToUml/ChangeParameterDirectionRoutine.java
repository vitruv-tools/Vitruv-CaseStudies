package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.PcmToUmlUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeParameterDirectionRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeParameterDirectionRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter pcmParameter, final org.eclipse.uml2.uml.Parameter umlParameter) {
      return umlParameter;
    }
    
    public void update0Element(final Parameter pcmParameter, final org.eclipse.uml2.uml.Parameter umlParameter) {
      ParameterModifier _modifier__Parameter = pcmParameter.getModifier__Parameter();
      ParameterDirectionKind _umlParameterDirection = PcmToUmlUtil.getUmlParameterDirection(_modifier__Parameter);
      umlParameter.setDirection(_umlParameterDirection);
    }
    
    public EObject getCorrepondenceSourceUmlParameter(final Parameter pcmParameter) {
      return pcmParameter;
    }
  }
  
  public ChangeParameterDirectionRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.ChangeParameterDirectionRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmParameter = pcmParameter;
  }
  
  private Parameter pcmParameter;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeParameterDirectionRoutine with input:");
    getLogger().debug("   Parameter: " + this.pcmParameter);
    
    org.eclipse.uml2.uml.Parameter umlParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParameter(pcmParameter), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	null);
    if (umlParameter == null) {
    	return;
    }
    registerObjectUnderModification(umlParameter);
    // val updatedElement userExecution.getElement1(pcmParameter, umlParameter);
    userExecution.update0Element(pcmParameter, umlParameter);
    
    postprocessElements();
  }
}
