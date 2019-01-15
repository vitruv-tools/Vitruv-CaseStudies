package mir.routines.pcmSystemReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmSystemReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeNameOfSystemCorrespondencesRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfSystemCorrespondencesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final org.palladiosimulator.pcm.system.System pcmSystem, final String newName, final Optional<org.eclipse.uml2.uml.Package> umlSystemPackage, final Optional<org.eclipse.uml2.uml.Class> umlSystemImplementation, final Optional<Operation> umlSystemConstructor, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlSystemPackage.isPresent();
      if (_isPresent) {
        org.eclipse.uml2.uml.Package _get = umlSystemPackage.get();
        _get.setName(StringExtensions.toFirstLower(newName));
      }
      boolean _isPresent_1 = umlSystemImplementation.isPresent();
      if (_isPresent_1) {
        org.eclipse.uml2.uml.Class _get_1 = umlSystemImplementation.get();
        _get_1.setName((newName + DefaultLiterals.IMPLEMENTATION_SUFFIX));
      }
      boolean _isPresent_2 = umlSystemConstructor.isPresent();
      if (_isPresent_2) {
        Operation _get_2 = umlSystemConstructor.get();
        _get_2.setName((newName + DefaultLiterals.IMPLEMENTATION_SUFFIX));
      }
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem, final String newName) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlSystemImplementation(final org.palladiosimulator.pcm.system.System pcmSystem, final String newName, final Optional<org.eclipse.uml2.uml.Package> umlSystemPackage) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSourceUmlSystemConstructor(final org.palladiosimulator.pcm.system.System pcmSystem, final String newName, final Optional<org.eclipse.uml2.uml.Package> umlSystemPackage, final Optional<org.eclipse.uml2.uml.Class> umlSystemImplementation) {
      return pcmSystem;
    }
    
    public String getRetrieveTag2(final org.palladiosimulator.pcm.system.System pcmSystem, final String newName, final Optional<org.eclipse.uml2.uml.Package> umlSystemPackage) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlSystemPackage(final org.palladiosimulator.pcm.system.System pcmSystem, final String newName) {
      return pcmSystem;
    }
    
    public String getRetrieveTag3(final org.palladiosimulator.pcm.system.System pcmSystem, final String newName, final Optional<org.eclipse.uml2.uml.Package> umlSystemPackage, final Optional<org.eclipse.uml2.uml.Class> umlSystemImplementation) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
  }
  
  public ChangeNameOfSystemCorrespondencesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.ChangeNameOfSystemCorrespondencesRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;this.newName = newName;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfSystemCorrespondencesRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    getLogger().debug("   newName: " + this.newName);
    
    	Optional<org.eclipse.uml2.uml.Package> umlSystemPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlSystemPackage(pcmSystem, newName), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(pcmSystem, newName), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlSystemPackage.isPresent() ? umlSystemPackage.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> umlSystemImplementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlSystemImplementation(pcmSystem, newName, umlSystemPackage), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmSystem, newName, umlSystemPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlSystemImplementation.isPresent() ? umlSystemImplementation.get() : null);
    	Optional<org.eclipse.uml2.uml.Operation> umlSystemConstructor = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlSystemConstructor(pcmSystem, newName, umlSystemPackage, umlSystemImplementation), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmSystem, newName, umlSystemPackage, umlSystemImplementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlSystemConstructor.isPresent() ? umlSystemConstructor.get() : null);
    userExecution.executeAction1(pcmSystem, newName, umlSystemPackage, umlSystemImplementation, umlSystemConstructor, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
