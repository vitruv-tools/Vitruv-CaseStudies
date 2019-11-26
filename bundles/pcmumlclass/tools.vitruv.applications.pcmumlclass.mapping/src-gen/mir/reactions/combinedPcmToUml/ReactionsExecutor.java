package mir.reactions.combinedPcmToUml;

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
    return new mir.routines.combinedPcmToUml.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlXpcmComponent_R2L.OnRepositoryComponentRepositoryInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmComponent_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_R2L.OnRepositoryComponentRepositoryDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmComponent_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_R2L.OnRepositoryComponentRepositoryComponentInsertedInRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmComponent_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_R2L.OnRepositoryComponentRepositoryComponentRemovedFromRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmComponent_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_R2L.OnRepositoryComponentRepositoryComponentDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmComponent_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_R2L.OnRepositoryComponentEntityNameReplacedAtRepositoryComponent_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmComponent_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeRepositoryInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeRepositoryDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeCompositeDataTypeInsertedInRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeCompositeDataTypeRemovedFromRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeCompositeDataTypeDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeEntityNameReplacedAtCompositeDataType_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDatatypeParentCompositeDataTypeInsertedInCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDatatypeParentCompositeDataTypeRemovedFromCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDatatypeParentCompositeDataTypeDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceRepositoryInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceRepositoryDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceOperationInterfaceInsertedInRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceOperationInterfaceRemovedFromRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceOperationInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceEntityNameReplacedAtOperationInterface_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceParentOperationInterfaceInsertedInOperationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceParentOperationInterfaceRemovedFromOperationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceParentOperationInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRepository_R2L.OnRepositoryRepositoryInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRepository_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRepository_R2L.OnRepositoryRepositoryDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRepository_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRepository_R2L.OnRepositoryEntityNameReplacedAtRepository_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRepository_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationInterfaceInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleInterfaceProvidingRequiringEntityInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleInterfaceProvidingRequiringEntityDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationRequiredRoleInsertedInInterfaceProvidingRequiringEntityReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationRequiredRoleRemovedFromInterfaceProvidingRequiringEntityReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationRequiredRoleDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationInterfaceReplacedAtOperationRequiredRole_requiredInterface__OperationRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleEntityNameReplacedAtOperationRequiredRole_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationInterfaceInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleInterfaceProvidingRequiringEntityInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleInterfaceProvidingRequiringEntityDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationProvidedRoleInsertedInInterfaceProvidingRequiringEntityReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationProvidedRoleRemovedFromInterfaceProvidingRequiringEntityReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationProvidedRoleDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationInterfaceReplacedAtOperationProvidedRole_providedInterface__OperationProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleEntityNameReplacedAtOperationProvidedRole_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureDataTypeInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureDataTypeDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureOperationInterfaceInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureOperationInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureOperationSignatureInsertedInOperationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureOperationSignatureRemovedFromOperationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureOperationSignatureDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureDataTypeReplacedAtOperationSignature_returnType__OperationSignatureReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureEntityNameReplacedAtOperationSignature_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmSignature_R2L.OnSignatureElementReplacedAtOperationSignature_returnType__OperationSignatureBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.umlXpcmSignature_R2L"))));
    this.addReaction(new mir.reactions.pcmCollectionDataTypeReactions.CollectionDataTypeInnerTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("combinedPcmToUml.pcmCollectionDataTypeReactions"))));
  }
}
