package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ProvidedRole pcmProvidedRole, final InterfaceRealization interfaceRealization) {
      return interfaceRealization;
    }
    
    public EObject getCorrepondenceSourceInterfaceRealization(final ProvidedRole pcmProvidedRole) {
      return pcmProvidedRole;
    }
  }
  
  public DeleteProvidedRoleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ProvidedRole pcmProvidedRole) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.DeleteProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmProvidedRole = pcmProvidedRole;
  }
  
  private ProvidedRole pcmProvidedRole;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteProvidedRoleRoutine with input:");
    getLogger().debug("   ProvidedRole: " + this.pcmProvidedRole);
    
    InterfaceRealization interfaceRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceRealization(pcmProvidedRole), // correspondence source supplier
    	InterfaceRealization.class,
    	(InterfaceRealization _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceRealization == null) {
    	return;
    }
    registerObjectUnderModification(interfaceRealization);
    deleteObject(userExecution.getElement1(pcmProvidedRole, interfaceRealization));
    
    postprocessElements();
  }
}
