package mir.routines.umlToJavaClassifier;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.List;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Namespace;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil;
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
    
    public EObject getElement1(final org.emftext.language.java.containers.Package jPackage, final CompilationUnit jCompUnit, final Namespace uNamespace) {
      return jCompUnit;
    }
    
    public void update0Element(final org.emftext.language.java.containers.Package jPackage, final CompilationUnit jCompUnit, final Namespace uNamespace) {
      jCompUnit.getNamespaces().clear();
      EList<String> _namespaces = jCompUnit.getNamespaces();
      List<String> _javaPackageAsStringList = JavaContainerAndClassifierUtil.getJavaPackageAsStringList(jPackage);
      Iterables.<String>addAll(_namespaces, _javaPackageAsStringList);
      this.persistProjectRelative(uNamespace, jCompUnit, JavaPersistenceHelper.buildJavaFilePath(jCompUnit));
    }
  }
  
  public ChangePackageOfJavaCompilationUnitRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package jPackage, final CompilationUnit jCompUnit, final Namespace uNamespace) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.ChangePackageOfJavaCompilationUnitRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.jPackage = jPackage;this.jCompUnit = jCompUnit;this.uNamespace = uNamespace;
  }
  
  private org.emftext.language.java.containers.Package jPackage;
  
  private CompilationUnit jCompUnit;
  
  private Namespace uNamespace;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangePackageOfJavaCompilationUnitRoutine with input:");
    getLogger().debug("   jPackage: " + this.jPackage);
    getLogger().debug("   jCompUnit: " + this.jCompUnit);
    getLogger().debug("   uNamespace: " + this.uNamespace);
    
    // val updatedElement userExecution.getElement1(jPackage, jCompUnit, uNamespace);
    userExecution.update0Element(jPackage, jCompUnit, uNamespace);
    
    postprocessElements();
    
    return true;
  }
}
