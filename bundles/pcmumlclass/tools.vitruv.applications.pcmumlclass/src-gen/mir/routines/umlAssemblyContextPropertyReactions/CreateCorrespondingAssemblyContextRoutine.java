package mir.routines.umlAssemblyContextPropertyReactions;

import java.io.IOException;
import mir.routines.umlAssemblyContextPropertyReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingAssemblyContextRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingAssemblyContextRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final RepositoryComponent pcmInnerComponent, final AssemblyContext pcmAssemblyContext) {
      return pcmAssemblyContext;
    }
    
    public EObject getCorrepondenceSource1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final RepositoryComponent pcmInnerComponent) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getElement2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final RepositoryComponent pcmInnerComponent, final AssemblyContext pcmAssemblyContext) {
      return umlProperty;
    }
    
    public String getRetrieveTag3(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final RepositoryComponent pcmInnerComponent) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public String getTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final RepositoryComponent pcmInnerComponent, final AssemblyContext pcmAssemblyContext) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public void updatePcmAssemblyContextElement(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent, final RepositoryComponent pcmInnerComponent, final AssemblyContext pcmAssemblyContext) {
      pcmAssemblyContext.setEncapsulatedComponent__AssemblyContext(pcmInnerComponent);
      EList<AssemblyContext> _assemblyContexts__ComposedStructure = pcmCompositeComponent.getAssemblyContexts__ComposedStructure();
      _assemblyContexts__ComposedStructure.add(pcmAssemblyContext);
    }
    
    public EObject getCorrepondenceSourcePcmCompositeComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public EObject getCorrepondenceSourcePcmInnerComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final ComposedProvidingRequiringEntity pcmCompositeComponent) {
      Type _type = umlProperty.getType();
      return _type;
    }
  }
  
  public CreateCorrespondingAssemblyContextRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlAssemblyContextPropertyReactions.CreateCorrespondingAssemblyContextRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlComponent = umlComponent;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingAssemblyContextRoutine with input:");
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
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlProperty, umlComponent, pcmCompositeComponent, pcmInnerComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.core.composition.AssemblyContext.class,
    	(org.palladiosimulator.pcm.core.composition.AssemblyContext _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(umlProperty, umlComponent, pcmCompositeComponent, pcmInnerComponent)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.core.composition.AssemblyContext pcmAssemblyContext = org.palladiosimulator.pcm.core.composition.impl.CompositionFactoryImpl.eINSTANCE.createAssemblyContext();
    notifyObjectCreated(pcmAssemblyContext);
    userExecution.updatePcmAssemblyContextElement(umlProperty, umlComponent, pcmCompositeComponent, pcmInnerComponent, pcmAssemblyContext);
    
    addCorrespondenceBetween(userExecution.getElement1(umlProperty, umlComponent, pcmCompositeComponent, pcmInnerComponent, pcmAssemblyContext), userExecution.getElement2(umlProperty, umlComponent, pcmCompositeComponent, pcmInnerComponent, pcmAssemblyContext), userExecution.getTag1(umlProperty, umlComponent, pcmCompositeComponent, pcmInnerComponent, pcmAssemblyContext));
    
    postprocessElements();
    
    return true;
  }
}
