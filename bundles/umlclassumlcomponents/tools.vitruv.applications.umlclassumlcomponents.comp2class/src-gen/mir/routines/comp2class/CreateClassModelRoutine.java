package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateClassModelRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateClassModelRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Model umlCompModel, final Model umlClassModel) {
      return umlCompModel;
    }
    
    public void updateUmlClassModelElement(final Model umlCompModel, final Model umlClassModel) {
      umlClassModel.setName(umlCompModel.getName());
      String _name = umlCompModel.getName();
      String _plus = ("model/" + _name);
      String _plus_1 = (_plus + ".uml");
      this.persistProjectRelative(umlCompModel, umlClassModel, _plus_1);
    }
    
    public EObject getElement2(final Model umlCompModel, final Model umlClassModel) {
      return umlClassModel;
    }
  }
  
  public CreateClassModelRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model umlCompModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateClassModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.umlCompModel = umlCompModel;
  }
  
  private Model umlCompModel;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateClassModelRoutine with input:");
    getLogger().debug("   Model: " + this.umlCompModel);
    
    Model umlClassModel = UMLFactoryImpl.eINSTANCE.createModel();
    userExecution.updateUmlClassModelElement(umlCompModel, umlClassModel);
    
    addCorrespondenceBetween(userExecution.getElement1(umlCompModel, umlClassModel), userExecution.getElement2(umlCompModel, umlClassModel), "");
    
    postprocessElements();
  }
}
