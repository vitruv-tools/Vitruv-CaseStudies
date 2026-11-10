package tools.vitruv.applications.demo.familiespersons.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Assertions for exceptions raised while publishing a change through Vitruv.
 *
 * Vitruv 4 wraps failures raised by a change propagation specification in one
 * or more plain {@link RuntimeException}s (Vitruv-Change 4.0.1 adds a second
 * one in the change propagator). The innermost cause that is not such a
 * wrapper is the domain exception produced by the specification.
 */
public final class PropagationExceptionAssertions {
	private PropagationExceptionAssertions() {
	}

	public static void assertPropagationException(RuntimeException exception, Class<? extends Throwable> expectedType, String expectedMessage) {
		Throwable cause = assertInstanceOf(expectedType, unwrap(exception.getCause()));
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
