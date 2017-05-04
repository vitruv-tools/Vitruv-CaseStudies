package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateComponentModelRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateComponentModelRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Model umlClassModel, final Model compModel) {
      return umlClassModel;
    }
    
    public EObject getElement2(final Model umlClassModel, final Model compModel) {
      return compModel;
    }
    
    public void updateCompModelElement(final Model umlClassModel, final Model compModel) {
      compModel.setName(umlClassModel.getName());
      String _name = umlClassModel.getName();
      String _plus = (SharedUtil.FOLDER_NAME + _name);
      String _plus_1 = (_plus + ".");
      String _plus_2 = (_plus_1 + SharedUtil.MODEL_FILE_EXTENSION);
      this.persistProjectRelative(umlClassModel, compModel, _plus_2);
    }
  }
  
  public CreateComponentModelRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model umlClassModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateComponentModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClassModel = umlClassModel;
  }
  
  private Model umlClassModel;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateComponentModelRoutine with input:");
    getLogger().debug("   Model: " + this.umlClassModel);
    
    Model compModel = UMLFactoryImpl.eINSTANCE.createModel();
    userExecution.updateCompModelElement(umlClassModel, compModel);
    
    addCorrespondenceBetween(userExecution.getElement1(umlClassModel, compModel), userExecution.getElement2(umlClassModel, compModel), "");
    
    postprocessElements();
  }
}
