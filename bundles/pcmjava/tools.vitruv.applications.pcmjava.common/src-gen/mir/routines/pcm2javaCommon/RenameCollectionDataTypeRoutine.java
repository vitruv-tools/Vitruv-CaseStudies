package mir.routines.pcm2javaCommon;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameCollectionDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameCollectionDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public boolean getCorrespondingModelElementsPreconditionDatatypesPackage(final CollectionDataType collectionDataType, final org.emftext.language.java.containers.Package datatypesPackage) {
      String _name = datatypesPackage.getName();
      boolean _equals = Objects.equal(_name, "datatypes");
      return _equals;
    }
    
    public EObject getCorrepondenceSourceDatatypesPackage(final CollectionDataType collectionDataType) {
      Repository _repository__DataType = collectionDataType.getRepository__DataType();
      return _repository__DataType;
    }
    
    public void callRoutine1(final CollectionDataType collectionDataType, final org.emftext.language.java.containers.Package datatypesPackage, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.renameJavaClassifier(collectionDataType, datatypesPackage, collectionDataType.getEntityName());
    }
  }
  
  public RenameCollectionDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType collectionDataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.RenameCollectionDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(getExecutionState(), this);
    this.collectionDataType = collectionDataType;
  }
  
  private CollectionDataType collectionDataType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameCollectionDataTypeRoutine with input:");
    getLogger().debug("   collectionDataType: " + this.collectionDataType);
    
    org.emftext.language.java.containers.Package datatypesPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDatatypesPackage(collectionDataType), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> userExecution.getCorrespondingModelElementsPreconditionDatatypesPackage(collectionDataType, _element), // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (datatypesPackage == null) {
    	return false;
    }
    registerObjectUnderModification(datatypesPackage);
    userExecution.callRoutine1(collectionDataType, datatypesPackage, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
