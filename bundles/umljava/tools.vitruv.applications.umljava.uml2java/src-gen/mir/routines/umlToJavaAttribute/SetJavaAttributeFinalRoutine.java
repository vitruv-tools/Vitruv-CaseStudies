package mir.routines.umlToJavaAttribute;

import java.io.IOException;
import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.modifiers.Final;
import org.emftext.language.java.modifiers.impl.ModifiersFactoryImpl;
import tools.vitruv.applications.umljava.util.JavaUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetJavaAttributeFinalRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetJavaAttributeFinalRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlAttr, final Field jAttr, final Final finalMod) {
      return jAttr;
    }
    
    public void update0Element(final Property umlAttr, final Field jAttr, final Final finalMod) {
      JavaUtil.setJavaModifier(jAttr, finalMod, umlAttr.isReadOnly());
    }
    
    public EObject getCorrepondenceSourceJAttr(final Property umlAttr) {
      return umlAttr;
    }
  }
  
  public SetJavaAttributeFinalRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlAttr) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaAttribute.SetJavaAttributeFinalRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaAttribute.RoutinesFacade(getExecutionState(), this);
    this.umlAttr = umlAttr;
  }
  
  private Property umlAttr;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetJavaAttributeFinalRoutine with input:");
    getLogger().debug("   Property: " + this.umlAttr);
    
    Field jAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJAttr(umlAttr), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (jAttr == null) {
    	return;
    }
    registerObjectUnderModification(jAttr);
    Final finalMod = ModifiersFactoryImpl.eINSTANCE.createFinal();
    
    // val updatedElement userExecution.getElement1(umlAttr, jAttr, finalMod);
    userExecution.update0Element(umlAttr, jAttr, finalMod);
    
    postprocessElements();
  }
}
