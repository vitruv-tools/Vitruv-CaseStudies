package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.palladiosimulator.pcm.repository.Interface;
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
    
    public EObject getElement1(final Interface pcmInterface, final org.emftext.language.java.containers.Package containingPackage, final org.emftext.language.java.classifiers.Interface javaInterface) {
      return javaInterface;
    }
    
    public void updateJavaInterfaceElement(final Interface pcmInterface, final org.emftext.language.java.containers.Package containingPackage, final org.emftext.language.java.classifiers.Interface javaInterface) {
      javaInterface.setName(pcmInterface.getEntityName());
      javaInterface.addModifier(ModifiersFactory.eINSTANCE.createPublic());
    }
    
    public EObject getElement2(final Interface pcmInterface, final org.emftext.language.java.containers.Package containingPackage, final org.emftext.language.java.classifiers.Interface javaInterface) {
      return pcmInterface;
    }
    
    public void callRoutine1(final Interface pcmInterface, final org.emftext.language.java.containers.Package containingPackage, final org.emftext.language.java.classifiers.Interface javaInterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createCompilationUnit(pcmInterface, javaInterface, containingPackage);
    }
  }
  
  public CreateJavaInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface pcmInterface, final org.emftext.language.java.containers.Package containingPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.CreateJavaInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.containingPackage = containingPackage;
  }
  
  private Interface pcmInterface;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaInterfaceRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   containingPackage: " + this.containingPackage);
    
    org.emftext.language.java.classifiers.Interface javaInterface = org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl.eINSTANCE.createInterface();
    notifyObjectCreated(javaInterface);
    userExecution.updateJavaInterfaceElement(pcmInterface, containingPackage, javaInterface);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmInterface, containingPackage, javaInterface), userExecution.getElement2(pcmInterface, containingPackage, javaInterface), "");
    
    userExecution.callRoutine1(pcmInterface, containingPackage, javaInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
