package mir.routines.umlXpcmRepository_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRepository_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Repository_CreateMappingRoutine extends AbstractRepairRoutineRealization {
  private Repository_CreateMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return repositoryPkg_;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:repositoryPkg_with_Repository:repository";
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, final Repository repository_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:repositoryPkg_with_Repository:repository";
    }
    
    public void callRoutine2(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, final Repository repository_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.repository_BidirectionalUpdate(repositoryPkg_, contractsPkg_, datatypesPkg_);
    }
    
    public EObject getElement6(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, final Repository repository_) {
      return datatypesPkg_;
    }
    
    public String getTag3(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, final Repository repository_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:datatypesPkg_with_Repository:repository";
    }
    
    public String getTag2(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, final Repository repository_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:contractsPkg_with_Repository:repository";
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, final Repository repository_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createRepositoryRoot(repositoryPkg_, repository_);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, final Repository repository_) {
      return repository_;
    }
    
    public EObject getElement4(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, final Repository repository_) {
      return contractsPkg_;
    }
    
    public EObject getElement5(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, final Repository repository_) {
      return repository_;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, final Repository repository_) {
      return repositoryPkg_;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, final Repository repository_) {
      return repository_;
    }
  }
  
  public Repository_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_L2R.Repository_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.repositoryPkg_ = repositoryPkg_;this.contractsPkg_ = contractsPkg_;this.datatypesPkg_ = datatypesPkg_;
  }
  
  private org.eclipse.uml2.uml.Package repositoryPkg_;
  
  private org.eclipse.uml2.uml.Package contractsPkg_;
  
  private org.eclipse.uml2.uml.Package datatypesPkg_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Repository_CreateMappingRoutine with input:");
    getLogger().debug("   repositoryPkg_: " + this.repositoryPkg_);
    getLogger().debug("   contractsPkg_: " + this.contractsPkg_);
    getLogger().debug("   datatypesPkg_: " + this.datatypesPkg_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(repositoryPkg_, contractsPkg_, datatypesPkg_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(repositoryPkg_, contractsPkg_, datatypesPkg_)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.Repository repository_ = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createRepository();
    notifyObjectCreated(repository_);
    
    addCorrespondenceBetween(userExecution.getElement1(repositoryPkg_, contractsPkg_, datatypesPkg_, repository_), userExecution.getElement2(repositoryPkg_, contractsPkg_, datatypesPkg_, repository_), userExecution.getTag1(repositoryPkg_, contractsPkg_, datatypesPkg_, repository_));
    
    addCorrespondenceBetween(userExecution.getElement3(repositoryPkg_, contractsPkg_, datatypesPkg_, repository_), userExecution.getElement4(repositoryPkg_, contractsPkg_, datatypesPkg_, repository_), userExecution.getTag2(repositoryPkg_, contractsPkg_, datatypesPkg_, repository_));
    
    addCorrespondenceBetween(userExecution.getElement5(repositoryPkg_, contractsPkg_, datatypesPkg_, repository_), userExecution.getElement6(repositoryPkg_, contractsPkg_, datatypesPkg_, repository_), userExecution.getTag3(repositoryPkg_, contractsPkg_, datatypesPkg_, repository_));
    
    userExecution.callRoutine1(repositoryPkg_, contractsPkg_, datatypesPkg_, repository_, this.getRoutinesFacade());
    
    userExecution.callRoutine2(repositoryPkg_, contractsPkg_, datatypesPkg_, repository_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
