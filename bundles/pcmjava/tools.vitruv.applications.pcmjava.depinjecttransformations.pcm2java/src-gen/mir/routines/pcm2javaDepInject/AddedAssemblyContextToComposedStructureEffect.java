package mir.routines.pcm2javaDepInject;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcm2javaDepInject.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.instantiations.NewConstructorCall;
import org.emftext.language.java.instantiations.impl.InstantiationsFactoryImpl;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmjava.depinjecttransformations.pcm2java.PCMJaMoPPUtilsGuice;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;

@SuppressWarnings("all")
public class AddedAssemblyContextToComposedStructureEffect extends AbstractEffectRealization {
  public AddedAssemblyContextToComposedStructureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<ComposedStructure, AssemblyContext> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<ComposedStructure, AssemblyContext> change;
  
  private EObject getElement0(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall) {
    return assemblyContextField;
  }
  
  private EObject getElement1(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall) {
    return newConstructorCall;
  }
  
  private EObject getCorrepondenceSourceEncapsulatedComponentJavaClass(final InsertEReference<ComposedStructure, AssemblyContext> change) {
    AssemblyContext _newValue = change.getNewValue();
    RepositoryComponent _encapsulatedComponent__AssemblyContext = _newValue.getEncapsulatedComponent__AssemblyContext();
    return _encapsulatedComponent__AssemblyContext;
  }
  
  private boolean checkMatcherPrecondition(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass) {
    AssemblyContext _newValue = change.getNewValue();
    ComposedStructure _parentStructure__AssemblyContext = _newValue.getParentStructure__AssemblyContext();
    final EList<AssemblyContext> assemblyContexts = _parentStructure__AssemblyContext.getAssemblyContexts__ComposedStructure();
    AssemblyContext _newValue_1 = change.getNewValue();
    final RepositoryComponent component = _newValue_1.getEncapsulatedComponent__AssemblyContext();
    for (final AssemblyContext ac : assemblyContexts) {
      boolean _and = false;
      AssemblyContext _newValue_2 = change.getNewValue();
      boolean _notEquals = (!Objects.equal(ac, _newValue_2));
      if (!_notEquals) {
        _and = false;
      } else {
        RepositoryComponent _encapsulatedComponent__AssemblyContext = ac.getEncapsulatedComponent__AssemblyContext();
        boolean _equals = _encapsulatedComponent__AssemblyContext.equals(component);
        _and = _equals;
      }
      if (_and) {
        String _entityName = component.getEntityName();
        String _plus = ("Assembly context for " + _entityName);
        final String msg = (_plus + 
          "already exists. Only one assembly context per basic component is allowed.");
        return false;
      }
    }
    return true;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddedAssemblyContextToComposedStructureEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    org.emftext.language.java.classifiers.Class compositeComponentJavaClass = getCorrespondingElement(
    	getCorrepondenceSourceCompositeComponentJavaClass(change), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (compositeComponentJavaClass == null) {
    	return;
    }
    initializeRetrieveElementState(compositeComponentJavaClass);
    org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass = getCorrespondingElement(
    	getCorrepondenceSourceEncapsulatedComponentJavaClass(change), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (encapsulatedComponentJavaClass == null) {
    	return;
    }
    initializeRetrieveElementState(encapsulatedComponentJavaClass);
    if (!checkMatcherPrecondition(change, compositeComponentJavaClass, encapsulatedComponentJavaClass)) {
    	return;
    }
    Field assemblyContextField = MembersFactoryImpl.eINSTANCE.createField();
    initializeCreateElementState(assemblyContextField);
    NewConstructorCall newConstructorCall = InstantiationsFactoryImpl.eINSTANCE.createNewConstructorCall();
    initializeCreateElementState(newConstructorCall);
    
    addCorrespondenceBetween(getElement0(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall), getElement2(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall), "");
    addCorrespondenceBetween(getElement1(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall), getElement3(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall), "");
    preprocessElementStates();
    new mir.routines.pcm2javaDepInject.AddedAssemblyContextToComposedStructureEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceCompositeComponentJavaClass(final InsertEReference<ComposedStructure, AssemblyContext> change) {
    ComposedStructure _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private EObject getElement2(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall) {
    AssemblyContext _newValue = change.getNewValue();
    return _newValue;
  }
  
  private EObject getElement3(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall) {
    AssemblyContext _newValue = change.getNewValue();
    return _newValue;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2javaDepInject.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall) {
      AssemblyContext _newValue = change.getNewValue();
      final RepositoryComponent component = _newValue.getEncapsulatedComponent__AssemblyContext();
      AssemblyContext _newValue_1 = change.getNewValue();
      final ClassMethod configureMethod = PCMJaMoPPUtilsGuice.createConfigureMethodForAssemblyContext(_newValue_1, component, this.correspondenceModel, this.userInteracting);
      AssemblyContext _newValue_2 = change.getNewValue();
      CorrespondenceModelUtil.createAndAddCorrespondence(this.correspondenceModel, _newValue_2, configureMethod);
      AssemblyContext _newValue_3 = change.getNewValue();
      PCMJaMoPPUtilsGuice.createBindCall(_newValue_3, component, configureMethod, this.correspondenceModel, this.userInteracting);
    }
  }
}
