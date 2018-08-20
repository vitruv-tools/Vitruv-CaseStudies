package mir.routines.umlInnerDeclarationPropertyReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlInnerDeclarationPropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
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
    
    public void executeAction1(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration, final Optional<CollectionDataType> pcmOldCollectionType, @Extension final RoutinesFacade _routinesFacade) {
      final DataType pcmDataType = PcmUmlClassHelper.getCorrespondingPcmDataType(this.correspondenceModel, umlProperty.getType(), umlProperty.getLower(), umlProperty.getUpper());
      pcmInnerDeclaration.setDatatype_InnerDeclaration(pcmDataType);
      if ((pcmOldCollectionType.isPresent() && (pcmOldCollectionType.get() != pcmDataType))) {
        _routinesFacade.removeCorrespondenceForOldCollectionType(umlProperty);
      }
      if ((pcmDataType instanceof CollectionDataType)) {
        _routinesFacade.addCorrespondenceForCollectionType(umlProperty, ((CollectionDataType) pcmDataType));
      }
    }
    
    public EObject getCorrepondenceSourcePcmInnerDeclaration(final Property umlProperty) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public EObject getCorrepondenceSourcePcmOldCollectionType(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return umlProperty;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      return TagLiterals.COLLECTION_DATATYPE__PROPERTY;
    }
  }
  
  public PropagateTypeChangeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInnerDeclarationPropertyReactions.PropagateTypeChangeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;
  }
  
  private Property umlProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PropagateTypeChangeRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    
    org.palladiosimulator.pcm.repository.InnerDeclaration pcmInnerDeclaration = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInnerDeclaration(umlProperty), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.InnerDeclaration.class,
    	(org.palladiosimulator.pcm.repository.InnerDeclaration _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty), 
    	false // asserted
    	);
    if (pcmInnerDeclaration == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInnerDeclaration);
    	Optional<org.palladiosimulator.pcm.repository.CollectionDataType> pcmOldCollectionType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmOldCollectionType(umlProperty, pcmInnerDeclaration), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CollectionDataType.class,
    		(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlProperty, pcmInnerDeclaration), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmOldCollectionType.isPresent() ? pcmOldCollectionType.get() : null);
    userExecution.executeAction1(umlProperty, pcmInnerDeclaration, pcmOldCollectionType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
