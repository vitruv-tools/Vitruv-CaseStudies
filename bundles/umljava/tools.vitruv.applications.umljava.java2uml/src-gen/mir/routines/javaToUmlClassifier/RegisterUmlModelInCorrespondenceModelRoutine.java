package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RegisterUmlModelInCorrespondenceModelRoutine extends AbstractRepairRoutineRealization {
  private RegisterUmlModelInCorrespondenceModelRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Model uModel) {
      return uModel;
    }
    
    public EObject getCorrepondenceSource1(final Model uModel) {
      return UMLPackage.Literals.MODEL;
    }
    
    public EObject getElement2(final Model uModel) {
      return UMLPackage.Literals.MODEL;
    }
  }
  
  public RegisterUmlModelInCorrespondenceModelRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model uModel) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.RegisterUmlModelInCorrespondenceModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.uModel = uModel;
  }
  
  private Model uModel;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RegisterUmlModelInCorrespondenceModelRoutine with input:");
    getLogger().debug("   uModel: " + this.uModel);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(uModel), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(uModel), userExecution.getElement2(uModel), "");
    
    postprocessElements();
    
    return true;
  }
}
