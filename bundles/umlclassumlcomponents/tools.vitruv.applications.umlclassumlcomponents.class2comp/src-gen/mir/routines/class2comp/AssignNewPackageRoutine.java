package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AssignNewPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AssignNewPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package classPackage, final Component umlComponent) {
      return umlComponent;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Package classPackage, final Component umlComponent) {
      umlComponent.setPackage(classPackage);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package classPackage, final Component umlComponent) {
      return umlComponent;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Package classPackage, final Component umlComponent) {
      return classPackage;
    }
  }
  
  public AssignNewPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package classPackage, final Component umlComponent) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.AssignNewPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classPackage = classPackage;this.umlComponent = umlComponent;
  }
  
  private org.eclipse.uml2.uml.Package classPackage;
  
  private Component umlComponent;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AssignNewPackageRoutine with input:");
    getLogger().debug("   Package: " + this.classPackage);
    getLogger().debug("   Component: " + this.umlComponent);
    
    // val updatedElement userExecution.getElement1(classPackage, umlComponent);
    userExecution.update0Element(classPackage, umlComponent);
    
    addCorrespondenceBetween(userExecution.getElement2(classPackage, umlComponent), userExecution.getElement3(classPackage, umlComponent), "");
    
    postprocessElements();
  }
}
