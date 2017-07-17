package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreateArchitecturalElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateArchitecturalElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package javaPackage, @Extension final RoutinesFacade _routinesFacade) {
      final String userMsg = "A package has been created. Please decide whether and which corresponding architectural element should be created";
      final String[] selections = { "Create basic component", "Create composite component", "Create system", "Do nothing/Decide later" };
      final int selected = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, userMsg, selections);
      switch (selected) {
        case 0:
          _routinesFacade.createBasicComponent(javaPackage);
          break;
        case 1:
          _routinesFacade.createCompositeComponent(javaPackage);
          break;
        case 2:
          _routinesFacade.createSystem(javaPackage);
          break;
      }
    }
  }
  
  public CreateArchitecturalElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateArchitecturalElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateArchitecturalElementRoutine with input:");
    getLogger().debug("   Package: " + this.javaPackage);
    
    userExecution.callRoutine1(javaPackage, actionsFacade);
    
    postprocessElements();
  }
}
