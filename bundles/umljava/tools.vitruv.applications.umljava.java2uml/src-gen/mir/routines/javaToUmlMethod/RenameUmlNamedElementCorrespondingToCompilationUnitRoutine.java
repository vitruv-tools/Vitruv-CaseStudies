package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameUmlNamedElementCorrespondingToCompilationUnitRoutine extends AbstractRepairRoutineRealization {
  private RenameUmlNamedElementCorrespondingToCompilationUnitRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUElement(final CompilationUnit jElement) {
      return jElement;
    }
    
    public EObject getElement1(final CompilationUnit jElement, final NamedElement uElement) {
      return uElement;
    }
    
    public void update0Element(final CompilationUnit jElement, final NamedElement uElement) {
      String propagatedName = jElement.getName();
      String _join = IterableExtensions.join(jElement.getNamespaces(), ".");
      final String namespacePrefix = (_join + ".");
      propagatedName = propagatedName.replaceFirst(namespacePrefix, "");
      propagatedName = propagatedName.replaceFirst(".java", "");
      uElement.setName(propagatedName);
    }
  }
  
  public RenameUmlNamedElementCorrespondingToCompilationUnitRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompilationUnit jElement) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.RenameUmlNamedElementCorrespondingToCompilationUnitRoutine.ActionUserExecution(getExecutionState(), this);
    this.jElement = jElement;
  }
  
  private CompilationUnit jElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameUmlNamedElementCorrespondingToCompilationUnitRoutine with input:");
    getLogger().debug("   jElement: " + this.jElement);
    
    org.eclipse.uml2.uml.NamedElement uElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUElement(jElement), // correspondence source supplier
    	org.eclipse.uml2.uml.NamedElement.class,
    	(org.eclipse.uml2.uml.NamedElement _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uElement == null) {
    	return false;
    }
    registerObjectUnderModification(uElement);
    // val updatedElement userExecution.getElement1(jElement, uElement);
    userExecution.update0Element(jElement, uElement);
    
    postprocessElements();
    
    return true;
  }
}
