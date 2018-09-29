package mir.reactions.pcmDataTypePropagationReactions;

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
    return new mir.routines.pcmDataTypePropagationReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.pcmDataTypePropagationReactions.UnsupportedPrimitiveTypeSetAtInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmDataTypePropagationReactions"))));
    this.addReaction(new mir.reactions.pcmDataTypePropagationReactions.UnsupportedPrimitiveTypeSetAtParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmDataTypePropagationReactions"))));
    this.addReaction(new mir.reactions.pcmDataTypePropagationReactions.UnsupportedPrimitiveTypeSetAtSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmDataTypePropagationReactions"))));
  }
}
