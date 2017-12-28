package mir.reactions.reactionsPcmToJava.pcm2javaCommon;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedRepositoryReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenamedRepositoryReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenameComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenamedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenamedCollectionDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedCollectionDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenameInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangeTypeOfInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangedProvidedInterfaceOfProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangedProvidingEntityOfProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedProvidedRoleFromComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedOperationSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangeOperationSignatureReturnTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedOperationSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenamedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangedParameterTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedSEFFReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangeOperationSignatureOfSeffReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedSeffReaction());
  }
}
