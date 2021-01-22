package tools.vitruv.applications.cbs.commonalities.java.operators

import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier
import org.emftext.language.java.modifiers.Final
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

@AttributeMappingOperator(
	name='javaFinalModifier',
	commonalityAttributeType=@AttributeType(multiValued=false, type=boolean),
	participationAttributeType=@AttributeType(multiValued=true, type=AnnotationInstanceOrModifier)
)
class JavaFinalModifierOperator extends AbstractJavaModifierOperator {

	/**
	 * @param executionState the reactions execution state
	 */
	new(ReactionExecutionState executionState,  AnnotableAndModifiable javaModifiable) {
		super(executionState, javaModifiable)
	}

	override protected getModifierName() {
		return 'final'
	}

	override protected isRelevantModifier(Modifier modifier) {
		return modifier instanceof Final
	}

	override protected createModifier() {
		return ModifiersFactory.eINSTANCE.createFinal
	}
}
