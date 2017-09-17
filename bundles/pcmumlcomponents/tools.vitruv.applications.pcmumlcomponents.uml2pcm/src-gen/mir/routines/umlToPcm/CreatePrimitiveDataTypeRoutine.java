package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.UmlToPcmTypesUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatePrimitiveDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePrimitiveDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final PrimitiveType umlType) {
      Model _model = umlType.getModel();
      return _model;
    }
    
    public void callRoutine1(final PrimitiveType umlType, final Repository pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      UmlToPcmTypesUtil.retrieveCorrespondingPcmType(umlType, pcmRepository, Boolean.valueOf(false), this.userInteracting, this.correspondenceModel);
    }
  }
  
  public CreatePrimitiveDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PrimitiveType umlType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreatePrimitiveDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlType = umlType;
  }
  
  private PrimitiveType umlType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePrimitiveDataTypeRoutine with input:");
    getLogger().debug("   umlType: " + this.umlType);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlType), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    userExecution.callRoutine1(umlType, pcmRepository, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
