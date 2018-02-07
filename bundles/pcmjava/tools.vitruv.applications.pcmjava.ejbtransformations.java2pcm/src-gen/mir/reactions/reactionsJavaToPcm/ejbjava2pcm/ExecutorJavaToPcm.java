package mir.reactions.reactionsJavaToPcm.ejbjava2pcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
public class ExecutorJavaToPcm extends AbstractReactionsExecutor {
  public ExecutorJavaToPcm() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.ejbjava2pcm.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedFirstPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassAnnotationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceAnnotationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateAnnotationForFieldReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateImplementsReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceMethodReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateParameterInInterfaceMethodReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.ReturnTypeCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldInDatatypeClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassMethodInEjbClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm"))));
  }
}
