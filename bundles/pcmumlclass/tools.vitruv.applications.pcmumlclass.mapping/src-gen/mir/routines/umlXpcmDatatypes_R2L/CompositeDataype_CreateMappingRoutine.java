package mir.routines.umlXpcmDatatypes_R2L;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDataype_CreateMappingRoutine extends AbstractRepairRoutineRealization {
  private CompositeDataype_CreateMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final CompositeDataType type_, final Repository repository_) {
      return type_;
    }
    
    public EObject getCorrepondenceSource2(final CompositeDataType type_, final Repository repository_) {
      return type_;
    }
    
    public String getRetrieveTag1(final CompositeDataType type_, final Repository repository_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public String getRetrieveTag2(final CompositeDataType type_, final Repository repository_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Package:datatypesPackage_with_CompositeDataType:type";
    }
    
    public String getRetrieveTag3(final CompositeDataType type_, final Repository repository_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:datatypesPkg_with_Repository:repository";
    }
    
    public String getTag1(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public EObject getElement8(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return repository_;
    }
    
    public EObject getElement6(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return repository_;
    }
    
    public String getTag3(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_Repository:repository";
    }
    
    public String getTag2(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Package:datatypesPackage_with_CompositeDataType:type";
    }
    
    public EObject getElement7(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return datatypesPackage_;
    }
    
    public void callRoutine1(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.compositeDataype_BidirectionalUpdate(type_, repository_);
    }
    
    public EObject getElement1(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return class_;
    }
    
    public void executeAction1(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_, @Extension final RoutinesFacade _routinesFacade) {
      datatypesPackage_.getPackagedElements().add(class_);
    }
    
    public EObject getElement4(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return type_;
    }
    
    public EObject getElement5(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return class_;
    }
    
    public String getTag4(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Package:datatypesPackage_with_Repository:repository";
    }
    
    public EObject getElement2(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return type_;
    }
    
    public EObject getElement3(final CompositeDataType type_, final Repository repository_, final org.eclipse.uml2.uml.Package datatypesPackage_, final org.eclipse.uml2.uml.Class class_) {
      return datatypesPackage_;
    }
    
    public EObject getCorrepondenceSourceDatatypesPackage_(final CompositeDataType type_, final Repository repository_) {
      return repository_;
    }
  }
  
  public CompositeDataype_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType type_, final Repository repository_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.type_ = type_;this.repository_ = repository_;
  }
  
  private CompositeDataType type_;
  
  private Repository repository_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDataype_CreateMappingRoutine with input:");
    getLogger().debug("   type_: " + this.type_);
    getLogger().debug("   repository_: " + this.repository_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(type_, repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(type_, repository_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(type_, repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(type_, repository_)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Package datatypesPackage_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDatatypesPackage_(type_, repository_), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(type_, repository_), 
    	false // asserted
    	);
    if (datatypesPackage_ == null) {
    	return false;
    }
    registerObjectUnderModification(datatypesPackage_);
    org.eclipse.uml2.uml.Class class_ = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createClass();
    notifyObjectCreated(class_);
    
    addCorrespondenceBetween(userExecution.getElement1(type_, repository_, datatypesPackage_, class_), userExecution.getElement2(type_, repository_, datatypesPackage_, class_), userExecution.getTag1(type_, repository_, datatypesPackage_, class_));
    
    addCorrespondenceBetween(userExecution.getElement3(type_, repository_, datatypesPackage_, class_), userExecution.getElement4(type_, repository_, datatypesPackage_, class_), userExecution.getTag2(type_, repository_, datatypesPackage_, class_));
    
    addCorrespondenceBetween(userExecution.getElement5(type_, repository_, datatypesPackage_, class_), userExecution.getElement6(type_, repository_, datatypesPackage_, class_), userExecution.getTag3(type_, repository_, datatypesPackage_, class_));
    
    addCorrespondenceBetween(userExecution.getElement7(type_, repository_, datatypesPackage_, class_), userExecution.getElement8(type_, repository_, datatypesPackage_, class_), userExecution.getTag4(type_, repository_, datatypesPackage_, class_));
    
    userExecution.executeAction1(type_, repository_, datatypesPackage_, class_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(type_, repository_, datatypesPackage_, class_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
