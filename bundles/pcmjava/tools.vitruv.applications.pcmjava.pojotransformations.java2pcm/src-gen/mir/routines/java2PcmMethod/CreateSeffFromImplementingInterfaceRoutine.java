package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateSeffFromImplementingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateSeffFromImplementingInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceOperationInterface(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class cls, final Interface iface) {
      return iface;
    }
    
    public void callRoutine1(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class cls, final Interface iface, final OperationInterface operationInterface, @Extension final RoutinesFacade _routinesFacade) {
      final Function1<Method, Boolean> _function = (Method it) -> {
        return Boolean.valueOf(Java2PcmHelper.sameSignature(it, classMethod));
      };
      final Iterable<Method> methods = IterableExtensions.<Method>filter(iface.getMethods(), _function);
      for (final Method method : methods) {
        _routinesFacade.createSEFF(method, cls, classMethod);
      }
    }
  }
  
  public CreateSeffFromImplementingInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class cls, final Interface iface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.classMethod = classMethod;this.cls = cls;this.iface = iface;
  }
  
  private ClassMethod classMethod;
  
  private org.emftext.language.java.classifiers.Class cls;
  
  private Interface iface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSeffFromImplementingInterfaceRoutine with input:");
    getLogger().debug("   classMethod: " + this.classMethod);
    getLogger().debug("   cls: " + this.cls);
    getLogger().debug("   iface: " + this.iface);
    
    org.palladiosimulator.pcm.repository.OperationInterface operationInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationInterface(classMethod, cls, iface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    if (operationInterface == null) {
    	return false;
    }
    registerObjectUnderModification(operationInterface);
    userExecution.callRoutine1(classMethod, cls, iface, operationInterface, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
