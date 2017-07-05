package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.UmlToPcmTypesUtil;
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
    
    public EObject getElement1(final DataType umlDataType, final CompositeDataType pcmCompositeType, final PrimitiveDataType pcmPrimitiveType, final CollectionDataType pcmCollectionType) {
      return pcmCompositeType;
    }
    
    public EObject getCorrepondenceSourcePcmPrimitiveType(final DataType umlDataType, final CompositeDataType pcmCompositeType) {
      return umlDataType;
    }
    
    public String getRetrieveTag1(final DataType umlDataType) {
      return "";
    }
    
    public EObject getCorrepondenceSourcePcmCompositeType(final DataType umlDataType) {
      return umlDataType;
    }
    
    public String getRetrieveTag2(final DataType umlDataType, final CompositeDataType pcmCompositeType) {
      return "";
    }
    
    public EObject getElement2(final DataType umlDataType, final CompositeDataType pcmCompositeType, final PrimitiveDataType pcmPrimitiveType, final CollectionDataType pcmCollectionType) {
      return pcmPrimitiveType;
    }
    
    public String getRetrieveTag3(final DataType umlDataType, final CompositeDataType pcmCompositeType, final PrimitiveDataType pcmPrimitiveType) {
      return UmlToPcmTypesUtil.COLLECTION_TYPE_TAG;
    }
    
    public EObject getElement3(final DataType umlDataType, final CompositeDataType pcmCompositeType, final PrimitiveDataType pcmPrimitiveType, final CollectionDataType pcmCollectionType) {
      return pcmCollectionType;
    }
    
    public EObject getCorrepondenceSourcePcmCollectionType(final DataType umlDataType, final CompositeDataType pcmCompositeType, final PrimitiveDataType pcmPrimitiveType) {
      return umlDataType;
    }
  }
  
  public DeleteDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType umlDataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.DeleteDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlDataType = umlDataType;
  }
  
  private DataType umlDataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteDataTypeRoutine with input:");
    getLogger().debug("   DataType: " + this.umlDataType);
    
    CompositeDataType pcmCompositeType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeType(umlDataType), // correspondence source supplier
    	CompositeDataType.class,
    	(CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlDataType));
    registerObjectUnderModification(pcmCompositeType);
    PrimitiveDataType pcmPrimitiveType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmPrimitiveType(umlDataType, pcmCompositeType), // correspondence source supplier
    	PrimitiveDataType.class,
    	(PrimitiveDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlDataType, pcmCompositeType));
    registerObjectUnderModification(pcmPrimitiveType);
    CollectionDataType pcmCollectionType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCollectionType(umlDataType, pcmCompositeType, pcmPrimitiveType), // correspondence source supplier
    	CollectionDataType.class,
    	(CollectionDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(umlDataType, pcmCompositeType, pcmPrimitiveType));
    registerObjectUnderModification(pcmCollectionType);
    deleteObject(userExecution.getElement1(umlDataType, pcmCompositeType, pcmPrimitiveType, pcmCollectionType));
    
    deleteObject(userExecution.getElement2(umlDataType, pcmCompositeType, pcmPrimitiveType, pcmCollectionType));
    
    deleteObject(userExecution.getElement3(umlDataType, pcmCompositeType, pcmPrimitiveType, pcmCollectionType));
    
    postprocessElements();
  }
}
