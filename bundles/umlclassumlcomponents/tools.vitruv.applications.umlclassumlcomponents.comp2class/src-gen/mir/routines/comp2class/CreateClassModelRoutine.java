package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
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
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateClassModelRoutine with input:");
    getLogger().debug("   compModel: " + this.compModel);
    
    org.eclipse.uml2.uml.Model umlClassModel = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createModel();
    notifyObjectCreated(umlClassModel);
    userExecution.updateUmlClassModelElement(compModel, umlClassModel);
    
    addCorrespondenceBetween(userExecution.getElement1(compModel, umlClassModel), userExecution.getElement2(compModel, umlClassModel), "");
    
    postprocessElements();
    
    return true;
  }
}
