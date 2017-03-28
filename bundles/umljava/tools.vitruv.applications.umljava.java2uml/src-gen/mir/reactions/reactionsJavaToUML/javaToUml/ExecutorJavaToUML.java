package mir.reactions.reactionsJavaToUML.javaToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaToUML extends AbstractReactionsExecutor {
  public ExecutorJavaToUML(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.emftext.language.java.impl.JavaPackageImpl.eNS_URI, org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaInterfaceCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaInterfaceCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassifierRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassifierRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaCompUnitDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaCompUnitDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassifierDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassifierDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaElementMadePublicReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaElementMadePublicReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadePrivateReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadePrivateReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadeProtectedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadeProtectedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadePackagePrivateReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadePackagePrivateReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadeNonAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadeNonAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaSuperClassChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaSuperClassChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaSuperClassRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaSuperClassRemovedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaSuperInterfaceAddedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaSuperInterfaceAddedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaSuperInterfaceRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaSuperInterfaceRemovedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassImplementAddedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassImplementAddedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassImplementRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassImplementRemovedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadeNonFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMadeNonFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaClassMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaInterfaceMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaInterfaceMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadePrivateReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadePrivateReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeProtectedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeProtectedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadePackagePrivateReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadePackagePrivateReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeNonAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeNonAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeNonStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaMethodMadeNonStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaParameterCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaParameterCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaParameterRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaParameterRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaParameterDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaParameterDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaParameterTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaParameterTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaReturnTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaReturnTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadePrivateReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadePrivateReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadeProtectedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadeProtectedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadePackagePrivateReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadePackagePrivateReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadeStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadeNonStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadeNonStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.JavaAttributeMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUML.javaToUml.LogreactionReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUML.javaToUml.LogreactionReaction(userInteracting));
  }
}
