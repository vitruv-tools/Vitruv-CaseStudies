package mir.routines.umlRepositoryComponentPackageReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRepositoryComponentPackageReactions.RoutinesFacade;
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
public class DetectOrCreateCorrespondingRepositoryComponentRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingRepositoryComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
      return umlParentPkg;
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository) {
      return umlPkg;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository, final Optional<RepositoryComponent> pcmComponent, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmComponent.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<RepositoryComponent, Boolean> _function = (RepositoryComponent it) -> {
          String _entityName = it.getEntityName();
          String _firstUpper = StringExtensions.toFirstUpper(umlPkg.getName());
          return Boolean.valueOf(Objects.equal(_entityName, _firstUpper));
        };
        final RepositoryComponent pcmComponentCandidate = IterableExtensions.<RepositoryComponent>findFirst(pcmRepository.getComponents__Repository(), _function);
        if ((pcmComponentCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingRepositoryComponent(umlPkg, pcmComponentCandidate);
        } else {
          _routinesFacade.userDisambiguateCorrespondingRepositoryComponentType(umlPkg, umlParentPkg);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingRepositoryComponentRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryComponentPackageReactions.DetectOrCreateCorrespondingRepositoryComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPkg = umlPkg;this.umlParentPkg = umlParentPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlPkg;
  
  private org.eclipse.uml2.uml.Package umlParentPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingRepositoryComponentRoutine with input:");
    getLogger().debug("   umlPkg: " + this.umlPkg);
    getLogger().debug("   umlParentPkg: " + this.umlParentPkg);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlPkg, umlParentPkg), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlPkg, umlParentPkg), 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    	Optional<org.palladiosimulator.pcm.repository.RepositoryComponent> pcmComponent = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmComponent(umlPkg, umlParentPkg, pcmRepository), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    		(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlPkg, umlParentPkg, pcmRepository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmComponent.isPresent() ? pcmComponent.get() : null);
    userExecution.callRoutine1(umlPkg, umlParentPkg, pcmRepository, pcmComponent, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
