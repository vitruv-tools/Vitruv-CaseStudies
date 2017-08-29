package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface javaInterface) {
      return umlInterface;
    }
    
    public void updateJavaInterfaceElement(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface javaInterface) {
      javaInterface.setName(umlInterface.getName());
      javaInterface.makePublic();
    }
    
    public EObject getElement2(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface javaInterface) {
      return javaInterface;
    }
    
    public void callRoutine1(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface javaInterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaCompilationUnit(umlInterface, javaInterface, umlInterface.getNamespace());
    }
  }
  
  public CreateJavaInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.CreateJavaInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.umlInterface = umlInterface;
  }
  
  private Interface umlInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaInterfaceRoutine with input:");
    getLogger().debug("   umlInterface: " + this.umlInterface);
    
    org.emftext.language.java.classifiers.Interface javaInterface = org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl.eINSTANCE.createInterface();
    notifyObjectCreated(javaInterface);
    userExecution.updateJavaInterfaceElement(umlInterface, javaInterface);
    
    addCorrespondenceBetween(userExecution.getElement1(umlInterface, javaInterface), userExecution.getElement2(umlInterface, javaInterface), "");
    
    userExecution.callRoutine1(umlInterface, javaInterface, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
