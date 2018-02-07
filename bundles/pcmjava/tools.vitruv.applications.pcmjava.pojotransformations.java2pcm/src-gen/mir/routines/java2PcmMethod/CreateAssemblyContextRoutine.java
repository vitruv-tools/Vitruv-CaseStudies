package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateAssemblyContextRoutine extends AbstractRepairRoutineRealization {
  private CreateAssemblyContextRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ConcreteClassifier classifier, final Field javaField, final ComposedProvidingRequiringEntity composedProvidingRequiringEntity, final RepositoryComponent repositoryComponent, final AssemblyContext assemblyContext) {
      return assemblyContext;
    }
    
    public EObject getElement2(final ConcreteClassifier classifier, final Field javaField, final ComposedProvidingRequiringEntity composedProvidingRequiringEntity, final RepositoryComponent repositoryComponent, final AssemblyContext assemblyContext) {
      return javaField;
    }
    
    public EObject getCorrepondenceSourceComposedProvidingRequiringEntity(final ConcreteClassifier classifier, final Field javaField) {
      return classifier;
    }
    
    public void updateAssemblyContextElement(final ConcreteClassifier classifier, final Field javaField, final ComposedProvidingRequiringEntity composedProvidingRequiringEntity, final RepositoryComponent repositoryComponent, final AssemblyContext assemblyContext) {
      assemblyContext.setEntityName(javaField.getName());
      assemblyContext.setEncapsulatedComponent__AssemblyContext(repositoryComponent);
      assemblyContext.setParentStructure__AssemblyContext(composedProvidingRequiringEntity);
    }
    
    public EObject getCorrepondenceSourceRepositoryComponent(final ConcreteClassifier classifier, final Field javaField, final ComposedProvidingRequiringEntity composedProvidingRequiringEntity) {
      Classifier _targetClassifierFromTypeReference = Java2PcmHelper.getTargetClassifierFromTypeReference(javaField.getTypeReference());
      return _targetClassifierFromTypeReference;
    }
  }
  
  public CreateAssemblyContextRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier classifier, final Field javaField) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateAssemblyContextRoutine.ActionUserExecution(getExecutionState(), this);
    this.classifier = classifier;this.javaField = javaField;
  }
  
  private ConcreteClassifier classifier;
  
  private Field javaField;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateAssemblyContextRoutine with input:");
    getLogger().debug("   classifier: " + this.classifier);
    getLogger().debug("   javaField: " + this.javaField);
    
    org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity composedProvidingRequiringEntity = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComposedProvidingRequiringEntity(classifier, javaField), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (composedProvidingRequiringEntity == null) {
    	return false;
    }
    registerObjectUnderModification(composedProvidingRequiringEntity);
    org.palladiosimulator.pcm.repository.RepositoryComponent repositoryComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepositoryComponent(classifier, javaField, composedProvidingRequiringEntity), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (repositoryComponent == null) {
    	return false;
    }
    registerObjectUnderModification(repositoryComponent);
    org.palladiosimulator.pcm.core.composition.AssemblyContext assemblyContext = org.palladiosimulator.pcm.core.composition.impl.CompositionFactoryImpl.eINSTANCE.createAssemblyContext();
    notifyObjectCreated(assemblyContext);
    userExecution.updateAssemblyContextElement(classifier, javaField, composedProvidingRequiringEntity, repositoryComponent, assemblyContext);
    
    addCorrespondenceBetween(userExecution.getElement1(classifier, javaField, composedProvidingRequiringEntity, repositoryComponent, assemblyContext), userExecution.getElement2(classifier, javaField, composedProvidingRequiringEntity, repositoryComponent, assemblyContext), "");
    
    postprocessElements();
    
    return true;
  }
}
