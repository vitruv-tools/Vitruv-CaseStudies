package mir.routines.pcmSystemReactions;

import java.io.IOException;
import mir.routines.pcmSystemReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondencToSystemPackageRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondencToSystemPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      return pcmSystem;
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
    
    public EObject getElement2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      return umlSystemPackage;
    }
    
    public EObject getCorrepondenceSourceUmlSystemPackage(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public EObject getElement3(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      return umlSystemPackage;
    }
    
    public String getTag1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
  }
  
  public DeleteCorrespondencToSystemPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.DeleteCorrespondencToSystemPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondencToSystemPackageRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    
    org.eclipse.uml2.uml.Package umlSystemPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlSystemPackage(pcmSystem), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSystem), 
    	false // asserted
    	);
    if (umlSystemPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlSystemPackage);
    removeCorrespondenceBetween(userExecution.getElement1(pcmSystem, umlSystemPackage), userExecution.getElement2(pcmSystem, umlSystemPackage), userExecution.getTag1(pcmSystem, umlSystemPackage));
    
    deleteObject(userExecution.getElement3(pcmSystem, umlSystemPackage));
    
    postprocessElements();
    
    return true;
  }
}
