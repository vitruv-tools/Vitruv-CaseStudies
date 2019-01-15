package mir.routines.umlInnerDeclarationPropertyReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlInnerDeclarationPropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingInnerDeclarationRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingInnerDeclarationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
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
    
    public void callRoutine1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType, final Optional<InnerDeclaration> pcmInnerDeclaration, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmInnerDeclaration.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<InnerDeclaration, Boolean> _function = (InnerDeclaration it) -> {
          String _entityName = it.getEntityName();
          String _name = umlProperty.getName();
          return Boolean.valueOf((_entityName == _name));
        };
        final InnerDeclaration pcmInnerDeclarationCandidate = IterableExtensions.<InnerDeclaration>findFirst(pcmCompositeType.getInnerDeclaration_CompositeDataType(), _function);
        if ((pcmInnerDeclarationCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingInnerDeclaration(umlProperty, pcmInnerDeclarationCandidate);
        } else {
          _routinesFacade.createCorrespondingInnerDeclaration(umlProperty, umlCompositeType);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingInnerDeclarationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInnerDeclarationPropertyReactions.DetectOrCreateCorrespondingInnerDeclarationRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlCompositeType = umlCompositeType;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlCompositeType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingInnerDeclarationRoutine with input:");
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
    	Optional<org.palladiosimulator.pcm.repository.InnerDeclaration> pcmInnerDeclaration = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmInnerDeclaration(umlProperty, umlCompositeType, pcmCompositeType), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.InnerDeclaration.class,
    		(org.palladiosimulator.pcm.repository.InnerDeclaration _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlProperty, umlCompositeType, pcmCompositeType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmInnerDeclaration.isPresent() ? pcmInnerDeclaration.get() : null);
    userExecution.callRoutine1(umlProperty, umlCompositeType, pcmCompositeType, pcmInnerDeclaration, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
