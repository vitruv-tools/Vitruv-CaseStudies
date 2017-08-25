package mir.reactions.reactionsPcmToJava.pcm2EjbJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRepositoryReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedRepositoryReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCompositeDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCompositeDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCompositeDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCollectionDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCollectionDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCollectionDataTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInnerDeclarationReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameInnerDeclarationReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeTypeOfInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeTypeOfInnerDeclarationReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedProvidedRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidedInterfaceOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidedInterfaceOfProvidedRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidingEntityOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidingEntityOfProvidedRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedProvidedRoleFromComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedProvidedRoleFromComponentReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRequiredRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedRequiredRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameOperationRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameOperationRequiredRoleReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleEntityReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleEntityReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleInterfaceReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedOperationSignatureReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureReturnTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureReturnTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedOperationSignatureReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedParameterTypeReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedParameterReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedSEFFReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedSEFFReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureOfSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureOfSeffReaction());
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedSeffReaction());
  }
}
