package mir.routines.ejbjava2pcm;

import java.io.IOException;
import java.util.Set;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EjbJava2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;

@SuppressWarnings("all")
public class CreatedClassMethodInEjbClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatedClassMethodInEjbClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceBasicComponent(final org.emftext.language.java.classifiers.Class clazz, final ClassMethod classMethod) {
      return clazz;
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class clazz, final ClassMethod classMethod, final BasicComponent basicComponent, @Extension final RoutinesFacade _routinesFacade) {
      final Method method = EjbJava2PcmHelper.getOoverridenInterfaceMethod(classMethod, clazz);
      final Set<OperationSignature> correspondingEObjects = CorrespondenceModelUtil.<OperationSignature, Correspondence>getCorrespondingEObjectsByType(this.correspondenceModel, method, OperationSignature.class);
      OperationSignature opSignature = null;
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(correspondingEObjects);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        opSignature = ((OperationSignature[])Conversions.unwrapArray(correspondingEObjects, OperationSignature.class))[0];
      }
      _routinesFacade.createSEFFForClassMethod(basicComponent, opSignature, classMethod);
    }
  }
  
  public CreatedClassMethodInEjbClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class clazz, final ClassMethod classMethod) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedClassMethodInEjbClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.clazz = clazz;this.classMethod = classMethod;
  }
  
  private org.emftext.language.java.classifiers.Class clazz;
  
  private ClassMethod classMethod;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedClassMethodInEjbClassRoutine with input:");
    getLogger().debug("   clazz: " + this.clazz);
    getLogger().debug("   classMethod: " + this.classMethod);
    
    org.palladiosimulator.pcm.repository.BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(clazz, classMethod), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.BasicComponent.class,
    	(org.palladiosimulator.pcm.repository.BasicComponent _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (basicComponent == null) {
    	return false;
    }
    registerObjectUnderModification(basicComponent);
    userExecution.callRoutine1(clazz, classMethod, basicComponent, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
