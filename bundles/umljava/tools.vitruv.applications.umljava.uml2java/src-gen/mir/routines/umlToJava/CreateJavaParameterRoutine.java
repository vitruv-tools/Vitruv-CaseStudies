package mir.routines.umlToJava;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.impl.ParametersFactoryImpl;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCustomType(final Operation uMeth, final Parameter umlParam, final Method javaMethod) {
      Type _type = umlParam.getType();
      return _type;
    }
    
    public EObject getElement1(final Operation uMeth, final Parameter umlParam, final Method javaMethod, final org.emftext.language.java.classifiers.Class customType, final OrdinaryParameter javaParam) {
      return javaParam;
    }
    
    public void update0Element(final Operation uMeth, final Parameter umlParam, final Method javaMethod, final org.emftext.language.java.classifiers.Class customType, final OrdinaryParameter javaParam) {
      EList<org.emftext.language.java.parameters.Parameter> _parameters = javaMethod.getParameters();
      _parameters.add(javaParam);
    }
    
    public EObject getElement2(final Operation uMeth, final Parameter umlParam, final Method javaMethod, final org.emftext.language.java.classifiers.Class customType, final OrdinaryParameter javaParam) {
      return umlParam;
    }
    
    public EObject getElement3(final Operation uMeth, final Parameter umlParam, final Method javaMethod, final org.emftext.language.java.classifiers.Class customType, final OrdinaryParameter javaParam) {
      return javaMethod;
    }
    
    public EObject getCorrepondenceSourceJavaMethod(final Operation uMeth, final Parameter umlParam) {
      return uMeth;
    }
    
    public void updateJavaParamElement(final Operation uMeth, final Parameter umlParam, final Method javaMethod, final org.emftext.language.java.classifiers.Class customType, final OrdinaryParameter javaParam) {
      String _name = umlParam.getName();
      boolean _notEquals = (!Objects.equal(_name, null));
      if (_notEquals) {
        String _name_1 = umlParam.getName();
        javaParam.setName(_name_1);
      } else {
        javaParam.setName("Defaultname");
      }
      Type _type = umlParam.getType();
      TypeReference _createTypeReference = UmlToJavaHelper.createTypeReference(_type, customType);
      javaParam.setTypeReference(_createTypeReference);
    }
  }
  
  public CreateJavaParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uMeth, final Parameter umlParam) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uMeth = uMeth;this.umlParam = umlParam;
  }
  
  private Operation uMeth;
  
  private Parameter umlParam;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaParameterRoutine with input:");
    getLogger().debug("   Operation: " + this.uMeth);
    getLogger().debug("   Parameter: " + this.umlParam);
    
    Method javaMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaMethod(uMeth, umlParam), // correspondence source supplier
    	Method.class,
    	(Method _element) -> true, // correspondence precondition checker
    	null);
    if (javaMethod == null) {
    	return;
    }
    initializeRetrieveElementState(javaMethod);
    org.emftext.language.java.classifiers.Class customType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomType(uMeth, umlParam, javaMethod), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(customType);
    OrdinaryParameter javaParam = ParametersFactoryImpl.eINSTANCE.createOrdinaryParameter();
    initializeCreateElementState(javaParam);
    userExecution.updateJavaParamElement(uMeth, umlParam, javaMethod, customType, javaParam);
    
    addCorrespondenceBetween(userExecution.getElement1(uMeth, umlParam, javaMethod, customType, javaParam), userExecution.getElement2(uMeth, umlParam, javaMethod, customType, javaParam), "");
    
    // val updatedElement userExecution.getElement3(uMeth, umlParam, javaMethod, customType, javaParam);
    userExecution.update0Element(uMeth, umlParam, javaMethod, customType, javaParam);
    
    postprocessElementStates();
  }
}
