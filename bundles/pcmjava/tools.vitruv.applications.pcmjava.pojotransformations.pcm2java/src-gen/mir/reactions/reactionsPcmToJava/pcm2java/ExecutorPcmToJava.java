package mir.reactions.reactionsPcmToJava.pcm2java;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedRepositoryReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenamedRepositoryReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedSystemReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedSystemReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangedSystemNameReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.AddedAssemblyContextToComposedStructureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenameComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenamedInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenamedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedCompositeDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedCollectionDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenamedCollectionDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedCollectionDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenameInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangeTypeOfInnerDeclarationReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromSystemReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromComponentReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedOperationSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureReturnTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedOperationSignatureReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenamedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangedParameterTypeReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedParameterReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedSEFFReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureOfSeffReaction());
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedSeffReaction());
  }
}
