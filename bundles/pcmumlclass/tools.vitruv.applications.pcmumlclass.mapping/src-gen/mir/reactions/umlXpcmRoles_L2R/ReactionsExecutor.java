package mir.reactions.umlXpcmRoles_L2R;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.umlXpcmRoles_L2R.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleClassInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleClassDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleInterfaceInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleOperationInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleOperationDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRolePropertyInsertedInClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRolePropertyRemovedFromClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRolePropertyDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleInterfaceReplacedAtProperty_typeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleInterfaceReplacedAtParameter_typeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleParameterInsertedInOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleParameterRemovedFromOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleNameReplacedAtProperty_nameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleNameReplacedAtParameter_nameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnRequiredRoleElementReplacedAtParameter_typeBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnProvidedRoleClassInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnProvidedRoleClassDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnProvidedRoleInterfaceInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnProvidedRoleInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnProvidedRoleInterfaceRealizationInsertedInClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnProvidedRoleInterfaceRealizationRemovedFromClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnProvidedRoleInterfaceRealizationDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnProvidedRoleInterfaceReplacedAtInterfaceRealization_contractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_L2R.OnProvidedRoleNameReplacedAtInterfaceRealization_nameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_L2R"))));
  }
}
