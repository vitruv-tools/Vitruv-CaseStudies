package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
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
      EList<String> _namespaces = jCompUnit.getNamespaces();
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(_namespaces);
      if (_isNullOrEmpty) {
        Model _umlModel = JavaToUmlHelper.getUmlModel(this.correspondenceModel, this.userInteracting);
        _routinesFacade.addUmlElementToPackage(uClassifier, _umlModel, jCompUnit);
      } else {
        EList<String> _namespaces_1 = jCompUnit.getNamespaces();
        String _last = IterableExtensions.<String>last(_namespaces_1);
        org.eclipse.uml2.uml.Package _findUmlPackage = JavaToUmlHelper.findUmlPackage(this.correspondenceModel, _last);
        _routinesFacade.addUmlElementToPackage(uClassifier, _findUmlPackage, jCompUnit);
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
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlElementToModelOrPackageRoutine with input:");
    getLogger().debug("   CompilationUnit: " + this.jCompUnit);
    getLogger().debug("   Classifier: " + this.uClassifier);
    
    userExecution.callRoutine1(jCompUnit, uClassifier, actionsFacade);
    
    postprocessElements();
  }
}
