package mir.routines.umlInnerDeclarationPropertyReactions;

import java.io.IOException;
import mir.routines.umlInnerDeclarationPropertyReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class MoveCorrespondingInnerDeclarationRoutine extends AbstractRepairRoutineRealization {
  private MoveCorrespondingInnerDeclarationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType, final InnerDeclaration pcmInnerDeclaration) {
      return pcmCompositeType;
    }
    
    public void update0Element(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType, final InnerDeclaration pcmInnerDeclaration) {
      EList<InnerDeclaration> _innerDeclaration_CompositeDataType = pcmCompositeType.getInnerDeclaration_CompositeDataType();
      _innerDeclaration_CompositeDataType.add(pcmInnerDeclaration);
    }
    
    public EObject getCorrepondenceSourcePcmInnerDeclaration(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeType(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
      return umlCompositeType;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
  }
  
  public MoveCorrespondingInnerDeclarationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInnerDeclarationPropertyReactions.MoveCorrespondingInnerDeclarationRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlCompositeType = umlCompositeType;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlCompositeType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MoveCorrespondingInnerDeclarationRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   umlCompositeType: " + this.umlCompositeType);
    
    org.palladiosimulator.pcm.repository.CompositeDataType pcmCompositeType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeType(umlProperty, umlCompositeType), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, umlCompositeType), 
    	false // asserted
    	);
    if (pcmCompositeType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCompositeType);
    org.palladiosimulator.pcm.repository.InnerDeclaration pcmInnerDeclaration = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInnerDeclaration(umlProperty, umlCompositeType, pcmCompositeType), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.InnerDeclaration.class,
    	(org.palladiosimulator.pcm.repository.InnerDeclaration _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlProperty, umlCompositeType, pcmCompositeType), 
    	false // asserted
    	);
    if (pcmInnerDeclaration == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInnerDeclaration);
    // val updatedElement userExecution.getElement1(umlProperty, umlCompositeType, pcmCompositeType, pcmInnerDeclaration);
    userExecution.update0Element(umlProperty, umlCompositeType, pcmCompositeType, pcmInnerDeclaration);
    
    postprocessElements();
    
    return true;
  }
}
