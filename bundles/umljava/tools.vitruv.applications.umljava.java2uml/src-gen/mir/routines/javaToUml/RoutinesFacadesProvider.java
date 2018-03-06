package mir.routines.javaToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacadesProvider extends AbstractRoutinesFacadesProvider {
  public AbstractRepairRoutinesFacade createRoutinesFacade(final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState sharedExecutionState) {
    switch(reactionsImportPath.getPathString()) {
    	case "javaToUml": {
    		return new mir.routines.javaToUml.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "javaToUml.javaToUmlAttribute": {
    		return new mir.routines.javaToUmlAttribute.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "javaToUml.javaToUmlClassifier": {
    		return new mir.routines.javaToUmlClassifier.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "javaToUml.javaToUmlMethod": {
    		return new mir.routines.javaToUmlMethod.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	default: {
    		throw new IllegalArgumentException("Unexpected import path: " + reactionsImportPath.getPathString());
    	}
    }
  }
}
