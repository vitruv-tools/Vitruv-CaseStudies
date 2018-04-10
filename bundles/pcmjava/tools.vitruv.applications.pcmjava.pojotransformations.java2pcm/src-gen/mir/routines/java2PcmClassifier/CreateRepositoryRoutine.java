package mir.routines.java2PcmClassifier;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.containers.ContainersPackage;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * *nCreates Repository.
 *  
 */
@SuppressWarnings("all")
public class CreateRepositoryRoutine extends AbstractRepairRoutineRealization {
  private CreateRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag) {
      return javaPackage;
    }
    
    public void update0Element(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag) {
      final URI packageUri = javaPackage.eResource().getURI();
      boolean _existsResourceAtUri = URIUtil.existsResourceAtUri(packageUri);
      boolean _not = (!_existsResourceAtUri);
      if (_not) {
        final Function2<String, String, String> _function = (String a, String b) -> {
          return ((a + "/") + b);
        };
        final String projectRelativeResourcePath = IterableExtensions.<String, String>fold(IterableExtensions.<String>tail(packageUri.segmentsList()), "", _function);
        this.persistProjectRelative(javaPackage, javaPackage, projectRelativeResourcePath);
      }
    }
    
    public EObject getCorrepondenceSource1(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag) {
      return javaPackage;
    }
    
    public EObject getCorrepondenceSource2(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag) {
      return ContainersPackage.Literals.PACKAGE;
    }
    
    public EObject getElement4(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public String getRetrieveTag1(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag) {
      return newTag;
    }
    
    public void updatePcmRepositoryElement(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag, final Repository pcmRepository) {
      pcmRepository.setEntityName(packageName);
      String _entityName = pcmRepository.getEntityName();
      String _plus = ("model/" + _entityName);
      String _plus_1 = (_plus + ".repository");
      this.persistProjectRelative(javaPackage, pcmRepository, _plus_1);
    }
    
    public EObject getElement5(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag, final Repository pcmRepository) {
      return ContainersPackage.Literals.PACKAGE;
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public EObject getElement3(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag, final Repository pcmRepository) {
      return javaPackage;
    }
    
    public String getTag1(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag, final Repository pcmRepository) {
      return newTag;
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag, final Repository pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaSubPackages(javaPackage);
    }
  }
  
  public CreateRepositoryRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.javaPackage = javaPackage;this.packageName = packageName;this.newTag = newTag;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  private String packageName;
  
  private String newTag;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRepositoryRoutine with input:");
    getLogger().debug("   javaPackage: " + this.javaPackage);
    getLogger().debug("   packageName: " + this.packageName);
    getLogger().debug("   newTag: " + this.newTag);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(javaPackage, packageName, newTag), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(javaPackage, packageName, newTag)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(javaPackage, packageName, newTag), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    // val updatedElement userExecution.getElement1(javaPackage, packageName, newTag);
    userExecution.update0Element(javaPackage, packageName, newTag);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createRepository();
    notifyObjectCreated(pcmRepository);
    userExecution.updatePcmRepositoryElement(javaPackage, packageName, newTag, pcmRepository);
    
    addCorrespondenceBetween(userExecution.getElement2(javaPackage, packageName, newTag, pcmRepository), userExecution.getElement3(javaPackage, packageName, newTag, pcmRepository), userExecution.getTag1(javaPackage, packageName, newTag, pcmRepository));
    
    userExecution.callRoutine1(javaPackage, packageName, newTag, pcmRepository, this.getRoutinesFacade());
    
    addCorrespondenceBetween(userExecution.getElement4(javaPackage, packageName, newTag, pcmRepository), userExecution.getElement5(javaPackage, packageName, newTag, pcmRepository), "");
    
    postprocessElements();
    
    return true;
  }
}
