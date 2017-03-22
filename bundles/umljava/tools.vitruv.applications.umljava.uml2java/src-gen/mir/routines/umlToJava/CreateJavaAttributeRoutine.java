package mir.routines.umlToJava;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateJavaAttributeElement(final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute, final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.classifiers.Class customTypeClass, final Field javaAttribute) {
      String _name = umlAttribute.getName();
      boolean _equals = Objects.equal(_name, null);
      if (_equals) {
        javaAttribute.setName("DefaultAttributeName");
      } else {
        String _name_1 = umlAttribute.getName();
        javaAttribute.setName(_name_1);
      }
      DataType _datatype = umlAttribute.getDatatype();
      TypeReference _createTypeReference = UmlToJavaHelper.createTypeReference(_datatype, customTypeClass);
      javaAttribute.setTypeReference(_createTypeReference);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute, final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.classifiers.Class customTypeClass, final Field javaAttribute) {
      return javaClass;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute, final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.classifiers.Class customTypeClass, final Field javaAttribute) {
      EList<Member> _members = javaClass.getMembers();
      _members.add(javaAttribute);
    }
    
    public EObject getCorrepondenceSourceJavaClass(final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSourceCustomTypeClass(final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute, final org.emftext.language.java.classifiers.Class javaClass) {
      Type _type = umlAttribute.getType();
      return _type;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute, final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.classifiers.Class customTypeClass, final Field javaAttribute) {
      return umlAttribute;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute, final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.classifiers.Class customTypeClass, final Field javaAttribute) {
      return javaAttribute;
    }
  }
  
  public CreateJavaAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;this.umlAttribute = umlAttribute;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private Property umlAttribute;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaAttributeRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    getLogger().debug("   Property: " + this.umlAttribute);
    
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClass(umlClass, umlAttribute), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (javaClass == null) {
    	return;
    }
    initializeRetrieveElementState(javaClass);
    org.emftext.language.java.classifiers.Class customTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomTypeClass(umlClass, umlAttribute, javaClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(customTypeClass);
    Field javaAttribute = MembersFactoryImpl.eINSTANCE.createField();
    initializeCreateElementState(javaAttribute);
    userExecution.updateJavaAttributeElement(umlClass, umlAttribute, javaClass, customTypeClass, javaAttribute);
    
    // val updatedElement userExecution.getElement1(umlClass, umlAttribute, javaClass, customTypeClass, javaAttribute);
    userExecution.update0Element(umlClass, umlAttribute, javaClass, customTypeClass, javaAttribute);
    
    addCorrespondenceBetween(userExecution.getElement2(umlClass, umlAttribute, javaClass, customTypeClass, javaAttribute), userExecution.getElement3(umlClass, umlAttribute, javaClass, customTypeClass, javaAttribute), "");
    
    postprocessElementStates();
  }
}
