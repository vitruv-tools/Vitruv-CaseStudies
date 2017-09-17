package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Type;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompositeDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCompositeDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CompositeDataType dataType, final Model umlModel, final DataType umlType) {
      return umlModel;
    }
    
    public void update0Element(final CompositeDataType dataType, final Model umlModel, final DataType umlType) {
      EList<Type> _ownedTypes = umlModel.getOwnedTypes();
      _ownedTypes.add(umlType);
    }
    
    public EObject getElement2(final CompositeDataType dataType, final Model umlModel, final DataType umlType) {
      return dataType;
    }
    
    public EObject getElement3(final CompositeDataType dataType, final Model umlModel, final DataType umlType) {
      return umlType;
    }
    
    public EObject getCorrepondenceSourceUmlModel(final CompositeDataType dataType) {
      Repository _repository__DataType = dataType.getRepository__DataType();
      return _repository__DataType;
    }
    
    public void updateUmlTypeElement(final CompositeDataType dataType, final Model umlModel, final DataType umlType) {
      umlType.setName(dataType.getEntityName());
    }
  }
  
  public CreateCompositeDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType dataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.dataType = dataType;
  }
  
  private CompositeDataType dataType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompositeDataTypeRoutine with input:");
    getLogger().debug("   dataType: " + this.dataType);
    
    org.eclipse.uml2.uml.Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(dataType), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlModel == null) {
    	return false;
    }
    registerObjectUnderModification(umlModel);
    org.eclipse.uml2.uml.DataType umlType = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createDataType();
    notifyObjectCreated(umlType);
    userExecution.updateUmlTypeElement(dataType, umlModel, umlType);
    
    // val updatedElement userExecution.getElement1(dataType, umlModel, umlType);
    userExecution.update0Element(dataType, umlModel, umlType);
    
    addCorrespondenceBetween(userExecution.getElement2(dataType, umlModel, umlType), userExecution.getElement3(dataType, umlModel, umlType), "");
    
    postprocessElements();
    
    return true;
  }
}
