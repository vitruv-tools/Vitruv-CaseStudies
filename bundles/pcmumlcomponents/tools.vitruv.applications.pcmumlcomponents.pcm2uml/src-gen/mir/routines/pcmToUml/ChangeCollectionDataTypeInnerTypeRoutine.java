package mir.routines.pcmToUml;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import mir.routines.pcmToUml.RoutinesFacade;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Model;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.PcmToUmlUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class ChangeCollectionDataTypeInnerTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeCollectionDataTypeInnerTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlInnerType(final CollectionDataType pcmDataType, final DataType pcmInnerType) {
      return pcmInnerType;
    }
    
    public EObject getCorrepondenceSourceUmlModel(final CollectionDataType pcmDataType, final DataType pcmInnerType, final Optional<org.eclipse.uml2.uml.DataType> umlInnerType) {
      Repository _repository__DataType = pcmDataType.getRepository__DataType();
      return _repository__DataType;
    }
    
    public void callRoutine1(final CollectionDataType pcmDataType, final DataType pcmInnerType, final Optional<org.eclipse.uml2.uml.DataType> umlInnerType, final Model umlModel, @Extension final RoutinesFacade _routinesFacade) {
      if ((pcmInnerType instanceof CollectionDataType)) {
        this.userInteracting.showMessage(UserInteractionType.MODAL, 
          "Nested collection types are not transformed to UML. Consider using a composite type.");
      }
      org.eclipse.uml2.uml.DataType innerType = null;
      boolean _isPresent = umlInnerType.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        innerType = PcmToUmlUtil.retrieveUmlType(this.correspondenceModel, pcmInnerType, umlModel);
      } else {
        innerType = umlInnerType.get();
      }
      if (((innerType != null) && (!(pcmInnerType instanceof CollectionDataType)))) {
        Logger _logger = this.getLogger();
        String _entityName = pcmDataType.getEntityName();
        String _plus = ("collection type: " + _entityName);
        _logger.info(_plus);
        this.getLogger().info(pcmInnerType);
        this.getLogger().info(innerType);
        _routinesFacade.clearCorrespondenceForCollectionTypes(pcmDataType);
        _routinesFacade.addCorrespondenceForCollectionTypes(pcmDataType, innerType);
      } else {
        this.getLogger().warn("CollectionDataType inner type could not be resolved");
      }
      final Collection<EStructuralFeature.Setting> references = EcoreUtil.UsageCrossReferencer.find(pcmDataType, pcmDataType.getRepository__DataType());
      for (final EStructuralFeature.Setting reference : references) {
        if (((reference.getEObject() instanceof OperationSignature) && Objects.equal(reference.getEStructuralFeature().getName(), "returnType__OperationSignature"))) {
          EObject _eObject = reference.getEObject();
          _routinesFacade.changeUmlOperationType(((OperationSignature) _eObject));
        } else {
          if (((reference.getEObject() instanceof InnerDeclaration) && Objects.equal(reference.getEStructuralFeature().getName(), "datatype_InnerDeclaration"))) {
            EObject _eObject_1 = reference.getEObject();
            _routinesFacade.changeInnerDeclarationType(((InnerDeclaration) _eObject_1), pcmDataType);
          } else {
            if (((reference.getEObject() instanceof Parameter) && Objects.equal(reference.getEStructuralFeature().getName(), "dataType__Parameter"))) {
              EObject _eObject_2 = reference.getEObject();
              _routinesFacade.changeParameterType(((Parameter) _eObject_2), pcmDataType);
            } else {
              Logger _logger_1 = this.getLogger();
              String _name = reference.getEObject().getClass().getName();
              String _plus_1 = ("Inner collection type changed at unhandled reference for " + _name);
              String _plus_2 = (_plus_1 + 
                " at ");
              String _name_1 = reference.getEStructuralFeature().getName();
              String _plus_3 = (_plus_2 + _name_1);
              _logger_1.warn(_plus_3);
            }
          }
        }
      }
    }
  }
  
  public ChangeCollectionDataTypeInnerTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType pcmDataType, final DataType pcmInnerType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.ChangeCollectionDataTypeInnerTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmDataType = pcmDataType;this.pcmInnerType = pcmInnerType;
  }
  
  private CollectionDataType pcmDataType;
  
  private DataType pcmInnerType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeCollectionDataTypeInnerTypeRoutine with input:");
    getLogger().debug("   pcmDataType: " + this.pcmDataType);
    getLogger().debug("   pcmInnerType: " + this.pcmInnerType);
    
    	Optional<org.eclipse.uml2.uml.DataType> umlInnerType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlInnerType(pcmDataType, pcmInnerType), // correspondence source supplier
    		org.eclipse.uml2.uml.DataType.class,
    		(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlInnerType.isPresent() ? umlInnerType.get() : null);
    org.eclipse.uml2.uml.Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(pcmDataType, pcmInnerType, umlInnerType), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlModel == null) {
    	return false;
    }
    registerObjectUnderModification(umlModel);
    userExecution.callRoutine1(pcmDataType, pcmInnerType, umlInnerType, umlModel, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
