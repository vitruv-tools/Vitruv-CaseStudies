package mir.reactions.reactionsPcmToJava.pcm2javaCommon;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.pcm2javaCommon.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenamedRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedSystemReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedSystemReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangedSystemNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.AddedAssemblyContextToComposedStructureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenameComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenamedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenamedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenamedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenameInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangeTypeOfInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangedProvidedInterfaceOfProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangedProvidingEntityOfProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedProvidedRoleFromSystemReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedProvidedRoleFromComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenameOperationRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangeOperationRequiredRoleInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedOperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenameOperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangeOperationSignatureReturnTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedOperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.RenamedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangedParameterTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.CreatedSEFFReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ChangeOperationSignatureOfSeffReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.DeletedSeffReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2javaCommon"))));
  }
}
