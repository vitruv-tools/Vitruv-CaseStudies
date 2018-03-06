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
  private FieldCreatedCorrespondingToOperationInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCorrespondingInterface(final Classifier classifier, final Field javaField) {
      return classifier;
    }
    
    public EObject getCorrepondenceSourceRepositoryComponent(final Classifier classifier, final Field javaField, final OperationInterface correspondingInterface) {
      ConcreteClassifier _containingConcreteClassifier = javaField.getContainingConcreteClassifier();
      return _containingConcreteClassifier;
    }
    
    public void callRoutine1(final Classifier classifier, final Field javaField, final OperationInterface correspondingInterface, final RepositoryComponent repositoryComponent, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createOperationRequiredRoleCorrespondingToField(javaField, correspondingInterface, repositoryComponent);
    }
  }
  
  public FieldCreatedCorrespondingToOperationInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier classifier, final Field javaField) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.FieldCreatedCorrespondingToOperationInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.classifier = classifier;this.javaField = javaField;
  }
  
  private Classifier classifier;
  
  private Field javaField;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine FieldCreatedCorrespondingToOperationInterfaceRoutine with input:");
    getLogger().debug("   classifier: " + this.classifier);
    getLogger().debug("   javaField: " + this.javaField);
    
    org.palladiosimulator.pcm.repository.OperationInterface correspondingInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCorrespondingInterface(classifier, javaField), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (correspondingInterface == null) {
    	return false;
    }
    registerObjectUnderModification(correspondingInterface);
    org.palladiosimulator.pcm.repository.RepositoryComponent repositoryComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepositoryComponent(classifier, javaField, correspondingInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (repositoryComponent == null) {
    	return false;
    }
    registerObjectUnderModification(repositoryComponent);
    userExecution.callRoutine1(classifier, javaField, correspondingInterface, repositoryComponent, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
