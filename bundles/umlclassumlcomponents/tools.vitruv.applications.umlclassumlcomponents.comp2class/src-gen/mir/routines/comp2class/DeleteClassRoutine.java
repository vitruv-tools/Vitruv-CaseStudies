package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Component umlComp, final String packageName, final String expectedTag, final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public String getRetrieveTag1(final Component umlComp, final String packageName, final String expectedTag) {
      return expectedTag;
    }
    
    public EObject getCorrepondenceSourceUmlClass(final Component umlComp, final String packageName, final String expectedTag) {
      return umlComp;
    }
  }
  
  public DeleteClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComp, final String packageName, final String expectedTag) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.DeleteClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.umlComp = umlComp;this.packageName = packageName;this.expectedTag = expectedTag;
  }
  
  private Component umlComp;
  
  private String packageName;
  
  private String expectedTag;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteClassRoutine with input:");
    getLogger().debug("   Component: " + this.umlComp);
    getLogger().debug("   String: " + this.packageName);
    getLogger().debug("   String: " + this.expectedTag);
    
    org.eclipse.uml2.uml.Class umlClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlClass(umlComp, packageName, expectedTag), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlComp, packageName, expectedTag));
    if (umlClass == null) {
    	return;
    }
    registerObjectUnderModification(umlClass);
    deleteObject(userExecution.getElement1(umlComp, packageName, expectedTag, umlClass));
    
    postprocessElements();
  }
}
