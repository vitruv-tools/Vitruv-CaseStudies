package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.applications.pcmumlcomp.uml2pcm.UmlToPcmUtil;
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
    
    public EObject getElement1(final PrimitiveType umlType, final Repository pcmRepository, final PrimitiveDataType pcmType) {
      return pcmRepository;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final PrimitiveType umlType) {
      Model _model = umlType.getModel();
      return _model;
    }
    
    public void update0Element(final PrimitiveType umlType, final Repository pcmRepository, final PrimitiveDataType pcmType) {
      EList<DataType> _dataTypes__Repository = pcmRepository.getDataTypes__Repository();
      _dataTypes__Repository.add(pcmType);
    }
    
    public EObject getElement2(final PrimitiveType umlType, final Repository pcmRepository, final PrimitiveDataType pcmType) {
      return umlType;
    }
    
    public void updatePcmTypeElement(final PrimitiveType umlType, final Repository pcmRepository, final PrimitiveDataType pcmType) {
      pcmType.setType(UmlToPcmUtil.getPcmPrimitiveType(umlType.getName()));
    }
    
    public EObject getElement3(final PrimitiveType umlType, final Repository pcmRepository, final PrimitiveDataType pcmType) {
      return pcmType;
    }
  }
  
  public CreatePrimitiveDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PrimitiveType umlType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreatePrimitiveDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlType = umlType;
  }
  
  private PrimitiveType umlType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePrimitiveDataTypeRoutine with input:");
    getLogger().debug("   PrimitiveType: " + this.umlType);
    
    Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlType), // correspondence source supplier
    	Repository.class,
    	(Repository _element) -> true, // correspondence precondition checker
    	null);
    if (pcmRepository == null) {
    	return;
    }
    registerObjectUnderModification(pcmRepository);
    PrimitiveDataType pcmType = RepositoryFactoryImpl.eINSTANCE.createPrimitiveDataType();
    userExecution.updatePcmTypeElement(umlType, pcmRepository, pcmType);
    
    // val updatedElement userExecution.getElement1(umlType, pcmRepository, pcmType);
    userExecution.update0Element(umlType, pcmRepository, pcmType);
    
    addCorrespondenceBetween(userExecution.getElement2(umlType, pcmRepository, pcmType), userExecution.getElement3(umlType, pcmRepository, pcmType), "");
    
    postprocessElements();
  }
}
