package mir.routines.umlCompositeDataTypeClassReactions;

import java.io.IOException;
import mir.routines.umlCompositeDataTypeClassReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCorrespondingCompositeTypeRoutine extends AbstractRepairRoutineRealization {
  private RemoveCorrespondingCompositeTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final CompositeDataType pcmCompositeType, final Repository pcmRepository) {
      return pcmCompositeType;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final CompositeDataType pcmCompositeType) {
      return umlPackage;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final CompositeDataType pcmCompositeType, final Repository pcmRepository) {
      EList<DataType> _dataTypes__Repository = pcmRepository.getDataTypes__Repository();
      _dataTypes__Repository.remove(pcmCompositeType);
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeType(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
      return umlClass;
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final CompositeDataType pcmCompositeType) {
      return TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE;
    }
  }
  
  public RemoveCorrespondingCompositeTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlCompositeDataTypeClassReactions.RemoveCorrespondingCompositeTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlClass = umlClass;this.umlPackage = umlPackage;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private org.eclipse.uml2.uml.Package umlPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondingCompositeTypeRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    getLogger().debug("   umlPackage: " + this.umlPackage);
    
    org.palladiosimulator.pcm.repository.CompositeDataType pcmCompositeType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeType(umlClass, umlPackage), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlClass, umlPackage), 
    	false // asserted
    	);
    if (pcmCompositeType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCompositeType);
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlClass, umlPackage, pcmCompositeType), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlClass, umlPackage, pcmCompositeType), 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    // val updatedElement userExecution.getElement1(umlClass, umlPackage, pcmCompositeType, pcmRepository);
    userExecution.update0Element(umlClass, umlPackage, pcmCompositeType, pcmRepository);
    
    postprocessElements();
    
    return true;
  }
}
