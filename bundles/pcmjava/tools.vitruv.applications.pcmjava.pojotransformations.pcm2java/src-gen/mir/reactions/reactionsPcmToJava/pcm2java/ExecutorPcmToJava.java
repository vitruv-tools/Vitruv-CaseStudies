package mir.reactions.reactionsPcmToJava.pcm2java;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava() {
    super(new PcmDomainProvider().getDomain(), 
    	new JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenamedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenamedRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedSystemReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedSystemReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedSystemReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedSystemReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangedSystemNameReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangedSystemNameReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.AddedAssemblyContextToComposedStructureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.AddedAssemblyContextToComposedStructureReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenameComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenameComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenamedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenamedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenamedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenamedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenamedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenamedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenameInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenameInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangeTypeOfInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangeTypeOfInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromSystemReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromSystemReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleEntityReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleEntityReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedOperationSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureReturnTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureReturnTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedOperationSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenamedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenamedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangedParameterTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedResourceDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedResourceDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.RenameResourceDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.RenameResourceDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.CreatedSEFFReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.CreatedSEFFReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureOfSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureOfSeffReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2java.DeletedSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2java.DeletedSeffReaction(userInteracting));
  }
}
