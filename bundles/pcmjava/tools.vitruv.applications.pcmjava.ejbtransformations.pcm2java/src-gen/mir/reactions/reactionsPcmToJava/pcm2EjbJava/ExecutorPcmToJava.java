package mir.reactions.reactionsPcmToJava.pcm2EjbJava;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava(final UserInteracting userInteracting) {
    super(userInteracting,
    	new PcmDomainProvider().getDomain(), 
    	new JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeTypeOfInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeTypeOfInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidedInterfaceOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidedInterfaceOfProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidingEntityOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidingEntityOfProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedProvidedRoleFromComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedProvidedRoleFromComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameOperationRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameOperationRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleEntityReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleEntityReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedOperationSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureReturnTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureReturnTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedOperationSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedParameterTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedResourceDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedResourceDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameResourceDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameResourceDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedSEFFReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedSEFFReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureOfSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureOfSeffReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedSeffReaction(userInteracting));
  }
}
