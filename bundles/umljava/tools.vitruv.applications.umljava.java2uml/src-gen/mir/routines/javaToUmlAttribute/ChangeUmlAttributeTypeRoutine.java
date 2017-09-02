package mir.routines.javaToUmlAttribute;

import java.io.IOException;
import mir.routines.javaToUmlAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeUmlAttributeTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeUmlAttributeTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUAttr(final Field jAttr, final TypeReference jType) {
      return jAttr;
    }
    
    public EObject getElement1(final Field jAttr, final TypeReference jType, final Property uAttr) {
      return uAttr;
    }
    
    public void update0Element(final Field jAttr, final TypeReference jType, final Property uAttr) {
      uAttr.setType(JavaToUmlHelper.getUmlType(jType, JavaToUmlHelper.getUmlModel(this.changePropagationObservable, this.correspondenceModel, this.userInteracting), this.correspondenceModel));
    }
  }
  
  public ChangeUmlAttributeTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field jAttr, final TypeReference jType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlAttribute.ChangeUmlAttributeTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlAttribute.RoutinesFacade(getExecutionState(), this);
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
    // val updatedElement userExecution.getElement1(jAttr, jType, uAttr);
    userExecution.update0Element(jAttr, jType, uAttr);
    
    postprocessElements();
    
    return true;
  }
}
