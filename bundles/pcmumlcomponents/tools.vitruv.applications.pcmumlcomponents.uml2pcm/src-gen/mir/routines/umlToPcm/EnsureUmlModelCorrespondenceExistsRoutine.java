package mir.routines.umlToPcm;

import java.io.IOException;
import java.util.List;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class EnsureUmlModelCorrespondenceExistsRoutine extends AbstractRepairRoutineRealization {
  private EnsureUmlModelCorrespondenceExistsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceAlreadyCorrespondingModels(final Model newModel) {
      return UMLPackage.Literals.MODEL;
    }
    
    public void executeAction1(final Model newModel, final List<Model> alreadyCorrespondingModels, @Extension final RoutinesFacade _routinesFacade) {
      boolean _contains = alreadyCorrespondingModels.contains(newModel);
      boolean _not = (!_contains);
      if (_not) {
        _routinesFacade.addUmlModelCorrespondence(newModel);
      }
    }
  }
  
  public EnsureUmlModelCorrespondenceExistsRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model newModel) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.EnsureUmlModelCorrespondenceExistsRoutine.ActionUserExecution(getExecutionState(), this);
    this.newModel = newModel;
  }
  
  private Model newModel;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine EnsureUmlModelCorrespondenceExistsRoutine with input:");
    getLogger().debug("   newModel: " + this.newModel);
    
    List<org.eclipse.uml2.uml.Model> alreadyCorrespondingModels = getCorrespondingElements(
    	userExecution.getCorrepondenceSourceAlreadyCorrespondingModels(newModel), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null
    );
    for (EObject _element : alreadyCorrespondingModels) {	
    	registerObjectUnderModification(_element);
    }
    userExecution.executeAction1(newModel, alreadyCorrespondingModels, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
