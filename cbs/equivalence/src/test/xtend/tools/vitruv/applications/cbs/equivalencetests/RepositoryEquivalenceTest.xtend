package tools.vitruv.applications.cbs.equivalencetests

import org.eclipse.uml2.uml.Model
import org.emftext.language.java.containers.Package
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestFactory
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName

import static extension tools.vitruv.applications.cbs.testutils.JavaCreators.*
import static extension tools.vitruv.applications.cbs.testutils.PcmCreators.*
import static extension tools.vitruv.applications.cbs.testutils.UmlCreators.*

@ReactionsEquivalenceTest
@InheritableDisplayName("repositories")
class RepositoryEquivalenceTest {
	@TestFactory
	def creation(extension EquivalenceTestBuilder builder) {
		userInteractions [
			// TODO unify the interactions
			onTextInput [message.contains("path for the UML root model")].respondWith("model/")
			onTextInput [message.contains("name for the UML root model")].respondWith("model")
			onTextInput [message.contains("where to save the corresponding Uml-Model")].respondWith("model/model")
			onTextInput [message.contains("where to save the corresponding PCM model")].respondWith("model/Test")
			onMultipleChoiceSingleSelection [message.contains("a pcm::Repository or a pcm::System")].
				respondWithChoiceMatching[contains("Repository")]
		]

		stepFor(pcm.metamodel) [ extension view |
			resourceAt('model/Test'.repository).propagate [
				contents += pcm.repository.Repository => [
					entityName = 'Test'
				]
			]
		]

		inputVariantFor(pcm.metamodel, 'lowercase repository name') [ extension view |
			resourceAt('model/test'.repository).propagate [
				contents += pcm.repository.Repository => [
					entityName = 'test'
				]
			]
		]

		stepFor(java.metamodel) [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'test'
				]
			]

			resourceAt('src/test/contracts/package-info'.java) => [
				if (contents.empty) {
					it.propagate [
						contents += java.containers.Package => [
							name = 'contracts'
							namespaces += #['test']
						]
					]
				}
			]

			resourceAt('src/test/datatypes/package-info'.java) => [
				if (contents.empty) {
					it.propagate [
						contents += java.containers.Package => [
							name = 'datatypes'
							namespaces += #['test']
						]
					]
				}
			]
		]

		inputVariantFor(java.metamodel, 'uppercase package name') [ extension view |
			resourceAt('src/Test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'Test'
				]
			]

			resourceAt('src/Test/contracts/package-info'.java) => [
				if (contents.empty) {
					it.propagate [
						contents += java.containers.Package => [
							name = 'contracts'
							namespaces += #['Test']
						]
					]
				}
			]

			resourceAt('src/Test/datatypes/package-info'.java) => [
				if (contents.empty) {
					it.propagate [
						contents += java.containers.Package => [
							name = 'datatypes'
							namespaces += #['Test']
						]
					]
				}
			]
		]

		inputVariantFor(java.metamodel, 'creating only Java root package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'test'
				]
			]
		].alsoCompareToMainStepOfSameMetamodel()

		inputVariantFor(java.metamodel, 'creating only Java root package - uppercase package name') [ extension view |
			resourceAt('src/Test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'Test'
				]
			]
		]

		stepFor(uml.metamodel) [ extension view |
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

		inputVariantFor(uml.metamodel, 'uppercase package name') [ extension view |
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

		inputVariantFor(uml.metamodel, 'creating only UML root package') [ extension view |
			resourceAt('model/model'.uml).propagate [
				contents += uml.Model => [
					name = 'model'
					packagedElements += uml.Package => [
						name = 'test'
					]
				]
			]
		].alsoCompareToMainStepOfSameMetamodel()

		inputVariantFor(uml.metamodel, 'creating only UML root package - uppercase package name') [ extension view |
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

		stepFor(pcm.metamodel) [ extension view |
			Repository.from('model/Test'.repository).propagate [
				entityName = 'Renamed'
			]
		]

		inputVariantFor(pcm.metamodel, 'lowercase repository name') [ extension view |
			Repository.from('model/Test'.repository).propagate [
				entityName = 'renamed'
			]
		]

		inputVariantFor(pcm.metamodel, 'also rename file') [ extension view |
			resourceAt('model/Test'.repository).propagate [
				moveTo('model/Renamed'.repository)
				Repository.from(it).entityName = 'Renamed'
			]
		]

		stepFor(java.metamodel) [ extension view |
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

		inputVariantFor(java.metamodel, 'uppercase package name') [ extension view |
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

		inputVariantFor(java.metamodel, 'renaming only the root package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				moveTo('src/renamed/package-info'.java)
				Package.from(it).name = 'renamed'
			]
		]

		inputVariantFor(java.metamodel, 'renaming only the root package - uppercase package name') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				moveTo('src/Renamed/package-info'.java)
				Package.from(it).name = 'Renamed'
			]
		]

		stepFor(uml.metamodel) [ extension view |
			Model.from('model/model'.uml).propagate [
				packagedElements.get(0).name = 'renamed'
			]
		]

		inputVariantFor(uml.metamodel, 'uppercase package name') [ extension view |
			Model.from('model/model'.uml).propagate [
				packagedElements.get(0).name = 'Renamed'
			]
		]

		return testsThatStepsAreEquivalent
	}

	@TestFactory
	def deletion(extension EquivalenceTestBuilder builder) {
		userInteractions [
			onConfirmation [message.contains("delete the UML model")].respondWith(true)
		]

		dependsOn [creation(it)]

		stepFor(pcm.metamodel) [ extension view |
			resourceAt('model/Test'.repository).propagate[contents.clear()]
		]

		inputVariantFor(pcm.metamodel, 'deleting complete PCM resource') [ extension view |
			resourceAt('model/Test'.repository).propagate[delete(emptyMap)]
		]

		/**
		 * TODO: JW - Tests from Java are currently failing as reloading the packages loads layout information data which is absent when using the resources from memory. 
		 * As such, propagateChanges tries to apply delete operations for the layout information which cannot be found in the underlying resources.
		stepFor(java.metamodel) [ extension view |
			resourceAt('src/test/package-info'.java).propagate[delete(emptyMap)]
			resourceAt('src/test/contracts/package-info'.java) => [delete(emptyMap)]
			resourceAt('src/test/datatypes/package-info'.java) => [delete(emptyMap)]
		]

		inputVariantFor(java.metamodel, 'deleting only the root package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate[delete(emptyMap)]
		].alsoCompareToMainStepOfSameMetamodel()
		* */

		stepFor(uml.metamodel) [ extension view |
			Model.from('model/model'.uml).propagate [
				packagedElements.remove(0)
			]
		]

		inputVariantFor(uml.metamodel, 'deleting complete UML model') [ extension view |
			resourceAt('model/model'.uml).propagate[contents.clear()]
		]

		inputVariantFor(uml.metamodel, 'deleting complete UML resource') [ extension view |
			resourceAt('model/model'.uml).propagate[delete(emptyMap)]
		]

		return testsThatStepsAreEquivalent
	}
}
