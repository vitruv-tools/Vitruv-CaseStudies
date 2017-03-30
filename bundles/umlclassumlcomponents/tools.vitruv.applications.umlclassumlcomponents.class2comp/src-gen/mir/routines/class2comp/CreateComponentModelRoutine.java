package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
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
    
    public EObject getElement1(final Model umlClassModel, final Model umlComponentModel) {
      return umlClassModel;
    }
    
    public EObject getElement2(final Model umlClassModel, final Model umlComponentModel) {
      return umlComponentModel;
    }
    
    public void updateUmlComponentModelElement(final Model umlClassModel, final Model umlComponentModel) {
      String _name = umlClassModel.getName();
      umlComponentModel.setName(_name);
      String _name_1 = umlClassModel.getName();
      String _plus = ("model/" + _name_1);
      String _plus_1 = (_plus + ".uml");
      this.persistProjectRelative(umlClassModel, umlComponentModel, _plus_1);
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
    
    Model umlComponentModel = UMLFactoryImpl.eINSTANCE.createModel();
    userExecution.updateUmlComponentModelElement(umlClassModel, umlComponentModel);
    
    addCorrespondenceBetween(userExecution.getElement1(umlClassModel, umlComponentModel), userExecution.getElement2(umlClassModel, umlComponentModel), "");
    
    postprocessElements();
  }
}
