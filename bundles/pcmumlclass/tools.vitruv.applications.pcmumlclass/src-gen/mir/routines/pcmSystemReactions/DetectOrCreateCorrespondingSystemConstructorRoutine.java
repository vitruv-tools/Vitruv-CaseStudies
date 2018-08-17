package mir.routines.pcmSystemReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmSystemReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingSystemConstructorRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingSystemConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlSystemImplementation(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlSystemConstructor(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return pcmSystem;
    }
    
    public String getRetrieveTag2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public void callRoutine1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation, final Optional<Operation> umlSystemConstructor, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlSystemConstructor.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<Operation, Boolean> _function = (Operation it) -> {
          String _name = it.getName();
          String _entityName = pcmSystem.getEntityName();
          String _plus = (_entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX);
          return Boolean.valueOf(Objects.equal(_name, _plus));
        };
        final Operation umlSystemConstructorCandidate = IterableExtensions.<Operation>findFirst(umlSystemImplementation.getOwnedOperations(), _function);
        if ((umlSystemConstructorCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingSystemConstructor(pcmSystem, umlSystemConstructorCandidate);
        } else {
          _routinesFacade.createCorrespondingSystemConstructor(pcmSystem);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingSystemConstructorRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.DetectOrCreateCorrespondingSystemConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingSystemConstructorRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    
    org.eclipse.uml2.uml.Class umlSystemImplementation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlSystemImplementation(pcmSystem), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSystem), 
    	false // asserted
    	);
    if (umlSystemImplementation == null) {
    	return false;
    }
    registerObjectUnderModification(umlSystemImplementation);
    	Optional<org.eclipse.uml2.uml.Operation> umlSystemConstructor = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlSystemConstructor(pcmSystem, umlSystemImplementation), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmSystem, umlSystemImplementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlSystemConstructor.isPresent() ? umlSystemConstructor.get() : null);
    userExecution.callRoutine1(pcmSystem, umlSystemImplementation, umlSystemConstructor, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
