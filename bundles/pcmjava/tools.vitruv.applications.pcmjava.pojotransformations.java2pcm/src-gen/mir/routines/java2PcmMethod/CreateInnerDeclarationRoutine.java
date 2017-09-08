package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateInnerDeclarationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateInnerDeclarationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ConcreteClassifier classifier, final Field javaField, final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceCompositeDataType(final ConcreteClassifier classifier, final Field javaField) {
      return classifier;
    }
    
    public EObject getElement2(final ConcreteClassifier classifier, final Field javaField, final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration) {
      return javaField;
    }
    
    public void updateInnerDeclarationElement(final ConcreteClassifier classifier, final Field javaField, final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration) {
      innerDeclaration.setEntityName(javaField.getName());
      innerDeclaration.setDatatype_InnerDeclaration(TypeReferenceCorrespondenceHelper.getDataTypeFromTypeReference(javaField.getTypeReference(), this.correspondenceModel, 
        this.userInteracting, null));
      innerDeclaration.setCompositeDataType_InnerDeclaration(compositeDataType);
    }
  }
  
  public CreateInnerDeclarationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier classifier, final Field javaField) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateInnerDeclarationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.classifier = classifier;this.javaField = javaField;
  }
  
  private ConcreteClassifier classifier;
  
  private Field javaField;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInnerDeclarationRoutine with input:");
    getLogger().debug("   classifier: " + this.classifier);
    getLogger().debug("   javaField: " + this.javaField);
    
    org.palladiosimulator.pcm.repository.CompositeDataType compositeDataType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeDataType(classifier, javaField), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compositeDataType == null) {
    	return false;
    }
    registerObjectUnderModification(compositeDataType);
    org.palladiosimulator.pcm.repository.InnerDeclaration innerDeclaration = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createInnerDeclaration();
    notifyObjectCreated(innerDeclaration);
    userExecution.updateInnerDeclarationElement(classifier, javaField, compositeDataType, innerDeclaration);
    
    addCorrespondenceBetween(userExecution.getElement1(classifier, javaField, compositeDataType, innerDeclaration), userExecution.getElement2(classifier, javaField, compositeDataType, innerDeclaration), "");
    
    postprocessElements();
    
    return true;
  }
}
