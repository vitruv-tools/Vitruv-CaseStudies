package mir.routines.umlToJavaMethod;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypesFactory;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AdaptJavaParametertoDirectionChangeRoutine extends AbstractRepairRoutineRealization {
  private AdaptJavaParametertoDirectionChangeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJParam(final Operation uOperation, final Parameter uParam, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection, final Method jMethod) {
      return uParam;
    }
    
    public EObject getCorrepondenceSourceJMethod(final Operation uOperation, final Parameter uParam, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection) {
      return uOperation;
    }
    
    public void executeAction1(final Operation uOperation, final Parameter uParam, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection, final Method jMethod, final Optional<OrdinaryParameter> jParam, final Optional<ConcreteClassifier> jCustomType, @Extension final RoutinesFacade _routinesFacade) {
      boolean _equals = Objects.equal(newDirection, ParameterDirectionKind.RETURN_LITERAL);
      if (_equals) {
        _routinesFacade.umlToJavaTypePropagation.propagateReturnParameterTypeChanged(uParam, jMethod, jCustomType.orElse(null));
      } else {
        boolean _equals_1 = Objects.equal(oldDirection, ParameterDirectionKind.RETURN_LITERAL);
        if (_equals_1) {
          jMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid());
        }
      }
    }
    
    public EObject getCorrepondenceSourceJCustomType(final Operation uOperation, final Parameter uParam, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection, final Method jMethod, final Optional<OrdinaryParameter> jParam) {
      Type _type = uParam.getType();
      return _type;
    }
    
    public void callRoutine1(final Operation uOperation, final Parameter uParam, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection, final Method jMethod, final Optional<OrdinaryParameter> jParam, final Optional<ConcreteClassifier> jCustomType, @Extension final RoutinesFacade _routinesFacade) {
      if ((Objects.equal(newDirection, ParameterDirectionKind.RETURN_LITERAL) && jParam.isPresent())) {
        EcoreUtil.remove(jParam.get());
      } else {
        if (((Objects.equal(newDirection, ParameterDirectionKind.IN_LITERAL) || Objects.equal(newDirection, ParameterDirectionKind.INOUT_LITERAL)) && (!jParam.isPresent()))) {
          _routinesFacade.createJavaParameter(uOperation, uParam);
        }
      }
    }
  }
  
  public AdaptJavaParametertoDirectionChangeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uOperation, final Parameter uParam, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.AdaptJavaParametertoDirectionChangeRoutine.ActionUserExecution(getExecutionState(), this);
    this.uOperation = uOperation;this.uParam = uParam;this.oldDirection = oldDirection;this.newDirection = newDirection;
  }
  
  private Operation uOperation;
  
  private Parameter uParam;
  
  private ParameterDirectionKind oldDirection;
  
  private ParameterDirectionKind newDirection;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdaptJavaParametertoDirectionChangeRoutine with input:");
    getLogger().debug("   uOperation: " + this.uOperation);
    getLogger().debug("   uParam: " + this.uParam);
    getLogger().debug("   oldDirection: " + this.oldDirection);
    getLogger().debug("   newDirection: " + this.newDirection);
    
    org.emftext.language.java.members.Method jMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJMethod(uOperation, uParam, oldDirection, newDirection), // correspondence source supplier
    	org.emftext.language.java.members.Method.class,
    	(org.emftext.language.java.members.Method _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jMethod == null) {
    	return false;
    }
    registerObjectUnderModification(jMethod);
    	Optional<org.emftext.language.java.parameters.OrdinaryParameter> jParam = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceJParam(uOperation, uParam, oldDirection, newDirection, jMethod), // correspondence source supplier
    		org.emftext.language.java.parameters.OrdinaryParameter.class,
    		(org.emftext.language.java.parameters.OrdinaryParameter _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(jParam.isPresent() ? jParam.get() : null);
    	Optional<org.emftext.language.java.classifiers.ConcreteClassifier> jCustomType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceJCustomType(uOperation, uParam, oldDirection, newDirection, jMethod, jParam), // correspondence source supplier
    		org.emftext.language.java.classifiers.ConcreteClassifier.class,
    		(org.emftext.language.java.classifiers.ConcreteClassifier _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(jCustomType.isPresent() ? jCustomType.get() : null);
    userExecution.executeAction1(uOperation, uParam, oldDirection, newDirection, jMethod, jParam, jCustomType, this.getRoutinesFacade());
    
    userExecution.callRoutine1(uOperation, uParam, oldDirection, newDirection, jMethod, jParam, jCustomType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
