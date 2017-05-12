package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlElementToRootModelRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddUmlElementToRootModelRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final PackageableElement uPackageable, final EObject persistedObject, @Extension final RoutinesFacade _routinesFacade) {
      final Model uModel = JavaToUmlHelper.getUmlModel(this.correspondenceModel, this.userInteracting);
    }
  }
  
  public AddUmlElementToRootModelRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PackageableElement uPackageable, final EObject persistedObject) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlElementToRootModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.uPackageable = uPackageable;this.persistedObject = persistedObject;
  }
  
  private PackageableElement uPackageable;
  
  private EObject persistedObject;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlElementToRootModelRoutine with input:");
    getLogger().debug("   PackageableElement: " + this.uPackageable);
    getLogger().debug("   EObject: " + this.persistedObject);
    
    userExecution.callRoutine1(uPackageable, persistedObject, actionsFacade);
    
    postprocessElements();
  }
}
