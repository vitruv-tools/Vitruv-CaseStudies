package mir.reactions.umlToJavaClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.umlToJavaClassifier.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlClassCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlClassifierRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlClassifierDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlClassMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlClassMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlSuperClassChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlSuperClassDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlInterfaceImplementerChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlImplementedInterfaceChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlImplementedInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlDataTypeCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlInterfaceCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlSuperInterfaceChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlSuperInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlEnumCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlEnumLiteralCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlEnumLiteralDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlPackageCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlPackageRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlPackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlPrimitiveTypeCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaClassifier"))));
  }
}
