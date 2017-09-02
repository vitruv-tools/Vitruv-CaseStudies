package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.members.EnumConstant;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlEnumLiteralRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlEnumLiteralRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Enumeration jEnum, final EnumConstant jConstant, final org.eclipse.uml2.uml.Enumeration uEnum, final EnumerationLiteral uLiteral) {
      return uLiteral;
    }
    
    public void update0Element(final Enumeration jEnum, final EnumConstant jConstant, final org.eclipse.uml2.uml.Enumeration uEnum, final EnumerationLiteral uLiteral) {
      EList<EnumerationLiteral> _ownedLiterals = uEnum.getOwnedLiterals();
      _ownedLiterals.add(uLiteral);
    }
    
    public EObject getCorrepondenceSourceUEnum(final Enumeration jEnum, final EnumConstant jConstant) {
      return jEnum;
    }
    
    public EObject getElement2(final Enumeration jEnum, final EnumConstant jConstant, final org.eclipse.uml2.uml.Enumeration uEnum, final EnumerationLiteral uLiteral) {
      return jConstant;
    }
    
    public EObject getElement3(final Enumeration jEnum, final EnumConstant jConstant, final org.eclipse.uml2.uml.Enumeration uEnum, final EnumerationLiteral uLiteral) {
      return uEnum;
    }
    
    public void updateULiteralElement(final Enumeration jEnum, final EnumConstant jConstant, final org.eclipse.uml2.uml.Enumeration uEnum, final EnumerationLiteral uLiteral) {
      uLiteral.setName(jConstant.getName());
    }
  }
  
  public CreateUmlEnumLiteralRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Enumeration jEnum, final EnumConstant jConstant) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.CreateUmlEnumLiteralRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jEnum = jEnum;this.jConstant = jConstant;
  }
  
  private Enumeration jEnum;
  
  private EnumConstant jConstant;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlEnumLiteralRoutine with input:");
    getLogger().debug("   jEnum: " + this.jEnum);
    getLogger().debug("   jConstant: " + this.jConstant);
    
    org.eclipse.uml2.uml.Enumeration uEnum = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUEnum(jEnum, jConstant), // correspondence source supplier
    	org.eclipse.uml2.uml.Enumeration.class,
    	(org.eclipse.uml2.uml.Enumeration _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uEnum == null) {
    	return false;
    }
    registerObjectUnderModification(uEnum);
    org.eclipse.uml2.uml.EnumerationLiteral uLiteral = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createEnumerationLiteral();
    notifyObjectCreated(uLiteral);
    userExecution.updateULiteralElement(jEnum, jConstant, uEnum, uLiteral);
    
    addCorrespondenceBetween(userExecution.getElement1(jEnum, jConstant, uEnum, uLiteral), userExecution.getElement2(jEnum, jConstant, uEnum, uLiteral), "");
    
    // val updatedElement userExecution.getElement3(jEnum, jConstant, uEnum, uLiteral);
    userExecution.update0Element(jEnum, jConstant, uEnum, uLiteral);
    
    postprocessElements();
    
    return true;
  }
}
