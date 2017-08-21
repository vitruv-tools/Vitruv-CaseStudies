package mir.reactions.reactionsPcmToUml.pcmToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPcmToUml extends AbstractReactionsExecutor {
  public ExecutorPcmToUml() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.RenamedElementReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.RenamedElementReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmRepositoryReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPrimitiveDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPrimitiveDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositeDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedInnerDeclarationReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.ChangedInnerDeclarationTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedInnerDeclarationTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedInnerDeclarationReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.AddedCompositeDataTypeParentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.AddedCompositeDataTypeParentReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.RemovedCompositeDataTypeParentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.RemovedCompositeDataTypeParentReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.ChangedCollectionDataTypeInnerTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedCollectionDataTypeInnerTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedPcmInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedPcmInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationInterfaceSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationInterfaceSignatureReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.ChangedOperationSignatureTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedOperationSignatureTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationInterfaceSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationInterfaceSignatureReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationSignatureParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationSignatureParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.RenamedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.RenamedParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterDirectionReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterDirectionReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationSignatureParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationSignatureParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedBasicPcmComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedBasicPcmComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositePcmComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositePcmComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedProvidedRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedProvidedRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationProvidedRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationProvidedRoleInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedRequiredRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedRequiredRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationRequiredRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationRequiredRoleInterfaceReaction());
  }
}
