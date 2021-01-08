package tools.vitruv.applications.pcmumlclassjava

import org.junit.jupiter.api.BeforeAll

/**
 * This class allows to create transitive change tests that to do use the fully network,
 * only the linear network <code>PCM <--> UML <--> Java</code>. This is required, as the
 * used applications contain stil some issues regarding the fully transitive use case.
 * TODO TS: these issues should be fixed in the PCM/UML/Java applications and all sub classes should extend TransitiveChangeTest directly
 */
abstract class LinearTransitiveChangeTest extends TransitiveChangeTest {

	@BeforeAll
	def static void enforceLinearNetwork() {
		linearNetwork = true
	}
}
