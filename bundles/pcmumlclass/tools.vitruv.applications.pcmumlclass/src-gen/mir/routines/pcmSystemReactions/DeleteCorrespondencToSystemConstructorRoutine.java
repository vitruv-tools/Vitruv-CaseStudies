package mir.routines.pcmSystemReactions;

import java.io.IOException;
import mir.routines.pcmSystemReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondencToSystemConstructorRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondencToSystemConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSourceUmlSystemConstructor(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public EObject getElement2(final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
      return umlSystemConstructor;
    }
    
    public String getTag1(final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
  }
  
  public DeleteCorrespondencToSystemConstructorRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.DeleteCorrespondencToSystemConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondencToSystemConstructorRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    
    org.eclipse.uml2.uml.Operation umlSystemConstructor = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlSystemConstructor(pcmSystem), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSystem), 
    	false // asserted
    	);
    if (umlSystemConstructor == null) {
    	return false;
    }
    registerObjectUnderModification(umlSystemConstructor);
    removeCorrespondenceBetween(userExecution.getElement1(pcmSystem, umlSystemConstructor), userExecution.getElement2(pcmSystem, umlSystemConstructor), userExecution.getTag1(pcmSystem, umlSystemConstructor));
    
    postprocessElements();
    
    return true;
  }
}
