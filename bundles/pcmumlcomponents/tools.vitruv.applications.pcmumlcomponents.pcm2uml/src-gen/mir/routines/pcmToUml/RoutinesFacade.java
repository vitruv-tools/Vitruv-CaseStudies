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
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean renameUmlElement(final NamedElement pcmElement) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.RenameUmlElementRoutine routine = new mir.routines.pcmToUml.RenameUmlElementRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmElement);
    return routine.applyRoutine();
  }
  
  public boolean deleteElement(final NamedElement pcmElement) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.DeleteElementRoutine routine = new mir.routines.pcmToUml.DeleteElementRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmElement);
    return routine.applyRoutine();
  }
  
  public boolean createUmlModel(final Repository pcmRepository) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.CreateUmlModelRoutine routine = new mir.routines.pcmToUml.CreateUmlModelRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean createPrimitiveDataType(final PrimitiveDataType dataType) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.CreatePrimitiveDataTypeRoutine routine = new mir.routines.pcmToUml.CreatePrimitiveDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType);
    return routine.applyRoutine();
  }
  
  public boolean deleteDataType(final DataType dataType) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.DeleteDataTypeRoutine routine = new mir.routines.pcmToUml.DeleteDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType);
    return routine.applyRoutine();
  }
  
  public boolean createCompositeDataType(final CompositeDataType dataType) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.CreateCompositeDataTypeRoutine routine = new mir.routines.pcmToUml.CreateCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType);
    return routine.applyRoutine();
  }
  
  public boolean createInnerDeclaration(final InnerDeclaration innerDeclaration) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.CreateInnerDeclarationRoutine routine = new mir.routines.pcmToUml.CreateInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, innerDeclaration);
    return routine.applyRoutine();
  }
  
  public boolean changeInnerDeclarationType(final InnerDeclaration innerDeclaration, final DataType pcmDataType) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.ChangeInnerDeclarationTypeRoutine routine = new mir.routines.pcmToUml.ChangeInnerDeclarationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, innerDeclaration, pcmDataType);
    return routine.applyRoutine();
  }
  
  public boolean deleteInnerDeclaration(final CompositeDataType dataType, final InnerDeclaration innerDeclaration) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.DeleteInnerDeclarationRoutine routine = new mir.routines.pcmToUml.DeleteInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType, innerDeclaration);
    return routine.applyRoutine();
  }
  
  public boolean addCompositeDataTypeParent(final CompositeDataType dataType, final CompositeDataType parent) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.AddCompositeDataTypeParentRoutine routine = new mir.routines.pcmToUml.AddCompositeDataTypeParentRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType, parent);
    return routine.applyRoutine();
  }
  
  public boolean removeCompositeDataTypeParent(final CompositeDataType dataType, final CompositeDataType parent) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.RemoveCompositeDataTypeParentRoutine routine = new mir.routines.pcmToUml.RemoveCompositeDataTypeParentRoutine(_routinesFacade, _reactionExecutionState, _caller, dataType, parent);
    return routine.applyRoutine();
  }
  
  public boolean changeCollectionDataTypeInnerType(final CollectionDataType pcmDataType, final DataType pcmInnerType) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.ChangeCollectionDataTypeInnerTypeRoutine routine = new mir.routines.pcmToUml.ChangeCollectionDataTypeInnerTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmDataType, pcmInnerType);
    return routine.applyRoutine();
  }
  
  public boolean clearCorrespondenceForCollectionTypes(final CollectionDataType pcmType) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.ClearCorrespondenceForCollectionTypesRoutine routine = new mir.routines.pcmToUml.ClearCorrespondenceForCollectionTypesRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForCollectionTypes(final CollectionDataType pcmType, final org.eclipse.uml2.uml.DataType innerType) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.AddCorrespondenceForCollectionTypesRoutine routine = new mir.routines.pcmToUml.AddCorrespondenceForCollectionTypesRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, innerType);
    return routine.applyRoutine();
  }
  
  public boolean createUmlPropertyForDatatype(final org.eclipse.uml2.uml.DataType type, final InnerDeclaration counterpart, final org.eclipse.uml2.uml.DataType owner) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.CreateUmlPropertyForDatatypeRoutine routine = new mir.routines.pcmToUml.CreateUmlPropertyForDatatypeRoutine(_routinesFacade, _reactionExecutionState, _caller, type, counterpart, owner);
    return routine.applyRoutine();
  }
  
  public boolean createUmlInterface(final Interface pcmInterface) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.CreateUmlInterfaceRoutine routine = new mir.routines.pcmToUml.CreateUmlInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface);
    return routine.applyRoutine();
  }
  
  public boolean createOperationInterfaceSignature(final OperationSignature pcmSignature) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.CreateOperationInterfaceSignatureRoutine routine = new mir.routines.pcmToUml.CreateOperationInterfaceSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlOperationType(final OperationSignature pcmSignature) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.ChangeUmlOperationTypeRoutine routine = new mir.routines.pcmToUml.ChangeUmlOperationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean unsetUmlOperationType(final OperationSignature pcmSignature) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.UnsetUmlOperationTypeRoutine routine = new mir.routines.pcmToUml.UnsetUmlOperationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean createOperationSignatureParameter(final OperationSignature pcmSignature, final Parameter pcmParameter) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.CreateOperationSignatureParameterRoutine routine = new mir.routines.pcmToUml.CreateOperationSignatureParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSignature, pcmParameter);
    return routine.applyRoutine();
  }
  
  public boolean renameParameter(final Parameter pcmParameter) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.RenameParameterRoutine routine = new mir.routines.pcmToUml.RenameParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterDirection(final Parameter pcmParameter) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.ChangeParameterDirectionRoutine routine = new mir.routines.pcmToUml.ChangeParameterDirectionRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterType(final Parameter pcmParameter, final DataType pcmDataType) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.ChangeParameterTypeRoutine routine = new mir.routines.pcmToUml.ChangeParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParameter, pcmDataType);
    return routine.applyRoutine();
  }
  
  public boolean removeOperationSignatureParameter(final Parameter pcmParameter) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.RemoveOperationSignatureParameterRoutine routine = new mir.routines.pcmToUml.RemoveOperationSignatureParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmParameter);
    return routine.applyRoutine();
  }
  
  public boolean createUmlComponent(final RepositoryComponent pcmComponent, final String correspondenceTag) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.CreateUmlComponentRoutine routine = new mir.routines.pcmToUml.CreateUmlComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, correspondenceTag);
    return routine.applyRoutine();
  }
  
  public boolean createProvidedRole(final InterfaceProvidingEntity pcmComponent, final ProvidedRole pcmProvidedRole) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.CreateProvidedRoleRoutine routine = new mir.routines.pcmToUml.CreateProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, pcmProvidedRole);
    return routine.applyRoutine();
  }
  
  public boolean deleteProvidedRole(final ProvidedRole pcmProvidedRole) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.DeleteProvidedRoleRoutine routine = new mir.routines.pcmToUml.DeleteProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvidedRole);
    return routine.applyRoutine();
  }
  
  public boolean addOperationProvidedRoleInterface(final OperationProvidedRole pcmRole, final OperationInterface pcmInterface) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.AddOperationProvidedRoleInterfaceRoutine routine = new mir.routines.pcmToUml.AddOperationProvidedRoleInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRole, pcmInterface);
    return routine.applyRoutine();
  }
  
  public boolean createRequiredRole(final InterfaceRequiringEntity pcmComponent, final OperationRequiredRole requiredRole) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.CreateRequiredRoleRoutine routine = new mir.routines.pcmToUml.CreateRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, requiredRole);
    return routine.applyRoutine();
  }
  
  public boolean deleteRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.DeleteRequiredRoleRoutine routine = new mir.routines.pcmToUml.DeleteRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, requiredRole);
    return routine.applyRoutine();
  }
  
  public boolean addOperationRequiredRoleInterface(final OperationRequiredRole pcmRole, final OperationInterface pcmInterface) {
    mir.routines.pcmToUml.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.pcmToUml.AddOperationRequiredRoleInterfaceRoutine routine = new mir.routines.pcmToUml.AddOperationRequiredRoleInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRole, pcmInterface);
    return routine.applyRoutine();
  }
}
