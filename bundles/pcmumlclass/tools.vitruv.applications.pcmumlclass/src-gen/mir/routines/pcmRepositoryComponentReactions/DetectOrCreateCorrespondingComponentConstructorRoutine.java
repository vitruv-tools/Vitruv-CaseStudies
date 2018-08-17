package mir.routines.pcmRepositoryComponentReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingComponentConstructorRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingComponentConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlComponentImplementation(final RepositoryComponent pcmComponent) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSourceUmlComponentConstructor(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return pcmComponent;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public void callRoutine1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Optional<Operation> umlComponentConstructor, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlComponentConstructor.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<Operation, Boolean> _function = (Operation it) -> {
          String _name = it.getName();
          String _entityName = pcmComponent.getEntityName();
          String _plus = (_entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX);
          return Boolean.valueOf(Objects.equal(_name, _plus));
        };
        final Operation umlComponentConstructorCandidate = IterableExtensions.<Operation>findFirst(umlComponentImplementation.getOwnedOperations(), _function);
        if ((umlComponentConstructorCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingComponentConstructor(pcmComponent, umlComponentConstructorCandidate);
        } else {
          _routinesFacade.createCorrespondingComponentConstructor(pcmComponent);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingComponentConstructorRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.DetectOrCreateCorrespondingComponentConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;
  }
  
  private RepositoryComponent pcmComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingComponentConstructorRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    
    org.eclipse.uml2.uml.Class umlComponentImplementation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentImplementation(pcmComponent), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmComponent), 
    	false // asserted
    	);
    if (umlComponentImplementation == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentImplementation);
    	Optional<org.eclipse.uml2.uml.Operation> umlComponentConstructor = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlComponentConstructor(pcmComponent, umlComponentImplementation), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmComponent, umlComponentImplementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlComponentConstructor.isPresent() ? umlComponentConstructor.get() : null);
    userExecution.callRoutine1(pcmComponent, umlComponentImplementation, umlComponentConstructor, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
