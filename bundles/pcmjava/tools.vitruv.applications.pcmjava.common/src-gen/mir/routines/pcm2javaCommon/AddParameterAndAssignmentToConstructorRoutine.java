package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.statements.Statement;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.domains.java.util.JavaModificationUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddParameterAndAssignmentToConstructorRoutine extends AbstractRepairRoutineRealization {
  private AddParameterAndAssignmentToConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName, final OrdinaryParameter newParameter) {
      return newParameter;
    }
    
    public void update0Element(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName, final OrdinaryParameter newParameter) {
      constructor.getParameters().add(newParameter);
      final Statement asssignment = JavaModificationUtil.createAssignmentFromParameterToField(fieldToBeAssigned, newParameter);
      constructor.getStatements().add(asssignment);
    }
    
    public void updateNewParameterElement(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName, final OrdinaryParameter newParameter) {
      newParameter.setName(parameterName);
      newParameter.setTypeReference(EcoreUtil.<NamespaceClassifierReference>copy(typeReference));
    }
    
    public EObject getElement2(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName, final OrdinaryParameter newParameter) {
      return parameterCorrespondenceSource;
    }
    
    public EObject getElement3(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName, final OrdinaryParameter newParameter) {
      return constructor;
    }
  }
  
  public AddParameterAndAssignmentToConstructorRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.AddParameterAndAssignmentToConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.parameterCorrespondenceSource = parameterCorrespondenceSource;this.constructor = constructor;this.typeReference = typeReference;this.fieldToBeAssigned = fieldToBeAssigned;this.parameterName = parameterName;
  }
  
  private NamedElement parameterCorrespondenceSource;
  
  private Constructor constructor;
  
  private NamespaceClassifierReference typeReference;
  
  private Field fieldToBeAssigned;
  
  private String parameterName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddParameterAndAssignmentToConstructorRoutine with input:");
    getLogger().debug("   parameterCorrespondenceSource: " + this.parameterCorrespondenceSource);
    getLogger().debug("   constructor: " + this.constructor);
    getLogger().debug("   typeReference: " + this.typeReference);
    getLogger().debug("   fieldToBeAssigned: " + this.fieldToBeAssigned);
    getLogger().debug("   parameterName: " + this.parameterName);
    
    org.emftext.language.java.parameters.OrdinaryParameter newParameter = org.emftext.language.java.parameters.impl.ParametersFactoryImpl.eINSTANCE.createOrdinaryParameter();
    notifyObjectCreated(newParameter);
    userExecution.updateNewParameterElement(parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName, newParameter);
    
    addCorrespondenceBetween(userExecution.getElement1(parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName, newParameter), userExecution.getElement2(parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName, newParameter), "");
    
    // val updatedElement userExecution.getElement3(parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName, newParameter);
    userExecution.update0Element(parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName, newParameter);
    
    postprocessElements();
    
    return true;
  }
}
