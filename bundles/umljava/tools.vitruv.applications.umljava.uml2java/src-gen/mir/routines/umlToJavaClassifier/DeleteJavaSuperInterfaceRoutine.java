package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import java.util.Iterator;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaSuperInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteJavaSuperInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface superUMLInterface, final Interface uI, final org.emftext.language.java.classifiers.Interface jI, final org.emftext.language.java.classifiers.Interface javaSuperInterface) {
      return jI;
    }
    
    public void update0Element(final Interface superUMLInterface, final Interface uI, final org.emftext.language.java.classifiers.Interface jI, final org.emftext.language.java.classifiers.Interface javaSuperInterface) {
      EList<TypeReference> _extends = jI.getExtends();
      Iterator<TypeReference> _iterator = _extends.iterator();
      JavaContainerAndClassifierUtil.removeClassifierFromIterator(_iterator, javaSuperInterface);
    }
    
    public EObject getCorrepondenceSourceJI(final Interface superUMLInterface, final Interface uI) {
      return uI;
    }
    
    public EObject getCorrepondenceSourceJavaSuperInterface(final Interface superUMLInterface, final Interface uI, final org.emftext.language.java.classifiers.Interface jI) {
      return superUMLInterface;
    }
  }
  
  public DeleteJavaSuperInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface superUMLInterface, final Interface uI) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.DeleteJavaSuperInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.superUMLInterface = superUMLInterface;this.uI = uI;
  }
  
  private Interface superUMLInterface;
  
  private Interface uI;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaSuperInterfaceRoutine with input:");
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
    org.emftext.language.java.classifiers.Interface javaSuperInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaSuperInterface(superUMLInterface, uI, jI), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (javaSuperInterface == null) {
    	return;
    }
    registerObjectUnderModification(javaSuperInterface);
    // val updatedElement userExecution.getElement1(superUMLInterface, uI, jI, javaSuperInterface);
    userExecution.update0Element(superUMLInterface, uI, jI, javaSuperInterface);
    
    postprocessElements();
  }
}
