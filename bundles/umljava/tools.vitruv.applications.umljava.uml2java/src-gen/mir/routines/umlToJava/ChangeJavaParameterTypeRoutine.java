package mir.routines.umlToJava;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeJavaParameterTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeJavaParameterTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJParam(final Parameter uParam, final Type uType) {
      return uParam;
    }
    
    public EObject getElement1(final Parameter uParam, final Type uType, final OrdinaryParameter jParam, final Method jMeth, final org.emftext.language.java.classifiers.Class customClass) {
      return jParam;
    }
    
    public void update0Element(final Parameter uParam, final Type uType, final OrdinaryParameter jParam, final Method jMeth, final org.emftext.language.java.classifiers.Class customClass) {
      final TypeReference jType = UmlToJavaHelper.createTypeReference(uType, customClass);
      boolean _equals = uParam.getDirection().equals(ParameterDirectionKind.RETURN_LITERAL);
      if (_equals) {
        jMeth.setTypeReference(jType);
        boolean _notEquals = (!Objects.equal(jParam, null));
        if (_notEquals) {
        }
      } else {
        jParam.setTypeReference(jType);
      }
    }
    
    public EObject getCorrepondenceSourceCustomClass(final Parameter uParam, final Type uType, final OrdinaryParameter jParam, final Method jMeth) {
      return uType;
    }
    
    public EObject getCorrepondenceSourceJMeth(final Parameter uParam, final Type uType, final OrdinaryParameter jParam) {
      Operation _operation = uParam.getOperation();
      return _operation;
    }
  }
  
  public ChangeJavaParameterTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter uParam, final Type uType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.ChangeJavaParameterTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uParam = uParam;this.uType = uType;
  }
  
  private Parameter uParam;
  
  private Type uType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeJavaParameterTypeRoutine with input:");
    getLogger().debug("   Parameter: " + this.uParam);
    getLogger().debug("   Type: " + this.uType);
    
    OrdinaryParameter jParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJParam(uParam, uType), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(jParam);
    Method jMeth = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJMeth(uParam, uType, jParam), // correspondence source supplier
    	Method.class,
    	(Method _element) -> true, // correspondence precondition checker
    	null);
    if (jMeth == null) {
    	return;
    }
    registerObjectUnderModification(jMeth);
    org.emftext.language.java.classifiers.Class customClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomClass(uParam, uType, jParam, jMeth), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customClass);
    // val updatedElement userExecution.getElement1(uParam, uType, jParam, jMeth, customClass);
    userExecution.update0Element(uParam, uType, jParam, jMeth, customClass);
    
    postprocessElements();
  }
}
