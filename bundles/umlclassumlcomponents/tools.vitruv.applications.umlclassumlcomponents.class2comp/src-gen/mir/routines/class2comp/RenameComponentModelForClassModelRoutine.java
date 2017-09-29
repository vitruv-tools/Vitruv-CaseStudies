package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameComponentModelForClassModelRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameComponentModelForClassModelRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Model umlClassModel, final Model compModel) {
      return compModel;
    }
    
    public void update0Element(final Model umlClassModel, final Model compModel) {
      compModel.setName(umlClassModel.getName());
    }
    
    public EObject getCorrepondenceSourceCompModel(final Model umlClassModel) {
      return umlClassModel;
    }
  }
  
  public RenameComponentModelForClassModelRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model umlClassModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RenameComponentModelForClassModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClassModel = umlClassModel;
  }
  
  private Model umlClassModel;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameComponentModelForClassModelRoutine with input:");
    getLogger().debug("   umlClassModel: " + this.umlClassModel);
    
    org.eclipse.uml2.uml.Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(umlClassModel), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compModel == null) {
    	return false;
    }
    registerObjectUnderModification(compModel);
    // val updatedElement userExecution.getElement1(umlClassModel, compModel);
    userExecution.update0Element(umlClassModel, compModel);
    
    postprocessElements();
    
    return true;
  }
}
