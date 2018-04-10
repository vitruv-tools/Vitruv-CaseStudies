package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersPackage;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * *nCreates a new architectural element and add correspondence.
 *  
 */
@SuppressWarnings("all")
public class CreateElementRoutine extends AbstractRepairRoutineRealization {
  private CreateElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJavaRootPackage(final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.containers.Package javaPackage, final CompilationUnit compilationUnit, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.containers.Package javaPackage, final CompilationUnit compilationUnit) {
      return ContainersPackage.Literals.PACKAGE;
    }
    
    public EObject getCorrepondenceSource1(final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.containers.Package javaPackage, final CompilationUnit compilationUnit) {
      return javaClass;
    }
    
    public EObject getCorrepondenceSource2(final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.containers.Package javaPackage, final CompilationUnit compilationUnit) {
      return javaClass;
    }
    
    public String getRetrieveTag1(final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.containers.Package javaPackage, final CompilationUnit compilationUnit, final Repository pcmRepository) {
      return "package_root";
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.containers.Package javaPackage, final CompilationUnit compilationUnit, final Repository pcmRepository, final org.emftext.language.java.containers.Package javaRootPackage, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createArchitecturalElement(javaRootPackage, javaClass.getName(), IterableExtensions.<String>head(compilationUnit.getNamespaces()));
      _routinesFacade.checkSystemAndComponent(javaRootPackage, javaClass);
    }
  }
  
  public CreateElementRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.containers.Package javaPackage, final CompilationUnit compilationUnit) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.javaClass = javaClass;this.javaPackage = javaPackage;this.compilationUnit = compilationUnit;
  }
  
  private org.emftext.language.java.classifiers.Class javaClass;
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  private CompilationUnit compilationUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateElementRoutine with input:");
    getLogger().debug("   javaClass: " + this.javaClass);
    getLogger().debug("   javaPackage: " + this.javaPackage);
    getLogger().debug("   compilationUnit: " + this.compilationUnit);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(javaClass, javaPackage, compilationUnit), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.DataType.class,
    	(org.palladiosimulator.pcm.repository.DataType _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(javaClass, javaPackage, compilationUnit), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(javaClass, javaPackage, compilationUnit), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    org.emftext.language.java.containers.Package javaRootPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaRootPackage(javaClass, javaPackage, compilationUnit, pcmRepository), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(javaClass, javaPackage, compilationUnit, pcmRepository), 
    	false // asserted
    	);
    if (javaRootPackage == null) {
    	return false;
    }
    registerObjectUnderModification(javaRootPackage);
    userExecution.callRoutine1(javaClass, javaPackage, compilationUnit, pcmRepository, javaRootPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
