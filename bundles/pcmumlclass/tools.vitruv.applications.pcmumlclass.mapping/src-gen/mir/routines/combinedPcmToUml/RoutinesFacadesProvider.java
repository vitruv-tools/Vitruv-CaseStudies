package mir.routines.combinedPcmToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacadesProvider extends AbstractRoutinesFacadesProvider {
  public AbstractRepairRoutinesFacade createRoutinesFacade(final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState sharedExecutionState) {
    switch(reactionsImportPath.getPathString()) {
    	case "combinedPcmToUml": {
    		return new mir.routines.combinedPcmToUml.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmComponent_R2L": {
    		return new mir.routines.umlXpcmComponent_R2L.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmComponent_R2L.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmComponent_R2L.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmComponent_R2L.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmDatatypes_R2L": {
    		return new mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmDatatypes_R2L.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmDatatypes_R2L.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmDatatypes_R2L.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmInterface_R2L": {
    		return new mir.routines.umlXpcmInterface_R2L.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmInterface_R2L.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmInterface_R2L.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmInterface_R2L.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmRepository_R2L": {
    		return new mir.routines.umlXpcmRepository_R2L.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmRepository_R2L.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmRepository_R2L.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmRepository_R2L.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmRoles_R2L": {
    		return new mir.routines.umlXpcmRoles_R2L.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmRoles_R2L.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmRoles_R2L.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmRoles_R2L.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmSignature_R2L": {
    		return new mir.routines.umlXpcmSignature_R2L.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmSignature_R2L.umlXpcmRoutines": {
    		return new mir.routines.umlXpcmRoutines.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmSignature_R2L.umlXpcmRoutines.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.umlXpcmSignature_R2L.umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUml.pcmCollectionDataTypeReactions": {
    		return new mir.routines.pcmCollectionDataTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	default: {
    		throw new IllegalArgumentException("Unexpected import path: " + reactionsImportPath.getPathString());
    	}
    }
  }
}
