package mir.routines.umlToJavaClassifier;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaCompilationUnitRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaCompilationUnitRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJPackage(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace) {
      return uNamespace;
    }
    
    public EObject getElement1(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace, final Optional<org.emftext.language.java.containers.Package> jPackage, final CompilationUnit javaCompilationUnit) {
      return umlClassifier;
    }
    
    public EObject getElement2(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace, final Optional<org.emftext.language.java.containers.Package> jPackage, final CompilationUnit javaCompilationUnit) {
      return javaCompilationUnit;
    }
    
    public void updateJavaCompilationUnitElement(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace, final Optional<org.emftext.language.java.containers.Package> jPackage, final CompilationUnit javaCompilationUnit) {
      boolean _isPresent = jPackage.isPresent();
      if (_isPresent) {
        EList<String> _namespaces = javaCompilationUnit.getNamespaces();
        List<String> _javaPackageAsStringList = JavaContainerAndClassifierUtil.getJavaPackageAsStringList(jPackage.get());
        Iterables.<String>addAll(_namespaces, _javaPackageAsStringList);
      }
      String _name = jClassifier.getName();
      String _plus = (_name + ".java");
      javaCompilationUnit.setName(_plus);
      EList<ConcreteClassifier> _classifiers = javaCompilationUnit.getClassifiers();
      _classifiers.add(jClassifier);
      this.persistProjectRelative(umlClassifier, javaCompilationUnit, JavaPersistenceHelper.buildJavaFilePath(javaCompilationUnit));
    }
    
    public void callRoutine1(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace, final Optional<org.emftext.language.java.containers.Package> jPackage, final CompilationUnit javaCompilationUnit, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = jPackage.isPresent();
      if (_isPresent) {
        EList<CompilationUnit> _compilationUnits = jPackage.get().getCompilationUnits();
        _compilationUnits.add(javaCompilationUnit);
      }
    }
  }
  
  public CreateJavaCompilationUnitRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.CreateJavaCompilationUnitRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.umlClassifier = umlClassifier;this.jClassifier = jClassifier;this.uNamespace = uNamespace;
  }
  
  private Classifier umlClassifier;
  
  private ConcreteClassifier jClassifier;
  
  private Namespace uNamespace;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaCompilationUnitRoutine with input:");
    getLogger().debug("   umlClassifier: " + this.umlClassifier);
    getLogger().debug("   jClassifier: " + this.jClassifier);
    getLogger().debug("   uNamespace: " + this.uNamespace);
    
    	Optional<org.emftext.language.java.containers.Package> jPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceJPackage(umlClassifier, jClassifier, uNamespace), // correspondence source supplier
    		org.emftext.language.java.containers.Package.class,
    		(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(jPackage.isPresent() ? jPackage.get() : null);
    org.emftext.language.java.containers.CompilationUnit javaCompilationUnit = org.emftext.language.java.containers.impl.ContainersFactoryImpl.eINSTANCE.createCompilationUnit();
    notifyObjectCreated(javaCompilationUnit);
    userExecution.updateJavaCompilationUnitElement(umlClassifier, jClassifier, uNamespace, jPackage, javaCompilationUnit);
    
    addCorrespondenceBetween(userExecution.getElement1(umlClassifier, jClassifier, uNamespace, jPackage, javaCompilationUnit), userExecution.getElement2(umlClassifier, jClassifier, uNamespace, jPackage, javaCompilationUnit), "");
    
    userExecution.callRoutine1(umlClassifier, jClassifier, uNamespace, jPackage, javaCompilationUnit, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
