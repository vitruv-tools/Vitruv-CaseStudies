package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlElementToModelOrPackageRoutine extends AbstractRepairRoutineRealization {
  private AddUmlElementToModelOrPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUModel(final CompilationUnit jCompUnit, final Classifier uClassifier) {
      return UMLPackage.Literals.MODEL;
    }
    
    public void callRoutine1(final CompilationUnit jCompUnit, final Classifier uClassifier, final Model uModel, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(jCompUnit.getNamespaces());
      if (_isNullOrEmpty) {
        _routinesFacade.addUmlElementToPackage(uClassifier, uModel);
      } else {
        _routinesFacade.addUmlElementToPackage(uClassifier, JavaToUmlHelper.findUmlPackage(uModel, IterableExtensions.<String>last(jCompUnit.getNamespaces())));
      }
    }
  }
  
  public AddUmlElementToModelOrPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompilationUnit jCompUnit, final Classifier uClassifier) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlElementToModelOrPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.jCompUnit = jCompUnit;this.uClassifier = uClassifier;
  }
  
  private CompilationUnit jCompUnit;
  
  private Classifier uClassifier;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlElementToModelOrPackageRoutine with input:");
    getLogger().debug("   jCompUnit: " + this.jCompUnit);
    getLogger().debug("   uClassifier: " + this.uClassifier);
    
    org.eclipse.uml2.uml.Model uModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUModel(jCompUnit, uClassifier), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uModel == null) {
    	return false;
    }
    registerObjectUnderModification(uModel);
    userExecution.callRoutine1(jCompUnit, uClassifier, uModel, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
