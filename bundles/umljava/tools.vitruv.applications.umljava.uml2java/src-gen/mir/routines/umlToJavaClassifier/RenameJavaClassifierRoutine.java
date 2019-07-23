package mir.routines.umlToJavaClassifier;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaClassifierRoutine extends AbstractRepairRoutineRealization {
  private RenameJavaClassifierRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJPackage(final Classifier umlClassifier) {
      EObject _eContainer = umlClassifier.eContainer();
      return _eContainer;
    }
    
    public EObject getElement1(final Classifier umlClassifier, final Optional<org.emftext.language.java.containers.Package> jPackage, final ConcreteClassifier javaClassifier, final CompilationUnit javaCompilationUnit) {
      return javaClassifier;
    }
    
    public void update0Element(final Classifier umlClassifier, final Optional<org.emftext.language.java.containers.Package> jPackage, final ConcreteClassifier javaClassifier, final CompilationUnit javaCompilationUnit) {
      javaClassifier.setName(umlClassifier.getName());
    }
    
    public EObject getElement2(final Classifier umlClassifier, final Optional<org.emftext.language.java.containers.Package> jPackage, final ConcreteClassifier javaClassifier, final CompilationUnit javaCompilationUnit) {
      return javaCompilationUnit;
    }
    
    public EObject getCorrepondenceSourceJavaCompilationUnit(final Classifier umlClassifier, final Optional<org.emftext.language.java.containers.Package> jPackage, final ConcreteClassifier javaClassifier) {
      return umlClassifier;
    }
    
    public EObject getCorrepondenceSourceJavaClassifier(final Classifier umlClassifier, final Optional<org.emftext.language.java.containers.Package> jPackage) {
      return umlClassifier;
    }
    
    public void update1Element(final Classifier umlClassifier, final Optional<org.emftext.language.java.containers.Package> jPackage, final ConcreteClassifier javaClassifier, final CompilationUnit javaCompilationUnit) {
      boolean _isPresent = jPackage.isPresent();
      if (_isPresent) {
        javaCompilationUnit.getNamespaces().clear();
        EList<String> _namespaces = javaCompilationUnit.getNamespaces();
        List<String> _javaPackageAsStringList = JavaContainerAndClassifierUtil.getJavaPackageAsStringList(jPackage.get());
        Iterables.<String>addAll(_namespaces, _javaPackageAsStringList);
      }
      String _name = umlClassifier.getName();
      String _plus = (_name + ".java");
      javaCompilationUnit.setName(_plus);
      this.persistProjectRelative(umlClassifier, javaCompilationUnit, JavaPersistenceHelper.buildJavaFilePath(javaCompilationUnit));
    }
    
    public void callRoutine1(final Classifier umlClassifier, final Optional<org.emftext.language.java.containers.Package> jPackage, final ConcreteClassifier javaClassifier, final CompilationUnit javaCompilationUnit, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = jPackage.isPresent();
      if (_isPresent) {
        EList<CompilationUnit> _compilationUnits = jPackage.get().getCompilationUnits();
        _compilationUnits.add(javaCompilationUnit);
      }
    }
  }
  
  public RenameJavaClassifierRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier umlClassifier) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.RenameJavaClassifierRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlClassifier = umlClassifier;
  }
  
  private Classifier umlClassifier;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaClassifierRoutine with input:");
    getLogger().debug("   umlClassifier: " + this.umlClassifier);
    
    	Optional<org.emftext.language.java.containers.Package> jPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceJPackage(umlClassifier), // correspondence source supplier
    		org.emftext.language.java.containers.Package.class,
    		(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(jPackage.isPresent() ? jPackage.get() : null);
    org.emftext.language.java.classifiers.ConcreteClassifier javaClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClassifier(umlClassifier, jPackage), // correspondence source supplier
    	org.emftext.language.java.classifiers.ConcreteClassifier.class,
    	(org.emftext.language.java.classifiers.ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaClassifier == null) {
    	return false;
    }
    registerObjectUnderModification(javaClassifier);
    org.emftext.language.java.containers.CompilationUnit javaCompilationUnit = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaCompilationUnit(umlClassifier, jPackage, javaClassifier), // correspondence source supplier
    	org.emftext.language.java.containers.CompilationUnit.class,
    	(org.emftext.language.java.containers.CompilationUnit _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaCompilationUnit == null) {
    	return false;
    }
    registerObjectUnderModification(javaCompilationUnit);
    // val updatedElement userExecution.getElement1(umlClassifier, jPackage, javaClassifier, javaCompilationUnit);
    userExecution.update0Element(umlClassifier, jPackage, javaClassifier, javaCompilationUnit);
    
    // val updatedElement userExecution.getElement2(umlClassifier, jPackage, javaClassifier, javaCompilationUnit);
    userExecution.update1Element(umlClassifier, jPackage, javaClassifier, javaCompilationUnit);
    
    userExecution.callRoutine1(umlClassifier, jPackage, javaClassifier, javaCompilationUnit, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
