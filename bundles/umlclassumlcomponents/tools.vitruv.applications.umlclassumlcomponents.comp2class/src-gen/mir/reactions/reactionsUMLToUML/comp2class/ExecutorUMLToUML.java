package mir.reactions.reactionsUMLToUML.comp2class;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUMLToUML extends AbstractReactionsExecutor {
  public ExecutorUMLToUML(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI, org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUMLToUML.comp2class.CreatedCompModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.comp2class.CreatedCompModelReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.comp2class.RenamedComponentModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.comp2class.RenamedComponentModelReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.comp2class.CreatedUmlComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.comp2class.CreatedUmlComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.comp2class.RenameComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.comp2class.RenameComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.comp2class.DeletedCompReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.comp2class.DeletedCompReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.comp2class.RenamedElementReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.comp2class.RenamedElementReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.comp2class.CreatedDatatypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.comp2class.CreatedDatatypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.comp2class.AddedDataTypePropertyReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.comp2class.AddedDataTypePropertyReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.comp2class.ChangedDataTypePropertyReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.comp2class.ChangedDataTypePropertyReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.comp2class.AddedDataTypeOperationReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.comp2class.AddedDataTypeOperationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.comp2class.ChangedDataTypeOperationReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.comp2class.ChangedDataTypeOperationReaction(userInteracting));
  }
}
