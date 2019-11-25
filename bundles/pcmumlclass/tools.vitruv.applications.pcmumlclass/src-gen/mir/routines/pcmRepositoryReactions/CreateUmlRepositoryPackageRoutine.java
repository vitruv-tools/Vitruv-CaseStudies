package mir.routines.pcmRepositoryReactions;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import java.io.IOException;
import mir.routines.pcmRepositoryReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionOptions;

@SuppressWarnings("all")
public class CreateUmlRepositoryPackageRoutine extends AbstractRepairRoutineRealization {
  private CreateUmlRepositoryPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
      return pcmRepo;
    }
    
    public EObject getCorrepondenceSource1(final Repository pcmRepo) {
      return pcmRepo;
    }
    
    public EObject getCorrepondenceSource2(final Repository pcmRepo) {
      return pcmRepo;
    }
    
    public String getRetrieveTag1(final Repository pcmRepo) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public EObject getCorrepondenceSource3(final Repository pcmRepo) {
      return pcmRepo;
    }
    
    public String getRetrieveTag2(final Repository pcmRepo) {
      return TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE;
    }
    
    public EObject getElement2(final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
      return umlRepositoryPkg;
    }
    
    public String getRetrieveTag3(final Repository pcmRepo) {
      return TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE;
    }
    
    public String getTag1(final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public void updateUmlRepositoryPkgElement(final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
      umlRepositoryPkg.setName(StringExtensions.toFirstLower(pcmRepo.getEntityName()));
    }
    
    public void callRoutine1(final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlRepositoryPkg, @Extension final RoutinesFacade _routinesFacade) {
      String relativeModelPath = this.userInteractor.getTextInputDialogBuilder().message(DefaultLiterals.INPUT_REQUEST_MODEL_PATH).windowModality(UserInteractionOptions.WindowModality.MODAL).startInteraction();
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(relativeModelPath);
      if (_isNullOrEmpty) {
        relativeModelPath = (((DefaultLiterals.MODEL_DIRECTORY + "/") + DefaultLiterals.UML_MODEL_FILE_NAME) + DefaultLiterals.UML_EXTENSION);
      }
      boolean _endsWith = relativeModelPath.endsWith(DefaultLiterals.UML_EXTENSION);
      boolean _not = (!_endsWith);
      if (_not) {
        String _relativeModelPath = relativeModelPath;
        relativeModelPath = (_relativeModelPath + DefaultLiterals.UML_EXTENSION);
      }
      Model umlRootModel = null;
      final URI uri = PersistenceHelper.getURIFromSourceProjectFolder(pcmRepo, relativeModelPath);
      boolean _existsResourceAtUri = URIUtil.existsResourceAtUri(uri);
      if (_existsResourceAtUri) {
        final Resource resource = pcmRepo.eResource().getResourceSet().getResource(uri, true);
        umlRootModel = IterableExtensions.<Model>head(Iterables.<Model>filter(resource.getContents(), Model.class));
      }
      if ((umlRootModel == null)) {
        umlRootModel = UMLFactory.eINSTANCE.createModel();
        umlRootModel.setName(DefaultLiterals.ROOT_MODEL_NAME);
        this.persistProjectRelative(pcmRepo, umlRootModel, relativeModelPath);
      }
      EList<org.eclipse.uml2.uml.Package> _nestedPackages = umlRootModel.getNestedPackages();
      _nestedPackages.add(umlRepositoryPkg);
      _routinesFacade.ensureUmlModelCorrespondenceExists(umlRootModel);
    }
  }
  
  public CreateUmlRepositoryPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository pcmRepo) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryReactions.CreateUmlRepositoryPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRepo = pcmRepo;
  }
  
  private Repository pcmRepo;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlRepositoryPackageRoutine with input:");
    getLogger().debug("   pcmRepo: " + this.pcmRepo);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmRepo), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmRepo)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmRepo), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmRepo)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource3(pcmRepo), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(pcmRepo)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Package umlRepositoryPkg = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(umlRepositoryPkg);
    userExecution.updateUmlRepositoryPkgElement(pcmRepo, umlRepositoryPkg);
    
    userExecution.callRoutine1(pcmRepo, umlRepositoryPkg, this.getRoutinesFacade());
    
    addCorrespondenceBetween(userExecution.getElement1(pcmRepo, umlRepositoryPkg), userExecution.getElement2(pcmRepo, umlRepositoryPkg), userExecution.getTag1(pcmRepo, umlRepositoryPkg));
    
    postprocessElements();
    
    return true;
  }
}
