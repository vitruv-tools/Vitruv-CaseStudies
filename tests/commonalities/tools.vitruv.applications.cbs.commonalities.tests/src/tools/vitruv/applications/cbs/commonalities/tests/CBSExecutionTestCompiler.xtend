package tools.vitruv.applications.cbs.commonalities.tests

import com.google.inject.Singleton
import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler

@Singleton // Shared among tests (only compiled once)
class CBSExecutionTestCompiler extends ExecutionTestCompiler {

	static val TEST_PROJECT_NAME = 'cbs-commonalities-test'
	static val COMMONALITY_FILE_EXTENSION = '.commonality'
	static val OO_COMMONALITIES_PACKAGE = 'tools/vitruv/applications/cbs/commonalities/oo'
	static val CBS_COMMONALITIES_PACKAGE = 'tools/vitruv/applications/cbs/commonalities/cbs'

	private static def String commonalityFile(String fileName) {
		return fileName + COMMONALITY_FILE_EXTENSION
	}

	private static def String ooCommonalityFile(String fileName) {
		return '/' + OO_COMMONALITIES_PACKAGE + '/' + commonalityFile(fileName)
	}

	private static def String cbsCommonalityFile(String fileName) {
		return '/' + CBS_COMMONALITIES_PACKAGE + '/' + commonalityFile(fileName)
	}

	static val COMMONALITY_FILES = #[
		// ObjectOrientedDesign:
		ooCommonalityFile('Package'),
		ooCommonalityFile('Interface'),
		ooCommonalityFile('Class'),
		ooCommonalityFile('Property'),
		ooCommonalityFile('InterfaceMethod'),
		ooCommonalityFile('ClassMethod'),
		ooCommonalityFile('Constructor'),
		ooCommonalityFile('MethodParameter'),

		// ComponentBasedDesign:
		cbsCommonalityFile('Repository'),
		cbsCommonalityFile('Component'),
		cbsCommonalityFile('ComponentInterface'),
		cbsCommonalityFile('CompositeDataType'),
		cbsCommonalityFile('CompositeDataTypeElement'),
		cbsCommonalityFile('Operation'),
		cbsCommonalityFile('OperationParameter')
	]

	static val DOMAIN_DEPENDENCIES = #[
		'tools.vitruv.domains.uml',
		'tools.vitruv.domains.java',
		'tools.vitruv.domains.java.echange',
		'tools.vitruv.domains.java.util',
		'tools.vitruv.domains.java.util.jamoppparser',
		'tools.vitruv.domains.pcm',
		'tools.vitruv.domains.pcm.util',
		'org.eclipse.uml2.uml',
		'org.emftext.language.java',
		'org.palladiosimulator.pcm',
		'org.palladiosimulator.pcm.resources',
		'tools.vitruv.applications.cbs.commonalities.util'
	]

	override protected getProjectName() {
		return TEST_PROJECT_NAME
	}

	override protected getCommonalityFiles() {
		return COMMONALITY_FILES
	}

	override protected getDomainDependencies() {
		return DOMAIN_DEPENDENCIES
	}
}
