package tools.vitruv.applications.cbs.testutils.junit;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

import java.lang.reflect.Method;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGenerator;

public class Inheritable implements DisplayNameGenerator {
	@Override
	public String generateDisplayNameForClass(Class<?> testClass) {
		return getAnnotatedName(testClass).orElse(testClass.getSimpleName());
	}

	@Override
	public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
		return testMethod.getName();
	}

	@Override
	public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
		return getAnnotatedName(nestedClass).orElse(nestedClass.getSimpleName());
	}

	private static Optional<String> getAnnotatedName(Class<?> testClass) {
		return findAnnotation(testClass, InheritableDisplayName.class).map(InheritableDisplayName::value);
	}
}
