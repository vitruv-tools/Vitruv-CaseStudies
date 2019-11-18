package mir.routines.umlXpcmSignature_R2L;

import java.io.IOException;
import mir.routines.umlXpcmSignature_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Signature_CreateMappingRoutine extends AbstractRepairRoutineRealization {
  private Signature_CreateMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getTag1(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationSignature:operationSignature";
    }
    
    public String getTag3(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_OperationSignature:operationSignature";
    }
    
    public String getTag2(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_OperationSignature:operationSignature";
    }
    
    public void callRoutine1(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.signature_BidirectionalUpdate(operationSignature_, returnType_, operationInterface_);
    }
    
    public String getTag9(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getTag8(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_OperationInterface:operationInterface";
    }
    
    public void executeAction1(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_, @Extension final RoutinesFacade _routinesFacade) {
      interface_.getOwnedOperations().add(operation_);
      operation_.getOwnedParameters().add(returnParameter_);
      returnParameter_.setName("returnParam");
    }
    
    public String getTag5(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_DataType:returnType";
    }
    
    public String getTag4(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_DataType:returnType";
    }
    
    public String getTag7(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationInterface:operationInterface";
    }
    
    public String getTag6(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_DataType:returnType";
    }
    
    public EObject getCorrepondenceSourceInterface_(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
      return operationInterface_;
    }
    
    public String getRetrieveTag4(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public EObject getElement10(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return returnType_;
    }
    
    public EObject getElement11(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return interface_;
    }
    
    public EObject getCorrepondenceSource1(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
      return operationSignature_;
    }
    
    public EObject getElement12(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return returnType_;
    }
    
    public EObject getCorrepondenceSource2(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
      return operationSignature_;
    }
    
    public String getRetrieveTag1(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationSignature:operationSignature";
    }
    
    public EObject getCorrepondenceSource3(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
      return operationSignature_;
    }
    
    public String getRetrieveTag2(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_OperationSignature:operationSignature";
    }
    
    public String getRetrieveTag3(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_OperationSignature:operationSignature";
    }
    
    public EObject getElement8(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return returnType_;
    }
    
    public EObject getElement17(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return interface_;
    }
    
    public EObject getElement9(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return returnParameter_;
    }
    
    public EObject getElement18(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return operationInterface_;
    }
    
    public EObject getElement6(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return operationSignature_;
    }
    
    public EObject getElement7(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return operation_;
    }
    
    public EObject getElement13(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return operation_;
    }
    
    public EObject getElement14(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return operationInterface_;
    }
    
    public EObject getElement15(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return returnParameter_;
    }
    
    public EObject getElement16(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return operationInterface_;
    }
    
    public EObject getElement1(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return operation_;
    }
    
    public EObject getElement4(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return operationSignature_;
    }
    
    public EObject getElement5(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return interface_;
    }
    
    public EObject getElement2(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return operationSignature_;
    }
    
    public EObject getElement3(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_, final Interface interface_, final Operation operation_, final Parameter returnParameter_) {
      return returnParameter_;
    }
  }
  
  public Signature_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmSignature_R2L.Signature_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationSignature_ = operationSignature_;this.returnType_ = returnType_;this.operationInterface_ = operationInterface_;
  }
  
  private OperationSignature operationSignature_;
  
  private DataType returnType_;
  
  private OperationInterface operationInterface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Signature_CreateMappingRoutine with input:");
    getLogger().debug("   operationSignature_: " + this.operationSignature_);
    getLogger().debug("   returnType_: " + this.returnType_);
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(operationSignature_, returnType_, operationInterface_), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(operationSignature_, returnType_, operationInterface_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(operationSignature_, returnType_, operationInterface_), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(operationSignature_, returnType_, operationInterface_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource3(operationSignature_, returnType_, operationInterface_), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(operationSignature_, returnType_, operationInterface_)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Interface interface_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterface_(operationSignature_, returnType_, operationInterface_), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag4(operationSignature_, returnType_, operationInterface_), 
    	false // asserted
    	);
    if (interface_ == null) {
    	return false;
    }
    registerObjectUnderModification(interface_);
    org.eclipse.uml2.uml.Operation operation_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createOperation();
    notifyObjectCreated(operation_);
    
    org.eclipse.uml2.uml.Parameter returnParameter_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(returnParameter_);
    
    addCorrespondenceBetween(userExecution.getElement1(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getElement2(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getTag1(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_));
    
    addCorrespondenceBetween(userExecution.getElement3(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getElement4(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getTag2(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_));
    
    addCorrespondenceBetween(userExecution.getElement5(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getElement6(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getTag3(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_));
    
    addCorrespondenceBetween(userExecution.getElement7(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getElement8(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getTag4(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_));
    
    addCorrespondenceBetween(userExecution.getElement9(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getElement10(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getTag5(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_));
    
    addCorrespondenceBetween(userExecution.getElement11(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getElement12(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getTag6(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_));
    
    addCorrespondenceBetween(userExecution.getElement13(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getElement14(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getTag7(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_));
    
    addCorrespondenceBetween(userExecution.getElement15(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getElement16(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getTag8(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_));
    
    addCorrespondenceBetween(userExecution.getElement17(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getElement18(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_), userExecution.getTag9(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_));
    
    userExecution.executeAction1(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(operationSignature_, returnType_, operationInterface_, interface_, operation_, returnParameter_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
