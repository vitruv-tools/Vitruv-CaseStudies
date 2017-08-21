package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.impl.CompositionFactoryImpl;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateAssemblyContextRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateAssemblyContextRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ConcreteClassifier classifier, final Field field, final ComposedProvidingRequiringEntity composedProvidingRequiringEntity, final RepositoryComponent repositoryComponent, final AssemblyContext assemblyContext) {
      return assemblyContext;
    }
    
    public EObject getElement2(final ConcreteClassifier classifier, final Field field, final ComposedProvidingRequiringEntity composedProvidingRequiringEntity, final RepositoryComponent repositoryComponent, final AssemblyContext assemblyContext) {
      return field;
    }
    
    public EObject getCorrepondenceSourceComposedProvidingRequiringEntity(final ConcreteClassifier classifier, final Field field) {
      return classifier;
    }
    
    public void updateAssemblyContextElement(final ConcreteClassifier classifier, final Field field, final ComposedProvidingRequiringEntity composedProvidingRequiringEntity, final RepositoryComponent repositoryComponent, final AssemblyContext assemblyContext) {
      assemblyContext.setEntityName(field.getName());
      assemblyContext.setEncapsulatedComponent__AssemblyContext(repositoryComponent);
      assemblyContext.setParentStructure__AssemblyContext(composedProvidingRequiringEntity);
    }
    
    public EObject getCorrepondenceSourceRepositoryComponent(final ConcreteClassifier classifier, final Field field, final ComposedProvidingRequiringEntity composedProvidingRequiringEntity) {
      Classifier _targetClassifierFromTypeReference = Java2PcmHelper.getTargetClassifierFromTypeReference(field.getTypeReference());
      return _targetClassifierFromTypeReference;
    }
  }
  
  public CreateAssemblyContextRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier classifier, final Field field) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateAssemblyContextRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.classifier = classifier;this.field = field;
  }
  
  private ConcreteClassifier classifier;
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateAssemblyContextRoutine with input:");
    getLogger().debug("   ConcreteClassifier: " + this.classifier);
    getLogger().debug("   Field: " + this.field);
    
    ComposedProvidingRequiringEntity composedProvidingRequiringEntity = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComposedProvidingRequiringEntity(classifier, field), // correspondence source supplier
    	ComposedProvidingRequiringEntity.class,
    	(ComposedProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	null);
    if (composedProvidingRequiringEntity == null) {
    	return;
    }
    registerObjectUnderModification(composedProvidingRequiringEntity);
    RepositoryComponent repositoryComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepositoryComponent(classifier, field, composedProvidingRequiringEntity), // correspondence source supplier
    	RepositoryComponent.class,
    	(RepositoryComponent _element) -> true, // correspondence precondition checker
    	null);
    if (repositoryComponent == null) {
    	return;
    }
    registerObjectUnderModification(repositoryComponent);
    AssemblyContext assemblyContext = CompositionFactoryImpl.eINSTANCE.createAssemblyContext();
    notifyObjectCreated(assemblyContext);
    userExecution.updateAssemblyContextElement(classifier, field, composedProvidingRequiringEntity, repositoryComponent, assemblyContext);
    
    addCorrespondenceBetween(userExecution.getElement1(classifier, field, composedProvidingRequiringEntity, repositoryComponent, assemblyContext), userExecution.getElement2(classifier, field, composedProvidingRequiringEntity, repositoryComponent, assemblyContext), "");
    
    postprocessElements();
  }
}
