package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.applications.pcmumlcomp.pcm2uml.PcmToUmlUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCollectionDataTypeInnerTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveCollectionDataTypeInnerTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlInnerType(final CollectionDataType pcmDataType, final DataType pcmInnerType) {
      return pcmInnerType;
    }
    
    public void callRoutine1(final CollectionDataType pcmDataType, final DataType pcmInnerType, final org.eclipse.uml2.uml.DataType umlInnerType, @Extension final RoutinesFacade _routinesFacade) {
      PcmToUmlUtil.unsetTypeOccurences(pcmDataType);
    }
  }
  
  public RemoveCollectionDataTypeInnerTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType pcmDataType, final DataType pcmInnerType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.RemoveCollectionDataTypeInnerTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmDataType = pcmDataType;this.pcmInnerType = pcmInnerType;
  }
  
  private CollectionDataType pcmDataType;
  
  private DataType pcmInnerType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCollectionDataTypeInnerTypeRoutine with input:");
    getLogger().debug("   CollectionDataType: " + this.pcmDataType);
    getLogger().debug("   DataType: " + this.pcmInnerType);
    
    org.eclipse.uml2.uml.DataType umlInnerType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInnerType(pcmDataType, pcmInnerType), // correspondence source supplier
    	org.eclipse.uml2.uml.DataType.class,
    	(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    	null);
    if (umlInnerType == null) {
    	return;
    }
    registerObjectUnderModification(umlInnerType);
    userExecution.callRoutine1(pcmDataType, pcmInnerType, umlInnerType, actionsFacade);
    
    postprocessElements();
  }
}
