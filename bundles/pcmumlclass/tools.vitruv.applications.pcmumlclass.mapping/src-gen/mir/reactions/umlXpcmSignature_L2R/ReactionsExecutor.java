package mir.reactions.umlXpcmSignature_L2R;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.umlXpcmSignature_L2R.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlXpcmSignature_L2R.OnSignatureInterfaceInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_L2R.OnSignatureInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_L2R.OnSignatureOperationInsertedInInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_L2R.OnSignatureOperationRemovedFromInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_L2R.OnSignatureOperationDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_L2R.OnSignatureParameterInsertedInOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_L2R.OnSignatureParameterRemovedFromOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_L2R.OnSignatureParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_L2R.OnSignatureNameReplacedAtParameter_nameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_L2R.OnSignatureNameReplacedAtOperation_nameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_L2R.OnSignatureElementReplacedAtParameter_typeBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_L2R"))));
  }
}
