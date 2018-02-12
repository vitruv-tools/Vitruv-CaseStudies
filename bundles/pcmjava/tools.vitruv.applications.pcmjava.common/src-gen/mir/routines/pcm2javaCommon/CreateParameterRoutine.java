package mir.routines.pcm2javaCommon;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateParameterRoutine extends AbstractRepairRoutineRealization {
  private CreateParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter parameter, final InterfaceMethod interfaceMethod, final Optional<org.emftext.language.java.classifiers.Class> javaParameterTypeClass, final OrdinaryParameter javaParameter) {
      return javaParameter;
    }
    
    public void update0Element(final Parameter parameter, final InterfaceMethod interfaceMethod, final Optional<org.emftext.language.java.classifiers.Class> javaParameterTypeClass, final OrdinaryParameter javaParameter) {
      EList<org.emftext.language.java.parameters.Parameter> _parameters = interfaceMethod.getParameters();
      _parameters.add(javaParameter);
    }
    
    public EObject getCorrepondenceSourceInterfaceMethod(final Parameter parameter) {
      OperationSignature _operationSignature__Parameter = parameter.getOperationSignature__Parameter();
      return _operationSignature__Parameter;
    }
    
    public EObject getCorrepondenceSource1(final Parameter parameter, final InterfaceMethod interfaceMethod) {
      return parameter;
    }
    
    public EObject getElement2(final Parameter parameter, final InterfaceMethod interfaceMethod, final Optional<org.emftext.language.java.classifiers.Class> javaParameterTypeClass, final OrdinaryParameter javaParameter) {
      return parameter;
    }
    
    public EObject getElement3(final Parameter parameter, final InterfaceMethod interfaceMethod, final Optional<org.emftext.language.java.classifiers.Class> javaParameterTypeClass, final OrdinaryParameter javaParameter) {
      return interfaceMethod;
    }
    
    public void updateJavaParameterElement(final Parameter parameter, final InterfaceMethod interfaceMethod, final Optional<org.emftext.language.java.classifiers.Class> javaParameterTypeClass, final OrdinaryParameter javaParameter) {
      javaParameter.setName(parameter.getParameterName());
      final TypeReference parameterTypeReference = Pcm2JavaHelper.createTypeReference(parameter.getDataType__Parameter(), javaParameterTypeClass);
      javaParameter.setTypeReference(parameterTypeReference);
    }
    
    public EObject getCorrepondenceSourceJavaParameterTypeClass(final Parameter parameter, final InterfaceMethod interfaceMethod) {
      DataType _dataType__Parameter = parameter.getDataType__Parameter();
      return _dataType__Parameter;
    }
  }
  
  public CreateParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter parameter) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.CreateParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.parameter = parameter;
  }
  
  private Parameter parameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateParameterRoutine with input:");
    getLogger().debug("   parameter: " + this.parameter);
    
    org.emftext.language.java.members.InterfaceMethod interfaceMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceMethod(parameter), // correspondence source supplier
    	org.emftext.language.java.members.InterfaceMethod.class,
    	(org.emftext.language.java.members.InterfaceMethod _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (interfaceMethod == null) {
    	return false;
    }
    registerObjectUnderModification(interfaceMethod);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(parameter, interfaceMethod), // correspondence source supplier
    	org.emftext.language.java.parameters.OrdinaryParameter.class,
    	(org.emftext.language.java.parameters.OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    	Optional<org.emftext.language.java.classifiers.Class> javaParameterTypeClass = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceJavaParameterTypeClass(parameter, interfaceMethod), // correspondence source supplier
    		org.emftext.language.java.classifiers.Class.class,
    		(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(javaParameterTypeClass.isPresent() ? javaParameterTypeClass.get() : null);
    org.emftext.language.java.parameters.OrdinaryParameter javaParameter = org.emftext.language.java.parameters.impl.ParametersFactoryImpl.eINSTANCE.createOrdinaryParameter();
    notifyObjectCreated(javaParameter);
    userExecution.updateJavaParameterElement(parameter, interfaceMethod, javaParameterTypeClass, javaParameter);
    
    addCorrespondenceBetween(userExecution.getElement1(parameter, interfaceMethod, javaParameterTypeClass, javaParameter), userExecution.getElement2(parameter, interfaceMethod, javaParameterTypeClass, javaParameter), "");
    
    // val updatedElement userExecution.getElement3(parameter, interfaceMethod, javaParameterTypeClass, javaParameter);
    userExecution.update0Element(parameter, interfaceMethod, javaParameterTypeClass, javaParameter);
    
    postprocessElements();
    
    return true;
  }
}
