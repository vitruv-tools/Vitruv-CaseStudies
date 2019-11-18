package mir.routines.umlXpcmDatatypes_L2R;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade;
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
    
    public EObject getCorrepondenceSource1(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
      return class_;
    }
    
    public EObject getCorrepondenceSource2(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
      return class_;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_Repository:repository";
    }
    
    public String getRetrieveTag3(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:datatypesPkg_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceRepository_(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
      return datatypesPackage_;
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public EObject getElement8(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return datatypesPackage_;
    }
    
    public EObject getElement6(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return datatypesPackage_;
    }
    
    public String getTag3(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Package:datatypesPackage_with_CompositeDataType:type";
    }
    
    public String getTag2(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_Repository:repository";
    }
    
    public EObject getElement7(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return repository_;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.compositeDataype_BidirectionalUpdate(class_, datatypesPackage_);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return type_;
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_, @Extension final RoutinesFacade _routinesFacade) {
      repository_.getDataTypes__Repository().add(type_);
    }
    
    public EObject getElement4(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return class_;
    }
    
    public EObject getElement5(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return type_;
    }
    
    public String getTag4(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Package:datatypesPackage_with_Repository:repository";
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return class_;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_, final Repository repository_, final CompositeDataType type_) {
      return repository_;
    }
  }
  
  public CompositeDataype_CreateMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.class_ = class_;this.datatypesPackage_ = datatypesPackage_;
  }
  
  private org.eclipse.uml2.uml.Class class_;
  
  private org.eclipse.uml2.uml.Package datatypesPackage_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDataype_CreateMappingRoutine with input:");
    getLogger().debug("   class_: " + this.class_);
    getLogger().debug("   datatypesPackage_: " + this.datatypesPackage_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(class_, datatypesPackage_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(class_, datatypesPackage_)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(class_, datatypesPackage_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(class_, datatypesPackage_)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.Repository repository_ = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepository_(class_, datatypesPackage_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(class_, datatypesPackage_), 
    	false // asserted
    	);
    if (repository_ == null) {
    	return false;
    }
    registerObjectUnderModification(repository_);
    org.palladiosimulator.pcm.repository.CompositeDataType type_ = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createCompositeDataType();
    notifyObjectCreated(type_);
    
    addCorrespondenceBetween(userExecution.getElement1(class_, datatypesPackage_, repository_, type_), userExecution.getElement2(class_, datatypesPackage_, repository_, type_), userExecution.getTag1(class_, datatypesPackage_, repository_, type_));
    
    addCorrespondenceBetween(userExecution.getElement3(class_, datatypesPackage_, repository_, type_), userExecution.getElement4(class_, datatypesPackage_, repository_, type_), userExecution.getTag2(class_, datatypesPackage_, repository_, type_));
    
    addCorrespondenceBetween(userExecution.getElement5(class_, datatypesPackage_, repository_, type_), userExecution.getElement6(class_, datatypesPackage_, repository_, type_), userExecution.getTag3(class_, datatypesPackage_, repository_, type_));
    
    addCorrespondenceBetween(userExecution.getElement7(class_, datatypesPackage_, repository_, type_), userExecution.getElement8(class_, datatypesPackage_, repository_, type_), userExecution.getTag4(class_, datatypesPackage_, repository_, type_));
    
    userExecution.executeAction1(class_, datatypesPackage_, repository_, type_, this.getRoutinesFacade());
    
    userExecution.callRoutine1(class_, datatypesPackage_, repository_, type_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
