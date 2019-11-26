package mir.routines.umlXpcmSignature_L2R;

import java.io.IOException;
import mir.routines.umlXpcmSignature_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
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
    
    public String getTag1(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationSignature:operationSignature";
    }
    
    public String getTag3(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationInterface:operationInterface";
    }
    
    public String getTag2(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_DataType:returnType";
    }
    
    public EObject getCorrepondenceSourceOperationInterface_(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
      return interface_;
    }
    
    public void callRoutine1(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.signature_BidirectionalUpdate(operation_, returnParameter_, interface_);
    }
    
    public String getTag9(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getTag8(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_DataType:returnType";
    }
    
    public void executeAction1(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_, @Extension final RoutinesFacade _routinesFacade) {
      operationInterface_.getSignatures__OperationInterface().add(operationSignature_);
      operationSignature_.setReturnType__OperationSignature(returnType_);
    }
    
    public String getTag5(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_DataType:returnType";
    }
    
    public String getTag4(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_OperationSignature:operationSignature";
    }
    
    public String getTag7(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_OperationSignature:operationSignature";
    }
    
    public String getTag6(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag4(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public EObject getElement10(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return returnParameter_;
    }
    
    public EObject getElement11(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return operationInterface_;
    }
    
    public EObject getCorrepondenceSource1(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
      return operation_;
    }
    
    public EObject getElement12(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return returnParameter_;
    }
    
    public EObject getCorrepondenceSource2(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
      return operation_;
    }
    
    public String getRetrieveTag1(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationSignature:operationSignature";
    }
    
    public EObject getCorrepondenceSource3(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
      return operation_;
    }
    
    public String getRetrieveTag2(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_DataType:returnType";
    }
    
    public String getRetrieveTag3(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationInterface:operationInterface";
    }
    
    public EObject getElement8(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return returnParameter_;
    }
    
    public EObject getElement17(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return operationInterface_;
    }
    
    public EObject getElement9(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return returnType_;
    }
    
    public EObject getElement18(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return interface_;
    }
    
    public EObject getElement6(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return operation_;
    }
    
    public EObject getElement7(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return operationSignature_;
    }
    
    public EObject getElement13(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return operationSignature_;
    }
    
    public EObject getElement14(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return interface_;
    }
    
    public EObject getElement15(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return returnType_;
    }
    
    public EObject getElement16(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return interface_;
    }
    
    public EObject getElement1(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return operationSignature_;
    }
    
    public EObject getElement4(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return operation_;
    }
    
    public EObject getElement5(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return operationInterface_;
    }
    
    public EObject getElement2(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return operation_;
    }
    
    public EObject getElement3(final Operation operation_, final Parameter returnParameter_, final Interface interface_, final OperationInterface operationInterface_, final OperationSignature operationSignature_, final PrimitiveDataType returnType_) {
      return returnType_;
    }
  }
  
  public Signature_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmSignature_L2R.Signature_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.operation_ = operation_;this.returnParameter_ = returnParameter_;this.interface_ = interface_;
  }
  
  private Operation operation_;
  
  private Parameter returnParameter_;
  
  private Interface interface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Signature_CreateMappingRoutine with input:");
    getLogger().debug("   operation_: " + this.operation_);
    getLogger().debug("   returnParameter_: " + this.returnParameter_);
    getLogger().debug("   interface_: " + this.interface_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(operation_, returnParameter_, interface_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(operation_, returnParameter_, interface_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(operation_, returnParameter_, interface_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.DataType.class,
    	(org.palladiosimulator.pcm.repository.DataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(operation_, returnParameter_, interface_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource3(operation_, returnParameter_, interface_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(operation_, returnParameter_, interface_)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.OperationInterface operationInterface_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationInterface_(operation_, returnParameter_, interface_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag4(operation_, returnParameter_, interface_), 
    	false // asserted
    	);
    if (operationInterface_ == null) {
    	return false;
    }
    registerObjectUnderModification(operationInterface_);
    org.palladiosimulator.pcm.repository.OperationSignature operationSignature_ = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationSignature();
    notifyObjectCreated(operationSignature_);
    
    org.palladiosimulator.pcm.repository.PrimitiveDataType returnType_ = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createPrimitiveDataType();
    notifyObjectCreated(returnType_);
    
    addCorrespondenceBetween(userExecution.getElement1(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getElement2(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getTag1(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_));
    
    addCorrespondenceBetween(userExecution.getElement3(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getElement4(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getTag2(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_));
    
    addCorrespondenceBetween(userExecution.getElement5(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getElement6(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getTag3(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_));
    
    addCorrespondenceBetween(userExecution.getElement7(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getElement8(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getTag4(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_));
    
    addCorrespondenceBetween(userExecution.getElement9(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getElement10(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getTag5(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_));
    
    addCorrespondenceBetween(userExecution.getElement11(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getElement12(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getTag6(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_));
    
    addCorrespondenceBetween(userExecution.getElement13(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getElement14(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getTag7(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_));
    
    addCorrespondenceBetween(userExecution.getElement15(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getElement16(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getTag8(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_));
    
    addCorrespondenceBetween(userExecution.getElement17(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getElement18(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_), userExecution.getTag9(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_));
    
    userExecution.executeAction1(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(operation_, returnParameter_, interface_, operationInterface_, operationSignature_, returnType_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
