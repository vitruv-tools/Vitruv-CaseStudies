package mir.routines.umlXpcmRepository_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRepository_R2L.RoutinesFacade;
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
    
    public EObject getCorrepondenceSource1(final Repository repository_) {
      return repository_;
    }
    
    public EObject getCorrepondenceSource2(final Repository repository_) {
      return repository_;
    }
    
    public String getRetrieveTag1(final Repository repository_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:repositoryPkg_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSource3(final Repository repository_) {
      return repository_;
    }
    
    public String getRetrieveTag2(final Repository repository_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:contractsPkg_with_Repository:repository";
    }
    
    public String getRetrieveTag3(final Repository repository_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:datatypesPkg_with_Repository:repository";
    }
    
    public String getTag1(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:repositoryPkg_with_Repository:repository";
    }
    
    public void callRoutine2(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.repository_BidirectionalUpdate(repository_);
    }
    
    public EObject getElement6(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return repository_;
    }
    
    public String getTag3(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:datatypesPkg_with_Repository:repository";
    }
    
    public String getTag2(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:contractsPkg_with_Repository:repository";
    }
    
    public void callRoutine1(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createUmlModelRoot(repository_, repositoryPkg_);
    }
    
    public EObject getElement1(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return repositoryPkg_;
    }
    
    public void executeAction1(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, @Extension final RoutinesFacade _routinesFacade) {
      repositoryPkg_.getPackagedElements().add(contractsPkg_);
      repositoryPkg_.getPackagedElements().add(datatypesPkg_);
      contractsPkg_.setName("contracts");
      datatypesPkg_.setName("datatypes");
    }
    
    public EObject getElement4(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return repository_;
    }
    
    public EObject getElement5(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return datatypesPkg_;
    }
    
    public EObject getElement2(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return repository_;
    }
    
    public EObject getElement3(final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
      return contractsPkg_;
    }
  }
  
  public Repository_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_R2L.Repository_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.repository_ = repository_;
  }
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Repository_CreateMappingRoutine with input:");
    getLogger().debug("   repository_: " + this.repository_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(repository_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(repository_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource3(repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(repository_)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Package repositoryPkg_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(repositoryPkg_);
    
    org.eclipse.uml2.uml.Package contractsPkg_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(contractsPkg_);
    
    org.eclipse.uml2.uml.Package datatypesPkg_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(datatypesPkg_);
    
    addCorrespondenceBetween(userExecution.getElement1(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_), userExecution.getElement2(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_), userExecution.getTag1(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_));
    
    addCorrespondenceBetween(userExecution.getElement3(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_), userExecution.getElement4(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_), userExecution.getTag2(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_));
    
    addCorrespondenceBetween(userExecution.getElement5(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_), userExecution.getElement6(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_), userExecution.getTag3(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_));
    
    userExecution.executeAction1(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_, this.getRoutinesFacade());
    
    userExecution.callRoutine2(repository_, repositoryPkg_, contractsPkg_, datatypesPkg_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
