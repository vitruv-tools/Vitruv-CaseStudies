package mir.reactions.java2Pcm;

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
    return new mir.routines.java2Pcm.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.java2PcmClassifier.PackageCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmClassifier"))));
    this.addReaction(new mir.reactions.java2PcmClassifier.InterfaceCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmClassifier"))));
    this.addReaction(new mir.reactions.java2PcmClassifier.ClassCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmClassifier"))));
    this.addReaction(new mir.reactions.java2PcmClassifier.TypeReferenceCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmClassifier"))));
    this.addReaction(new mir.reactions.java2PcmMethod.JavaNamedElementRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.ParameterCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.ParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.ParameterNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.FieldCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.FieldTypeChangeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.ClassMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.InterfaceMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmMethod"))));
    this.addReaction(new mir.reactions.java2PcmMethod.JavaReturnTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("java2Pcm.java2PcmMethod"))));
  }
}
