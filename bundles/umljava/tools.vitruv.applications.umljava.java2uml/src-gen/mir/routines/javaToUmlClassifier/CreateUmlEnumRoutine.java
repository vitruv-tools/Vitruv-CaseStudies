package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlEnumRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlEnumRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Enumeration jEnum, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Enumeration uEnum) {
      return uEnum;
    }
    
    public void updateUEnumElement(final Enumeration jEnum, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Enumeration uEnum) {
      uEnum.setName(jEnum.getName());
    }
    
    public EObject getElement4(final Enumeration jEnum, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Enumeration uEnum) {
      return jCompUnit;
    }
    
    public EObject getElement2(final Enumeration jEnum, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Enumeration uEnum) {
      return jEnum;
    }
    
    public EObject getElement3(final Enumeration jEnum, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Enumeration uEnum) {
      return uEnum;
    }
    
    public void callRoutine1(final Enumeration jEnum, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Enumeration uEnum, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addUmlElementToModelOrPackage(jCompUnit, uEnum);
    }
  }
  
  public CreateUmlEnumRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Enumeration jEnum, final CompilationUnit jCompUnit) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.CreateUmlEnumRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jEnum = jEnum;this.jCompUnit = jCompUnit;
  }
  
  private Enumeration jEnum;
  
  private CompilationUnit jCompUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlEnumRoutine with input:");
    getLogger().debug("   jEnum: " + this.jEnum);
    getLogger().debug("   jCompUnit: " + this.jCompUnit);
    
    org.eclipse.uml2.uml.Enumeration uEnum = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createEnumeration();
    notifyObjectCreated(uEnum);
    userExecution.updateUEnumElement(jEnum, jCompUnit, uEnum);
    
    userExecution.callRoutine1(jEnum, jCompUnit, uEnum, actionsFacade);
    
    addCorrespondenceBetween(userExecution.getElement1(jEnum, jCompUnit, uEnum), userExecution.getElement2(jEnum, jCompUnit, uEnum), "");
    
    addCorrespondenceBetween(userExecution.getElement3(jEnum, jCompUnit, uEnum), userExecution.getElement4(jEnum, jCompUnit, uEnum), "");
    
    postprocessElements();
    
    return true;
  }
}
