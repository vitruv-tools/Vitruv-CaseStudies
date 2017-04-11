package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl;
import org.emftext.language.java.members.EnumConstant;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaEnumRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaEnumRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateJEnumElement(final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum) {
      jEnum.setName(uEnum.getName());
      EList<EnumerationLiteral> _ownedLiterals = uEnum.getOwnedLiterals();
      for (final EnumerationLiteral enumLit : _ownedLiterals) {
        EList<EnumConstant> _constants = jEnum.getConstants();
        EnumConstant _createJavaEnumConstant = UmlToJavaHelper.createJavaEnumConstant(enumLit);
        _constants.add(_createJavaEnumConstant);
      }
    }
    
    public EObject getElement1(final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum) {
      return uEnum;
    }
    
    public EObject getElement2(final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum) {
      return jEnum;
    }
    
    public void callRoutine1(final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaCompilationUnit(uEnum, jEnum, uEnum.getNamespace());
    }
  }
  
  public CreateJavaEnumRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Enumeration uEnum) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaEnumRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uEnum = uEnum;
  }
  
  private Enumeration uEnum;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaEnumRoutine with input:");
    getLogger().debug("   Enumeration: " + this.uEnum);
    
    org.emftext.language.java.classifiers.Enumeration jEnum = ClassifiersFactoryImpl.eINSTANCE.createEnumeration();
    userExecution.updateJEnumElement(uEnum, jEnum);
    
    addCorrespondenceBetween(userExecution.getElement1(uEnum, jEnum), userExecution.getElement2(uEnum, jEnum), "");
    
    userExecution.callRoutine1(uEnum, jEnum, actionsFacade);
    
    postprocessElements();
  }
}
