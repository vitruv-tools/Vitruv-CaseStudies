package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement classElement, final NamedElement compElement) {
      return compElement;
    }
    
    public void update0Element(final NamedElement classElement, final NamedElement compElement) {
      String _name = classElement.getName();
      compElement.setName(_name);
    }
    
    public EObject getCorrepondenceSourceCompElement(final NamedElement classElement) {
      return classElement;
    }
  }
  
  public RenameElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement classElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RenameElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classElement = classElement;
  }
  
  private NamedElement classElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameElementRoutine with input:");
    getLogger().debug("   NamedElement: " + this.classElement);
    
    NamedElement compElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompElement(classElement), // correspondence source supplier
    	NamedElement.class,
    	(NamedElement _element) -> true, // correspondence precondition checker
    	null);
    if (compElement == null) {
    	return;
    }
    registerObjectUnderModification(compElement);
    // val updatedElement userExecution.getElement1(classElement, compElement);
    userExecution.update0Element(classElement, compElement);
    
    postprocessElements();
  }
}
