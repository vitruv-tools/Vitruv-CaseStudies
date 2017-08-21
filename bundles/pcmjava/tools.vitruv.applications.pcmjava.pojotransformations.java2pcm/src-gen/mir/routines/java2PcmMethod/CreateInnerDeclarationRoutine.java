package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
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
    
    public EObject getElement1(final ConcreteClassifier classifier, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceCompositeDataType(final ConcreteClassifier classifier, final Field field) {
      return classifier;
    }
    
    public EObject getElement2(final ConcreteClassifier classifier, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration) {
      return field;
    }
    
    public void updateInnerDeclarationElement(final ConcreteClassifier classifier, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDeclaration) {
      innerDeclaration.setEntityName(field.getName());
      innerDeclaration.setDatatype_InnerDeclaration(TypeReferenceCorrespondenceHelper.getDataTypeFromTypeReference(field.getTypeReference(), this.correspondenceModel, 
        this.userInteracting, null));
      innerDeclaration.setCompositeDataType_InnerDeclaration(compositeDataType);
    }
  }
  
  public CreateInnerDeclarationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier classifier, final Field field) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateInnerDeclarationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.classifier = classifier;this.field = field;
  }
  
  private ConcreteClassifier classifier;
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInnerDeclarationRoutine with input:");
    getLogger().debug("   ConcreteClassifier: " + this.classifier);
    getLogger().debug("   Field: " + this.field);
    
    CompositeDataType compositeDataType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeDataType(classifier, field), // correspondence source supplier
    	CompositeDataType.class,
    	(CompositeDataType _element) -> true, // correspondence precondition checker
    	null);
    if (compositeDataType == null) {
    	return;
    }
    registerObjectUnderModification(compositeDataType);
    InnerDeclaration innerDeclaration = RepositoryFactoryImpl.eINSTANCE.createInnerDeclaration();
    notifyObjectCreated(innerDeclaration);
    userExecution.updateInnerDeclarationElement(classifier, field, compositeDataType, innerDeclaration);
    
    addCorrespondenceBetween(userExecution.getElement1(classifier, field, compositeDataType, innerDeclaration), userExecution.getElement2(classifier, field, compositeDataType, innerDeclaration), "");
    
    postprocessElements();
  }
}
