package tools.vitruv.applications.pcmjava.tests.pcm2java

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.apache.log4j.Logger
import tools.vitruv.change.propagation.ChangePropagationMode
import tools.vitruv.framework.vsum.VirtualModel

@Utility
class TransformationDirectionConfiguration {
	static val LOGGER = Logger.getLogger(TransformationDirectionConfiguration)

	def static void configureUnidirectionalExecution(VirtualModel virtualModel) {
		LOGGER.trace("Configuring for unidirectional execution")
		virtualModel.changePropagationMode = ChangePropagationMode.SINGLE_STEP
	}

	def static void configureBidirectionalExecution(VirtualModel virtualModel) {
		LOGGER.trace("Configuring for bidirectional execution")
		virtualModel.changePropagationMode = ChangePropagationMode.TRANSITIVE_CYCLIC
	}
}
