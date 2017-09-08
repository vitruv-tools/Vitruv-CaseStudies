package mir.routines.javaToUmlAttribute;

import java.io.IOException;
import mir.routines.javaToUmlAttribute.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.emftext.language.java.members.Field;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlAttributeInClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlAttributeInClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Property uAttr) {
      return uAttr;
    }
    
    public EObject getCorrepondenceSourceUClass(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
      return jClass;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Property uAttr) {
      EList<Property> _ownedAttributes = uClass.getOwnedAttributes();
      _ownedAttributes.add(uAttr);
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Property uAttr) {
      return jAttr;
    }
    
    public EObject getElement3(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Property uAttr) {
      return uClass;
    }
    
    public void updateUAttrElement(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Property uAttr) {
      uAttr.setName(jAttr.getName());
    }
  }
  
  public CreateUmlAttributeInClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlAttribute.CreateUmlAttributeInClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlAttribute.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;this.jAttr = jAttr;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private Field jAttr;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlAttributeInClassRoutine with input:");
    getLogger().debug("   jClass: " + this.jClass);
    getLogger().debug("   jAttr: " + this.jAttr);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, jAttr), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uClass == null) {
    	return false;
    }
    registerObjectUnderModification(uClass);
    org.eclipse.uml2.uml.Property uAttr = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createProperty();
    notifyObjectCreated(uAttr);
    userExecution.updateUAttrElement(jClass, jAttr, uClass, uAttr);
    
    addCorrespondenceBetween(userExecution.getElement1(jClass, jAttr, uClass, uAttr), userExecution.getElement2(jClass, jAttr, uClass, uAttr), "");
    
    // val updatedElement userExecution.getElement3(jClass, jAttr, uClass, uAttr);
    userExecution.update0Element(jClass, jAttr, uClass, uAttr);
    
    postprocessElements();
    
    return true;
  }
}
