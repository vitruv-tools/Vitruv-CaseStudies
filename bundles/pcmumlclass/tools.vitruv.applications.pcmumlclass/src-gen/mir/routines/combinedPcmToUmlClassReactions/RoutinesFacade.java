package mir.routines.combinedPcmToUmlClassReactions;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
    this.pcmRepositoryReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmRepositoryReactions")));
    this.pcmSystemReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmSystemReactions")));
    this.pcmInterfaceReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmInterfaceReactions")));
    this.pcmSignatureReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmSignatureReactions")));
    this.pcmParameterReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmParameterReactions")));
    this.pcmCompositeDataTypeReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmCompositeDataTypeReactions")));
    this.pcmInnerDeclarationReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmInnerDeclarationReactions")));
    this.pcmCollectionDataTypeReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmCollectionDataTypeReactions")));
    this.pcmRepositoryComponentReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmRepositoryComponentReactions")));
    this.pcmRequiredRoleReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmRequiredRoleReactions")));
    this.pcmProvidedRoleReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmProvidedRoleReactions")));
    this.pcmAssemblyContextReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmAssemblyContextReactions")));
  }
  
  public final mir.routines.pcmRepositoryReactions.RoutinesFacade pcmRepositoryReactions;
  
  public final mir.routines.pcmSystemReactions.RoutinesFacade pcmSystemReactions;
  
  public final mir.routines.pcmInterfaceReactions.RoutinesFacade pcmInterfaceReactions;
  
  public final mir.routines.pcmSignatureReactions.RoutinesFacade pcmSignatureReactions;
  
  public final mir.routines.pcmParameterReactions.RoutinesFacade pcmParameterReactions;
  
  public final mir.routines.pcmCompositeDataTypeReactions.RoutinesFacade pcmCompositeDataTypeReactions;
  
  public final mir.routines.pcmInnerDeclarationReactions.RoutinesFacade pcmInnerDeclarationReactions;
  
  public final mir.routines.pcmCollectionDataTypeReactions.RoutinesFacade pcmCollectionDataTypeReactions;
  
  public final mir.routines.pcmRepositoryComponentReactions.RoutinesFacade pcmRepositoryComponentReactions;
  
  public final mir.routines.pcmRequiredRoleReactions.RoutinesFacade pcmRequiredRoleReactions;
  
  public final mir.routines.pcmProvidedRoleReactions.RoutinesFacade pcmProvidedRoleReactions;
  
  public final mir.routines.pcmAssemblyContextReactions.RoutinesFacade pcmAssemblyContextReactions;
}
