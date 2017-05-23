package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.modifiers.Abstract;
import org.emftext.language.java.modifiers.impl.ModifiersFactoryImpl;
import tools.vitruv.applications.umljava.util.JavaUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetJavaClassAbstractRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetJavaClassAbstractRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class jClass, final Abstract abstr) {
      return jClass;
    }
    
    public EObject getCorrepondenceSourceJClass(final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class jClass, final Abstract abstr) {
      JavaUtil.setJavaModifier(jClass, abstr, umlClass.isAbstract());
    }
  }
  
  public SetJavaClassAbstractRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.SetJavaClassAbstractRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetJavaClassAbstractRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    
    org.emftext.language.java.classifiers.Class jClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClass(umlClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (jClass == null) {
    	return;
    }
    registerObjectUnderModification(jClass);
    Abstract abstr = ModifiersFactoryImpl.eINSTANCE.createAbstract();
    
    // val updatedElement userExecution.getElement1(umlClass, jClass, abstr);
    userExecution.update0Element(umlClass, jClass, abstr);
    
    postprocessElements();
  }
}