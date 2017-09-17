package mir.routines.pcm2EjbJava;

import java.io.IOException;
import java.util.Collections;
import mir.routines.pcm2EjbJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.BasicComponent;
import tools.vitruv.domains.java.util.JavaModificationUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final org.emftext.language.java.classifiers.Class javaClass) {
      return javaClass;
    }
    
    public void updateJavaClassElement(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final org.emftext.language.java.classifiers.Class javaClass) {
      javaClass.setName(className);
      javaClass.addModifier(ModifiersFactory.eINSTANCE.createPublic());
      if ((sourceElementMappedToClass instanceof BasicComponent)) {
        JavaModificationUtil.addImportToClassFromString(javaClass, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("javax", "ejb")), "Stateless");
        JavaModificationUtil.addAnnotationToAnnotableAndModifiable(javaClass, "Stateless");
      }
    }
    
    public EObject getElement2(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final org.emftext.language.java.classifiers.Class javaClass) {
      return sourceElementMappedToClass;
    }
    
    public void callRoutine1(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final org.emftext.language.java.classifiers.Class javaClass, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createCompilationUnit(sourceElementMappedToClass, javaClass, containingPackage);
    }
  }
  
  public CreateJavaClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2EjbJava.CreateJavaClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2EjbJava.RoutinesFacade(getExecutionState(), this);
    this.sourceElementMappedToClass = sourceElementMappedToClass;this.containingPackage = containingPackage;this.className = className;
  }
  
  private NamedElement sourceElementMappedToClass;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  private String className;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaClassRoutine with input:");
    getLogger().debug("   sourceElementMappedToClass: " + this.sourceElementMappedToClass);
    getLogger().debug("   containingPackage: " + this.containingPackage);
    getLogger().debug("   className: " + this.className);
    
    org.emftext.language.java.classifiers.Class javaClass = org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl.eINSTANCE.createClass();
    notifyObjectCreated(javaClass);
    userExecution.updateJavaClassElement(sourceElementMappedToClass, containingPackage, className, javaClass);
    
    addCorrespondenceBetween(userExecution.getElement1(sourceElementMappedToClass, containingPackage, className, javaClass), userExecution.getElement2(sourceElementMappedToClass, containingPackage, className, javaClass), "");
    
    userExecution.callRoutine1(sourceElementMappedToClass, containingPackage, className, javaClass, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
