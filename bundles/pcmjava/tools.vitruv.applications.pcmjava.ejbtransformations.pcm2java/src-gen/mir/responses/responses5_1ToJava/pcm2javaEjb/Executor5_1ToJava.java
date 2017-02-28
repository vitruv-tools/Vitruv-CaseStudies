package mir.responses.responses5_1ToJava.pcm2javaEjb;

import tools.vitruv.extensions.dslsruntime.response.AbstractResponseExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class Executor5_1ToJava extends AbstractResponseExecutor {
  public Executor5_1ToJava(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  protected void setup() {
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedRepositoryResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedRepositoryResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.RenamedRepositoryResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.RenamedRepositoryResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.RenameComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.RenameComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.RenamedInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.RenamedInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.RenamedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.RenamedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.RenamedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.RenamedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedInnerDeclarationResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.RenameInnerDeclarationResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.RenameInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.ChangeTypeOfInnerDeclarationResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.ChangeTypeOfInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedProvidedRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.ChangedProvidedInterfaceOfProvidedRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.ChangedProvidedInterfaceOfProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.ChangedProvidingEntityOfProvidedRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.ChangedProvidingEntityOfProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedProvidedRoleFromSystemResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedProvidedRoleFromSystemResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedProvidedRoleFromComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedProvidedRoleFromComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedRequiredRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedRequiredRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedRequiredRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedRequiredRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.RenameOperationRequiredRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.RenameOperationRequiredRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.ChangeOperationRequiredRoleEntityResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.ChangeOperationRequiredRoleEntityResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.ChangeOperationRequiredRoleInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.ChangeOperationRequiredRoleInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.RenameOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.RenameOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.ChangeOperationSignatureReturnTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.ChangeOperationSignatureReturnTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedParameterResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.RenamedParameterResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.RenamedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.ChangedParameterTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.ChangedParameterTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedParameterResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedResourceDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.RenameResourceDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.RenameResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedSEFFResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.CreatedSEFFResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.ChangeOperationSignatureOfSeffResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.ChangeOperationSignatureOfSeffResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedSeffResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2javaEjb.DeletedSeffResponse(userInteracting));
  }
}
