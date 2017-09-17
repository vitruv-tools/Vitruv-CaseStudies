package mir.reactions.reactionsPcmToJava.pcm2EjbJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRepositoryReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedRepositoryReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCollectionDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCollectionDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCollectionDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeTypeOfInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidedInterfaceOfProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidingEntityOfProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedProvidedRoleFromComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameOperationRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleEntityReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedOperationSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureReturnTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedOperationSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedParameterTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedSEFFReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureOfSeffReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedSeffReaction());
  }
}
