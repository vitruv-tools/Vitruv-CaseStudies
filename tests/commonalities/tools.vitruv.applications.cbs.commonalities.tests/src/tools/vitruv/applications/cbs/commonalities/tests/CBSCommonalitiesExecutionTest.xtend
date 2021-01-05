package tools.vitruv.applications.cbs.commonalities.tests

import org.emftext.language.java.JavaClasspath
import tools.vitruv.applications.cbs.commonalities.tests.util.AbstractCBSCommonalitiesExecutionTest
import tools.vitruv.domains.java.JamoppLibraryHelper
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider
import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler

abstract class CBSCommonalitiesExecutionTest extends AbstractCBSCommonalitiesExecutionTest {	
	static val COMMONALITY_FILE_EXTENSION = '.commonality'
	static val OO_COMMONALITIES_PACKAGE = 'tools/vitruv/applications/cbs/commonalities/oo'
	static val CBS_COMMONALITIES_PACKAGE = 'tools/vitruv/applications/cbs/commonalities/cbs'

	private static def String ooCommonalityFile(String fileName) {
		return '''/«OO_COMMONALITIES_PACKAGE»/«fileName»«COMMONALITY_FILE_EXTENSION»''' 
	}

	private static def String cbsCommonalityFile(String fileName) {
		return '''/«CBS_COMMONALITIES_PACKAGE»/«fileName»«COMMONALITY_FILE_EXTENSION»'''
	}
	
	override createCompiler(ExecutionTestCompiler.Factory factory) {
		factory.createCompiler [
			commonalities = #[
				// ObjectOrientedDesign:
				ooCommonalityFile('Package'),
				ooCommonalityFile('Interface'),
				ooCommonalityFile('Class'),
				ooCommonalityFile('Property'),
				ooCommonalityFile('InterfaceMethod'),
				ooCommonalityFile('ClassMethod'),
				ooCommonalityFile('Constructor'),
				ooCommonalityFile('MethodParameter'),
		
				// ComponentBasedSystems:
				cbsCommonalityFile('Repository'),
				cbsCommonalityFile('Component'),
				cbsCommonalityFile('ComponentInterface'),
				cbsCommonalityFile('CompositeDataType'),
				cbsCommonalityFile('CompositeDataTypeElement'),
				cbsCommonalityFile('Operation'),
				cbsCommonalityFile('OperationParameter')
			]
			domainDependencies = #[
				'tools.vitruv.domains.uml',
				'tools.vitruv.domains.java',
				'tools.vitruv.domains.java.echange',
				'tools.vitruv.domains.pcm',
				'tools.vitruv.applications.cbs.commonalities.util'
			]
		]
	}
	
	def <T> T getModels(DomainModelsProvider<T> modelsProvider) {
		return modelsProvider.getModels(vitruvApplicationTestAdapter)
	}
	
	@BeforeEach
	def protected setupJaMoPP() {
		// This is necessary because otherwise Maven tests will fail as resources from previous
		// tests are still in the classpath and accidentally resolved:
		JavaClasspath.reset()
		// We also need to freshly register the standard library again then:
		JamoppLibraryHelper.registerStdLib
	}
}
