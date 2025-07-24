package tools.vitruv.applications.cbs.testutils.junit

import org.junit.jupiter.api.DisplayNameGenerator
import java.lang.reflect.Method
import static extension org.junit.platform.commons.support.AnnotationSupport.findAnnotation
import java.util.Optional

class Inheritable implements DisplayNameGenerator {
	override generateDisplayNameForClass(Class<?> testClass) {
		testClass.annotatedName.orElse(testClass.simpleName)
	}

	override generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
		testMethod.name
	}

	override generateDisplayNameForNestedClass(Class<?> nestedClass) {
		nestedClass.annotatedName.orElse(nestedClass.simpleName)
	}

	def private static Optional<String> getAnnotatedName(Class<?> testClass) {
		testClass.findAnnotation(InheritableDisplayName).map[value]
	}
}
