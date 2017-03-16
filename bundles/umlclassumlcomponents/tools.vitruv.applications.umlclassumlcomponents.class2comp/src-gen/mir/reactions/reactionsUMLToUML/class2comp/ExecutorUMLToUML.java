package mir.reactions.reactionsUMLToUML.class2comp;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUMLToUML extends AbstractReactionsExecutor {
  public ExecutorUMLToUML(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI, org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.CreatedUmlClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.CreatedUmlClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.RenameClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.RenameClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.DeletedClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.DeletedClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.RenamedElementReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.RenamedElementReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.CreatedClassModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.CreatedClassModelReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.RenamedClassModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.RenamedClassModelReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.CreatedPrimitiveDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.CreatedPrimitiveDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.CreatedDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.CreatedDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.ChangedPropertyTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.ChangedPropertyTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.ClassAttributeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.ClassAttributeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.ClassAttributeRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.ClassAttributeRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToUML.class2comp.ClassAttributeDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToUML.class2comp.ClassAttributeDeletedReaction(userInteracting));
  }
}
