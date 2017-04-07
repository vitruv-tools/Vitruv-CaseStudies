package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public EObject getElement1(final Model umlCompModel, final Model umlClassModel) {
      return umlClassModel;
    }
    
    public void update0Element(final Model umlCompModel, final Model umlClassModel) {
      umlClassModel.setName(umlCompModel.getName());
    }
    
    public EObject getCorrepondenceSourceUmlClassModel(final Model umlCompModel) {
      return umlCompModel;
    }
    
    public void callRoutine1(final Model umlCompModel, final Model umlClassModel, @Extension final RoutinesFacade _routinesFacade) {
      String _name = umlCompModel.getName();
      String _plus = ("model/" + _name);
      String _plus_1 = (_plus + ".uml");
      this.persistProjectRelative(umlCompModel, umlClassModel, _plus_1);
    }
  }
  
  public RenameClassModelForComponentModelRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model umlCompModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.RenameClassModelForComponentModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.umlCompModel = umlCompModel;
  }
  
  private Model umlCompModel;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameClassModelForComponentModelRoutine with input:");
    getLogger().debug("   Model: " + this.umlCompModel);
    
    Model umlClassModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlClassModel(umlCompModel), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlClassModel == null) {
    	return;
    }
    registerObjectUnderModification(umlClassModel);
    // val updatedElement userExecution.getElement1(umlCompModel, umlClassModel);
    userExecution.update0Element(umlCompModel, umlClassModel);
    
    userExecution.callRoutine1(umlCompModel, umlClassModel, actionsFacade);
    
    postprocessElements();
  }
}
