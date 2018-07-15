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
public class CreateCompilationUnitRoutine extends AbstractRepairRoutineRealization {
  private CreateCompilationUnitRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage, final CompilationUnit compilationUnit) {
      return compilationUnit;
    }
    
    public void updateCompilationUnitElement(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage, final CompilationUnit compilationUnit) {
      EList<String> _namespaces = compilationUnit.getNamespaces();
      EList<String> _namespaces_1 = containingPackage.getNamespaces();
      Iterables.<String>addAll(_namespaces, _namespaces_1);
      EList<String> _namespaces_2 = compilationUnit.getNamespaces();
      String _name = containingPackage.getName();
      _namespaces_2.add(_name);
      String _name_1 = classifier.getName();
      String _plus = (_name_1 + ".java");
      compilationUnit.setName(_plus);
      compilationUnit.getClassifiers().add(classifier);
      this.persistProjectRelative(sourceElementMappedToClass, compilationUnit, JavaPersistenceHelper.buildJavaFilePath(compilationUnit));
    }
    
    public EObject getElement2(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage, final CompilationUnit compilationUnit) {
      return sourceElementMappedToClass;
    }
  }
  
  public CreateCompilationUnitRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.CreateCompilationUnitRoutine.ActionUserExecution(getExecutionState(), this);
    this.sourceElementMappedToClass = sourceElementMappedToClass;this.classifier = classifier;this.containingPackage = containingPackage;
  }
  
  private NamedElement sourceElementMappedToClass;
  
  private ConcreteClassifier classifier;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompilationUnitRoutine with input:");
    getLogger().debug("   sourceElementMappedToClass: " + this.sourceElementMappedToClass);
    getLogger().debug("   classifier: " + this.classifier);
    getLogger().debug("   containingPackage: " + this.containingPackage);
    
    org.emftext.language.java.containers.CompilationUnit compilationUnit = org.emftext.language.java.containers.impl.ContainersFactoryImpl.eINSTANCE.createCompilationUnit();
    notifyObjectCreated(compilationUnit);
    userExecution.updateCompilationUnitElement(sourceElementMappedToClass, classifier, containingPackage, compilationUnit);
    
    addCorrespondenceBetween(userExecution.getElement1(sourceElementMappedToClass, classifier, containingPackage, compilationUnit), userExecution.getElement2(sourceElementMappedToClass, classifier, containingPackage, compilationUnit), "");
    
    postprocessElements();
    
    return true;
  }
}
