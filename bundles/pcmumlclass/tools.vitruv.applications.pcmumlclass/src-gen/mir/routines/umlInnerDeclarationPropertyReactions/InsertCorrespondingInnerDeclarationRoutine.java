package mir.routines.umlInnerDeclarationPropertyReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlInnerDeclarationPropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingInnerDeclarationRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingInnerDeclarationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeType(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
      return umlCompositeType;
    }
    
    public void callRoutine1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final Optional<CompositeDataType> pcmCompositeType, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmCompositeType.isPresent();
      if (_isPresent) {
        _routinesFacade.detectOrCreateCorrespondingInnerDeclaration(umlProperty, umlCompositeType);
        _routinesFacade.moveCorrespondingInnerDeclaration(umlProperty, umlCompositeType);
      } else {
        _routinesFacade.deleteCorrespondingInnerDeclaration(umlProperty);
      }
    }
  }
  
  public InsertCorrespondingInnerDeclarationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInnerDeclarationPropertyReactions.InsertCorrespondingInnerDeclarationRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlCompositeType = umlCompositeType;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlCompositeType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingInnerDeclarationRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   umlCompositeType: " + this.umlCompositeType);
    
    	Optional<org.palladiosimulator.pcm.repository.CompositeDataType> pcmCompositeType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmCompositeType(umlProperty, umlCompositeType), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CompositeDataType.class,
    		(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlProperty, umlCompositeType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmCompositeType.isPresent() ? pcmCompositeType.get() : null);
    userExecution.callRoutine1(umlProperty, umlCompositeType, pcmCompositeType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
