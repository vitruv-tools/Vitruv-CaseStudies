package mir.reactions.reactionsUmlToUml.class2comp;

import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUmlToUml extends AbstractReactionsExecutor {
  public ExecutorUmlToUml(final UserInteracting userInteracting) {
    super(userInteracting,
    	new UmlDomainProvider().getDomain(), 
    	new UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.CreatedClassModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.CreatedClassModelReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.RenamedClassModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.RenamedClassModelReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.RenamedElementReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.RenamedElementReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.CreatedUmlClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.CreatedUmlClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.RenameClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.RenameClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.DeletedClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.DeletedClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.CreatedPrimitiveDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.CreatedPrimitiveDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.CreatedDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.CreatedDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.AddedDataClassTypePropertyReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.AddedDataClassTypePropertyReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassPropertyReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassPropertyReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.DeletedDataTypeClassPropertyReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.DeletedDataTypeClassPropertyReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassAttributeTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassAttributeTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.AddedDataClassTypeOperationReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.AddedDataClassTypeOperationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassOperationReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassOperationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.DeletedDataTypeClassOperationReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.DeletedDataTypeClassOperationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.CreatePackageReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.CreatePackageReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.MoveClassToDifferentPackageReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.MoveClassToDifferentPackageReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.PackageRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.PackageRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.PackageDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.PackageDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.ElementVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.ElementVisibilityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.InterfaceRealizedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.InterfaceRealizedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.RemovedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.RemovedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.class2comp.RemovedInterfaceRealizationReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.class2comp.RemovedInterfaceRealizationReaction(userInteracting));
  }
}
