package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlInterfaceRoutine extends AbstractRepairRoutineRealization {
  private CreateUmlInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateUInterfaceElement(final Interface jInterface, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Interface uInterface) {
      uInterface.setName(jInterface.getName());
    }
    
    public void callRoutine1(final Interface jInterface, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Interface uInterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addInterfaceCorrespondence(jInterface, uInterface, jCompUnit);
      _routinesFacade.addUmlElementToModelOrPackage(jCompUnit, uInterface);
    }
  }
  
  public CreateUmlInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface jInterface, final CompilationUnit jCompUnit) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.CreateUmlInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.jInterface = jInterface;this.jCompUnit = jCompUnit;
  }
  
  private Interface jInterface;
  
  private CompilationUnit jCompUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlInterfaceRoutine with input:");
    getLogger().debug("   jInterface: " + this.jInterface);
    getLogger().debug("   jCompUnit: " + this.jCompUnit);
    
    org.eclipse.uml2.uml.Interface uInterface = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createInterface();
    notifyObjectCreated(uInterface);
    userExecution.updateUInterfaceElement(jInterface, jCompUnit, uInterface);
    
    userExecution.callRoutine1(jInterface, jCompUnit, uInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
