package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.emftext.language.java.parameters.OrdinaryParameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameJavaParameterRoutine.ActionUserExecution userExecution;
  
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
    
    public void update0Element(final Parameter uParam, final OrdinaryParameter jParam) {
      String _name = uParam.getName();
      jParam.setName(_name);
    }
  }
  
  public RenameJavaParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter uParam) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.RenameJavaParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uParam = uParam;
  }
  
  private Parameter uParam;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaParameterRoutine with input:");
    getLogger().debug("   Parameter: " + this.uParam);
    
    OrdinaryParameter jParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJParam(uParam), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null);
    if (jParam == null) {
    	return;
    }
    initializeRetrieveElementState(jParam);
    // val updatedElement userExecution.getElement1(uParam, jParam);
    userExecution.update0Element(uParam, jParam);
    
    postprocessElementStates();
  }
}
