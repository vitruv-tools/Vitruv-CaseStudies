package tools.vitruv.applications.cbs.operators.java.operators;

import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.modifiers.Final;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "javaFinalModifier", commonalityAttributeType = @AttributeType(multiValued = false, type = Boolean.class), participationAttributeType = @AttributeType(multiValued = true, type = AnnotationInstanceOrModifier.class))
public class JavaFinalModifierOperator extends AbstractJavaModifierOperator {

    /**
     * Creates a new JavaFinalModifierOperator.
     * 
     * @param executionState the reactions execution state
     * @param javaModifiable the modifiable Java element
     */
    public JavaFinalModifierOperator(ReactionExecutionState executionState,
            AnnotableAndModifiable javaModifiable) {
        super(executionState, javaModifiable);
    }

    @Override
    protected String getModifierName() {
        return "final";
    }

    @Override
    protected boolean isRelevantModifier(Modifier modifier) {
        return modifier instanceof Final;
    }

    @Override
    protected Modifier createModifier() {
        return ModifiersFactory.eINSTANCE.createFinal();
    }
}