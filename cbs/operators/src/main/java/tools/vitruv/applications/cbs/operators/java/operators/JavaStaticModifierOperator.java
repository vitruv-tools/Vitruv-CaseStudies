package tools.vitruv.applications.cbs.operators.java.operators;

import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.Static;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "javaStaticModifier", commonalityAttributeType = @AttributeType(multiValued = false, type = Boolean.class), participationAttributeType = @AttributeType(multiValued = true, type = AnnotationInstanceOrModifier.class))
public class JavaStaticModifierOperator extends AbstractJavaModifierOperator {

    public JavaStaticModifierOperator(ReactionExecutionState executionState, AnnotableAndModifiable javaModifiable) {
        super(executionState, javaModifiable);
    }

    @Override
    protected String getModifierName() {
        return "static";
    }

    @Override
    protected boolean isRelevantModifier(Modifier modifier) {
        return modifier instanceof Static;
    }

    @Override
    protected Modifier createModifier() {
        return ModifiersFactory.eINSTANCE.createStatic();
    }
}