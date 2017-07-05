package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeJavaSuperInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeJavaSuperInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface superUMLInterface, final Interface uI, final org.emftext.language.java.classifiers.Interface jI, final org.emftext.language.java.classifiers.Interface superJavaInterface) {
      return jI;
    }
    
    public void update0Element(final Interface superUMLInterface, final Interface uI, final org.emftext.language.java.classifiers.Interface jI, final org.emftext.language.java.classifiers.Interface superJavaInterface) {
      EList<TypeReference> _extends = jI.getExtends();
      CompilationUnit _containingCompilationUnit = jI.getContainingCompilationUnit();
      TypeReference _createTypeReferenceAndUpdateImport = UmlToJavaHelper.createTypeReferenceAndUpdateImport(null, superJavaInterface, _containingCompilationUnit, this.userInteracting);
      _extends.add(_createTypeReferenceAndUpdateImport);
    }
    
    public EObject getCorrepondenceSourceSuperJavaInterface(final Interface superUMLInterface, final Interface uI, final org.emftext.language.java.classifiers.Interface jI) {
      return superUMLInterface;
    }
    
    public EObject getCorrepondenceSourceJI(final Interface superUMLInterface, final Interface uI) {
      return uI;
    }
  }
  
  public ChangeJavaSuperInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface superUMLInterface, final Interface uI) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.ChangeJavaSuperInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.superUMLInterface = superUMLInterface;this.uI = uI;
  }
  
  private Interface superUMLInterface;
  
  private Interface uI;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeJavaSuperInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.superUMLInterface);
    getLogger().debug("   Interface: " + this.uI);
    
    org.emftext.language.java.classifiers.Interface jI = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJI(superUMLInterface, uI), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (jI == null) {
    	return;
    }
    registerObjectUnderModification(jI);
    org.emftext.language.java.classifiers.Interface superJavaInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceSuperJavaInterface(superUMLInterface, uI, jI), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (superJavaInterface == null) {
    	return;
    }
    registerObjectUnderModification(superJavaInterface);
    // val updatedElement userExecution.getElement1(superUMLInterface, uI, jI, superJavaInterface);
    userExecution.update0Element(superUMLInterface, uI, jI, superJavaInterface);
    
    postprocessElements();
  }
}
