package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Feature;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetUmlFeatureStaticRoutine extends AbstractRepairRoutineRealization {
  private SetUmlFeatureStaticRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final AnnotableAndModifiable jElem, final Boolean isStatic, final Feature uFeature) {
      return uFeature;
    }
    
    public void update0Element(final AnnotableAndModifiable jElem, final Boolean isStatic, final Feature uFeature) {
      uFeature.setIsStatic((isStatic).booleanValue());
    }
    
    public EObject getCorrepondenceSourceUFeature(final AnnotableAndModifiable jElem, final Boolean isStatic) {
      return jElem;
    }
  }
  
  public SetUmlFeatureStaticRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AnnotableAndModifiable jElem, final Boolean isStatic) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.SetUmlFeatureStaticRoutine.ActionUserExecution(getExecutionState(), this);
    this.jElem = jElem;this.isStatic = isStatic;
  }
  
  private AnnotableAndModifiable jElem;
  
  private Boolean isStatic;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlFeatureStaticRoutine with input:");
    getLogger().debug("   jElem: " + this.jElem);
    getLogger().debug("   isStatic: " + this.isStatic);
    
    org.eclipse.uml2.uml.Feature uFeature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUFeature(jElem, isStatic), // correspondence source supplier
    	org.eclipse.uml2.uml.Feature.class,
    	(org.eclipse.uml2.uml.Feature _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uFeature == null) {
    	return false;
    }
    registerObjectUnderModification(uFeature);
    // val updatedElement userExecution.getElement1(jElem, isStatic, uFeature);
    userExecution.update0Element(jElem, isStatic, uFeature);
    
    postprocessElements();
    
    return true;
  }
}
