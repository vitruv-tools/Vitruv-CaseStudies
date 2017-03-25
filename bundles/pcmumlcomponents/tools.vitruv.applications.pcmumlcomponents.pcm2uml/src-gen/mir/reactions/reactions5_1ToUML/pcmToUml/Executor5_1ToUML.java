package mir.reactions.reactions5_1ToUML.pcmToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class Executor5_1ToUML extends AbstractReactionsExecutor {
  public Executor5_1ToUML(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI, org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.RenamedElementReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.RenamedElementReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPcmRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPcmRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPrimitiveDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPrimitiveDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.DeletedDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.DeletedDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedCompositeDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.DeletedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.DeletedInnerDeclarationReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedCollectionDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedCollectionDataTypeTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedCollectionDataTypeTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.ChangedCollectionDataTypeTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.ChangedCollectionDataTypeTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPcmInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPcmInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.DeletedPcmInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.DeletedPcmInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedOperationInterfaceSignatureReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedOperationInterfaceSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.ChangedOperationSignatureTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.ChangedOperationSignatureTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.DeletedOperationInterfaceSignatureReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.DeletedOperationInterfaceSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedOperationSignatureParameterReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedOperationSignatureParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.RenamedParameterReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.RenamedParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.ChangedParameterDirectionReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.ChangedParameterDirectionReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.ChangedParameterTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.DeletedOperationSignatureParameterReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.DeletedOperationSignatureParameterReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedBasicPcmComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedBasicPcmComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedCompositePcmComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedCompositePcmComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.DeletedComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.DeletedComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.DeletedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.DeletedProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.AddedOperationProvidedRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.AddedOperationProvidedRoleInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.DeletedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.DeletedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.AddedOperationRequiredRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.AddedOperationRequiredRoleInterfaceReaction(userInteracting));
  }
}
