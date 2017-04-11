package mir.routines.umlToJava;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaCollectionClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaCollectionClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType dType, final Property innerType, final org.emftext.language.java.classifiers.Class jCollectionClass, final org.emftext.language.java.classifiers.Class jInnerType) {
      return jCollectionClass;
    }
    
    public void update0Element(final DataType dType, final Property innerType, final org.emftext.language.java.classifiers.Class jCollectionClass, final org.emftext.language.java.classifiers.Class jInnerType) {
      boolean _notEquals = (!Objects.equal(jInnerType, null));
      if (_notEquals) {
      } else {
        Type _type = innerType.getType();
        if ((_type instanceof PrimitiveType)) {
        } else {
        }
      }
    }
    
    public EObject getCorrepondenceSourceJCollectionClass(final DataType dType, final Property innerType) {
      return dType;
    }
    
    public EObject getCorrepondenceSourceJInnerType(final DataType dType, final Property innerType, final org.emftext.language.java.classifiers.Class jCollectionClass) {
      Type _type = innerType.getType();
      return _type;
    }
  }
  
  public CreateJavaCollectionClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType dType, final Property innerType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaCollectionClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.dType = dType;this.innerType = innerType;
  }
  
  private DataType dType;
  
  private Property innerType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaCollectionClassRoutine with input:");
    getLogger().debug("   DataType: " + this.dType);
    getLogger().debug("   Property: " + this.innerType);
    
    org.emftext.language.java.classifiers.Class jCollectionClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJCollectionClass(dType, innerType), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (jCollectionClass == null) {
    	return;
    }
    registerObjectUnderModification(jCollectionClass);
    org.emftext.language.java.classifiers.Class jInnerType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJInnerType(dType, innerType, jCollectionClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(jInnerType);
    // val updatedElement userExecution.getElement1(dType, innerType, jCollectionClass, jInnerType);
    userExecution.update0Element(dType, innerType, jCollectionClass, jInnerType);
    
    postprocessElements();
  }
}
