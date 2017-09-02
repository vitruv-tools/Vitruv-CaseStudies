package mir.routines.umlToPcm;

import java.io.IOException;
import java.util.Optional;
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
    
    public EObject getElement1(final DataType umlDataType, final Optional<CompositeDataType> pcmCompositeType, final Optional<PrimitiveDataType> pcmPrimitiveType, final Optional<CollectionDataType> pcmCollectionType) {
      CompositeDataType _xifexpression = null;
      boolean _isPresent = pcmCompositeType.isPresent();
      if (_isPresent) {
        _xifexpression = pcmCompositeType.get();
      }
      return _xifexpression;
    }
    
    public EObject getCorrepondenceSourcePcmPrimitiveType(final DataType umlDataType, final Optional<CompositeDataType> pcmCompositeType) {
      return umlDataType;
    }
    
    public String getRetrieveTag1(final DataType umlDataType) {
      return "";
    }
    
    public EObject getCorrepondenceSourcePcmCompositeType(final DataType umlDataType) {
      return umlDataType;
    }
    
    public String getRetrieveTag2(final DataType umlDataType, final Optional<CompositeDataType> pcmCompositeType) {
      return "";
    }
    
    public EObject getElement2(final DataType umlDataType, final Optional<CompositeDataType> pcmCompositeType, final Optional<PrimitiveDataType> pcmPrimitiveType, final Optional<CollectionDataType> pcmCollectionType) {
      PrimitiveDataType _xifexpression = null;
      boolean _isPresent = pcmPrimitiveType.isPresent();
      if (_isPresent) {
        _xifexpression = pcmPrimitiveType.get();
      }
      return _xifexpression;
    }
    
    public String getRetrieveTag3(final DataType umlDataType, final Optional<CompositeDataType> pcmCompositeType, final Optional<PrimitiveDataType> pcmPrimitiveType) {
      return UmlToPcmTypesUtil.COLLECTION_TYPE_TAG;
    }
    
    public EObject getElement3(final DataType umlDataType, final Optional<CompositeDataType> pcmCompositeType, final Optional<PrimitiveDataType> pcmPrimitiveType, final Optional<CollectionDataType> pcmCollectionType) {
      CollectionDataType _xifexpression = null;
      boolean _isPresent = pcmCollectionType.isPresent();
      if (_isPresent) {
        _xifexpression = pcmCollectionType.get();
      }
      return _xifexpression;
    }
    
    public EObject getCorrepondenceSourcePcmCollectionType(final DataType umlDataType, final Optional<CompositeDataType> pcmCompositeType, final Optional<PrimitiveDataType> pcmPrimitiveType) {
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
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteDataTypeRoutine with input:");
    getLogger().debug("   umlDataType: " + this.umlDataType);
    
    	Optional<org.palladiosimulator.pcm.repository.CompositeDataType> pcmCompositeType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmCompositeType(umlDataType), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CompositeDataType.class,
    		(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlDataType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmCompositeType.isPresent() ? pcmCompositeType.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.PrimitiveDataType> pcmPrimitiveType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmPrimitiveType(umlDataType, pcmCompositeType), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.PrimitiveDataType.class,
    		(org.palladiosimulator.pcm.repository.PrimitiveDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlDataType, pcmCompositeType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmPrimitiveType.isPresent() ? pcmPrimitiveType.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.CollectionDataType> pcmCollectionType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmCollectionType(umlDataType, pcmCompositeType, pcmPrimitiveType), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CollectionDataType.class,
    		(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlDataType, pcmCompositeType, pcmPrimitiveType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmCollectionType.isPresent() ? pcmCollectionType.get() : null);
    deleteObject(userExecution.getElement1(umlDataType, pcmCompositeType, pcmPrimitiveType, pcmCollectionType));
    
    deleteObject(userExecution.getElement2(umlDataType, pcmCompositeType, pcmPrimitiveType, pcmCollectionType));
    
    deleteObject(userExecution.getElement3(umlDataType, pcmCompositeType, pcmPrimitiveType, pcmCollectionType));
    
    postprocessElements();
    
    return true;
  }
}
