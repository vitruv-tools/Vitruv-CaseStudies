package mir.routines.umlXpcmRoles_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRoles_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RequiredRole_CreateMappingRoutine extends AbstractRepairRoutineRealization {
  private RequiredRole_CreateMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement20(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return interface_;
    }
    
    public EObject getElement21(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return operationInterface_;
    }
    
    public EObject getElement22(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return interface_;
    }
    
    public EObject getElement23(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return requiringEntity_;
    }
    
    public EObject getElement28(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return operation_;
    }
    
    public EObject getElement29(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return requiringEntity_;
    }
    
    public EObject getElement24(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return interface_;
    }
    
    public EObject getElement25(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return role_;
    }
    
    public EObject getElement26(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return operation_;
    }
    
    public EObject getElement27(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return operationInterface_;
    }
    
    public void callRoutine1(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.requiredRole_BidirectionalUpdate(property_, parameter_, implementation_, interface_, operation_);
    }
    
    public EObject getCorrepondenceSource1(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
      return property_;
    }
    
    public EObject getCorrepondenceSource2(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
      return property_;
    }
    
    public EObject getCorrepondenceSource3(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
      return property_;
    }
    
    public String getTag15(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public EObject getElement30(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return operation_;
    }
    
    public String getTag12(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getTag11(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getTag14(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_OperationInterface:operationInterface";
    }
    
    public String getTag13(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_OperationRequiredRole:role";
    }
    
    public String getTag10(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationRequiredRole:role";
    }
    
    public String getTag1(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationRequiredRole:role";
    }
    
    public String getTag3(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getTag2(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceOperationInterface_(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
      return interface_;
    }
    
    public String getTag9(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getTag8(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationInterface:operationInterface";
    }
    
    public void executeAction1(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_, @Extension final RoutinesFacade _routinesFacade) {
      requiringEntity_.getRequiredRoles_InterfaceRequiringEntity().add(role_);
      role_.setRequiredInterface__OperationRequiredRole(operationInterface_);
    }
    
    public String getTag5(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_OperationInterface:operationInterface";
    }
    
    public String getTag4(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_OperationRequiredRole:role";
    }
    
    public String getTag7(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationRequiredRole:role";
    }
    
    public String getTag6(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getRetrieveTag4(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag5(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_RepositoryComponent:component";
    }
    
    public EObject getElement10(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return parameter_;
    }
    
    public EObject getElement11(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return requiringEntity_;
    }
    
    public EObject getElement12(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return parameter_;
    }
    
    public String getRetrieveTag1(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationRequiredRole:role";
    }
    
    public String getRetrieveTag2(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag3(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public EObject getElement8(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return parameter_;
    }
    
    public EObject getElement17(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return requiringEntity_;
    }
    
    public EObject getElement9(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return operationInterface_;
    }
    
    public EObject getElement18(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return implementation_;
    }
    
    public EObject getElement6(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return property_;
    }
    
    public EObject getElement19(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return role_;
    }
    
    public EObject getElement7(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return role_;
    }
    
    public EObject getElement13(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return role_;
    }
    
    public EObject getElement14(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return implementation_;
    }
    
    public EObject getElement15(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return operationInterface_;
    }
    
    public EObject getElement16(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return implementation_;
    }
    
    public EObject getElement1(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return role_;
    }
    
    public EObject getElement4(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return property_;
    }
    
    public EObject getElement5(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return requiringEntity_;
    }
    
    public EObject getElement2(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return property_;
    }
    
    public EObject getElement3(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final OperationRequiredRole role_) {
      return operationInterface_;
    }
    
    public EObject getCorrepondenceSourceRequiringEntity_(final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final OperationInterface operationInterface_) {
      return implementation_;
    }
  }
  
  public RequiredRole_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property property_, final Parameter parameter_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_L2R.RequiredRole_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.property_ = property_;this.parameter_ = parameter_;this.implementation_ = implementation_;this.interface_ = interface_;this.operation_ = operation_;
  }
  
  private Property property_;
  
  private Parameter parameter_;
  
  private org.eclipse.uml2.uml.Class implementation_;
  
  private Interface interface_;
  
  private Operation operation_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RequiredRole_CreateMappingRoutine with input:");
    getLogger().debug("   property_: " + this.property_);
    getLogger().debug("   parameter_: " + this.parameter_);
    getLogger().debug("   implementation_: " + this.implementation_);
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   operation_: " + this.operation_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(property_, parameter_, implementation_, interface_, operation_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(property_, parameter_, implementation_, interface_, operation_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(property_, parameter_, implementation_, interface_, operation_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(property_, parameter_, implementation_, interface_, operation_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource3(property_, parameter_, implementation_, interface_, operation_), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(property_, parameter_, implementation_, interface_, operation_)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.OperationInterface operationInterface_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationInterface_(property_, parameter_, implementation_, interface_, operation_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag4(property_, parameter_, implementation_, interface_, operation_), 
    	false // asserted
    	);
    if (operationInterface_ == null) {
    	return false;
    }
    registerObjectUnderModification(operationInterface_);
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity requiringEntity_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRequiringEntity_(property_, parameter_, implementation_, interface_, operation_, operationInterface_), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag5(property_, parameter_, implementation_, interface_, operation_, operationInterface_), 
    	false // asserted
    	);
    if (requiringEntity_ == null) {
    	return false;
    }
    registerObjectUnderModification(requiringEntity_);
    org.palladiosimulator.pcm.repository.OperationRequiredRole role_ = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationRequiredRole();
    notifyObjectCreated(role_);
    
    addCorrespondenceBetween(userExecution.getElement1(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement2(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag1(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement3(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement4(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag2(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement5(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement6(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag3(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement7(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement8(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag4(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement9(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement10(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag5(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement11(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement12(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag6(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement13(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement14(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag7(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement15(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement16(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag8(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement17(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement18(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag9(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement19(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement20(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag10(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement21(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement22(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag11(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement23(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement24(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag12(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement25(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement26(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag13(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement27(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement28(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag14(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement29(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getElement30(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_), userExecution.getTag15(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_));
    
    userExecution.executeAction1(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(property_, parameter_, implementation_, interface_, operation_, operationInterface_, requiringEntity_, role_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
