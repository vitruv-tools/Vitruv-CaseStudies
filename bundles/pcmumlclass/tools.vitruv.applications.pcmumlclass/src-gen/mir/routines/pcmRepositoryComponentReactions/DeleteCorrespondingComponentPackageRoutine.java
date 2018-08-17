package mir.routines.pcmRepositoryComponentReactions;

import java.io.IOException;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingComponentPackageRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingComponentPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlComponentImplementation(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return pcmComponent;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlComponentConstructor(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return pcmComponent;
    }
    
    public String getRetrieveTag3(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public String getTag1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public EObject getElement6(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return umlComponentConstructor;
    }
    
    public String getTag3(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public String getTag2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getElement7(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return umlComponentPackage;
    }
    
    public EObject getElement1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSourceUmlComponentPackage(final RepositoryComponent pcmComponent) {
      return pcmComponent;
    }
    
    public EObject getElement4(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return umlComponentImplementation;
    }
    
    public EObject getElement5(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return pcmComponent;
    }
    
    public EObject getElement2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return umlComponentPackage;
    }
    
    public EObject getElement3(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return pcmComponent;
    }
  }
  
  public DeleteCorrespondingComponentPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.DeleteCorrespondingComponentPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;
  }
  
  private RepositoryComponent pcmComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingComponentPackageRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    
    org.eclipse.uml2.uml.Package umlComponentPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentPackage(pcmComponent), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmComponent), 
    	false // asserted
    	);
    if (umlComponentPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentPackage);
    org.eclipse.uml2.uml.Class umlComponentImplementation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentImplementation(pcmComponent, umlComponentPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmComponent, umlComponentPackage), 
    	false // asserted
    	);
    if (umlComponentImplementation == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentImplementation);
    org.eclipse.uml2.uml.Operation umlComponentConstructor = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentConstructor(pcmComponent, umlComponentPackage, umlComponentImplementation), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(pcmComponent, umlComponentPackage, umlComponentImplementation), 
    	false // asserted
    	);
    if (umlComponentConstructor == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentConstructor);
    removeCorrespondenceBetween(userExecution.getElement1(pcmComponent, umlComponentPackage, umlComponentImplementation, umlComponentConstructor), userExecution.getElement2(pcmComponent, umlComponentPackage, umlComponentImplementation, umlComponentConstructor), userExecution.getTag1(pcmComponent, umlComponentPackage, umlComponentImplementation, umlComponentConstructor));
    
    removeCorrespondenceBetween(userExecution.getElement3(pcmComponent, umlComponentPackage, umlComponentImplementation, umlComponentConstructor), userExecution.getElement4(pcmComponent, umlComponentPackage, umlComponentImplementation, umlComponentConstructor), userExecution.getTag2(pcmComponent, umlComponentPackage, umlComponentImplementation, umlComponentConstructor));
    
    removeCorrespondenceBetween(userExecution.getElement5(pcmComponent, umlComponentPackage, umlComponentImplementation, umlComponentConstructor), userExecution.getElement6(pcmComponent, umlComponentPackage, umlComponentImplementation, umlComponentConstructor), userExecution.getTag3(pcmComponent, umlComponentPackage, umlComponentImplementation, umlComponentConstructor));
    
    deleteObject(userExecution.getElement7(pcmComponent, umlComponentPackage, umlComponentImplementation, umlComponentConstructor));
    
    postprocessElements();
    
    return true;
  }
}
