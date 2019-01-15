package mir.routines.pcmRepositoryReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcmRepositoryReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeNameOfCorrespondingRepositoryPackageRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingRepositoryPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlRepositoryPkg(final Repository pcmRepo) {
      return pcmRepo;
    }
    
    public EObject getElement1(final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
      return umlRepositoryPkg;
    }
    
    public void update0Element(final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
      String _name = umlRepositoryPkg.getName();
      String _firstLower = StringExtensions.toFirstLower(pcmRepo.getEntityName());
      boolean _notEquals = (!Objects.equal(_name, _firstLower));
      if (_notEquals) {
        umlRepositoryPkg.setName(StringExtensions.toFirstLower(pcmRepo.getEntityName()));
      }
    }
    
    public String getRetrieveTag1(final Repository pcmRepo) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
  }
  
  public ChangeNameOfCorrespondingRepositoryPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository pcmRepo) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryReactions.ChangeNameOfCorrespondingRepositoryPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRepo = pcmRepo;
  }
  
  private Repository pcmRepo;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingRepositoryPackageRoutine with input:");
    getLogger().debug("   pcmRepo: " + this.pcmRepo);
    
    org.eclipse.uml2.uml.Package umlRepositoryPkg = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRepositoryPkg(pcmRepo), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmRepo), 
    	true // asserted
    	);
    if (umlRepositoryPkg == null) {
    	return false;
    }
    registerObjectUnderModification(umlRepositoryPkg);
    // val updatedElement userExecution.getElement1(pcmRepo, umlRepositoryPkg);
    userExecution.update0Element(pcmRepo, umlRepositoryPkg);
    
    postprocessElements();
    
    return true;
  }
}
