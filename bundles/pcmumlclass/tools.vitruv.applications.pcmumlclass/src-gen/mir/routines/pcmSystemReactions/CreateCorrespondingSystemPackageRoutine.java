package mir.routines.pcmSystemReactions;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import java.io.IOException;
import mir.routines.pcmSystemReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingSystemPackageRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingSystemPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateUmlSystemPackageElement(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      umlSystemPackage.setName(StringExtensions.toFirstLower(pcmSystem.getEntityName()));
    }
    
    public EObject getElement1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSource1(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
    
    public EObject getElement2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      return umlSystemPackage;
    }
    
    public String getTag1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
    
    public void callRoutine1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage, @Extension final RoutinesFacade _routinesFacade) {
      String relativeModelPath = this.userInteractor.getTextInputDialogBuilder().message(DefaultLiterals.INPUT_REQUEST_MODEL_PATH).startInteraction();
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
      final URI uri = PersistenceHelper.getURIFromSourceProjectFolder(pcmSystem, relativeModelPath);
      boolean _existsResourceAtUri = URIUtil.existsResourceAtUri(uri);
      if (_existsResourceAtUri) {
        final Resource resource = pcmSystem.eResource().getResourceSet().getResource(uri, true);
        umlRootModel = IterableExtensions.<Model>head(Iterables.<Model>filter(resource.getContents(), Model.class));
      }
      if ((umlRootModel == null)) {
        umlRootModel = UMLFactory.eINSTANCE.createModel();
        umlRootModel.setName(DefaultLiterals.ROOT_MODEL_NAME);
        this.persistProjectRelative(pcmSystem, umlRootModel, relativeModelPath);
      }
      EList<org.eclipse.uml2.uml.Package> _nestedPackages = umlRootModel.getNestedPackages();
      _nestedPackages.add(umlSystemPackage);
    }
  }
  
  public CreateCorrespondingSystemPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.CreateCorrespondingSystemPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingSystemPackageRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmSystem), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSystem)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Package umlSystemPackage = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(umlSystemPackage);
    userExecution.updateUmlSystemPackageElement(pcmSystem, umlSystemPackage);
    
    userExecution.callRoutine1(pcmSystem, umlSystemPackage, this.getRoutinesFacade());
    
    addCorrespondenceBetween(userExecution.getElement1(pcmSystem, umlSystemPackage), userExecution.getElement2(pcmSystem, umlSystemPackage), userExecution.getTag1(pcmSystem, umlSystemPackage));
    
    postprocessElements();
    
    return true;
  }
}
