package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.domains.java.util.JavaModificationUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private AddProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport) {
      return interfaceImport;
    }
    
    public void update0Element(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      javaClass.getImplements().add(namespaceClassifierReference);
    }
    
    public EObject getCorrepondenceSourceJavaClass(final OperationProvidedRole providedRole, final Interface operationProvidingInterface) {
      InterfaceProvidingEntity _providingEntity_ProvidedRole = providedRole.getProvidingEntity_ProvidedRole();
      return _providingEntity_ProvidedRole;
    }
    
    public EObject getElement4(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      return providedRole;
    }
    
    public EObject getElement5(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      return javaClass;
    }
    
    public EObject getElement2(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport) {
      return providedRole;
    }
    
    public EObject getElement3(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      return namespaceClassifierReference;
    }
    
    public void updateNamespaceClassifierReferenceElement(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      JavaModificationUtil.createNamespaceClassifierReference(namespaceClassifierReference, operationProvidingInterface);
    }
    
    public EObject getCorrepondenceSourceOperationProvidingInterface(final OperationProvidedRole providedRole) {
      OperationInterface _providedInterface__OperationProvidedRole = providedRole.getProvidedInterface__OperationProvidedRole();
      return _providedInterface__OperationProvidedRole;
    }
    
    public void updateInterfaceImportElement(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport) {
      JavaModificationUtil.addImportToCompilationUnitOfClassifier(interfaceImport, javaClass, operationProvidingInterface);
    }
  }
  
  public AddProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole providedRole) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.AddProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.providedRole = providedRole;
  }
  
  private OperationProvidedRole providedRole;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddProvidedRoleRoutine with input:");
    getLogger().debug("   providedRole: " + this.providedRole);
    
    org.emftext.language.java.classifiers.Interface operationProvidingInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationProvidingInterface(providedRole), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (operationProvidingInterface == null) {
    	return false;
    }
    registerObjectUnderModification(operationProvidingInterface);
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClass(providedRole, operationProvidingInterface), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaClass == null) {
    	return false;
    }
    registerObjectUnderModification(javaClass);
    org.emftext.language.java.imports.ClassifierImport interfaceImport = org.emftext.language.java.imports.impl.ImportsFactoryImpl.eINSTANCE.createClassifierImport();
    notifyObjectCreated(interfaceImport);
    userExecution.updateInterfaceImportElement(providedRole, operationProvidingInterface, javaClass, interfaceImport);
    
    addCorrespondenceBetween(userExecution.getElement1(providedRole, operationProvidingInterface, javaClass, interfaceImport), userExecution.getElement2(providedRole, operationProvidingInterface, javaClass, interfaceImport), "");
    
    org.emftext.language.java.types.NamespaceClassifierReference namespaceClassifierReference = org.emftext.language.java.types.impl.TypesFactoryImpl.eINSTANCE.createNamespaceClassifierReference();
    notifyObjectCreated(namespaceClassifierReference);
    userExecution.updateNamespaceClassifierReferenceElement(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference);
    
    addCorrespondenceBetween(userExecution.getElement3(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference), userExecution.getElement4(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference), "");
    
    // val updatedElement userExecution.getElement5(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference);
    userExecution.update0Element(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference);
    
    postprocessElements();
    
    return true;
  }
}
