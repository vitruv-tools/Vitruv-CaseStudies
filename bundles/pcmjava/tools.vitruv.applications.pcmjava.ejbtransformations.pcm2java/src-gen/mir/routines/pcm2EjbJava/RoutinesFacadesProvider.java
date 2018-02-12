package mir.routines.pcm2EjbJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacadesProvider extends AbstractRoutinesFacadesProvider {
  public AbstractRepairRoutinesFacade createRoutinesFacade(final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState sharedExecutionState) {
    switch(reactionsImportPath.getPathString()) {
    	case "Pcm2EjbJava": {
    		return new mir.routines.pcm2EjbJava.RoutinesFacade(this, reactionsImportPath.getParent(), sharedExecutionState);
    	}
    	case "Pcm2EjbJava.pcm2javaCommon": {
    		return new mir.routines.pcm2EjbJava.pcm2javaCommon.RoutinesFacade(this, reactionsImportPath.getParent(), sharedExecutionState);
    	}
    	default: {
    		return null;
    	}
    }
  }
}
