package mir.reactions.java2PcmMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.java2PcmMethod.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.java2PcmMethod.JavaNamedElementRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.ParameterCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.ParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.ParameterNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.FieldCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.FieldTypeChangeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.ClassMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.InterfaceMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.JavaReturnTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2PcmMethod"))));
  }
}
