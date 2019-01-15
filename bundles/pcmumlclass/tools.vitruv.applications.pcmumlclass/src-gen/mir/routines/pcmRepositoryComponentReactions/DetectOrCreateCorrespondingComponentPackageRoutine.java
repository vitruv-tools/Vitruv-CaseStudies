package mir.routines.pcmRepositoryComponentReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingComponentPackageRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingComponentPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlComponentPackage(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage) {
      return pcmComponent;
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent, final Repository pcmRepository) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlRepositoryPackage(final RepositoryComponent pcmComponent, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public void callRoutine1(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage, final Optional<org.eclipse.uml2.uml.Package> umlComponentPackage, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlComponentPackage.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<org.eclipse.uml2.uml.Package, Boolean> _function = (org.eclipse.uml2.uml.Package it) -> {
          String _name = it.getName();
          String _firstLower = StringExtensions.toFirstLower(pcmComponent.getEntityName());
          return Boolean.valueOf(Objects.equal(_name, _firstLower));
        };
        final org.eclipse.uml2.uml.Package umlComponentPackageCandidate = IterableExtensions.<org.eclipse.uml2.uml.Package>findFirst(umlRepositoryPackage.getNestedPackages(), _function);
        if ((umlComponentPackageCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingComponentPackage(pcmComponent, umlComponentPackageCandidate);
        } else {
          _routinesFacade.createCorrespondingComponentPackage(pcmComponent, pcmRepository);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingComponentPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent, final Repository pcmRepository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.DetectOrCreateCorrespondingComponentPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.pcmRepository = pcmRepository;
  }
  
  private RepositoryComponent pcmComponent;
  
  private Repository pcmRepository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingComponentPackageRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   pcmRepository: " + this.pcmRepository);
    
    org.eclipse.uml2.uml.Package umlRepositoryPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRepositoryPackage(pcmComponent, pcmRepository), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmComponent, pcmRepository), 
    	false // asserted
    	);
    if (umlRepositoryPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlRepositoryPackage);
    	Optional<org.eclipse.uml2.uml.Package> umlComponentPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlComponentPackage(pcmComponent, pcmRepository, umlRepositoryPackage), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmComponent, pcmRepository, umlRepositoryPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlComponentPackage.isPresent() ? umlComponentPackage.get() : null);
    userExecution.callRoutine1(pcmComponent, pcmRepository, umlRepositoryPackage, umlComponentPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
