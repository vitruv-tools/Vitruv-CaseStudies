package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaInterfaceImplementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaInterfaceImplementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface uI, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Interface jI) {
      return jClass;
    }
    
    public EObject getCorrepondenceSourceJClass(final Interface uI, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public void update0Element(final Interface uI, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Interface jI) {
      EList<TypeReference> _implements = jClass.getImplements();
      TypeReference _createTypeReference = UmlToJavaHelper.createTypeReference(null, jI);
      _implements.add(_createTypeReference);
    }
    
    public EObject getCorrepondenceSourceJI(final Interface uI, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass) {
      return uI;
    }
  }
  
  public CreateJavaInterfaceImplementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface uI, final org.eclipse.uml2.uml.Class uClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaInterfaceImplementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uI = uI;this.uClass = uClass;
  }
  
  private Interface uI;
  
  private org.eclipse.uml2.uml.Class uClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaInterfaceImplementRoutine with input:");
    getLogger().debug("   Interface: " + this.uI);
    getLogger().debug("   Class: " + this.uClass);
    
    org.emftext.language.java.classifiers.Class jClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClass(uI, uClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (jClass == null) {
    	return;
    }
    initializeRetrieveElementState(jClass);
    org.emftext.language.java.classifiers.Interface jI = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJI(uI, uClass, jClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (jI == null) {
    	return;
    }
    initializeRetrieveElementState(jI);
    // val updatedElement userExecution.getElement1(uI, uClass, jClass, jI);
    userExecution.update0Element(uI, uClass, jClass, jI);
    
    postprocessElementStates();
  }
}