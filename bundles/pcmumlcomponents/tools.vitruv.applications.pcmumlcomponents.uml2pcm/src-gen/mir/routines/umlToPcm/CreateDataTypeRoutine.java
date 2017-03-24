package mir.routines.umlToPcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreateDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final DataType umlType, @Extension final RoutinesFacade _routinesFacade) {
      final String userPromptMsg = "Please select the PCM type to create";
      final List<? extends Class<? extends CDOObject>> pcmDataTypes = Collections.<Class<? extends CDOObject>>unmodifiableList(CollectionLiterals.<Class<? extends CDOObject>>newArrayList(CollectionDataType.class, CompositeDataType.class));
      int _size = pcmDataTypes.size();
      final List<String> pcmDataTypeNames = new ArrayList<String>(_size);
      for (final Class<? extends CDOObject> collectionDataType : pcmDataTypes) {
        pcmDataTypeNames.add(collectionDataType.getName());
      }
      final int userInput = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, userPromptMsg, ((String[])Conversions.unwrapArray(pcmDataTypeNames, String.class)));
      if ((userInput == 0)) {
        _routinesFacade.createCollectionDataType(umlType);
      } else {
        _routinesFacade.createCompositeDataType(umlType);
      }
    }
  }
  
  public CreateDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType umlType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreateDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlType = umlType;
  }
  
  private DataType umlType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateDataTypeRoutine with input:");
    getLogger().debug("   DataType: " + this.umlType);
    
    userExecution.callRoutine1(umlType, actionsFacade);
    
    postprocessElementStates();
  }
}
