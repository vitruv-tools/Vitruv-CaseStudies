package mir.routines.pcm2depInjectJava;

import java.io.IOException;
import mir.routines.pcm2depInjectJava.RoutinesFacade;
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
public class CreateInnerDeclarationImplementationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateInnerDeclarationImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceNonPrimitiveInnerDataTypeClass(final InnerDeclaration innerDeclaration) {
      DataType _datatype_InnerDeclaration = innerDeclaration.getDatatype_InnerDeclaration();
      return _datatype_InnerDeclaration;
    }
    
    public void callRoutine1(final InnerDeclaration innerDeclaration, final org.emftext.language.java.classifiers.Class nonPrimitiveInnerDataTypeClass, @Extension final RoutinesFacade _routinesFacade) {
      final TypeReference innerDataTypeReference = Pcm2JavaHelper.createTypeReference(innerDeclaration.getDatatype_InnerDeclaration(), nonPrimitiveInnerDataTypeClass);
      _routinesFacade.addInnerDeclarationToCompositeDataType(innerDeclaration.getCompositeDataType_InnerDeclaration(), innerDeclaration, innerDataTypeReference);
    }
  }
  
  public CreateInnerDeclarationImplementationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration innerDeclaration) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2depInjectJava.CreateInnerDeclarationImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(getExecutionState(), this);
    this.innerDeclaration = innerDeclaration;
  }
  
  private InnerDeclaration innerDeclaration;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInnerDeclarationImplementationRoutine with input:");
    getLogger().debug("   innerDeclaration: " + this.innerDeclaration);
    
    org.emftext.language.java.classifiers.Class nonPrimitiveInnerDataTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceNonPrimitiveInnerDataTypeClass(innerDeclaration), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(nonPrimitiveInnerDataTypeClass);
    userExecution.callRoutine1(innerDeclaration, nonPrimitiveInnerDataTypeClass, actionsFacade);
    
    postprocessElements();
  }
}
