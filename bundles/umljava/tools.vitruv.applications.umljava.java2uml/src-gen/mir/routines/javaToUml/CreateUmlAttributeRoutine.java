package mir.routines.javaToUml;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;
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
    
    public EObject getCorrepondenceSourceUType(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass) {
      TypeReference _typeReference = jAttr.getTypeReference();
      Type _target = _typeReference.getTarget();
      return _target;
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Classifier uType, final Property uAttr) {
      return jAttr;
    }
    
    public void updateUAttrElement(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr, final org.eclipse.uml2.uml.Class uClass, final Classifier uType, final Property uAttr) {
      String _name = jAttr.getName();
      boolean _notEquals = (!Objects.equal(_name, null));
      if (_notEquals) {
        String _name_1 = jAttr.getName();
        uAttr.setName(_name_1);
      } else {
        uAttr.setName("DefaultUmlAttributeName");
      }
      boolean _notEquals_1 = (!Objects.equal(uType, null));
      if (_notEquals_1) {
        uAttr.setType(uType);
      }
    }
  }
  
  public CreateUmlAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.CreateUmlAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
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
    initializeRetrieveElementState(uClass);
    Classifier uType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUType(jClass, jAttr, uClass), // correspondence source supplier
    	Classifier.class,
    	(Classifier _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(uType);
    Property uAttr = UMLFactoryImpl.eINSTANCE.createProperty();
    initializeCreateElementState(uAttr);
    userExecution.updateUAttrElement(jClass, jAttr, uClass, uType, uAttr);
    
    addCorrespondenceBetween(userExecution.getElement1(jClass, jAttr, uClass, uType, uAttr), userExecution.getElement2(jClass, jAttr, uClass, uType, uAttr), "");
    
    postprocessElementStates();
  }
}
