package mir.routines.java2Pcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacadesProvider extends AbstractRoutinesFacadesProvider {
  public AbstractRepairRoutinesFacade createRoutinesFacade(final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState sharedExecutionState) {
    switch(reactionsImportPath.getPathString()) {
    	case "java2Pcm": {
    		return new mir.routines.java2Pcm.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "java2Pcm.java2PcmClassifier": {
    		return new mir.routines.java2PcmClassifier.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "java2Pcm.java2PcmMethod": {
    		return new mir.routines.java2PcmMethod.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	default: {
    		throw new IllegalArgumentException("Unexpected import path: " + reactionsImportPath.getPathString());
    	}
    }
  }
}
