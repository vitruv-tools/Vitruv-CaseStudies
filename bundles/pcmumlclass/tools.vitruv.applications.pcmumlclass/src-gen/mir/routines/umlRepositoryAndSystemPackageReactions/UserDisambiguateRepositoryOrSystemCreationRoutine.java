package mir.routines.umlRepositoryAndSystemPackageReactions;

import java.io.IOException;
import mir.routines.umlRepositoryAndSystemPackageReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionOptions;

@SuppressWarnings("all")
public class UserDisambiguateRepositoryOrSystemCreationRoutine extends AbstractRepairRoutineRealization {
  private UserDisambiguateRepositoryOrSystemCreationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, @Extension final RoutinesFacade _routinesFacade) {
      final Integer pcmElementType = this.userInteractor.getSingleSelectionDialogBuilder().message(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REQUEST).choices(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__OPTIONS).windowModality(UserInteractionOptions.WindowModality.MODAL).startInteraction();
      if (pcmElementType != null) {
        switch (pcmElementType) {
          case DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY:
            _routinesFacade.createCorrespondingRepository(umlPkg, umlParentPkg);
            break;
          case DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__SYSTEM:
            _routinesFacade.createCorrespondingSystem(umlPkg, umlParentPkg);
            break;
          default:
            return;
        }
      } else {
        return;
      }
    }
  }
  
  public UserDisambiguateRepositoryOrSystemCreationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryAndSystemPackageReactions.UserDisambiguateRepositoryOrSystemCreationRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPkg = umlPkg;this.umlParentPkg = umlParentPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlPkg;
  
  private org.eclipse.uml2.uml.Package umlParentPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UserDisambiguateRepositoryOrSystemCreationRoutine with input:");
    getLogger().debug("   umlPkg: " + this.umlPkg);
    getLogger().debug("   umlParentPkg: " + this.umlParentPkg);
    
    userExecution.executeAction1(umlPkg, umlParentPkg, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
