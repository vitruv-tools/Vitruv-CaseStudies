package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.impl.ContainersFactoryImpl;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface javaInterface) {
      return umlInterface;
    }
    
    public EObject getElement4(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface javaInterface, final CompilationUnit javaCompilationUnit) {
      return javaCompilationUnit;
    }
    
    public void updateJavaInterfaceElement(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface javaInterface) {
      String _name = umlInterface.getName();
      javaInterface.setName(_name);
    }
    
    public EObject getElement2(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface javaInterface) {
      return javaInterface;
    }
    
    public EObject getElement3(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface javaInterface, final CompilationUnit javaCompilationUnit) {
      return umlInterface;
    }
    
    public void updateJavaCompilationUnitElement(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface javaInterface, final CompilationUnit javaCompilationUnit) {
      String _name = umlInterface.getName();
      javaCompilationUnit.setName(_name);
      EList<ConcreteClassifier> _classifiers = javaCompilationUnit.getClassifiers();
      _classifiers.add(javaInterface);
      String _buildJavaFilePath = JavaPersistenceHelper.buildJavaFilePath(javaCompilationUnit);
      this.persistProjectRelative(umlInterface, javaCompilationUnit, _buildJavaFilePath);
    }
  }
  
  public CreateJavaInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlInterface = umlInterface;
  }
  
  private Interface umlInterface;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.umlInterface);
    
    org.emftext.language.java.classifiers.Interface javaInterface = ClassifiersFactoryImpl.eINSTANCE.createInterface();
    initializeCreateElementState(javaInterface);
    userExecution.updateJavaInterfaceElement(umlInterface, javaInterface);
    
    addCorrespondenceBetween(userExecution.getElement1(umlInterface, javaInterface), userExecution.getElement2(umlInterface, javaInterface), "");
    
    CompilationUnit javaCompilationUnit = ContainersFactoryImpl.eINSTANCE.createCompilationUnit();
    initializeCreateElementState(javaCompilationUnit);
    userExecution.updateJavaCompilationUnitElement(umlInterface, javaInterface, javaCompilationUnit);
    
    addCorrespondenceBetween(userExecution.getElement3(umlInterface, javaInterface, javaCompilationUnit), userExecution.getElement4(umlInterface, javaInterface, javaCompilationUnit), "");
    
    postprocessElementStates();
  }
}
