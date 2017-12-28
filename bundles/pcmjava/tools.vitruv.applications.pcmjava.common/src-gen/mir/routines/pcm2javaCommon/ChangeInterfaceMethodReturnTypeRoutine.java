package mir.routines.pcm2javaCommon;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInterfaceMethodReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeInterfaceMethodReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceMethod interfaceMethod, final DataType returnType, final Optional<org.emftext.language.java.classifiers.Class> returnTypeClass) {
      return interfaceMethod;
    }
    
    public void update0Element(final InterfaceMethod interfaceMethod, final DataType returnType, final Optional<org.emftext.language.java.classifiers.Class> returnTypeClass) {
      final TypeReference returnTypeReference = Pcm2JavaHelper.createTypeReference(returnType, returnTypeClass);
      interfaceMethod.setTypeReference(returnTypeReference);
    }
    
    public EObject getCorrepondenceSourceReturnTypeClass(final InterfaceMethod interfaceMethod, final DataType returnType) {
      return returnType;
    }
  }
  
  public ChangeInterfaceMethodReturnTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod interfaceMethod, final DataType returnType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.ChangeInterfaceMethodReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(getExecutionState(), this);
    this.interfaceMethod = interfaceMethod;this.returnType = returnType;
  }
  
  private InterfaceMethod interfaceMethod;
  
  private DataType returnType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInterfaceMethodReturnTypeRoutine with input:");
    getLogger().debug("   interfaceMethod: " + this.interfaceMethod);
    getLogger().debug("   returnType: " + this.returnType);
    
    	Optional<org.emftext.language.java.classifiers.Class> returnTypeClass = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceReturnTypeClass(interfaceMethod, returnType), // correspondence source supplier
    		org.emftext.language.java.classifiers.Class.class,
    		(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(returnTypeClass.isPresent() ? returnTypeClass.get() : null);
    // val updatedElement userExecution.getElement1(interfaceMethod, returnType, returnTypeClass);
    userExecution.update0Element(interfaceMethod, returnType, returnTypeClass);
    
    postprocessElements();
    
    return true;
  }
}
