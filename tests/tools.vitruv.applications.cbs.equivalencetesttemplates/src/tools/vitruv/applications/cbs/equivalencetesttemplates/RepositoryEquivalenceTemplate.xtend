package tools.vitruv.applications.cbs.equivalencetesttemplates

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestFactory
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder
import static extension tools.vitruv.applications.cbs.testutils.PcmCreators.*
import static extension tools.vitruv.applications.cbs.testutils.JavaCreators.*
import static extension tools.vitruv.applications.cbs.testutils.UmlCreators.*
import org.palladiosimulator.pcm.repository.Repository
import org.emftext.language.java.containers.Package
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName

@InheritableDisplayName("repositories")
abstract class RepositoryEquivalenceTemplate {

	@TestFactory
	def creation(extension EquivalenceTestBuilder builder) {
		stepFor(pcm.domain) [ extension view |
			resourceAt('model/Test'.repository).propagate [
				contents += pcm.repository.Repository => [
					entityName = 'Test'
				]
			]
		]

		inputVariantFor(pcm.domain, 'lowercase name') [ extension view |
			resourceAt('model/test'.repository).propagate [
				contents += pcm.repository.Repository => [
					entityName = 'test'
				]
			]
		]

		stepFor(java.domain) [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'test'
				]
			]

			resourceAt('src/test/contracts/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'contracts'
					namespaces += #['test']
				]
			]

			resourceAt('src/test/datatypes/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'datatypes'
					namespaces += #['test']
				]
			]
		]

		inputVariantFor(java.domain, 'creating only the root package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'test'
				]
			]
		].alsoCompareToMainStepOfSameDomain()
		
		stepFor(uml.domain) [ extension view |
			resourceAt('model/Test'.uml).propagate [
				contents += uml.Model => [
					packagedElements += uml.Package => [
						name = 'test'
						packagedElements += uml.Package => [
							name = 'contracts'
						]
						packagedElements += uml.Package => [
							name = 'datatypes'
						]
					]
				]
			]
		]
		
		inputVariantFor(uml.domain, 'creating only the root package') [ extension view |
			resourceAt('model/Test'.uml).propagate [
				contents += uml.Model => [
					packagedElements += uml.Package => [
						name = 'test'
					]
				]
			]
		].alsoCompareToMainStepOfSameDomain()

		return testsThatStepsAreEquivalent
	}

	@TestFactory
	@DisplayName("renaming")
	def renaming(extension EquivalenceTestBuilder builder) {
		dependsOn [creation(it)]

		stepFor(pcm.domain) [ extension view |
			Repository.from('model/Test'.repository).propagate [
				entityName = 'Renamed'
			]
		]

		inputVariantFor(pcm.domain, 'lowercase name') [ extension view |
			Repository.from('model/Test'.repository).propagate [
				entityName = 'renamed'
			]
		]

		inputVariantFor(pcm.domain, 'also rename file') [ extension view |
			resourceAt('model/Test'.repository).propagate [
				moveTo('model/Renamed'.repository)
				Repository.from(it).entityName = 'Renamed'
			]
		]

		stepFor(java.domain) [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				moveTo('src/renamed/package-info'.java)
				Package.from(it).name = 'renamed'
			]

			resourceAt('src/test/contracts/package-info'.java).propagate [
				moveTo('src/renamed/contracts/package-info'.java)
				Package.from(it).namespaces.set(0, 'renamed')
			]

			resourceAt('src/test/datatypes/package-info'.java).propagate [
				moveTo('src/renamed/datatypes/package-info'.java)
				Package.from(it).namespaces.set(0, 'renamed')
			]
		]

		inputVariantFor(java.domain, 'renaming only the root package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				moveTo('src/renamed/package-info'.java)
				Package.from(it).name = 'renamed'
			]
		]

		return testsThatStepsAreEquivalent
	}

	@TestFactory
	def deletion(extension EquivalenceTestBuilder builder) {
		dependsOn [creation(it)]

		stepFor(pcm.domain) [ extension view |
			resourceAt('model/Test'.repository).propagate [delete(emptyMap())]
		]

		stepFor(java.domain) [ extension view |
			resourceAt('src/test/package-info'.java).propagate [delete(emptyMap())]
			resourceAt('src/test/contracts/package-info'.java).propagate [delete(emptyMap())]
			resourceAt('src/test/datatypes/package-info'.java).propagate [delete(emptyMap())]
		]

		inputVariantFor(java.domain, 'deleting only the root package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [delete(emptyMap())]
		].alsoCompareToMainStepOfSameDomain()

		return testsThatStepsAreEquivalent
	}
}
