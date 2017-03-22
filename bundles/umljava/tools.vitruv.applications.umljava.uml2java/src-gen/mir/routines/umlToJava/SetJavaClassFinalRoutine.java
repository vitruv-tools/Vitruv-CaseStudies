package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.modifiers.Final;
import org.emftext.language.java.modifiers.impl.ModifiersFactoryImpl;
import tools.vitruv.applications.umljava.util.JavaUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetJavaClassFinalRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetJavaClassFinalRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class jClass, final Final finalMod) {
      return jClass;
    }
    
    public EObject getCorrepondenceSourceJClass(final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class jClass, final Final finalMod) {
      boolean _isFinalSpecialization = umlClass.isFinalSpecialization();
      JavaUtil.setJavaModifier(jClass, finalMod, _isFinalSpecialization);
    }
  }
  
  public SetJavaClassFinalRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.SetJavaClassFinalRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetJavaClassFinalRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    
    org.emftext.language.java.classifiers.Class jClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClass(umlClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (jClass == null) {
    	return;
    }
    initializeRetrieveElementState(jClass);
    Final finalMod = ModifiersFactoryImpl.eINSTANCE.createFinal();
    initializeCreateElementState(finalMod);
    
    // val updatedElement userExecution.getElement1(umlClass, jClass, finalMod);
    userExecution.update0Element(umlClass, jClass, finalMod);
    
    postprocessElementStates();
  }
}
