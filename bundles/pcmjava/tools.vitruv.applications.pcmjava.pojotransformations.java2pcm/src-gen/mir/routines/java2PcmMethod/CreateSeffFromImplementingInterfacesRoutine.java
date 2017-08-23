package mir.routines.java2PcmMethod;

import java.io.IOException;
import java.util.ArrayList;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.ClassMethod;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateSeffFromImplementingInterfacesRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateSeffFromImplementingInterfacesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class cls, @Extension final RoutinesFacade _routinesFacade) {
      final ArrayList<Interface> implementingInterfaces = Java2PcmHelper.findImplementingInterfacesFromTypeRefs(cls.getImplements());
      for (final Interface implementingInterface : implementingInterfaces) {
        _routinesFacade.createSeffFromImplementingInterface(classMethod, cls, implementingInterface);
      }
    }
  }
  
  public CreateSeffFromImplementingInterfacesRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class cls) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfacesRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.classMethod = classMethod;this.cls = cls;
  }
  
  private ClassMethod classMethod;
  
  private org.emftext.language.java.classifiers.Class cls;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSeffFromImplementingInterfacesRoutine with input:");
    getLogger().debug("   classMethod: " + this.classMethod);
    getLogger().debug("   cls: " + this.cls);
    
    userExecution.callRoutine1(classMethod, cls, actionsFacade);
    
    postprocessElements();
  }
}
