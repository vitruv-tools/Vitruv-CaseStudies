package mir.reactions.reactionsPcmToJava.pcm2java;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedRepositoryReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenamedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenamedRepositoryReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedSystemReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedSystemReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedSystemReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedSystemReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangedSystemNameReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangedSystemNameReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.AddedAssemblyContextToComposedStructureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.AddedAssemblyContextToComposedStructureReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenameComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenameComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenamedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenamedInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedCompositeDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenamedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenamedCompositeDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedCompositeDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedCollectionDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenamedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenamedCollectionDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedCollectionDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedInnerDeclarationReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenameInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenameInnerDeclarationReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangeTypeOfInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangeTypeOfInnerDeclarationReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedProvidedRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromSystemReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromSystemReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedRequiredRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedRequiredRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationRequiredRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleEntityReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleEntityReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedOperationSignatureReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationSignatureReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureReturnTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureReturnTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedOperationSignatureReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenamedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenamedParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangedParameterTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedSEFFReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedSEFFReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureOfSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureOfSeffReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedSeffReaction());
  }
}
