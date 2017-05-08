package mir.routines.umlToJavaAttribute;

import java.io.IOException;
import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaAttributeInEnumRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaAttributeInEnumRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateJavaAttributeElement(final Enumeration uEnum, final Property umlAttribute, final org.emftext.language.java.classifiers.Enumeration jEnum, final org.emftext.language.java.classifiers.Class customTypeClass, final Field javaAttribute) {
      javaAttribute.setName(umlAttribute.getName());
      javaAttribute.makePublic();
    }
    
    public EObject getElement1(final Enumeration uEnum, final Property umlAttribute, final org.emftext.language.java.classifiers.Enumeration jEnum, final org.emftext.language.java.classifiers.Class customTypeClass, final Field javaAttribute) {
      return jEnum;
    }
    
    public void update0Element(final Enumeration uEnum, final Property umlAttribute, final org.emftext.language.java.classifiers.Enumeration jEnum, final org.emftext.language.java.classifiers.Class customTypeClass, final Field javaAttribute) {
      EList<Member> _members = jEnum.getMembers();
      _members.add(javaAttribute);
    }
    
    public EObject getCorrepondenceSourceCustomTypeClass(final Enumeration uEnum, final Property umlAttribute, final org.emftext.language.java.classifiers.Enumeration jEnum) {
      Type _type = umlAttribute.getType();
      return _type;
    }
    
    public EObject getElement2(final Enumeration uEnum, final Property umlAttribute, final org.emftext.language.java.classifiers.Enumeration jEnum, final org.emftext.language.java.classifiers.Class customTypeClass, final Field javaAttribute) {
      return umlAttribute;
    }
    
    public EObject getElement3(final Enumeration uEnum, final Property umlAttribute, final org.emftext.language.java.classifiers.Enumeration jEnum, final org.emftext.language.java.classifiers.Class customTypeClass, final Field javaAttribute) {
      return javaAttribute;
    }
    
    public EObject getCorrepondenceSourceJEnum(final Enumeration uEnum, final Property umlAttribute) {
      return uEnum;
    }
  }
  
  public CreateJavaAttributeInEnumRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Enumeration uEnum, final Property umlAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaAttribute.CreateJavaAttributeInEnumRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaAttribute.RoutinesFacade(getExecutionState(), this);
    this.uEnum = uEnum;this.umlAttribute = umlAttribute;
  }
  
  private Enumeration uEnum;
  
  private Property umlAttribute;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaAttributeInEnumRoutine with input:");
    getLogger().debug("   Enumeration: " + this.uEnum);
    getLogger().debug("   Property: " + this.umlAttribute);
    
    org.emftext.language.java.classifiers.Enumeration jEnum = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJEnum(uEnum, umlAttribute), // correspondence source supplier
    	org.emftext.language.java.classifiers.Enumeration.class,
    	(org.emftext.language.java.classifiers.Enumeration _element) -> true, // correspondence precondition checker
    	null);
    if (jEnum == null) {
    	return;
    }
    registerObjectUnderModification(jEnum);
    org.emftext.language.java.classifiers.Class customTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomTypeClass(uEnum, umlAttribute, jEnum), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customTypeClass);
    Field javaAttribute = MembersFactoryImpl.eINSTANCE.createField();
    userExecution.updateJavaAttributeElement(uEnum, umlAttribute, jEnum, customTypeClass, javaAttribute);
    
    // val updatedElement userExecution.getElement1(uEnum, umlAttribute, jEnum, customTypeClass, javaAttribute);
    userExecution.update0Element(uEnum, umlAttribute, jEnum, customTypeClass, javaAttribute);
    
    addCorrespondenceBetween(userExecution.getElement2(uEnum, umlAttribute, jEnum, customTypeClass, javaAttribute), userExecution.getElement3(uEnum, umlAttribute, jEnum, customTypeClass, javaAttribute), "");
    
    postprocessElements();
  }
}
