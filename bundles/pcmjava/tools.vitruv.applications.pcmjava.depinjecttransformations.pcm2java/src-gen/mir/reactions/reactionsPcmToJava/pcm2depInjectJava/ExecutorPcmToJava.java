package mir.reactions.reactionsPcmToJava.pcm2depInjectJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedRepositoryReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedRepositoryReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedSystemReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedSystemReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedSystemReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedSystemReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedSystemNameReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedSystemNameReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.AddedAssemblyContextToComposedStructureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.AddedAssemblyContextToComposedStructureReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.AddedConnectorReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.AddedConnectorReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.AddedProvidedDelegationConnectorReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.AddedProvidedDelegationConnectorReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedCompositeDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedCompositeDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedCompositeDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedCollectionDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedCollectionDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedCollectionDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedInnerDeclarationReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameInnerDeclarationReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeTypeOfInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeTypeOfInnerDeclarationReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedProvidedRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedProvidedInterfaceOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedProvidedInterfaceOfProvidedRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedProvidingEntityOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedProvidingEntityOfProvidedRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedProvidedRoleFromSystemReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedProvidedRoleFromSystemReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedProvidedRoleFromComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedProvidedRoleFromComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedRequiredRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedRequiredRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameOperationRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameOperationRequiredRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationRequiredRoleEntityReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationRequiredRoleEntityReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationRequiredRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationRequiredRoleInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedOperationSignatureReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenameOperationSignatureReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationSignatureReturnTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationSignatureReturnTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedOperationSignatureReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.RenamedParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedParameterTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedSEFFReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.CreatedSEFFReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationSignatureOfSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangeOperationSignatureOfSeffReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedSeffReaction());
  }
}
