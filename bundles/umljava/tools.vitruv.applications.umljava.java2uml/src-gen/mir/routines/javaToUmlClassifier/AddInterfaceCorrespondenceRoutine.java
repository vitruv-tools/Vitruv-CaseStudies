package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddInterfaceCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private AddInterfaceCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface, final CompilationUnit jCompUnit) {
      return uInterface;
    }
    
    public EObject getElement4(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface, final CompilationUnit jCompUnit) {
      return jCompUnit;
    }
    
    public EObject getElement2(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface, final CompilationUnit jCompUnit) {
      return jInterface;
    }
    
    public EObject getElement3(final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface, final CompilationUnit jCompUnit) {
      return uInterface;
    }
  }
  
  public AddInterfaceCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface, final CompilationUnit jCompUnit) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddInterfaceCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.jInterface = jInterface;this.uInterface = uInterface;this.jCompUnit = jCompUnit;
  }
  
  private Interface jInterface;
  
  private org.eclipse.uml2.uml.Interface uInterface;
  
  private CompilationUnit jCompUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddInterfaceCorrespondenceRoutine with input:");
    getLogger().debug("   jInterface: " + this.jInterface);
    getLogger().debug("   uInterface: " + this.uInterface);
    getLogger().debug("   jCompUnit: " + this.jCompUnit);
    
    addCorrespondenceBetween(userExecution.getElement1(jInterface, uInterface, jCompUnit), userExecution.getElement2(jInterface, uInterface, jCompUnit), "");
    
    addCorrespondenceBetween(userExecution.getElement3(jInterface, uInterface, jCompUnit), userExecution.getElement4(jInterface, uInterface, jCompUnit), "");
    
    postprocessElements();
    
    return true;
  }
}
