package mir.routines.pcmRepositoryReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcmRepositoryReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlDatatypesPackageRoutine extends AbstractRepairRoutineRealization {
  private CreateUmlDatatypesPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlRepositoryPkg(final Repository pcmRepo) {
      return pcmRepo;
    }
    
    public EObject getCorrepondenceSource1(final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
      return pcmRepo;
    }
    
    public String getRetrieveTag1(final Repository pcmRepo) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public String getRetrieveTag2(final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
      return TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE;
    }
    
    public void callRoutine1(final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlRepositoryPkg, @Extension final RoutinesFacade _routinesFacade) {
      final Function1<org.eclipse.uml2.uml.Package, Boolean> _function = (org.eclipse.uml2.uml.Package pkg) -> {
        String _name = pkg.getName();
        return Boolean.valueOf(Objects.equal(_name, DefaultLiterals.DATATYPES_PACKAGE_NAME));
      };
      org.eclipse.uml2.uml.Package umlDatatypesPkg = IterableExtensions.<org.eclipse.uml2.uml.Package>findFirst(umlRepositoryPkg.getNestedPackages(), _function);
      if ((umlDatatypesPkg == null)) {
        umlDatatypesPkg = umlRepositoryPkg.createNestedPackage(DefaultLiterals.DATATYPES_PACKAGE_NAME);
      }
      ReactionsCorrespondenceHelper.addCorrespondence(this.correspondenceModel, pcmRepo, umlDatatypesPkg, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE);
    }
  }
  
  public CreateUmlDatatypesPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository pcmRepo) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryReactions.CreateUmlDatatypesPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRepo = pcmRepo;
  }
  
  private Repository pcmRepo;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlDatatypesPackageRoutine with input:");
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
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmRepo, umlRepositoryPkg), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmRepo, umlRepositoryPkg)
    ).isEmpty()) {
    	return false;
    }
    userExecution.callRoutine1(pcmRepo, umlRepositoryPkg, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
