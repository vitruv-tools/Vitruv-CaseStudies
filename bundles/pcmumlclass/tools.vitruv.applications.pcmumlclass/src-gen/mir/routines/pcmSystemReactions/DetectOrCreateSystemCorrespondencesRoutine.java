package mir.routines.pcmSystemReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmSystemReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateSystemCorrespondencesRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateSystemCorrespondencesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlSystemImplementation(final org.palladiosimulator.pcm.system.System pcmSystem, final Optional<org.eclipse.uml2.uml.Package> umlSystemPackage) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSourceUmlSystemConstructor(final org.palladiosimulator.pcm.system.System pcmSystem, final Optional<org.eclipse.uml2.uml.Package> umlSystemPackage, final Optional<org.eclipse.uml2.uml.Class> umlSystemImplementation) {
      return pcmSystem;
    }
    
    public String getRetrieveTag2(final org.palladiosimulator.pcm.system.System pcmSystem, final Optional<org.eclipse.uml2.uml.Package> umlSystemPackage) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlSystemPackage(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public String getRetrieveTag3(final org.palladiosimulator.pcm.system.System pcmSystem, final Optional<org.eclipse.uml2.uml.Package> umlSystemPackage, final Optional<org.eclipse.uml2.uml.Class> umlSystemImplementation) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public void callRoutine1(final org.palladiosimulator.pcm.system.System pcmSystem, final Optional<org.eclipse.uml2.uml.Package> umlSystemPackage, final Optional<org.eclipse.uml2.uml.Class> umlSystemImplementation, final Optional<Operation> umlSystemConstructor, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlSystemPackage.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        _routinesFacade.createCorrespondingSystemPackage(pcmSystem);
      }
      boolean _isPresent_1 = umlSystemImplementation.isPresent();
      boolean _not_1 = (!_isPresent_1);
      if (_not_1) {
        _routinesFacade.detectOrCreateCorrespondingSystemImplementation(pcmSystem);
      }
      boolean _isPresent_2 = umlSystemConstructor.isPresent();
      boolean _not_2 = (!_isPresent_2);
      if (_not_2) {
        _routinesFacade.detectOrCreateCorrespondingSystemConstructor(pcmSystem);
      }
    }
  }
  
  public DetectOrCreateSystemCorrespondencesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.DetectOrCreateSystemCorrespondencesRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateSystemCorrespondencesRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    
    	Optional<org.eclipse.uml2.uml.Package> umlSystemPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlSystemPackage(pcmSystem), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(pcmSystem), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlSystemPackage.isPresent() ? umlSystemPackage.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> umlSystemImplementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlSystemImplementation(pcmSystem, umlSystemPackage), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmSystem, umlSystemPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlSystemImplementation.isPresent() ? umlSystemImplementation.get() : null);
    	Optional<org.eclipse.uml2.uml.Operation> umlSystemConstructor = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlSystemConstructor(pcmSystem, umlSystemPackage, umlSystemImplementation), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmSystem, umlSystemPackage, umlSystemImplementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlSystemConstructor.isPresent() ? umlSystemConstructor.get() : null);
    userExecution.callRoutine1(pcmSystem, umlSystemPackage, umlSystemImplementation, umlSystemConstructor, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
