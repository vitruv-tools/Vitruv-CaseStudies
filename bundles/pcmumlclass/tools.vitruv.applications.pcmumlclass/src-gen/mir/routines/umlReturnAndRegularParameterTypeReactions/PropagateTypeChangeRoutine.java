package mir.routines.umlReturnAndRegularParameterTypeReactions;

import com.google.common.collect.Iterators;
import java.io.IOException;
import java.util.Optional;
import mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class PropagateTypeChangeRoutine extends AbstractRepairRoutineRealization {
  private PropagateTypeChangeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Parameter umlParameter, final Optional<OperationSignature> pcmSignature, final Optional<org.palladiosimulator.pcm.repository.Parameter> pcmParameter, final Optional<CollectionDataType> pcmCollectionType, @Extension final RoutinesFacade _routinesFacade) {
      if ((pcmSignature.isPresent() || pcmParameter.isPresent())) {
        CDOObject _xifexpression = null;
        boolean _isPresent = pcmSignature.isPresent();
        if (_isPresent) {
          _xifexpression = pcmSignature.get();
        } else {
          _xifexpression = pcmParameter.get();
        }
        final EObject pcmStoredElement = _xifexpression;
        final Repository pcmRepository = IteratorExtensions.<Repository>head(Iterators.<Repository>filter(pcmStoredElement.eResource().getAllContents(), Repository.class));
        final DataType pcmDataType = PcmUmlClassHelper.getCorrespondingPcmDataType(this.correspondenceModel, umlParameter.getType(), umlParameter.getLower(), umlParameter.getUpper(), pcmRepository);
        boolean _isPresent_1 = pcmSignature.isPresent();
        if (_isPresent_1) {
          OperationSignature _get = pcmSignature.get();
          _get.setReturnType__OperationSignature(pcmDataType);
        }
        boolean _isPresent_2 = pcmParameter.isPresent();
        if (_isPresent_2) {
          org.palladiosimulator.pcm.repository.Parameter _get_1 = pcmParameter.get();
          _get_1.setDataType__Parameter(pcmDataType);
        }
        boolean _isPresent_3 = pcmCollectionType.isPresent();
        if (_isPresent_3) {
          _routinesFacade.removeCorrespondenceForOldCollectionType_Parameter(umlParameter);
        }
        if ((pcmDataType instanceof CollectionDataType)) {
          _routinesFacade.addCorrespondenceForCollectionType_Parameter(umlParameter, ((CollectionDataType)pcmDataType));
        }
      }
    }
    
    public String getRetrieveTag1(final Parameter umlParameter) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public String getRetrieveTag2(final Parameter umlParameter, final Optional<OperationSignature> pcmSignature) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public String getRetrieveTag3(final Parameter umlParameter, final Optional<OperationSignature> pcmSignature, final Optional<org.palladiosimulator.pcm.repository.Parameter> pcmParameter) {
      return TagLiterals.COLLECTION_DATATYPE__PROPERTY;
    }
    
    public EObject getCorrepondenceSourcePcmCollectionType(final Parameter umlParameter, final Optional<OperationSignature> pcmSignature, final Optional<org.palladiosimulator.pcm.repository.Parameter> pcmParameter) {
      return umlParameter;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Parameter umlParameter) {
      return umlParameter;
    }
    
    public EObject getCorrepondenceSourcePcmParameter(final Parameter umlParameter, final Optional<OperationSignature> pcmSignature) {
      return umlParameter;
    }
  }
  
  public PropagateTypeChangeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlReturnAndRegularParameterTypeReactions.PropagateTypeChangeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;
  }
  
  private Parameter umlParameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PropagateTypeChangeRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    
    	Optional<org.palladiosimulator.pcm.repository.OperationSignature> pcmSignature = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmSignature(umlParameter), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationSignature.class,
    		(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlParameter), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmSignature.isPresent() ? pcmSignature.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.Parameter> pcmParameter = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmParameter(umlParameter, pcmSignature), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Parameter.class,
    		(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlParameter, pcmSignature), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmParameter.isPresent() ? pcmParameter.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.CollectionDataType> pcmCollectionType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmCollectionType(umlParameter, pcmSignature, pcmParameter), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CollectionDataType.class,
    		(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlParameter, pcmSignature, pcmParameter), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmCollectionType.isPresent() ? pcmCollectionType.get() : null);
    userExecution.executeAction1(umlParameter, pcmSignature, pcmParameter, pcmCollectionType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
