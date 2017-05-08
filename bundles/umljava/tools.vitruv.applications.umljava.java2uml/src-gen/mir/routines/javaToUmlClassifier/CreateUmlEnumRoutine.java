package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Enumeration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlEnumRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlEnumRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Enumeration jEnum, final org.eclipse.uml2.uml.Enumeration uEnum) {
      return uEnum;
    }
    
    public void updateUEnumElement(final Enumeration jEnum, final org.eclipse.uml2.uml.Enumeration uEnum) {
      uEnum.setName(jEnum.getName());
    }
    
    public EObject getElement4(final Enumeration jEnum, final org.eclipse.uml2.uml.Enumeration uEnum) {
      EObject _eContainer = jEnum.eContainer();
      return _eContainer;
    }
    
    public EObject getElement2(final Enumeration jEnum, final org.eclipse.uml2.uml.Enumeration uEnum) {
      return jEnum;
    }
    
    public EObject getElement3(final Enumeration jEnum, final org.eclipse.uml2.uml.Enumeration uEnum) {
      return uEnum;
    }
    
    public void callRoutine1(final Enumeration jEnum, final org.eclipse.uml2.uml.Enumeration uEnum, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addUmlElementToRootModel(uEnum, jEnum);
    }
  }
  
  public CreateUmlEnumRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Enumeration jEnum) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.CreateUmlEnumRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jEnum = jEnum;
  }
  
  private Enumeration jEnum;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlEnumRoutine with input:");
    getLogger().debug("   Enumeration: " + this.jEnum);
    
    org.eclipse.uml2.uml.Enumeration uEnum = UMLFactoryImpl.eINSTANCE.createEnumeration();
    userExecution.updateUEnumElement(jEnum, uEnum);
    
    userExecution.callRoutine1(jEnum, uEnum, actionsFacade);
    
    addCorrespondenceBetween(userExecution.getElement1(jEnum, uEnum), userExecution.getElement2(jEnum, uEnum), "");
    
    addCorrespondenceBetween(userExecution.getElement3(jEnum, uEnum), userExecution.getElement4(jEnum, uEnum), "");
    
    postprocessElements();
  }
}
