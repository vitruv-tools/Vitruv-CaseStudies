package mir.routines.umlToJava;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.emftext.language.java.members.EnumConstant;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaEnumConstantRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaEnumConstantRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final EnumerationLiteral uLiteral, final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum, final EnumConstant jConstant) {
      return jConstant;
    }
    
    public void update0Element(final EnumerationLiteral uLiteral, final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum, final EnumConstant jConstant) {
      EList<EnumConstant> _constants = jEnum.getConstants();
      _constants.add(jConstant);
    }
    
    public EObject getElement2(final EnumerationLiteral uLiteral, final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum, final EnumConstant jConstant) {
      return uLiteral;
    }
    
    public EObject getElement3(final EnumerationLiteral uLiteral, final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum, final EnumConstant jConstant) {
      return jEnum;
    }
    
    public void updateJConstantElement(final EnumerationLiteral uLiteral, final Enumeration uEnum, final org.emftext.language.java.classifiers.Enumeration jEnum, final EnumConstant jConstant) {
      String _name = uLiteral.getName();
      boolean _equals = Objects.equal(_name, null);
      if (_equals) {
        jConstant.setName("DefaultEnumConstantName");
      } else {
        jConstant.setName(uLiteral.getName());
      }
    }
    
    public EObject getCorrepondenceSourceJEnum(final EnumerationLiteral uLiteral, final Enumeration uEnum) {
      return uEnum;
    }
  }
  
  public CreateJavaEnumConstantRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EnumerationLiteral uLiteral, final Enumeration uEnum) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaEnumConstantRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uLiteral = uLiteral;this.uEnum = uEnum;
  }
  
  private EnumerationLiteral uLiteral;
  
  private Enumeration uEnum;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaEnumConstantRoutine with input:");
    getLogger().debug("   EnumerationLiteral: " + this.uLiteral);
    getLogger().debug("   Enumeration: " + this.uEnum);
    
    org.emftext.language.java.classifiers.Enumeration jEnum = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJEnum(uLiteral, uEnum), // correspondence source supplier
    	org.emftext.language.java.classifiers.Enumeration.class,
    	(org.emftext.language.java.classifiers.Enumeration _element) -> true, // correspondence precondition checker
    	null);
    if (jEnum == null) {
    	return;
    }
    registerObjectUnderModification(jEnum);
    EnumConstant jConstant = MembersFactoryImpl.eINSTANCE.createEnumConstant();
    userExecution.updateJConstantElement(uLiteral, uEnum, jEnum, jConstant);
    
    addCorrespondenceBetween(userExecution.getElement1(uLiteral, uEnum, jEnum, jConstant), userExecution.getElement2(uLiteral, uEnum, jEnum, jConstant), "");
    
    // val updatedElement userExecution.getElement3(uLiteral, uEnum, jEnum, jConstant);
    userExecution.update0Element(uLiteral, uEnum, jEnum, jConstant);
    
    postprocessElements();
  }
}