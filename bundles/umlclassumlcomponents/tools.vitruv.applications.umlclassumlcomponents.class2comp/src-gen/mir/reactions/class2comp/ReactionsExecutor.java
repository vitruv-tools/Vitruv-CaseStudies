package mir.reactions.class2comp;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.class2comp.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.class2comp.CreatedClassModelReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.RenamedClassModelReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.RenamedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.CreatedUmlClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.RenameClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.DeletedClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.CreatedPrimitiveDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.CreatedDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.AddedDataClassTypePropertyReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.ChangedDataTypeClassPropertyReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.DeletedDataTypeClassPropertyReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.ChangedDataTypeClassAttributeTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.AddedDataClassTypeOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.ChangedDataTypeClassOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.DeletedDataTypeClassOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.CreatePackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.MoveClassToDifferentPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.PackageRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.PackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.ElementVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.InterfaceRealizedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.RemovedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.class2comp.RemovedInterfaceRealizationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("class2comp"))));
  }
}
