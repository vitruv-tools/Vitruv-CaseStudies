package mir.routines.pcmToUml;

import mir.routines.pcmToUml.AddCompositeDataTypeParentRoutine;
import mir.routines.pcmToUml.AddCorrespondenceForCollectionTypesRoutine;
import mir.routines.pcmToUml.AddOperationProvidedRoleInterfaceRoutine;
import mir.routines.pcmToUml.AddOperationRequiredRoleInterfaceRoutine;
import mir.routines.pcmToUml.ChangeCollectionDataTypeInnerTypeRoutine;
import mir.routines.pcmToUml.ChangeInnerDeclarationTypeRoutine;
import mir.routines.pcmToUml.ChangeParameterDirectionRoutine;
import mir.routines.pcmToUml.ChangeParameterTypeRoutine;
import mir.routines.pcmToUml.ChangeUmlOperationTypeRoutine;
import mir.routines.pcmToUml.ClearCorrespondenceForCollectionTypesRoutine;
import mir.routines.pcmToUml.CreateCompositeDataTypeRoutine;
import mir.routines.pcmToUml.CreateInnerDeclarationRoutine;
import mir.routines.pcmToUml.CreateOperationInterfaceSignatureRoutine;
import mir.routines.pcmToUml.CreateOperationSignatureParameterRoutine;
import mir.routines.pcmToUml.CreatePrimitiveDataTypeRoutine;
import mir.routines.pcmToUml.CreateProvidedRoleRoutine;
import mir.routines.pcmToUml.CreateRequiredRoleRoutine;
import mir.routines.pcmToUml.CreateUmlComponentRoutine;
import mir.routines.pcmToUml.CreateUmlInterfaceRoutine;
import mir.routines.pcmToUml.CreateUmlModelRoutine;
import mir.routines.pcmToUml.CreateUmlPropertyForDatatypeRoutine;
import mir.routines.pcmToUml.DeleteDataTypeRoutine;
import mir.routines.pcmToUml.DeleteElementRoutine;
import mir.routines.pcmToUml.DeleteInnerDeclarationRoutine;
import mir.routines.pcmToUml.DeleteProvidedRoleRoutine;
import mir.routines.pcmToUml.DeleteRequiredRoleRoutine;
import mir.routines.pcmToUml.RemoveCompositeDataTypeParentRoutine;
import mir.routines.pcmToUml.RemoveOperationSignatureParameterRoutine;
import mir.routines.pcmToUml.RenameParameterRoutine;
import mir.routines.pcmToUml.RenameUmlElementRoutine;
import mir.routines.pcmToUml.UnsetUmlOperationTypeRoutine;
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
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean renameUmlElement(final NamedElement pcmElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameUmlElementRoutine routine = new RenameUmlElementRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmElement);
    return routine.applyRoutine();
  }
  
  public boolean deleteElement(final NamedElement pcmElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteElementRoutine routine = new DeleteElementRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmElement);
    return routine.applyRoutine();
  }
  
  public boolean createUmlModel(final Repository pcmRepository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlModelRoutine routine = new CreateUmlModelRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean createPrimitiveDataType(final PrimitiveDataType dataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatePrimitiveDataTypeRoutine routine = new CreatePrimitiveDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType);
    return routine.applyRoutine();
  }
  
  public boolean deleteDataType(final DataType dataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteDataTypeRoutine routine = new DeleteDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType);
    return routine.applyRoutine();
  }
  
  public boolean createCompositeDataType(final CompositeDataType dataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCompositeDataTypeRoutine routine = new CreateCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType);
    return routine.applyRoutine();
  }
  
  public boolean createInnerDeclaration(final InnerDeclaration innerDeclaration) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateInnerDeclarationRoutine routine = new CreateInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, innerDeclaration);
    return routine.applyRoutine();
  }
  
  public boolean changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final DataType pcmDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeInnerDeclarationTypeRoutine routine = new ChangeInnerDeclarationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, innerDeclaration, pcmDataType);
    return routine.applyRoutine();
  }
  
  public boolean deleteInnerDeclaration(final CompositeDataType dataType, final InnerDeclaration innerDeclaration) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteInnerDeclarationRoutine routine = new DeleteInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType, innerDeclaration);
    return routine.applyRoutine();
  }
  
  public boolean addCompositeDataTypeParent(final CompositeDataType dataType, final CompositeDataType parent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCompositeDataTypeParentRoutine routine = new AddCompositeDataTypeParentRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType, parent);
    return routine.applyRoutine();
  }
  
  public boolean removeCompositeDataTypeParent(final CompositeDataType dataType, final CompositeDataType parent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCompositeDataTypeParentRoutine routine = new RemoveCompositeDataTypeParentRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType, parent);
    return routine.applyRoutine();
  }
  
  public boolean changeCollectionDataTypeInnerType(final CollectionDataType pcmDataType, final DataType pcmInnerType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeCollectionDataTypeInnerTypeRoutine routine = new ChangeCollectionDataTypeInnerTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmDataType, pcmInnerType);
    return routine.applyRoutine();
  }
  
  public boolean clearCorrespondenceForCollectionTypes(final CollectionDataType pcmType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ClearCorrespondenceForCollectionTypesRoutine routine = new ClearCorrespondenceForCollectionTypesRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForCollectionTypes(final CollectionDataType pcmType, final org.eclipse.uml2.uml.DataType innerType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForCollectionTypesRoutine routine = new AddCorrespondenceForCollectionTypesRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, innerType);
    return routine.applyRoutine();
  }
  
  public boolean createUmlPropertyForDatatype(final org.eclipse.uml2.uml.DataType type, final InnerDeclaration counterpart, final org.eclipse.uml2.uml.DataType owner) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlPropertyForDatatypeRoutine routine = new CreateUmlPropertyForDatatypeRoutine(_routinesFacade, _reactionExecutionState, _caller, type, counterpart, owner);
    return routine.applyRoutine();
  }
  
  public boolean createUmlInterface(final Interface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlInterfaceRoutine routine = new CreateUmlInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface);
    return routine.applyRoutine();
  }
  
  public boolean createOperationInterfaceSignature(final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateOperationInterfaceSignatureRoutine routine = new CreateOperationInterfaceSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlOperationType(final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeUmlOperationTypeRoutine routine = new ChangeUmlOperationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean unsetUmlOperationType(final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UnsetUmlOperationTypeRoutine routine = new UnsetUmlOperationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean createOperationSignatureParameter(final OperationSignature pcmSignature, final Parameter pcmParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateOperationSignatureParameterRoutine routine = new CreateOperationSignatureParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature, pcmParameter);
    return routine.applyRoutine();
  }
  
  public boolean renameParameter(final Parameter pcmParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameParameterRoutine routine = new RenameParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterDirection(final Parameter pcmParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeParameterDirectionRoutine routine = new ChangeParameterDirectionRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterType(final Parameter pcmParameter, final DataType pcmDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeParameterTypeRoutine routine = new ChangeParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParameter, pcmDataType);
    return routine.applyRoutine();
  }
  
  public boolean removeOperationSignatureParameter(final Parameter pcmParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveOperationSignatureParameterRoutine routine = new RemoveOperationSignatureParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParameter);
    return routine.applyRoutine();
  }
  
  public boolean createUmlComponent(final RepositoryComponent pcmComponent, final String correspondenceTag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlComponentRoutine routine = new CreateUmlComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, correspondenceTag);
    return routine.applyRoutine();
  }
  
  public boolean createProvidedRole(final InterfaceProvidingEntity pcmComponent, final ProvidedRole pcmProvidedRole) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateProvidedRoleRoutine routine = new CreateProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, pcmProvidedRole);
    return routine.applyRoutine();
  }
  
  public boolean deleteProvidedRole(final ProvidedRole pcmProvidedRole) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteProvidedRoleRoutine routine = new DeleteProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvidedRole);
    return routine.applyRoutine();
  }
  
  public boolean addOperationProvidedRoleInterface(final OperationProvidedRole pcmRole, final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddOperationProvidedRoleInterfaceRoutine routine = new AddOperationProvidedRoleInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRole, pcmInterface);
    return routine.applyRoutine();
  }
  
  public boolean createRequiredRole(final InterfaceRequiringEntity pcmComponent, final OperationRequiredRole requiredRole) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateRequiredRoleRoutine routine = new CreateRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, requiredRole);
    return routine.applyRoutine();
  }
  
  public boolean deleteRequiredRole(final OperationRequiredRole requiredRole) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteRequiredRoleRoutine routine = new DeleteRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, requiredRole);
    return routine.applyRoutine();
  }
  
  public boolean addOperationRequiredRoleInterface(final OperationRequiredRole pcmRole, final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddOperationRequiredRoleInterfaceRoutine routine = new AddOperationRequiredRoleInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRole, pcmInterface);
    return routine.applyRoutine();
  }
}
