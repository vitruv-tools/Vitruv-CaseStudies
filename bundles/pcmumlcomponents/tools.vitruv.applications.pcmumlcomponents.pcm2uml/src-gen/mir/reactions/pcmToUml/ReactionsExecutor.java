package mir.reactions.pcmToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.pcmToUml.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.pcmToUml.RenamedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.CreatedPcmRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.CreatedPrimitiveDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.DeletedDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.CreatedCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.CreatedInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.ChangedInnerDeclarationTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.DeletedInnerDeclarationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.AddedCompositeDataTypeParentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.RemovedCompositeDataTypeParentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.ChangedCollectionDataTypeInnerTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.CreatedPcmInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.DeletedPcmInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.CreatedOperationInterfaceSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.ChangedOperationSignatureTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.DeletedOperationInterfaceSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.CreatedOperationSignatureParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.RenamedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.ChangedParameterDirectionReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.ChangedParameterTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.DeletedOperationSignatureParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.CreatedBasicPcmComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.CreatedCompositePcmComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.DeletedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.CreatedProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.DeletedProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.AddedOperationProvidedRoleInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.CreatedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.DeletedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
    this.addReaction(new mir.reactions.pcmToUml.AddedOperationRequiredRoleInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PcmToUml"))));
  }
}
