package mir.routines.umlIPREClassReactions;

import java.io.IOException;
import mir.routines.umlIPREClassReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeNameOfCorrespondingIPRE_ImplementationRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingIPRE_ImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final String newName, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return pcmIPRE;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final String newName, final InterfaceProvidingRequiringEntity pcmIPRE) {
      boolean _endsWith = newName.endsWith(DefaultLiterals.IMPLEMENTATION_SUFFIX);
      if (_endsWith) {
        int _length = newName.length();
        int _length_1 = DefaultLiterals.IMPLEMENTATION_SUFFIX.length();
        int _minus = (_length - _length_1);
        pcmIPRE.setEntityName(StringExtensions.toFirstUpper(newName.substring(0, _minus)));
      } else {
        pcmIPRE.setEntityName(newName);
      }
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class umlClass, final String newName) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourcePcmIPRE(final org.eclipse.uml2.uml.Class umlClass, final String newName) {
      return umlClass;
    }
  }
  
  public ChangeNameOfCorrespondingIPRE_ImplementationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlIPREClassReactions.ChangeNameOfCorrespondingIPRE_ImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlClass = umlClass;this.newName = newName;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingIPRE_ImplementationRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    getLogger().debug("   newName: " + this.newName);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmIPRE = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmIPRE(umlClass, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlClass, newName), 
    	false // asserted
    	);
    if (pcmIPRE == null) {
    	return false;
    }
    registerObjectUnderModification(pcmIPRE);
    // val updatedElement userExecution.getElement1(umlClass, newName, pcmIPRE);
    userExecution.update0Element(umlClass, newName, pcmIPRE);
    
    postprocessElements();
    
    return true;
  }
}
