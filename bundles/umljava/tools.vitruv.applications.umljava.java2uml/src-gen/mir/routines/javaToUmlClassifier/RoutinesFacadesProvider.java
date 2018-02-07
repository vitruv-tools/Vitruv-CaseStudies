package mir.routines.javaToUmlClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacadesProvider extends AbstractRoutinesFacadesProvider {
  public AbstractRepairRoutinesFacade createRoutinesFacade(final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState sharedExecutionState) {
    switch(reactionsImportPath.getPathString()) {
    	case "JavaToUmlClassifier": {
    		return new mir.routines.javaToUmlClassifier.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	default: {
    		return null;
    	}
    }
  }
}
