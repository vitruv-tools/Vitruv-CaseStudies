package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * *nCreates a new architectural element and add correspondance.
 *  
 */
@SuppressWarnings("all")
public class CreateElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final Repository repository, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
      return "package_root";
    }
    
    public EObject getCorrepondenceSourceJavaPackage(final Repository repository, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
      return repository;
    }
    
    public void callRoutine1(final Repository repository, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final org.emftext.language.java.containers.Package javaPackage, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createArchitecturalElement(javaPackage, javaClass.getName(), IterableExtensions.<String>head(compilationUnit.getNamespaces()));
      _routinesFacade.checkSystemAndComponent(javaPackage, javaClass);
    }
  }
  
  public CreateElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repository, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.repository = repository;this.javaClass = javaClass;this.compilationUnit = compilationUnit;
  }
  
  private Repository repository;
  
  private org.emftext.language.java.classifiers.Class javaClass;
  
  private CompilationUnit compilationUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateElementRoutine with input:");
    getLogger().debug("   repository: " + this.repository);
    getLogger().debug("   javaClass: " + this.javaClass);
    getLogger().debug("   compilationUnit: " + this.compilationUnit);
    
    org.emftext.language.java.containers.Package javaPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaPackage(repository, javaClass, compilationUnit), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(repository, javaClass, compilationUnit));
    if (javaPackage == null) {
    	return false;
    }
    registerObjectUnderModification(javaPackage);
    userExecution.callRoutine1(repository, javaClass, compilationUnit, javaPackage, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
