package mir.routines.pcmAssemblyContextReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmAssemblyContextReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
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
public class CreateCorrespondingAssemblyContextPropertyRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation, final Optional<org.eclipse.uml2.uml.Class> umlInnerComponent, final Property umlProperty) {
      return pcmAssembly;
    }
    
    public void updateUmlPropertyElement(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation, final Optional<org.eclipse.uml2.uml.Class> umlInnerComponent, final Property umlProperty) {
      umlProperty.setName(pcmAssembly.getEntityName());
    }
    
    public EObject getCorrepondenceSourceUmlCompositeImplementation(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
      return pcmComposite;
    }
    
    public EObject getCorrepondenceSource1(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation) {
      return pcmAssembly;
    }
    
    public String getRetrieveTag1(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getElement2(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation, final Optional<org.eclipse.uml2.uml.Class> umlInnerComponent, final Property umlProperty) {
      return umlProperty;
    }
    
    public String getRetrieveTag3(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getTag1(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation, final Optional<org.eclipse.uml2.uml.Class> umlInnerComponent, final Property umlProperty) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlInnerComponent(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation) {
      RepositoryComponent _encapsulatedComponent__AssemblyContext = pcmAssembly.getEncapsulatedComponent__AssemblyContext();
      return _encapsulatedComponent__AssemblyContext;
    }
    
    public void callRoutine1(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation, final Optional<org.eclipse.uml2.uml.Class> umlInnerComponent, final Property umlProperty, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeTypeOfCorrespondingAssemblyContextProperty(pcmAssembly, pcmAssembly.getEncapsulatedComponent__AssemblyContext());
    }
  }
  
  public CreateCorrespondingAssemblyContextPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmAssemblyContextReactions.CreateCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAssembly = pcmAssembly;this.pcmComposite = pcmComposite;
  }
  
  private AssemblyContext pcmAssembly;
  
  private ComposedProvidingRequiringEntity pcmComposite;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingAssemblyContextPropertyRoutine with input:");
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
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmAssembly, pcmComposite, umlCompositeImplementation), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmAssembly, pcmComposite, umlCompositeImplementation)
    ).isEmpty()) {
    	return false;
    }
    	Optional<org.eclipse.uml2.uml.Class> umlInnerComponent = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlInnerComponent(pcmAssembly, pcmComposite, umlCompositeImplementation), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmAssembly, pcmComposite, umlCompositeImplementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlInnerComponent.isPresent() ? umlInnerComponent.get() : null);
    org.eclipse.uml2.uml.Property umlProperty = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createProperty();
    notifyObjectCreated(umlProperty);
    userExecution.updateUmlPropertyElement(pcmAssembly, pcmComposite, umlCompositeImplementation, umlInnerComponent, umlProperty);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmAssembly, pcmComposite, umlCompositeImplementation, umlInnerComponent, umlProperty), userExecution.getElement2(pcmAssembly, pcmComposite, umlCompositeImplementation, umlInnerComponent, umlProperty), userExecution.getTag1(pcmAssembly, pcmComposite, umlCompositeImplementation, umlInnerComponent, umlProperty));
    
    userExecution.callRoutine1(pcmAssembly, pcmComposite, umlCompositeImplementation, umlInnerComponent, umlProperty, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
