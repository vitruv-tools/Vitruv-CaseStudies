package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.aplications.pcmumlcomp.pcm2uml.PcmToUmlUtil;
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
    
    public EObject getElement1(final PrimitiveDataType dataType, final Model umlModel, final PrimitiveType umlType) {
      return umlModel;
    }
    
    public void update0Element(final PrimitiveDataType dataType, final Model umlModel, final PrimitiveType umlType) {
      EList<PackageableElement> _packagedElements = umlModel.getPackagedElements();
      _packagedElements.add(umlType);
    }
    
    public EObject getElement2(final PrimitiveDataType dataType, final Model umlModel, final PrimitiveType umlType) {
      return dataType;
    }
    
    public EObject getElement3(final PrimitiveDataType dataType, final Model umlModel, final PrimitiveType umlType) {
      return umlType;
    }
    
    public EObject getCorrepondenceSourceUmlModel(final PrimitiveDataType dataType) {
      Repository _repository__DataType = dataType.getRepository__DataType();
      return _repository__DataType;
    }
    
    public void updateUmlTypeElement(final PrimitiveDataType dataType, final Model umlModel, final PrimitiveType umlType) {
      PrimitiveTypeEnum _type = dataType.getType();
      String _umlPrimitiveType = PcmToUmlUtil.getUmlPrimitiveType(_type);
      umlType.setName(_umlPrimitiveType);
    }
  }
  
  public CreatePrimitiveDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PrimitiveDataType dataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreatePrimitiveDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.dataType = dataType;
  }
  
  private PrimitiveDataType dataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePrimitiveDataTypeRoutine with input:");
    getLogger().debug("   PrimitiveDataType: " + this.dataType);
    
    Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(dataType), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlModel == null) {
    	return;
    }
    initializeRetrieveElementState(umlModel);
    PrimitiveType umlType = UMLFactoryImpl.eINSTANCE.createPrimitiveType();
    initializeCreateElementState(umlType);
    userExecution.updateUmlTypeElement(dataType, umlModel, umlType);
    
    // val updatedElement userExecution.getElement1(dataType, umlModel, umlType);
    userExecution.update0Element(dataType, umlModel, umlType);
    
    addCorrespondenceBetween(userExecution.getElement2(dataType, umlModel, umlType), userExecution.getElement3(dataType, umlModel, umlType), "");
    
    postprocessElementStates();
  }
}
