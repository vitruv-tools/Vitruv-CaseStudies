package mir.routines.pcmRepositoryComponentReactions;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
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
public class DetectOrCreateCorrespondingComponentImplementationRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingComponentImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlComponentPackage(final RepositoryComponent pcmComponent) {
      return pcmComponent;
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlComponentImplementation(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return pcmComponent;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public void callRoutine1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final Optional<org.eclipse.uml2.uml.Class> umlComponentImplementation, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlComponentImplementation.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<org.eclipse.uml2.uml.Class, Boolean> _function = (org.eclipse.uml2.uml.Class it) -> {
          String _name = it.getName();
          String _entityName = pcmComponent.getEntityName();
          String _plus = (_entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX);
          return Boolean.valueOf(Objects.equal(_name, _plus));
        };
        final org.eclipse.uml2.uml.Class umlComponentImplementationCandidate = IterableExtensions.<org.eclipse.uml2.uml.Class>findFirst(Iterables.<org.eclipse.uml2.uml.Class>filter(umlComponentPackage.getPackagedElements(), org.eclipse.uml2.uml.Class.class), _function);
        if ((umlComponentImplementationCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingComponentImplementation(pcmComponent, umlComponentImplementationCandidate);
        } else {
          _routinesFacade.createCorrespondingComponentImplementation(pcmComponent);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingComponentImplementationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.DetectOrCreateCorrespondingComponentImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;
  }
  
  private RepositoryComponent pcmComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingComponentImplementationRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    
    org.eclipse.uml2.uml.Package umlComponentPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentPackage(pcmComponent), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmComponent), 
    	false // asserted
    	);
    if (umlComponentPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentPackage);
    	Optional<org.eclipse.uml2.uml.Class> umlComponentImplementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlComponentImplementation(pcmComponent, umlComponentPackage), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmComponent, umlComponentPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlComponentImplementation.isPresent() ? umlComponentImplementation.get() : null);
    userExecution.callRoutine1(pcmComponent, umlComponentPackage, umlComponentImplementation, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
