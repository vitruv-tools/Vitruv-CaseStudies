package mir.routines.umlToJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacadesProvider extends AbstractRoutinesFacadesProvider {
  public AbstractRepairRoutinesFacade createRoutinesFacade(final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState sharedExecutionState) {
    switch(reactionsImportPath.getPathString()) {
    	case "umlToJava": {
    		return new mir.routines.umlToJava.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "umlToJava.umlToJavaAttribute": {
    		return new mir.routines.umlToJavaAttribute.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "umlToJava.umlToJavaAttribute.umlToJavaTypePropagation": {
    		return new mir.routines.umlToJavaTypePropagation.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "umlToJava.umlToJavaClassifier": {
    		return new mir.routines.umlToJavaClassifier.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "umlToJava.umlToJavaMethod": {
    		return new mir.routines.umlToJavaMethod.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "umlToJava.umlToJavaMethod.umlToJavaTypePropagation": {
    		return new mir.routines.umlToJavaTypePropagation.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	default: {
    		throw new IllegalArgumentException("Unexpected import path: " + reactionsImportPath.getPathString());
    	}
    }
  }
}
