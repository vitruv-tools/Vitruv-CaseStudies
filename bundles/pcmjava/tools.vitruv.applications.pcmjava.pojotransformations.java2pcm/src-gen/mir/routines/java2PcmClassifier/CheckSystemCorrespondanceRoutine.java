package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CheckSystemCorrespondanceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CheckSystemCorrespondanceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSourcePcmSystem(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
      return javaPackage;
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return javaClass;
    }
  }
  
  public CheckSystemCorrespondanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CheckSystemCorrespondanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;this.javaClass = javaClass;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  private org.emftext.language.java.classifiers.Class javaClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CheckSystemCorrespondanceRoutine with input:");
    getLogger().debug("   Package: " + this.javaPackage);
    getLogger().debug("   Class: " + this.javaClass);
    
    org.palladiosimulator.pcm.system.System pcmSystem = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSystem(javaPackage, javaClass), // correspondence source supplier
    	org.palladiosimulator.pcm.system.System.class,
    	(org.palladiosimulator.pcm.system.System _element) -> true, // correspondence precondition checker
    	null);
    if (pcmSystem == null) {
    	return;
    }
    registerObjectUnderModification(pcmSystem);
    addCorrespondenceBetween(userExecution.getElement1(javaPackage, javaClass, pcmSystem), userExecution.getElement2(javaPackage, javaClass, pcmSystem), "");
    
    postprocessElements();
  }
}
