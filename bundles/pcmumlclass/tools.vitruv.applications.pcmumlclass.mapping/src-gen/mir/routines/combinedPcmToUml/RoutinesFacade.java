package mir.routines.combinedPcmToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
    this.umlXpcmComponent_R2L = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmComponent_R2L")));
    this.umlXpcmDatatypes_R2L = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmDatatypes_R2L")));
    this.umlXpcmInterface_R2L = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmInterface_R2L")));
    this.umlXpcmRepository_R2L = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRepository_R2L")));
    this.umlXpcmRoles_R2L = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L")));
    this.umlXpcmSignature_R2L = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmSignature_R2L")));
    this.pcmCollectionDataTypeReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmCollectionDataTypeReactions")));
  }
  
  public final mir.routines.umlXpcmComponent_R2L.RoutinesFacade umlXpcmComponent_R2L;
  
  public final mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade umlXpcmDatatypes_R2L;
  
  public final mir.routines.umlXpcmInterface_R2L.RoutinesFacade umlXpcmInterface_R2L;
  
  public final mir.routines.umlXpcmRepository_R2L.RoutinesFacade umlXpcmRepository_R2L;
  
  public final mir.routines.umlXpcmRoles_R2L.RoutinesFacade umlXpcmRoles_R2L;
  
  public final mir.routines.umlXpcmSignature_R2L.RoutinesFacade umlXpcmSignature_R2L;
  
  public final mir.routines.pcmCollectionDataTypeReactions.RoutinesFacade pcmCollectionDataTypeReactions;
}
