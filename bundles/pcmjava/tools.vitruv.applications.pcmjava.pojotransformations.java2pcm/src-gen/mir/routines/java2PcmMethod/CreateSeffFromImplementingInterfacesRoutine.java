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
  private CreateSeffFromImplementingInterfacesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass, @Extension final RoutinesFacade _routinesFacade) {
      final ArrayList<Interface> implementingInterfaces = Java2PcmHelper.findImplementingInterfacesFromTypeRefs(javaClass.getImplements());
      for (final Interface implementingInterface : implementingInterfaces) {
        _routinesFacade.createSeffFromImplementingInterface(classMethod, javaClass, implementingInterface);
      }
    }
  }
  
  public CreateSeffFromImplementingInterfacesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfacesRoutine.ActionUserExecution(getExecutionState(), this);
    this.classMethod = classMethod;this.javaClass = javaClass;
  }
  
  private ClassMethod classMethod;
  
  private org.emftext.language.java.classifiers.Class javaClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSeffFromImplementingInterfacesRoutine with input:");
    getLogger().debug("   classMethod: " + this.classMethod);
    getLogger().debug("   javaClass: " + this.javaClass);
    
    userExecution.callRoutine1(classMethod, javaClass, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
