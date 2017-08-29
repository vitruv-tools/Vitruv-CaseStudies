package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlElementToModelOrPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddUmlElementToModelOrPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final CompilationUnit jCompUnit, final Classifier uClassifier, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(jCompUnit.getNamespaces());
      if (_isNullOrEmpty) {
        _routinesFacade.addUmlElementToPackage(uClassifier, JavaToUmlHelper.getUmlModel(this.changePropagationObservable, this.correspondenceModel, this.userInteracting), jCompUnit);
      } else {
        _routinesFacade.addUmlElementToPackage(uClassifier, JavaToUmlHelper.findUmlPackage(this.correspondenceModel, IterableExtensions.<String>last(jCompUnit.getNamespaces())), jCompUnit);
      }
    }
  }
  
  public AddUmlElementToModelOrPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompilationUnit jCompUnit, final Classifier uClassifier) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlElementToModelOrPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jCompUnit = jCompUnit;this.uClassifier = uClassifier;
  }
  
  private CompilationUnit jCompUnit;
  
  private Classifier uClassifier;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlElementToModelOrPackageRoutine with input:");
    getLogger().debug("   jCompUnit: " + this.jCompUnit);
    getLogger().debug("   uClassifier: " + this.uClassifier);
    
    userExecution.callRoutine1(jCompUnit, uClassifier, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
