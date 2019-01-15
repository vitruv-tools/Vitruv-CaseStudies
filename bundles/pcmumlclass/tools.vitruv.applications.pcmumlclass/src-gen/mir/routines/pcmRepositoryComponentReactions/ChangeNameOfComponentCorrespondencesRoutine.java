package mir.routines.pcmRepositoryComponentReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeNameOfComponentCorrespondencesRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfComponentCorrespondencesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final RepositoryComponent pcmComponent, final String newName, final Optional<org.eclipse.uml2.uml.Package> umlComponentPackage, final Optional<org.eclipse.uml2.uml.Class> umlComponentImplementation, final Optional<Operation> umlComponentConstructor, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlComponentPackage.isPresent();
      if (_isPresent) {
        org.eclipse.uml2.uml.Package _get = umlComponentPackage.get();
        _get.setName(StringExtensions.toFirstLower(pcmComponent.getEntityName()));
      }
      boolean _isPresent_1 = umlComponentImplementation.isPresent();
      if (_isPresent_1) {
        org.eclipse.uml2.uml.Class _get_1 = umlComponentImplementation.get();
        _get_1.setName((newName + DefaultLiterals.IMPLEMENTATION_SUFFIX));
      }
      boolean _isPresent_2 = umlComponentConstructor.isPresent();
      if (_isPresent_2) {
        Operation _get_2 = umlComponentConstructor.get();
        _get_2.setName((newName + DefaultLiterals.IMPLEMENTATION_SUFFIX));
      }
    }
    
    public EObject getCorrepondenceSourceUmlComponentPackage(final RepositoryComponent pcmComponent, final String newName) {
      return pcmComponent;
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent, final String newName) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlComponentImplementation(final RepositoryComponent pcmComponent, final String newName, final Optional<org.eclipse.uml2.uml.Package> umlComponentPackage) {
      return pcmComponent;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final String newName, final Optional<org.eclipse.uml2.uml.Package> umlComponentPackage) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlComponentConstructor(final RepositoryComponent pcmComponent, final String newName, final Optional<org.eclipse.uml2.uml.Package> umlComponentPackage, final Optional<org.eclipse.uml2.uml.Class> umlComponentImplementation) {
      return pcmComponent;
    }
    
    public String getRetrieveTag3(final RepositoryComponent pcmComponent, final String newName, final Optional<org.eclipse.uml2.uml.Package> umlComponentPackage, final Optional<org.eclipse.uml2.uml.Class> umlComponentImplementation) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
  }
  
  public ChangeNameOfComponentCorrespondencesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.ChangeNameOfComponentCorrespondencesRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.newName = newName;
  }
  
  private RepositoryComponent pcmComponent;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfComponentCorrespondencesRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   newName: " + this.newName);
    
    	Optional<org.eclipse.uml2.uml.Package> umlComponentPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlComponentPackage(pcmComponent, newName), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(pcmComponent, newName), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlComponentPackage.isPresent() ? umlComponentPackage.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> umlComponentImplementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlComponentImplementation(pcmComponent, newName, umlComponentPackage), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmComponent, newName, umlComponentPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlComponentImplementation.isPresent() ? umlComponentImplementation.get() : null);
    	Optional<org.eclipse.uml2.uml.Operation> umlComponentConstructor = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlComponentConstructor(pcmComponent, newName, umlComponentPackage, umlComponentImplementation), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmComponent, newName, umlComponentPackage, umlComponentImplementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlComponentConstructor.isPresent() ? umlComponentConstructor.get() : null);
    userExecution.executeAction1(pcmComponent, newName, umlComponentPackage, umlComponentImplementation, umlComponentConstructor, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
