package mir.routines.java2PcmMethod;

import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void renameUmlNamedElement(final NamedElement jElement) {
    mir.routines.java2PcmMethod.RenameUmlNamedElementRoutine effect = new mir.routines.java2PcmMethod.RenameUmlNamedElementRoutine(this.executionState, calledBy,
    	jElement);
    effect.applyRoutine();
  }
  
  public void createPCMSignature(final InterfaceMethod method) {
    mir.routines.java2PcmMethod.CreatePCMSignatureRoutine effect = new mir.routines.java2PcmMethod.CreatePCMSignatureRoutine(this.executionState, calledBy,
    	method);
    effect.applyRoutine();
  }
  
  public void changeUmlReturnType(final Method jMeth, final TypeReference jType) {
    mir.routines.java2PcmMethod.ChangeUmlReturnTypeRoutine effect = new mir.routines.java2PcmMethod.ChangeUmlReturnTypeRoutine(this.executionState, calledBy,
    	jMeth, jType);
    effect.applyRoutine();
  }
}
