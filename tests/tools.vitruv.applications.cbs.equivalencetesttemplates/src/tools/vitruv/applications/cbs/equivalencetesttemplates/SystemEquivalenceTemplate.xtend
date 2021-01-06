package tools.vitruv.applications.cbs.equivalencetesttemplates

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestFactory
import static extension tools.vitruv.applications.cbs.testutils.PcmCreators.*
import static extension tools.vitruv.applications.cbs.testutils.JavaCreators.*
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder
import org.palladiosimulator.pcm.system.System
import org.emftext.language.java.containers.Package
import org.emftext.language.java.containers.CompilationUnit
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName

@InheritableDisplayName("systems")
abstract class SystemEquivalenceTemplate {
	abstract protected def RepositoryEquivalenceTemplate getRepository()

	@TestFactory
	@DisplayName("creation")
	def create(extension EquivalenceTestBuilder builder) {
		dependsOn[repository.create(it)]

		stepFor(pcm.domain) [ extension view |
			resourceAt('model/Test'.system).propagate [
				contents += pcm.system.System => [
					entityName = 'Test'
				]
			]
		]

		inputVariantFor(pcm.domain, 'lowercase name') [ extension view |
			resourceAt('model/test'.system).propagate [
				contents += pcm.system.System => [
					entityName = 'test'
				]
			]
		]

		stepFor(java.domain) [ extension view |
			userInteraction.addNextSingleSelection(2 /* create a System */ )
			resourceAt('src/test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'test'
				]
			]

			resourceAt('src/test/TestImpl'.java).propagate [
				contents += java.containers.CompilationUnit => [
					namespaces += #['test']
					classifiers += java.classifiers.Class => [
						name = 'TestImpl'
						makePublic()
					]
				]
			]
		]

		inputVariantFor(java.domain, 'creating only a package') [ extension view |
			userInteraction.addNextSingleSelection(2 /* create a System */ )
			resourceAt('src/test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'test'
				]
			]
		].alsoCompareToMainStepOfSameDomain()

		inputVariantFor(java.domain, 'creating only a class') [ extension view |
			userInteraction.addNextSingleSelection(2 /* create a System */ )
			resourceAt('src/test/TestImpl'.java).propagate [
				contents += java.containers.CompilationUnit => [
					namespaces += #['test']
					classifiers += java.classifiers.Class => [
						name = 'TestImpl'
						makePublic()
					]
				]
			]
		].alsoCompareToMainStepOfSameDomain()

		return testsThatStepsAreEquivalent
	}

	@TestFactory
	@DisplayName("renaming")
	def rename(extension EquivalenceTestBuilder builder) {
		dependsOn([create(it)])

		stepFor(pcm.domain) [ extension view |
			System.from('model/Test'.system).propagate[entityName = 'Renamed']
		]

		inputVariantFor(pcm.domain, 'lowercase name') [ extension view |
			System.from('model/Test'.system).propagate[entityName = 'renamed']
		]

		stepFor(java.domain) [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				moveTo('src/renamed/package-info'.java)
				Package.from(it).name = 'renamed'
			]

			resourceAt('src/test/TestImpl'.java).propagate [
				moveTo('src/renamed/RenamedImpl'.java)
				CompilationUnit.from(it) => [
					namespaces.set(0, 'renamed')
					classifiers.get(0).name = 'RenamedImpl'
				]
			]
		]

		return testsThatStepsAreEquivalent
	}
}
