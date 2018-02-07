package mir.reactions.reactionsUmlToJava.umlToJavaClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.umlToJavaClassifier.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceImplementerChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlDataTypeCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPrimitiveTypeCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaClassifier"))));
  }
}
