package mir.routines.pcmAssemblyContextReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmAssemblyContextReactions.RoutinesFacade;
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
public class DetectOrCreateCorrespondingAssemblyContextPropertyRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlCompositeImplementation(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
      return pcmComposite;
    }
    
    public String getRetrieveTag1(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlProperty(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation) {
      return pcmAssembly;
    }
    
    public String getRetrieveTag2(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlComponentImplementation(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation, final Optional<Property> umlProperty) {
      RepositoryComponent _encapsulatedComponent__AssemblyContext = pcmAssembly.getEncapsulatedComponent__AssemblyContext();
      return _encapsulatedComponent__AssemblyContext;
    }
    
    public String getRetrieveTag3(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation, final Optional<Property> umlProperty) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public void callRoutine1(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation, final Optional<Property> umlProperty, final Optional<org.eclipse.uml2.uml.Class> umlComponentImplementation, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlProperty.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<Property, Boolean> _function = (Property it) -> {
          Type _type = it.getType();
          return Boolean.valueOf(Objects.equal(_type, umlComponentImplementation));
        };
        final Property umlPropertyCandidate = IterableExtensions.<Property>findFirst(umlCompositeImplementation.getOwnedAttributes(), _function);
        if ((umlPropertyCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingAssemblyContextProperty(pcmAssembly, umlPropertyCandidate);
        } else {
          _routinesFacade.createCorrespondingAssemblyContextProperty(pcmAssembly, pcmComposite);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingAssemblyContextPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmAssemblyContextReactions.DetectOrCreateCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAssembly = pcmAssembly;this.pcmComposite = pcmComposite;
  }
  
  private AssemblyContext pcmAssembly;
  
  private ComposedProvidingRequiringEntity pcmComposite;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingAssemblyContextPropertyRoutine with input:");
    getLogger().debug("   pcmAssembly: " + this.pcmAssembly);
    getLogger().debug("   pcmComposite: " + this.pcmComposite);
    
    org.eclipse.uml2.uml.Class umlCompositeImplementation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlCompositeImplementation(pcmAssembly, pcmComposite), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmAssembly, pcmComposite), 
    	false // asserted
    	);
    if (umlCompositeImplementation == null) {
    	return false;
    }
    registerObjectUnderModification(umlCompositeImplementation);
    	Optional<org.eclipse.uml2.uml.Property> umlProperty = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlProperty(pcmAssembly, pcmComposite, umlCompositeImplementation), // correspondence source supplier
    		org.eclipse.uml2.uml.Property.class,
    		(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmAssembly, pcmComposite, umlCompositeImplementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlProperty.isPresent() ? umlProperty.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> umlComponentImplementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlComponentImplementation(pcmAssembly, pcmComposite, umlCompositeImplementation, umlProperty), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmAssembly, pcmComposite, umlCompositeImplementation, umlProperty), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlComponentImplementation.isPresent() ? umlComponentImplementation.get() : null);
    userExecution.callRoutine1(pcmAssembly, pcmComposite, umlCompositeImplementation, umlProperty, umlComponentImplementation, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
