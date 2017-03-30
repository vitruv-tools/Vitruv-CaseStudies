package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
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
    
    public EObject getElement1(final NamedElement compElement, final NamedElement classElement) {
      return classElement;
    }
    
    public void update0Element(final NamedElement compElement, final NamedElement classElement) {
      String _name = compElement.getName();
      classElement.setName(_name);
    }
    
    public EObject getCorrepondenceSourceClassElement(final NamedElement compElement) {
      return compElement;
    }
  }
  
  public RenameElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement compElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.RenameElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compElement = compElement;
  }
  
  private NamedElement compElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameElementRoutine with input:");
    getLogger().debug("   NamedElement: " + this.compElement);
    
    NamedElement classElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassElement(compElement), // correspondence source supplier
    	NamedElement.class,
    	(NamedElement _element) -> true, // correspondence precondition checker
    	null);
    if (classElement == null) {
    	return;
    }
    registerObjectUnderModification(classElement);
    // val updatedElement userExecution.getElement1(compElement, classElement);
    userExecution.update0Element(compElement, classElement);
    
    postprocessElements();
  }
}
