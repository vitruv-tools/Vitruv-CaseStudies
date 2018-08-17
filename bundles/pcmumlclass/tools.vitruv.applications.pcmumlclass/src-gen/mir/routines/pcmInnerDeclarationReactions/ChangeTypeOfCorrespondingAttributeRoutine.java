package mir.routines.pcmInnerDeclarationReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmInnerDeclarationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfCorrespondingAttributeRoutine extends AbstractRepairRoutineRealization {
  private ChangeTypeOfCorrespondingAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final InnerDeclaration pcmInnerDeclaration, final DataType pcmDataType, final Property umlProperty, final Optional<CollectionDataType> previousTypeWasCollectionType, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = previousTypeWasCollectionType.isPresent();
      if (_isPresent) {
        _routinesFacade.pcmDataTypePropagationReactions.removeCollectionTypeCorrespondenceFromParameterOrProperty(previousTypeWasCollectionType.get(), umlProperty, umlProperty, TagLiterals.COLLECTION_DATATYPE__PROPERTY);
      }
      if ((pcmDataType instanceof CollectionDataType)) {
        _routinesFacade.pcmDataTypePropagationReactions.addCollectionTypeCorrespondenceToParameterOrProperty(((CollectionDataType)pcmDataType), umlProperty, umlProperty, TagLiterals.COLLECTION_DATATYPE__PROPERTY);
      } else {
        _routinesFacade.pcmDataTypePropagationReactions.replaceTypeOfCorrespondingParameterOrProperty(pcmDataType, umlProperty);
      }
    }
    
    public String getRetrieveTag1(final InnerDeclaration pcmInnerDeclaration, final DataType pcmDataType) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public EObject getCorrepondenceSourcePreviousTypeWasCollectionType(final InnerDeclaration pcmInnerDeclaration, final DataType pcmDataType, final Property umlProperty) {
      return umlProperty;
    }
    
    public EObject getCorrepondenceSourceUmlProperty(final InnerDeclaration pcmInnerDeclaration, final DataType pcmDataType) {
      return pcmInnerDeclaration;
    }
    
    public String getRetrieveTag2(final InnerDeclaration pcmInnerDeclaration, final DataType pcmDataType, final Property umlProperty) {
      return TagLiterals.COLLECTION_DATATYPE__PROPERTY;
    }
  }
  
  public ChangeTypeOfCorrespondingAttributeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration pcmInnerDeclaration, final DataType pcmDataType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInnerDeclarationReactions.ChangeTypeOfCorrespondingAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInnerDeclaration = pcmInnerDeclaration;this.pcmDataType = pcmDataType;
  }
  
  private InnerDeclaration pcmInnerDeclaration;
  
  private DataType pcmDataType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingAttributeRoutine with input:");
    getLogger().debug("   pcmInnerDeclaration: " + this.pcmInnerDeclaration);
    getLogger().debug("   pcmDataType: " + this.pcmDataType);
    
    org.eclipse.uml2.uml.Property umlProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlProperty(pcmInnerDeclaration, pcmDataType), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmInnerDeclaration, pcmDataType), 
    	false // asserted
    	);
    if (umlProperty == null) {
    	return false;
    }
    registerObjectUnderModification(umlProperty);
    	Optional<org.palladiosimulator.pcm.repository.CollectionDataType> previousTypeWasCollectionType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePreviousTypeWasCollectionType(pcmInnerDeclaration, pcmDataType, umlProperty), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CollectionDataType.class,
    		(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmInnerDeclaration, pcmDataType, umlProperty), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(previousTypeWasCollectionType.isPresent() ? previousTypeWasCollectionType.get() : null);
    userExecution.executeAction1(pcmInnerDeclaration, pcmDataType, umlProperty, previousTypeWasCollectionType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
