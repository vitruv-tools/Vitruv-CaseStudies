package mir.routines.umlXpcmComponent_R2L;

import java.io.IOException;
import mir.routines.umlXpcmComponent_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
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
    
    public String getTag1(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_RepositoryComponent:component";
    }
    
    public String getTag3(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_RepositoryComponent:component";
    }
    
    public String getTag2(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:repositoryPackage_with_RepositoryComponent:component";
    }
    
    public void callRoutine1(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.repositoryComponent_BidirectionalUpdate(component_, repository_);
    }
    
    public String getTag8(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Operation:constructor_with_Repository:repository";
    }
    
    public void executeAction1(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, @Extension final RoutinesFacade _routinesFacade) {
      repositoryPackage_.getPackagedElements().add(componentPackage_);
      componentPackage_.getPackagedElements().add(implementation_);
      implementation_.getOwnedOperations().add(constructor_);
    }
    
    public String getTag5(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_Repository:repository";
    }
    
    public String getTag4(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Operation:constructor_with_RepositoryComponent:component";
    }
    
    public String getTag7(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_Repository:repository";
    }
    
    public String getTag6(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:repositoryPackage_with_Repository:repository";
    }
    
    public String getRetrieveTag4(final RepositoryComponent component_, final Repository repository_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Operation:constructor_with_RepositoryComponent:component";
    }
    
    public String getRetrieveTag5(final RepositoryComponent component_, final Repository repository_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:repositoryPkg_with_Repository:repository";
    }
    
    public EObject getElement10(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return repository_;
    }
    
    public EObject getElement11(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return repositoryPackage_;
    }
    
    public EObject getCorrepondenceSource1(final RepositoryComponent component_, final Repository repository_) {
      return component_;
    }
    
    public EObject getCorrepondenceSourceRepositoryPackage_(final RepositoryComponent component_, final Repository repository_) {
      return repository_;
    }
    
    public EObject getElement12(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return repository_;
    }
    
    public EObject getCorrepondenceSource2(final RepositoryComponent component_, final Repository repository_) {
      return component_;
    }
    
    public String getRetrieveTag1(final RepositoryComponent component_, final Repository repository_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_RepositoryComponent:component";
    }
    
    public EObject getCorrepondenceSource3(final RepositoryComponent component_, final Repository repository_) {
      return component_;
    }
    
    public String getRetrieveTag2(final RepositoryComponent component_, final Repository repository_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:repositoryPackage_with_RepositoryComponent:component";
    }
    
    public EObject getCorrepondenceSource4(final RepositoryComponent component_, final Repository repository_) {
      return component_;
    }
    
    public String getRetrieveTag3(final RepositoryComponent component_, final Repository repository_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_RepositoryComponent:component";
    }
    
    public EObject getElement8(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return component_;
    }
    
    public EObject getElement9(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return componentPackage_;
    }
    
    public EObject getElement6(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return component_;
    }
    
    public EObject getElement7(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return constructor_;
    }
    
    public EObject getElement13(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return implementation_;
    }
    
    public EObject getElement14(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return repository_;
    }
    
    public EObject getElement15(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return constructor_;
    }
    
    public EObject getElement16(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return repository_;
    }
    
    public EObject getElement1(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return componentPackage_;
    }
    
    public EObject getElement4(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return component_;
    }
    
    public EObject getElement5(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return implementation_;
    }
    
    public EObject getElement2(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return component_;
    }
    
    public EObject getElement3(final RepositoryComponent component_, final Repository repository_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
      return repositoryPackage_;
    }
  }
  
  public RepositoryComponent_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component_, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_R2L.RepositoryComponent_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.component_ = component_;this.repository_ = repository_;
  }
  
  private RepositoryComponent component_;
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RepositoryComponent_CreateMappingRoutine with input:");
    getLogger().debug("   component_: " + this.component_);
    getLogger().debug("   repository_: " + this.repository_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(component_, repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(component_, repository_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(component_, repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(component_, repository_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource3(component_, repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(component_, repository_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource4(component_, repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag4(component_, repository_)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Package repositoryPackage_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepositoryPackage_(component_, repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag5(component_, repository_), 
    	false // asserted
    	);
    if (repositoryPackage_ == null) {
    	return false;
    }
    registerObjectUnderModification(repositoryPackage_);
    org.eclipse.uml2.uml.Package componentPackage_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(componentPackage_);
    
    org.eclipse.uml2.uml.Class implementation_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createClass();
    notifyObjectCreated(implementation_);
    
    org.eclipse.uml2.uml.Operation constructor_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createOperation();
    notifyObjectCreated(constructor_);
    
    addCorrespondenceBetween(userExecution.getElement1(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getElement2(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getTag1(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_));
    
    addCorrespondenceBetween(userExecution.getElement3(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getElement4(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getTag2(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_));
    
    addCorrespondenceBetween(userExecution.getElement5(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getElement6(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getTag3(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_));
    
    addCorrespondenceBetween(userExecution.getElement7(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getElement8(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getTag4(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_));
    
    addCorrespondenceBetween(userExecution.getElement9(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getElement10(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getTag5(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_));
    
    addCorrespondenceBetween(userExecution.getElement11(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getElement12(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getTag6(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_));
    
    addCorrespondenceBetween(userExecution.getElement13(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getElement14(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getTag7(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_));
    
    addCorrespondenceBetween(userExecution.getElement15(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getElement16(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_), userExecution.getTag8(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_));
    
    userExecution.executeAction1(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(component_, repository_, repositoryPackage_, componentPackage_, implementation_, constructor_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
