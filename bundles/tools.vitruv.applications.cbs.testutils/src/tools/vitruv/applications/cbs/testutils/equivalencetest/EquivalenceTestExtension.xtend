package tools.vitruv.applications.cbs.testutils.equivalencetest

import org.junit.jupiter.api.^extension.ExtensionContext
import org.junit.jupiter.api.^extension.ParameterContext
import org.junit.jupiter.api.^extension.ParameterResolutionException
import org.junit.jupiter.api.^extension.ParameterResolver

import static com.google.common.base.Preconditions.checkNotNull
import static extension org.junit.platform.commons.support.AnnotationSupport.findAnnotation
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings
import tools.vitruv.applications.cbs.testutils.JoinedModelComparisonSettings
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class EquivalenceTestExtension implements ParameterResolver {

	override resolveParameter(ParameterContext parameterContext,
		ExtensionContext extensionContext) throws ParameterResolutionException {
		val config = extensionContext.requiredTestMethod.findAnnotation(EquivalenceTest).or [
			extensionContext.requiredTestClass.findAnnotation(EquivalenceTest)
		].orElseThrow [
			new ParameterResolutionException('''Please annotate the test method or class with «EquivalenceTest.simpleName»!''')
		]
		val changeSpecs = config.value.mapFixed[instantiate()]
		val comparisonSettings = switch (config.comparisonSettings.length) {
			case 0: ModelComparisonSettings.NONE
			case 1: config.comparisonSettings.get(0).instantiate()
			default: new JoinedModelComparisonSettings(config.comparisonSettings.mapFixed[instantiate()])
		}
		switch (parameterContext.parameter.type) {
			case EquivalenceTestBuilder:
				new DefaultEquivalenceTestBuilder(extensionContext, changeSpecs, config.uriMode, comparisonSettings)
			case ParameterizedEquivalenceTestBuilder:
				new DefaultParameterizedEquivalenceTestBuilder(extensionContext, changeSpecs, config.uriMode,
					comparisonSettings)
			default:
				throw new ParameterResolutionException('''unsupported type «parameterContext.parameter.type»''')
		}
	}

	override supportsParameter(ParameterContext parameterContext,
		ExtensionContext extensionContext) throws ParameterResolutionException {
		parameterContext.parameter.type == EquivalenceTestBuilder ||
			parameterContext.parameter.type == ParameterizedEquivalenceTestBuilder
	}

	def private static <T> instantiate(Class<? extends T> clazz) {
		checkNotNull(clazz.constructors.findFirst[parameterCount == 0], '''«clazz» has no zero-arg constructor!''').
			newInstance() as T
	}
}
