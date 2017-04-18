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
    
    public EObject getElement1(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterfacenterface) {
      return uInterfacenterface;
    }
    
    public EObject getElement4(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterfacenterface) {
      EObject _eContainer = jInterface.eContainer();
      return _eContainer;
    }
    
    public EObject getElement2(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterfacenterface) {
      return jInterface;
    }
    
    public EObject getElement3(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterfacenterface) {
      return uInterfacenterface;
    }
    
    public void updateUInterfacenterfaceElement(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterfacenterface) {
      uInterfacenterface.setName(jInterface.getName());
      uInterfacenterface.setVisibility(VisibilityKind.PUBLIC_LITERAL);
    }
    
    public void callRoutine1(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterfacenterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addUmlElementToRootModel(uInterfacenterface, jInterface);
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
    
    org.eclipse.uml2.uml.Interface uInterfacenterface = UMLFactoryImpl.eINSTANCE.createInterface();
    userExecution.updateUInterfacenterfaceElement(jInterface, uInterfacenterface);
    
    userExecution.callRoutine1(jInterface, uInterfacenterface, actionsFacade);
    
    addCorrespondenceBetween(userExecution.getElement1(jInterface, uInterfacenterface), userExecution.getElement2(jInterface, uInterfacenterface), "");
    
    addCorrespondenceBetween(userExecution.getElement3(jInterface, uInterfacenterface), userExecution.getElement4(jInterface, uInterfacenterface), "");
    
    postprocessElements();
  }
}
