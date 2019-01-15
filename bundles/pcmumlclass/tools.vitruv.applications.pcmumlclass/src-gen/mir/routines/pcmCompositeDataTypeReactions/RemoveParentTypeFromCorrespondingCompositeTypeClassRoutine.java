package mir.routines.pcmCompositeDataTypeReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcmCompositeDataTypeReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveParentTypeFromCorrespondingCompositeTypeClassRoutine extends AbstractRepairRoutineRealization {
  private RemoveParentTypeFromCorrespondingCompositeTypeClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlCompositeClass(final CompositeDataType pcmType, final CompositeDataType pcmParentType) {
      return pcmType;
    }
    
    public void executeAction1(final CompositeDataType pcmType, final CompositeDataType pcmParentType, final org.eclipse.uml2.uml.Class umlCompositeClass, final org.eclipse.uml2.uml.Class umlParentCompositeClass, @Extension final RoutinesFacade _routinesFacade) {
      final Function1<Generalization, Boolean> _function = (Generalization it) -> {
        Classifier _general = it.getGeneral();
        return Boolean.valueOf(Objects.equal(_general, umlParentCompositeClass));
      };
      final Generalization generalization = IterableExtensions.<Generalization>findFirst(umlCompositeClass.getGeneralizations(), _function);
      generalization.setGeneral(null);
      generalization.setSpecific(null);
      generalization.destroy();
    }
    
    public String getRetrieveTag1(final CompositeDataType pcmType, final CompositeDataType pcmParentType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public String getRetrieveTag2(final CompositeDataType pcmType, final CompositeDataType pcmParentType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getCorrepondenceSourceUmlParentCompositeClass(final CompositeDataType pcmType, final CompositeDataType pcmParentType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return pcmParentType;
    }
  }
  
  public RemoveParentTypeFromCorrespondingCompositeTypeClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType pcmType, final CompositeDataType pcmParentType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmCompositeDataTypeReactions.RemoveParentTypeFromCorrespondingCompositeTypeClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.pcmParentType = pcmParentType;
  }
  
  private CompositeDataType pcmType;
  
  private CompositeDataType pcmParentType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveParentTypeFromCorrespondingCompositeTypeClassRoutine with input:");
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
    userExecution.executeAction1(pcmType, pcmParentType, umlCompositeClass, umlParentCompositeClass, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
