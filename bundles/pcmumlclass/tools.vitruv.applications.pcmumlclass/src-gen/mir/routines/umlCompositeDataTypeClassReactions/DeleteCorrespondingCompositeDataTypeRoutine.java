package mir.routines.umlCompositeDataTypeClassReactions;

import java.io.IOException;
import mir.routines.umlCompositeDataTypeClassReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingCompositeDataTypeRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingCompositeDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final CompositeDataType pcmCompositeType) {
      return pcmCompositeType;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class umlClass) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeType(final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final CompositeDataType pcmCompositeType) {
      return umlClass;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final CompositeDataType pcmCompositeType) {
      return pcmCompositeType;
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Class umlClass, final CompositeDataType pcmCompositeType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
  }
  
  public DeleteCorrespondingCompositeDataTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlCompositeDataTypeClassReactions.DeleteCorrespondingCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingCompositeDataTypeRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    
    org.palladiosimulator.pcm.repository.CompositeDataType pcmCompositeType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeType(umlClass), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlClass), 
    	false // asserted
    	);
    if (pcmCompositeType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCompositeType);
    removeCorrespondenceBetween(userExecution.getElement1(umlClass, pcmCompositeType), userExecution.getElement2(umlClass, pcmCompositeType), userExecution.getTag1(umlClass, pcmCompositeType));
    
    deleteObject(userExecution.getElement3(umlClass, pcmCompositeType));
    
    postprocessElements();
    
    return true;
  }
}
