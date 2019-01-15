package mir.routines.umlAssemblyContextPropertyReactions;

import java.io.IOException;
import mir.routines.umlAssemblyContextPropertyReactions.RoutinesFacade;
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
public class MoveCorrespondingAssemblyContextRoutine extends AbstractRepairRoutineRealization {
  private MoveCorrespondingAssemblyContextRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final AssemblyContext pcmAssemblyContext) {
      return pcmCompositeComponent;
    }
    
    public void update0Element(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final AssemblyContext pcmAssemblyContext) {
      EList<AssemblyContext> _assemblyContexts__ComposedStructure = pcmCompositeComponent.getAssemblyContexts__ComposedStructure();
      _assemblyContexts__ComposedStructure.add(pcmAssemblyContext);
    }
    
    public String getRetrieveTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getCorrepondenceSourcePcmAssemblyContext(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent) {
      return umlProperty;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
  }
  
  public MoveCorrespondingAssemblyContextRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlAssemblyContextPropertyReactions.MoveCorrespondingAssemblyContextRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlComponent = umlComponent;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MoveCorrespondingAssemblyContextRoutine with input:");
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
    org.palladiosimulator.pcm.core.composition.AssemblyContext pcmAssemblyContext = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmAssemblyContext(umlProperty, umlComponent, pcmCompositeComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.core.composition.AssemblyContext.class,
    	(org.palladiosimulator.pcm.core.composition.AssemblyContext _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlProperty, umlComponent, pcmCompositeComponent), 
    	false // asserted
    	);
    if (pcmAssemblyContext == null) {
    	return false;
    }
    registerObjectUnderModification(pcmAssemblyContext);
    // val updatedElement userExecution.getElement1(umlProperty, umlComponent, pcmCompositeComponent, pcmAssemblyContext);
    userExecution.update0Element(umlProperty, umlComponent, pcmCompositeComponent, pcmAssemblyContext);
    
    postprocessElements();
    
    return true;
  }
}
