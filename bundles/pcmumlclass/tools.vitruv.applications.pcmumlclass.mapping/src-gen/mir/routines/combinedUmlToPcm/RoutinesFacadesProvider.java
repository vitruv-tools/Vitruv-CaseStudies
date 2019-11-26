package mir.routines.combinedUmlToPcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacadesProvider extends AbstractRoutinesFacadesProvider {
  public AbstractRepairRoutinesFacade createRoutinesFacade(final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState sharedExecutionState) {
    switch(reactionsImportPath.getPathString()) {
    	case "combinedUmlToPcm": {
    		return new mir.routines.combinedUmlToPcm.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmComponent_L2R": {
    		return new mir.routines.umlXpcmComponent_L2R.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmComponent_L2R.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmComponent_L2R.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmComponent_L2R.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmDatatypes_L2R": {
    		return new mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmDatatypes_L2R.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmDatatypes_L2R.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmDatatypes_L2R.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmInterface_L2R": {
    		return new mir.routines.umlXpcmInterface_L2R.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmInterface_L2R.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmInterface_L2R.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmInterface_L2R.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmRepository_L2R": {
    		return new mir.routines.umlXpcmRepository_L2R.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmRepository_L2R.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmRepository_L2R.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmRepository_L2R.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmRoles_L2R": {
    		return new mir.routines.umlXpcmRoles_L2R.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmRoles_L2R.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmRoles_L2R.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmRoles_L2R.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmSignature_L2R": {
    		return new mir.routines.umlXpcmSignature_L2R.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmSignature_L2R.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmSignature_L2R.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlXpcmSignature_L2R.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlToPcm.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	default: {
    		throw new IllegalArgumentException("Unexpected import path: " + reactionsImportPath.getPathString());
    	}
    }
  }
}
