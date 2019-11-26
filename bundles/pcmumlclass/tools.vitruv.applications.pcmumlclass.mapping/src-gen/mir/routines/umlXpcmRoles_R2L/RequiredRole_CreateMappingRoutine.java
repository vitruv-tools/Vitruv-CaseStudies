package mir.routines.umlXpcmRoles_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
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
    
    public EObject getElement20(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return operationInterface_;
    }
    
    public EObject getElement21(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return property_;
    }
    
    public EObject getElement22(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return requiringEntity_;
    }
    
    public EObject getElement23(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return parameter_;
    }
    
    public EObject getElement28(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return requiringEntity_;
    }
    
    public EObject getElement29(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return operation_;
    }
    
    public EObject getElement24(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return requiringEntity_;
    }
    
    public EObject getElement25(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return implementation_;
    }
    
    public EObject getElement26(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return requiringEntity_;
    }
    
    public EObject getElement27(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return interface_;
    }
    
    public void callRoutine1(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.requiredRole_BidirectionalUpdate(role_, operationInterface_, requiringEntity_);
    }
    
    public EObject getCorrepondenceSource1(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return role_;
    }
    
    public EObject getCorrepondenceSource2(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return role_;
    }
    
    public EObject getCorrepondenceSource3(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return role_;
    }
    
    public String getTag15(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public EObject getCorrepondenceSource4(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return role_;
    }
    
    public EObject getCorrepondenceSource5(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return role_;
    }
    
    public EObject getElement30(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return requiringEntity_;
    }
    
    public String getTag12(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getTag11(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getTag14(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getTag13(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getTag10(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceOperation_(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
      return operationInterface_;
    }
    
    public String getTag1(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationRequiredRole:role";
    }
    
    public String getTag3(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationRequiredRole:role";
    }
    
    public String getTag2(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_OperationRequiredRole:role";
    }
    
    public String getTag9(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getTag8(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationInterface:operationInterface";
    }
    
    public void executeAction1(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_, @Extension final RoutinesFacade _routinesFacade) {
      implementation_.getOwnedAttributes().add(property_);
      property_.setType(interface_);
      parameter_.setType(interface_);
      operation_.getOwnedParameters().add(parameter_);
    }
    
    public String getTag5(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_OperationRequiredRole:role";
    }
    
    public String getTag4(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationRequiredRole:role";
    }
    
    public String getTag7(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_OperationInterface:operationInterface";
    }
    
    public String getTag6(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceInterface_(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_) {
      return operationInterface_;
    }
    
    public EObject getCorrepondenceSourceImplementation_(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return requiringEntity_;
    }
    
    public String getRetrieveTag4(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationRequiredRole:role";
    }
    
    public String getRetrieveTag5(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_OperationRequiredRole:role";
    }
    
    public EObject getElement10(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return role_;
    }
    
    public String getRetrieveTag6(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_RepositoryComponent:component";
    }
    
    public EObject getElement11(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return property_;
    }
    
    public String getRetrieveTag7(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public EObject getElement12(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return operationInterface_;
    }
    
    public String getRetrieveTag1(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationRequiredRole:role";
    }
    
    public String getRetrieveTag2(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_OperationRequiredRole:role";
    }
    
    public String getRetrieveTag3(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationRequiredRole:role";
    }
    
    public EObject getElement8(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return role_;
    }
    
    public EObject getElement17(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return interface_;
    }
    
    public EObject getElement9(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return operation_;
    }
    
    public EObject getElement18(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return operationInterface_;
    }
    
    public EObject getElement6(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return role_;
    }
    
    public EObject getElement19(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return operation_;
    }
    
    public EObject getElement7(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return interface_;
    }
    
    public EObject getElement13(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return parameter_;
    }
    
    public EObject getElement14(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return operationInterface_;
    }
    
    public EObject getElement15(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return implementation_;
    }
    
    public EObject getElement16(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return operationInterface_;
    }
    
    public EObject getElement1(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return property_;
    }
    
    public EObject getElement4(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return role_;
    }
    
    public EObject getElement5(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return implementation_;
    }
    
    public EObject getElement2(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return role_;
    }
    
    public EObject getElement3(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final Operation operation_, final Property property_, final Parameter parameter_) {
      return parameter_;
    }
    
    public String getRetrieveTag8(final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationInterface:operationInterface";
    }
  }
  
  public RequiredRole_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity requiringEntity_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.RequiredRole_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.role_ = role_;this.operationInterface_ = operationInterface_;this.requiringEntity_ = requiringEntity_;
  }
  
  private OperationRequiredRole role_;
  
  private OperationInterface operationInterface_;
  
  private InterfaceProvidingRequiringEntity requiringEntity_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RequiredRole_CreateMappingRoutine with input:");
    getLogger().debug("   role_: " + this.role_);
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    getLogger().debug("   requiringEntity_: " + this.requiringEntity_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(role_, operationInterface_, requiringEntity_), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(role_, operationInterface_, requiringEntity_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(role_, operationInterface_, requiringEntity_), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(role_, operationInterface_, requiringEntity_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource3(role_, operationInterface_, requiringEntity_), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(role_, operationInterface_, requiringEntity_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource4(role_, operationInterface_, requiringEntity_), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag4(role_, operationInterface_, requiringEntity_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource5(role_, operationInterface_, requiringEntity_), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag5(role_, operationInterface_, requiringEntity_)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Class implementation_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceImplementation_(role_, operationInterface_, requiringEntity_), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag6(role_, operationInterface_, requiringEntity_), 
    	false // asserted
    	);
    if (implementation_ == null) {
    	return false;
    }
    registerObjectUnderModification(implementation_);
    org.eclipse.uml2.uml.Interface interface_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterface_(role_, operationInterface_, requiringEntity_, implementation_), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag7(role_, operationInterface_, requiringEntity_, implementation_), 
    	false // asserted
    	);
    if (interface_ == null) {
    	return false;
    }
    registerObjectUnderModification(interface_);
    org.eclipse.uml2.uml.Operation operation_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperation_(role_, operationInterface_, requiringEntity_, implementation_, interface_), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag8(role_, operationInterface_, requiringEntity_, implementation_, interface_), 
    	false // asserted
    	);
    if (operation_ == null) {
    	return false;
    }
    registerObjectUnderModification(operation_);
    org.eclipse.uml2.uml.Property property_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createProperty();
    notifyObjectCreated(property_);
    
    org.eclipse.uml2.uml.Parameter parameter_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(parameter_);
    
    addCorrespondenceBetween(userExecution.getElement1(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement2(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag1(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement3(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement4(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag2(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement5(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement6(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag3(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement7(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement8(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag4(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement9(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement10(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag5(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement11(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement12(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag6(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement13(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement14(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag7(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement15(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement16(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag8(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement17(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement18(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag9(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement19(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement20(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag10(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement21(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement22(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag11(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement23(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement24(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag12(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement25(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement26(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag13(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement27(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement28(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag14(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    addCorrespondenceBetween(userExecution.getElement29(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getElement30(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_), userExecution.getTag15(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_));
    
    userExecution.executeAction1(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(role_, operationInterface_, requiringEntity_, implementation_, interface_, operation_, property_, parameter_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
