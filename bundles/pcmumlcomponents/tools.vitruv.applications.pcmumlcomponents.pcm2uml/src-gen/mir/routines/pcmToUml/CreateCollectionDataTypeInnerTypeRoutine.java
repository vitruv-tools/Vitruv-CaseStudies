package mir.routines.pcmToUml;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Collection;
import mir.routines.pcmToUml.RoutinesFacade;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import tools.vitruv.applications.pcmumlcomp.pcm2uml.PcmToUmlUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreateCollectionDataTypeInnerTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCollectionDataTypeInnerTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlInnerType(final CollectionDataType pcmDataType, final DataType pcmInnerType) {
      return pcmInnerType;
    }
    
    public void callRoutine1(final CollectionDataType pcmDataType, final DataType pcmInnerType, final org.eclipse.uml2.uml.DataType umlInnerType, @Extension final RoutinesFacade _routinesFacade) {
      if ((pcmInnerType instanceof CollectionDataType)) {
        this.userInteracting.showMessage(UserInteractionType.MODAL, 
          "Nested collection types are not transformed to UML. Consider using a composite type.");
      } else {
        org.eclipse.uml2.uml.DataType innerType = umlInnerType;
        if ((innerType == null)) {
          innerType = PcmToUmlUtil.retrieveUmlType(this.correspondenceModel, pcmInnerType);
        }
        if ((innerType == null)) {
          Logger _logger = this.getLogger();
          String _entityName = pcmDataType.getEntityName();
          String _plus = ("collection type: " + _entityName);
          _logger.info(_plus);
          this.getLogger().info(pcmInnerType);
          this.getLogger().info(((PrimitiveDataType) pcmInnerType).getType());
          this.getLogger().info(innerType);
          _routinesFacade.clearCorrespondenceForCollectionTypes(pcmDataType);
          _routinesFacade.addCorrespondenceForCollectionTypes(pcmDataType, innerType);
          final Collection<EStructuralFeature.Setting> references = EcoreUtil.UsageCrossReferencer.find(pcmDataType, pcmDataType.getRepository__DataType());
          for (final EStructuralFeature.Setting reference : references) {
            {
              if (((reference.getEObject() instanceof OperationSignature) && Objects.equal(reference.getEStructuralFeature().getName(), "returnType__OperationSignature"))) {
                EObject _eObject = reference.getEObject();
                _routinesFacade.changeUmlOperationType(((OperationSignature) _eObject));
              } else {
                if (((reference.getEObject() instanceof InnerDeclaration) && Objects.equal(reference.getEStructuralFeature().getName(), "datatype_InnerDeclaration"))) {
                  EObject _eObject_1 = reference.getEObject();
                  _routinesFacade.changeInnerDeclarationType(((InnerDeclaration) _eObject_1), pcmInnerType);
                } else {
                  if (((reference.getEObject() instanceof Parameter) && Objects.equal(reference.getEStructuralFeature().getName(), "dataType__Parameter"))) {
                    EObject _eObject_2 = reference.getEObject();
                    _routinesFacade.changeParameterType(((Parameter) _eObject_2), pcmInnerType);
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
              this.getLogger().info("reference for collection type: ");
              this.getLogger().info(reference.getEObject());
              this.getLogger().info(reference.getEStructuralFeature());
            }
          }
        } else {
          this.getLogger().warn("CollectionDataType inner type could not be resolved");
        }
      }
    }
  }
  
  public CreateCollectionDataTypeInnerTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType pcmDataType, final DataType pcmInnerType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateCollectionDataTypeInnerTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmDataType = pcmDataType;this.pcmInnerType = pcmInnerType;
  }
  
  private CollectionDataType pcmDataType;
  
  private DataType pcmInnerType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCollectionDataTypeInnerTypeRoutine with input:");
    getLogger().debug("   CollectionDataType: " + this.pcmDataType);
    getLogger().debug("   DataType: " + this.pcmInnerType);
    
    org.eclipse.uml2.uml.DataType umlInnerType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInnerType(pcmDataType, pcmInnerType), // correspondence source supplier
    	org.eclipse.uml2.uml.DataType.class,
    	(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(umlInnerType);
    userExecution.callRoutine1(pcmDataType, pcmInnerType, umlInnerType, actionsFacade);
    
    postprocessElements();
  }
}
