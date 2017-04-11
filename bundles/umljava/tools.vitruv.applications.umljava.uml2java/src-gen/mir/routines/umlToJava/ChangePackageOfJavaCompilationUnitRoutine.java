package mir.routines.umlToJava;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.List;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.umljava.util.UmlUtil;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangePackageOfJavaCompilationUnitRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangePackageOfJavaCompilationUnitRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJPackage(final org.eclipse.uml2.uml.Package uPackage, final Classifier uClassifier, final CompilationUnit jCompUnit) {
      return uPackage;
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package uPackage, final Classifier uClassifier, final CompilationUnit jCompUnit, final org.emftext.language.java.containers.Package jPackage) {
      return jPackage;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Package uPackage, final Classifier uClassifier, final CompilationUnit jCompUnit, final org.emftext.language.java.containers.Package jPackage) {
      EList<CompilationUnit> _compilationUnits = jPackage.getCompilationUnits();
      _compilationUnits.add(jCompUnit);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package uPackage, final Classifier uClassifier, final CompilationUnit jCompUnit, final org.emftext.language.java.containers.Package jPackage) {
      return jCompUnit;
    }
    
    public EObject getCorrepondenceSourceJCompUnit(final org.eclipse.uml2.uml.Package uPackage, final Classifier uClassifier) {
      return uClassifier;
    }
    
    public void update1Element(final org.eclipse.uml2.uml.Package uPackage, final Classifier uClassifier, final CompilationUnit jCompUnit, final org.emftext.language.java.containers.Package jPackage) {
      jCompUnit.getNamespaces().clear();
      EList<String> _namespaces = jCompUnit.getNamespaces();
      List<String> _umlNamespaceAsStringList = UmlUtil.getUmlNamespaceAsStringList(uPackage);
      Iterables.<String>addAll(_namespaces, _umlNamespaceAsStringList);
      this.persistProjectRelative(uClassifier, jCompUnit, JavaPersistenceHelper.buildJavaFilePath(jCompUnit));
    }
  }
  
  public ChangePackageOfJavaCompilationUnitRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package uPackage, final Classifier uClassifier) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.ChangePackageOfJavaCompilationUnitRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uPackage = uPackage;this.uClassifier = uClassifier;
  }
  
  private org.eclipse.uml2.uml.Package uPackage;
  
  private Classifier uClassifier;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangePackageOfJavaCompilationUnitRoutine with input:");
    getLogger().debug("   Package: " + this.uPackage);
    getLogger().debug("   Classifier: " + this.uClassifier);
    
    CompilationUnit jCompUnit = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJCompUnit(uPackage, uClassifier), // correspondence source supplier
    	CompilationUnit.class,
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	null);
    if (jCompUnit == null) {
    	return;
    }
    registerObjectUnderModification(jCompUnit);
    org.emftext.language.java.containers.Package jPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJPackage(uPackage, uClassifier, jCompUnit), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null);
    if (jPackage == null) {
    	return;
    }
    registerObjectUnderModification(jPackage);
    // val updatedElement userExecution.getElement1(uPackage, uClassifier, jCompUnit, jPackage);
    userExecution.update0Element(uPackage, uClassifier, jCompUnit, jPackage);
    
    // val updatedElement userExecution.getElement2(uPackage, uClassifier, jCompUnit, jPackage);
    userExecution.update1Element(uPackage, uClassifier, jCompUnit, jPackage);
    
    postprocessElements();
  }
}
