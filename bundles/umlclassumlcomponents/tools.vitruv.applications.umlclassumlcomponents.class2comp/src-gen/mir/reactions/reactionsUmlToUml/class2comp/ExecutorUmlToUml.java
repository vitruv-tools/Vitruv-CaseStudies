package mir.reactions.reactionsUmlToUml.class2comp;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorUmlToUml extends AbstractReactionsExecutor {
  public ExecutorUmlToUml() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.CreatedClassModelReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.RenamedClassModelReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.RenamedElementReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.CreatedUmlClassReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.RenameClassReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.DeletedClassReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.CreatedPrimitiveDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.CreatedDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.AddedDataClassTypePropertyReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassPropertyReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.DeletedDataTypeClassPropertyReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassAttributeTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.AddedDataClassTypeOperationReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassOperationReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.DeletedDataTypeClassOperationReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.CreatePackageReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.MoveClassToDifferentPackageReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.PackageRenamedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.PackageDeletedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.ElementVisibilityChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.InterfaceRealizedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.RemovedInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.RemovedInterfaceRealizationReaction());
  }
}
