package mir.routines.umlToJavaMethod;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parametrizable;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaParameterRoutine extends AbstractRepairRoutineRealization {
  private CreateJavaParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCustomType(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod) {
      Type _type = umlParam.getType();
      return _type;
    }
    
    public EObject getElement1(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<ConcreteClassifier> customType, final OrdinaryParameter javaParam) {
      return javaMethod;
    }
    
    public void update0Element(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<ConcreteClassifier> customType, final OrdinaryParameter javaParam) {
      EList<org.emftext.language.java.parameters.Parameter> _parameters = javaMethod.getParameters();
      _parameters.add(javaParam);
    }
    
    public EObject getCorrepondenceSource1(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<ConcreteClassifier> customType) {
      return umlParam;
    }
    
    public EObject getElement2(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<ConcreteClassifier> customType, final OrdinaryParameter javaParam) {
      return javaParam;
    }
    
    public EObject getElement3(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<ConcreteClassifier> customType, final OrdinaryParameter javaParam) {
      return umlParam;
    }
    
    public EObject getCorrepondenceSourceJavaMethod(final Operation uMeth, final Parameter umlParam) {
      return uMeth;
    }
    
    public void updateJavaParamElement(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<ConcreteClassifier> customType, final OrdinaryParameter javaParam) {
      javaParam.setName(umlParam.getName());
    }
    
    public void callRoutine1(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<ConcreteClassifier> customType, final OrdinaryParameter javaParam, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeJavaParameterType(umlParam, umlParam.getType());
    }
  }
  
  public CreateJavaParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uMeth, final Parameter umlParam) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.CreateJavaParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.uMeth = uMeth;this.umlParam = umlParam;
  }
  
  private Operation uMeth;
  
  private Parameter umlParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaParameterRoutine with input:");
    getLogger().debug("   uMeth: " + this.uMeth);
    getLogger().debug("   umlParam: " + this.umlParam);
    
    org.emftext.language.java.parameters.Parametrizable javaMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaMethod(uMeth, umlParam), // correspondence source supplier
    	org.emftext.language.java.parameters.Parametrizable.class,
    	(org.emftext.language.java.parameters.Parametrizable _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaMethod == null) {
    	return false;
    }
    registerObjectUnderModification(javaMethod);
    	Optional<org.emftext.language.java.classifiers.ConcreteClassifier> customType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceCustomType(uMeth, umlParam, javaMethod), // correspondence source supplier
    		org.emftext.language.java.classifiers.ConcreteClassifier.class,
    		(org.emftext.language.java.classifiers.ConcreteClassifier _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(customType.isPresent() ? customType.get() : null);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(uMeth, umlParam, javaMethod, customType), // correspondence source supplier
    	org.emftext.language.java.parameters.OrdinaryParameter.class,
    	(org.emftext.language.java.parameters.OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    org.emftext.language.java.parameters.OrdinaryParameter javaParam = org.emftext.language.java.parameters.impl.ParametersFactoryImpl.eINSTANCE.createOrdinaryParameter();
    notifyObjectCreated(javaParam);
    userExecution.updateJavaParamElement(uMeth, umlParam, javaMethod, customType, javaParam);
    
    // val updatedElement userExecution.getElement1(uMeth, umlParam, javaMethod, customType, javaParam);
    userExecution.update0Element(uMeth, umlParam, javaMethod, customType, javaParam);
    
    addCorrespondenceBetween(userExecution.getElement2(uMeth, umlParam, javaMethod, customType, javaParam), userExecution.getElement3(uMeth, umlParam, javaMethod, customType, javaParam), "");
    
    userExecution.callRoutine1(uMeth, umlParam, javaMethod, customType, javaParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
