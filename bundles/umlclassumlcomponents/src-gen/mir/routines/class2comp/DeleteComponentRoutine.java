package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final String packageName, final String expectedTag, final Component component) {
      return component;
    }
    
    public EObject getCorrepondenceSourceComponent(final org.eclipse.uml2.uml.Class umlClass, final String packageName, final String expectedTag) {
      return umlClass;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class umlClass, final String packageName, final String expectedTag) {
      return expectedTag;
    }
  }
  
  public DeleteComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final String packageName, final String expectedTag) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.DeleteComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;this.packageName = packageName;this.expectedTag = expectedTag;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private String packageName;
  
  private String expectedTag;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteComponentRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    getLogger().debug("   String: " + this.packageName);
    getLogger().debug("   String: " + this.expectedTag);
    
    Component component = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComponent(umlClass, packageName, expectedTag), // correspondence source supplier
    	Component.class,
    	(Component _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlClass, packageName, expectedTag));
    if (component == null) {
    	return;
    }
    registerObjectUnderModification(component);
    deleteObject(userExecution.getElement1(umlClass, packageName, expectedTag, component));
    
    postprocessElements();
  }
}
