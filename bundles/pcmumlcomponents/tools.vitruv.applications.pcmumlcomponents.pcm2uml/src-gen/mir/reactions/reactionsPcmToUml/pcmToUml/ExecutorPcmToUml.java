package mir.reactions.reactionsPcmToUml.pcmToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPcmToUml extends AbstractReactionsExecutor {
  public ExecutorPcmToUml() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.RenamedElementReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmRepositoryReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPrimitiveDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedInnerDeclarationTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.AddedCompositeDataTypeParentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.RemovedCompositeDataTypeParentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedCollectionDataTypeInnerTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedPcmInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationInterfaceSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedOperationSignatureTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationInterfaceSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationSignatureParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.RenamedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterDirectionReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationSignatureParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedBasicPcmComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositePcmComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationProvidedRoleInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationRequiredRoleInterfaceReaction());
  }
}
