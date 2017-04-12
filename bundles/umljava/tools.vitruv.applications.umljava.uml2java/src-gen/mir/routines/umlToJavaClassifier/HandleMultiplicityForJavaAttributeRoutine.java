package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.internal.impl.LiteralUnlimitedNaturalImpl;
import org.emftext.language.java.members.Field;
import tools.vitruv.applications.umljava.util.JavaUtil;
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
        ValueSpecification _upperValue = uAttribute.getUpperValue();
        if ((_upperValue instanceof LiteralUnlimitedNaturalImpl)) {
          jAttribute.setTypeReference(JavaUtil.createCollectiontypeReference("List", jType));
        }
      } else {
        int _upper = uAttribute.getUpper();
        boolean _greaterThan = (_upper > 1);
        if (_greaterThan) {
          jAttribute.setTypeReference(JavaUtil.createCollectiontypeReference("List", jType));
        }
      }
    }
  }
  
  public HandleMultiplicityForJavaAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property uAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.HandleMultiplicityForJavaAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
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
    
    postprocessElements();
  }
}
