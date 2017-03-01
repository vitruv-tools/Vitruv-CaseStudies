package mir.reactions.reactions5_1ToJava.pcm2depInjectJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class Executor5_1ToJava extends AbstractReactionsExecutor {
  public Executor5_1ToJava(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI, org.emftext.language.java.impl.JavaPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenamedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenamedRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedSystemReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedSystemReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedSystemReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedSystemReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangedSystemNameReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangedSystemNameReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.AddedAssemblyContextToComposedStructureReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.AddedAssemblyContextToComposedStructureReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.AddedConnectorReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.AddedConnectorReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.AddedProvidedDelegationConnectorReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.AddedProvidedDelegationConnectorReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenameComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenameComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenamedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenamedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenamedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenamedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenamedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenamedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenameInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenameInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangeTypeOfInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangeTypeOfInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangedProvidedInterfaceOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangedProvidedInterfaceOfProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangedProvidingEntityOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangedProvidingEntityOfProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedProvidedRoleFromSystemReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedProvidedRoleFromSystemReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedProvidedRoleFromComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedProvidedRoleFromComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenameOperationRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenameOperationRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangeOperationRequiredRoleEntityReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangeOperationRequiredRoleEntityReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangeOperationRequiredRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangeOperationRequiredRoleInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedOperationSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenameOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenameOperationSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangeOperationSignatureReturnTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangeOperationSignatureReturnTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedOperationSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedParameterReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenamedParameterReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenamedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangedParameterTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedParameterReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedResourceDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedResourceDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenameResourceDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.RenameResourceDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedDemandingInternalBehaviorReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedSEFFReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.CreatedSEFFReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangeOperationSignatureOfSeffReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.ChangeOperationSignatureOfSeffReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedSeffReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2depInjectJava.DeletedSeffReaction(userInteracting));
  }
}
