package mir.routines.pcmRepositoryReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmRepositoryReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InitializePcmRepositoryUmlPackagesCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private InitializePcmRepositoryUmlPackagesCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlRepositoryPkg(final Repository pcmRepo) {
      return pcmRepo;
    }
    
    public EObject getCorrepondenceSourceUmlContractsPkg(final Repository pcmRepo, final Optional<org.eclipse.uml2.uml.Package> umlRepositoryPkg) {
      return pcmRepo;
    }
    
    public String getRetrieveTag1(final Repository pcmRepo) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public String getRetrieveTag2(final Repository pcmRepo, final Optional<org.eclipse.uml2.uml.Package> umlRepositoryPkg) {
      return TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE;
    }
    
    public String getRetrieveTag3(final Repository pcmRepo, final Optional<org.eclipse.uml2.uml.Package> umlRepositoryPkg, final Optional<org.eclipse.uml2.uml.Package> umlContractsPkg) {
      return TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlDatatypesPkg(final Repository pcmRepo, final Optional<org.eclipse.uml2.uml.Package> umlRepositoryPkg, final Optional<org.eclipse.uml2.uml.Package> umlContractsPkg) {
      return pcmRepo;
    }
    
    public void callRoutine1(final Repository pcmRepo, final Optional<org.eclipse.uml2.uml.Package> umlRepositoryPkg, final Optional<org.eclipse.uml2.uml.Package> umlContractsPkg, final Optional<org.eclipse.uml2.uml.Package> umlDatatypesPkg, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlRepositoryPkg.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        _routinesFacade.createUmlRepositoryPackage(pcmRepo);
      }
      boolean _isPresent_1 = umlContractsPkg.isPresent();
      boolean _not_1 = (!_isPresent_1);
      if (_not_1) {
        _routinesFacade.createUmlContractsPackage(pcmRepo);
      }
      boolean _isPresent_2 = umlDatatypesPkg.isPresent();
      boolean _not_2 = (!_isPresent_2);
      if (_not_2) {
        _routinesFacade.createUmlDatatypesPackage(pcmRepo);
      }
      _routinesFacade.bootstrapPrimitiveDatatypes(pcmRepo);
    }
  }
  
  public InitializePcmRepositoryUmlPackagesCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository pcmRepo) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryReactions.InitializePcmRepositoryUmlPackagesCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRepo = pcmRepo;
  }
  
  private Repository pcmRepo;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InitializePcmRepositoryUmlPackagesCorrespondenceRoutine with input:");
    getLogger().debug("   pcmRepo: " + this.pcmRepo);
    
    	Optional<org.eclipse.uml2.uml.Package> umlRepositoryPkg = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlRepositoryPkg(pcmRepo), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(pcmRepo), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlRepositoryPkg.isPresent() ? umlRepositoryPkg.get() : null);
    	Optional<org.eclipse.uml2.uml.Package> umlContractsPkg = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlContractsPkg(pcmRepo, umlRepositoryPkg), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmRepo, umlRepositoryPkg), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlContractsPkg.isPresent() ? umlContractsPkg.get() : null);
    	Optional<org.eclipse.uml2.uml.Package> umlDatatypesPkg = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlDatatypesPkg(pcmRepo, umlRepositoryPkg, umlContractsPkg), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmRepo, umlRepositoryPkg, umlContractsPkg), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlDatatypesPkg.isPresent() ? umlDatatypesPkg.get() : null);
    userExecution.callRoutine1(pcmRepo, umlRepositoryPkg, umlContractsPkg, umlDatatypesPkg, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
