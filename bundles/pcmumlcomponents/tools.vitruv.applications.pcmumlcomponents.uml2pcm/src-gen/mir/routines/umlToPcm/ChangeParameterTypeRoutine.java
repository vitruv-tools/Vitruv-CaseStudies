package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.UmlToPcmTypesUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeParameterTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeParameterTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public void update0Element(final Parameter umlParameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      DataType resolvedType = null;
      if ((((resolvedType == null) && (umlParameter.getType() != null)) && (umlParameter.getType() instanceof org.eclipse.uml2.uml.DataType))) {
        final boolean unbounded = ((umlParameter.lowerBound() != 1) || (umlParameter.upperBound() != 1));
        final Repository pcmRepository = pcmParameter.getOperationSignature__Parameter().getInterface__OperationSignature().getRepository__Interface();
        Type _type = umlParameter.getType();
        resolvedType = UmlToPcmTypesUtil.retrieveCorrespondingPcmType(((org.eclipse.uml2.uml.DataType) _type), pcmRepository, Boolean.valueOf(unbounded), this.userInteracting, this.correspondenceModel);
      }
      pcmParameter.setDataType__Parameter(resolvedType);
    }
    
    public EObject getCorrepondenceSourcePcmParameter(final Parameter umlParameter) {
      return umlParameter;
    }
  }
  
  public ChangeParameterTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.ChangeParameterTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlParameter = umlParameter;
  }
  
  private Parameter umlParameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeParameterTypeRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    
    org.palladiosimulator.pcm.repository.Parameter pcmParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmParameter(umlParameter), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmParameter == null) {
    	return false;
    }
    registerObjectUnderModification(pcmParameter);
    // val updatedElement userExecution.getElement1(umlParameter, pcmParameter);
    userExecution.update0Element(umlParameter, pcmParameter);
    
    postprocessElements();
    
    return true;
  }
}
