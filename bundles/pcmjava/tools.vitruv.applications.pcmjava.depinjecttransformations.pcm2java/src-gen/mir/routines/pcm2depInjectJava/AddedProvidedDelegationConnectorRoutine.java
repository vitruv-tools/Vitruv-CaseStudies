package mir.routines.pcm2depInjectJava;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import mir.routines.pcm2depInjectJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmjava.util.PCMJaMoPPUtils;
import tools.vitruv.applications.pcmjava.util.pcm2java.PCM2JaMoPPUtils;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import tools.vitruv.framework.util.bridges.CollectionBridge;

@SuppressWarnings("all")
public class AddedProvidedDelegationConnectorRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddedProvidedDelegationConnectorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceConfigureMethod(final ProvidedDelegationConnector providedDelegationConnector, final ComposedStructure pcmSystem) {
      final AssemblyContext assemblyContext = providedDelegationConnector.getAssemblyContext_ProvidedDelegationConnector();
      return assemblyContext;
    }
    
    public void callRoutine1(final ProvidedDelegationConnector providedDelegationConnector, final ComposedStructure pcmSystem, final ClassMethod configureMethod, @Extension final RoutinesFacade _routinesFacade) {
      OperationProvidedRole _innerProvidedRole_ProvidedDelegationConnector = providedDelegationConnector.getInnerProvidedRole_ProvidedDelegationConnector();
      final OperationInterface opInterface = _innerProvidedRole_ProvidedDelegationConnector.getProvidedInterface__OperationProvidedRole();
      ConcreteClassifier _containingConcreteClassifier = configureMethod.getContainingConcreteClassifier();
      final org.emftext.language.java.classifiers.Class systemClass = ((org.emftext.language.java.classifiers.Class) _containingConcreteClassifier);
      Set<Interface> _correspondingEObjectsByType = CorrespondenceModelUtil.<Interface, Correspondence>getCorrespondingEObjectsByType(this.correspondenceModel, opInterface, Interface.class);
      final Interface jaMoPPInterface = CollectionBridge.<Interface>claimOne(_correspondingEObjectsByType);
      final NamespaceClassifierReference namespaceClassifierRef = PCM2JaMoPPUtils.createNamespaceClassifierReference(jaMoPPInterface);
      EList<TypeReference> _implements = systemClass.getImplements();
      for (final TypeReference impl : _implements) {
        boolean _hasSameTargetReference = PCMJaMoPPUtils.hasSameTargetReference(namespaceClassifierRef, impl);
        if (_hasSameTargetReference) {
          return;
        }
      }
      EList<TypeReference> _implements_1 = systemClass.getImplements();
      _implements_1.add(namespaceClassifierRef);
      final Import classifierImport = PCM2JaMoPPUtils.addImportToCompilationUnitOfClassifier(systemClass, jaMoPPInterface);
      List<EObject> _list = CollectionBridge.<EObject>toList(pcmSystem);
      this.correspondenceModel.createAndAddCorrespondence(_list, Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(namespaceClassifierRef, classifierImport)));
    }
  }
  
  public AddedProvidedDelegationConnectorRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ProvidedDelegationConnector providedDelegationConnector, final ComposedStructure pcmSystem) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2depInjectJava.AddedProvidedDelegationConnectorRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(getExecutionState(), this);
    this.providedDelegationConnector = providedDelegationConnector;this.pcmSystem = pcmSystem;
  }
  
  private ProvidedDelegationConnector providedDelegationConnector;
  
  private ComposedStructure pcmSystem;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddedProvidedDelegationConnectorRoutine with input:");
    getLogger().debug("   ProvidedDelegationConnector: " + this.providedDelegationConnector);
    getLogger().debug("   ComposedStructure: " + this.pcmSystem);
    
    ClassMethod configureMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceConfigureMethod(providedDelegationConnector, pcmSystem), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    if (configureMethod == null) {
    	return;
    }
    initializeRetrieveElementState(configureMethod);
    userExecution.callRoutine1(providedDelegationConnector, pcmSystem, configureMethod, actionsFacade);
    
    postprocessElementStates();
  }
}
