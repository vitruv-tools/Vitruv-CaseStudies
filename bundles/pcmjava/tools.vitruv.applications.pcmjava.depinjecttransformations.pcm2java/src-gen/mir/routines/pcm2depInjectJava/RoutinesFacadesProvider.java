package mir.routines.pcm2depInjectJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacadesProvider extends AbstractRoutinesFacadesProvider {
  public AbstractRepairRoutinesFacade createRoutinesFacade(final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState sharedExecutionState) {
    switch(reactionsImportPath.getPathString()) {
    	case "pcm2depInjectJava": {
    		return new mir.routines.pcm2depInjectJava.RoutinesFacade(this, reactionsImportPath.getParent(), sharedExecutionState);
    	}
    	case "pcm2depInjectJava.pcm2javaCommon": {
    		return new mir.routines.pcm2depInjectJava.pcm2javaCommon.RoutinesFacade(this, reactionsImportPath.getParent(), sharedExecutionState);
    	}
    	default: {
    		return null;
    	}
    }
  }
}
