package mir.routines.umlRepositoryComponentPackageReactions;

import java.io.IOException;
import mir.routines.umlRepositoryComponentPackageReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeNameOfCorrespondingRepositoryComponentRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingRepositoryComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package umlPkg, final String newName, final RepositoryComponent pcmComponent) {
      return pcmComponent;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Package umlPkg, final String newName, final RepositoryComponent pcmComponent) {
      String _firstUpper = null;
      if (newName!=null) {
        _firstUpper=StringExtensions.toFirstUpper(newName);
      }
      pcmComponent.setEntityName(_firstUpper);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final org.eclipse.uml2.uml.Package umlPkg, final String newName) {
      return umlPkg;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package umlPkg, final String newName) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
  }
  
  public ChangeNameOfCorrespondingRepositoryComponentRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPkg, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryComponentPackageReactions.ChangeNameOfCorrespondingRepositoryComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPkg = umlPkg;this.newName = newName;
  }
  
  private org.eclipse.uml2.uml.Package umlPkg;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingRepositoryComponentRoutine with input:");
    getLogger().debug("   umlPkg: " + this.umlPkg);
    getLogger().debug("   newName: " + this.newName);
    
    org.palladiosimulator.pcm.repository.RepositoryComponent pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlPkg, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlPkg, newName), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    // val updatedElement userExecution.getElement1(umlPkg, newName, pcmComponent);
    userExecution.update0Element(umlPkg, newName, pcmComponent);
    
    postprocessElements();
    
    return true;
  }
}
