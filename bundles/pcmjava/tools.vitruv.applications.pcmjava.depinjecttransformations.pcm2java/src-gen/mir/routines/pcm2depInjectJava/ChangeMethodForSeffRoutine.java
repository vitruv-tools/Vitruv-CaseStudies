package mir.routines.pcm2depInjectJava;

import java.io.IOException;
import mir.routines.pcm2depInjectJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeMethodForSeffRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeMethodForSeffRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ResourceDemandingSEFF seff, final ClassMethod oldClassMethod) {
      return oldClassMethod;
    }
    
    public EObject getCorrepondenceSourceOldClassMethod(final ResourceDemandingSEFF seff) {
      return seff;
    }
    
    public void callRoutine1(final ResourceDemandingSEFF seff, final ClassMethod oldClassMethod, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createSEFF(seff);
    }
  }
  
  public ChangeMethodForSeffRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ResourceDemandingSEFF seff) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2depInjectJava.ChangeMethodForSeffRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(getExecutionState(), this);
    this.seff = seff;
  }
  
  private ResourceDemandingSEFF seff;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeMethodForSeffRoutine with input:");
    getLogger().debug("   seff: " + this.seff);
    
    org.emftext.language.java.members.ClassMethod oldClassMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOldClassMethod(seff), // correspondence source supplier
    	org.emftext.language.java.members.ClassMethod.class,
    	(org.emftext.language.java.members.ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(oldClassMethod);
    deleteObject(userExecution.getElement1(seff, oldClassMethod));
    
    userExecution.callRoutine1(seff, oldClassMethod, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
