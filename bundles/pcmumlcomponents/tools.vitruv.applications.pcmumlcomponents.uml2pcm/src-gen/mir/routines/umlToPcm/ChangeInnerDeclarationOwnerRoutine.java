package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInnerDeclarationOwnerRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeInnerDeclarationOwnerRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration, final CompositeDataType pcmOwner) {
      return pcmOwner;
    }
    
    public void update0Element(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration, final CompositeDataType pcmOwner) {
      EList<InnerDeclaration> _innerDeclaration_CompositeDataType = pcmOwner.getInnerDeclaration_CompositeDataType();
      _innerDeclaration_CompositeDataType.add(pcmInnerDeclaration);
    }
    
    public EObject getCorrepondenceSourcePcmInnerDeclaration(final Property umlProperty) {
      return umlProperty;
    }
    
    public EObject getCorrepondenceSourcePcmOwner(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
      Element _owner = umlProperty.getOwner();
      return _owner;
    }
  }
  
  public ChangeInnerDeclarationOwnerRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.ChangeInnerDeclarationOwnerRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlProperty = umlProperty;
  }
  
  private Property umlProperty;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInnerDeclarationOwnerRoutine with input:");
    getLogger().debug("   Property: " + this.umlProperty);
    
    InnerDeclaration pcmInnerDeclaration = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInnerDeclaration(umlProperty), // correspondence source supplier
    	InnerDeclaration.class,
    	(InnerDeclaration _element) -> true, // correspondence precondition checker
    	null);
    if (pcmInnerDeclaration == null) {
    	return;
    }
    initializeRetrieveElementState(pcmInnerDeclaration);
    CompositeDataType pcmOwner = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmOwner(umlProperty, pcmInnerDeclaration), // correspondence source supplier
    	CompositeDataType.class,
    	(CompositeDataType _element) -> true, // correspondence precondition checker
    	null);
    if (pcmOwner == null) {
    	return;
    }
    initializeRetrieveElementState(pcmOwner);
    // val updatedElement userExecution.getElement1(umlProperty, pcmInnerDeclaration, pcmOwner);
    userExecution.update0Element(umlProperty, pcmInnerDeclaration, pcmOwner);
    
    postprocessElementStates();
  }
}
