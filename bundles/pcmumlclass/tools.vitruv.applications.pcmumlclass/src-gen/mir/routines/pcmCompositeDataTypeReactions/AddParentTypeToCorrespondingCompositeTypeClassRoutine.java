package mir.routines.pcmCompositeDataTypeReactions;

import java.io.IOException;
import mir.routines.pcmCompositeDataTypeReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddParentTypeToCorrespondingCompositeTypeClassRoutine extends AbstractRepairRoutineRealization {
  private AddParentTypeToCorrespondingCompositeTypeClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlCompositeClass(final CompositeDataType pcmType, final CompositeDataType pcmParentType) {
      return pcmType;
    }
    
    public String getRetrieveTag1(final CompositeDataType pcmType, final CompositeDataType pcmParentType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public String getRetrieveTag2(final CompositeDataType pcmType, final CompositeDataType pcmParentType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public boolean checkMatcherPrecondition1(final CompositeDataType pcmType, final CompositeDataType pcmParentType, final org.eclipse.uml2.uml.Class umlCompositeClass, final org.eclipse.uml2.uml.Class umlParentCompositeClass) {
      final Function1<Generalization, Boolean> _function = (Generalization it) -> {
        Classifier _general = it.getGeneral();
        return Boolean.valueOf((_general == umlParentCompositeClass));
      };
      boolean _exists = IterableExtensions.<Generalization>exists(umlCompositeClass.getGeneralizations(), _function);
      return (!_exists);
    }
    
    public EObject getCorrepondenceSourceUmlParentCompositeClass(final CompositeDataType pcmType, final CompositeDataType pcmParentType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return pcmParentType;
    }
    
    public void updateGeneralizationElement(final CompositeDataType pcmType, final CompositeDataType pcmParentType, final org.eclipse.uml2.uml.Class umlCompositeClass, final org.eclipse.uml2.uml.Class umlParentCompositeClass, final Generalization generalization) {
      generalization.setSpecific(umlCompositeClass);
      generalization.setGeneral(umlParentCompositeClass);
    }
  }
  
  public AddParentTypeToCorrespondingCompositeTypeClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType pcmType, final CompositeDataType pcmParentType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmCompositeDataTypeReactions.AddParentTypeToCorrespondingCompositeTypeClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.pcmParentType = pcmParentType;
  }
  
  private CompositeDataType pcmType;
  
  private CompositeDataType pcmParentType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddParentTypeToCorrespondingCompositeTypeClassRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   pcmParentType: " + this.pcmParentType);
    
    org.eclipse.uml2.uml.Class umlCompositeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlCompositeClass(pcmType, pcmParentType), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmType, pcmParentType), 
    	false // asserted
    	);
    if (umlCompositeClass == null) {
    	return false;
    }
    registerObjectUnderModification(umlCompositeClass);
    org.eclipse.uml2.uml.Class umlParentCompositeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParentCompositeClass(pcmType, pcmParentType, umlCompositeClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmType, pcmParentType, umlCompositeClass), 
    	false // asserted
    	);
    if (umlParentCompositeClass == null) {
    	return false;
    }
    registerObjectUnderModification(umlParentCompositeClass);
    if (!userExecution.checkMatcherPrecondition1(pcmType, pcmParentType, umlCompositeClass, umlParentCompositeClass)) {
    	return false;
    }
    org.eclipse.uml2.uml.Generalization generalization = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createGeneralization();
    notifyObjectCreated(generalization);
    userExecution.updateGeneralizationElement(pcmType, pcmParentType, umlCompositeClass, umlParentCompositeClass, generalization);
    
    postprocessElements();
    
    return true;
  }
}
