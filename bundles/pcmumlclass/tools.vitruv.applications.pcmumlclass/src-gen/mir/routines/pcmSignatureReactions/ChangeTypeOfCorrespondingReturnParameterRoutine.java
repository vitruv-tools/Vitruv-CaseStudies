package mir.routines.pcmSignatureReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfCorrespondingReturnParameterRoutine extends AbstractRepairRoutineRealization {
  private ChangeTypeOfCorrespondingReturnParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationSignature pcmSignature, final DataType pcmDataType, final Parameter umlParam, final Optional<CollectionDataType> previousTypeWasCollectionType, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = previousTypeWasCollectionType.isPresent();
      if (_isPresent) {
        _routinesFacade.pcmDataTypePropagationReactions.removeCollectionTypeCorrespondenceFromParameterOrProperty(previousTypeWasCollectionType.get(), umlParam, umlParam, TagLiterals.COLLECTION_DATATYPE__PARAMETER);
      }
      if ((pcmDataType instanceof CollectionDataType)) {
        _routinesFacade.pcmDataTypePropagationReactions.addCollectionTypeCorrespondenceToParameterOrProperty(((CollectionDataType)pcmDataType), umlParam, umlParam, TagLiterals.COLLECTION_DATATYPE__PARAMETER);
      } else {
        _routinesFacade.pcmDataTypePropagationReactions.replaceTypeOfCorrespondingParameterOrProperty(pcmDataType, umlParam);
      }
    }
    
    public String getRetrieveTag1(final OperationSignature pcmSignature, final DataType pcmDataType) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getCorrepondenceSourcePreviousTypeWasCollectionType(final OperationSignature pcmSignature, final DataType pcmDataType, final Parameter umlParam) {
      return umlParam;
    }
    
    public EObject getCorrepondenceSourceUmlParam(final OperationSignature pcmSignature, final DataType pcmDataType) {
      return pcmSignature;
    }
    
    public String getRetrieveTag2(final OperationSignature pcmSignature, final DataType pcmDataType, final Parameter umlParam) {
      return TagLiterals.COLLECTION_DATATYPE__PARAMETER;
    }
  }
  
  public ChangeTypeOfCorrespondingReturnParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature, final DataType pcmDataType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.ChangeTypeOfCorrespondingReturnParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;this.pcmDataType = pcmDataType;
  }
  
  private OperationSignature pcmSignature;
  
  private DataType pcmDataType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingReturnParameterRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    getLogger().debug("   pcmDataType: " + this.pcmDataType);
    
    org.eclipse.uml2.uml.Parameter umlParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParam(pcmSignature, pcmDataType), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSignature, pcmDataType), 
    	false // asserted
    	);
    if (umlParam == null) {
    	return false;
    }
    registerObjectUnderModification(umlParam);
    	Optional<org.palladiosimulator.pcm.repository.CollectionDataType> previousTypeWasCollectionType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePreviousTypeWasCollectionType(pcmSignature, pcmDataType, umlParam), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CollectionDataType.class,
    		(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmSignature, pcmDataType, umlParam), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(previousTypeWasCollectionType.isPresent() ? previousTypeWasCollectionType.get() : null);
    userExecution.executeAction1(pcmSignature, pcmDataType, umlParam, previousTypeWasCollectionType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
