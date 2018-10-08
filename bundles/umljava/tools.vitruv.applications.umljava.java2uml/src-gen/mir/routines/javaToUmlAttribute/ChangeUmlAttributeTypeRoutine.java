package mir.routines.javaToUmlAttribute;

import java.io.IOException;
import mir.routines.javaToUmlAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeUmlAttributeTypeRoutine extends AbstractRepairRoutineRealization {
  private ChangeUmlAttributeTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUAttr(final Field jAttr, final TypeReference jType) {
      return jAttr;
    }
    
    public void callRoutine1(final Field jAttr, final TypeReference jType, final Property uAttr, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.javaToUmlTypePropagation.propagateTypeChangeToTypedMultiplicityElement(uAttr, uAttr, jAttr);
    }
  }
  
  public ChangeUmlAttributeTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field jAttr, final TypeReference jType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlAttribute.ChangeUmlAttributeTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.jAttr = jAttr;this.jType = jType;
  }
  
  private Field jAttr;
  
  private TypeReference jType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlAttributeTypeRoutine with input:");
    getLogger().debug("   jAttr: " + this.jAttr);
    getLogger().debug("   jType: " + this.jType);
    
    org.eclipse.uml2.uml.Property uAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUAttr(jAttr, jType), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uAttr == null) {
    	return false;
    }
    registerObjectUnderModification(uAttr);
    userExecution.callRoutine1(jAttr, jType, uAttr, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
