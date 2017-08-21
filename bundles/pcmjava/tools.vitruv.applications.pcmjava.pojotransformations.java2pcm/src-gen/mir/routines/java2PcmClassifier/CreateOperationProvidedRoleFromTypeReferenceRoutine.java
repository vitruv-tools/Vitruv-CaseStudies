package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateOperationProvidedRoleFromTypeReferenceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateOperationProvidedRoleFromTypeReferenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceBasicComponent(final Classifier classifier, final org.emftext.language.java.classifiers.Class javaClass, final TypeReference reference, final OperationInterface opInterface) {
      return javaClass;
    }
    
    public EObject getElement1(final Classifier classifier, final org.emftext.language.java.classifiers.Class javaClass, final TypeReference reference, final OperationInterface opInterface, final BasicComponent basicComponent, final OperationProvidedRole operationProvidedRole) {
      return operationProvidedRole;
    }
    
    public void updateOperationProvidedRoleElement(final Classifier classifier, final org.emftext.language.java.classifiers.Class javaClass, final TypeReference reference, final OperationInterface opInterface, final BasicComponent basicComponent, final OperationProvidedRole operationProvidedRole) {
      operationProvidedRole.setProvidedInterface__OperationProvidedRole(opInterface);
      operationProvidedRole.setProvidingEntity_ProvidedRole(basicComponent);
      String _entityName = basicComponent.getEntityName();
      String _plus = (_entityName + "_provides_");
      String _entityName_1 = opInterface.getEntityName();
      String _plus_1 = (_plus + _entityName_1);
      operationProvidedRole.setEntityName(_plus_1);
    }
    
    public EObject getElement2(final Classifier classifier, final org.emftext.language.java.classifiers.Class javaClass, final TypeReference reference, final OperationInterface opInterface, final BasicComponent basicComponent, final OperationProvidedRole operationProvidedRole) {
      return reference;
    }
    
    public EObject getCorrepondenceSourceOpInterface(final Classifier classifier, final org.emftext.language.java.classifiers.Class javaClass, final TypeReference reference) {
      return classifier;
    }
  }
  
  public CreateOperationProvidedRoleFromTypeReferenceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier classifier, final org.emftext.language.java.classifiers.Class javaClass, final TypeReference reference) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateOperationProvidedRoleFromTypeReferenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.classifier = classifier;this.javaClass = javaClass;this.reference = reference;
  }
  
  private Classifier classifier;
  
  private org.emftext.language.java.classifiers.Class javaClass;
  
  private TypeReference reference;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationProvidedRoleFromTypeReferenceRoutine with input:");
    getLogger().debug("   Classifier: " + this.classifier);
    getLogger().debug("   Class: " + this.javaClass);
    getLogger().debug("   TypeReference: " + this.reference);
    
    OperationInterface opInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpInterface(classifier, javaClass, reference), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    if (opInterface == null) {
    	return;
    }
    registerObjectUnderModification(opInterface);
    BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(classifier, javaClass, reference, opInterface), // correspondence source supplier
    	BasicComponent.class,
    	(BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    registerObjectUnderModification(basicComponent);
    OperationProvidedRole operationProvidedRole = RepositoryFactoryImpl.eINSTANCE.createOperationProvidedRole();
    notifyObjectCreated(operationProvidedRole);
    userExecution.updateOperationProvidedRoleElement(classifier, javaClass, reference, opInterface, basicComponent, operationProvidedRole);
    
    addCorrespondenceBetween(userExecution.getElement1(classifier, javaClass, reference, opInterface, basicComponent, operationProvidedRole), userExecution.getElement2(classifier, javaClass, reference, opInterface, basicComponent, operationProvidedRole), "");
    
    postprocessElements();
  }
}
