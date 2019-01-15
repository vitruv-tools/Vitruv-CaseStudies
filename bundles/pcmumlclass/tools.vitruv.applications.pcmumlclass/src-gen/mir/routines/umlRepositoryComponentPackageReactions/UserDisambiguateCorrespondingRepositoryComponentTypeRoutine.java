package mir.routines.umlRepositoryComponentPackageReactions;

import java.io.IOException;
import mir.routines.umlRepositoryComponentPackageReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UserDisambiguateCorrespondingRepositoryComponentTypeRoutine extends AbstractRepairRoutineRealization {
  private UserDisambiguateCorrespondingRepositoryComponentTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, @Extension final RoutinesFacade _routinesFacade) {
      final Integer componentType = this.userInteractor.getSingleSelectionDialogBuilder().message(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__REQUEST).choices(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__OPTIONS).startInteraction();
      if (componentType != null) {
        switch (componentType) {
          case DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT:
            _routinesFacade.createCorrespondingBasicComponent(umlPkg, umlParentPkg);
            break;
          case DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__COMPOSITE_COMPONENT:
            _routinesFacade.createCorrespondingCompositeComponent(umlPkg, umlParentPkg);
            break;
          case DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__SUB_SYSTEM:
            _routinesFacade.createCorrespondingSubSystem(umlPkg, umlParentPkg);
            break;
          default:
            return;
        }
      } else {
        return;
      }
    }
  }
  
  public UserDisambiguateCorrespondingRepositoryComponentTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryComponentPackageReactions.UserDisambiguateCorrespondingRepositoryComponentTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPkg = umlPkg;this.umlParentPkg = umlParentPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlPkg;
  
  private org.eclipse.uml2.uml.Package umlParentPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UserDisambiguateCorrespondingRepositoryComponentTypeRoutine with input:");
    getLogger().debug("   umlPkg: " + this.umlPkg);
    getLogger().debug("   umlParentPkg: " + this.umlParentPkg);
    
    userExecution.executeAction1(umlPkg, umlParentPkg, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
