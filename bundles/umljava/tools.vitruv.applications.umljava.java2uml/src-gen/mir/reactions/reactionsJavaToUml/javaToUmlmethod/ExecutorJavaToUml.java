package mir.reactions.reactionsJavaToUml.javaToUmlmethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaToUml extends AbstractReactionsExecutor {
  public ExecutorJavaToUml(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.emftext.language.java.impl.JavaPackageImpl.eNS_URI, org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaClassMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaClassMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaInterfaceMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaInterfaceMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaMemberDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaMemberDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaMethodMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaMethodMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaMethodMadeNonAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaMethodMadeNonAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaElementMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaElementMadeStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaMethodMadeNonStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaMethodMadeNonStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaParameterCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaParameterCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaParameterDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaParameterDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaParameterTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaParameterTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaReturnTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlmethod.JavaReturnTypeChangedReaction(userInteracting));
  }
}
