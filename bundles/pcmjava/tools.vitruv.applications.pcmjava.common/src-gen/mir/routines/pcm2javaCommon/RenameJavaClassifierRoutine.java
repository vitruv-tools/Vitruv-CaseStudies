package mir.routines.pcm2javaCommon;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaClassifierRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameJavaClassifierRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit, final ConcreteClassifier javaClassifier) {
      return javaClassifier;
    }
    
    public void update0Element(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit, final ConcreteClassifier javaClassifier) {
      javaClassifier.setName(className);
    }
    
    public EObject getElement2(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit, final ConcreteClassifier javaClassifier) {
      return compilationUnit;
    }
    
    public EObject getCorrepondenceSourceJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit) {
      return classSourceElement;
    }
    
    public void update1Element(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit, final ConcreteClassifier javaClassifier) {
      compilationUnit.setName((className + ".java"));
      compilationUnit.getNamespaces().clear();
      EList<String> _namespaces = compilationUnit.getNamespaces();
      EList<String> _namespaces_1 = containingPackage.getNamespaces();
      Iterables.<String>addAll(_namespaces, _namespaces_1);
      EList<String> _namespaces_2 = compilationUnit.getNamespaces();
      String _name = containingPackage.getName();
      _namespaces_2.add(_name);
      this.persistProjectRelative(classSourceElement, compilationUnit, JavaPersistenceHelper.buildJavaFilePath(compilationUnit));
    }
    
    public EObject getCorrepondenceSourceCompilationUnit(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
      return classSourceElement;
    }
  }
  
  public RenameJavaClassifierRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.RenameJavaClassifierRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(getExecutionState(), this);
    this.classSourceElement = classSourceElement;this.containingPackage = containingPackage;this.className = className;
  }
  
  private NamedElement classSourceElement;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  private String className;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaClassifierRoutine with input:");
    getLogger().debug("   classSourceElement: " + this.classSourceElement);
    getLogger().debug("   containingPackage: " + this.containingPackage);
    getLogger().debug("   className: " + this.className);
    
    org.emftext.language.java.containers.CompilationUnit compilationUnit = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompilationUnit(classSourceElement, containingPackage, className), // correspondence source supplier
    	org.emftext.language.java.containers.CompilationUnit.class,
    	(org.emftext.language.java.containers.CompilationUnit _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compilationUnit == null) {
    	return false;
    }
    registerObjectUnderModification(compilationUnit);
    org.emftext.language.java.classifiers.ConcreteClassifier javaClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClassifier(classSourceElement, containingPackage, className, compilationUnit), // correspondence source supplier
    	org.emftext.language.java.classifiers.ConcreteClassifier.class,
    	(org.emftext.language.java.classifiers.ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaClassifier == null) {
    	return false;
    }
    registerObjectUnderModification(javaClassifier);
    // val updatedElement userExecution.getElement1(classSourceElement, containingPackage, className, compilationUnit, javaClassifier);
    userExecution.update0Element(classSourceElement, containingPackage, className, compilationUnit, javaClassifier);
    
    // val updatedElement userExecution.getElement2(classSourceElement, containingPackage, className, compilationUnit, javaClassifier);
    userExecution.update1Element(classSourceElement, containingPackage, className, compilationUnit, javaClassifier);
    
    postprocessElements();
    
    return true;
  }
}
