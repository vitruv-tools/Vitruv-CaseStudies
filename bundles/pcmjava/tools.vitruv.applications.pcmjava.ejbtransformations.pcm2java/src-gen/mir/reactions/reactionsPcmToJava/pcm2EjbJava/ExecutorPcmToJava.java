package mir.reactions.reactionsPcmToJava.pcm2EjbJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
public class ExecutorPcmToJava extends AbstractReactionsExecutor {
  public ExecutorPcmToJava() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.pcm2EjbJava.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedCollectionDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeTypeOfInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidedInterfaceOfProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedProvidingEntityOfProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedProvidedRoleFromComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenameOperationRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleEntityReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationRequiredRoleInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedOperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureReturnTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedOperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.RenamedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangedParameterTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.CreatedSEFFReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ChangeOperationSignatureOfSeffReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
    this.addReaction(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.DeletedSeffReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Pcm2EjbJava"))));
  }
}
