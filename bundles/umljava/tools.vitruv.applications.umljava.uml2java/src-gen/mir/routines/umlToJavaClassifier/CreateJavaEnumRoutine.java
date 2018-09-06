package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaEnumRoutine extends AbstractRepairRoutineRealization {
  private CreateJavaEnumRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateJEnumElement(final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum) {
      jEnum.setName(uEnum.getName());
      jEnum.makePublic();
    }
    
    public EObject getElement1(final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum) {
      return uEnum;
    }
    
    public EObject getCorrepondenceSource1(final Enumeration uEnum) {
      return uEnum;
    }
    
    public EObject getElement2(final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum) {
      return jEnum;
    }
    
    public void callRoutine1(final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaCompilationUnit(uEnum, jEnum, uEnum.getNamespace());
    }
  }
  
  public CreateJavaEnumRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Enumeration uEnum) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.CreateJavaEnumRoutine.ActionUserExecution(getExecutionState(), this);
    this.uEnum = uEnum;
  }
  
  private Enumeration uEnum;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaEnumRoutine with input:");
    getLogger().debug("   uEnum: " + this.uEnum);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(uEnum), // correspondence source supplier
    	org.emftext.language.java.classifiers.Enumeration.class,
    	(org.emftext.language.java.classifiers.Enumeration _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    org.emftext.language.java.classifiers.Enumeration jEnum = org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl.eINSTANCE.createEnumeration();
    notifyObjectCreated(jEnum);
    userExecution.updateJEnumElement(uEnum, jEnum);
    
    userExecution.callRoutine1(uEnum, jEnum, this.getRoutinesFacade());
    
    addCorrespondenceBetween(userExecution.getElement1(uEnum, jEnum), userExecution.getElement2(uEnum, jEnum), "");
    
    postprocessElements();
    
    return true;
  }
}
