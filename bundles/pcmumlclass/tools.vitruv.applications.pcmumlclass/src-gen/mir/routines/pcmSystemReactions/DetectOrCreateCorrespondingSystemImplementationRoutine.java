package mir.routines.pcmSystemReactions;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmSystemReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingSystemImplementationRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingSystemImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlSystemImplementation(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      return pcmSystem;
    }
    
    public String getRetrieveTag2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlSystemPackage(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public void callRoutine1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage, final Optional<org.eclipse.uml2.uml.Class> umlSystemImplementation, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlSystemImplementation.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<org.eclipse.uml2.uml.Class, Boolean> _function = (org.eclipse.uml2.uml.Class it) -> {
          String _name = it.getName();
          String _entityName = pcmSystem.getEntityName();
          String _plus = (_entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX);
          return Boolean.valueOf(Objects.equal(_name, _plus));
        };
        final org.eclipse.uml2.uml.Class umlSystemImplementationCandidate = IterableExtensions.<org.eclipse.uml2.uml.Class>findFirst(Iterables.<org.eclipse.uml2.uml.Class>filter(umlSystemPackage.getPackagedElements(), org.eclipse.uml2.uml.Class.class), _function);
        if ((umlSystemImplementationCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingSystemImplementation(pcmSystem, umlSystemImplementationCandidate);
        } else {
          _routinesFacade.createCorrespondingSystemImplementation(pcmSystem);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingSystemImplementationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.DetectOrCreateCorrespondingSystemImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingSystemImplementationRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    
    org.eclipse.uml2.uml.Package umlSystemPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlSystemPackage(pcmSystem), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSystem), 
    	false // asserted
    	);
    if (umlSystemPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlSystemPackage);
    	Optional<org.eclipse.uml2.uml.Class> umlSystemImplementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlSystemImplementation(pcmSystem, umlSystemPackage), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmSystem, umlSystemPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlSystemImplementation.isPresent() ? umlSystemImplementation.get() : null);
    userExecution.callRoutine1(pcmSystem, umlSystemPackage, umlSystemImplementation, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
