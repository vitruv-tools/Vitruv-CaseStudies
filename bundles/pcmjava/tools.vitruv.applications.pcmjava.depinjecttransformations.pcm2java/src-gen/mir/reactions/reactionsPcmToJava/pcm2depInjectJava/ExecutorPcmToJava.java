package mir.reactions.reactionsPcmToJava.pcm2depInjectJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedRepositoryReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedRepositoryReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedSystemReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedSystemReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedSystemNameReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.AddedAssemblyContextToComposedStructureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.AddedConnectorReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.AddedProvidedDelegationConnectorReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedCollectionDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedCollectionDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedCollectionDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeTypeOfInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedProvidedInterfaceOfProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedProvidingEntityOfProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedProvidedRoleFromSystemReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedProvidedRoleFromComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameOperationRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationRequiredRoleEntityReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationRequiredRoleInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedOperationSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameOperationSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationSignatureReturnTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedOperationSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedParameterTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedSEFFReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationSignatureOfSeffReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedSeffReaction());
  }
}
