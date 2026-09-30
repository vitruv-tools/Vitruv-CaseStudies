package tools.vitruv.applications.cbs.testutils.equivalencetest;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import tools.vitruv.applications.cbs.testutils.JoinedModelComparisonSettings;
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.applications.VitruvApplication;

public class EquivalenceTestExtension implements ParameterResolver {

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		EquivalenceTest config = findAnnotation(extensionContext.getRequiredTestMethod(), EquivalenceTest.class)
				.or(() -> findAnnotation(extensionContext.getRequiredTestClass(), EquivalenceTest.class))
				.orElseThrow(() -> new ParameterResolutionException(
						"Please annotate the test method or class with " + EquivalenceTest.class.getSimpleName() + "!"));
		List<ChangePropagationSpecification> changeSpecs = Arrays.stream(config.applications())
				.<VitruvApplication>map(EquivalenceTestExtension::instantiate)
				.flatMap(application -> application.getChangePropagationSpecifications().stream())
				.toList();
		ModelComparisonSettings comparisonSettings = switch (config.comparisonSettings().length) {
		case 0 -> ModelComparisonSettings.NONE;
		case 1 -> instantiate(config.comparisonSettings()[0]);
		default -> new JoinedModelComparisonSettings(Arrays.stream(config.comparisonSettings())
				.<ModelComparisonSettings>map(EquivalenceTestExtension::instantiate)
				.toList());
		};
		Class<?> parameterType = parameterContext.getParameter().getType();
		if (parameterType == EquivalenceTestBuilder.class) {
			return new DefaultEquivalenceTestBuilder(extensionContext, changeSpecs, config.uriMode(),
					comparisonSettings);
		}
		if (parameterType == ParameterizedEquivalenceTestBuilder.class) {
			return new DefaultParameterizedEquivalenceTestBuilder(extensionContext, changeSpecs, config.uriMode(),
					comparisonSettings);
		}
		throw new ParameterResolutionException("unsupported type " + parameterType);
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Class<?> parameterType = parameterContext.getParameter().getType();
		return parameterType == EquivalenceTestBuilder.class
				|| parameterType == ParameterizedEquivalenceTestBuilder.class;
	}

	@SuppressWarnings("unchecked")
	private static <T> T instantiate(Class<? extends T> clazz) {
		Constructor<?> constructor = checkNotNull(
				Arrays.stream(clazz.getConstructors())
						.filter(candidate -> candidate.getParameterCount() == 0)
						.findFirst()
						.orElse(null),
				"%s has no zero-arg constructor!", clazz);
		try {
			return (T) constructor.newInstance();
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException("Could not instantiate " + clazz, e);
		}
	}
}
