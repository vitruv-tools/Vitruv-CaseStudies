package tools.vitruv.applications.pcmumlclass.mapping

import org.apache.commons.lang3.StringUtils
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingUpdateTransformation

class MappingUpdateUtils {

	static def chain(MappingUpdateTransformation t1, MappingUpdateTransformation t2) {
		new MappingUpdateTransformation() {

			override transformToInterchangeableValue() {
				[
					t2.transformToInterchangeableValue.apply(t1.transformToInterchangeableValue.apply(it))
				]
			}

			override transformToTargetValue() {
				[
					t2.transformToTargetValue.apply(t1.transformToTargetValue.apply(it))
				]
			}
		}
	}

	static def transformationFirstLower(boolean reverseOnSet) {
		new MappingUpdateTransformation() {
			override transformToInterchangeableValue() {
				[(it as String).toFirstLower]
			}

			override transformToTargetValue() {
				if (reverseOnSet) {
					[(it as String).toFirstUpper]
				} else {
					[it]
				}
			}
		}
	}

	static def transformationFirstUpper(boolean reverseOnSet) {
		new MappingUpdateTransformation() {
			override transformToInterchangeableValue() {
				[(it as String).toFirstUpper]
			}

			override transformToTargetValue() {
				if (reverseOnSet) {
					[(it as String).toFirstLower]
				} else {
					[it]
				}
			}
		}
	}

	static def transformationImplementationSuffix(boolean suffixInInterchangeableValue, boolean targetContainsSuffix) {
		new MappingUpdateTransformation() {
			override transformToInterchangeableValue() {
				if (suffixInInterchangeableValue) {
					[
						if (StringUtils.endsWith((it as String), DefaultLiterals.IMPLEMENTATION_SUFFIX)) {
							it
						} else {
							(it as String) + DefaultLiterals.IMPLEMENTATION_SUFFIX
						}
					]
				} else {
					[StringUtils.removeEnd((it as String), DefaultLiterals.IMPLEMENTATION_SUFFIX)]
				}
			}

			override transformToTargetValue() {
				if (targetContainsSuffix) {
					if (suffixInInterchangeableValue) {
						[it]
					} else {
						[(it as String) + DefaultLiterals.IMPLEMENTATION_SUFFIX]
					}
				} else {
					if (suffixInInterchangeableValue) {
						[StringUtils.removeEnd((it as String), DefaultLiterals.IMPLEMENTATION_SUFFIX)]
					} else {
						[it]
					}
				}
			}
		}
	}

}
