package mir.routines.umlToJavaMethod;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.parameters.OrdinaryParameter;
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
    
    public EObject getElement1(final Parameter uParam, final Type uType, final OrdinaryParameter jParam, final Optional<org.emftext.language.java.classifiers.Class> customClass) {
      return jParam;
    }
    
    public void update0Element(final Parameter uParam, final Type uType, final OrdinaryParameter jParam, final Optional<org.emftext.language.java.classifiers.Class> customClass) {
      jParam.setTypeReference(UmlToJavaHelper.createTypeReferenceAndUpdateImport(uType, customClass, jParam.getContainingCompilationUnit(), this.userInteracting));
    }
    
    public EObject getCorrepondenceSourceCustomClass(final Parameter uParam, final Type uType, final OrdinaryParameter jParam) {
      return uType;
    }
  }
  
  public ChangeJavaParameterTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter uParam, final Type uType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.ChangeJavaParameterTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uParam = uParam;this.uType = uType;
  }
  
  private Parameter uParam;
  
  private Type uType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeJavaParameterTypeRoutine with input:");
    getLogger().debug("   uParam: " + this.uParam);
    getLogger().debug("   uType: " + this.uType);
    
    org.emftext.language.java.parameters.OrdinaryParameter jParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJParam(uParam, uType), // correspondence source supplier
    	org.emftext.language.java.parameters.OrdinaryParameter.class,
    	(org.emftext.language.java.parameters.OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jParam == null) {
    	return false;
    }
    registerObjectUnderModification(jParam);
    	Optional<org.emftext.language.java.classifiers.Class> customClass = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceCustomClass(uParam, uType, jParam), // correspondence source supplier
    		org.emftext.language.java.classifiers.Class.class,
    		(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(customClass.isPresent() ? customClass.get() : null);
    // val updatedElement userExecution.getElement1(uParam, uType, jParam, customClass);
    userExecution.update0Element(uParam, uType, jParam, customClass);
    
    postprocessElements();
    
    return true;
  }
}
