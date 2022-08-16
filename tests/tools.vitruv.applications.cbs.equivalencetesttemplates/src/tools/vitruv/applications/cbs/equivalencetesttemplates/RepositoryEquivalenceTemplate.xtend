package tools.vitruv.applications.cbs.equivalencetesttemplates

import org.eclipse.uml2.uml.Model
import org.emftext.language.java.containers.Package
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.TestMethodOrder
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName

import static extension tools.vitruv.applications.cbs.testutils.JavaCreators.*
import static extension tools.vitruv.applications.cbs.testutils.PcmCreators.*
import static extension tools.vitruv.applications.cbs.testutils.UmlCreators.*

@TestMethodOrder(OrderAnnotation)
@InheritableDisplayName("repositories")
abstract class RepositoryEquivalenceTemplate {

	@Order(0)
	@TestFactory
	def creation(extension EquivalenceTestBuilder builder) {
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
			val mainPackageResource = resourceAt('src/test/package-info'.java)
			mainPackageResource.startRecordingChanges
			mainPackageResource => [
				contents += java.containers.Package => [
					name = 'test'
				]
			]
			mainPackageResource.stopRecordingChanges

			val contractsPackageResource = resourceAt('src/test/contracts/package-info'.java)
			contractsPackageResource.startRecordingChanges
			contractsPackageResource => [
				contents += java.containers.Package => [
					name = 'contracts'
					namespaces += #['test']
				]
			]
			contractsPackageResource.stopRecordingChanges

			val datatypesPackageResource = resourceAt('src/test/datatypes/package-info'.java)
			datatypesPackageResource.startRecordingChanges
			datatypesPackageResource => [
				contents += java.containers.Package => [
					name = 'datatypes'
					namespaces += #['test']
				]
			]
			datatypesPackageResource.stopRecordingChanges
			propagate
		]

		inputVariantFor(java.metamodel, 'uppercase package name') [ extension view |
			val mainPackageResource = resourceAt('src/Test/package-info'.java)
			mainPackageResource.startRecordingChanges
			mainPackageResource => [
				contents += java.containers.Package => [
					name = 'Test'
				]
			]
			mainPackageResource.stopRecordingChanges

			val contractsPackageResource = resourceAt('src/Test/contracts/package-info'.java)
			contractsPackageResource.startRecordingChanges
			contractsPackageResource => [
				contents += java.containers.Package => [
					name = 'contracts'
					namespaces += #['Test']
				]
			]
			contractsPackageResource.stopRecordingChanges

			val datatypesPackageResource = resourceAt('src/Test/datatypes/package-info'.java)
			datatypesPackageResource.startRecordingChanges
			datatypesPackageResource => [
				contents += java.containers.Package => [
					name = 'datatypes'
					namespaces += #['Test']
				]
			]
			datatypesPackageResource.stopRecordingChanges
			propagate
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
		dependsOn [creation(it)]

		stepFor(pcm.metamodel) [ extension view |
			resourceAt('model/Test'.repository).propagate[contents.clear()]
		]

		inputVariantFor(pcm.metamodel, 'deleting complete PCM resource') [ extension view |
			resourceAt('model/Test'.repository).propagate[delete(emptyMap)]
		]

		stepFor(java.metamodel) [ extension view |
			resourceAt('src/test/package-info'.java).propagate[delete(emptyMap)]
			resourceAt('src/test/contracts/package-info'.java) => [delete(emptyMap)]
			resourceAt('src/test/datatypes/package-info'.java) => [delete(emptyMap)]
		]

		inputVariantFor(java.metamodel, 'deleting only the root package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate[delete(emptyMap)]
		].alsoCompareToMainStepOfSameMetamodel()

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
