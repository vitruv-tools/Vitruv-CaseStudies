package mir.routines.umlCompositeDataTypeGeneralizationReactions;

import java.io.IOException;
import mir.routines.umlCompositeDataTypeGeneralizationReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCompositeDatatypeParentRoutine extends AbstractRepairRoutineRealization {
  private RemoveCompositeDatatypeParentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Class umlClass, final Classifier umlOldParent, final CompositeDataType pcmCompositeDataType, final CompositeDataType pcmParentCompositeDataType, @Extension final RoutinesFacade _routinesFacade) {
      boolean _contains = pcmCompositeDataType.getParentType_CompositeDataType().contains(pcmParentCompositeDataType);
      if (_contains) {
        EList<CompositeDataType> _parentType_CompositeDataType = pcmCompositeDataType.getParentType_CompositeDataType();
        _parentType_CompositeDataType.remove(pcmParentCompositeDataType);
      }
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class umlClass, final Classifier umlOldParent) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Class umlClass, final Classifier umlOldParent, final CompositeDataType pcmCompositeDataType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeDataType(final org.eclipse.uml2.uml.Class umlClass, final Classifier umlOldParent) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSourcePcmParentCompositeDataType(final org.eclipse.uml2.uml.Class umlClass, final Classifier umlOldParent, final CompositeDataType pcmCompositeDataType) {
      return umlOldParent;
    }
  }
  
  public RemoveCompositeDatatypeParentRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final Classifier umlOldParent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlCompositeDataTypeGeneralizationReactions.RemoveCompositeDatatypeParentRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlClass = umlClass;this.umlOldParent = umlOldParent;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private Classifier umlOldParent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCompositeDatatypeParentRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    getLogger().debug("   umlOldParent: " + this.umlOldParent);
    
    org.palladiosimulator.pcm.repository.CompositeDataType pcmCompositeDataType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeDataType(umlClass, umlOldParent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlClass, umlOldParent), 
    	false // asserted
    	);
    if (pcmCompositeDataType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCompositeDataType);
    org.palladiosimulator.pcm.repository.CompositeDataType pcmParentCompositeDataType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmParentCompositeDataType(umlClass, umlOldParent, pcmCompositeDataType), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlClass, umlOldParent, pcmCompositeDataType), 
    	false // asserted
    	);
    if (pcmParentCompositeDataType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmParentCompositeDataType);
    userExecution.executeAction1(umlClass, umlOldParent, pcmCompositeDataType, pcmParentCompositeDataType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
