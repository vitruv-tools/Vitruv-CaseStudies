package tools.vitruv.applications.demo.insurancefamilies.tests.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Assertions for exceptions raised while publishing a change through Vitruv.
 *
 * Vitruv-Change 4.0.0 wraps failures raised by a change propagation specification
 * in a plain {@link RuntimeException}, 4.0.1 in two, and the Xtend versions before
 * rethrew it unwrapped. The outermost exception that is not such a wrapper
 * is the domain exception produced by the specification.
 */
public final class PropagationExceptionAssertions {
	private PropagationExceptionAssertions() {
	}

	public static void assertPropagationException(RuntimeException exception, Class<? extends Throwable> expectedType, String expectedMessage) {
		Throwable cause = assertInstanceOf(expectedType, unwrap(exception));
		assertEquals(expectedMessage, cause.getMessage());
	}

	private static Throwable unwrap(Throwable throwable) {
		Throwable current = throwable;
		while (current != null && current.getClass() == RuntimeException.class && current.getCause() != null) {
			current = current.getCause();
		}
		return current;
	}
}
