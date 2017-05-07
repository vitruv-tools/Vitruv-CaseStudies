package mir.routines.umlToJavaAttribute;

import java.io.IOException;
import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.applications.umljava.util.java.JavaTypeUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class HandleMultiplicityForJavaAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private HandleMultiplicityForJavaAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJType(final Property uAttribute, final Field jAttribute) {
      Type _type = uAttribute.getType();
      return _type;
    }
    
    public EObject getElement1(final Property uAttribute, final Field jAttribute, final org.emftext.language.java.classifiers.Class jType) {
      return jAttribute;
    }
    
    public EObject getCorrepondenceSourceJAttribute(final Property uAttribute) {
      return uAttribute;
    }
    
    public void update0Element(final Property uAttribute, final Field jAttribute, final org.emftext.language.java.classifiers.Class jType) {
      int _lower = uAttribute.getLower();
      boolean _equals = (_lower == 0);
      if (_equals) {
        int _upper = uAttribute.getUpper();
        boolean _equals_1 = (_upper == 1);
        if (_equals_1) {
          jAttribute.setTypeReference(UmlToJavaHelper.createTypeReference(uAttribute.getType(), jType));
        } else {
          int _upper_1 = uAttribute.getUpper();
          boolean _equals_2 = (_upper_1 == LiteralUnlimitedNatural.UNLIMITED);
          if (_equals_2) {
            jAttribute.setTypeReference(JavaTypeUtil.createCollectiontypeReference(UmlToJavaHelper.letUserSelectCollectionTypeName(this.userInteracting), jType));
          } else {
            int _upper_2 = uAttribute.getUpper();
            boolean _greaterThan = (_upper_2 > 1);
            if (_greaterThan) {
              jAttribute.setTypeReference(JavaTypeUtil.createCollectiontypeReference(UmlToJavaHelper.letUserSelectCollectionTypeName(this.userInteracting), jType));
            }
          }
        }
      } else {
        int _lower_1 = uAttribute.getLower();
        boolean _equals_3 = (_lower_1 == 1);
        if (_equals_3) {
          int _upper_3 = uAttribute.getUpper();
          boolean _equals_4 = (_upper_3 == 1);
          if (_equals_4) {
            jAttribute.setTypeReference(UmlToJavaHelper.createTypeReference(uAttribute.getType(), jType));
          }
        }
      }
    }
    
    public void callRoutine1(final Property uAttribute, final Field jAttribute, final org.emftext.language.java.classifiers.Class jType, @Extension final RoutinesFacade _routinesFacade) {
      if (((uAttribute.getLower() == 1) && (uAttribute.getUpper() == 1))) {
        _routinesFacade.createJavaConstructor(jAttribute);
        _routinesFacade.createJavaGetter(jAttribute);
        _routinesFacade.createJavaSetterWithNullCheck(jAttribute);
      }
    }
  }
  
  public HandleMultiplicityForJavaAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property uAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaAttribute.HandleMultiplicityForJavaAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaAttribute.RoutinesFacade(getExecutionState(), this);
    this.uAttribute = uAttribute;
  }
  
  private Property uAttribute;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine HandleMultiplicityForJavaAttributeRoutine with input:");
    getLogger().debug("   Property: " + this.uAttribute);
    
    Field jAttribute = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJAttribute(uAttribute), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (jAttribute == null) {
    	return;
    }
    registerObjectUnderModification(jAttribute);
    org.emftext.language.java.classifiers.Class jType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJType(uAttribute, jAttribute), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (jType == null) {
    	return;
    }
    registerObjectUnderModification(jType);
    // val updatedElement userExecution.getElement1(uAttribute, jAttribute, jType);
    userExecution.update0Element(uAttribute, jAttribute, jType);
    
    userExecution.callRoutine1(uAttribute, jAttribute, jType, actionsFacade);
    
    postprocessElements();
  }
}
