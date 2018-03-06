package mir.routines.pcm2javaCommon;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.RequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private RemoveRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceRequiredInterfaceField(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport) {
      return requiredRole;
    }
    
    public EObject getCorrepondenceSourceRequiredInterfaceImport(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
      return requiredRole;
    }
    
    public EObject getElement1(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final org.emftext.language.java.classifiers.Class javaClass) {
      return requiredInterfaceImport;
    }
    
    public EObject getCorrepondenceSourceJavaClass(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField) {
      return requiringEntity;
    }
    
    public EObject getElement2(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final org.emftext.language.java.classifiers.Class javaClass) {
      return requiredInterfaceField;
    }
    
    public void callRoutine1(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final org.emftext.language.java.classifiers.Class javaClass, @Extension final RoutinesFacade _routinesFacade) {
      Iterable<Constructor> _filter = Iterables.<Constructor>filter(javaClass.getMembers(), Constructor.class);
      for (final Constructor ctor : _filter) {
        {
          _routinesFacade.removeParameterToFieldAssignmentFromConstructor(ctor, requiredRole.getEntityName());
          _routinesFacade.removeCorrespondingParameterFromConstructor(ctor, requiredRole);
        }
      }
    }
  }
  
  public RemoveRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.RemoveRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.requiredRole = requiredRole;this.requiringEntity = requiringEntity;
  }
  
  private RequiredRole requiredRole;
  
  private InterfaceRequiringEntity requiringEntity;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveRequiredRoleRoutine with input:");
    getLogger().debug("   requiredRole: " + this.requiredRole);
    getLogger().debug("   requiringEntity: " + this.requiringEntity);
    
    org.emftext.language.java.imports.ClassifierImport requiredInterfaceImport = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRequiredInterfaceImport(requiredRole, requiringEntity), // correspondence source supplier
    	org.emftext.language.java.imports.ClassifierImport.class,
    	(org.emftext.language.java.imports.ClassifierImport _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (requiredInterfaceImport == null) {
    	return false;
    }
    registerObjectUnderModification(requiredInterfaceImport);
    org.emftext.language.java.members.Field requiredInterfaceField = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRequiredInterfaceField(requiredRole, requiringEntity, requiredInterfaceImport), // correspondence source supplier
    	org.emftext.language.java.members.Field.class,
    	(org.emftext.language.java.members.Field _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (requiredInterfaceField == null) {
    	return false;
    }
    registerObjectUnderModification(requiredInterfaceField);
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClass(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaClass == null) {
    	return false;
    }
    registerObjectUnderModification(javaClass);
    deleteObject(userExecution.getElement1(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField, javaClass));
    
    deleteObject(userExecution.getElement2(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField, javaClass));
    
    userExecution.callRoutine1(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField, javaClass, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
