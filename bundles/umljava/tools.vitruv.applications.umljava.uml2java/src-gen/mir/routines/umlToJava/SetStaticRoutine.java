package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Feature;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.Static;
import org.emftext.language.java.modifiers.impl.ModifiersFactoryImpl;
import tools.vitruv.applications.umljava.util.JavaUtil;
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
      boolean _isStatic = uFeat.isStatic();
      JavaUtil.setJavaModifier(jMod, staticMod, _isStatic);
    }
  }
  
  public SetStaticRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Feature uFeat) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.SetStaticRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uFeat = uFeat;
  }
  
  private Feature uFeat;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetStaticRoutine with input:");
    getLogger().debug("   Feature: " + this.uFeat);
    
    AnnotableAndModifiable jMod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJMod(uFeat), // correspondence source supplier
    	AnnotableAndModifiable.class,
    	(AnnotableAndModifiable _element) -> true, // correspondence precondition checker
    	null);
    if (jMod == null) {
    	return;
    }
    initializeRetrieveElementState(jMod);
    Static staticMod = ModifiersFactoryImpl.eINSTANCE.createStatic();
    initializeCreateElementState(staticMod);
    
    // val updatedElement userExecution.getElement1(uFeat, jMod, staticMod);
    userExecution.update0Element(uFeat, jMod, staticMod);
    
    postprocessElementStates();
  }
}
