package mir.routines.pcmSystemReactions;

import java.io.IOException;
import mir.routines.pcmSystemReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrespondenceForExistingSystemImplementationRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingSystemImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSource1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSource2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return umlSystemImplementation;
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getElement2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return umlSystemImplementation;
    }
    
    public String getTag1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
  }
  
  public AddCorrespondenceForExistingSystemImplementationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.AddCorrespondenceForExistingSystemImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;this.umlSystemImplementation = umlSystemImplementation;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  private org.eclipse.uml2.uml.Class umlSystemImplementation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingSystemImplementationRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    getLogger().debug("   umlSystemImplementation: " + this.umlSystemImplementation);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmSystem, umlSystemImplementation), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSystem, umlSystemImplementation)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmSystem, umlSystemImplementation), // correspondence source supplier
    	org.palladiosimulator.pcm.system.System.class,
    	(org.palladiosimulator.pcm.system.System _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmSystem, umlSystemImplementation)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmSystem, umlSystemImplementation), userExecution.getElement2(pcmSystem, umlSystemImplementation), userExecution.getTag1(pcmSystem, umlSystemImplementation));
    
    postprocessElements();
    
    return true;
  }
}
