package mir.routines.umlXpcmDatatypes_L2R;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateCompositeDataTypeNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateCompositeDataTypeNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateCompositeDataTypeNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.UpdateCompositeDataTypeNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.class_ = class_;this.datatypesPackage = datatypesPackage;
  }
  
  private org.eclipse.uml2.uml.Class class_;
  
  private org.eclipse.uml2.uml.Package datatypesPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateCompositeDataTypeNameRoutine with input:");
    getLogger().debug("   class_: " + this.class_);
    getLogger().debug("   datatypesPackage: " + this.datatypesPackage);
    
    userExecution.executeAction1(class_, datatypesPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
