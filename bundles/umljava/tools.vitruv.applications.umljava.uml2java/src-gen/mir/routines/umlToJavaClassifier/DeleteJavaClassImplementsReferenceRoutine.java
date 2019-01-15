package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaClassImplementsReferenceRoutine extends AbstractRepairRoutineRealization {
  private DeleteJavaClassImplementsReferenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference) {
      return uRealization;
    }
    
    public EObject getCorrepondenceSourceJClass(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public void update0Element(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference) {
      EList<NamedElement> _clients = uRealization.getClients();
      _clients.remove(uClass);
    }
    
    public EObject getElement2(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference) {
      return jReference;
    }
    
    public EObject getCorrepondenceSourceJReference(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass) {
      return uRealization;
    }
  }
  
  public DeleteJavaClassImplementsReferenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.DeleteJavaClassImplementsReferenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.uRealization = uRealization;this.uClass = uClass;
  }
  
  private InterfaceRealization uRealization;
  
  private org.eclipse.uml2.uml.Class uClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaClassImplementsReferenceRoutine with input:");
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
    org.emftext.language.java.types.TypeReference jReference = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJReference(uRealization, uClass, jClass), // correspondence source supplier
    	org.emftext.language.java.types.TypeReference.class,
    	(org.emftext.language.java.types.TypeReference _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jReference == null) {
    	return false;
    }
    registerObjectUnderModification(jReference);
    // val updatedElement userExecution.getElement1(uRealization, uClass, jClass, jReference);
    userExecution.update0Element(uRealization, uClass, jClass, jReference);
    
    deleteObject(userExecution.getElement2(uRealization, uClass, jClass, jReference));
    
    postprocessElements();
    
    return true;
  }
}
