package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
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
    
    public void callRoutine1(final Model compModel, final Model classModel, @Extension final RoutinesFacade _routinesFacade) {
      String _name = compModel.getName();
      String _plus = (SharedUtil.FOLDER_NAME + _name);
      String _plus_1 = (_plus + ".");
      String _plus_2 = (_plus_1 + SharedUtil.MODEL_FILE_EXTENSION);
      this.persistProjectRelative(compModel, classModel, _plus_2);
    }
  }
  
  public RenameClassModelForComponentModelRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model compModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.RenameClassModelForComponentModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compModel = compModel;
  }
  
  private Model compModel;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameClassModelForComponentModelRoutine with input:");
    getLogger().debug("   Model: " + this.compModel);
    
    Model classModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassModel(compModel), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (classModel == null) {
    	return;
    }
    registerObjectUnderModification(classModel);
    // val updatedElement userExecution.getElement1(compModel, classModel);
    userExecution.update0Element(compModel, classModel);
    
    userExecution.callRoutine1(compModel, classModel, actionsFacade);
    
    postprocessElements();
  }
}
