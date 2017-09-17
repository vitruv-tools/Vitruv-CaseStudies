package mir.routines.umlToJavaMethod;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parametrizable;
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
    
    public EObject getElement1(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<org.emftext.language.java.classifiers.Class> customTypeClass, final OrdinaryParameter javaParam) {
      return javaParam;
    }
    
    public void update0Element(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<org.emftext.language.java.classifiers.Class> customTypeClass, final OrdinaryParameter javaParam) {
      EList<org.emftext.language.java.parameters.Parameter> _parameters = javaMethod.getParameters();
      _parameters.add(javaParam);
    }
    
    public EObject getCorrepondenceSourceCustomTypeClass(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod) {
      Type _type = umlParam.getType();
      return _type;
    }
    
    public EObject getElement2(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<org.emftext.language.java.classifiers.Class> customTypeClass, final OrdinaryParameter javaParam) {
      return umlParam;
    }
    
    public EObject getElement3(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<org.emftext.language.java.classifiers.Class> customTypeClass, final OrdinaryParameter javaParam) {
      return javaMethod;
    }
    
    public EObject getCorrepondenceSourceJavaMethod(final Operation uMeth, final Parameter umlParam) {
      return uMeth;
    }
    
    public void updateJavaParamElement(final Operation uMeth, final Parameter umlParam, final Parametrizable javaMethod, final Optional<org.emftext.language.java.classifiers.Class> customTypeClass, final OrdinaryParameter javaParam) {
      javaParam.setName(umlParam.getName());
      javaParam.setTypeReference(UmlToJavaHelper.createTypeReferenceAndUpdateImport(umlParam.getType(), customTypeClass, javaMethod.getContainingCompilationUnit(), this.userInteracting));
    }
  }
  
  public CreateJavaParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uMeth, final Parameter umlParam) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.CreateJavaParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
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
    	Optional<org.emftext.language.java.classifiers.Class> customTypeClass = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceCustomTypeClass(uMeth, umlParam, javaMethod), // correspondence source supplier
    		org.emftext.language.java.classifiers.Class.class,
    		(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(customTypeClass.isPresent() ? customTypeClass.get() : null);
    org.emftext.language.java.parameters.OrdinaryParameter javaParam = org.emftext.language.java.parameters.impl.ParametersFactoryImpl.eINSTANCE.createOrdinaryParameter();
    notifyObjectCreated(javaParam);
    userExecution.updateJavaParamElement(uMeth, umlParam, javaMethod, customTypeClass, javaParam);
    
    addCorrespondenceBetween(userExecution.getElement1(uMeth, umlParam, javaMethod, customTypeClass, javaParam), userExecution.getElement2(uMeth, umlParam, javaMethod, customTypeClass, javaParam), "");
    
    // val updatedElement userExecution.getElement3(uMeth, umlParam, javaMethod, customTypeClass, javaParam);
    userExecution.update0Element(uMeth, umlParam, javaMethod, customTypeClass, javaParam);
    
    postprocessElements();
    
    return true;
  }
}
