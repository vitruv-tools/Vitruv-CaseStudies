package tools.vitruv.applications.cbs.equivalencetesttemplates

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
	def creation(extension EquivalenceTestBuilder builder) {
		dependsOn[repository.creation(it)]

		stepFor(pcm.metamodel) [ extension view |
			resourceAt('model/Test'.system).propagate [
				contents += pcm.system.System => [
					entityName = 'Test'
				]
			]
		]

		inputVariantFor(pcm.metamodel, 'lowercase name') [ extension view |
			resourceAt('model/test'.system).propagate [
				contents += pcm.system.System => [
					entityName = 'test'
				]
			]
		]

		stepFor(java.metamodel) [ extension view |
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

		inputVariantFor(java.metamodel, 'creating only a package') [ extension view |
			userInteraction.addNextSingleSelection(2 /* create a System */ )
			resourceAt('src/test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'test'
				]
			]
		].alsoCompareToMainStepOfSameMetamodel()

		inputVariantFor(java.metamodel, 'creating only a class') [ extension view |
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
		].alsoCompareToMainStepOfSameMetamodel()

		return testsThatStepsAreEquivalent
	}

	@TestFactory
	def renaming(extension EquivalenceTestBuilder builder) {
		dependsOn([creation(it)])

		stepFor(pcm.metamodel) [ extension view |
			System.from('model/Test'.system).propagate[entityName = 'Renamed']
		]

		inputVariantFor(pcm.metamodel, 'lowercase name') [ extension view |
			System.from('model/Test'.system).propagate[entityName = 'renamed']
		]

		stepFor(java.metamodel) [ extension view |
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
