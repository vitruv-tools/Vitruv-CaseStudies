package mir.routines.pcmCollectionDataTypeReactions;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import mir.routines.pcmCollectionDataTypeReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * *
 * nChange the type of the corresponding uml::Parameter or uml::Property.
 * n
 * nAssumes that the innerType of the CollectionDataType is not set to another CollectionDataType.
 *  
 */
@SuppressWarnings("all")
public class ChangeTypeOfCorrespondingPropertyOrParameterRoutine extends AbstractRepairRoutineRealization {
  private ChangeTypeOfCorrespondingPropertyOrParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlParameterList(final CollectionDataType pcmCollection, final DataType newInnerType) {
      return pcmCollection;
    }
    
    public String getRetrieveTag4(final CollectionDataType pcmCollection, final DataType newInnerType, final List<Parameter> umlParameterList, final List<Property> umlPropertyList, final Optional<Type> umlPrimitiveType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public void executeAction1(final CollectionDataType pcmCollection, final DataType newInnerType, final List<Parameter> umlParameterList, final List<Property> umlPropertyList, final Optional<Type> umlPrimitiveType, final Optional<Type> umlCompositeType, @Extension final RoutinesFacade _routinesFacade) {
      final Type umlType = umlPrimitiveType.orElse(umlCompositeType.orElse(null));
      for (final Parameter umlParameter : umlParameterList) {
        umlParameter.setType(umlType);
      }
      for (final Property umlProperty : umlPropertyList) {
        umlProperty.setType(umlType);
      }
    }
    
    public String getRetrieveTag1(final CollectionDataType pcmCollection, final DataType newInnerType) {
      return TagLiterals.COLLECTION_DATATYPE__PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlPrimitiveType(final CollectionDataType pcmCollection, final DataType newInnerType, final List<Parameter> umlParameterList, final List<Property> umlPropertyList) {
      return newInnerType;
    }
    
    public EObject getCorrepondenceSourceUmlPropertyList(final CollectionDataType pcmCollection, final DataType newInnerType, final List<Parameter> umlParameterList) {
      return pcmCollection;
    }
    
    public String getRetrieveTag2(final CollectionDataType pcmCollection, final DataType newInnerType, final List<Parameter> umlParameterList) {
      return TagLiterals.COLLECTION_DATATYPE__PROPERTY;
    }
    
    public String getRetrieveTag3(final CollectionDataType pcmCollection, final DataType newInnerType, final List<Parameter> umlParameterList, final List<Property> umlPropertyList) {
      return TagLiterals.DATATYPE__TYPE;
    }
    
    public EObject getCorrepondenceSourceUmlCompositeType(final CollectionDataType pcmCollection, final DataType newInnerType, final List<Parameter> umlParameterList, final List<Property> umlPropertyList, final Optional<Type> umlPrimitiveType) {
      return newInnerType;
    }
  }
  
  public ChangeTypeOfCorrespondingPropertyOrParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType pcmCollection, final DataType newInnerType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmCollectionDataTypeReactions.ChangeTypeOfCorrespondingPropertyOrParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmCollection = pcmCollection;this.newInnerType = newInnerType;
  }
  
  private CollectionDataType pcmCollection;
  
  private DataType newInnerType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingPropertyOrParameterRoutine with input:");
    getLogger().debug("   pcmCollection: " + this.pcmCollection);
    getLogger().debug("   newInnerType: " + this.newInnerType);
    
    List<org.eclipse.uml2.uml.Parameter> umlParameterList = getCorrespondingElements(
    	userExecution.getCorrepondenceSourceUmlParameterList(pcmCollection, newInnerType), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmCollection, newInnerType)
    );
    for (EObject _element : umlParameterList) {	
    	registerObjectUnderModification(_element);
    }
    List<org.eclipse.uml2.uml.Property> umlPropertyList = getCorrespondingElements(
    	userExecution.getCorrepondenceSourceUmlPropertyList(pcmCollection, newInnerType, umlParameterList), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmCollection, newInnerType, umlParameterList)
    );
    for (EObject _element : umlPropertyList) {	
    	registerObjectUnderModification(_element);
    }
    	Optional<org.eclipse.uml2.uml.Type> umlPrimitiveType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlPrimitiveType(pcmCollection, newInnerType, umlParameterList, umlPropertyList), // correspondence source supplier
    		org.eclipse.uml2.uml.Type.class,
    		(org.eclipse.uml2.uml.Type _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmCollection, newInnerType, umlParameterList, umlPropertyList), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlPrimitiveType.isPresent() ? umlPrimitiveType.get() : null);
    	Optional<org.eclipse.uml2.uml.Type> umlCompositeType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlCompositeType(pcmCollection, newInnerType, umlParameterList, umlPropertyList, umlPrimitiveType), // correspondence source supplier
    		org.eclipse.uml2.uml.Type.class,
    		(org.eclipse.uml2.uml.Type _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(pcmCollection, newInnerType, umlParameterList, umlPropertyList, umlPrimitiveType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlCompositeType.isPresent() ? umlCompositeType.get() : null);
    userExecution.executeAction1(pcmCollection, newInnerType, umlParameterList, umlPropertyList, umlPrimitiveType, umlCompositeType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
