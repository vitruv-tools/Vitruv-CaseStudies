package mir.routines.pcmToUml;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.PcmToUmlUtil;
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
    
    public EObject getElement1(final CollectionDataType pcmType, final DataType innerType, final Optional<DataType> oldInnerType) {
      return pcmType;
    }
    
    public String getRetrieveTag1(final CollectionDataType pcmType, final DataType innerType) {
      return PcmToUmlUtil.COLLECTION_TYPE_TAG;
    }
    
    public EObject getElement2(final CollectionDataType pcmType, final DataType innerType, final Optional<DataType> oldInnerType) {
      return innerType;
    }
    
    public String getTag1(final CollectionDataType pcmType, final DataType innerType, final Optional<DataType> oldInnerType) {
      return PcmToUmlUtil.COLLECTION_TYPE_TAG;
    }
    
    public EObject getCorrepondenceSourceOldInnerType(final CollectionDataType pcmType, final DataType innerType) {
      return pcmType;
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
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForCollectionTypesRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   innerType: " + this.innerType);
    
    	Optional<org.eclipse.uml2.uml.DataType> oldInnerType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOldInnerType(pcmType, innerType), // correspondence source supplier
    		org.eclipse.uml2.uml.DataType.class,
    		(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(pcmType, innerType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(oldInnerType.isPresent() ? oldInnerType.get() : null);
    addCorrespondenceBetween(userExecution.getElement1(pcmType, innerType, oldInnerType), userExecution.getElement2(pcmType, innerType, oldInnerType), userExecution.getTag1(pcmType, innerType, oldInnerType));
    
    postprocessElements();
    
    return true;
  }
}
