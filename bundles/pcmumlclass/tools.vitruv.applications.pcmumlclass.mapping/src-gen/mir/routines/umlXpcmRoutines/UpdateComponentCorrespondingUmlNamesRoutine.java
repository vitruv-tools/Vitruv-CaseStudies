package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.mapping.MappingUpdateUtils;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingRoutineUpdateHelper;
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingUpdateSource;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateComponentCorrespondingUmlNamesRoutine extends AbstractRepairRoutineRealization {
  private UpdateComponentCorrespondingUmlNamesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final RepositoryComponent component, final Repository repository, final org.eclipse.uml2.uml.Class umlComponentImplementation, final org.eclipse.uml2.uml.Package umlComponentPackage, final Operation umlComponentConstructor, @Extension final RoutinesFacade _routinesFacade) {
      final MappingUpdateSource componentSource = MappingRoutineUpdateHelper.eObjectSource(component, "entityName", MappingUpdateUtils.chain(MappingUpdateUtils.transformationFirstUpper(false), MappingUpdateUtils.transformationImplementationSuffix(true, false)));
      final MappingUpdateSource componentSourcePackage = MappingRoutineUpdateHelper.eObjectSource(component, "entityName", MappingUpdateUtils.transformationFirstLower(true));
      MappingRoutineUpdateHelper.updateFromSources(MappingRoutineUpdateHelper.eObjectTarget(umlComponentImplementation, "name"), componentSource);
      MappingRoutineUpdateHelper.updateFromSources(MappingRoutineUpdateHelper.eObjectTarget(umlComponentPackage, "name"), componentSourcePackage);
      MappingRoutineUpdateHelper.updateFromSources(MappingRoutineUpdateHelper.eObjectTarget(umlComponentConstructor, "name"), componentSource);
    }
    
    public EObject getCorrepondenceSourceUmlComponentPackage(final RepositoryComponent component, final Repository repository, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return component;
    }
    
    public String getRetrieveTag1(final RepositoryComponent component, final Repository repository) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlComponentImplementation(final RepositoryComponent component, final Repository repository) {
      return component;
    }
    
    public String getRetrieveTag2(final RepositoryComponent component, final Repository repository, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlComponentConstructor(final RepositoryComponent component, final Repository repository, final org.eclipse.uml2.uml.Class umlComponentImplementation, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return component;
    }
    
    public String getRetrieveTag3(final RepositoryComponent component, final Repository repository, final org.eclipse.uml2.uml.Class umlComponentImplementation, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
  }
  
  public UpdateComponentCorrespondingUmlNamesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent component, final Repository repository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateComponentCorrespondingUmlNamesRoutine.ActionUserExecution(getExecutionState(), this);
    this.component = component;this.repository = repository;
  }
  
  private RepositoryComponent component;
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateComponentCorrespondingUmlNamesRoutine with input:");
    getLogger().debug("   component: " + this.component);
    getLogger().debug("   repository: " + this.repository);
    
    org.eclipse.uml2.uml.Class umlComponentImplementation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentImplementation(component, repository), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(component, repository), 
    	false // asserted
    	);
    if (umlComponentImplementation == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentImplementation);
    org.eclipse.uml2.uml.Package umlComponentPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentPackage(component, repository, umlComponentImplementation), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(component, repository, umlComponentImplementation), 
    	false // asserted
    	);
    if (umlComponentPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentPackage);
    org.eclipse.uml2.uml.Operation umlComponentConstructor = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentConstructor(component, repository, umlComponentImplementation, umlComponentPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(component, repository, umlComponentImplementation, umlComponentPackage), 
    	false // asserted
    	);
    if (umlComponentConstructor == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentConstructor);
    userExecution.executeAction1(component, repository, umlComponentImplementation, umlComponentPackage, umlComponentConstructor, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
