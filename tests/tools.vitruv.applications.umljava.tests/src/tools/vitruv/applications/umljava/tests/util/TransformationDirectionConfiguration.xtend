package tools.vitruv.applications.umljava.tests.util

import org.apache.log4j.Logger
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.vsum.VirtualModel

@Utility
class TransformationDirectionConfiguration {
	static val LOGGER = Logger.getLogger(TransformationDirectionConfiguration)

	def static void configureUnidirectionalExecution(VirtualModel virtualModel) {
		LOGGER.trace("Configuring for unidirectional execution")
		virtualModel.transitiveChangePropagationEnabled = false
	}

	def static void configureBidirectionalExecution(VirtualModel virtualModel) {
		LOGGER.trace("Configuring for bidirectional execution")
		virtualModel.transitiveChangePropagationEnabled = true
	}
}
