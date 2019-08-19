package mir.routines.umlToJavaAttribute;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeJavaAttributeTypeRoutine extends AbstractRepairRoutineRealization {
  private ChangeJavaAttributeTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJAttribute(final Property uAttribute) {
      return uAttribute;
    }
    
    public EObject getCorrepondenceSourceJCustomType(final Property uAttribute, final Field jAttribute) {
      Type _type = uAttribute.getType();
      return _type;
    }
    
    public void callRoutine1(final Property uAttribute, final Field jAttribute, final Optional<ConcreteClassifier> jCustomType, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.umlToJavaTypePropagation.propagatePropertyTypeChanged(uAttribute, jAttribute, jCustomType.orElse(null));
      JavaMemberAndParameterUtil.updateAttributeTypeInSetters(jAttribute);
      JavaMemberAndParameterUtil.updateAttributeTypeInGetters(jAttribute);
    }
  }
  
  public ChangeJavaAttributeTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property uAttribute) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaAttribute.ChangeJavaAttributeTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.uAttribute = uAttribute;
  }
  
  private Property uAttribute;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeJavaAttributeTypeRoutine with input:");
    getLogger().debug("   uAttribute: " + this.uAttribute);
    
    org.emftext.language.java.members.Field jAttribute = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJAttribute(uAttribute), // correspondence source supplier
    	org.emftext.language.java.members.Field.class,
    	(org.emftext.language.java.members.Field _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jAttribute == null) {
    	return false;
    }
    registerObjectUnderModification(jAttribute);
    	Optional<org.emftext.language.java.classifiers.ConcreteClassifier> jCustomType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceJCustomType(uAttribute, jAttribute), // correspondence source supplier
    		org.emftext.language.java.classifiers.ConcreteClassifier.class,
    		(org.emftext.language.java.classifiers.ConcreteClassifier _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(jCustomType.isPresent() ? jCustomType.get() : null);
    userExecution.callRoutine1(uAttribute, jAttribute, jCustomType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
