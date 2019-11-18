package mir.routines.umlXpcmDatatypes_R2L;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDatatypeParent_CreateMappingRoutine extends AbstractRepairRoutineRealization {
  private CompositeDatatypeParent_CreateMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final CompositeDataType type_, final CompositeDataType parentType_) {
      return type_;
    }
    
    public EObject getCorrepondenceSource2(final CompositeDataType type_, final CompositeDataType parentType_) {
      return type_;
    }
    
    public String getRetrieveTag1(final CompositeDataType type_, final CompositeDataType parentType_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public String getRetrieveTag2(final CompositeDataType type_, final CompositeDataType parentType_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Generalization:generalization_with_CompositeDataType:type";
    }
    
    public String getRetrieveTag3(final CompositeDataType type_, final CompositeDataType parentType_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public String getTag1(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public EObject getElement8(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return parentType_;
    }
    
    public EObject getElement6(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return parentType_;
    }
    
    public String getTag3(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:parentType";
    }
    
    public String getTag2(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Generalization:generalization_with_CompositeDataType:type";
    }
    
    public EObject getElement7(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return generalization_;
    }
    
    public EObject getCorrepondenceSourceClass_(final CompositeDataType type_, final CompositeDataType parentType_) {
      return type_;
    }
    
    public void callRoutine1(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.compositeDatatypeParent_BidirectionalUpdate(type_, parentType_);
    }
    
    public EObject getElement1(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return class_;
    }
    
    public void executeAction1(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, @Extension final RoutinesFacade _routinesFacade) {
      class_.getGeneralizations().add(generalization_);
    }
    
    public EObject getElement4(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return type_;
    }
    
    public EObject getElement5(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return class_;
    }
    
    public String getTag4(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Generalization:generalization_with_CompositeDataType:parentType";
    }
    
    public EObject getElement2(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return type_;
    }
    
    public EObject getElement3(final CompositeDataType type_, final CompositeDataType parentType_, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return generalization_;
    }
  }
  
  public CompositeDatatypeParent_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType type_, final CompositeDataType parentType_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.type_ = type_;this.parentType_ = parentType_;
  }
  
  private CompositeDataType type_;
  
  private CompositeDataType parentType_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDatatypeParent_CreateMappingRoutine with input:");
    getLogger().debug("   type_: " + this.type_);
    getLogger().debug("   parentType_: " + this.parentType_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(type_, parentType_), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(type_, parentType_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(type_, parentType_), // correspondence source supplier
    	org.eclipse.uml2.uml.Generalization.class,
    	(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(type_, parentType_)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Class class_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClass_(type_, parentType_), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(type_, parentType_), 
    	false // asserted
    	);
    if (class_ == null) {
    	return false;
    }
    registerObjectUnderModification(class_);
    org.eclipse.uml2.uml.Generalization generalization_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createGeneralization();
    notifyObjectCreated(generalization_);
    
    addCorrespondenceBetween(userExecution.getElement1(type_, parentType_, class_, generalization_), userExecution.getElement2(type_, parentType_, class_, generalization_), userExecution.getTag1(type_, parentType_, class_, generalization_));
    
    addCorrespondenceBetween(userExecution.getElement3(type_, parentType_, class_, generalization_), userExecution.getElement4(type_, parentType_, class_, generalization_), userExecution.getTag2(type_, parentType_, class_, generalization_));
    
    addCorrespondenceBetween(userExecution.getElement5(type_, parentType_, class_, generalization_), userExecution.getElement6(type_, parentType_, class_, generalization_), userExecution.getTag3(type_, parentType_, class_, generalization_));
    
    addCorrespondenceBetween(userExecution.getElement7(type_, parentType_, class_, generalization_), userExecution.getElement8(type_, parentType_, class_, generalization_), userExecution.getTag4(type_, parentType_, class_, generalization_));
    
    userExecution.executeAction1(type_, parentType_, class_, generalization_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(type_, parentType_, class_, generalization_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
