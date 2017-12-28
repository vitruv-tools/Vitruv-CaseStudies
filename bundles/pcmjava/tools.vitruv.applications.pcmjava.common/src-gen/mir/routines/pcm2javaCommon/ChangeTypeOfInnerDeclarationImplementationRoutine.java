package mir.routines.pcm2javaCommon;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfInnerDeclarationImplementationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeTypeOfInnerDeclarationImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceNewJavaDataType(final InnerDeclaration innerDeclaration) {
      DataType _datatype_InnerDeclaration = innerDeclaration.getDatatype_InnerDeclaration();
      return _datatype_InnerDeclaration;
    }
    
    public void callRoutine1(final InnerDeclaration innerDeclaration, final Optional<org.emftext.language.java.classifiers.Class> newJavaDataType, @Extension final RoutinesFacade _routinesFacade) {
      final TypeReference newDataTypeReference = Pcm2JavaHelper.createTypeReference(innerDeclaration.getDatatype_InnerDeclaration(), newJavaDataType);
      _routinesFacade.changeInnerDeclarationType(innerDeclaration, newDataTypeReference);
    }
  }
  
  public ChangeTypeOfInnerDeclarationImplementationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.ChangeTypeOfInnerDeclarationImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(getExecutionState(), this);
    this.innerDeclaration = innerDeclaration;
  }
  
  private InnerDeclaration innerDeclaration;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfInnerDeclarationImplementationRoutine with input:");
    getLogger().debug("   innerDeclaration: " + this.innerDeclaration);
    
    	Optional<org.emftext.language.java.classifiers.Class> newJavaDataType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceNewJavaDataType(innerDeclaration), // correspondence source supplier
    		org.emftext.language.java.classifiers.Class.class,
    		(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(newJavaDataType.isPresent() ? newJavaDataType.get() : null);
    userExecution.callRoutine1(innerDeclaration, newJavaDataType, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
