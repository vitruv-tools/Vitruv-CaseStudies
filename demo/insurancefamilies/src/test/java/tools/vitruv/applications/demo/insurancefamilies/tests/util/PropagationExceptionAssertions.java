package tools.vitruv.applications.demo.insurancefamilies.tests.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Assertions for exceptions raised while publishing a change through Vitruv.
 *
 * Vitruv 4 wraps failures raised by a change propagation specification in a
 * {@link RuntimeException}. The wrapped cause remains the domain exception
 * produced by the specification.
 */
public final class PropagationExceptionAssertions {
	private PropagationExceptionAssertions() {
	}

	public static void assertPropagationException(RuntimeException exception, Class<? extends Throwable> expectedType, String expectedMessage) {
		Throwable cause = assertInstanceOf(expectedType, exception.getCause());
		assertEquals(expectedMessage, cause.getMessage());
	}
}
