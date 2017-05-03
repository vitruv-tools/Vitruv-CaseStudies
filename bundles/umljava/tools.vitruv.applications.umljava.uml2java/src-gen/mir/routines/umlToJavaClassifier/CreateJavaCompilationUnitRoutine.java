package mir.routines.umlToJavaClassifier;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Collection;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.impl.ContainersFactoryImpl;
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
    
    public EObject getElement1(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace, final org.emftext.language.java.containers.Package jPackage, final CompilationUnit javaCompilationUnit) {
      return umlClassifier;
    }
    
    public EObject getElement2(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace, final org.emftext.language.java.containers.Package jPackage, final CompilationUnit javaCompilationUnit) {
      return javaCompilationUnit;
    }
    
    public void updateJavaCompilationUnitElement(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace, final org.emftext.language.java.containers.Package jPackage, final CompilationUnit javaCompilationUnit) {
      if ((jPackage != null)) {
        EList<String> _namespaces = javaCompilationUnit.getNamespaces();
        Collection<String> _javaPackageAsStringList = JavaContainerAndClassifierUtil.getJavaPackageAsStringList(jPackage);
        Iterables.<String>addAll(_namespaces, _javaPackageAsStringList);
      }
      javaCompilationUnit.setName(jClassifier.getName());
      EList<ConcreteClassifier> _classifiers = javaCompilationUnit.getClassifiers();
      _classifiers.add(jClassifier);
      this.persistProjectRelative(umlClassifier, javaCompilationUnit, JavaPersistenceHelper.buildJavaFilePath(javaCompilationUnit));
    }
    
    public void callRoutine1(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace, final org.emftext.language.java.containers.Package jPackage, final CompilationUnit javaCompilationUnit, @Extension final RoutinesFacade _routinesFacade) {
      if ((jPackage != null)) {
        EList<CompilationUnit> _compilationUnits = jPackage.getCompilationUnits();
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
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaCompilationUnitRoutine with input:");
    getLogger().debug("   Classifier: " + this.umlClassifier);
    getLogger().debug("   ConcreteClassifier: " + this.jClassifier);
    getLogger().debug("   Namespace: " + this.uNamespace);
    
    org.emftext.language.java.containers.Package jPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJPackage(umlClassifier, jClassifier, uNamespace), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(jPackage);
    CompilationUnit javaCompilationUnit = ContainersFactoryImpl.eINSTANCE.createCompilationUnit();
    userExecution.updateJavaCompilationUnitElement(umlClassifier, jClassifier, uNamespace, jPackage, javaCompilationUnit);
    
    addCorrespondenceBetween(userExecution.getElement1(umlClassifier, jClassifier, uNamespace, jPackage, javaCompilationUnit), userExecution.getElement2(umlClassifier, jClassifier, uNamespace, jPackage, javaCompilationUnit), "");
    
    userExecution.callRoutine1(umlClassifier, jClassifier, uNamespace, jPackage, javaCompilationUnit, actionsFacade);
    
    postprocessElements();
  }
}
