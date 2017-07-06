package mir.routines.java2pcm;

import java.io.IOException;
import mir.routines.java2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
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
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class cls, final CompositeDataType pcmCompositeDataType) {
      return pcmCompositeDataType;
    }
    
    public void updatePcmCompositeDataTypeElement(final org.emftext.language.java.classifiers.Class cls, final CompositeDataType pcmCompositeDataType) {
      pcmCompositeDataType.setEntityName(cls.getName());
      pcmCompositeDataType.setRepository__DataType(null);
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class cls, final CompositeDataType pcmCompositeDataType) {
      return cls;
    }
  }
  
  public CreateCompositeDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class cls) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2pcm.CreateCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2pcm.RoutinesFacade(getExecutionState(), this);
    this.cls = cls;
  }
  
  private org.emftext.language.java.classifiers.Class cls;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompositeDataTypeRoutine with input:");
    getLogger().debug("   Class: " + this.cls);
    
    CompositeDataType pcmCompositeDataType = RepositoryFactoryImpl.eINSTANCE.createCompositeDataType();
    userExecution.updatePcmCompositeDataTypeElement(cls, pcmCompositeDataType);
    
    addCorrespondenceBetween(userExecution.getElement1(cls, pcmCompositeDataType), userExecution.getElement2(cls, pcmCompositeDataType), "");
    
    postprocessElements();
  }
}
