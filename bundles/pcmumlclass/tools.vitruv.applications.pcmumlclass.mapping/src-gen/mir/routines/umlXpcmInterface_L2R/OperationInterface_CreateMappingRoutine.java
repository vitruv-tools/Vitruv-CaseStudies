package mir.routines.umlXpcmInterface_L2R;

import java.io.IOException;
import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterface_CreateMappingRoutine extends AbstractRepairRoutineRealization {
  private OperationInterface_CreateMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
      return interface_;
    }
    
    public EObject getCorrepondenceSource2(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
      return interface_;
    }
    
    public String getRetrieveTag1(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag2(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_Repository:repository";
    }
    
    public String getRetrieveTag3(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:contractsPkg_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceRepository_(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
      return contractsPackage_;
    }
    
    public String getTag1(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public EObject getElement8(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return contractsPackage_;
    }
    
    public EObject getElement6(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return contractsPackage_;
    }
    
    public String getTag3(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Package:contractsPackage_with_OperationInterface:operationInterface";
    }
    
    public String getTag2(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_Repository:repository";
    }
    
    public EObject getElement7(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return repository_;
    }
    
    public void callRoutine1(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.operationInterface_BidirectionalUpdate(interface_, contractsPackage_);
    }
    
    public EObject getElement1(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return operationInterface_;
    }
    
    public void executeAction1(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_, @Extension final RoutinesFacade _routinesFacade) {
      repository_.getInterfaces__Repository().add(operationInterface_);
    }
    
    public EObject getElement4(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return interface_;
    }
    
    public EObject getElement5(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return operationInterface_;
    }
    
    public String getTag4(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Package:contractsPackage_with_Repository:repository";
    }
    
    public EObject getElement2(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return interface_;
    }
    
    public EObject getElement3(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_, final Repository repository_, final OperationInterface operationInterface_) {
      return repository_;
    }
  }
  
  public OperationInterface_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_L2R.OperationInterface_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.interface_ = interface_;this.contractsPackage_ = contractsPackage_;
  }
  
  private Interface interface_;
  
  private org.eclipse.uml2.uml.Package contractsPackage_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterface_CreateMappingRoutine with input:");
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   contractsPackage_: " + this.contractsPackage_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(interface_, contractsPackage_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(interface_, contractsPackage_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(interface_, contractsPackage_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(interface_, contractsPackage_)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.Repository repository_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepository_(interface_, contractsPackage_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(interface_, contractsPackage_), 
    	false // asserted
    	);
    if (repository_ == null) {
    	return false;
    }
    registerObjectUnderModification(repository_);
    org.palladiosimulator.pcm.repository.OperationInterface operationInterface_ = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    notifyObjectCreated(operationInterface_);
    
    addCorrespondenceBetween(userExecution.getElement1(interface_, contractsPackage_, repository_, operationInterface_), userExecution.getElement2(interface_, contractsPackage_, repository_, operationInterface_), userExecution.getTag1(interface_, contractsPackage_, repository_, operationInterface_));
    
    addCorrespondenceBetween(userExecution.getElement3(interface_, contractsPackage_, repository_, operationInterface_), userExecution.getElement4(interface_, contractsPackage_, repository_, operationInterface_), userExecution.getTag2(interface_, contractsPackage_, repository_, operationInterface_));
    
    addCorrespondenceBetween(userExecution.getElement5(interface_, contractsPackage_, repository_, operationInterface_), userExecution.getElement6(interface_, contractsPackage_, repository_, operationInterface_), userExecution.getTag3(interface_, contractsPackage_, repository_, operationInterface_));
    
    addCorrespondenceBetween(userExecution.getElement7(interface_, contractsPackage_, repository_, operationInterface_), userExecution.getElement8(interface_, contractsPackage_, repository_, operationInterface_), userExecution.getTag4(interface_, contractsPackage_, repository_, operationInterface_));
    
    userExecution.executeAction1(interface_, contractsPackage_, repository_, operationInterface_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(interface_, contractsPackage_, repository_, operationInterface_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
