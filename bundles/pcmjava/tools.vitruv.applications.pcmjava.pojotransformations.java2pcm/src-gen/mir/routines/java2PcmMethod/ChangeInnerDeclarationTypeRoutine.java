package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInnerDeclarationTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeInnerDeclarationTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceInnerDeclaration(final TypeReference typeReference, final Field javaField) {
      return javaField;
    }
    
    public EObject getElement1(final TypeReference typeReference, final Field javaField, final InnerDeclaration innerDeclaration) {
      return innerDeclaration;
    }
    
    public void update0Element(final TypeReference typeReference, final Field javaField, final InnerDeclaration innerDeclaration) {
      innerDeclaration.setDatatype_InnerDeclaration(TypeReferenceCorrespondenceHelper.getDataTypeFromTypeReference(typeReference, this.correspondenceModel, 
        this.userInteracting, null));
    }
  }
  
  public ChangeInnerDeclarationTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final TypeReference typeReference, final Field javaField) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.ChangeInnerDeclarationTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.typeReference = typeReference;this.javaField = javaField;
  }
  
  private TypeReference typeReference;
  
  private Field javaField;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInnerDeclarationTypeRoutine with input:");
    getLogger().debug("   typeReference: " + this.typeReference);
    getLogger().debug("   javaField: " + this.javaField);
    
    org.palladiosimulator.pcm.repository.InnerDeclaration innerDeclaration = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInnerDeclaration(typeReference, javaField), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.InnerDeclaration.class,
    	(org.palladiosimulator.pcm.repository.InnerDeclaration _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (innerDeclaration == null) {
    	return false;
    }
    registerObjectUnderModification(innerDeclaration);
    // val updatedElement userExecution.getElement1(typeReference, javaField, innerDeclaration);
    userExecution.update0Element(typeReference, javaField, innerDeclaration);
    
    postprocessElements();
    
    return true;
  }
}
