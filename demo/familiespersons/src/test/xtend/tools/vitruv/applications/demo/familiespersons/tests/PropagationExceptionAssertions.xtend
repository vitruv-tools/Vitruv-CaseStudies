package tools.vitruv.applications.demo.familiespersons.tests

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertInstanceOf

/**
 * Assertions for exceptions raised while publishing a change through Vitruv.
 *
 * Vitruv 4 wraps failures raised by a change propagation specification in a
 * {@link RuntimeException}. The wrapped cause remains the domain exception
 * produced by the specification.
 */
class PropagationExceptionAssertions {
	static def void assertPropagationException(RuntimeException exception, Class<? extends Throwable> expectedType, String expectedMessage) {
		val cause = assertInstanceOf(expectedType, exception.cause)
		assertEquals(expectedMessage, cause.message)
	}
}
