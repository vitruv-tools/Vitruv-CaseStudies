package mir.routines.umlInterfaceReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlInterfaceReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
      return umlPackage;
    }
    
    public String getRetrieveTag1(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
      return TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE;
    }
    
    public void callRoutine1(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final Optional<Repository> pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmRepository.isPresent();
      if (_isPresent) {
        _routinesFacade.detectOrCreateCorrespondingInterface(umlInterface, umlPackage);
        _routinesFacade.moveCorrespondingInterface(umlInterface, umlPackage);
      } else {
        _routinesFacade.deleteCorrespondingInterface(umlInterface);
      }
    }
  }
  
  public InsertCorrespondingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInterfaceReactions.InsertCorrespondingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlInterface = umlInterface;this.umlPackage = umlPackage;
  }
  
  private Interface umlInterface;
  
  private org.eclipse.uml2.uml.Package umlPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingInterfaceRoutine with input:");
    getLogger().debug("   umlInterface: " + this.umlInterface);
    getLogger().debug("   umlPackage: " + this.umlPackage);
    
    	Optional<org.palladiosimulator.pcm.repository.Repository> pcmRepository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmRepository(umlInterface, umlPackage), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlInterface, umlPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmRepository.isPresent() ? pcmRepository.get() : null);
    userExecution.callRoutine1(umlInterface, umlPackage, pcmRepository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
