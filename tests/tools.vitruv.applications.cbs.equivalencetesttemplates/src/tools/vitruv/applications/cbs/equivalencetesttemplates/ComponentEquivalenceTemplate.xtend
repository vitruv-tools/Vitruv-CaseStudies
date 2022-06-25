package tools.vitruv.applications.cbs.equivalencetesttemplates

import org.junit.jupiter.api.TestFactory
import static extension tools.vitruv.applications.cbs.testutils.PcmCreators.*
import static extension tools.vitruv.applications.cbs.testutils.JavaCreators.*
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.CompositeComponent
import tools.vitruv.applications.cbs.testutils.equivalencetest.ParameterizedEquivalenceTestBuilder
import org.emftext.language.java.containers.Package
import org.emftext.language.java.containers.CompilationUnit
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName

@InheritableDisplayName("components")
abstract class ComponentEquivalenceTemplate {
	abstract protected def RepositoryEquivalenceTemplate getRepository()

	@TestFactory
	def creation(extension ParameterizedEquivalenceTestBuilder parameterBuilder) {
		parameterizedBy(#[
			parameters(BasicComponent, 0),
			parameters(CompositeComponent, 1)
		], [$0.simpleName]) [ extension builder, componentType, componentTypeChoice |

			dependsOn[repository.creation(it)]

			stepFor(pcm.metamodel) [ extension view |
				Repository.from('model/Test'.repository).propagate [
					components__Repository += pcm.repository.create(componentType) => [
						entityName = 'TestComponent'
					]
				]
			]

			inputVariantFor(pcm.metamodel, 'lowercase name') [ extension view |
				Repository.from('model/Test'.repository).propagate [
					components__Repository += pcm.repository.create(componentType) => [
						entityName = 'testComponent'
					]
				]
			]

			stepFor(java.metamodel) [ extension view |
				userInteraction.addNextSingleSelection(componentTypeChoice /* create appropriate Component */ )
				resourceAt('src/test/testComponent/package-info'.java).propagate [
					contents += java.containers.Package => [
						namespaces += 'test'
						name = 'testComponent'
					]
				]

				resourceAt('src/test/testComponent/TestComponentImpl'.java).propagate [
					contents += java.containers.CompilationUnit => [
						namespaces += #['test', 'testComponent']
						classifiers += java.classifiers.Class => [
							name = 'TestComponentImpl'
							makePublic()
						]
					]
				]
			]

			inputVariantFor(java.metamodel, 'creating only a package') [ extension view |
				userInteraction.addNextSingleSelection(componentTypeChoice /* create appropriate Component */ )
				resourceAt('src/test/testComponent/package-info'.java).propagate [
					contents += java.containers.Package => [
						namespaces += 'test'
						name = 'testComponent'
					]
				]
			].alsoCompareToMainStepOfSameMetamodel()

			inputVariantFor(java.metamodel, 'creating only a class') [ extension view |
				userInteraction.addNextSingleSelection(componentTypeChoice /* create appropriate Component */ )
				resourceAt('src/test/testComponent/TestComponentImpl'.java).propagate [
					contents += java.containers.CompilationUnit => [
						namespaces += #['test', 'testComponent']
						classifiers += java.classifiers.Class => [
							name = 'TestComponentImpl'
							makePublic()
						]
					]
				]
			].alsoCompareToMainStepOfSameMetamodel()
		]
	}

	@TestFactory
	def renaming(extension ParameterizedEquivalenceTestBuilder parameterBuilder) {
		dependsOn([creation(it)]) [ extension builder |

			stepFor(pcm.metamodel) [ extension view |
				Repository.from('model/Test'.repository).components__Repository.get(0).propagate [
					entityName = 'RenamedComponent'
				]
			]

			inputVariantFor(pcm.metamodel, 'lowercase name') [ extension view |
				Repository.from('model/Test'.repository).components__Repository.get(0).propagate [
					entityName = 'renamedComponent'
				]
			]

			stepFor(java.metamodel) [ extension view |
				resourceAt('src/test/testComponent/package-info'.java).propagate [
					moveTo('src/test/renamedComponent/package-info'.java)
					Package.from(it).name = 'renamedComponent'
				]

				resourceAt('src/test/testComponent/TestComponentImpl'.java).propagate [
					moveTo('src/test/renamedComponent/RenamedComponentImpl'.java)
					CompilationUnit.from(it) => [
						namespaces.set(1, 'renamedComponent')
						name = 'RenamedComponentImpl'
					]
				]
			]
		]
	}
}
