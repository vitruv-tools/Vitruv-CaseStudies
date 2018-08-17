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
public class AddCorrespondenceForExistingCompositeDataTypeRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingCompositeDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CompositeDataType pcmCompositeType, final org.eclipse.uml2.uml.Class umlClass) {
      return pcmCompositeType;
    }
    
    public EObject getCorrepondenceSource1(final CompositeDataType pcmCompositeType, final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSource2(final CompositeDataType pcmCompositeType, final org.eclipse.uml2.uml.Class umlClass) {
      return pcmCompositeType;
    }
    
    public String getRetrieveTag1(final CompositeDataType pcmCompositeType, final org.eclipse.uml2.uml.Class umlClass) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public String getRetrieveTag2(final CompositeDataType pcmCompositeType, final org.eclipse.uml2.uml.Class umlClass) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getElement2(final CompositeDataType pcmCompositeType, final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public String getTag1(final CompositeDataType pcmCompositeType, final org.eclipse.uml2.uml.Class umlClass) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
  }
  
  public AddCorrespondenceForExistingCompositeDataTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType pcmCompositeType, final org.eclipse.uml2.uml.Class umlClass) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlCompositeDataTypeClassReactions.AddCorrespondenceForExistingCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmCompositeType = pcmCompositeType;this.umlClass = umlClass;
  }
  
  private CompositeDataType pcmCompositeType;
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingCompositeDataTypeRoutine with input:");
    getLogger().debug("   pcmCompositeType: " + this.pcmCompositeType);
    getLogger().debug("   umlClass: " + this.umlClass);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmCompositeType, umlClass), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmCompositeType, umlClass)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmCompositeType, umlClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmCompositeType, umlClass)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmCompositeType, umlClass), userExecution.getElement2(pcmCompositeType, umlClass), userExecution.getTag1(pcmCompositeType, umlClass));
    
    postprocessElements();
    
    return true;
  }
}
