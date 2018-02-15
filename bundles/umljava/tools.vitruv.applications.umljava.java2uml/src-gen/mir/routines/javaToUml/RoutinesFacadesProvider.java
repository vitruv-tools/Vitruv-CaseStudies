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
    		return new mir.routines.javaToUml.RoutinesFacade(this, reactionsImportPath.getParent(), sharedExecutionState);
    	}
    	case "javaToUml.JavaToUmlAttribute": {
    		return new mir.routines.javaToUmlAttribute.RoutinesFacade(this, reactionsImportPath.getParent(), sharedExecutionState);
    	}
    	case "javaToUml.JavaToUmlClassifier": {
    		return new mir.routines.javaToUmlClassifier.RoutinesFacade(this, reactionsImportPath.getParent(), sharedExecutionState);
    	}
    	case "javaToUml.JavaToUmlMethod": {
    		return new mir.routines.javaToUmlMethod.RoutinesFacade(this, reactionsImportPath.getParent(), sharedExecutionState);
    	}
    	default: {
    		return null;
    	}
    }
  }
}
