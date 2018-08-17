package mir.routines.umlIPREClassReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlIPREClassReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class WarnUserAboutImplRemoveIfIPRECorrespondenceExistsRoutine extends AbstractRepairRoutineRealization {
  private WarnUserAboutImplRemoveIfIPRECorrespondenceExistsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final Optional<RepositoryComponent> pcmRepositoryComponent, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmRepositoryComponent.isPresent();
      if (_isPresent) {
        this.getLogger().warn((DefaultLiterals.WARNING_IPRE_IMPLEMENTATION_REMOVED + umlClass));
      }
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourcePcmRepositoryComponent(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
      return umlClass;
    }
  }
  
  public WarnUserAboutImplRemoveIfIPRECorrespondenceExistsRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlIPREClassReactions.WarnUserAboutImplRemoveIfIPRECorrespondenceExistsRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlClass = umlClass;this.umlPackage = umlPackage;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private org.eclipse.uml2.uml.Package umlPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine WarnUserAboutImplRemoveIfIPRECorrespondenceExistsRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    getLogger().debug("   umlPackage: " + this.umlPackage);
    
    	Optional<org.palladiosimulator.pcm.repository.RepositoryComponent> pcmRepositoryComponent = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmRepositoryComponent(umlClass, umlPackage), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    		(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlClass, umlPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmRepositoryComponent.isPresent() ? pcmRepositoryComponent.get() : null);
    userExecution.executeAction1(umlClass, umlPackage, pcmRepositoryComponent, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
