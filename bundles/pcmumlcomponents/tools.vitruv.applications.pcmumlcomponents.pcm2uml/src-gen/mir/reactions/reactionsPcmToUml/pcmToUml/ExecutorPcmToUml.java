package mir.reactions.reactionsPcmToUml.pcmToUml;

import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPcmToUml extends AbstractReactionsExecutor {
  public ExecutorPcmToUml() {
    super(new PcmDomainProvider().getDomain(), 
    	new UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.RenamedElementReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.RenamedElementReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPrimitiveDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPrimitiveDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.ChangedInnerDeclarationTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedInnerDeclarationTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.AddedCompositeDataTypeParentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.AddedCompositeDataTypeParentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.RemovedCompositeDataTypeParentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.RemovedCompositeDataTypeParentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.ChangedCollectionDataTypeInnerTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedCollectionDataTypeInnerTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedPcmInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedPcmInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationInterfaceSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationInterfaceSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.ChangedOperationSignatureTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedOperationSignatureTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationInterfaceSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationInterfaceSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationSignatureParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedOperationSignatureParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.RenamedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.RenamedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterDirectionReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterDirectionReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.ChangedParameterTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationSignatureParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedOperationSignatureParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedBasicPcmComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedBasicPcmComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositePcmComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedCompositePcmComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationProvidedRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationProvidedRoleInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.DeletedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.DeletedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationRequiredRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.AddedOperationRequiredRoleInterfaceReaction(userInteracting));
  }
}
