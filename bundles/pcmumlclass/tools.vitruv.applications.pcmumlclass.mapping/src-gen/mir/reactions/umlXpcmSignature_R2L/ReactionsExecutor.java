package mir.reactions.umlXpcmSignature_R2L;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.umlXpcmSignature_R2L.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureDataTypeInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureDataTypeDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureOperationInterfaceInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureOperationInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureOperationSignatureInsertedInOperationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureOperationSignatureRemovedFromOperationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureOperationSignatureDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureDataTypeReplacedAtOperationSignature_returnType__OperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureEntityNameReplacedAtOperationSignature_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureElementReplacedAtOperationSignature_returnType__OperationSignatureBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmSignature_R2L"))));
  }
}
