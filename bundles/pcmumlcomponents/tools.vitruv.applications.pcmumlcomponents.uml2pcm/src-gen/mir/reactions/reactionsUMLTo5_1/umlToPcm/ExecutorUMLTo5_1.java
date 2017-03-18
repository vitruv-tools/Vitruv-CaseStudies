package mir.reactions.reactionsUMLTo5_1.umlToPcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUMLTo5_1 extends AbstractReactionsExecutor {
  public ExecutorUMLTo5_1(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI, org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.RenamedElementReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.RenamedElementReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.DeletedElementReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.DeletedElementReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedUmlModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedUmlModelReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedPrimitiveDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedPrimitiveDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedPropertyForDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedPropertyForDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedPropertyOwnerForDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedPropertyOwnerForDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedPropertyTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedPropertyTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedInterfaceOperationReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.CreatedInterfaceOperationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedInterfaceOperationTypeIReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedInterfaceOperationTypeIReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedInterfaceOperationTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedInterfaceOperationTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedParameterTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedParameterNameReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedParameterNameReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedParameterDirectionReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLTo5_1.umlToPcm.ChangedParameterDirectionReaction(userInteracting));
  }
}
