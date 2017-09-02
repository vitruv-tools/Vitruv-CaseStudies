package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeJavaImplementedInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeJavaImplementedInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface uInterface, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Interface jInterface) {
      return jClass;
    }
    
    public EObject getCorrepondenceSourceJClass(final Interface uInterface, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public void update0Element(final Interface uInterface, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Interface jInterface) {
      EList<TypeReference> _implements = jClass.getImplements();
      TypeReference _createTypeReferenceAndUpdateImport = UmlToJavaHelper.createTypeReferenceAndUpdateImport(null, jInterface, jClass.getContainingCompilationUnit(), this.userInteracting);
      _implements.add(_createTypeReferenceAndUpdateImport);
    }
    
    public EObject getCorrepondenceSourceJInterface(final Interface uInterface, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass) {
      return uInterface;
    }
    
    public void callRoutine1(final Interface uInterface, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Interface jInterface, @Extension final RoutinesFacade _routinesFacade) {
      if ((oldInterface != null)) {
        _routinesFacade.deleteJavaImplementedInterface(oldInterface, uClass);
      }
    }
  }
  
  public ChangeJavaImplementedInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface uInterface, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.ChangeJavaImplementedInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.uInterface = uInterface;this.oldInterface = oldInterface;this.uClass = uClass;
  }
  
  private Interface uInterface;
  
  private Interface oldInterface;
  
  private org.eclipse.uml2.uml.Class uClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeJavaImplementedInterfaceRoutine with input:");
    getLogger().debug("   uInterface: " + this.uInterface);
    getLogger().debug("   oldInterface: " + this.oldInterface);
    getLogger().debug("   uClass: " + this.uClass);
    
    org.emftext.language.java.classifiers.Class jClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClass(uInterface, oldInterface, uClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jClass == null) {
    	return false;
    }
    registerObjectUnderModification(jClass);
    org.emftext.language.java.classifiers.Interface jInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJInterface(uInterface, oldInterface, uClass, jClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jInterface == null) {
    	return false;
    }
    registerObjectUnderModification(jInterface);
    userExecution.callRoutine1(uInterface, oldInterface, uClass, jClass, jInterface, actionsFacade);
    
    // val updatedElement userExecution.getElement1(uInterface, oldInterface, uClass, jClass, jInterface);
    userExecution.update0Element(uInterface, oldInterface, uClass, jClass, jInterface);
    
    postprocessElements();
    
    return true;
  }
}
