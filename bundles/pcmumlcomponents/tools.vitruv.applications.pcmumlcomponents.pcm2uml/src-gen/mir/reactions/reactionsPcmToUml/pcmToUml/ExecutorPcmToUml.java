package mir.reactions.reactionsPcmToUml.pcmToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
class ExecutorPcmToUml extends AbstractReactionsExecutor {
  public ExecutorPcmToUml() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.pcmToUml.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.RenamedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPrimitiveDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedInnerDeclarationTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.AddedCompositeDataTypeParentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.RemovedCompositeDataTypeParentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedCollectionDataTypeInnerTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedPcmInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationInterfaceSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedOperationSignatureTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationInterfaceSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationSignatureParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.RenamedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterDirectionReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationSignatureParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedBasicPcmComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositePcmComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationProvidedRoleInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationRequiredRoleInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
  }
}
