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
import org.eclipse.uml2.uml.Model
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order

@TestMethodOrder(OrderAnnotation)
@InheritableDisplayName("repositories")
abstract class RepositoryEquivalenceTemplate {

	@Order(0)
	@TestFactory
	def creation(extension EquivalenceTestBuilder builder) {
		stepFor(pcm.domain) [ extension view |
			resourceAt('model/Test'.repository).propagate [
				contents += pcm.repository.Repository => [
					entityName = 'Test'
				]
			]
		]

		inputVariantFor(pcm.domain, 'lowercase repository name') [ extension view |
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

		inputVariantFor(java.domain, 'uppercase package name') [ extension view |
			resourceAt('src/Test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'Test'
				]
			]

			resourceAt('src/Test/contracts/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'contracts'
					namespaces += #['Test']
				]
			]

			resourceAt('src/Test/datatypes/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'datatypes'
					namespaces += #['Test']
				]
			]
		]

		inputVariantFor(java.domain, 'creating only Java root package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'test'
				]
			]
		].alsoCompareToMainStepOfSameDomain()

		inputVariantFor(java.domain, 'creating only Java root package - uppercase package name') [ extension view |
			resourceAt('src/Test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'Test'
				]
			]
		]

		stepFor(uml.domain) [ extension view |
			resourceAt('model/model'.uml).propagate [
				contents += uml.Model => [
					name = 'model'
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

		inputVariantFor(uml.domain, 'uppercase package name') [ extension view |
			resourceAt('model/model'.uml).propagate [
				contents += uml.Model => [
					name = 'model'
					packagedElements += uml.Package => [
						name = 'Test'
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

		inputVariantFor(uml.domain, 'creating only UML root package') [ extension view |
			resourceAt('model/model'.uml).propagate [
				contents += uml.Model => [
					name = 'model'
					packagedElements += uml.Package => [
						name = 'test'
					]
				]
			]
		].alsoCompareToMainStepOfSameDomain()

		inputVariantFor(uml.domain, 'creating only UML root package - uppercase package name') [ extension view |
			resourceAt('model/model'.uml).propagate [
				contents += uml.Model => [
					name = 'model'
					packagedElements += uml.Package => [
						name = 'Test'
					]
				]
			]
		]

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

		inputVariantFor(pcm.domain, 'lowercase repository name') [ extension view |
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

		inputVariantFor(java.domain, 'uppercase package name') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				moveTo('src/Renamed/package-info'.java)
				Package.from(it).name = 'Renamed'
			]

			resourceAt('src/test/contracts/package-info'.java).propagate [
				moveTo('src/Renamed/contracts/package-info'.java)
				Package.from(it).namespaces.set(0, 'Renamed')
			]

			resourceAt('src/test/datatypes/package-info'.java).propagate [
				moveTo('src/Renamed/datatypes/package-info'.java)
				Package.from(it).namespaces.set(0, 'Renamed')
			]
		]

		inputVariantFor(java.domain, 'renaming only the root package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				moveTo('src/renamed/package-info'.java)
				Package.from(it).name = 'renamed'
			]
		]

		inputVariantFor(java.domain, 'renaming only the root package - uppercase package name') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				moveTo('src/Renamed/package-info'.java)
				Package.from(it).name = 'Renamed'
			]
		]

		stepFor(uml.domain) [ extension view |
			Model.from('model/model'.uml).propagate [
				packagedElements.get(0).name = 'renamed'
			]
		]

		inputVariantFor(uml.domain, 'uppercase package name') [ extension view |
			Model.from('model/model'.uml).propagate [
				packagedElements.get(0).name = 'Renamed'
			]
		]

		return testsThatStepsAreEquivalent
	}

	@TestFactory
	def deletion(extension EquivalenceTestBuilder builder) {
		dependsOn [creation(it)]

		stepFor(pcm.domain) [ extension view |
			resourceAt('model/Test'.repository).propagate[contents.clear()]
		]

		inputVariantFor(pcm.domain, 'deleting complete PCM resource') [ extension view |
			resourceAt('model/Test'.repository).propagate[delete(emptyMap)]
		]

		stepFor(java.domain) [ extension view |
			resourceAt('src/test/package-info'.java).propagate[delete(emptyMap)]
			resourceAt('src/test/contracts/package-info'.java) => [delete(emptyMap)]
			resourceAt('src/test/datatypes/package-info'.java) => [delete(emptyMap)]
		]

		inputVariantFor(java.domain, 'deleting only the root package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate[delete(emptyMap)]
		].alsoCompareToMainStepOfSameDomain()

		stepFor(uml.domain) [ extension view |
			Model.from('model/model'.uml).propagate [
				packagedElements.remove(0)
			]
		]

		inputVariantFor(uml.domain, 'deleting complete UML model') [ extension view |
			resourceAt('model/model'.uml).propagate[contents.clear()]
		]

		inputVariantFor(uml.domain, 'deleting complete UML resource') [ extension view |
			resourceAt('model/model'.uml).propagate[delete(emptyMap)]
		]

		return testsThatStepsAreEquivalent
	}
}
