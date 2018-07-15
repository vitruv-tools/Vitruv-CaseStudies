package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaHelper;
import tools.vitruv.domains.java.util.JavaModificationUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddInnerDeclarationToCompositeDataTypeRoutine extends AbstractRepairRoutineRealization {
  private AddInnerDeclarationToCompositeDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void update0Element(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      EList<Member> _members = dataTypeClass.getMembers();
      _members.add(innerDataTypeField);
      EList<Member> _members_1 = dataTypeClass.getMembers();
      _members_1.add(getterMethod);
      EList<Member> _members_2 = dataTypeClass.getMembers();
      _members_2.add(setterMethod);
      Pcm2JavaHelper.sortMembers(dataTypeClass.getMembers());
    }
    
    public EObject getCorrepondenceSourceDataTypeClass(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
      return dataType;
    }
    
    public String getTag1(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod) {
      return "getter";
    }
    
    public void updateSetterMethodElement(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      Pcm2JavaHelper.createSetter(innerDataTypeField, setterMethod);
    }
    
    public EObject getElement6(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      return innerDeclaration;
    }
    
    public String getTag2(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      return "setter";
    }
    
    public EObject getElement7(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      return dataTypeClass;
    }
    
    public EObject getElement1(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField) {
      return innerDataTypeField;
    }
    
    public EObject getElement4(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod) {
      return innerDeclaration;
    }
    
    public EObject getElement5(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod, final ClassMethod setterMethod) {
      return setterMethod;
    }
    
    public EObject getElement2(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField) {
      return innerDeclaration;
    }
    
    public EObject getElement3(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod) {
      return getterMethod;
    }
    
    public void updateInnerDataTypeFieldElement(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField) {
      JavaModificationUtil.createPrivateField(innerDataTypeField, EcoreUtil.<TypeReference>copy(dataTypeReference), innerDeclaration.getEntityName());
    }
    
    public void updateGetterMethodElement(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference, final org.emftext.language.java.classifiers.Class dataTypeClass, final Field innerDataTypeField, final ClassMethod getterMethod) {
      Pcm2JavaHelper.createGetter(innerDataTypeField, getterMethod);
    }
  }
  
  public AddInnerDeclarationToCompositeDataTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final TypeReference dataTypeReference) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.AddInnerDeclarationToCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.dataType = dataType;this.innerDeclaration = innerDeclaration;this.dataTypeReference = dataTypeReference;
  }
  
  private CompositeDataType dataType;
  
  private InnerDeclaration innerDeclaration;
  
  private TypeReference dataTypeReference;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddInnerDeclarationToCompositeDataTypeRoutine with input:");
    getLogger().debug("   dataType: " + this.dataType);
    getLogger().debug("   innerDeclaration: " + this.innerDeclaration);
    getLogger().debug("   dataTypeReference: " + this.dataTypeReference);
    
    org.emftext.language.java.classifiers.Class dataTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDataTypeClass(dataType, innerDeclaration, dataTypeReference), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (dataTypeClass == null) {
    	return false;
    }
    registerObjectUnderModification(dataTypeClass);
    org.emftext.language.java.members.Field innerDataTypeField = org.emftext.language.java.members.impl.MembersFactoryImpl.eINSTANCE.createField();
    notifyObjectCreated(innerDataTypeField);
    userExecution.updateInnerDataTypeFieldElement(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField);
    
    addCorrespondenceBetween(userExecution.getElement1(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField), userExecution.getElement2(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField), "");
    
    org.emftext.language.java.members.ClassMethod getterMethod = org.emftext.language.java.members.impl.MembersFactoryImpl.eINSTANCE.createClassMethod();
    notifyObjectCreated(getterMethod);
    userExecution.updateGetterMethodElement(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod);
    
    addCorrespondenceBetween(userExecution.getElement3(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod), userExecution.getElement4(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod), userExecution.getTag1(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod));
    
    org.emftext.language.java.members.ClassMethod setterMethod = org.emftext.language.java.members.impl.MembersFactoryImpl.eINSTANCE.createClassMethod();
    notifyObjectCreated(setterMethod);
    userExecution.updateSetterMethodElement(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod);
    
    addCorrespondenceBetween(userExecution.getElement5(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod), userExecution.getElement6(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod), userExecution.getTag2(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod));
    
    // val updatedElement userExecution.getElement7(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod);
    userExecution.update0Element(dataType, innerDeclaration, dataTypeReference, dataTypeClass, innerDataTypeField, getterMethod, setterMethod);
    
    postprocessElements();
    
    return true;
  }
}
