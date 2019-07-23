package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class PotentiallyMoveClassRoutine extends AbstractRepairRoutineRealization {
  private PotentiallyMoveClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlClass(final CompilationUnit compUnit) {
      return compUnit;
    }
    
    public void callRoutine1(final CompilationUnit compUnit, final org.eclipse.uml2.uml.Class umlClass, @Extension final RoutinesFacade _routinesFacade) {
      org.eclipse.uml2.uml.Package _package = umlClass.getPackage();
      boolean _tripleNotEquals = (_package != null);
      if (_tripleNotEquals) {
        EList<PackageableElement> _packagedElements = umlClass.getPackage().getPackagedElements();
        _packagedElements.remove(umlClass);
      }
      _routinesFacade.addUmlElementToModelOrPackage(compUnit, umlClass);
    }
  }
  
  public PotentiallyMoveClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompilationUnit compUnit) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.PotentiallyMoveClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.compUnit = compUnit;
  }
  
  private CompilationUnit compUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PotentiallyMoveClassRoutine with input:");
    getLogger().debug("   compUnit: " + this.compUnit);
    
    org.eclipse.uml2.uml.Class umlClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlClass(compUnit), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlClass == null) {
    	return false;
    }
    registerObjectUnderModification(umlClass);
    userExecution.callRoutine1(compUnit, umlClass, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
