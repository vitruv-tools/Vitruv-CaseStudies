package mir.routines.umlToJava;

import java.io.IOException;
import java.util.ListIterator;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
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
      final ListIterator<TypeReference> iter = _extends.listIterator();
      while (iter.hasNext()) {
        {
          TypeReference _next = iter.next();
          EList<ClassifierReference> _classifierReferences = ((NamespaceClassifierReference) _next).getClassifierReferences();
          ClassifierReference _head = IterableExtensions.<ClassifierReference>head(_classifierReferences);
          final Classifier type = _head.getTarget();
          String _name = javaSuperInterface.getName();
          String _name_1 = type.getName();
          boolean _equals = _name.equals(_name_1);
          if (_equals) {
            iter.remove();
          }
        }
      }
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
    this.userExecution = new mir.routines.umlToJava.DeleteJavaSuperInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
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
    initializeRetrieveElementState(jI);
    org.emftext.language.java.classifiers.Interface javaSuperInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaSuperInterface(superUMLInterface, uI, jI), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (javaSuperInterface == null) {
    	return;
    }
    initializeRetrieveElementState(javaSuperInterface);
    // val updatedElement userExecution.getElement1(superUMLInterface, uI, jI, javaSuperInterface);
    userExecution.update0Element(superUMLInterface, uI, jI, javaSuperInterface);
    
    postprocessElementStates();
  }
}
