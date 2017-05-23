package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.applications.umljava.util.JavaUtil;
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
    
    public EObject getCorrepondenceSourceCustomType(final Field jAttr, final TypeReference jType, final Property uAttr) {
      ConcreteClassifier _classifierfromTypeRef = JavaUtil.getClassifierfromTypeRef(jType);
      return _classifierfromTypeRef;
    }
    
    public EObject getCorrepondenceSourceUAttr(final Field jAttr, final TypeReference jType) {
      return jAttr;
    }
    
    public EObject getElement1(final Field jAttr, final TypeReference jType, final Property uAttr, final org.eclipse.uml2.uml.Class customType) {
      return uAttr;
    }
    
    public void update0Element(final Field jAttr, final TypeReference jType, final Property uAttr, final org.eclipse.uml2.uml.Class customType) {
      Model _umlModel = JavaToUmlHelper.getUmlModel(this.correspondenceModel);
      Type _umlType = JavaToUmlHelper.getUmlType(jType, customType, _umlModel);
      uAttr.setType(_umlType);
    }
  }
  
  public ChangeUmlAttributeTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field jAttr, final TypeReference jType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.ChangeUmlAttributeTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jAttr = jAttr;this.jType = jType;
  }
  
  private Field jAttr;
  
  private TypeReference jType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlAttributeTypeRoutine with input:");
    getLogger().debug("   Field: " + this.jAttr);
    getLogger().debug("   TypeReference: " + this.jType);
    
    Property uAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUAttr(jAttr, jType), // correspondence source supplier
    	Property.class,
    	(Property _element) -> true, // correspondence precondition checker
    	null);
    if (uAttr == null) {
    	return;
    }
    initializeRetrieveElementState(uAttr);
    org.eclipse.uml2.uml.Class customType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomType(jAttr, jType, uAttr), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(customType);
    // val updatedElement userExecution.getElement1(jAttr, jType, uAttr, customType);
    userExecution.update0Element(jAttr, jType, uAttr, customType);
    
    postprocessElementStates();
  }
}