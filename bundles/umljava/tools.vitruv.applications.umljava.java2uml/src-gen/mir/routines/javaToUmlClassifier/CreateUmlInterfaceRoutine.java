package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface) {
      return uInterface;
    }
    
    public EObject getElement4(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface) {
      EObject _eContainer = jInterface.eContainer();
      return _eContainer;
    }
    
    public EObject getElement2(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface) {
      return jInterface;
    }
    
    public EObject getElement3(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface) {
      return uInterface;
    }
    
    public void updateUInterfaceElement(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface) {
      uInterface.setName(jInterface.getName());
      uInterface.setVisibility(VisibilityKind.PUBLIC_LITERAL);
    }
    
    public void callRoutine1(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addUmlElementToRootModel(uInterface, jInterface);
    }
  }
  
  public CreateUmlInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface jInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.CreateUmlInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jInterface = jInterface;
  }
  
  private Interface jInterface;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.jInterface);
    
    org.eclipse.uml2.uml.Interface uInterface = UMLFactoryImpl.eINSTANCE.createInterface();
    userExecution.updateUInterfaceElement(jInterface, uInterface);
    
    userExecution.callRoutine1(jInterface, uInterface, actionsFacade);
    
    addCorrespondenceBetween(userExecution.getElement1(jInterface, uInterface), userExecution.getElement2(jInterface, uInterface), "");
    
    addCorrespondenceBetween(userExecution.getElement3(jInterface, uInterface), userExecution.getElement4(jInterface, uInterface), "");
    
    postprocessElements();
  }
}
