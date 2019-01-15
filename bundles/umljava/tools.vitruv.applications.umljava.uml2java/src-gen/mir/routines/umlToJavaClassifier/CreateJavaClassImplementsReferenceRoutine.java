package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaClassImplementsReferenceRoutine extends AbstractRepairRoutineRealization {
  private CreateJavaClassImplementsReferenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJClass(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public EObject getCorrepondenceSource1(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final Interface jInterface) {
      return uRealization;
    }
    
    public void executeAction1(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final Interface jInterface, @Extension final RoutinesFacade _routinesFacade) {
      TypeReference typeReference = UmlJavaTypePropagationHelper.createTypeReference(uRealization.getContract(), Optional.<Interface>of(jInterface), null, this.userInteractor);
      UmlJavaTypePropagationHelper.addJavaImport(jClass.getContainingCompilationUnit(), typeReference);
      EList<TypeReference> _implements = jClass.getImplements();
      _implements.add(typeReference);
      _routinesFacade.addImplementsCorrespondence(uRealization, typeReference);
    }
    
    public EObject getCorrepondenceSourceJInterface(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass) {
      org.eclipse.uml2.uml.Interface _contract = uRealization.getContract();
      return _contract;
    }
  }
  
  public CreateJavaClassImplementsReferenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.CreateJavaClassImplementsReferenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.uRealization = uRealization;this.uClass = uClass;
  }
  
  private InterfaceRealization uRealization;
  
  private org.eclipse.uml2.uml.Class uClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaClassImplementsReferenceRoutine with input:");
    getLogger().debug("   uRealization: " + this.uRealization);
    getLogger().debug("   uClass: " + this.uClass);
    
    org.emftext.language.java.classifiers.Class jClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClass(uRealization, uClass), // correspondence source supplier
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
    	userExecution.getCorrepondenceSourceJInterface(uRealization, uClass, jClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jInterface == null) {
    	return false;
    }
    registerObjectUnderModification(jInterface);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(uRealization, uClass, jClass, jInterface), // correspondence source supplier
    	org.emftext.language.java.types.TypeReference.class,
    	(org.emftext.language.java.types.TypeReference _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    userExecution.executeAction1(uRealization, uClass, jClass, jInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
