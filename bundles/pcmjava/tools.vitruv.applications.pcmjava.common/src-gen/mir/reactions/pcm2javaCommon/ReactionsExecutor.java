package mir.reactions.pcm2javaCommon;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.pcm2javaCommon.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.RenamedRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedSystemReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.DeletedSystemReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.ChangedSystemNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.AddedAssemblyContextToComposedStructureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.RenameComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.DeletedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.RenamedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.RenamedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.DeletedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.RenamedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.DeletedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.RenameInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.ChangeTypeOfInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.ChangedProvidedInterfaceOfProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.ChangedProvidingEntityOfProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.DeletedProvidedRoleFromSystemReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.DeletedProvidedRoleFromComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.DeletedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.RenameOperationRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.ChangeOperationRequiredRoleInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedOperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.RenameOperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.ChangeOperationSignatureReturnTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.DeletedOperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.RenamedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.ChangedParameterTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.DeletedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.CreatedSEFFReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.ChangeOperationSignatureOfSeffReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.pcm2javaCommon.DeletedSeffReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcm2javaCommon"))));
  }
}
