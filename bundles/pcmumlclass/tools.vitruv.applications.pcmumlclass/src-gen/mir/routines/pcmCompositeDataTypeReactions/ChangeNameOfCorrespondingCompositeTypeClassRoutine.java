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
public class ChangeNameOfCorrespondingCompositeTypeClassRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingCompositeTypeClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CompositeDataType pcmType, final String newName, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return umlCompositeClass;
    }
    
    public void update0Element(final CompositeDataType pcmType, final String newName, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      umlCompositeClass.setName(newName);
    }
    
    public EObject getCorrepondenceSourceUmlCompositeClass(final CompositeDataType pcmType, final String newName) {
      return pcmType;
    }
    
    public String getRetrieveTag1(final CompositeDataType pcmType, final String newName) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
  }
  
  public ChangeNameOfCorrespondingCompositeTypeClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType pcmType, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmCompositeDataTypeReactions.ChangeNameOfCorrespondingCompositeTypeClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.newName = newName;
  }
  
  private CompositeDataType pcmType;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingCompositeTypeClassRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   newName: " + this.newName);
    
    org.eclipse.uml2.uml.Class umlCompositeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlCompositeClass(pcmType, newName), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmType, newName), 
    	false // asserted
    	);
    if (umlCompositeClass == null) {
    	return false;
    }
    registerObjectUnderModification(umlCompositeClass);
    // val updatedElement userExecution.getElement1(pcmType, newName, umlCompositeClass);
    userExecution.update0Element(pcmType, newName, umlCompositeClass);
    
    postprocessElements();
    
    return true;
  }
}
