package mir.routines.pcmCompositeDataTypeReactions;

import java.io.IOException;
import mir.routines.pcmCompositeDataTypeReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingCompositeTypeClassRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingCompositeTypeClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return pcmType;
    }
    
    public EObject getCorrepondenceSourceUmlCompositeClass(final CompositeDataType pcmType) {
      return pcmType;
    }
    
    public String getRetrieveTag1(final CompositeDataType pcmType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getElement2(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return umlCompositeClass;
    }
    
    public EObject getElement3(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return umlCompositeClass;
    }
    
    public String getTag1(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
  }
  
  public DeleteCorrespondingCompositeTypeClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType pcmType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmCompositeDataTypeReactions.DeleteCorrespondingCompositeTypeClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;
  }
  
  private CompositeDataType pcmType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingCompositeTypeClassRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    
    org.eclipse.uml2.uml.Class umlCompositeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlCompositeClass(pcmType), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmType), 
    	false // asserted
    	);
    if (umlCompositeClass == null) {
    	return false;
    }
    registerObjectUnderModification(umlCompositeClass);
    removeCorrespondenceBetween(userExecution.getElement1(pcmType, umlCompositeClass), userExecution.getElement2(pcmType, umlCompositeClass), userExecution.getTag1(pcmType, umlCompositeClass));
    
    deleteObject(userExecution.getElement3(pcmType, umlCompositeClass));
    
    postprocessElements();
    
    return true;
  }
}
