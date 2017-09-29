package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameClassModelForComponentModelRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameClassModelForComponentModelRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Model compModel, final Model classModel) {
      return classModel;
    }
    
    public void update0Element(final Model compModel, final Model classModel) {
      classModel.setName(compModel.getName());
    }
    
    public EObject getCorrepondenceSourceClassModel(final Model compModel) {
      return compModel;
    }
  }
  
  public RenameClassModelForComponentModelRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model compModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.RenameClassModelForComponentModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compModel = compModel;
  }
  
  private Model compModel;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameClassModelForComponentModelRoutine with input:");
    getLogger().debug("   compModel: " + this.compModel);
    
    org.eclipse.uml2.uml.Model classModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassModel(compModel), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (classModel == null) {
    	return false;
    }
    registerObjectUnderModification(classModel);
    // val updatedElement userExecution.getElement1(compModel, classModel);
    userExecution.update0Element(compModel, classModel);
    
    postprocessElements();
    
    return true;
  }
}
