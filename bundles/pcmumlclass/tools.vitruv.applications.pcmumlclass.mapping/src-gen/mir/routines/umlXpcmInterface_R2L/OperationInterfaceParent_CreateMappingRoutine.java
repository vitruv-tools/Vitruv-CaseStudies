package mir.routines.umlXpcmInterface_R2L;

import java.io.IOException;
import mir.routines.umlXpcmInterface_R2L.RoutinesFacade;
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
    
    public EObject getCorrepondenceSource1(final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return operationInterface_;
    }
    
    public EObject getCorrepondenceSource2(final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return operationInterface_;
    }
    
    public String getRetrieveTag1(final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag2(final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Generalization:generalization_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag3(final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getTag1(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public EObject getElement8(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return parentInterface_;
    }
    
    public EObject getElement6(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return parentInterface_;
    }
    
    public String getTag3(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:parentInterface";
    }
    
    public String getTag2(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Generalization:generalization_with_OperationInterface:operationInterface";
    }
    
    public EObject getElement7(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return generalization_;
    }
    
    public void callRoutine1(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.operationInterfaceParent_BidirectionalUpdate(operationInterface_, parentInterface_);
    }
    
    public EObject getElement1(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return interface_;
    }
    
    public void executeAction1(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_, @Extension final RoutinesFacade _routinesFacade) {
      interface_.getGeneralizations().add(generalization_);
    }
    
    public EObject getElement4(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return operationInterface_;
    }
    
    public EObject getElement5(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return interface_;
    }
    
    public String getTag4(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Generalization:generalization_with_OperationInterface:parentInterface";
    }
    
    public EObject getElement2(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return operationInterface_;
    }
    
    public EObject getElement3(final OperationInterface operationInterface_, final OperationInterface parentInterface_, final Interface interface_, final Generalization generalization_) {
      return generalization_;
    }
    
    public EObject getCorrepondenceSourceInterface_(final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
      return operationInterface_;
    }
  }
  
  public OperationInterfaceParent_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_R2L.OperationInterfaceParent_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationInterface_ = operationInterface_;this.parentInterface_ = parentInterface_;
  }
  
  private OperationInterface operationInterface_;
  
  private OperationInterface parentInterface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterfaceParent_CreateMappingRoutine with input:");
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    getLogger().debug("   parentInterface_: " + this.parentInterface_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(operationInterface_, parentInterface_), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(operationInterface_, parentInterface_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(operationInterface_, parentInterface_), // correspondence source supplier
    	org.eclipse.uml2.uml.Generalization.class,
    	(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(operationInterface_, parentInterface_)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Interface interface_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterface_(operationInterface_, parentInterface_), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(operationInterface_, parentInterface_), 
    	false // asserted
    	);
    if (interface_ == null) {
    	return false;
    }
    registerObjectUnderModification(interface_);
    org.eclipse.uml2.uml.Generalization generalization_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createGeneralization();
    notifyObjectCreated(generalization_);
    
    addCorrespondenceBetween(userExecution.getElement1(operationInterface_, parentInterface_, interface_, generalization_), userExecution.getElement2(operationInterface_, parentInterface_, interface_, generalization_), userExecution.getTag1(operationInterface_, parentInterface_, interface_, generalization_));
    
    addCorrespondenceBetween(userExecution.getElement3(operationInterface_, parentInterface_, interface_, generalization_), userExecution.getElement4(operationInterface_, parentInterface_, interface_, generalization_), userExecution.getTag2(operationInterface_, parentInterface_, interface_, generalization_));
    
    addCorrespondenceBetween(userExecution.getElement5(operationInterface_, parentInterface_, interface_, generalization_), userExecution.getElement6(operationInterface_, parentInterface_, interface_, generalization_), userExecution.getTag3(operationInterface_, parentInterface_, interface_, generalization_));
    
    addCorrespondenceBetween(userExecution.getElement7(operationInterface_, parentInterface_, interface_, generalization_), userExecution.getElement8(operationInterface_, parentInterface_, interface_, generalization_), userExecution.getTag4(operationInterface_, parentInterface_, interface_, generalization_));
    
    userExecution.executeAction1(operationInterface_, parentInterface_, interface_, generalization_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(operationInterface_, parentInterface_, interface_, generalization_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
