package mir.routines.umlInterfaceReactions;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Optional;
import mir.routines.umlInterfaceReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingInterfaceRoutine.ActionUserExecution userExecution;
  
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
    
    public EObject getCorrepondenceSourcePcmInterface(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository) {
      return umlInterface;
    }
    
    public String getRetrieveTag2(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public void callRoutine1(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository, final Optional<OperationInterface> pcmInterface, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmInterface.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<OperationInterface, Boolean> _function = (OperationInterface it) -> {
          String _entityName = it.getEntityName();
          String _name = umlInterface.getName();
          return Boolean.valueOf(Objects.equal(_entityName, _name));
        };
        final OperationInterface pcmInterfaceCandidate = IterableExtensions.<OperationInterface>findFirst(Iterables.<OperationInterface>filter(pcmRepository.getInterfaces__Repository(), OperationInterface.class), _function);
        if ((pcmInterfaceCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingInterface(umlInterface, pcmInterfaceCandidate);
        } else {
          _routinesFacade.createCorrespondingInterface(umlInterface, umlPackage);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInterfaceReactions.DetectOrCreateCorrespondingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlInterface = umlInterface;this.umlPackage = umlPackage;
  }
  
  private Interface umlInterface;
  
  private org.eclipse.uml2.uml.Package umlPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingInterfaceRoutine with input:");
    getLogger().debug("   umlInterface: " + this.umlInterface);
    getLogger().debug("   umlPackage: " + this.umlPackage);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlInterface, umlPackage), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlInterface, umlPackage), 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> pcmInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmInterface(umlInterface, umlPackage, pcmRepository), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlInterface, umlPackage, pcmRepository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmInterface.isPresent() ? pcmInterface.get() : null);
    userExecution.callRoutine1(umlInterface, umlPackage, pcmRepository, pcmInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
