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
public class RenameComponentPackageAndClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameComponentPackageAndClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public boolean getCorrespondingModelElementsPreconditionRepositoryPackage(final RepositoryComponent component, final org.emftext.language.java.containers.Package repositoryPackage) {
      boolean _equals = repositoryPackage.getName().equals(component.getRepository__RepositoryComponent().getEntityName());
      return _equals;
    }
    
    public EObject getCorrepondenceSourceRepositoryPackage(final RepositoryComponent component) {
      Repository _repository__RepositoryComponent = component.getRepository__RepositoryComponent();
      return _repository__RepositoryComponent;
    }
    
    public void callRoutine1(final RepositoryComponent component, final org.emftext.language.java.containers.Package repositoryPackage, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.renameJavaPackage(component, repositoryPackage, component.getEntityName(), null);
      _routinesFacade.renameComponentClass(component);
    }
  }
  
  public RenameComponentPackageAndClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.RenameComponentPackageAndClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(getExecutionState(), this);
    this.component = component;
  }
  
  private RepositoryComponent component;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameComponentPackageAndClassRoutine with input:");
    getLogger().debug("   component: " + this.component);
    
    org.emftext.language.java.containers.Package repositoryPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepositoryPackage(component), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> userExecution.getCorrespondingModelElementsPreconditionRepositoryPackage(component, _element), // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (repositoryPackage == null) {
    	return false;
    }
    registerObjectUnderModification(repositoryPackage);
    userExecution.callRoutine1(component, repositoryPackage, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
