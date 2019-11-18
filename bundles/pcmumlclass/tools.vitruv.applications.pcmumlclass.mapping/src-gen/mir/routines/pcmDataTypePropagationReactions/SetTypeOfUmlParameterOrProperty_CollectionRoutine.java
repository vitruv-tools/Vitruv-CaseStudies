package mir.routines.pcmDataTypePropagationReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmDataTypePropagationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetTypeOfUmlParameterOrProperty_CollectionRoutine extends AbstractRepairRoutineRealization {
  private SetTypeOfUmlParameterOrProperty_CollectionRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag, final Optional<Type> umlPrimitiveType, final Optional<Type> umlCompositeType) {
      return umlElement;
    }
    
    public void update0Element(final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag, final Optional<Type> umlPrimitiveType, final Optional<Type> umlCompositeType) {
      umlElement.setType(umlPrimitiveType.orElse(umlCompositeType.orElse(null)));
    }
    
    public EObject getCorrepondenceSourceUmlPrimitiveType(final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      DataType _innerType_CollectionDataType = pcmType.getInnerType_CollectionDataType();
      return _innerType_CollectionDataType;
    }
    
    public String getRetrieveTag1(final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
      return TagLiterals.DATATYPE__TYPE;
    }
    
    public String getRetrieveTag2(final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag, final Optional<Type> umlPrimitiveType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getElement2(final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag, final Optional<Type> umlPrimitiveType, final Optional<Type> umlCompositeType) {
      return umlMultiplicity;
    }
    
    public void callRoutine2(final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag, final Optional<Type> umlPrimitiveType, final Optional<Type> umlCompositeType, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addCollectionDataTypeCorrespondence(pcmType, umlElement, tag);
    }
    
    public EObject getCorrepondenceSourceUmlCompositeType(final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag, final Optional<Type> umlPrimitiveType) {
      DataType _innerType_CollectionDataType = pcmType.getInnerType_CollectionDataType();
      return _innerType_CollectionDataType;
    }
    
    public void update1Element(final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag, final Optional<Type> umlPrimitiveType, final Optional<Type> umlCompositeType) {
      umlMultiplicity.setLower(0);
      umlMultiplicity.setUpper(LiteralUnlimitedNatural.UNLIMITED);
    }
    
    public void callRoutine1(final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag, final Optional<Type> umlPrimitiveType, final Optional<Type> umlCompositeType, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.removeOldCollectionDataTypeCorrespondence(umlElement, tag);
    }
  }
  
  public SetTypeOfUmlParameterOrProperty_CollectionRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmDataTypePropagationReactions.SetTypeOfUmlParameterOrProperty_CollectionRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.umlElement = umlElement;this.umlMultiplicity = umlMultiplicity;this.tag = tag;
  }
  
  private CollectionDataType pcmType;
  
  private TypedElement umlElement;
  
  private MultiplicityElement umlMultiplicity;
  
  private String tag;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetTypeOfUmlParameterOrProperty_CollectionRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   umlElement: " + this.umlElement);
    getLogger().debug("   umlMultiplicity: " + this.umlMultiplicity);
    getLogger().debug("   tag: " + this.tag);
    
    	Optional<org.eclipse.uml2.uml.Type> umlPrimitiveType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlPrimitiveType(pcmType, umlElement, umlMultiplicity, tag), // correspondence source supplier
    		org.eclipse.uml2.uml.Type.class,
    		(org.eclipse.uml2.uml.Type _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(pcmType, umlElement, umlMultiplicity, tag), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlPrimitiveType.isPresent() ? umlPrimitiveType.get() : null);
    	Optional<org.eclipse.uml2.uml.Type> umlCompositeType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlCompositeType(pcmType, umlElement, umlMultiplicity, tag, umlPrimitiveType), // correspondence source supplier
    		org.eclipse.uml2.uml.Type.class,
    		(org.eclipse.uml2.uml.Type _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmType, umlElement, umlMultiplicity, tag, umlPrimitiveType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlCompositeType.isPresent() ? umlCompositeType.get() : null);
    // val updatedElement userExecution.getElement1(pcmType, umlElement, umlMultiplicity, tag, umlPrimitiveType, umlCompositeType);
    userExecution.update0Element(pcmType, umlElement, umlMultiplicity, tag, umlPrimitiveType, umlCompositeType);
    
    // val updatedElement userExecution.getElement2(pcmType, umlElement, umlMultiplicity, tag, umlPrimitiveType, umlCompositeType);
    userExecution.update1Element(pcmType, umlElement, umlMultiplicity, tag, umlPrimitiveType, umlCompositeType);
    
    userExecution.callRoutine1(pcmType, umlElement, umlMultiplicity, tag, umlPrimitiveType, umlCompositeType, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmType, umlElement, umlMultiplicity, tag, umlPrimitiveType, umlCompositeType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
