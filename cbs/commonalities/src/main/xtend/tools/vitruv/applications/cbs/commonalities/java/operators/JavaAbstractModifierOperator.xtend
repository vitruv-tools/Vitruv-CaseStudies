package tools.vitruv.applications.cbs.commonalities.java.operators

import org.emftext.language.java.modifiers.Abstract
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState

@AttributeMappingOperator(
	name='javaAbstractModifier',
	commonalityAttributeType=@AttributeType(multiValued=false, type=boolean),
	participationAttributeType=@AttributeType(multiValued=true, type=AnnotationInstanceOrModifier)
)
class JavaAbstractModifierOperator extends AbstractJavaModifierOperator {

	/**
	 * @param executionState the reactions execution state
	 */
	new(ReactionExecutionState executionState, AnnotableAndModifiable javaModifiable) {
		super(executionState, javaModifiable)
	}

	override protected getModifierName() {
		return 'abstract'
	}

	override protected isRelevantModifier(Modifier modifier) {
		return modifier instanceof Abstract
	}

	override protected createModifier() {
		return ModifiersFactory.eINSTANCE.createAbstract
	}
}
