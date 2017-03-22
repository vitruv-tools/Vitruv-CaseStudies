package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeJavaSuperClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeJavaSuperClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class superJavaClass) {
      return jClass;
    }
    
    public EObject getCorrepondenceSourceJClass(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class superJavaClass) {
      TypeReference _createTypeReference = UmlToJavaHelper.createTypeReference(null, superJavaClass);
      jClass.setExtends(_createTypeReference);
    }
    
    public EObject getCorrepondenceSourceSuperJavaClass(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass) {
      return superUMLClass;
    }
  }
  
  public ChangeJavaSuperClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.ChangeJavaSuperClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.superUMLClass = superUMLClass;this.uClass = uClass;
  }
  
  private org.eclipse.uml2.uml.Class superUMLClass;
  
  private org.eclipse.uml2.uml.Class uClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeJavaSuperClassRoutine with input:");
    getLogger().debug("   Class: " + this.superUMLClass);
    getLogger().debug("   Class: " + this.uClass);
    
    org.emftext.language.java.classifiers.Class jClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClass(superUMLClass, uClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (jClass == null) {
    	return;
    }
    initializeRetrieveElementState(jClass);
    org.emftext.language.java.classifiers.Class superJavaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceSuperJavaClass(superUMLClass, uClass, jClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (superJavaClass == null) {
    	return;
    }
    initializeRetrieveElementState(superJavaClass);
    // val updatedElement userExecution.getElement1(superUMLClass, uClass, jClass, superJavaClass);
    userExecution.update0Element(superUMLClass, uClass, jClass, superJavaClass);
    
    postprocessElementStates();
  }
}
