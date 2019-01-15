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
public class AddCorrespondenceForExistingCompositeTypeClassRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingCompositeTypeClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return pcmType;
    }
    
    public EObject getCorrepondenceSource1(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return umlCompositeClass;
    }
    
    public EObject getCorrepondenceSource2(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return pcmType;
    }
    
    public String getRetrieveTag1(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public String getRetrieveTag2(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getElement2(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return umlCompositeClass;
    }
    
    public String getTag1(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
  }
  
  public AddCorrespondenceForExistingCompositeTypeClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmCompositeDataTypeReactions.AddCorrespondenceForExistingCompositeTypeClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.umlCompositeClass = umlCompositeClass;
  }
  
  private CompositeDataType pcmType;
  
  private org.eclipse.uml2.uml.Class umlCompositeClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingCompositeTypeClassRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   umlCompositeClass: " + this.umlCompositeClass);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmType, umlCompositeClass), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmType, umlCompositeClass)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmType, umlCompositeClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmType, umlCompositeClass)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmType, umlCompositeClass), userExecution.getElement2(pcmType, umlCompositeClass), userExecution.getTag1(pcmType, umlCompositeClass));
    
    postprocessElements();
    
    return true;
  }
}
