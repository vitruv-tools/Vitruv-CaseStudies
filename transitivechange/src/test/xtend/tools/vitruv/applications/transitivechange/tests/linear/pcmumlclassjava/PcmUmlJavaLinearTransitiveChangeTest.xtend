package tools.vitruv.applications.transitivechange.tests.linear.pcmumlclassjava

import org.junit.jupiter.api.BeforeAll
import tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava.PcmUmlJavaTransitiveChangeTest

/**
 * This class allows to create transitive change tests that to do not use the full network,
 * but only the linear network <code>PCM <--> UML <--> Java</code>. This is required, as the
 * used applications contain still some issues regarding the fully transitive use case.
 * TODO TS: these issues should be fixed in the PCM/UML/Java applications and all sub classes should extend TransitiveChangeTest directly
 */
abstract package class PcmUmlJavaLinearTransitiveChangeTest extends PcmUmlJavaTransitiveChangeTest {

	@BeforeAll
	def static void enforceLinearNetwork() {
		linearNetwork = true
	}
}
