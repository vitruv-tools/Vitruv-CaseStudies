package tools.vitruv.applications.umljava.tests.util

import tools.vitruv.domains.java.JavaDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import org.apache.log4j.Logger
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class TransformationDirectionConfiguration {
	static val LOGGER = Logger.getLogger(TransformationDirectionConfiguration)

	def static void configureUnidirectionalExecution() {
		LOGGER.trace("Configuring for unidirectional execution")
		new JavaDomainProvider().domain.disableTransitiveChangePropagation
		new UmlDomainProvider().domain.disableTransitiveChangePropagation
	}

	def static void configureBidirectionalExecution() {
		LOGGER.trace("Configuring for bidirectional execution")
		new JavaDomainProvider().domain.enableTransitiveChangePropagation
		new UmlDomainProvider().domain.enableTransitiveChangePropagation
	}
}
