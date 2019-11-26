package mir.routines.umlXpcmInterface_L2R;

import java.io.IOException;
import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterfaceParent_CreateMappingRoutine extends AbstractRepairRoutineRealization {
  private OperationInterfaceParent_CreateMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final Interface interface_, final Generalization generalization_) {
      return interface_;
    }
    
    public EObject getCorrepondenceSource2(final Interface interface_, final Generalization generalization_) {
      return interface_;
    }
    
    public String getRetrieveTag1(final Interface interface_, final Generalization generalization_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag2(final Interface interface_, final Generalization generalization_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:parentInterface";
    }
    
    public String getRetrieveTag3(final Interface interface_, final Generalization generalization_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getTag1(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public EObject getElement8(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return generalization_;
    }
    
    public EObject getElement6(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return generalization_;
    }
    
    public String getTag3(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Generalization:generalization_with_OperationInterface:operationInterface";
    }
    
    public String getTag2(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:parentInterface";
    }
    
    public EObject getElement7(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return parentInterface_;
    }
    
    public EObject getCorrepondenceSourceOperationInterface_(final Interface interface_, final Generalization generalization_) {
      return interface_;
    }
    
    public void callRoutine1(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.operationInterfaceParent_BidirectionalUpdate(interface_, generalization_);
    }
    
    public EObject getElement1(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return operationInterface_;
    }
    
    public void executeAction1(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_, @Extension final RoutinesFacade _routinesFacade) {
      operationInterface_.getParentInterfaces__Interface().add(parentInterface_);
    }
    
    public EObject getElement4(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return interface_;
    }
    
    public EObject getElement5(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return operationInterface_;
    }
    
    public String getTag4(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Generalization:generalization_with_OperationInterface:parentInterface";
    }
    
    public EObject getElement2(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return interface_;
    }
    
    public EObject getElement3(final Interface interface_, final Generalization generalization_, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return parentInterface_;
    }
  }
  
  public OperationInterfaceParent_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface interface_, final Generalization generalization_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.interface_ = interface_;this.generalization_ = generalization_;
  }
  
  private Interface interface_;
  
  private Generalization generalization_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterfaceParent_CreateMappingRoutine with input:");
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   generalization_: " + this.generalization_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(interface_, generalization_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(interface_, generalization_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(interface_, generalization_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(interface_, generalization_)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.OperationInterface operationInterface_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationInterface_(interface_, generalization_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(interface_, generalization_), 
    	false // asserted
    	);
    if (operationInterface_ == null) {
    	return false;
    }
    registerObjectUnderModification(operationInterface_);
    org.palladiosimulator.pcm.repository.OperationInterface parentInterface_ = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    notifyObjectCreated(parentInterface_);
    
    addCorrespondenceBetween(userExecution.getElement1(interface_, generalization_, operationInterface_, parentInterface_), userExecution.getElement2(interface_, generalization_, operationInterface_, parentInterface_), userExecution.getTag1(interface_, generalization_, operationInterface_, parentInterface_));
    
    addCorrespondenceBetween(userExecution.getElement3(interface_, generalization_, operationInterface_, parentInterface_), userExecution.getElement4(interface_, generalization_, operationInterface_, parentInterface_), userExecution.getTag2(interface_, generalization_, operationInterface_, parentInterface_));
    
    addCorrespondenceBetween(userExecution.getElement5(interface_, generalization_, operationInterface_, parentInterface_), userExecution.getElement6(interface_, generalization_, operationInterface_, parentInterface_), userExecution.getTag3(interface_, generalization_, operationInterface_, parentInterface_));
    
    addCorrespondenceBetween(userExecution.getElement7(interface_, generalization_, operationInterface_, parentInterface_), userExecution.getElement8(interface_, generalization_, operationInterface_, parentInterface_), userExecution.getTag4(interface_, generalization_, operationInterface_, parentInterface_));
    
    userExecution.executeAction1(interface_, generalization_, operationInterface_, parentInterface_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(interface_, generalization_, operationInterface_, parentInterface_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
