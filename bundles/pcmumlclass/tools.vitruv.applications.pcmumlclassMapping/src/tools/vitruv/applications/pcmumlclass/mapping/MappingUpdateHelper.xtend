package tools.vitruv.applications.pcmumlclass.mapping

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.xbase.lib.Functions.Function0

class MappingUpdateHelper {

	static def updateFromSources(MappingUpdateHelper.MappingUpdateParameter target,
		MappingUpdateHelper.MappingUpdateParameter... sources) {
		val updateSource = sources.findFirst[isCurrentUpdateSource(target)]
		if (updateSource !== null) {
			// there was actually an update 
			val newValue = updateSource.interchangeableValue
			// 1) update the target
			target.updateValue(newValue)
			// 2) update the other sources that were not the actual update source
			sources.filter[it !== updateSource].forEach [
				it.updateValue(newValue)
			]
		}
	}

	static def simpleEObjectParameter(EObject object, String featureName) { 
		val feature = object.eClass.EAllStructuralFeatures.findFirst[it.name == featureName]
		simpleObjectParameter(object.eGet(feature), [object.eSet(feature, it)])
	}

	static def simpleEObjectParameter(EObject object, EStructuralFeature feature) {
		simpleObjectParameter(object.eGet(feature), [object.eSet(feature, it)])
	}

	static def simpleObjectParameter(Object value, ValueSetter valueSetter) {
		return new MappingUpdateParameter {
			override interchangeableValue() {
				value
			}

			override updateValue(Object interchangeableValue) {
				valueSetter.set(interchangeableValue)
			}
		}
	}

	static def objectParameter(Function0<Object> valueGetter, ValueSetter valueSetter) {
		return new MappingUpdateParameter {
			override interchangeableValue() {
				valueGetter.apply
			}

			override updateValue(Object interchangeableValue) {
				valueSetter.set(interchangeableValue)
			}
		}
	}

	private static def isCurrentUpdateSource(MappingUpdateParameter source, MappingUpdateParameter target) {
		if (target.interchangeableValue === null) {
			// inital update, just take the first one
			return true
		}
		target.interchangeableValue != source.interchangeableValue
	}

	static interface ValueSetter {
		def void set(Object value)
	}

	static interface MappingUpdateParameter {
		def Object interchangeableValue()

		def void updateValue(Object interchangeableValue)
	}
}
