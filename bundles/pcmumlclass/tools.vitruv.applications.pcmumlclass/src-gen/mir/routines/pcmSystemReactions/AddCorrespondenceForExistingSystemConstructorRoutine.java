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
public class AddCorrespondenceForExistingSystemConstructorRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingSystemConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSource1(final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSource2(final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
      return umlSystemConstructor;
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public String getRetrieveTag2(final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public EObject getElement2(final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
      return umlSystemConstructor;
    }
    
    public String getTag1(final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
  }
  
  public AddCorrespondenceForExistingSystemConstructorRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.AddCorrespondenceForExistingSystemConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;this.umlSystemConstructor = umlSystemConstructor;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  private Operation umlSystemConstructor;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingSystemConstructorRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    getLogger().debug("   umlSystemConstructor: " + this.umlSystemConstructor);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmSystem, umlSystemConstructor), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSystem, umlSystemConstructor)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmSystem, umlSystemConstructor), // correspondence source supplier
    	org.palladiosimulator.pcm.system.System.class,
    	(org.palladiosimulator.pcm.system.System _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmSystem, umlSystemConstructor)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmSystem, umlSystemConstructor), userExecution.getElement2(pcmSystem, umlSystemConstructor), userExecution.getTag1(pcmSystem, umlSystemConstructor));
    
    postprocessElements();
    
    return true;
  }
}
