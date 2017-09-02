package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Feature;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.Static;
import tools.vitruv.applications.umljava.util.java.JavaModifierUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetStaticRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetStaticRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJMod(final Feature uFeat) {
      return uFeat;
    }
    
    public EObject getElement1(final Feature uFeat, final AnnotableAndModifiable jMod, final Static staticMod) {
      return jMod;
    }
    
    public void update0Element(final Feature uFeat, final AnnotableAndModifiable jMod, final Static staticMod) {
      JavaModifierUtil.setStatic(jMod, uFeat.isStatic());
    }
  }
  
  public SetStaticRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Feature uFeat) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.SetStaticRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uFeat = uFeat;
  }
  
  private Feature uFeat;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetStaticRoutine with input:");
    getLogger().debug("   uFeat: " + this.uFeat);
    
    org.emftext.language.java.modifiers.AnnotableAndModifiable jMod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJMod(uFeat), // correspondence source supplier
    	org.emftext.language.java.modifiers.AnnotableAndModifiable.class,
    	(org.emftext.language.java.modifiers.AnnotableAndModifiable _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jMod == null) {
    	return false;
    }
    registerObjectUnderModification(jMod);
    org.emftext.language.java.modifiers.Static staticMod = org.emftext.language.java.modifiers.impl.ModifiersFactoryImpl.eINSTANCE.createStatic();
    notifyObjectCreated(staticMod);
    
    // val updatedElement userExecution.getElement1(uFeat, jMod, staticMod);
    userExecution.update0Element(uFeat, jMod, staticMod);
    
    postprocessElements();
    
    return true;
  }
}
