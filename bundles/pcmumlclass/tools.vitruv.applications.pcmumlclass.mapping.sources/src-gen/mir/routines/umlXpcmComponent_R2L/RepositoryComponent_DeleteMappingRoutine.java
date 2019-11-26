package mir.routines.umlXpcmComponent_R2L;

import java.io.IOException;
import mir.routines.umlXpcmComponent_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RepositoryComponent_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private RepositoryComponent_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return componentPackage_;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return implementation_;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return constructor_;
    }
  }
  
  public RepositoryComponent_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_R2L.RepositoryComponent_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.componentPackage_ = componentPackage_;this.repositoryPackage_ = repositoryPackage_;this.implementation_ = implementation_;this.constructor_ = constructor_;
  }
  
  private org.eclipse.uml2.uml.Package componentPackage_;
  
  private org.eclipse.uml2.uml.Package repositoryPackage_;
  
  private org.eclipse.uml2.uml.Class implementation_;
  
  private Operation constructor_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RepositoryComponent_DeleteMappingRoutine with input:");
    getLogger().debug("   componentPackage_: " + this.componentPackage_);
    getLogger().debug("   repositoryPackage_: " + this.repositoryPackage_);
    getLogger().debug("   implementation_: " + this.implementation_);
    getLogger().debug("   constructor_: " + this.constructor_);
    
    deleteObject(userExecution.getElement1(componentPackage_, repositoryPackage_, implementation_, constructor_));
    
    deleteObject(userExecution.getElement2(componentPackage_, repositoryPackage_, implementation_, constructor_));
    
    deleteObject(userExecution.getElement3(componentPackage_, repositoryPackage_, implementation_, constructor_));
    
    postprocessElements();
    
    return true;
  }
}
