package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.ContainersPackage;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddDataTypeInRepositoryRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddDataTypeInRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType pcmDataType, final Repository pcmRepository) {
      return pcmDataType;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final DataType pcmDataType) {
      return ContainersPackage.Literals.PACKAGE;
    }
    
    public void update0Element(final DataType pcmDataType, final Repository pcmRepository) {
      pcmDataType.setRepository__DataType(pcmRepository);
    }
    
    public EObject getElement2(final DataType pcmDataType, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public void update1Element(final DataType pcmDataType, final Repository pcmRepository) {
      EList<DataType> _dataTypes__Repository = pcmRepository.getDataTypes__Repository();
      _dataTypes__Repository.add(pcmDataType);
    }
  }
  
  public AddDataTypeInRepositoryRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType pcmDataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.AddDataTypeInRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.pcmDataType = pcmDataType;
  }
  
  private DataType pcmDataType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddDataTypeInRepositoryRoutine with input:");
    getLogger().debug("   pcmDataType: " + this.pcmDataType);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(pcmDataType), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    // val updatedElement userExecution.getElement1(pcmDataType, pcmRepository);
    userExecution.update0Element(pcmDataType, pcmRepository);
    
    // val updatedElement userExecution.getElement2(pcmDataType, pcmRepository);
    userExecution.update1Element(pcmDataType, pcmRepository);
    
    postprocessElements();
    
    return true;
  }
}
