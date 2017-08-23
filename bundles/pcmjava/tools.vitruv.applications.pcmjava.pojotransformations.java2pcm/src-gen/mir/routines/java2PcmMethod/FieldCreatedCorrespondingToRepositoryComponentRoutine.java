package mir.routines.java2PcmMethod;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class FieldCreatedCorrespondingToRepositoryComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private FieldCreatedCorrespondingToRepositoryComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceRepoComponent(final Classifier classifier, final Field field, final RepositoryComponent repositoryComponent) {
      ConcreteClassifier _containingConcreteClassifier = field.getContainingConcreteClassifier();
      return _containingConcreteClassifier;
    }
    
    public EObject getCorrepondenceSourceRepositoryComponent(final Classifier classifier, final Field field) {
      return classifier;
    }
    
    public void callRoutine1(final Classifier classifier, final Field field, final RepositoryComponent repositoryComponent, final RepositoryComponent repoComponent, @Extension final RoutinesFacade _routinesFacade) {
      Iterable<OperationProvidedRole> operationProvidedRoles = Iterables.<OperationProvidedRole>filter(repositoryComponent.getProvidedRoles_InterfaceProvidingEntity(), OperationProvidedRole.class);
      for (final OperationProvidedRole providedRole : operationProvidedRoles) {
        _routinesFacade.createOperationRequiredRoleCorrespondingToField(field, providedRole.getProvidedInterface__OperationProvidedRole(), repoComponent);
      }
    }
  }
  
  public FieldCreatedCorrespondingToRepositoryComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier classifier, final Field field) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.FieldCreatedCorrespondingToRepositoryComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.classifier = classifier;this.field = field;
  }
  
  private Classifier classifier;
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine FieldCreatedCorrespondingToRepositoryComponentRoutine with input:");
    getLogger().debug("   classifier: " + this.classifier);
    getLogger().debug("   field: " + this.field);
    
    org.palladiosimulator.pcm.repository.RepositoryComponent repositoryComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepositoryComponent(classifier, field), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	null);
    if (repositoryComponent == null) {
    	return;
    }
    registerObjectUnderModification(repositoryComponent);
    org.palladiosimulator.pcm.repository.RepositoryComponent repoComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepoComponent(classifier, field, repositoryComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	null);
    if (repoComponent == null) {
    	return;
    }
    registerObjectUnderModification(repoComponent);
    userExecution.callRoutine1(classifier, field, repositoryComponent, repoComponent, actionsFacade);
    
    postprocessElements();
  }
}
