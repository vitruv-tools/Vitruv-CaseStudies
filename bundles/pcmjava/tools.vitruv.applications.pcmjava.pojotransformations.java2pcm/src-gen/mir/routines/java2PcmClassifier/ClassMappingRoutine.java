package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersPackage;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * *nCreates Datatype if class was in datatypes package created or checks if there can be a correspondence and if nnot create a new pcm element.
 *  
 */
@SuppressWarnings("all")
public class ClassMappingRoutine extends AbstractRepairRoutineRealization {
  private ClassMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final org.emftext.language.java.containers.Package javaPackage) {
      return ContainersPackage.Literals.PACKAGE;
    }
    
    public String getRetrieveTag1(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository) {
      return "datatypes";
    }
    
    public EObject getCorrepondenceSourceDatatypesPackage(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository, final org.emftext.language.java.containers.Package datatypesPackage, @Extension final RoutinesFacade _routinesFacade) {
      if (((javaPackage != null) && javaPackage.getName().equals(datatypesPackage.getName()))) {
        _routinesFacade.createDataType(javaClass, compilationUnit);
      } else {
        _routinesFacade.checkSystemAndComponent(javaPackage, javaClass);
        _routinesFacade.createElement(javaClass, javaPackage, compilationUnit);
      }
    }
  }
  
  public ClassMappingRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final org.emftext.language.java.containers.Package javaPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.ClassMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.javaClass = javaClass;this.compilationUnit = compilationUnit;this.javaPackage = javaPackage;
  }
  
  private org.emftext.language.java.classifiers.Class javaClass;
  
  private CompilationUnit compilationUnit;
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ClassMappingRoutine with input:");
    getLogger().debug("   javaClass: " + this.javaClass);
    getLogger().debug("   compilationUnit: " + this.compilationUnit);
    getLogger().debug("   javaPackage: " + this.javaPackage);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(javaClass, compilationUnit, javaPackage), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    org.emftext.language.java.containers.Package datatypesPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDatatypesPackage(javaClass, compilationUnit, javaPackage, pcmRepository), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(javaClass, compilationUnit, javaPackage, pcmRepository), 
    	false // asserted
    	);
    if (datatypesPackage == null) {
    	return false;
    }
    registerObjectUnderModification(datatypesPackage);
    userExecution.callRoutine1(javaClass, compilationUnit, javaPackage, pcmRepository, datatypesPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
