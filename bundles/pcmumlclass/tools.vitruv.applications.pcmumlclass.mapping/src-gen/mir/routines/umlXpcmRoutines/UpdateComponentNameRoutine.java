package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.mapping.MappingUpdateUtils;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingRoutineUpdateHelper;
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingUpdateSource;
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingUpdateTarget;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateComponentNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateComponentNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final org.eclipse.uml2.uml.Package componentPackage, final org.eclipse.uml2.uml.Package repositoryPackage, final org.eclipse.uml2.uml.Class implementation, final Operation constructor) {
      return componentPackage;
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Package componentPackage, final org.eclipse.uml2.uml.Package repositoryPackage, final org.eclipse.uml2.uml.Class implementation, final Operation constructor, final RepositoryComponent pcmComponent, @Extension final RoutinesFacade _routinesFacade) {
      final MappingUpdateTarget componentTarget = MappingRoutineUpdateHelper.eObjectTarget(pcmComponent, "entityName");
      final MappingUpdateSource componentPackageSource = MappingRoutineUpdateHelper.eObjectSource(componentPackage, "name", MappingUpdateUtils.transformationFirstUpper(true));
      final MappingUpdateSource implementationSource = MappingRoutineUpdateHelper.eObjectSource(implementation, "name", MappingUpdateUtils.chain(MappingUpdateUtils.transformationFirstUpper(false), MappingUpdateUtils.transformationImplementationSuffix(false, true)));
      final MappingUpdateSource constructorSource = MappingRoutineUpdateHelper.eObjectSource(constructor, "name", MappingUpdateUtils.chain(MappingUpdateUtils.transformationFirstUpper(false), MappingUpdateUtils.transformationImplementationSuffix(false, true)));
      MappingRoutineUpdateHelper.updateFromSources(componentTarget, componentPackageSource, implementationSource, constructorSource);
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package componentPackage, final org.eclipse.uml2.uml.Package repositoryPackage, final org.eclipse.uml2.uml.Class implementation, final Operation constructor) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
  }
  
  public UpdateComponentNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package componentPackage, final org.eclipse.uml2.uml.Package repositoryPackage, final org.eclipse.uml2.uml.Class implementation, final Operation constructor) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateComponentNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.componentPackage = componentPackage;this.repositoryPackage = repositoryPackage;this.implementation = implementation;this.constructor = constructor;
  }
  
  private org.eclipse.uml2.uml.Package componentPackage;
  
  private org.eclipse.uml2.uml.Package repositoryPackage;
  
  private org.eclipse.uml2.uml.Class implementation;
  
  private Operation constructor;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateComponentNameRoutine with input:");
    getLogger().debug("   componentPackage: " + this.componentPackage);
    getLogger().debug("   repositoryPackage: " + this.repositoryPackage);
    getLogger().debug("   implementation: " + this.implementation);
    getLogger().debug("   constructor: " + this.constructor);
    
    org.palladiosimulator.pcm.repository.RepositoryComponent pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(componentPackage, repositoryPackage, implementation, constructor), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(componentPackage, repositoryPackage, implementation, constructor), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    userExecution.executeAction1(componentPackage, repositoryPackage, implementation, constructor, pcmComponent, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
