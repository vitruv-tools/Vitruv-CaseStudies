package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
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
    
    public EObject getElement1(final Interface uI, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Interface jI) {
      return jClass;
    }
    
    public EObject getCorrepondenceSourceJClass(final Interface uI, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public void update0Element(final Interface uI, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Interface jI) {
      EList<TypeReference> _implements = jClass.getImplements();
      TypeReference _createTypeReference = UmlToJavaHelper.createTypeReference(null, jI);
      _implements.add(_createTypeReference);
    }
    
    public EObject getCorrepondenceSourceJI(final Interface uI, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass) {
      return uI;
    }
    
    public void callRoutine1(final Interface uI, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Interface jI, @Extension final RoutinesFacade _routinesFacade) {
      if ((oldInterface != null)) {
        _routinesFacade.deleteJavaImplementedInterface(oldInterface, uClass);
      }
    }
  }
  
  public ChangeJavaImplementedInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface uI, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.ChangeJavaImplementedInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uI = uI;this.oldInterface = oldInterface;this.uClass = uClass;
  }
  
  private Interface uI;
  
  private Interface oldInterface;
  
  private org.eclipse.uml2.uml.Class uClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeJavaImplementedInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.uI);
    getLogger().debug("   Interface: " + this.oldInterface);
    getLogger().debug("   Class: " + this.uClass);
    
    org.emftext.language.java.classifiers.Class jClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClass(uI, oldInterface, uClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (jClass == null) {
    	return;
    }
    registerObjectUnderModification(jClass);
    org.emftext.language.java.classifiers.Interface jI = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJI(uI, oldInterface, uClass, jClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (jI == null) {
    	return;
    }
    registerObjectUnderModification(jI);
    userExecution.callRoutine1(uI, oldInterface, uClass, jClass, jI, actionsFacade);
    
    // val updatedElement userExecution.getElement1(uI, oldInterface, uClass, jClass, jI);
    userExecution.update0Element(uI, oldInterface, uClass, jClass, jI);
    
    postprocessElements();
  }
}
