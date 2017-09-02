package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.PcmToUmlUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInnerDeclarationTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeInnerDeclarationTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InnerDeclaration innerDeclaration, final DataType pcmDataType, final Property umlProperty, final Model umlModel) {
      return umlProperty;
    }
    
    public void update0Element(final InnerDeclaration innerDeclaration, final DataType pcmDataType, final Property umlProperty, final Model umlModel) {
      if (((pcmDataType == null) || ((pcmDataType instanceof CollectionDataType) && (((CollectionDataType) pcmDataType).getInnerType_CollectionDataType() == null)))) {
        umlProperty.setType(null);
      } else {
        umlProperty.setType(PcmToUmlUtil.retrieveUmlType(this.correspondenceModel, pcmDataType, umlModel));
      }
      PcmToUmlUtil.updateMultiplicity(umlProperty, Boolean.valueOf(((umlProperty.getType() != null) && (pcmDataType instanceof CollectionDataType))));
    }
    
    public EObject getCorrepondenceSourceUmlProperty(final InnerDeclaration innerDeclaration, final DataType pcmDataType) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceUmlModel(final InnerDeclaration innerDeclaration, final DataType pcmDataType, final Property umlProperty) {
      Repository _repository__DataType = innerDeclaration.getCompositeDataType_InnerDeclaration().getRepository__DataType();
      return _repository__DataType;
    }
  }
  
  public ChangeInnerDeclarationTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration, final DataType pcmDataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.ChangeInnerDeclarationTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.innerDeclaration = innerDeclaration;this.pcmDataType = pcmDataType;
  }
  
  private InnerDeclaration innerDeclaration;
  
  private DataType pcmDataType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInnerDeclarationTypeRoutine with input:");
    getLogger().debug("   innerDeclaration: " + this.innerDeclaration);
    getLogger().debug("   pcmDataType: " + this.pcmDataType);
    
    org.eclipse.uml2.uml.Property umlProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlProperty(innerDeclaration, pcmDataType), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlProperty == null) {
    	return false;
    }
    registerObjectUnderModification(umlProperty);
    org.eclipse.uml2.uml.Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(innerDeclaration, pcmDataType, umlProperty), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlModel == null) {
    	return false;
    }
    registerObjectUnderModification(umlModel);
    // val updatedElement userExecution.getElement1(innerDeclaration, pcmDataType, umlProperty, umlModel);
    userExecution.update0Element(innerDeclaration, pcmDataType, umlProperty, umlModel);
    
    postprocessElements();
    
    return true;
  }
}
