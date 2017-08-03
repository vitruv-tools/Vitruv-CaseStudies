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
public class FooRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private FooRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceF(final ConcreteClassifier classifier, final Field field) {
      return classifier;
    }
    
    public void updateInnerDeclarationElement(final ConcreteClassifier classifier, final Field field, final CompositeDataType f, final InnerDeclaration innerDeclaration) {
      innerDeclaration.setEntityName(field.getName());
      innerDeclaration.setDatatype_InnerDeclaration(TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(field.getTypeReference(), this.correspondenceModel, 
        this.userInteracting, null, field.getArrayDimension()));
      innerDeclaration.setCompositeDataType_InnerDeclaration(f);
    }
  }
  
  public FooRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier classifier, final Field field) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.FooRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.classifier = classifier;this.field = field;
  }
  
  private ConcreteClassifier classifier;
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine FooRoutine with input:");
    getLogger().debug("   ConcreteClassifier: " + this.classifier);
    getLogger().debug("   Field: " + this.field);
    
    CompositeDataType f = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceF(classifier, field), // correspondence source supplier
    	CompositeDataType.class,
    	(CompositeDataType _element) -> true, // correspondence precondition checker
    	null);
    if (f == null) {
    	return;
    }
    registerObjectUnderModification(f);
    InnerDeclaration innerDeclaration = RepositoryFactoryImpl.eINSTANCE.createInnerDeclaration();
    notifyObjectCreated(innerDeclaration);
    userExecution.updateInnerDeclarationElement(classifier, field, f, innerDeclaration);
    
    postprocessElements();
  }
}
