package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private RemoveProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceRequiredInterfaceImport(final ProvidedRole providedRole) {
      return providedRole;
    }
    
    public EObject getElement1(final ProvidedRole providedRole, final ClassifierImport requiredInterfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      return requiredInterfaceImport;
    }
    
    public EObject getElement2(final ProvidedRole providedRole, final ClassifierImport requiredInterfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      return namespaceClassifierReference;
    }
    
    public EObject getCorrepondenceSourceNamespaceClassifierReference(final ProvidedRole providedRole, final ClassifierImport requiredInterfaceImport) {
      return providedRole;
    }
  }
  
  public RemoveProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ProvidedRole providedRole) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.RemoveProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.providedRole = providedRole;
  }
  
  private ProvidedRole providedRole;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveProvidedRoleRoutine with input:");
    getLogger().debug("   providedRole: " + this.providedRole);
    
    org.emftext.language.java.imports.ClassifierImport requiredInterfaceImport = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRequiredInterfaceImport(providedRole), // correspondence source supplier
    	org.emftext.language.java.imports.ClassifierImport.class,
    	(org.emftext.language.java.imports.ClassifierImport _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (requiredInterfaceImport == null) {
    	return false;
    }
    registerObjectUnderModification(requiredInterfaceImport);
    org.emftext.language.java.types.NamespaceClassifierReference namespaceClassifierReference = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceNamespaceClassifierReference(providedRole, requiredInterfaceImport), // correspondence source supplier
    	org.emftext.language.java.types.NamespaceClassifierReference.class,
    	(org.emftext.language.java.types.NamespaceClassifierReference _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (namespaceClassifierReference == null) {
    	return false;
    }
    registerObjectUnderModification(namespaceClassifierReference);
    deleteObject(userExecution.getElement1(providedRole, requiredInterfaceImport, namespaceClassifierReference));
    
    deleteObject(userExecution.getElement2(providedRole, requiredInterfaceImport, namespaceClassifierReference));
    
    postprocessElements();
    
    return true;
  }
}
