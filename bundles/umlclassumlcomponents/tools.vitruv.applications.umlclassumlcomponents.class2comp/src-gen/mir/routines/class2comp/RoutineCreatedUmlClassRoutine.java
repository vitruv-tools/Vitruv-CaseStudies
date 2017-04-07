package mir.routines.class2comp;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umlclassumlcomponents.class2comp.class2compUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutineCreatedUmlClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RoutineCreatedUmlClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public boolean checkMatcherPrecondition1(final org.eclipse.uml2.uml.Class umlClass) {
      String _name = umlClass.getName();
      String _plus = ("Create corresponding component for created class \'" + _name);
      final String msg = (_plus + "\'?");
      return class2compUtil.modalTextYesNoUserInteracting(this.userInteracting, msg);
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class umlClass, @Extension final RoutinesFacade _routinesFacade) {
      String _name = umlClass.getName();
      String _plus = ("Should \'" + _name);
      final String msg = (_plus + "\' be represented by a DataType?");
      boolean _modalTextYesNoUserInteracting = class2compUtil.modalTextYesNoUserInteracting(this.userInteracting, msg);
      if (_modalTextYesNoUserInteracting) {
        _routinesFacade.createDataTypeForClass(umlClass);
      } else {
        if (((umlClass.getPackage() == null) || Objects.equal(umlClass.getPackage().getName(), "default"))) {
          _routinesFacade.createUmlComponentAndPackage(umlClass);
        } else {
          _routinesFacade.createUmlComponent(umlClass);
        }
      }
    }
  }
  
  public RoutineCreatedUmlClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RoutineCreatedUmlClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RoutineCreatedUmlClassRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    
    if (!userExecution.checkMatcherPrecondition1(umlClass)) {
    	return;
    }
    userExecution.callRoutine1(umlClass, actionsFacade);
    
    postprocessElements();
  }
}
