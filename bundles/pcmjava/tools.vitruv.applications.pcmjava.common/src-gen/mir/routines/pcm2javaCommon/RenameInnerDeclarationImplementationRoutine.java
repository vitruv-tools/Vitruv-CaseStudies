package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.parameters.Parameter;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameInnerDeclarationImplementationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameInnerDeclarationImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      return compositeTypeField;
    }
    
    public void update0Element(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      compositeTypeField.setName(innerDeclaration.getEntityName());
    }
    
    public String getRetrieveTag1(final InnerDeclaration innerDeclaration, final Field compositeTypeField) {
      return "getter";
    }
    
    public String getRetrieveTag2(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod) {
      return "setter";
    }
    
    public EObject getElement2(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      return compositeTypeGetterMethod;
    }
    
    public EObject getElement3(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      return compositeTypeSetterMethod;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeField(final InnerDeclaration innerDeclaration) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeGetterMethod(final InnerDeclaration innerDeclaration, final Field compositeTypeField) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceCompositeTypeSetterMethod(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod) {
      return innerDeclaration;
    }
    
    public void update2Element(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(compositeTypeSetterMethod.getParameters());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        final Parameter parameter = compositeTypeSetterMethod.getParameters().get(0);
        String _firstUpper = StringExtensions.toFirstUpper(innerDeclaration.getEntityName());
        String _plus = ("set" + _firstUpper);
        parameter.setName(_plus);
      }
    }
    
    public void update1Element(final InnerDeclaration innerDeclaration, final Field compositeTypeField, final ClassMethod compositeTypeGetterMethod, final ClassMethod compositeTypeSetterMethod) {
      String _firstUpper = StringExtensions.toFirstUpper(innerDeclaration.getEntityName());
      String _plus = ("get" + _firstUpper);
      compositeTypeGetterMethod.setName(_plus);
    }
  }
  
  public RenameInnerDeclarationImplementationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.RenameInnerDeclarationImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(getExecutionState(), this);
    this.innerDeclaration = innerDeclaration;
  }
  
  private InnerDeclaration innerDeclaration;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameInnerDeclarationImplementationRoutine with input:");
    getLogger().debug("   innerDeclaration: " + this.innerDeclaration);
    
    org.emftext.language.java.members.Field compositeTypeField = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeField(innerDeclaration), // correspondence source supplier
    	org.emftext.language.java.members.Field.class,
    	(org.emftext.language.java.members.Field _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compositeTypeField == null) {
    	return false;
    }
    registerObjectUnderModification(compositeTypeField);
    org.emftext.language.java.members.ClassMethod compositeTypeGetterMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeGetterMethod(innerDeclaration, compositeTypeField), // correspondence source supplier
    	org.emftext.language.java.members.ClassMethod.class,
    	(org.emftext.language.java.members.ClassMethod _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(innerDeclaration, compositeTypeField), 
    	false // asserted
    	);
    if (compositeTypeGetterMethod == null) {
    	return false;
    }
    registerObjectUnderModification(compositeTypeGetterMethod);
    org.emftext.language.java.members.ClassMethod compositeTypeSetterMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeTypeSetterMethod(innerDeclaration, compositeTypeField, compositeTypeGetterMethod), // correspondence source supplier
    	org.emftext.language.java.members.ClassMethod.class,
    	(org.emftext.language.java.members.ClassMethod _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(innerDeclaration, compositeTypeField, compositeTypeGetterMethod), 
    	false // asserted
    	);
    if (compositeTypeSetterMethod == null) {
    	return false;
    }
    registerObjectUnderModification(compositeTypeSetterMethod);
    // val updatedElement userExecution.getElement1(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update0Element(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    // val updatedElement userExecution.getElement2(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update1Element(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    // val updatedElement userExecution.getElement3(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    userExecution.update2Element(innerDeclaration, compositeTypeField, compositeTypeGetterMethod, compositeTypeSetterMethod);
    
    postprocessElements();
    
    return true;
  }
}
