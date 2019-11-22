package mir.routines.umlXpcmDatatypes_L2R;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade;
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
    
    public EObject getCorrepondenceSource1(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return class_;
    }
    
    public EObject getCorrepondenceSource2(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return class_;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:parentType";
    }
    
    public String getRetrieveTag3(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public EObject getElement8(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return generalization_;
    }
    
    public EObject getElement6(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return generalization_;
    }
    
    public String getTag3(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Generalization:generalization_with_CompositeDataType:type";
    }
    
    public String getTag2(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:parentType";
    }
    
    public EObject getElement7(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return parentType_;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.compositeDatatypeParent_BidirectionalUpdate(class_, generalization_);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return type_;
    }
    
    public EObject getCorrepondenceSourceType_(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
      return class_;
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_, @Extension final RoutinesFacade _routinesFacade) {
      type_.getParentType_CompositeDataType().add(parentType_);
    }
    
    public EObject getElement4(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return class_;
    }
    
    public EObject getElement5(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return type_;
    }
    
    public String getTag4(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Generalization:generalization_with_CompositeDataType:parentType";
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return class_;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_, final CompositeDataType type_, final CompositeDataType parentType_) {
      return parentType_;
    }
  }
  
  public CompositeDatatypeParent_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.class_ = class_;this.generalization_ = generalization_;
  }
  
  private org.eclipse.uml2.uml.Class class_;
  
  private Generalization generalization_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDatatypeParent_CreateMappingRoutine with input:");
    getLogger().debug("   class_: " + this.class_);
    getLogger().debug("   generalization_: " + this.generalization_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(class_, generalization_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(class_, generalization_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(class_, generalization_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(class_, generalization_)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.CompositeDataType type_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceType_(class_, generalization_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(class_, generalization_), 
    	false // asserted
    	);
    if (type_ == null) {
    	return false;
    }
    registerObjectUnderModification(type_);
    org.palladiosimulator.pcm.repository.CompositeDataType parentType_ = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createCompositeDataType();
    notifyObjectCreated(parentType_);
    
    addCorrespondenceBetween(userExecution.getElement1(class_, generalization_, type_, parentType_), userExecution.getElement2(class_, generalization_, type_, parentType_), userExecution.getTag1(class_, generalization_, type_, parentType_));
    
    addCorrespondenceBetween(userExecution.getElement3(class_, generalization_, type_, parentType_), userExecution.getElement4(class_, generalization_, type_, parentType_), userExecution.getTag2(class_, generalization_, type_, parentType_));
    
    addCorrespondenceBetween(userExecution.getElement5(class_, generalization_, type_, parentType_), userExecution.getElement6(class_, generalization_, type_, parentType_), userExecution.getTag3(class_, generalization_, type_, parentType_));
    
    addCorrespondenceBetween(userExecution.getElement7(class_, generalization_, type_, parentType_), userExecution.getElement8(class_, generalization_, type_, parentType_), userExecution.getTag4(class_, generalization_, type_, parentType_));
    
    userExecution.executeAction1(class_, generalization_, type_, parentType_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(class_, generalization_, type_, parentType_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
