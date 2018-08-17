package mir.routines.umlAssemblyContextPropertyReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlAssemblyContextPropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingAssemblyContextRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingAssemblyContextRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag3(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final RepositoryComponent pcmInnerComponent) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getCorrepondenceSourcePcmAssemblyContext(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final RepositoryComponent pcmInnerComponent) {
      return umlProperty;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public EObject getCorrepondenceSourcePcmInnerComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent) {
      Type _type = umlProperty.getType();
      return _type;
    }
    
    public void callRoutine1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final RepositoryComponent pcmInnerComponent, final Optional<AssemblyContext> pcmAssemblyContext, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmAssemblyContext.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<AssemblyContext, Boolean> _function = (AssemblyContext it) -> {
          RepositoryComponent _encapsulatedComponent__AssemblyContext = it.getEncapsulatedComponent__AssemblyContext();
          return Boolean.valueOf((_encapsulatedComponent__AssemblyContext == pcmInnerComponent));
        };
        final AssemblyContext pcmAssemblyContextCandidate = IterableExtensions.<AssemblyContext>findFirst(pcmCompositeComponent.getAssemblyContexts__ComposedStructure(), _function);
        if ((pcmAssemblyContextCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingAssemblyContext(umlProperty, pcmAssemblyContextCandidate);
        } else {
          _routinesFacade.createCorrespondingAssemblyContext(umlProperty, umlComponent);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingAssemblyContextRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlAssemblyContextPropertyReactions.DetectOrCreateCorrespondingAssemblyContextRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlComponent = umlComponent;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingAssemblyContextRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity pcmCompositeComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeComponent(umlProperty, umlComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, umlComponent), 
    	false // asserted
    	);
    if (pcmCompositeComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCompositeComponent);
    org.palladiosimulator.pcm.repository.RepositoryComponent pcmInnerComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInnerComponent(umlProperty, umlComponent, pcmCompositeComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlProperty, umlComponent, pcmCompositeComponent), 
    	false // asserted
    	);
    if (pcmInnerComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInnerComponent);
    	Optional<org.palladiosimulator.pcm.core.composition.AssemblyContext> pcmAssemblyContext = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmAssemblyContext(umlProperty, umlComponent, pcmCompositeComponent, pcmInnerComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.core.composition.AssemblyContext.class,
    		(org.palladiosimulator.pcm.core.composition.AssemblyContext _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlProperty, umlComponent, pcmCompositeComponent, pcmInnerComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmAssemblyContext.isPresent() ? pcmAssemblyContext.get() : null);
    userExecution.callRoutine1(umlProperty, umlComponent, pcmCompositeComponent, pcmInnerComponent, pcmAssemblyContext, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
