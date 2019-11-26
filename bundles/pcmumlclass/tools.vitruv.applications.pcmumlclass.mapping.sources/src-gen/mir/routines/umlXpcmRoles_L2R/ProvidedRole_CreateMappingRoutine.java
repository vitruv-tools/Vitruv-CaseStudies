package mir.routines.umlXpcmRoles_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRoles_L2R.RoutinesFacade;
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
    
    public String getTag1(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationProvidedRole:role";
    }
    
    public String getTag3(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public String getTag2(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceOperationInterface_(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
      return interface_;
    }
    
    public void callRoutine1(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.providedRole_BidirectionalUpdate(interfaceRealization_, implementation_, interface_);
    }
    
    public String getTag9(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public String getTag8(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public void executeAction1(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_, @Extension final RoutinesFacade _routinesFacade) {
      providingEntity_.getProvidedRoles_InterfaceProvidingEntity().add(role_);
      role_.setProvidedInterface__OperationProvidedRole(operationInterface_);
    }
    
    public String getTag5(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationInterface:operationInterface";
    }
    
    public String getTag4(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationProvidedRole:role";
    }
    
    public String getTag7(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationProvidedRole:role";
    }
    
    public String getTag6(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public String getRetrieveTag4(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag5(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_RepositoryComponent:component";
    }
    
    public EObject getElement10(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return implementation_;
    }
    
    public EObject getElement11(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return providingEntity_;
    }
    
    public EObject getCorrepondenceSource1(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
      return interfaceRealization_;
    }
    
    public EObject getElement12(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return implementation_;
    }
    
    public EObject getCorrepondenceSource2(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
      return interfaceRealization_;
    }
    
    public String getRetrieveTag1(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationProvidedRole:role";
    }
    
    public EObject getCorrepondenceSource3(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
      return interfaceRealization_;
    }
    
    public String getRetrieveTag2(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag3(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public EObject getElement8(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return implementation_;
    }
    
    public EObject getElement17(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return providingEntity_;
    }
    
    public EObject getElement9(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return operationInterface_;
    }
    
    public EObject getElement18(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return interface_;
    }
    
    public EObject getElement6(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return interfaceRealization_;
    }
    
    public EObject getElement7(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return role_;
    }
    
    public EObject getElement13(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return role_;
    }
    
    public EObject getElement14(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return interface_;
    }
    
    public EObject getElement15(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return operationInterface_;
    }
    
    public EObject getElement16(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return interface_;
    }
    
    public EObject getElement1(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return role_;
    }
    
    public EObject getElement4(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return interfaceRealization_;
    }
    
    public EObject getElement5(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return providingEntity_;
    }
    
    public EObject getElement2(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return interfaceRealization_;
    }
    
    public EObject getCorrepondenceSourceProvidingEntity_(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_) {
      return implementation_;
    }
    
    public EObject getElement3(final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_, final OperationInterface operationInterface_, final InterfaceProvidingRequiringEntity providingEntity_, final OperationProvidedRole role_) {
      return operationInterface_;
    }
  }
  
  public ProvidedRole_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization interfaceRealization_, final org.eclipse.uml2.uml.Class implementation_, final Interface interface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_L2R.ProvidedRole_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.interfaceRealization_ = interfaceRealization_;this.implementation_ = implementation_;this.interface_ = interface_;
  }
  
  private InterfaceRealization interfaceRealization_;
  
  private org.eclipse.uml2.uml.Class implementation_;
  
  private Interface interface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ProvidedRole_CreateMappingRoutine with input:");
    getLogger().debug("   interfaceRealization_: " + this.interfaceRealization_);
    getLogger().debug("   implementation_: " + this.implementation_);
    getLogger().debug("   interface_: " + this.interface_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(interfaceRealization_, implementation_, interface_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    	(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(interfaceRealization_, implementation_, interface_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(interfaceRealization_, implementation_, interface_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(interfaceRealization_, implementation_, interface_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource3(interfaceRealization_, implementation_, interface_), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(interfaceRealization_, implementation_, interface_)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.OperationInterface operationInterface_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationInterface_(interfaceRealization_, implementation_, interface_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag4(interfaceRealization_, implementation_, interface_), 
    	false // asserted
    	);
    if (operationInterface_ == null) {
    	return false;
    }
    registerObjectUnderModification(operationInterface_);
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity providingEntity_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceProvidingEntity_(interfaceRealization_, implementation_, interface_, operationInterface_), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag5(interfaceRealization_, implementation_, interface_, operationInterface_), 
    	false // asserted
    	);
    if (providingEntity_ == null) {
    	return false;
    }
    registerObjectUnderModification(providingEntity_);
    org.palladiosimulator.pcm.repository.OperationProvidedRole role_ = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationProvidedRole();
    notifyObjectCreated(role_);
    
    addCorrespondenceBetween(userExecution.getElement1(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getElement2(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getTag1(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement3(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getElement4(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getTag2(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement5(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getElement6(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getTag3(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement7(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getElement8(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getTag4(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement9(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getElement10(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getTag5(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement11(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getElement12(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getTag6(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement13(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getElement14(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getTag7(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement15(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getElement16(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getTag8(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_));
    
    addCorrespondenceBetween(userExecution.getElement17(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getElement18(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_), userExecution.getTag9(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_));
    
    userExecution.executeAction1(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(interfaceRealization_, implementation_, interface_, operationInterface_, providingEntity_, role_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
