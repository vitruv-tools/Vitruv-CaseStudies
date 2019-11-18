package mir.routines.umlXpcmDatatypes_R2L;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacadesProvider extends AbstractRoutinesFacadesProvider {
  public AbstractRepairRoutinesFacade createRoutinesFacade(final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState sharedExecutionState) {
    switch(reactionsImportPath.getPathString()) {
    	case "umlXpcmDatatypes_R2L": {
    		return new mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "umlXpcmDatatypes_R2L.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "umlXpcmDatatypes_R2L.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "umlXpcmDatatypes_R2L.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	default: {
    		throw new IllegalArgumentException("Unexpected import path: " + reactionsImportPath.getPathString());
    	}
    }
  }
}
