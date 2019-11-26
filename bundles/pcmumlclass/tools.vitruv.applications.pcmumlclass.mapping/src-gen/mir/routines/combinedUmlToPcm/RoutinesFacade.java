package mir.routines.combinedUmlToPcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
    this.umlXpcmComponent_L2R = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R")));
    this.umlXpcmDatatypes_L2R = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R")));
    this.umlXpcmInterface_L2R = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R")));
    this.umlXpcmRepository_L2R = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRepository_L2R")));
    this.umlXpcmRoles_L2R = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R")));
    this.umlXpcmSignature_L2R = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R")));
    this.umlReturnAndRegularParameterTypeReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlReturnAndRegularParameterTypeReactions")));
  }
  
  public final mir.routines.umlXpcmComponent_L2R.RoutinesFacade umlXpcmComponent_L2R;
  
  public final mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade umlXpcmDatatypes_L2R;
  
  public final mir.routines.umlXpcmInterface_L2R.RoutinesFacade umlXpcmInterface_L2R;
  
  public final mir.routines.umlXpcmRepository_L2R.RoutinesFacade umlXpcmRepository_L2R;
  
  public final mir.routines.umlXpcmRoles_L2R.RoutinesFacade umlXpcmRoles_L2R;
  
  public final mir.routines.umlXpcmSignature_L2R.RoutinesFacade umlXpcmSignature_L2R;
  
  public final mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade umlReturnAndRegularParameterTypeReactions;
}
