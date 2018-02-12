package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateComponentImplementationRoutine extends AbstractRepairRoutineRealization {
  private CreateComponentImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final RepositoryComponent component) {
      return "repository_root";
    }
    
    public EObject getCorrepondenceSourceRepositoryPackage(final RepositoryComponent component) {
      Repository _repository__RepositoryComponent = component.getRepository__RepositoryComponent();
      return _repository__RepositoryComponent;
    }
    
    public void callRoutine1(final RepositoryComponent component, final org.emftext.language.java.containers.Package repositoryPackage, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaPackage(component, repositoryPackage, component.getEntityName(), null);
      _routinesFacade.createImplementationForComponent(component);
    }
  }
  
  public CreateComponentImplementationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.CreateComponentImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.component = component;
  }
  
  private RepositoryComponent component;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateComponentImplementationRoutine with input:");
    getLogger().debug("   component: " + this.component);
    
    org.emftext.language.java.containers.Package repositoryPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepositoryPackage(component), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(component), 
    	false // asserted
    	);
    if (repositoryPackage == null) {
    	return false;
    }
    registerObjectUnderModification(repositoryPackage);
    userExecution.callRoutine1(component, repositoryPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
