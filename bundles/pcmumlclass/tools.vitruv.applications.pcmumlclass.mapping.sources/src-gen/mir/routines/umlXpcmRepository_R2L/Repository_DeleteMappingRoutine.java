package mir.routines.umlXpcmRepository_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRepository_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Repository_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private Repository_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return repositoryPkg_;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return contractsPkg_;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return datatypesPkg_;
    }
  }
  
  public Repository_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_R2L.Repository_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.repositoryPkg_ = repositoryPkg_;this.contractsPkg_ = contractsPkg_;this.datatypesPkg_ = datatypesPkg_;
  }
  
  private org.eclipse.uml2.uml.Package repositoryPkg_;
  
  private org.eclipse.uml2.uml.Package contractsPkg_;
  
  private org.eclipse.uml2.uml.Package datatypesPkg_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Repository_DeleteMappingRoutine with input:");
    getLogger().debug("   repositoryPkg_: " + this.repositoryPkg_);
    getLogger().debug("   contractsPkg_: " + this.contractsPkg_);
    getLogger().debug("   datatypesPkg_: " + this.datatypesPkg_);
    
    deleteObject(userExecution.getElement1(repositoryPkg_, contractsPkg_, datatypesPkg_));
    
    deleteObject(userExecution.getElement2(repositoryPkg_, contractsPkg_, datatypesPkg_));
    
    deleteObject(userExecution.getElement3(repositoryPkg_, contractsPkg_, datatypesPkg_));
    
    postprocessElements();
    
    return true;
  }
}
