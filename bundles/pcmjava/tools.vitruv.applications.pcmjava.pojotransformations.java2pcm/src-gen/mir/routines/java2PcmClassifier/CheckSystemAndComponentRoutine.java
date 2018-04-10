package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * *nCheck if package has a correspondence with a component or system. nIf there is one create correspondence between component or system and the given class.
 *  
 */
@SuppressWarnings("all")
public class CheckSystemAndComponentRoutine extends AbstractRepairRoutineRealization {
  private CheckSystemAndComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceComponentOrSystem(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
      return javaPackage;
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass, final InterfaceProvidingRequiringEntity componentOrSystem) {
      return javaClass;
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass, final InterfaceProvidingRequiringEntity componentOrSystem) {
      return componentOrSystem;
    }
  }
  
  public CheckSystemAndComponentRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CheckSystemAndComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.javaPackage = javaPackage;this.javaClass = javaClass;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  private org.emftext.language.java.classifiers.Class javaClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CheckSystemAndComponentRoutine with input:");
    getLogger().debug("   javaPackage: " + this.javaPackage);
    getLogger().debug("   javaClass: " + this.javaClass);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity componentOrSystem = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComponentOrSystem(javaPackage, javaClass), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (componentOrSystem == null) {
    	return false;
    }
    registerObjectUnderModification(componentOrSystem);
    addCorrespondenceBetween(userExecution.getElement1(javaPackage, javaClass, componentOrSystem), userExecution.getElement2(javaPackage, javaClass, componentOrSystem), "");
    
    postprocessElements();
    
    return true;
  }
}
