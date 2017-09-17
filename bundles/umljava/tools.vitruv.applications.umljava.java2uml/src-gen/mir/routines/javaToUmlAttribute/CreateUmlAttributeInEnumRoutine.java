package mir.routines.javaToUmlAttribute;

import java.io.IOException;
import mir.routines.javaToUmlAttribute.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.members.Field;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlAttributeInEnumRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlAttributeInEnumRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Enumeration jEnum, final Field jAttr, final org.eclipse.uml2.uml.Enumeration uEnum, final Property uAttr) {
      return uAttr;
    }
    
    public void update0Element(final Enumeration jEnum, final Field jAttr, final org.eclipse.uml2.uml.Enumeration uEnum, final Property uAttr) {
      EList<Property> _ownedAttributes = uEnum.getOwnedAttributes();
      _ownedAttributes.add(uAttr);
    }
    
    public EObject getCorrepondenceSourceUEnum(final Enumeration jEnum, final Field jAttr) {
      return jEnum;
    }
    
    public EObject getElement2(final Enumeration jEnum, final Field jAttr, final org.eclipse.uml2.uml.Enumeration uEnum, final Property uAttr) {
      return jAttr;
    }
    
    public EObject getElement3(final Enumeration jEnum, final Field jAttr, final org.eclipse.uml2.uml.Enumeration uEnum, final Property uAttr) {
      return uEnum;
    }
    
    public void updateUAttrElement(final Enumeration jEnum, final Field jAttr, final org.eclipse.uml2.uml.Enumeration uEnum, final Property uAttr) {
      uAttr.setName(jAttr.getName());
    }
  }
  
  public CreateUmlAttributeInEnumRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Enumeration jEnum, final Field jAttr) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlAttribute.CreateUmlAttributeInEnumRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlAttribute.RoutinesFacade(getExecutionState(), this);
    this.jEnum = jEnum;this.jAttr = jAttr;
  }
  
  private Enumeration jEnum;
  
  private Field jAttr;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlAttributeInEnumRoutine with input:");
    getLogger().debug("   jEnum: " + this.jEnum);
    getLogger().debug("   jAttr: " + this.jAttr);
    
    org.eclipse.uml2.uml.Enumeration uEnum = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUEnum(jEnum, jAttr), // correspondence source supplier
    	org.eclipse.uml2.uml.Enumeration.class,
    	(org.eclipse.uml2.uml.Enumeration _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uEnum == null) {
    	return false;
    }
    registerObjectUnderModification(uEnum);
    org.eclipse.uml2.uml.Property uAttr = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createProperty();
    notifyObjectCreated(uAttr);
    userExecution.updateUAttrElement(jEnum, jAttr, uEnum, uAttr);
    
    addCorrespondenceBetween(userExecution.getElement1(jEnum, jAttr, uEnum, uAttr), userExecution.getElement2(jEnum, jAttr, uEnum, uAttr), "");
    
    // val updatedElement userExecution.getElement3(jEnum, jAttr, uEnum, uAttr);
    userExecution.update0Element(jEnum, jAttr, uEnum, uAttr);
    
    postprocessElements();
    
    return true;
  }
}
