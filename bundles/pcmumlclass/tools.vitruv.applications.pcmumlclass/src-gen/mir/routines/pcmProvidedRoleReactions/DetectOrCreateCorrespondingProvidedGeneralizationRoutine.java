package mir.routines.pcmProvidedRoleReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmProvidedRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
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
public class DetectOrCreateCorrespondingProvidedGeneralizationRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingProvidedGeneralizationRoutine.ActionUserExecution userExecution;
  
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
    
    public EObject getCorrepondenceSourceUmlGeneralization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Generalization> umlGeneralization) {
      OperationInterface _providedInterface__OperationProvidedRole = pcmProvided.getProvidedInterface__OperationProvidedRole();
      return _providedInterface__OperationProvidedRole;
    }
    
    public String getRetrieveTag2(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public String getRetrieveTag3(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Generalization> umlGeneralization) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public void callRoutine1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Generalization> umlGeneralization, final Optional<Interface> umlInterface, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlGeneralization.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        Generalization umlGeneralizationCandidate = null;
        boolean _isPresent_1 = umlInterface.isPresent();
        if (_isPresent_1) {
          final Function1<Generalization, Boolean> _function = (Generalization it) -> {
            Classifier _general = it.getGeneral();
            Interface _get = umlInterface.get();
            return Boolean.valueOf((_general == _get));
          };
          umlGeneralizationCandidate = IterableExtensions.<Generalization>findFirst(umlComponentImpl.getGeneralizations(), _function);
        }
        if ((umlGeneralizationCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingGeneralization(pcmProvided, umlGeneralizationCandidate);
        }
        _routinesFacade.createCorrespondingProvidedGeneralization(pcmProvided, pcmIPRE);
      }
    }
  }
  
  public DetectOrCreateCorrespondingProvidedGeneralizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.DetectOrCreateCorrespondingProvidedGeneralizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationProvidedRole pcmProvided;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingProvidedGeneralizationRoutine with input:");
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
    	Optional<org.eclipse.uml2.uml.Generalization> umlGeneralization = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlGeneralization(pcmProvided, pcmIPRE, umlComponentImpl), // correspondence source supplier
    		org.eclipse.uml2.uml.Generalization.class,
    		(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmProvided, pcmIPRE, umlComponentImpl), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlGeneralization.isPresent() ? umlGeneralization.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> umlInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlInterface(pcmProvided, pcmIPRE, umlComponentImpl, umlGeneralization), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmProvided, pcmIPRE, umlComponentImpl, umlGeneralization), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlInterface.isPresent() ? umlInterface.get() : null);
    userExecution.callRoutine1(pcmProvided, pcmIPRE, umlComponentImpl, umlGeneralization, umlInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
