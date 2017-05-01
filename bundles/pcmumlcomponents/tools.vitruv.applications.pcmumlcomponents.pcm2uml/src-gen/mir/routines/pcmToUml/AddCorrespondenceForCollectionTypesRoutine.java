package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrespondenceForCollectionTypesRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddCorrespondenceForCollectionTypesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CollectionDataType pcmType, final DataType innerType, final DataType oldInnerType) {
      return pcmType;
    }
    
    public String getRetrieveTag1(final CollectionDataType pcmType, final DataType innerType) {
      return "collectionType";
    }
    
    public EObject getElement2(final CollectionDataType pcmType, final DataType innerType, final DataType oldInnerType) {
      return innerType;
    }
    
    public String getTag1(final CollectionDataType pcmType, final DataType innerType, final DataType oldInnerType) {
      return "collectionType";
    }
    
    public EObject getCorrepondenceSourceOldInnerType(final CollectionDataType pcmType, final DataType innerType) {
      return pcmType;
    }
    
    public void callRoutine1(final CollectionDataType pcmType, final DataType innerType, final DataType oldInnerType, @Extension final RoutinesFacade _routinesFacade) {
      InputOutput.<String>println("created correspondence between an inner type and a uml type");
    }
  }
  
  public AddCorrespondenceForCollectionTypesRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType pcmType, final DataType innerType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.AddCorrespondenceForCollectionTypesRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmType = pcmType;this.innerType = innerType;
  }
  
  private CollectionDataType pcmType;
  
  private DataType innerType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForCollectionTypesRoutine with input:");
    getLogger().debug("   CollectionDataType: " + this.pcmType);
    getLogger().debug("   DataType: " + this.innerType);
    
    DataType oldInnerType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOldInnerType(pcmType, innerType), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmType, innerType));
    registerObjectUnderModification(oldInnerType);
    userExecution.callRoutine1(pcmType, innerType, oldInnerType, actionsFacade);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmType, innerType, oldInnerType), userExecution.getElement2(pcmType, innerType, oldInnerType), userExecution.getTag1(pcmType, innerType, oldInnerType));
    
    postprocessElements();
  }
}
