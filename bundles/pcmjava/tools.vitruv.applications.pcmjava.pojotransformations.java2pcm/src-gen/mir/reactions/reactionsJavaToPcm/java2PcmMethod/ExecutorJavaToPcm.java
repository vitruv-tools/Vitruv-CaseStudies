package mir.reactions.reactionsJavaToPcm.java2PcmMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
public class ExecutorJavaToPcm extends AbstractReactionsExecutor {
  public ExecutorJavaToPcm() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.java2PcmMethod.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaNamedElementRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.FieldCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.FieldTypeChangeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ClassMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.InterfaceMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaReturnTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("Java2PcmMethod"))));
  }
}
