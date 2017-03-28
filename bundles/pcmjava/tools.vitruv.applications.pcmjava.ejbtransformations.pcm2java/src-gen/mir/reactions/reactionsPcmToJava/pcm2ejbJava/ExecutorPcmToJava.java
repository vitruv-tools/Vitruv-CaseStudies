package mir.reactions.reactionsPcmToJava.pcm2ejbJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI, org.emftext.language.java.impl.JavaPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenamedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenamedRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenameComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenameComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenamedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenamedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenamedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenamedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenamedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenamedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenameInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenameInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangeTypeOfInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangeTypeOfInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangedProvidedInterfaceOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangedProvidedInterfaceOfProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangedProvidingEntityOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangedProvidingEntityOfProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedProvidedRoleFromComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedProvidedRoleFromComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenameOperationRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenameOperationRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangeOperationRequiredRoleEntityReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangeOperationRequiredRoleEntityReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangeOperationRequiredRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangeOperationRequiredRoleInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedOperationSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangeOperationSignatureReturnTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangeOperationSignatureReturnTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedOperationSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenamedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenamedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangedParameterTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedResourceDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedResourceDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenameResourceDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.RenameResourceDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedSEFFReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.CreatedSEFFReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangeOperationSignatureOfSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.ChangeOperationSignatureOfSeffReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedSeffReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToJava.pcm2ejbJava.DeletedSeffReaction(userInteracting));
  }
}
