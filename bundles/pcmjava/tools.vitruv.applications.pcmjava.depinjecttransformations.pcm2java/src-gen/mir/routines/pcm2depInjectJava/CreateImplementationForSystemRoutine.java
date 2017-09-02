package mir.routines.pcm2depInjectJava;

import java.io.IOException;
import mir.routines.pcm2depInjectJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateImplementationForSystemRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateImplementationForSystemRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceSystemPackage(final org.palladiosimulator.pcm.system.System system) {
      return system;
    }
    
    public void callRoutine1(final org.palladiosimulator.pcm.system.System system, final org.emftext.language.java.containers.Package systemPackage, @Extension final RoutinesFacade _routinesFacade) {
      String _entityName = system.getEntityName();
      String _plus = (_entityName + "Impl");
      _routinesFacade.createJavaClass(system, systemPackage, _plus);
    }
  }
  
  public CreateImplementationForSystemRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System system) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2depInjectJava.CreateImplementationForSystemRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(getExecutionState(), this);
    this.system = system;
  }
  
  private org.palladiosimulator.pcm.system.System system;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateImplementationForSystemRoutine with input:");
    getLogger().debug("   system: " + this.system);
    
    org.emftext.language.java.containers.Package systemPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceSystemPackage(system), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (systemPackage == null) {
    	return false;
    }
    registerObjectUnderModification(systemPackage);
    userExecution.callRoutine1(system, systemPackage, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
