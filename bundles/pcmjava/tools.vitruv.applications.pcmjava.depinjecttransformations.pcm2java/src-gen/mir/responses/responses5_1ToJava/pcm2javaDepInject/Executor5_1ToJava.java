package mir.responses.responses5_1ToJava.pcm2javaDepInject;

import tools.vitruv.extensions.dslsruntime.response.AbstractResponseExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class Executor5_1ToJava extends AbstractResponseExecutor {
  public Executor5_1ToJava(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  protected void setup() {
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedRepositoryResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedRepositoryResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.RenamedRepositoryResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.RenamedRepositoryResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedSystemResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedSystemResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedSystemResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedSystemResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangedSystemNameResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangedSystemNameResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.AddedAssemblyContextToComposedStructureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.AddedAssemblyContextToComposedStructureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.AddedConnectorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.AddedConnectorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.AddedProvidedDelegationConnectorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.AddedProvidedDelegationConnectorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.RenameComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.RenameComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.RenamedInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.RenamedInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.RenamedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.RenamedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.RenamedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.RenamedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedInnerDeclarationResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.RenameInnerDeclarationResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.RenameInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangeTypeOfInnerDeclarationResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangeTypeOfInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedProvidedRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangedProvidedInterfaceOfProvidedRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangedProvidedInterfaceOfProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangedProvidingEntityOfProvidedRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangedProvidingEntityOfProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedProvidedRoleFromSystemResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedProvidedRoleFromSystemResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedProvidedRoleFromComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedProvidedRoleFromComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedRequiredRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedRequiredRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedRequiredRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedRequiredRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.RenameOperationRequiredRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.RenameOperationRequiredRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangeOperationRequiredRoleEntityResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangeOperationRequiredRoleEntityResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangeOperationRequiredRoleInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangeOperationRequiredRoleInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.RenameOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.RenameOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangeOperationSignatureReturnTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangeOperationSignatureReturnTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedParameterResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.RenamedParameterResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.RenamedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangedParameterTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangedParameterTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedParameterResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedResourceDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.RenameResourceDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.RenameResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedSEFFResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.CreatedSEFFResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangeOperationSignatureOfSeffResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.ChangeOperationSignatureOfSeffResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedSeffResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaDepInject.DeletedSeffResponse(userInteracting));
  }
}
