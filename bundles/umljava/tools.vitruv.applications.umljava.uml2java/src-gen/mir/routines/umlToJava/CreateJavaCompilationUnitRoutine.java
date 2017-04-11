package mir.routines.umlToJava;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.List;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Namespace;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.impl.ContainersFactoryImpl;
import tools.vitruv.applications.umljava.util.UmlUtil;
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
    
    public EObject getElement1(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace, final CompilationUnit javaCompilationUnit) {
      return umlClassifier;
    }
    
    public EObject getElement2(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace, final CompilationUnit javaCompilationUnit) {
      return javaCompilationUnit;
    }
    
    public void updateJavaCompilationUnitElement(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace, final CompilationUnit javaCompilationUnit) {
      if ((!(uNamespace instanceof Model))) {
        EList<String> _namespaces = javaCompilationUnit.getNamespaces();
        List<String> _umlNamespaceAsStringList = UmlUtil.getUmlNamespaceAsStringList(uNamespace);
        Iterables.<String>addAll(_namespaces, _umlNamespaceAsStringList);
      }
      javaCompilationUnit.setName(jClassifier.getName());
      EList<ConcreteClassifier> _classifiers = javaCompilationUnit.getClassifiers();
      _classifiers.add(jClassifier);
      this.persistProjectRelative(umlClassifier, javaCompilationUnit, JavaPersistenceHelper.buildJavaFilePath(javaCompilationUnit));
    }
  }
  
  public CreateJavaCompilationUnitRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaCompilationUnitRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
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
    
    CompilationUnit javaCompilationUnit = ContainersFactoryImpl.eINSTANCE.createCompilationUnit();
    userExecution.updateJavaCompilationUnitElement(umlClassifier, jClassifier, uNamespace, javaCompilationUnit);
    
    addCorrespondenceBetween(userExecution.getElement1(umlClassifier, jClassifier, uNamespace, javaCompilationUnit), userExecution.getElement2(umlClassifier, jClassifier, uNamespace, javaCompilationUnit), "");
    
    postprocessElements();
  }
}
