package mir.routines.umlXpcmComponent_L2R;

import java.io.IOException;
import mir.routines.umlXpcmComponent_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RepositoryComponent_CreateMappingRoutine extends AbstractRepairRoutineRealization {
  private RepositoryComponent_CreateMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_RepositoryComponent:component";
    }
    
    public String getTag3(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:repositoryPackage_with_RepositoryComponent:component";
    }
    
    public String getTag2(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_Repository:repository";
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.repositoryComponent_BidirectionalUpdate(componentPackage_, repositoryPackage_, implementation_, constructor_);
    }
    
    public String getTag8(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Operation:constructor_with_Repository:repository";
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_, @Extension final RoutinesFacade _routinesFacade) {
      repository_.getComponents__Repository().add(component_);
    }
    
    public String getTag5(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_RepositoryComponent:component";
    }
    
    public String getTag4(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:repositoryPackage_with_Repository:repository";
    }
    
    public String getTag7(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Operation:constructor_with_RepositoryComponent:component";
    }
    
    public String getTag6(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_Repository:repository";
    }
    
    public EObject getElement10(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return implementation_;
    }
    
    public EObject getElement11(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return repository_;
    }
    
    public EObject getCorrepondenceSource1(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return componentPackage_;
    }
    
    public EObject getElement12(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return implementation_;
    }
    
    public EObject getCorrepondenceSource2(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return componentPackage_;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_RepositoryComponent:component";
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_Repository:repository";
    }
    
    public String getRetrieveTag3(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:repositoryPkg_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceRepository_(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return repositoryPackage_;
    }
    
    public EObject getElement8(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return repositoryPackage_;
    }
    
    public EObject getElement9(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return component_;
    }
    
    public EObject getElement6(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return repositoryPackage_;
    }
    
    public EObject getElement7(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return repository_;
    }
    
    public EObject getElement13(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return component_;
    }
    
    public EObject getElement14(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return constructor_;
    }
    
    public EObject getElement15(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return repository_;
    }
    
    public EObject getElement16(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return constructor_;
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return component_;
    }
    
    public EObject getElement4(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return componentPackage_;
    }
    
    public EObject getElement5(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return component_;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return componentPackage_;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, final Repository repository_, final BasicComponent component_) {
      return repository_;
    }
  }
  
  public RepositoryComponent_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_L2R.RepositoryComponent_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.componentPackage_ = componentPackage_;this.repositoryPackage_ = repositoryPackage_;this.implementation_ = implementation_;this.constructor_ = constructor_;
  }
  
  private org.eclipse.uml2.uml.Package componentPackage_;
  
  private org.eclipse.uml2.uml.Package repositoryPackage_;
  
  private org.eclipse.uml2.uml.Class implementation_;
  
  private Operation constructor_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RepositoryComponent_CreateMappingRoutine with input:");
    getLogger().debug("   componentPackage_: " + this.componentPackage_);
    getLogger().debug("   repositoryPackage_: " + this.repositoryPackage_);
    getLogger().debug("   implementation_: " + this.implementation_);
    getLogger().debug("   constructor_: " + this.constructor_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(componentPackage_, repositoryPackage_, implementation_, constructor_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(componentPackage_, repositoryPackage_, implementation_, constructor_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(componentPackage_, repositoryPackage_, implementation_, constructor_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(componentPackage_, repositoryPackage_, implementation_, constructor_)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.Repository repository_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepository_(componentPackage_, repositoryPackage_, implementation_, constructor_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(componentPackage_, repositoryPackage_, implementation_, constructor_), 
    	false // asserted
    	);
    if (repository_ == null) {
    	return false;
    }
    registerObjectUnderModification(repository_);
    org.palladiosimulator.pcm.repository.BasicComponent component_ = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createBasicComponent();
    notifyObjectCreated(component_);
    
    addCorrespondenceBetween(userExecution.getElement1(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getElement2(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getTag1(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_));
    
    addCorrespondenceBetween(userExecution.getElement3(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getElement4(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getTag2(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_));
    
    addCorrespondenceBetween(userExecution.getElement5(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getElement6(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getTag3(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_));
    
    addCorrespondenceBetween(userExecution.getElement7(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getElement8(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getTag4(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_));
    
    addCorrespondenceBetween(userExecution.getElement9(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getElement10(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getTag5(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_));
    
    addCorrespondenceBetween(userExecution.getElement11(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getElement12(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getTag6(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_));
    
    addCorrespondenceBetween(userExecution.getElement13(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getElement14(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getTag7(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_));
    
    addCorrespondenceBetween(userExecution.getElement15(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getElement16(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_), userExecution.getTag8(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_));
    
    userExecution.executeAction1(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(componentPackage_, repositoryPackage_, implementation_, constructor_, repository_, component_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
