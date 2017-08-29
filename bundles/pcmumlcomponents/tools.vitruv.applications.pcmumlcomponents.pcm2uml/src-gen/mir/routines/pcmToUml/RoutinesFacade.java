package mir.routines.pcmToUml;

import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public boolean renameUmlElement(final NamedElement pcmElement) {
    mir.routines.pcmToUml.RenameUmlElementRoutine effect = new mir.routines.pcmToUml.RenameUmlElementRoutine(this.executionState, calledBy, pcmElement);
    return effect.applyRoutine();
  }
  
  public boolean deleteElement(final NamedElement pcmElement) {
    mir.routines.pcmToUml.DeleteElementRoutine effect = new mir.routines.pcmToUml.DeleteElementRoutine(this.executionState, calledBy, pcmElement);
    return effect.applyRoutine();
  }
  
  public boolean createUmlModel(final Repository pcmRepository) {
    mir.routines.pcmToUml.CreateUmlModelRoutine effect = new mir.routines.pcmToUml.CreateUmlModelRoutine(this.executionState, calledBy, pcmRepository);
    return effect.applyRoutine();
  }
  
  public boolean createPrimitiveDataType(final PrimitiveDataType dataType) {
    mir.routines.pcmToUml.CreatePrimitiveDataTypeRoutine effect = new mir.routines.pcmToUml.CreatePrimitiveDataTypeRoutine(this.executionState, calledBy, dataType);
    return effect.applyRoutine();
  }
  
  public boolean deleteDataType(final DataType dataType) {
    mir.routines.pcmToUml.DeleteDataTypeRoutine effect = new mir.routines.pcmToUml.DeleteDataTypeRoutine(this.executionState, calledBy, dataType);
    return effect.applyRoutine();
  }
  
  public boolean createCompositeDataType(final CompositeDataType dataType) {
    mir.routines.pcmToUml.CreateCompositeDataTypeRoutine effect = new mir.routines.pcmToUml.CreateCompositeDataTypeRoutine(this.executionState, calledBy, dataType);
    return effect.applyRoutine();
  }
  
  public boolean createInnerDeclaration(final InnerDeclaration innerDeclaration) {
    mir.routines.pcmToUml.CreateInnerDeclarationRoutine effect = new mir.routines.pcmToUml.CreateInnerDeclarationRoutine(this.executionState, calledBy, innerDeclaration);
    return effect.applyRoutine();
  }
  
  public boolean changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final DataType pcmDataType) {
    mir.routines.pcmToUml.ChangeInnerDeclarationTypeRoutine effect = new mir.routines.pcmToUml.ChangeInnerDeclarationTypeRoutine(this.executionState, calledBy, innerDeclaration, pcmDataType);
    return effect.applyRoutine();
  }
  
  public boolean deleteInnerDeclaration(final CompositeDataType dataType, final InnerDeclaration innerDeclaration) {
    mir.routines.pcmToUml.DeleteInnerDeclarationRoutine effect = new mir.routines.pcmToUml.DeleteInnerDeclarationRoutine(this.executionState, calledBy, dataType, innerDeclaration);
    return effect.applyRoutine();
  }
  
  public boolean addCompositeDataTypeParent(final CompositeDataType dataType, final CompositeDataType parent) {
    mir.routines.pcmToUml.AddCompositeDataTypeParentRoutine effect = new mir.routines.pcmToUml.AddCompositeDataTypeParentRoutine(this.executionState, calledBy, dataType, parent);
    return effect.applyRoutine();
  }
  
  public boolean removeCompositeDataTypeParent(final CompositeDataType dataType, final CompositeDataType parent) {
    mir.routines.pcmToUml.RemoveCompositeDataTypeParentRoutine effect = new mir.routines.pcmToUml.RemoveCompositeDataTypeParentRoutine(this.executionState, calledBy, dataType, parent);
    return effect.applyRoutine();
  }
  
  public boolean changeCollectionDataTypeInnerType(final CollectionDataType pcmDataType, final DataType pcmInnerType) {
    mir.routines.pcmToUml.ChangeCollectionDataTypeInnerTypeRoutine effect = new mir.routines.pcmToUml.ChangeCollectionDataTypeInnerTypeRoutine(this.executionState, calledBy, pcmDataType, pcmInnerType);
    return effect.applyRoutine();
  }
  
  public boolean clearCorrespondenceForCollectionTypes(final CollectionDataType pcmType) {
    mir.routines.pcmToUml.ClearCorrespondenceForCollectionTypesRoutine effect = new mir.routines.pcmToUml.ClearCorrespondenceForCollectionTypesRoutine(this.executionState, calledBy, pcmType);
    return effect.applyRoutine();
  }
  
  public boolean addCorrespondenceForCollectionTypes(final CollectionDataType pcmType, final org.eclipse.uml2.uml.DataType innerType) {
    mir.routines.pcmToUml.AddCorrespondenceForCollectionTypesRoutine effect = new mir.routines.pcmToUml.AddCorrespondenceForCollectionTypesRoutine(this.executionState, calledBy, pcmType, innerType);
    return effect.applyRoutine();
  }
  
  public boolean createUmlPropertyForDatatype(final org.eclipse.uml2.uml.DataType type, final InnerDeclaration counterpart, final org.eclipse.uml2.uml.DataType owner) {
    mir.routines.pcmToUml.CreateUmlPropertyForDatatypeRoutine effect = new mir.routines.pcmToUml.CreateUmlPropertyForDatatypeRoutine(this.executionState, calledBy, type, counterpart, owner);
    return effect.applyRoutine();
  }
  
  public boolean createUmlInterface(final Interface pcmInterface) {
    mir.routines.pcmToUml.CreateUmlInterfaceRoutine effect = new mir.routines.pcmToUml.CreateUmlInterfaceRoutine(this.executionState, calledBy, pcmInterface);
    return effect.applyRoutine();
  }
  
  public boolean createOperationInterfaceSignature(final OperationSignature pcmSignature) {
    mir.routines.pcmToUml.CreateOperationInterfaceSignatureRoutine effect = new mir.routines.pcmToUml.CreateOperationInterfaceSignatureRoutine(this.executionState, calledBy, pcmSignature);
    return effect.applyRoutine();
  }
  
  public boolean changeUmlOperationType(final OperationSignature pcmSignature) {
    mir.routines.pcmToUml.ChangeUmlOperationTypeRoutine effect = new mir.routines.pcmToUml.ChangeUmlOperationTypeRoutine(this.executionState, calledBy, pcmSignature);
    return effect.applyRoutine();
  }
  
  public boolean unsetUmlOperationType(final OperationSignature pcmSignature) {
    mir.routines.pcmToUml.UnsetUmlOperationTypeRoutine effect = new mir.routines.pcmToUml.UnsetUmlOperationTypeRoutine(this.executionState, calledBy, pcmSignature);
    return effect.applyRoutine();
  }
  
  public boolean createOperationSignatureParameter(final OperationSignature pcmSignature, final Parameter pcmParameter) {
    mir.routines.pcmToUml.CreateOperationSignatureParameterRoutine effect = new mir.routines.pcmToUml.CreateOperationSignatureParameterRoutine(this.executionState, calledBy, pcmSignature, pcmParameter);
    return effect.applyRoutine();
  }
  
  public boolean renameParameter(final Parameter pcmParameter) {
    mir.routines.pcmToUml.RenameParameterRoutine effect = new mir.routines.pcmToUml.RenameParameterRoutine(this.executionState, calledBy, pcmParameter);
    return effect.applyRoutine();
  }
  
  public boolean changeParameterDirection(final Parameter pcmParameter) {
    mir.routines.pcmToUml.ChangeParameterDirectionRoutine effect = new mir.routines.pcmToUml.ChangeParameterDirectionRoutine(this.executionState, calledBy, pcmParameter);
    return effect.applyRoutine();
  }
  
  public boolean changeParameterType(final Parameter pcmParameter, final DataType pcmDataType) {
    mir.routines.pcmToUml.ChangeParameterTypeRoutine effect = new mir.routines.pcmToUml.ChangeParameterTypeRoutine(this.executionState, calledBy, pcmParameter, pcmDataType);
    return effect.applyRoutine();
  }
  
  public boolean removeOperationSignatureParameter(final Parameter pcmParameter) {
    mir.routines.pcmToUml.RemoveOperationSignatureParameterRoutine effect = new mir.routines.pcmToUml.RemoveOperationSignatureParameterRoutine(this.executionState, calledBy, pcmParameter);
    return effect.applyRoutine();
  }
  
  public boolean createUmlComponent(final RepositoryComponent pcmComponent, final String correspondenceTag) {
    mir.routines.pcmToUml.CreateUmlComponentRoutine effect = new mir.routines.pcmToUml.CreateUmlComponentRoutine(this.executionState, calledBy, pcmComponent, correspondenceTag);
    return effect.applyRoutine();
  }
  
  public boolean createProvidedRole(final InterfaceProvidingEntity pcmComponent, final ProvidedRole pcmProvidedRole) {
    mir.routines.pcmToUml.CreateProvidedRoleRoutine effect = new mir.routines.pcmToUml.CreateProvidedRoleRoutine(this.executionState, calledBy, pcmComponent, pcmProvidedRole);
    return effect.applyRoutine();
  }
  
  public boolean deleteProvidedRole(final ProvidedRole pcmProvidedRole) {
    mir.routines.pcmToUml.DeleteProvidedRoleRoutine effect = new mir.routines.pcmToUml.DeleteProvidedRoleRoutine(this.executionState, calledBy, pcmProvidedRole);
    return effect.applyRoutine();
  }
  
  public boolean addOperationProvidedRoleInterface(final OperationProvidedRole pcmRole, final OperationInterface pcmInterface) {
    mir.routines.pcmToUml.AddOperationProvidedRoleInterfaceRoutine effect = new mir.routines.pcmToUml.AddOperationProvidedRoleInterfaceRoutine(this.executionState, calledBy, pcmRole, pcmInterface);
    return effect.applyRoutine();
  }
  
  public boolean createRequiredRole(final InterfaceRequiringEntity pcmComponent, final OperationRequiredRole requiredRole) {
    mir.routines.pcmToUml.CreateRequiredRoleRoutine effect = new mir.routines.pcmToUml.CreateRequiredRoleRoutine(this.executionState, calledBy, pcmComponent, requiredRole);
    return effect.applyRoutine();
  }
  
  public boolean deleteRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcmToUml.DeleteRequiredRoleRoutine effect = new mir.routines.pcmToUml.DeleteRequiredRoleRoutine(this.executionState, calledBy, requiredRole);
    return effect.applyRoutine();
  }
  
  public boolean addOperationRequiredRoleInterface(final OperationRequiredRole pcmRole, final OperationInterface pcmInterface) {
    mir.routines.pcmToUml.AddOperationRequiredRoleInterfaceRoutine effect = new mir.routines.pcmToUml.AddOperationRequiredRoleInterfaceRoutine(this.executionState, calledBy, pcmRole, pcmInterface);
    return effect.applyRoutine();
  }
}
