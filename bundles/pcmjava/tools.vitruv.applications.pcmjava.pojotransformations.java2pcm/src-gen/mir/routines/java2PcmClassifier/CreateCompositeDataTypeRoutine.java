package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompositeDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCompositeDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType) {
      return pcmCompositeDataType;
    }
    
    public EObject getElement4(final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType) {
      return cls;
    }
    
    public void updatePcmCompositeDataTypeElement(final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType) {
      pcmCompositeDataType.setEntityName(cls.getName());
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType) {
      return cls;
    }
    
    public EObject getElement3(final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType) {
      return compilationUnit;
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit, final CompositeDataType pcmCompositeDataType, @Extension final RoutinesFacade _routinesFacade) {
      final Repository repo = Java2PcmHelper.findPcmRepository(this.correspondenceModel);
      _routinesFacade.addDataTypeInRepository(repo, pcmCompositeDataType);
    }
  }
  
  public CreateCompositeDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.cls = cls;this.compilationUnit = compilationUnit;
  }
  
  private org.emftext.language.java.classifiers.Class cls;
  
  private CompilationUnit compilationUnit;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompositeDataTypeRoutine with input:");
    getLogger().debug("   Class: " + this.cls);
    getLogger().debug("   CompilationUnit: " + this.compilationUnit);
    
    CompositeDataType pcmCompositeDataType = RepositoryFactoryImpl.eINSTANCE.createCompositeDataType();
    notifyObjectCreated(pcmCompositeDataType);
    userExecution.updatePcmCompositeDataTypeElement(cls, compilationUnit, pcmCompositeDataType);
    
    addCorrespondenceBetween(userExecution.getElement1(cls, compilationUnit, pcmCompositeDataType), userExecution.getElement2(cls, compilationUnit, pcmCompositeDataType), "");
    
    addCorrespondenceBetween(userExecution.getElement3(cls, compilationUnit, pcmCompositeDataType), userExecution.getElement4(cls, compilationUnit, pcmCompositeDataType), "");
    
    userExecution.callRoutine1(cls, compilationUnit, pcmCompositeDataType, actionsFacade);
    
    postprocessElements();
  }
}
