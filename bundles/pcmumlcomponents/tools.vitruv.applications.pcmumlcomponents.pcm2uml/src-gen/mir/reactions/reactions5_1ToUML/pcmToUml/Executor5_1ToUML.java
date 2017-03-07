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
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedOperationInterfaceSignatureReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedOperationInterfaceSignatureReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.ChangedOperationSignatureTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.ChangedOperationSignatureTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPcmComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPcmComponentReaction(userInteracting));
  }
}
