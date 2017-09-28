package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Type;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlType(final DataType dataType) {
      return dataType;
    }
    
    public EObject getElement1(final DataType dataType, final Type umlType) {
      return umlType;
    }
  }
  
  public DeleteDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType dataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.DeleteDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.dataType = dataType;
  }
  
  private DataType dataType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteDataTypeRoutine with input:");
    getLogger().debug("   dataType: " + this.dataType);
    
    org.eclipse.uml2.uml.Type umlType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlType(dataType), // correspondence source supplier
    	org.eclipse.uml2.uml.Type.class,
    	(org.eclipse.uml2.uml.Type _element) -> true, // correspondence precondition checker
    	null, 
    	true // asserted
    	);
    if (umlType == null) {
    	return false;
    }
    registerObjectUnderModification(umlType);
    deleteObject(userExecution.getElement1(dataType, umlType));
    
    postprocessElements();
    
    return true;
  }
}
