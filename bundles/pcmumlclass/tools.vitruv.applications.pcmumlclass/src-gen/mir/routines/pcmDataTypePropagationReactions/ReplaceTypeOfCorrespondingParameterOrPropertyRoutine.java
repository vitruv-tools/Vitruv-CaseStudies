package mir.routines.pcmDataTypePropagationReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmDataTypePropagationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceTypeOfCorrespondingParameterOrPropertyRoutine extends AbstractRepairRoutineRealization {
  private ReplaceTypeOfCorrespondingParameterOrPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType pcmSimpleType, final TypedElement umlElement, final Optional<Type> umlPrimitiveType, final Optional<Type> umlCompositeType) {
      return umlElement;
    }
    
    public void update0Element(final DataType pcmSimpleType, final TypedElement umlElement, final Optional<Type> umlPrimitiveType, final Optional<Type> umlCompositeType) {
      umlElement.setType(umlPrimitiveType.orElse(umlCompositeType.orElse(null)));
    }
    
    public EObject getCorrepondenceSourceUmlPrimitiveType(final DataType pcmSimpleType, final TypedElement umlElement) {
      return pcmSimpleType;
    }
    
    public String getRetrieveTag1(final DataType pcmSimpleType, final TypedElement umlElement) {
      return TagLiterals.DATATYPE__TYPE;
    }
    
    public String getRetrieveTag2(final DataType pcmSimpleType, final TypedElement umlElement, final Optional<Type> umlPrimitiveType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getCorrepondenceSourceUmlCompositeType(final DataType pcmSimpleType, final TypedElement umlElement, final Optional<Type> umlPrimitiveType) {
      return pcmSimpleType;
    }
  }
  
  public ReplaceTypeOfCorrespondingParameterOrPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType pcmSimpleType, final TypedElement umlElement) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmDataTypePropagationReactions.ReplaceTypeOfCorrespondingParameterOrPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSimpleType = pcmSimpleType;this.umlElement = umlElement;
  }
  
  private DataType pcmSimpleType;
  
  private TypedElement umlElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceTypeOfCorrespondingParameterOrPropertyRoutine with input:");
    getLogger().debug("   pcmSimpleType: " + this.pcmSimpleType);
    getLogger().debug("   umlElement: " + this.umlElement);
    
    	Optional<org.eclipse.uml2.uml.Type> umlPrimitiveType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlPrimitiveType(pcmSimpleType, umlElement), // correspondence source supplier
    		org.eclipse.uml2.uml.Type.class,
    		(org.eclipse.uml2.uml.Type _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(pcmSimpleType, umlElement), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlPrimitiveType.isPresent() ? umlPrimitiveType.get() : null);
    	Optional<org.eclipse.uml2.uml.Type> umlCompositeType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlCompositeType(pcmSimpleType, umlElement, umlPrimitiveType), // correspondence source supplier
    		org.eclipse.uml2.uml.Type.class,
    		(org.eclipse.uml2.uml.Type _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmSimpleType, umlElement, umlPrimitiveType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlCompositeType.isPresent() ? umlCompositeType.get() : null);
    // val updatedElement userExecution.getElement1(pcmSimpleType, umlElement, umlPrimitiveType, umlCompositeType);
    userExecution.update0Element(pcmSimpleType, umlElement, umlPrimitiveType, umlCompositeType);
    
    postprocessElements();
    
    return true;
  }
}
