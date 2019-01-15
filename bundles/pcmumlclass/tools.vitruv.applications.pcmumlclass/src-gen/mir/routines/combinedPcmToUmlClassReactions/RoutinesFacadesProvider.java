package mir.routines.combinedPcmToUmlClassReactions;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacadesProvider extends AbstractRoutinesFacadesProvider {
  public AbstractRepairRoutinesFacade createRoutinesFacade(final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState sharedExecutionState) {
    switch(reactionsImportPath.getPathString()) {
    	case "combinedPcmToUmlClassReactions": {
    		return new mir.routines.combinedPcmToUmlClassReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmRepositoryReactions": {
    		return new mir.routines.pcmRepositoryReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmSystemReactions": {
    		return new mir.routines.pcmSystemReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmInterfaceReactions": {
    		return new mir.routines.pcmInterfaceReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmSignatureReactions": {
    		return new mir.routines.pcmSignatureReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmSignatureReactions.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmParameterReactions": {
    		return new mir.routines.pcmParameterReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmParameterReactions.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmCompositeDataTypeReactions": {
    		return new mir.routines.pcmCompositeDataTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmInnerDeclarationReactions": {
    		return new mir.routines.pcmInnerDeclarationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmInnerDeclarationReactions.pcmDataTypePropagationReactions": {
    		return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmCollectionDataTypeReactions": {
    		return new mir.routines.pcmCollectionDataTypeReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmRepositoryComponentReactions": {
    		return new mir.routines.pcmRepositoryComponentReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmRequiredRoleReactions": {
    		return new mir.routines.pcmRequiredRoleReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmProvidedRoleReactions": {
    		return new mir.routines.pcmProvidedRoleReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	case "combinedPcmToUmlClassReactions.pcmAssemblyContextReactions": {
    		return new mir.routines.pcmAssemblyContextReactions.RoutinesFacade(this, reactionsImportPath, sharedExecutionState);
    	}
    	default: {
    		throw new IllegalArgumentException("Unexpected import path: " + reactionsImportPath.getPathString());
    	}
    }
  }
}
