package mir.routines.pcmProvidedRoleReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmProvidedRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingProvidedRealizationRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingProvidedRealizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlComponentImpl(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return pcmIPRE;
    }
    
    public String getRetrieveTag1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<InterfaceRealization> umlRealization) {
      OperationInterface _providedInterface__OperationProvidedRole = pcmProvided.getProvidedInterface__OperationProvidedRole();
      return _providedInterface__OperationProvidedRole;
    }
    
    public String getRetrieveTag2(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public String getRetrieveTag3(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<InterfaceRealization> umlRealization) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getCorrepondenceSourceUmlRealization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return pcmProvided;
    }
    
    public void callRoutine1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<InterfaceRealization> umlRealization, final Optional<Interface> umlInterface, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlRealization.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        InterfaceRealization umlRealizationCandidate = null;
        boolean _isPresent_1 = umlInterface.isPresent();
        if (_isPresent_1) {
          final Function1<InterfaceRealization, Boolean> _function = (InterfaceRealization it) -> {
            return Boolean.valueOf((Objects.equal(it.getName(), pcmProvided.getEntityName()) && (it.getContract() == umlInterface.get())));
          };
          umlRealizationCandidate = IterableExtensions.<InterfaceRealization>findFirst(umlComponentImpl.getInterfaceRealizations(), _function);
        }
        if ((umlRealizationCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingRealization(pcmProvided, umlRealizationCandidate);
        }
        _routinesFacade.createCorrespondingProvidedRealization(pcmProvided, pcmIPRE);
      }
    }
  }
  
  public DetectOrCreateCorrespondingProvidedRealizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.DetectOrCreateCorrespondingProvidedRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationProvidedRole pcmProvided;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingProvidedRealizationRoutine with input:");
    getLogger().debug("   pcmProvided: " + this.pcmProvided);
    getLogger().debug("   pcmIPRE: " + this.pcmIPRE);
    
    org.eclipse.uml2.uml.Class umlComponentImpl = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentImpl(pcmProvided, pcmIPRE), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmProvided, pcmIPRE), 
    	false // asserted
    	);
    if (umlComponentImpl == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentImpl);
    	Optional<org.eclipse.uml2.uml.InterfaceRealization> umlRealization = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlRealization(pcmProvided, pcmIPRE, umlComponentImpl), // correspondence source supplier
    		org.eclipse.uml2.uml.InterfaceRealization.class,
    		(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmProvided, pcmIPRE, umlComponentImpl), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlRealization.isPresent() ? umlRealization.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> umlInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlInterface(pcmProvided, pcmIPRE, umlComponentImpl, umlRealization), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmProvided, pcmIPRE, umlComponentImpl, umlRealization), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlInterface.isPresent() ? umlInterface.get() : null);
    userExecution.callRoutine1(pcmProvided, pcmIPRE, umlComponentImpl, umlRealization, umlInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
