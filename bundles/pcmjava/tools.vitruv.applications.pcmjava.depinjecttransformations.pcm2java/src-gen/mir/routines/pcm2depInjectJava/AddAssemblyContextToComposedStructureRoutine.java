package mir.routines.pcm2depInjectJava;

import java.io.IOException;
import mir.routines.pcm2depInjectJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.instantiations.NewConstructorCall;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmjava.depinjecttransformations.pcm2java.PcmJamoppUtilsGuice;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddAssemblyContextToComposedStructureRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddAssemblyContextToComposedStructureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall) {
      return assemblyContextField;
    }
    
    public EObject getCorrepondenceSourceEncapsulatedComponentJavaClass(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass) {
      RepositoryComponent _encapsulatedComponent__AssemblyContext = assemblyContext.getEncapsulatedComponent__AssemblyContext();
      return _encapsulatedComponent__AssemblyContext;
    }
    
    public EObject getCorrepondenceSourceCompositeComponentJavaClass(final ComposedStructure composedStructure, final AssemblyContext assemblyContext) {
      return composedStructure;
    }
    
    public EObject getElement4(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall) {
      return assemblyContext;
    }
    
    public EObject getElement2(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall) {
      return assemblyContext;
    }
    
    public EObject getElement3(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall) {
      return newConstructorCall;
    }
    
    public void callRoutine1(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, @Extension final RoutinesFacade _routinesFacade) {
      final RepositoryComponent component = assemblyContext.getEncapsulatedComponent__AssemblyContext();
      final ClassMethod configureMethod = PcmJamoppUtilsGuice.createConfigureMethodForAssemblyContext(assemblyContext, component, this.correspondenceModel, this.userInteracting);
      PcmJamoppUtilsGuice.createBindCall(assemblyContext, component, configureMethod, this.correspondenceModel, this.userInteracting);
    }
  }
  
  public AddAssemblyContextToComposedStructureRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ComposedStructure composedStructure, final AssemblyContext assemblyContext) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2depInjectJava.AddAssemblyContextToComposedStructureRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(getExecutionState(), this);
    this.composedStructure = composedStructure;this.assemblyContext = assemblyContext;
  }
  
  private ComposedStructure composedStructure;
  
  private AssemblyContext assemblyContext;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddAssemblyContextToComposedStructureRoutine with input:");
    getLogger().debug("   composedStructure: " + this.composedStructure);
    getLogger().debug("   assemblyContext: " + this.assemblyContext);
    
    org.emftext.language.java.classifiers.Class compositeComponentJavaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeComponentJavaClass(composedStructure, assemblyContext), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compositeComponentJavaClass == null) {
    	return false;
    }
    registerObjectUnderModification(compositeComponentJavaClass);
    org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceEncapsulatedComponentJavaClass(composedStructure, assemblyContext, compositeComponentJavaClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (encapsulatedComponentJavaClass == null) {
    	return false;
    }
    registerObjectUnderModification(encapsulatedComponentJavaClass);
    org.emftext.language.java.members.Field assemblyContextField = org.emftext.language.java.members.impl.MembersFactoryImpl.eINSTANCE.createField();
    notifyObjectCreated(assemblyContextField);
    
    org.emftext.language.java.instantiations.NewConstructorCall newConstructorCall = org.emftext.language.java.instantiations.impl.InstantiationsFactoryImpl.eINSTANCE.createNewConstructorCall();
    notifyObjectCreated(newConstructorCall);
    
    addCorrespondenceBetween(userExecution.getElement1(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall), userExecution.getElement2(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall), "");
    
    addCorrespondenceBetween(userExecution.getElement3(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall), userExecution.getElement4(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall), "");
    
    userExecution.callRoutine1(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
