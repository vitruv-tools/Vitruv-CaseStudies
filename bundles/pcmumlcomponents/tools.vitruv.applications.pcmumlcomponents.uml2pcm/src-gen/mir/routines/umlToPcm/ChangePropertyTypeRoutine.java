package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.UmlToPcmTypesUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangePropertyTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangePropertyTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final DataType umlType, final InnerDeclaration pcmDeclaration) {
      return pcmDeclaration;
    }
    
    public void update0Element(final Property umlProperty, final DataType umlType, final InnerDeclaration pcmDeclaration) {
      final boolean unbounded = ((umlProperty.upperBound() != 1) || (umlProperty.lowerBound() != 1));
      CompositeDataType _compositeDataType_InnerDeclaration = pcmDeclaration.getCompositeDataType_InnerDeclaration();
      final Repository pcmRepository = _compositeDataType_InnerDeclaration.getRepository__DataType();
      org.palladiosimulator.pcm.repository.DataType _retrieveCorrespondingPcmType = UmlToPcmTypesUtil.retrieveCorrespondingPcmType(umlType, pcmRepository, Boolean.valueOf(unbounded), this.userInteracting, this.correspondenceModel);
      pcmDeclaration.setDatatype_InnerDeclaration(_retrieveCorrespondingPcmType);
    }
    
    public EObject getCorrepondenceSourcePcmDeclaration(final Property umlProperty, final DataType umlType) {
      return umlProperty;
    }
  }
  
  public ChangePropertyTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final DataType umlType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.ChangePropertyTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlType = umlType;
  }
  
  private Property umlProperty;
  
  private DataType umlType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangePropertyTypeRoutine with input:");
    getLogger().debug("   Property: " + this.umlProperty);
    getLogger().debug("   DataType: " + this.umlType);
    
    InnerDeclaration pcmDeclaration = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmDeclaration(umlProperty, umlType), // correspondence source supplier
    	InnerDeclaration.class,
    	(InnerDeclaration _element) -> true, // correspondence precondition checker
    	null);
    if (pcmDeclaration == null) {
    	return;
    }
    registerObjectUnderModification(pcmDeclaration);
    // val updatedElement userExecution.getElement1(umlProperty, umlType, pcmDeclaration);
    userExecution.update0Element(umlProperty, umlType, pcmDeclaration);
    
    postprocessElements();
  }
}
