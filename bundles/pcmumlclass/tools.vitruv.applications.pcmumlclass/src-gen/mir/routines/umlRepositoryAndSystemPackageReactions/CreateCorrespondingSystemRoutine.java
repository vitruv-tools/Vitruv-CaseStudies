package mir.routines.umlRepositoryAndSystemPackageReactions;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import java.io.IOException;
import mir.routines.umlRepositoryAndSystemPackageReactions.RoutinesFacade;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingSystemRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingSystemRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public void updatePcmSystemElement(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final org.palladiosimulator.pcm.system.System pcmSystem) {
      String _name = umlPkg.getName();
      String _firstUpper = null;
      if (_name!=null) {
        _firstUpper=StringExtensions.toFirstUpper(_name);
      }
      pcmSystem.setEntityName(_firstUpper);
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final org.palladiosimulator.pcm.system.System pcmSystem, @Extension final RoutinesFacade _routinesFacade) {
      final String fileExtension = DefaultLiterals.PCM_SYSTEM_EXTENSION;
      String relativeModelPath = this.userInteractor.getTextInputDialogBuilder().message(DefaultLiterals.INPUT_REQUEST_NEW_MODEL_PATH).startInteraction();
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(relativeModelPath);
      if (_isNullOrEmpty) {
        relativeModelPath = ((DefaultLiterals.MODEL_DIRECTORY + "/") + DefaultLiterals.PCM_SYSTEM_FILE_NAME);
      }
      boolean _endsWith = relativeModelPath.endsWith(fileExtension);
      if (_endsWith) {
        int _length = relativeModelPath.length();
        int _length_1 = fileExtension.length();
        int _minus = (_length - _length_1);
        relativeModelPath.substring(0, _minus);
      }
      URI uri = PersistenceHelper.getURIFromSourceProjectFolder(umlPkg, (relativeModelPath + fileExtension));
      while (URIUtil.existsResourceAtUri(uri)) {
        uri = PersistenceHelper.getURIFromSourceProjectFolder(umlPkg, ((relativeModelPath + "-2") + fileExtension));
      }
      boolean _endsWith_1 = relativeModelPath.endsWith(fileExtension);
      boolean _not = (!_endsWith_1);
      if (_not) {
        String _relativeModelPath = relativeModelPath;
        relativeModelPath = (_relativeModelPath + fileExtension);
      }
      this.persistProjectRelative(umlPkg, pcmSystem, relativeModelPath);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return umlPkg;
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
  }
  
  public CreateCorrespondingSystemRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryAndSystemPackageReactions.CreateCorrespondingSystemRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPkg = umlPkg;this.umlParentPkg = umlParentPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlPkg;
  
  private org.eclipse.uml2.uml.Package umlParentPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingSystemRoutine with input:");
    getLogger().debug("   umlPkg: " + this.umlPkg);
    getLogger().debug("   umlParentPkg: " + this.umlParentPkg);
    
    org.palladiosimulator.pcm.system.System pcmSystem = org.palladiosimulator.pcm.system.impl.SystemFactoryImpl.eINSTANCE.createSystem();
    notifyObjectCreated(pcmSystem);
    userExecution.updatePcmSystemElement(umlPkg, umlParentPkg, pcmSystem);
    
    addCorrespondenceBetween(userExecution.getElement1(umlPkg, umlParentPkg, pcmSystem), userExecution.getElement2(umlPkg, umlParentPkg, pcmSystem), userExecution.getTag1(umlPkg, umlParentPkg, pcmSystem));
    
    userExecution.executeAction1(umlPkg, umlParentPkg, pcmSystem, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
