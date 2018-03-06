package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompositeDataTypeRoutine extends AbstractRepairRoutineRealization {
  private CreateCompositeDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType) {
      return pcmCompositeDataType;
    }
    
    public EObject getElement4(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType) {
      return javaClass;
    }
    
    public void updatePcmCompositeDataTypeElement(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType) {
      pcmCompositeDataType.setEntityName(javaClass.getName());
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType) {
      return javaClass;
    }
    
    public EObject getElement3(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType) {
      return compilationUnit;
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addDataTypeInRepository(pcmCompositeDataType);
    }
  }
  
  public CreateCompositeDataTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.javaClass = javaClass;this.compilationUnit = compilationUnit;
  }
  
  private org.emftext.language.java.classifiers.Class javaClass;
  
  private CompilationUnit compilationUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompositeDataTypeRoutine with input:");
    getLogger().debug("   javaClass: " + this.javaClass);
    getLogger().debug("   compilationUnit: " + this.compilationUnit);
    
    org.palladiosimulator.pcm.repository.CompositeDataType pcmCompositeDataType = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createCompositeDataType();
    notifyObjectCreated(pcmCompositeDataType);
    userExecution.updatePcmCompositeDataTypeElement(javaClass, compilationUnit, pcmCompositeDataType);
    
    addCorrespondenceBetween(userExecution.getElement1(javaClass, compilationUnit, pcmCompositeDataType), userExecution.getElement2(javaClass, compilationUnit, pcmCompositeDataType), "");
    
    addCorrespondenceBetween(userExecution.getElement3(javaClass, compilationUnit, pcmCompositeDataType), userExecution.getElement4(javaClass, compilationUnit, pcmCompositeDataType), "");
    
    userExecution.callRoutine1(javaClass, compilationUnit, pcmCompositeDataType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
