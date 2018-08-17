package mir.routines.umlAssemblyContextPropertyReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlAssemblyContextPropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfCorrespondingAssemblyContextRoutine extends AbstractRepairRoutineRealization {
  private ChangeTypeOfCorrespondingAssemblyContextRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmNewInnerComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlNewInnerComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final Optional<AssemblyContext> pcmAssemblyContext) {
      return umlNewInnerComponent;
    }
    
    public void executeAction1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlNewInnerComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final Optional<AssemblyContext> pcmAssemblyContext, final Optional<RepositoryComponent> pcmNewInnerComponent, @Extension final RoutinesFacade _routinesFacade) {
      if (((!pcmAssemblyContext.isPresent()) && pcmNewInnerComponent.isPresent())) {
        Element _owner = umlProperty.getOwner();
        _routinesFacade.createCorrespondingAssemblyContext(umlProperty, ((org.eclipse.uml2.uml.Class) _owner));
      } else {
        if ((pcmAssemblyContext.isPresent() && pcmNewInnerComponent.isPresent())) {
          AssemblyContext _get = pcmAssemblyContext.get();
          _get.setEncapsulatedComponent__AssemblyContext(pcmNewInnerComponent.get());
        } else {
          if ((pcmAssemblyContext.isPresent() && (pcmNewInnerComponent == null))) {
            AssemblyContext _get_1 = pcmAssemblyContext.get();
            _get_1.setEncapsulatedComponent__AssemblyContext(null);
          } else {
            this.getLogger().warn(
              ("The type of a uml::Property in a pcm::AssemblyContext ~ uml::Property correspondence" + "has been set to a non-RepositoryComponent type. This is against convention and the corresponding AssemblyContext will be deleted."));
            _routinesFacade.deleteCorrespondingAssemblyContext(umlProperty);
          }
        }
      }
    }
    
    public String getRetrieveTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlNewInnerComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlNewInnerComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public String getRetrieveTag3(final Property umlProperty, final org.eclipse.uml2.uml.Class umlNewInnerComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final Optional<AssemblyContext> pcmAssemblyContext) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourcePcmAssemblyContext(final Property umlProperty, final org.eclipse.uml2.uml.Class umlNewInnerComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent) {
      return umlProperty;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlNewInnerComponent) {
      Element _owner = umlProperty.getOwner();
      return _owner;
    }
  }
  
  public ChangeTypeOfCorrespondingAssemblyContextRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlNewInnerComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlAssemblyContextPropertyReactions.ChangeTypeOfCorrespondingAssemblyContextRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlNewInnerComponent = umlNewInnerComponent;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlNewInnerComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingAssemblyContextRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   umlNewInnerComponent: " + this.umlNewInnerComponent);
    
    org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity pcmCompositeComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeComponent(umlProperty, umlNewInnerComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, umlNewInnerComponent), 
    	false // asserted
    	);
    if (pcmCompositeComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCompositeComponent);
    	Optional<org.palladiosimulator.pcm.core.composition.AssemblyContext> pcmAssemblyContext = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmAssemblyContext(umlProperty, umlNewInnerComponent, pcmCompositeComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.core.composition.AssemblyContext.class,
    		(org.palladiosimulator.pcm.core.composition.AssemblyContext _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlProperty, umlNewInnerComponent, pcmCompositeComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmAssemblyContext.isPresent() ? pcmAssemblyContext.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.RepositoryComponent> pcmNewInnerComponent = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmNewInnerComponent(umlProperty, umlNewInnerComponent, pcmCompositeComponent, pcmAssemblyContext), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    		(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlProperty, umlNewInnerComponent, pcmCompositeComponent, pcmAssemblyContext), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmNewInnerComponent.isPresent() ? pcmNewInnerComponent.get() : null);
    userExecution.executeAction1(umlProperty, umlNewInnerComponent, pcmCompositeComponent, pcmAssemblyContext, pcmNewInnerComponent, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
