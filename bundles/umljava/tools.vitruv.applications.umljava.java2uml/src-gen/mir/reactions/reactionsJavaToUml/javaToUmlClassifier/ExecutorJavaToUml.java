package mir.reactions.reactionsJavaToUml.javaToUmlClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToUml extends AbstractReactionsExecutor {
  public ExecutorJavaToUml() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompUnitDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompUnitDeletedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierDeletedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeAbstractReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonAbstractReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeFinalReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonFinalReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassChangedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassRemovedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementAddedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementAddedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementRemovedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaInterfaceCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaInterfaceCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceAddedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceAddedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceRemovedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageDeletedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitInsertedInPackageReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitInsertedInPackageReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitRemovedFromPackageReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitRemovedFromPackageReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantDeletedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumerationImplementAddedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumerationImplementAddedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierMadeStaticReaction());
  }
}
