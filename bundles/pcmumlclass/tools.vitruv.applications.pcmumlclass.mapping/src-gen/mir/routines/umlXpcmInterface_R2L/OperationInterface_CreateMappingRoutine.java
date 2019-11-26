package mir.routines.umlXpcmInterface_R2L;

import java.io.IOException;
import mir.routines.umlXpcmInterface_R2L.RoutinesFacade;
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
    
    public EObject getCorrepondenceSource1(final OperationInterface operationInterface_, final Repository repository_) {
      return operationInterface_;
    }
    
    public EObject getCorrepondenceSource2(final OperationInterface operationInterface_, final Repository repository_) {
      return operationInterface_;
    }
    
    public String getRetrieveTag1(final OperationInterface operationInterface_, final Repository repository_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceContractsPackage_(final OperationInterface operationInterface_, final Repository repository_) {
      return repository_;
    }
    
    public String getRetrieveTag2(final OperationInterface operationInterface_, final Repository repository_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Package:contractsPackage_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag3(final OperationInterface operationInterface_, final Repository repository_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:contractsPkg_with_Repository:repository";
    }
    
    public String getTag1(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public EObject getElement8(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return repository_;
    }
    
    public EObject getElement6(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return repository_;
    }
    
    public String getTag3(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_Repository:repository";
    }
    
    public String getTag2(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Package:contractsPackage_with_OperationInterface:operationInterface";
    }
    
    public EObject getElement7(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return contractsPackage_;
    }
    
    public void callRoutine1(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.operationInterface_BidirectionalUpdate(operationInterface_, repository_);
    }
    
    public EObject getElement1(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return interface_;
    }
    
    public void executeAction1(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_, @Extension final RoutinesFacade _routinesFacade) {
      contractsPackage_.getPackagedElements().add(interface_);
    }
    
    public EObject getElement4(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return operationInterface_;
    }
    
    public EObject getElement5(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return interface_;
    }
    
    public String getTag4(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Package:contractsPackage_with_Repository:repository";
    }
    
    public EObject getElement2(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return operationInterface_;
    }
    
    public EObject getElement3(final OperationInterface operationInterface_, final Repository repository_, final org.eclipse.uml2.uml.Package contractsPackage_, final Interface interface_) {
      return contractsPackage_;
    }
  }
  
  public OperationInterface_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface operationInterface_, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_R2L.OperationInterface_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationInterface_ = operationInterface_;this.repository_ = repository_;
  }
  
  private OperationInterface operationInterface_;
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterface_CreateMappingRoutine with input:");
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    getLogger().debug("   repository_: " + this.repository_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(operationInterface_, repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(operationInterface_, repository_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(operationInterface_, repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(operationInterface_, repository_)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Package contractsPackage_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceContractsPackage_(operationInterface_, repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(operationInterface_, repository_), 
    	false // asserted
    	);
    if (contractsPackage_ == null) {
    	return false;
    }
    registerObjectUnderModification(contractsPackage_);
    org.eclipse.uml2.uml.Interface interface_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createInterface();
    notifyObjectCreated(interface_);
    
    addCorrespondenceBetween(userExecution.getElement1(operationInterface_, repository_, contractsPackage_, interface_), userExecution.getElement2(operationInterface_, repository_, contractsPackage_, interface_), userExecution.getTag1(operationInterface_, repository_, contractsPackage_, interface_));
    
    addCorrespondenceBetween(userExecution.getElement3(operationInterface_, repository_, contractsPackage_, interface_), userExecution.getElement4(operationInterface_, repository_, contractsPackage_, interface_), userExecution.getTag2(operationInterface_, repository_, contractsPackage_, interface_));
    
    addCorrespondenceBetween(userExecution.getElement5(operationInterface_, repository_, contractsPackage_, interface_), userExecution.getElement6(operationInterface_, repository_, contractsPackage_, interface_), userExecution.getTag3(operationInterface_, repository_, contractsPackage_, interface_));
    
    addCorrespondenceBetween(userExecution.getElement7(operationInterface_, repository_, contractsPackage_, interface_), userExecution.getElement8(operationInterface_, repository_, contractsPackage_, interface_), userExecution.getTag4(operationInterface_, repository_, contractsPackage_, interface_));
    
    userExecution.executeAction1(operationInterface_, repository_, contractsPackage_, interface_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(operationInterface_, repository_, contractsPackage_, interface_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
