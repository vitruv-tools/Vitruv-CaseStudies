package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
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
    
    public EObject getElement1(final Model compModel, final Model umlClassModel) {
      return compModel;
    }
    
    public void updateUmlClassModelElement(final Model compModel, final Model umlClassModel) {
      umlClassModel.setName(compModel.getName());
      String _name = compModel.getName();
      String _plus = (SharedUtil.FOLDER_NAME + _name);
      String _plus_1 = (_plus + ".");
      String _plus_2 = (_plus_1 + SharedUtil.MODEL_FILE_EXTENSION);
      this.persistProjectRelative(compModel, umlClassModel, _plus_2);
    }
    
    public EObject getElement2(final Model compModel, final Model umlClassModel) {
      return umlClassModel;
    }
  }
  
  public CreateClassModelRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model compModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateClassModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compModel = compModel;
  }
  
  private Model compModel;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateClassModelRoutine with input:");
    getLogger().debug("   Model: " + this.compModel);
    
    Model umlClassModel = UMLFactoryImpl.eINSTANCE.createModel();
    userExecution.updateUmlClassModelElement(compModel, umlClassModel);
    
    addCorrespondenceBetween(userExecution.getElement1(compModel, umlClassModel), userExecution.getElement2(compModel, umlClassModel), "");
    
    postprocessElements();
  }
}
