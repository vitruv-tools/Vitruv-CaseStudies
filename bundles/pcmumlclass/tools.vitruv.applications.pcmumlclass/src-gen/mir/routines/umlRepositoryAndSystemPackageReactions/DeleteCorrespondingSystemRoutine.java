package mir.routines.umlRepositoryAndSystemPackageReactions;

import java.io.IOException;
import mir.routines.umlRepositoryAndSystemPackageReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingSystemRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingSystemRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package umlSystemPkg, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSourcePcmSystem(final org.eclipse.uml2.uml.Package umlSystemPkg) {
      return umlSystemPkg;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package umlSystemPkg) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package umlSystemPkg, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return umlSystemPkg;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Package umlSystemPkg, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Package umlSystemPkg, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
  }
  
  public DeleteCorrespondingSystemRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlSystemPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryAndSystemPackageReactions.DeleteCorrespondingSystemRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlSystemPkg = umlSystemPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlSystemPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingSystemRoutine with input:");
    getLogger().debug("   umlSystemPkg: " + this.umlSystemPkg);
    
    org.palladiosimulator.pcm.system.System pcmSystem = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSystem(umlSystemPkg), // correspondence source supplier
    	org.palladiosimulator.pcm.system.System.class,
    	(org.palladiosimulator.pcm.system.System _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlSystemPkg), 
    	false // asserted
    	);
    if (pcmSystem == null) {
    	return false;
    }
    registerObjectUnderModification(pcmSystem);
    removeCorrespondenceBetween(userExecution.getElement1(umlSystemPkg, pcmSystem), userExecution.getElement2(umlSystemPkg, pcmSystem), userExecution.getTag1(umlSystemPkg, pcmSystem));
    
    deleteObject(userExecution.getElement3(umlSystemPkg, pcmSystem));
    
    postprocessElements();
    
    return true;
  }
}
