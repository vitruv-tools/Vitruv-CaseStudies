package mir.routines.javaToUmlmethod;

import java.io.IOException;
import mir.routines.javaToUmlmethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Feature;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetUmlFeatureStaticRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetUmlFeatureStaticRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final AnnotableAndModifiable jElem, final Integer isStatic, final Feature uFeature) {
      return uFeature;
    }
    
    public void update0Element(final AnnotableAndModifiable jElem, final Integer isStatic, final Feature uFeature) {
      if (((isStatic).intValue() == 1)) {
        uFeature.setIsStatic(true);
      } else {
        if (((isStatic).intValue() == 0)) {
          uFeature.setIsStatic(false);
        } else {
          throw new IllegalArgumentException(("Invalid isStatic Value: " + isStatic));
        }
      }
    }
    
    public EObject getCorrepondenceSourceUFeature(final AnnotableAndModifiable jElem, final Integer isStatic) {
      return jElem;
    }
  }
  
  public SetUmlFeatureStaticRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AnnotableAndModifiable jElem, final Integer isStatic) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlmethod.SetUmlFeatureStaticRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlmethod.RoutinesFacade(getExecutionState(), this);
    this.jElem = jElem;this.isStatic = isStatic;
  }
  
  private AnnotableAndModifiable jElem;
  
  private Integer isStatic;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlFeatureStaticRoutine with input:");
    getLogger().debug("   AnnotableAndModifiable: " + this.jElem);
    getLogger().debug("   Integer: " + this.isStatic);
    
    Feature uFeature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUFeature(jElem, isStatic), // correspondence source supplier
    	Feature.class,
    	(Feature _element) -> true, // correspondence precondition checker
    	null);
    if (uFeature == null) {
    	return;
    }
    registerObjectUnderModification(uFeature);
    // val updatedElement userExecution.getElement1(jElem, isStatic, uFeature);
    userExecution.update0Element(jElem, isStatic, uFeature);
    
    postprocessElements();
  }
}
