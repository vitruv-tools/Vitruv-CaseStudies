package mir.routines.umlRepositoryComponentPackageReactions;

import java.io.IOException;
import mir.routines.umlRepositoryComponentPackageReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.repository.BasicComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingBasicComponentRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingBasicComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final BasicComponent pcmComponent) {
      return pcmComponent;
    }
    
    public void updatePcmComponentElement(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final BasicComponent pcmComponent) {
      String _name = umlPkg.getName();
      String _firstUpper = null;
      if (_name!=null) {
        _firstUpper=StringExtensions.toFirstUpper(_name);
      }
      pcmComponent.setEntityName(_firstUpper);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final BasicComponent pcmComponent) {
      return umlPkg;
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final BasicComponent pcmComponent) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
  }
  
  public CreateCorrespondingBasicComponentRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryComponentPackageReactions.CreateCorrespondingBasicComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPkg = umlPkg;this.umlParentPkg = umlParentPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlPkg;
  
  private org.eclipse.uml2.uml.Package umlParentPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingBasicComponentRoutine with input:");
    getLogger().debug("   umlPkg: " + this.umlPkg);
    getLogger().debug("   umlParentPkg: " + this.umlParentPkg);
    
    org.palladiosimulator.pcm.repository.BasicComponent pcmComponent = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createBasicComponent();
    notifyObjectCreated(pcmComponent);
    userExecution.updatePcmComponentElement(umlPkg, umlParentPkg, pcmComponent);
    
    addCorrespondenceBetween(userExecution.getElement1(umlPkg, umlParentPkg, pcmComponent), userExecution.getElement2(umlPkg, umlParentPkg, pcmComponent), userExecution.getTag1(umlPkg, umlParentPkg, pcmComponent));
    
    postprocessElements();
    
    return true;
  }
}
