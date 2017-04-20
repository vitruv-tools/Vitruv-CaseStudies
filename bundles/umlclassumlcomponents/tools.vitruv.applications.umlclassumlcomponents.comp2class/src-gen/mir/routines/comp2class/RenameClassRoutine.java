package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage) {
      return umlClass;
    }
    
    public void update0Element(final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage) {
      umlClass.setName(umlComp.getName());
    }
    
    public EObject getCorrepondenceSourceClassPackage(final Component umlComp, final org.eclipse.uml2.uml.Class umlClass) {
      return umlComp;
    }
    
    public EObject getCorrepondenceSourceUmlClass(final Component umlComp) {
      return umlComp;
    }
    
    public boolean getCorrespondingModelElementsPreconditionUmlClass(final Component umlComp, final org.eclipse.uml2.uml.Class umlClass) {
      boolean _equals = umlClass.getName().equals(umlComp.getName());
      return _equals;
    }
  }
  
  public RenameClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.RenameClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.umlComp = umlComp;
  }
  
  private Component umlComp;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameClassRoutine with input:");
    getLogger().debug("   Component: " + this.umlComp);
    
    org.eclipse.uml2.uml.Class umlClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlClass(umlComp), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> userExecution.getCorrespondingModelElementsPreconditionUmlClass(umlComp, _element), // correspondence precondition checker
    	null);
    if (umlClass == null) {
    	return;
    }
    registerObjectUnderModification(umlClass);
    org.eclipse.uml2.uml.Package classPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassPackage(umlComp, umlClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(classPackage);
    // val updatedElement userExecution.getElement1(umlComp, umlClass, classPackage);
    userExecution.update0Element(umlComp, umlClass, classPackage);
    
    postprocessElements();
  }
}
