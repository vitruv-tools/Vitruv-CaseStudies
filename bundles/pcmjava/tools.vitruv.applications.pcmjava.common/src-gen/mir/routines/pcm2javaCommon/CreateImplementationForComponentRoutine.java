package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateImplementationForComponentRoutine extends AbstractRepairRoutineRealization {
  private CreateImplementationForComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceComponentPackage(final RepositoryComponent component) {
      return component;
    }
    
    public void callRoutine1(final RepositoryComponent component, final org.emftext.language.java.containers.Package componentPackage, @Extension final RoutinesFacade _routinesFacade) {
      String _entityName = component.getEntityName();
      String _plus = (_entityName + "Impl");
      _routinesFacade.createJavaClass(component, componentPackage, _plus);
    }
  }
  
  public CreateImplementationForComponentRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.CreateImplementationForComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.component = component;
  }
  
  private RepositoryComponent component;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateImplementationForComponentRoutine with input:");
    getLogger().debug("   component: " + this.component);
    
    org.emftext.language.java.containers.Package componentPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComponentPackage(component), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (componentPackage == null) {
    	return false;
    }
    registerObjectUnderModification(componentPackage);
    userExecution.callRoutine1(component, componentPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
