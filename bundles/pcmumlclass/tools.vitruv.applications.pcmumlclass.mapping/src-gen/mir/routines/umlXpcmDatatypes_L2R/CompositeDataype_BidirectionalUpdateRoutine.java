package mir.routines.umlXpcmDatatypes_L2R;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDataype_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private CompositeDataype_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateCompositeDataTypeName(class_, datatypesPackage_);
    }
  }
  
  public CompositeDataype_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.class_ = class_;this.datatypesPackage_ = datatypesPackage_;
  }
  
  private org.eclipse.uml2.uml.Class class_;
  
  private org.eclipse.uml2.uml.Package datatypesPackage_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDataype_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   class_: " + this.class_);
    getLogger().debug("   datatypesPackage_: " + this.datatypesPackage_);
    
    userExecution.callRoutine1(class_, datatypesPackage_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
