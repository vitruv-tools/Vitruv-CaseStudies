package mir.routines.javaToUmlAttribute;

import java.io.IOException;
import mir.routines.javaToUmlAttribute.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.Type;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Classifier uType, final Property uAttr) {
      return uAttr;
    }
    
    public EObject getCorrepondenceSourceUClass(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
      return jClass;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Classifier uType, final Property uAttr) {
      EList<Property> _ownedAttributes = uClass.getOwnedAttributes();
      _ownedAttributes.add(uAttr);
    }
    
    public EObject getCorrepondenceSourceUType(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass) {
      Type _target = jAttr.getTypeReference().getTarget();
      return _target;
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Classifier uType, final Property uAttr) {
      return jAttr;
    }
    
    public EObject getElement3(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Classifier uType, final Property uAttr) {
      return uClass;
    }
    
    public void updateUAttrElement(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Classifier uType, final Property uAttr) {
      uAttr.setName(jAttr.getName());
    }
  }
  
  public CreateUmlAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlAttribute.CreateUmlAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlAttribute.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;this.jAttr = jAttr;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private Field jAttr;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlAttributeRoutine with input:");
    getLogger().debug("   Class: " + this.jClass);
    getLogger().debug("   Field: " + this.jAttr);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, jAttr), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    if (uClass == null) {
    	return;
    }
    registerObjectUnderModification(uClass);
    Classifier uType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUType(jClass, jAttr, uClass), // correspondence source supplier
    	Classifier.class,
    	(Classifier _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(uType);
    Property uAttr = UMLFactoryImpl.eINSTANCE.createProperty();
    userExecution.updateUAttrElement(jClass, jAttr, uClass, uType, uAttr);
    
    addCorrespondenceBetween(userExecution.getElement1(jClass, jAttr, uClass, uType, uAttr), userExecution.getElement2(jClass, jAttr, uClass, uType, uAttr), "");
    
    // val updatedElement userExecution.getElement3(jClass, jAttr, uClass, uType, uAttr);
    userExecution.update0Element(jClass, jAttr, uClass, uType, uAttr);
    
    postprocessElements();
  }
}
