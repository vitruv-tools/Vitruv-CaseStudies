package mir.routines.combinedUmlClassToPcmReactions;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacadesProvider extends AbstractRoutinesFacadesProvider {
  public AbstractRepairRoutinesFacade createRoutinesFacade(final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState sharedExecutionState) {
    switch(reactionsImportPath.getPathString()) {
    	case "combinedUmlClassToPcmReactions": {
    		return new mir.routines.combinedUmlClassToPcmReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlRepositoryAndSystemPackageReactions": {
    		return new mir.routines.umlRepositoryAndSystemPackageReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlInterfaceReactions": {
    		return new mir.routines.umlInterfaceReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlInterfaceGeneralizationReactions": {
    		return new mir.routines.umlInterfaceGeneralizationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlSignatureOperationReactions": {
    		return new mir.routines.umlSignatureOperationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlRegularParameterReactions": {
    		return new mir.routines.umlRegularParameterReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlReturnAndRegularParameterTypeReactions": {
    		return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlCompositeDataTypeClassReactions": {
    		return new mir.routines.umlCompositeDataTypeClassReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlCompositeDataTypeGeneralizationReactions": {
    		return new mir.routines.umlCompositeDataTypeGeneralizationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlInnerDeclarationPropertyReactions": {
    		return new mir.routines.umlInnerDeclarationPropertyReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlRepositoryComponentPackageReactions": {
    		return new mir.routines.umlRepositoryComponentPackageReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlIPREClassReactions": {
    		return new mir.routines.umlIPREClassReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlIPREConstructorOperationReactions": {
    		return new mir.routines.umlIPREConstructorOperationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlProvidedRoleGeneralizationReactions": {
    		return new mir.routines.umlProvidedRoleGeneralizationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlRequiredRoleParameterReactions": {
    		return new mir.routines.umlRequiredRoleParameterReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlRequiredRolePropertyReactions": {
    		return new mir.routines.umlRequiredRolePropertyReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedUmlClassToPcmReactions.umlAssemblyContextPropertyReactions": {
    		return new mir.routines.umlAssemblyContextPropertyReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	default: {
    		throw new IllegalArgumentException("Unexpected import path: " + reactionsImportPath.getPathString());
    	}
    }
  }
}
