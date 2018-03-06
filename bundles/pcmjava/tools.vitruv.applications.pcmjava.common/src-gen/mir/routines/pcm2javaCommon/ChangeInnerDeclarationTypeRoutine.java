package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInnerDeclarationTypeRoutine extends AbstractRepairRoutineRealization {
  private ChangeInnerDeclarationTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      return compositeTypeField;
    }
    
    public void update0Element(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      compositeTypeField.setTypeReference(EcoreUtil.<TypeReference>copy(newTypeReference));
    }
    
    public String getRetrieveTag1(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField) {
      return "getter";
    }
    
    public String getRetrieveTag2(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod) {
      return "setter";
    }
    
    public EObject getElement2(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      return compositeTypeGetterMethod;
    }
    
    public EObject getElement3(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      return compositeTypeSetterMethod;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeField(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeGetterMethod(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeSetterMethod(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod) {
      return innerDeclaration;
    }
    
    public void update2Element(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(compositeTypeSetterMethod.getParameters());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        final Parameter parameter = compositeTypeSetterMethod.getParameters().get(0);
        parameter.setTypeReference(EcoreUtil.<TypeReference>copy(newTypeReference));
      }
    }
    
    public void update1Element(final InnerDeclaration innerDeclaration, final TypeReference newTypeReference, final Field compositeTypeField, final Method compositeTypeGetterMethod, final Method compositeTypeSetterMethod) {
      compositeTypeGetterMethod.setTypeReference(EcoreUtil.<TypeReference>copy(newTypeReference));
    }
  }
  
  public ChangeInnerDeclarationTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration, final TypeReference newTypeReference) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.ChangeInnerDeclarationTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.innerDeclaration = innerDeclaration;this.newTypeReference = newTypeReference;
  }
  
  private InnerDeclaration innerDeclaration;
  
  private TypeReference newTypeReference;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInnerDeclarationTypeRoutine with input:");
    getLogger().debug("   innerDeclaration: " + this.innerDeclaration);
    getLogger().debug("   newTypeReference: " + this.newTypeReference);
    
    org.emftext.language.java.members.Field compositeTypeField = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeField(innerDeclaration, newTypeReference), // correspondence source supplier
    	org.emftext.language.java.members.Field.class,
    	(org.emftext.language.java.members.Field _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compositeTypeField == null) {
    	return false;
    }
    registerObjectUnderModification(compositeTypeField);
    org.emftext.language.java.members.Method compositeTypeGetterMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeGetterMethod(innerDeclaration, newTypeReference, compositeTypeField), // correspondence source supplier
    	org.emftext.language.java.members.Method.class,
    	(org.emftext.language.java.members.Method _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(innerDeclaration, newTypeReference, compositeTypeField), 
    	false // asserted
    	);
    if (compositeTypeGetterMethod == null) {
    	return false;
    }
    registerObjectUnderModification(compositeTypeGetterMethod);
    org.emftext.language.java.members.Method compositeTypeSetterMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeSetterMethod(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod), // correspondence source supplier
    	org.emftext.language.java.members.Method.class,
    	(org.emftext.language.java.members.Method _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod), 
    	false // asserted
    	);
    if (compositeTypeSetterMethod == null) {
    	return false;
    }
    registerObjectUnderModification(compositeTypeSetterMethod);
    // val updatedElement userExecution.getElement1(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update0Element(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    // val updatedElement userExecution.getElement2(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update1Element(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    // val updatedElement userExecution.getElement3(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update2Element(innerDeclaration, newTypeReference, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    postprocessElements();
    
    return true;
  }
}
