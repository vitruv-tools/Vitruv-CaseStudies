package mir.reactions.reactionsUmlToUml.class2comp;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUmlToUml extends AbstractReactionsExecutor {
  public ExecutorUmlToUml(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI, org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.CreatedClassModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.CreatedClassModelReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.RenamedClassModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.RenamedClassModelReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.CreatedUmlClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.CreatedUmlClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.RenameClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.RenameClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.DeletedClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.DeletedClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.RenamedElementReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.RenamedElementReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.CreatedPrimitiveDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.CreatedPrimitiveDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.CreatedDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.CreatedDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.ClassAttributeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.ClassAttributeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.ClassAttributeRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.ClassAttributeRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.ClassAttributeDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.ClassAttributeDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.ChangedPropertyTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.ChangedPropertyTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.CreatePackageReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.CreatePackageReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.MoveClassToDifferentPackageReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.MoveClassToDifferentPackageReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.CreatedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.InterfaceRealizedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.InterfaceRealizedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.InterfaceDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.InterfaceDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.AddedUsesRelationshipReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.AddedUsesRelationshipReaction(userInteracting));
  }
}
