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
    		return new mir.routines.java2Pcm.RoutinesFacade(this, reactionsImportPath.getParent(), sharedExecutionState);
    	}
    	case "java2Pcm.Java2PcmClassifier": {
    		return new mir.routines.java2PcmClassifier.RoutinesFacade(this, reactionsImportPath.getParent(), sharedExecutionState);
    	}
    	case "java2Pcm.Java2PcmMethod": {
    		return new mir.routines.java2PcmMethod.RoutinesFacade(this, reactionsImportPath.getParent(), sharedExecutionState);
    	}
    	default: {
    		return null;
    	}
    }
  }
}
