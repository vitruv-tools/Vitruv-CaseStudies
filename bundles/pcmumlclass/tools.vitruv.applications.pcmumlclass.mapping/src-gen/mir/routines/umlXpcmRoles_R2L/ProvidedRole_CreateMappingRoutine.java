package mir.routines.umlXpcmRoles_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ProvidedRole_CreateMappingRoutine extends AbstractRepairRoutineRealization {
  private ProvidedRole_CreateMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getTag1(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationProvidedRole:role";
    }
    
    public String getTag3(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationProvidedRole:role";
    }
    
    public String getTag2(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationProvidedRole:role";
    }
    
    public void callRoutine1(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.providedRole_BidirectionalUpdate(role_, operationInterface_, providingEntity_);
    }
    
    public String getTag9(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public String getTag8(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public void executeAction1(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_, @Extension final RoutinesFacade _routinesFacade) {
      implementation_.getInterfaceRealizations().add(interfaceRealization_);
      interfaceRealization_.setContract(interface_);
    }
    
    public String getTag5(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationInterface:operationInterface";
    }
    
    public String getTag4(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationInterface:operationInterface";
    }
    
    public String getTag7(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public String getTag6(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceInterface_(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_) {
      return operationInterface_;
    }
    
    public EObject getCorrepondenceSourceImplementation_(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_) {
      return providingEntity_;
    }
    
    public String getRetrieveTag4(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_RepositoryComponent:component";
    }
    
    public String getRetrieveTag5(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public EObject getElement10(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return operationInterface_;
    }
    
    public EObject getElement11(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return interface_;
    }
    
    public EObject getCorrepondenceSource1(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_) {
      return role_;
    }
    
    public EObject getElement12(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return operationInterface_;
    }
    
    public EObject getCorrepondenceSource2(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_) {
      return role_;
    }
    
    public String getRetrieveTag1(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationProvidedRole:role";
    }
    
    public EObject getCorrepondenceSource3(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_) {
      return role_;
    }
    
    public String getRetrieveTag2(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationProvidedRole:role";
    }
    
    public String getRetrieveTag3(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationProvidedRole:role";
    }
    
    public EObject getElement8(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return operationInterface_;
    }
    
    public EObject getElement17(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return interface_;
    }
    
    public EObject getElement9(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return implementation_;
    }
    
    public EObject getElement18(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return providingEntity_;
    }
    
    public EObject getElement6(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return role_;
    }
    
    public EObject getElement7(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return interfaceRealization_;
    }
    
    public EObject getElement13(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return interfaceRealization_;
    }
    
    public EObject getElement14(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return providingEntity_;
    }
    
    public EObject getElement15(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return implementation_;
    }
    
    public EObject getElement16(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return providingEntity_;
    }
    
    public EObject getElement1(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return interfaceRealization_;
    }
    
    public EObject getElement4(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return role_;
    }
    
    public EObject getElement5(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return interface_;
    }
    
    public EObject getElement2(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return role_;
    }
    
    public EObject getElement3(final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final InterfaceRealization interfaceRealization_) {
      return implementation_;
    }
  }
  
  public ProvidedRole_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole role_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.ProvidedRole_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.role_ = role_;this.operationInterface_ = operationInterface_;this.providingEntity_ = providingEntity_;
  }
  
  private OperationProvidedRole role_;
  
  private OperationInterface operationInterface_;
  
  private InterfaceProvidingRequiringEntity providingEntity_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ProvidedRole_CreateMappingRoutine with input:");
    getLogger().debug("   role_: " + this.role_);
    getLogger().debug("   operationInterface_: " + this.operationInterface_);
    getLogger().debug("   providingEntity_: " + this.providingEntity_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(role_, operationInterface_, providingEntity_), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(role_, operationInterface_, providingEntity_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(role_, operationInterface_, providingEntity_), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(role_, operationInterface_, providingEntity_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource3(role_, operationInterface_, providingEntity_), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(role_, operationInterface_, providingEntity_)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Class implementation_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceImplementation_(role_, operationInterface_, providingEntity_), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag4(role_, operationInterface_, providingEntity_), 
    	false // asserted
    	);
    if (implementation_ == null) {
    	return false;
    }
    registerObjectUnderModification(implementation_);
    org.eclipse.uml2.uml.Interface interface_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterface_(role_, operationInterface_, providingEntity_, implementation_), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag5(role_, operationInterface_, providingEntity_, implementation_), 
    	false // asserted
    	);
    if (interface_ == null) {
    	return false;
    }
    registerObjectUnderModification(interface_);
    org.eclipse.uml2.uml.InterfaceRealization interfaceRealization_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createInterfaceRealization();
    notifyObjectCreated(interfaceRealization_);
    
    addCorrespondenceBetween(userExecution.getElement1(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getElement2(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getTag1(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_));
    
    addCorrespondenceBetween(userExecution.getElement3(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getElement4(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getTag2(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_));
    
    addCorrespondenceBetween(userExecution.getElement5(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getElement6(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getTag3(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_));
    
    addCorrespondenceBetween(userExecution.getElement7(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getElement8(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getTag4(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_));
    
    addCorrespondenceBetween(userExecution.getElement9(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getElement10(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getTag5(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_));
    
    addCorrespondenceBetween(userExecution.getElement11(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getElement12(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getTag6(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_));
    
    addCorrespondenceBetween(userExecution.getElement13(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getElement14(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getTag7(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_));
    
    addCorrespondenceBetween(userExecution.getElement15(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getElement16(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getTag8(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_));
    
    addCorrespondenceBetween(userExecution.getElement17(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getElement18(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_), userExecution.getTag9(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_));
    
    userExecution.executeAction1(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(role_, operationInterface_, providingEntity_, implementation_, interface_, interfaceRealization_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
