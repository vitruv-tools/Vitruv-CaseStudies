package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
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
    
    public void callRoutine1(final Model umlClassModel, final Model compModel, @Extension final RoutinesFacade _routinesFacade) {
      String _name = umlClassModel.getName();
      String _plus = (SharedUtil.FOLDER_NAME + _name);
      String _plus_1 = (_plus + ".");
      String _plus_2 = (_plus_1 + SharedUtil.MODEL_FILE_EXTENSION);
      this.persistProjectRelative(umlClassModel, compModel, _plus_2);
    }
  }
  
  public RenameComponentModelForClassModelRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model umlClassModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RenameComponentModelForClassModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClassModel = umlClassModel;
  }
  
  private Model umlClassModel;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameComponentModelForClassModelRoutine with input:");
    getLogger().debug("   Model: " + this.umlClassModel);
    
    Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(umlClassModel), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (compModel == null) {
    	return;
    }
    registerObjectUnderModification(compModel);
    // val updatedElement userExecution.getElement1(umlClassModel, compModel);
    userExecution.update0Element(umlClassModel, compModel);
    
    userExecution.callRoutine1(umlClassModel, compModel, actionsFacade);
    
    postprocessElements();
  }
}
