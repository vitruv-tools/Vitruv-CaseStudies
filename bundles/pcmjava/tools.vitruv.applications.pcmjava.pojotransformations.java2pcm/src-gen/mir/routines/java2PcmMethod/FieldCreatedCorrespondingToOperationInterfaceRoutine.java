package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class FieldCreatedCorrespondingToOperationInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private FieldCreatedCorrespondingToOperationInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceRepoComponent(final Classifier classifier, final Field field, final OperationInterface correspondingInterface) {
      ConcreteClassifier _containingConcreteClassifier = field.getContainingConcreteClassifier();
      return _containingConcreteClassifier;
    }
    
    public EObject getCorrepondenceSourceCorrespondingInterface(final Classifier classifier, final Field field) {
      return classifier;
    }
    
    public void callRoutine1(final Classifier classifier, final Field field, final OperationInterface correspondingInterface, final RepositoryComponent repoComponent, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createOperationRequiredRoleCorrespondingToField(field, correspondingInterface, repoComponent);
    }
  }
  
  public FieldCreatedCorrespondingToOperationInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier classifier, final Field field) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.FieldCreatedCorrespondingToOperationInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.classifier = classifier;this.field = field;
  }
  
  private Classifier classifier;
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine FieldCreatedCorrespondingToOperationInterfaceRoutine with input:");
    getLogger().debug("   Classifier: " + this.classifier);
    getLogger().debug("   Field: " + this.field);
    
    OperationInterface correspondingInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCorrespondingInterface(classifier, field), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    if (correspondingInterface == null) {
    	return;
    }
    registerObjectUnderModification(correspondingInterface);
    RepositoryComponent repoComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepoComponent(classifier, field, correspondingInterface), // correspondence source supplier
    	RepositoryComponent.class,
    	(RepositoryComponent _element) -> true, // correspondence precondition checker
    	null);
    if (repoComponent == null) {
    	return;
    }
    registerObjectUnderModification(repoComponent);
    userExecution.callRoutine1(classifier, field, correspondingInterface, repoComponent, actionsFacade);
    
    postprocessElements();
  }
}
