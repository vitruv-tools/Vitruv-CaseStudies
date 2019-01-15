package mir.routines.pcmInterfaceReactions;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmInterfaceReactions.RoutinesFacade;
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
public class DetectOrCreateInterfaceCandidateRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateInterfaceCandidateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final OperationInterface pcmInterface, final Repository pcmRepository) {
      return TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationInterface pcmInterface, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlContractsPackage) {
      return pcmInterface;
    }
    
    public String getRetrieveTag2(final OperationInterface pcmInterface, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlContractsPackage) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getCorrepondenceSourceUmlContractsPackage(final OperationInterface pcmInterface, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public void callRoutine1(final OperationInterface pcmInterface, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlContractsPackage, final Optional<Interface> umlInterface, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlInterface.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<Interface, Boolean> _function = (Interface it) -> {
          String _name = it.getName();
          String _entityName = pcmInterface.getEntityName();
          return Boolean.valueOf(Objects.equal(_name, _entityName));
        };
        final Interface umlInterfaceCandidate = IterableExtensions.<Interface>findFirst(Iterables.<Interface>filter(umlContractsPackage.getPackagedElements(), Interface.class), _function);
        if ((umlInterfaceCandidate != null)) {
          _routinesFacade.attemptAddingCorrespondenceForExistingInterfaceCandidate(pcmInterface, pcmRepository, umlInterfaceCandidate);
        } else {
          _routinesFacade.createInterfaceCorrespondence(pcmInterface, pcmRepository);
        }
      }
    }
  }
  
  public DetectOrCreateInterfaceCandidateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface pcmInterface, final Repository pcmRepository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInterfaceReactions.DetectOrCreateInterfaceCandidateRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.pcmRepository = pcmRepository;
  }
  
  private OperationInterface pcmInterface;
  
  private Repository pcmRepository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateInterfaceCandidateRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   pcmRepository: " + this.pcmRepository);
    
    org.eclipse.uml2.uml.Package umlContractsPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlContractsPackage(pcmInterface, pcmRepository), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmInterface, pcmRepository), 
    	false // asserted
    	);
    if (umlContractsPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlContractsPackage);
    	Optional<org.eclipse.uml2.uml.Interface> umlInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlInterface(pcmInterface, pcmRepository, umlContractsPackage), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmInterface, pcmRepository, umlContractsPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlInterface.isPresent() ? umlInterface.get() : null);
    userExecution.callRoutine1(pcmInterface, pcmRepository, umlContractsPackage, umlInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
