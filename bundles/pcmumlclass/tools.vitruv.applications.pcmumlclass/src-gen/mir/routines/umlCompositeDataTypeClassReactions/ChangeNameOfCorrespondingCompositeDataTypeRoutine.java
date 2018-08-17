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
public class ChangeNameOfCorrespondingCompositeDataTypeRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingCompositeDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final String newName, final CompositeDataType pcmCompositeType) {
      return pcmCompositeType;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final String newName, final CompositeDataType pcmCompositeType) {
      pcmCompositeType.setEntityName(newName);
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class umlClass, final String newName) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeType(final org.eclipse.uml2.uml.Class umlClass, final String newName) {
      return umlClass;
    }
  }
  
  public ChangeNameOfCorrespondingCompositeDataTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlCompositeDataTypeClassReactions.ChangeNameOfCorrespondingCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlClass = umlClass;this.newName = newName;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingCompositeDataTypeRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    getLogger().debug("   newName: " + this.newName);
    
    org.palladiosimulator.pcm.repository.CompositeDataType pcmCompositeType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeType(umlClass, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlClass, newName), 
    	false // asserted
    	);
    if (pcmCompositeType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCompositeType);
    // val updatedElement userExecution.getElement1(umlClass, newName, pcmCompositeType);
    userExecution.update0Element(umlClass, newName, pcmCompositeType);
    
    postprocessElements();
    
    return true;
  }
}
