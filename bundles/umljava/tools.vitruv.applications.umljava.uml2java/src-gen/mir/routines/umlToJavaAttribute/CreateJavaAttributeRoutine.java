package mir.routines.umlToJavaAttribute;

import java.io.IOException;
import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
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
    
    public void updateJavaAttributeElement(final Classifier uClassifier, final Property umlAttribute, final ConcreteClassifier jClassifier, final Field javaAttribute) {
      String _name = umlAttribute.getName();
      javaAttribute.setName(_name);
      javaAttribute.makePublic();
    }
    
    public EObject getElement1(final Classifier uClassifier, final Property umlAttribute, final ConcreteClassifier jClassifier, final Field javaAttribute) {
      return jClassifier;
    }
    
    public void update0Element(final Classifier uClassifier, final Property umlAttribute, final ConcreteClassifier jClassifier, final Field javaAttribute) {
      EList<Member> _members = jClassifier.getMembers();
      _members.add(javaAttribute);
    }
    
    public EObject getCorrepondenceSourceJClassifier(final Classifier uClassifier, final Property umlAttribute) {
      return uClassifier;
    }
    
    public EObject getElement2(final Classifier uClassifier, final Property umlAttribute, final ConcreteClassifier jClassifier, final Field javaAttribute) {
      return umlAttribute;
    }
    
    public EObject getElement3(final Classifier uClassifier, final Property umlAttribute, final ConcreteClassifier jClassifier, final Field javaAttribute) {
      return javaAttribute;
    }
  }
  
  public CreateJavaAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier uClassifier, final Property umlAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaAttribute.CreateJavaAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaAttribute.RoutinesFacade(getExecutionState(), this);
    this.uClassifier = uClassifier;this.umlAttribute = umlAttribute;
  }
  
  private Classifier uClassifier;
  
  private Property umlAttribute;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaAttributeRoutine with input:");
    getLogger().debug("   Classifier: " + this.uClassifier);
    getLogger().debug("   Property: " + this.umlAttribute);
    
    ConcreteClassifier jClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClassifier(uClassifier, umlAttribute), // correspondence source supplier
    	ConcreteClassifier.class,
    	(ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null);
    if (jClassifier == null) {
    	return;
    }
    registerObjectUnderModification(jClassifier);
    Field javaAttribute = MembersFactoryImpl.eINSTANCE.createField();
    userExecution.updateJavaAttributeElement(uClassifier, umlAttribute, jClassifier, javaAttribute);
    
    // val updatedElement userExecution.getElement1(uClassifier, umlAttribute, jClassifier, javaAttribute);
    userExecution.update0Element(uClassifier, umlAttribute, jClassifier, javaAttribute);
    
    addCorrespondenceBetween(userExecution.getElement2(uClassifier, umlAttribute, jClassifier, javaAttribute), userExecution.getElement3(uClassifier, umlAttribute, jClassifier, javaAttribute), "");
    
    postprocessElements();
  }
}
