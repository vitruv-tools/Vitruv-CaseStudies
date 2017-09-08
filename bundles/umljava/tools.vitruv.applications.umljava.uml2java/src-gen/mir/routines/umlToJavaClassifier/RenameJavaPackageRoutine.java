package mir.routines.umlToJavaClassifier;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.List;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameJavaPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJPackage(final org.eclipse.uml2.uml.Package uPackage, final Namespace uNamespace) {
      return uPackage;
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package uPackage, final Namespace uNamespace, final org.emftext.language.java.containers.Package jPackage) {
      return jPackage;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Package uPackage, final Namespace uNamespace, final org.emftext.language.java.containers.Package jPackage) {
      jPackage.getNamespaces().clear();
      EList<String> _namespaces = jPackage.getNamespaces();
      List<String> _umlParentNamespaceAsStringList = UmlClassifierAndPackageUtil.getUmlParentNamespaceAsStringList(uPackage);
      Iterables.<String>addAll(_namespaces, _umlParentNamespaceAsStringList);
      jPackage.setName(uPackage.getName());
      this.persistProjectRelative(uPackage, jPackage, JavaPersistenceHelper.buildJavaFilePath(jPackage));
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package uPackage, final Namespace uNamespace, final org.emftext.language.java.containers.Package jPackage, @Extension final RoutinesFacade _routinesFacade) {
      boolean _equals = uPackage.getName().equals(jPackage.getName());
      boolean _not = (!_equals);
      if (_not) {
        EList<CompilationUnit> _compilationUnits = jPackage.getCompilationUnits();
        for (final CompilationUnit compUnit : _compilationUnits) {
          _routinesFacade.changePackageOfJavaCompilationUnit(jPackage, compUnit, uNamespace);
        }
      }
    }
  }
  
  public RenameJavaPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package uPackage, final Namespace uNamespace) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.RenameJavaPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.uPackage = uPackage;this.uNamespace = uNamespace;
  }
  
  private org.eclipse.uml2.uml.Package uPackage;
  
  private Namespace uNamespace;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaPackageRoutine with input:");
    getLogger().debug("   uPackage: " + this.uPackage);
    getLogger().debug("   uNamespace: " + this.uNamespace);
    
    org.emftext.language.java.containers.Package jPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJPackage(uPackage, uNamespace), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jPackage == null) {
    	return false;
    }
    registerObjectUnderModification(jPackage);
    // val updatedElement userExecution.getElement1(uPackage, uNamespace, jPackage);
    userExecution.update0Element(uPackage, uNamespace, jPackage);
    
    userExecution.callRoutine1(uPackage, uNamespace, jPackage, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
