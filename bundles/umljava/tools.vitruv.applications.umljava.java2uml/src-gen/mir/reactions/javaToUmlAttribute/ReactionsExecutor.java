package mir.reactions.javaToUmlAttribute;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.javaToUmlAttribute.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeCreatedInClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlAttribute"))));
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeCreatedInEnumReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlAttribute"))));
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlAttribute"))));
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlAttribute"))));
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeMadeNonFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlAttribute"))));
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeCreatedInInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlAttribute"))));
  }
}
