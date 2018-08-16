package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaInterfaceRoutine extends AbstractRepairRoutineRealization {
  private CreateJavaInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface) {
      return javaInterface;
    }
    
    public void updateJavaInterfaceElement(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface) {
      javaInterface.setName(className);
      javaInterface.addModifier(ModifiersFactory.eINSTANCE.createPublic());
    }
    
    public EObject getElement2(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface) {
      return sourceElementMappedToClass;
    }
    
    public void callRoutine1(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createCompilationUnit(sourceElementMappedToClass, javaInterface, containingPackage);
    }
  }
  
  public CreateJavaInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.CreateJavaInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.sourceElementMappedToClass = sourceElementMappedToClass;this.containingPackage = containingPackage;this.className = className;
  }
  
  private NamedElement sourceElementMappedToClass;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  private String className;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaInterfaceRoutine with input:");
    getLogger().debug("   sourceElementMappedToClass: " + this.sourceElementMappedToClass);
    getLogger().debug("   containingPackage: " + this.containingPackage);
    getLogger().debug("   className: " + this.className);
    
    org.emftext.language.java.classifiers.Interface javaInterface = org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl.eINSTANCE.createInterface();
    notifyObjectCreated(javaInterface);
    userExecution.updateJavaInterfaceElement(sourceElementMappedToClass, containingPackage, className, javaInterface);
    
    addCorrespondenceBetween(userExecution.getElement1(sourceElementMappedToClass, containingPackage, className, javaInterface), userExecution.getElement2(sourceElementMappedToClass, containingPackage, className, javaInterface), "");
    
    userExecution.callRoutine1(sourceElementMappedToClass, containingPackage, className, javaInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
