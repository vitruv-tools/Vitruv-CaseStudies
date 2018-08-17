package mir.routines.umlAssemblyContextPropertyReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlAssemblyContextPropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingAssemblyContextRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingAssemblyContextRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final Optional<ComposedProvidingRequiringEntity> pcmCompositeComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public EObject getCorrepondenceSourcePcmInnerComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final Optional<ComposedProvidingRequiringEntity> pcmCompositeComponent) {
      Type _type = umlProperty.getType();
      return _type;
    }
    
    public void callRoutine1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final Optional<ComposedProvidingRequiringEntity> pcmCompositeComponent, final Optional<RepositoryComponent> pcmInnerComponent, @Extension final RoutinesFacade _routinesFacade) {
      if ((pcmCompositeComponent.isPresent() && (pcmInnerComponent.isPresent() || (umlProperty.getType() == null)))) {
        _routinesFacade.detectOrCreateCorrespondingAssemblyContext(umlProperty, umlComponent);
        _routinesFacade.moveCorrespondingAssemblyContext(umlProperty, umlComponent);
      } else {
        _routinesFacade.deleteCorrespondingAssemblyContext(umlProperty);
      }
    }
  }
  
  public InsertCorrespondingAssemblyContextRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlAssemblyContextPropertyReactions.InsertCorrespondingAssemblyContextRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlComponent = umlComponent;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingAssemblyContextRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    	Optional<org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity> pcmCompositeComponent = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmCompositeComponent(umlProperty, umlComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlProperty, umlComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmCompositeComponent.isPresent() ? pcmCompositeComponent.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.RepositoryComponent> pcmInnerComponent = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmInnerComponent(umlProperty, umlComponent, pcmCompositeComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    		(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlProperty, umlComponent, pcmCompositeComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmInnerComponent.isPresent() ? pcmInnerComponent.get() : null);
    userExecution.callRoutine1(umlProperty, umlComponent, pcmCompositeComponent, pcmInnerComponent, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
