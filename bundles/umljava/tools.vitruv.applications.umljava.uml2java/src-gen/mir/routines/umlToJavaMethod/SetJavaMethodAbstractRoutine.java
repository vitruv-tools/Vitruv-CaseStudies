package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.modifiers.Abstract;
import org.emftext.language.java.modifiers.impl.ModifiersFactoryImpl;
import tools.vitruv.applications.umljava.util.JavaUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetJavaMethodAbstractRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetJavaMethodAbstractRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOp, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod javaMethod, final Abstract abstr) {
      return javaMethod;
    }
    
    public void update0Element(final Operation umlOp, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod javaMethod, final Abstract abstr) {
      JavaUtil.setJavaModifier(javaMethod, abstr, umlOp.isAbstract());
    }
    
    public EObject getCorrepondenceSourceJavaClass(final Operation umlOp) {
      org.eclipse.uml2.uml.Class _class_ = umlOp.getClass_();
      return _class_;
    }
    
    public EObject getCorrepondenceSourceJavaMethod(final Operation umlOp, final org.emftext.language.java.classifiers.Class javaClass) {
      return umlOp;
    }
  }
  
  public SetJavaMethodAbstractRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.SetJavaMethodAbstractRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.umlOp = umlOp;
  }
  
  private Operation umlOp;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetJavaMethodAbstractRoutine with input:");
    getLogger().debug("   Operation: " + this.umlOp);
    
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClass(umlOp), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (javaClass == null) {
    	return;
    }
    registerObjectUnderModification(javaClass);
    ClassMethod javaMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaMethod(umlOp, javaClass), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    if (javaMethod == null) {
    	return;
    }
    registerObjectUnderModification(javaMethod);
    Abstract abstr = ModifiersFactoryImpl.eINSTANCE.createAbstract();
    
    // val updatedElement userExecution.getElement1(umlOp, javaClass, javaMethod, abstr);
    userExecution.update0Element(umlOp, javaClass, javaMethod, abstr);
    
    postprocessElements();
  }
}
