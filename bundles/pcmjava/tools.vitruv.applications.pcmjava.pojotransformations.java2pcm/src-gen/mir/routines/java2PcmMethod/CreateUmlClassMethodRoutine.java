package mir.routines.java2PcmMethod;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Method;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlClassMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlClassMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class cls, @Extension final RoutinesFacade _routinesFacade) {
      final Function1<Interface, Boolean> _function = (Interface x) -> {
        return Boolean.valueOf(Java2PcmHelper.hasCorrespondance(x, this.correspondenceModel));
      };
      final Function1<Interface, EList<Method>> _function_1 = (Interface x) -> {
        return x.getMethods();
      };
      final Function1<Method, Boolean> _function_2 = (Method it) -> {
        return Boolean.valueOf(Java2PcmHelper.sameSignature(it, classMethod));
      };
      final Iterable<Method> methods = IterableExtensions.<Method>filter(Iterables.<Method>concat(IterableExtensions.<Interface, EList<Method>>map(IterableExtensions.<Interface>filter(Java2PcmHelper.findImplementingInterfacesFromTypeRefs(cls.getImplements()), _function), _function_1)), _function_2);
      for (final Method method : methods) {
        _routinesFacade.createSEFF(method, cls, classMethod);
      }
    }
  }
  
  public CreateUmlClassMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class cls) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateUmlClassMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.classMethod = classMethod;this.cls = cls;
  }
  
  private ClassMethod classMethod;
  
  private org.emftext.language.java.classifiers.Class cls;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlClassMethodRoutine with input:");
    getLogger().debug("   ClassMethod: " + this.classMethod);
    getLogger().debug("   Class: " + this.cls);
    
    userExecution.callRoutine1(classMethod, cls, actionsFacade);
    
    postprocessElements();
  }
}
