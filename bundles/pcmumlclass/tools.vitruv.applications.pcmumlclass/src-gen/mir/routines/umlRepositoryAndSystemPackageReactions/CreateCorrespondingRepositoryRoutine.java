package mir.routines.umlRepositoryAndSystemPackageReactions;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import java.io.IOException;
import mir.routines.umlRepositoryAndSystemPackageReactions.RoutinesFacade;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingRepositoryRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      final String fileExtension = DefaultLiterals.PCM_REPOSITORY_EXTENSION;
      String relativeModelPath = this.userInteractor.getTextInputDialogBuilder().message(DefaultLiterals.INPUT_REQUEST_NEW_MODEL_PATH).startInteraction();
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(relativeModelPath);
      if (_isNullOrEmpty) {
        relativeModelPath = ((DefaultLiterals.MODEL_DIRECTORY + "/") + DefaultLiterals.PCM_REPOSITORY_FILE_NAME);
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
      this.persistProjectRelative(umlPkg, pcmRepository, relativeModelPath);
    }
    
    public void updatePcmRepositoryElement(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository) {
      String _name = umlPkg.getName();
      String _firstUpper = null;
      if (_name!=null) {
        _firstUpper=StringExtensions.toFirstUpper(_name);
      }
      pcmRepository.setEntityName(_firstUpper);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository) {
      return umlPkg;
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
  }
  
  public CreateCorrespondingRepositoryRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryAndSystemPackageReactions.CreateCorrespondingRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPkg = umlPkg;this.umlParentPkg = umlParentPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlPkg;
  
  private org.eclipse.uml2.uml.Package umlParentPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingRepositoryRoutine with input:");
    getLogger().debug("   umlPkg: " + this.umlPkg);
    getLogger().debug("   umlParentPkg: " + this.umlParentPkg);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createRepository();
    notifyObjectCreated(pcmRepository);
    userExecution.updatePcmRepositoryElement(umlPkg, umlParentPkg, pcmRepository);
    
    addCorrespondenceBetween(userExecution.getElement1(umlPkg, umlParentPkg, pcmRepository), userExecution.getElement2(umlPkg, umlParentPkg, pcmRepository), userExecution.getTag1(umlPkg, umlParentPkg, pcmRepository));
    
    userExecution.executeAction1(umlPkg, umlParentPkg, pcmRepository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
