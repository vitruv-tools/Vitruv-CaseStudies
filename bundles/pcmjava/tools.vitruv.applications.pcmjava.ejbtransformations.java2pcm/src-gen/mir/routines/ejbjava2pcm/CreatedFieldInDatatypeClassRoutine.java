package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatedFieldInDatatypeClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatedFieldInDatatypeClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class clazz, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
      return innerDec;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class clazz, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
      compositeDataType.getInnerDeclaration_CompositeDataType().add(innerDec);
    }
    
    public EObject getCorrepondenceSourceCompositeDataType(final org.emftext.language.java.classifiers.Class clazz, final Field field) {
      return clazz;
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class clazz, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
      return field;
    }
    
    public EObject getElement3(final org.emftext.language.java.classifiers.Class clazz, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
      return compositeDataType;
    }
    
    public void updateInnerDecElement(final org.emftext.language.java.classifiers.Class clazz, final Field field, final CompositeDataType compositeDataType, final InnerDeclaration innerDec) {
      innerDec.setDatatype_InnerDeclaration(TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(field.getTypeReference(), this.correspondenceModel, 
        this.userInteracting, compositeDataType.getRepository__DataType(), field.getArrayDimension()));
      innerDec.setEntityName(field.getName());
    }
  }
  
  public CreatedFieldInDatatypeClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class clazz, final Field field) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedFieldInDatatypeClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.clazz = clazz;this.field = field;
  }
  
  private org.emftext.language.java.classifiers.Class clazz;
  
  private Field field;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedFieldInDatatypeClassRoutine with input:");
    getLogger().debug("   clazz: " + this.clazz);
    getLogger().debug("   field: " + this.field);
    
    org.palladiosimulator.pcm.repository.CompositeDataType compositeDataType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeDataType(clazz, field), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compositeDataType == null) {
    	return false;
    }
    registerObjectUnderModification(compositeDataType);
    org.palladiosimulator.pcm.repository.InnerDeclaration innerDec = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createInnerDeclaration();
    notifyObjectCreated(innerDec);
    userExecution.updateInnerDecElement(clazz, field, compositeDataType, innerDec);
    
    addCorrespondenceBetween(userExecution.getElement1(clazz, field, compositeDataType, innerDec), userExecution.getElement2(clazz, field, compositeDataType, innerDec), "");
    
    // val updatedElement userExecution.getElement3(clazz, field, compositeDataType, innerDec);
    userExecution.update0Element(clazz, field, compositeDataType, innerDec);
    
    postprocessElements();
    
    return true;
  }
}
