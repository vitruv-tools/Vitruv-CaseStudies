package mir.routines.pcmAssemblyContextReactions;

import java.io.IOException;
import mir.routines.pcmAssemblyContextReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class MoveCorrespondingAssemblyContextPropertyRoutine extends AbstractRepairRoutineRealization {
  private MoveCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation, final Property umlProperty) {
      return umlCompositeImplementation;
    }
    
    public void update0Element(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, final org.eclipse.uml2.uml.Class umlCompositeImplementation, final Property umlProperty) {
      EList<Property> _ownedAttributes = umlCompositeImplementation.getOwnedAttributes();
      _ownedAttributes.add(umlProperty);
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
  }
  
  public MoveCorrespondingAssemblyContextPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmAssemblyContextReactions.MoveCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAssembly = pcmAssembly;this.pcmComposite = pcmComposite;
  }
  
  private AssemblyContext pcmAssembly;
  
  private ComposedProvidingRequiringEntity pcmComposite;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MoveCorrespondingAssemblyContextPropertyRoutine with input:");
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
    org.eclipse.uml2.uml.Property umlProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlProperty(pcmAssembly, pcmComposite, umlCompositeImplementation), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmAssembly, pcmComposite, umlCompositeImplementation), 
    	false // asserted
    	);
    if (umlProperty == null) {
    	return false;
    }
    registerObjectUnderModification(umlProperty);
    // val updatedElement userExecution.getElement1(pcmAssembly, pcmComposite, umlCompositeImplementation, umlProperty);
    userExecution.update0Element(pcmAssembly, pcmComposite, umlCompositeImplementation, umlProperty);
    
    postprocessElements();
    
    return true;
  }
}
