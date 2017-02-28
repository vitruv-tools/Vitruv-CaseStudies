package mir.routines.pcm2javaDepInject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import mir.routines.pcm2javaDepInject.RoutinesFacade;
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
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmjava.util.PCMJaMoPPUtils;
import tools.vitruv.applications.pcmjava.util.pcm2java.PCM2JaMoPPUtils;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import tools.vitruv.framework.util.bridges.CollectionBridge;

@SuppressWarnings("all")
public class AddedProvidedDelegationConnectorEffect extends AbstractEffectRealization {
  public AddedProvidedDelegationConnectorEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<ComposedStructure, Connector> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<ComposedStructure, Connector> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddedProvidedDelegationConnectorEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    ClassMethod configureMethod = getCorrespondingElement(
    	getCorrepondenceSourceConfigureMethod(change), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    if (configureMethod == null) {
    	return;
    }
    initializeRetrieveElementState(configureMethod);
    
    preprocessElementStates();
    new mir.routines.pcm2javaDepInject.AddedProvidedDelegationConnectorEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, configureMethod);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceConfigureMethod(final InsertEReference<ComposedStructure, Connector> change) {
    Connector _newValue = change.getNewValue();
    final ProvidedDelegationConnector providedDelegationRole = ((ProvidedDelegationConnector) _newValue);
    final AssemblyContext assemblyContext = providedDelegationRole.getAssemblyContext_ProvidedDelegationConnector();
    return assemblyContext;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2javaDepInject.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<ComposedStructure, Connector> change, final ClassMethod configureMethod) {
      Connector _newValue = change.getNewValue();
      final ProvidedDelegationConnector providedDelegationRole = ((ProvidedDelegationConnector) _newValue);
      OperationProvidedRole _innerProvidedRole_ProvidedDelegationConnector = providedDelegationRole.getInnerProvidedRole_ProvidedDelegationConnector();
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
      final ComposedStructure pcmSystem = change.getAffectedEObject();
      List<EObject> _list = CollectionBridge.<EObject>toList(pcmSystem);
      this.correspondenceModel.createAndAddCorrespondence(_list, Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(namespaceClassifierRef, classifierImport)));
    }
  }
}
