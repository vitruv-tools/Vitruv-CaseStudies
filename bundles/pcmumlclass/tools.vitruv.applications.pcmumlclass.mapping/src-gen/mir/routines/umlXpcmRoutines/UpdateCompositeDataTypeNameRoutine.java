package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateCompositeDataTypeNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateCompositeDataTypeNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage, final CompositeDataType pcmCompositeType, @Extension final RoutinesFacade _routinesFacade) {
      pcmCompositeType.setEntityName(class_.getName());
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeType(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage) {
      return class_;
    }
  }
  
  public UpdateCompositeDataTypeNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateCompositeDataTypeNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.class_ = class_;this.datatypesPackage = datatypesPackage;
  }
  
  private org.eclipse.uml2.uml.Class class_;
  
  private org.eclipse.uml2.uml.Package datatypesPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateCompositeDataTypeNameRoutine with input:");
    getLogger().debug("   class_: " + this.class_);
    getLogger().debug("   datatypesPackage: " + this.datatypesPackage);
    
    org.palladiosimulator.pcm.repository.CompositeDataType pcmCompositeType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeType(class_, datatypesPackage), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(class_, datatypesPackage), 
    	false // asserted
    	);
    if (pcmCompositeType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCompositeType);
    userExecution.executeAction1(class_, datatypesPackage, pcmCompositeType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
