package mir.routines.umlXpcmDatatypes_R2L;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDataype_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private CompositeDataype_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
      return class_;
    }
  }
  
  public CompositeDataype_DeleteMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.class_ = class_;this.datatypesPackage_ = datatypesPackage_;
  }
  
  private org.eclipse.uml2.uml.Class class_;
  
  private org.eclipse.uml2.uml.Package datatypesPackage_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDataype_DeleteMappingRoutine with input:");
    getLogger().debug("   class_: " + this.class_);
    getLogger().debug("   datatypesPackage_: " + this.datatypesPackage_);
    
    deleteObject(userExecution.getElement1(class_, datatypesPackage_));
    
    postprocessElements();
    
    return true;
  }
}
