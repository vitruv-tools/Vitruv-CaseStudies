package mir.routines.javaToUmlClassifier;

import com.google.common.collect.Iterators;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateUmlModelRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateUmlModelRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final EObject alreadyPersistedEObject) {
      return UMLPackage.Literals.MODEL;
    }
    
    public void executeAction1(final EObject alreadyPersistedEObject, @Extension final RoutinesFacade _routinesFacade) {
      final String MODELNAME_INPUTMESSAGE = "Please enter a name for the uml root model (no file ending)";
      final String MODELPATH_INPUTMESSAGE = "Please enter a path for the uml root model (project relative)";
      String userModelName = this.userInteractor.getTextInputDialogBuilder().message(MODELNAME_INPUTMESSAGE).startInteraction();
      String userModelPath = this.userInteractor.getTextInputDialogBuilder().message(MODELPATH_INPUTMESSAGE).startInteraction();
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(userModelName);
      if (_isNullOrEmpty) {
        userModelName = "model";
      }
      boolean _isNullOrEmpty_1 = StringExtensions.isNullOrEmpty(userModelPath);
      if (_isNullOrEmpty_1) {
        userModelPath = "model";
      }
      final String rootModelFile = (((userModelPath + "/") + userModelName) + ".uml");
      Model umlRootModel = null;
      if ((alreadyPersistedEObject == null)) {
        throw new UnsupportedOperationException(
          "Can not persist an uml::Model from JavaToUml-reactions without any previously persisted elements.");
      }
      final URI uri = PersistenceHelper.getURIFromSourceProjectFolder(alreadyPersistedEObject, rootModelFile);
      boolean _existsResourceAtUri = URIUtil.existsResourceAtUri(uri);
      if (_existsResourceAtUri) {
        final Resource resource = alreadyPersistedEObject.eResource().getResourceSet().getResource(uri, true);
        umlRootModel = IteratorExtensions.<Model>head(Iterators.<Model>filter(resource.getAllContents(), Model.class));
      }
      if ((umlRootModel == null)) {
        umlRootModel = UMLFactory.eINSTANCE.createModel();
        umlRootModel.setName(userModelName);
        this.persistProjectRelative(alreadyPersistedEObject, umlRootModel, rootModelFile);
      }
      if ((umlRootModel != null)) {
        _routinesFacade.registerUmlModelInCorrespondenceModel(umlRootModel);
      }
    }
  }
  
  public DetectOrCreateUmlModelRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject alreadyPersistedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.DetectOrCreateUmlModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.alreadyPersistedEObject = alreadyPersistedEObject;
  }
  
  private EObject alreadyPersistedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateUmlModelRoutine with input:");
    getLogger().debug("   alreadyPersistedEObject: " + this.alreadyPersistedEObject);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(alreadyPersistedEObject), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    userExecution.executeAction1(alreadyPersistedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
