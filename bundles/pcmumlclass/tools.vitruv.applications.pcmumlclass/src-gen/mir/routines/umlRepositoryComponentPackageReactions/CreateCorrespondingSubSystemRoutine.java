package mir.routines.umlRepositoryComponentPackageReactions;

import java.io.IOException;
import mir.routines.umlRepositoryComponentPackageReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingSubSystemRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingSubSystemRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final SubSystem pcmComponent) {
      return pcmComponent;
    }
    
    public void updatePcmComponentElement(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final SubSystem pcmComponent) {
      String _name = umlPkg.getName();
      String _firstUpper = null;
      if (_name!=null) {
        _firstUpper=StringExtensions.toFirstUpper(_name);
      }
      pcmComponent.setEntityName(_firstUpper);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final SubSystem pcmComponent) {
      return umlPkg;
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final SubSystem pcmComponent) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
  }
  
  public CreateCorrespondingSubSystemRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryComponentPackageReactions.CreateCorrespondingSubSystemRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPkg = umlPkg;this.umlParentPkg = umlParentPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlPkg;
  
  private org.eclipse.uml2.uml.Package umlParentPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingSubSystemRoutine with input:");
    getLogger().debug("   umlPkg: " + this.umlPkg);
    getLogger().debug("   umlParentPkg: " + this.umlParentPkg);
    
    org.palladiosimulator.pcm.subsystem.SubSystem pcmComponent = org.palladiosimulator.pcm.subsystem.impl.SubsystemFactoryImpl.eINSTANCE.createSubSystem();
    notifyObjectCreated(pcmComponent);
    userExecution.updatePcmComponentElement(umlPkg, umlParentPkg, pcmComponent);
    
    addCorrespondenceBetween(userExecution.getElement1(umlPkg, umlParentPkg, pcmComponent), userExecution.getElement2(umlPkg, umlParentPkg, pcmComponent), userExecution.getTag1(umlPkg, umlParentPkg, pcmComponent));
    
    postprocessElements();
    
    return true;
  }
}
