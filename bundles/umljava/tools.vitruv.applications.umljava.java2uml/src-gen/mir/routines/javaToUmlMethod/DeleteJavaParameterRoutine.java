package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.emftext.language.java.parameters.OrdinaryParameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteJavaParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUParam(final OrdinaryParameter jParam) {
      return jParam;
    }
    
    public EObject getElement1(final OrdinaryParameter jParam, final Parameter uParam) {
      return uParam;
    }
  }
  
  public DeleteJavaParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OrdinaryParameter jParam) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.DeleteJavaParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.jParam = jParam;
  }
  
  private OrdinaryParameter jParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaParameterRoutine with input:");
    getLogger().debug("   jParam: " + this.jParam);
    
    org.eclipse.uml2.uml.Parameter uParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUParam(jParam), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uParam == null) {
    	return false;
    }
    registerObjectUnderModification(uParam);
    deleteObject(userExecution.getElement1(jParam, uParam));
    
    postprocessElements();
    
    return true;
  }
}
