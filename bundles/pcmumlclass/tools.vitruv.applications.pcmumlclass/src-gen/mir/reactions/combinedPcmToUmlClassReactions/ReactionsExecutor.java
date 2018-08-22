package mir.reactions.combinedPcmToUmlClassReactions;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.combinedPcmToUmlClassReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.pcmRepositoryReactions.RepositoryCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRepositoryReactions"))));
    this.addReaction(new mir.reactions.pcmRepositoryReactions.RepositoryNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRepositoryReactions"))));
    this.addReaction(new mir.reactions.pcmRepositoryReactions.RepositoryDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRepositoryReactions"))));
    this.addReaction(new mir.reactions.pcmSystemReactions.SystemCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmSystemReactions"))));
    this.addReaction(new mir.reactions.pcmSystemReactions.SystemDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmSystemReactions"))));
    this.addReaction(new mir.reactions.pcmSystemReactions.SystemNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmSystemReactions"))));
    this.addReaction(new mir.reactions.pcmInterfaceReactions.InterfaceInsertedInRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmInterfaceReactions"))));
    this.addReaction(new mir.reactions.pcmInterfaceReactions.InterfaceRemovedFromRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmInterfaceReactions"))));
    this.addReaction(new mir.reactions.pcmInterfaceReactions.InterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmInterfaceReactions"))));
    this.addReaction(new mir.reactions.pcmInterfaceReactions.InterfaceRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmInterfaceReactions"))));
    this.addReaction(new mir.reactions.pcmInterfaceReactions.ParentInterfaceAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmInterfaceReactions"))));
    this.addReaction(new mir.reactions.pcmInterfaceReactions.ParentInterfaceRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmInterfaceReactions"))));
    this.addReaction(new mir.reactions.pcmSignatureReactions.SignatureInsertedInInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmSignatureReactions"))));
    this.addReaction(new mir.reactions.pcmSignatureReactions.SignatureRemovedFromInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmSignatureReactions"))));
    this.addReaction(new mir.reactions.pcmSignatureReactions.SignatureDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmSignatureReactions"))));
    this.addReaction(new mir.reactions.pcmSignatureReactions.SignatureRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmSignatureReactions"))));
    this.addReaction(new mir.reactions.pcmSignatureReactions.SignatureReturnTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmSignatureReactions"))));
    this.addReaction(new mir.reactions.pcmParameterReactions.ParameterInsertedInSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmParameterReactions"))));
    this.addReaction(new mir.reactions.pcmParameterReactions.ParameterRemovedFromSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmParameterReactions"))));
    this.addReaction(new mir.reactions.pcmParameterReactions.ParameterDeletedFromSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmParameterReactions"))));
    this.addReaction(new mir.reactions.pcmParameterReactions.ParameterRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmParameterReactions"))));
    this.addReaction(new mir.reactions.pcmParameterReactions.ParameterModifierChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmParameterReactions"))));
    this.addReaction(new mir.reactions.pcmParameterReactions.ParameterTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmParameterReactions"))));
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeInsertedInRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmCompositeDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeRemovedFromRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmCompositeDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmCompositeDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmCompositeDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeParentAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmCompositeDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeParentRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmCompositeDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmInnerDeclarationReactions.InnerDeclarationInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmInnerDeclarationReactions"))));
    this.addReaction(new mir.reactions.pcmInnerDeclarationReactions.InnerDeclarationRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmInnerDeclarationReactions"))));
    this.addReaction(new mir.reactions.pcmInnerDeclarationReactions.InnerDeclarationDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmInnerDeclarationReactions"))));
    this.addReaction(new mir.reactions.pcmInnerDeclarationReactions.InnerDeclarationNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmInnerDeclarationReactions"))));
    this.addReaction(new mir.reactions.pcmInnerDeclarationReactions.InnerDeclarationTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmInnerDeclarationReactions"))));
    this.addReaction(new mir.reactions.pcmCollectionDataTypeReactions.CollectionDataTypeInnerTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmCollectionDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmRepositoryComponentReactions.RepositoryComponentInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRepositoryComponentReactions"))));
    this.addReaction(new mir.reactions.pcmRepositoryComponentReactions.RepositoryComponentRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRepositoryComponentReactions"))));
    this.addReaction(new mir.reactions.pcmRepositoryComponentReactions.RepositoryComponentDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRepositoryComponentReactions"))));
    this.addReaction(new mir.reactions.pcmRepositoryComponentReactions.RepositoryComponentNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRepositoryComponentReactions"))));
    this.addReaction(new mir.reactions.pcmRequiredRoleReactions.RequiredRoleInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRequiredRoleReactions"))));
    this.addReaction(new mir.reactions.pcmRequiredRoleReactions.RequiredRoleRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRequiredRoleReactions"))));
    this.addReaction(new mir.reactions.pcmRequiredRoleReactions.RequiredRoleDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRequiredRoleReactions"))));
    this.addReaction(new mir.reactions.pcmRequiredRoleReactions.RequiredRoleNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRequiredRoleReactions"))));
    this.addReaction(new mir.reactions.pcmRequiredRoleReactions.RequiredRoleInterfaceChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmRequiredRoleReactions"))));
    this.addReaction(new mir.reactions.pcmProvidedRoleReactions.ProvidedRoleInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmProvidedRoleReactions"))));
    this.addReaction(new mir.reactions.pcmProvidedRoleReactions.ProvidedRoleRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmProvidedRoleReactions"))));
    this.addReaction(new mir.reactions.pcmProvidedRoleReactions.ProvidedRoleDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmProvidedRoleReactions"))));
    this.addReaction(new mir.reactions.pcmProvidedRoleReactions.ProvidedRoleNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmProvidedRoleReactions"))));
    this.addReaction(new mir.reactions.pcmProvidedRoleReactions.ProvidedRoleInterfaceChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmProvidedRoleReactions"))));
    this.addReaction(new mir.reactions.pcmAssemblyContextReactions.AssemblyContextInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmAssemblyContextReactions"))));
    this.addReaction(new mir.reactions.pcmAssemblyContextReactions.AssemblyContextRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmAssemblyContextReactions"))));
    this.addReaction(new mir.reactions.pcmAssemblyContextReactions.AssemblyContextDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmAssemblyContextReactions"))));
    this.addReaction(new mir.reactions.pcmAssemblyContextReactions.AssemblyContextNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmAssemblyContextReactions"))));
    this.addReaction(new mir.reactions.pcmAssemblyContextReactions.AssemblyContextComponentChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUmlClassReactions.pcmAssemblyContextReactions"))));
  }
}
