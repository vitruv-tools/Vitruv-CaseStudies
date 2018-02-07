package mir.reactions.reactionsUmlToUml.class2comp;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
public class ExecutorUmlToUml extends AbstractReactionsExecutor {
  public ExecutorUmlToUml() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.class2comp.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.CreatedClassModelReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.RenamedClassModelReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.RenamedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.CreatedUmlClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.RenameClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.DeletedClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.CreatedPrimitiveDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.CreatedDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.AddedDataClassTypePropertyReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassPropertyReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.DeletedDataTypeClassPropertyReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassAttributeTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.AddedDataClassTypeOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.ChangedDataTypeClassOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.DeletedDataTypeClassOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.CreatePackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.MoveClassToDifferentPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.PackageRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.PackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.ElementVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.InterfaceRealizedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.RemovedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.class2comp.RemovedInterfaceRealizationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("class2comp"))));
  }
}
