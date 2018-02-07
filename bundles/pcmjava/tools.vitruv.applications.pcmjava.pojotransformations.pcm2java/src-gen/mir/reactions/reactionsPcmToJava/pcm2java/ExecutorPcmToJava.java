package mir.reactions.reactionsPcmToJava.pcm2java;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.pcm2java.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenamedRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedSystemReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedSystemReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangedSystemNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.AddedAssemblyContextToComposedStructureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenameComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenamedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenamedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenamedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenameInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangeTypeOfInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromSystemReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedProvidedRoleFromComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationRequiredRoleInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedOperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenameOperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureReturnTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedOperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.RenamedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangedParameterTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.CreatedSEFFReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.ChangeOperationSignatureOfSeffReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2java.DeletedSeffReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("pcm2java"))));
  }
}
