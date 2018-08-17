package mir.routines.combinedUmlClassToPcmReactions;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
    this.umlRepositoryAndSystemPackageReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlRepositoryAndSystemPackageReactions")));
    this.umlInterfaceReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlInterfaceReactions")));
    this.umlInterfaceGeneralizationReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlInterfaceGeneralizationReactions")));
    this.umlSignatureOperationReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlSignatureOperationReactions")));
    this.umlRegularParameterReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlRegularParameterReactions")));
    this.umlReturnAndRegularParameterTypeReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlReturnAndRegularParameterTypeReactions")));
    this.umlCompositeDataTypeClassReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlCompositeDataTypeClassReactions")));
    this.umlCompositeDataTypeGeneralizationReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlCompositeDataTypeGeneralizationReactions")));
    this.umlInnerDeclarationPropertyReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlInnerDeclarationPropertyReactions")));
    this.umlRepositoryComponentPackageReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlRepositoryComponentPackageReactions")));
    this.umlIPREClassReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlIPREClassReactions")));
    this.umlIPREConstructorOperationReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlIPREConstructorOperationReactions")));
    this.umlProvidedRoleGeneralizationReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlProvidedRoleGeneralizationReactions")));
    this.umlRequiredRoleParameterReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlRequiredRoleParameterReactions")));
    this.umlRequiredRolePropertyReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlRequiredRolePropertyReactions")));
    this.umlAssemblyContextPropertyReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlAssemblyContextPropertyReactions")));
  }
  
  public final mir.routines.umlRepositoryAndSystemPackageReactions.RoutinesFacade umlRepositoryAndSystemPackageReactions;
  
  public final mir.routines.umlInterfaceReactions.RoutinesFacade umlInterfaceReactions;
  
  public final mir.routines.umlInterfaceGeneralizationReactions.RoutinesFacade umlInterfaceGeneralizationReactions;
  
  public final mir.routines.umlSignatureOperationReactions.RoutinesFacade umlSignatureOperationReactions;
  
  public final mir.routines.umlRegularParameterReactions.RoutinesFacade umlRegularParameterReactions;
  
  public final mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade umlReturnAndRegularParameterTypeReactions;
  
  public final mir.routines.umlCompositeDataTypeClassReactions.RoutinesFacade umlCompositeDataTypeClassReactions;
  
  public final mir.routines.umlCompositeDataTypeGeneralizationReactions.RoutinesFacade umlCompositeDataTypeGeneralizationReactions;
  
  public final mir.routines.umlInnerDeclarationPropertyReactions.RoutinesFacade umlInnerDeclarationPropertyReactions;
  
  public final mir.routines.umlRepositoryComponentPackageReactions.RoutinesFacade umlRepositoryComponentPackageReactions;
  
  public final mir.routines.umlIPREClassReactions.RoutinesFacade umlIPREClassReactions;
  
  public final mir.routines.umlIPREConstructorOperationReactions.RoutinesFacade umlIPREConstructorOperationReactions;
  
  public final mir.routines.umlProvidedRoleGeneralizationReactions.RoutinesFacade umlProvidedRoleGeneralizationReactions;
  
  public final mir.routines.umlRequiredRoleParameterReactions.RoutinesFacade umlRequiredRoleParameterReactions;
  
  public final mir.routines.umlRequiredRolePropertyReactions.RoutinesFacade umlRequiredRolePropertyReactions;
  
  public final mir.routines.umlAssemblyContextPropertyReactions.RoutinesFacade umlAssemblyContextPropertyReactions;
}
