package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CheckComponentCorrespondanceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CheckComponentCorrespondanceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass, final ImplementationComponentType pcmComponent) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
      return javaPackage;
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass, final ImplementationComponentType pcmComponent) {
      return javaClass;
    }
  }
  
  public CheckComponentCorrespondanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CheckComponentCorrespondanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;this.javaClass = javaClass;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  private org.emftext.language.java.classifiers.Class javaClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CheckComponentCorrespondanceRoutine with input:");
    getLogger().debug("   Package: " + this.javaPackage);
    getLogger().debug("   Class: " + this.javaClass);
    
    ImplementationComponentType pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(javaPackage, javaClass), // correspondence source supplier
    	ImplementationComponentType.class,
    	(ImplementationComponentType _element) -> true, // correspondence precondition checker
    	null);
    if (pcmComponent == null) {
    	return;
    }
    registerObjectUnderModification(pcmComponent);
    addCorrespondenceBetween(userExecution.getElement1(javaPackage, javaClass, pcmComponent), userExecution.getElement2(javaPackage, javaClass, pcmComponent), "");
    
    postprocessElements();
  }
}
