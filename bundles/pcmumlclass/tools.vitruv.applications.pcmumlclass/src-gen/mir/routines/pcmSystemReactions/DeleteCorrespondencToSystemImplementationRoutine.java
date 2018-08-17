package mir.routines.pcmSystemReactions;

import java.io.IOException;
import mir.routines.pcmSystemReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondencToSystemImplementationRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondencToSystemImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSourceUmlSystemImplementation(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getElement2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return umlSystemImplementation;
    }
    
    public String getTag1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
  }
  
  public DeleteCorrespondencToSystemImplementationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.DeleteCorrespondencToSystemImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondencToSystemImplementationRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    
    org.eclipse.uml2.uml.Class umlSystemImplementation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlSystemImplementation(pcmSystem), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSystem), 
    	false // asserted
    	);
    if (umlSystemImplementation == null) {
    	return false;
    }
    registerObjectUnderModification(umlSystemImplementation);
    removeCorrespondenceBetween(userExecution.getElement1(pcmSystem, umlSystemImplementation), userExecution.getElement2(pcmSystem, umlSystemImplementation), userExecution.getTag1(pcmSystem, umlSystemImplementation));
    
    postprocessElements();
    
    return true;
  }
}
