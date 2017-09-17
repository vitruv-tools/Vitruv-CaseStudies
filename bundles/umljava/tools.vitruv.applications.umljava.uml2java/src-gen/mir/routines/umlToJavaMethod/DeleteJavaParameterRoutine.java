package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
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
    
    public EObject getCorrepondenceSourceJParam(final Parameter uParam) {
      return uParam;
    }
    
    public EObject getElement1(final Parameter uParam, final OrdinaryParameter jParam) {
      return jParam;
    }
  }
  
  public DeleteJavaParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter uParam) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.DeleteJavaParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uParam = uParam;
  }
  
  private Parameter uParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaParameterRoutine with input:");
    getLogger().debug("   uParam: " + this.uParam);
    
    org.emftext.language.java.parameters.OrdinaryParameter jParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJParam(uParam), // correspondence source supplier
    	org.emftext.language.java.parameters.OrdinaryParameter.class,
    	(org.emftext.language.java.parameters.OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jParam == null) {
    	return false;
    }
    registerObjectUnderModification(jParam);
    deleteObject(userExecution.getElement1(uParam, jParam));
    
    postprocessElements();
    
    return true;
  }
}
