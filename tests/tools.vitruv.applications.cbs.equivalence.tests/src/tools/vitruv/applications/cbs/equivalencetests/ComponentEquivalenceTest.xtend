package tools.vitruv.applications.cbs.equivalencetests

import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import org.emftext.language.java.modifiers.ModifiersFactory
import org.junit.jupiter.api.TestFactory
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.cbs.testutils.equivalencetest.ParameterizedEquivalenceTestBuilder
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName

import static extension tools.vitruv.applications.cbs.testutils.JavaCreators.*
import static extension tools.vitruv.applications.cbs.testutils.PcmCreators.*

@ReactionsEquivalenceTest
@InheritableDisplayName("components")
class ComponentEquivalenceTest {
	protected def getRepository() {
		new RepositoryEquivalenceTest
	}

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

				resourceAt('src/test/testComponent/TestComponentImpl'.java) => [
					/* In the test view, the implementation class may already be present due to transitive change propagation,
					 * so do not create it again. For the reference view created without change propagation, it has to be created.
					 */
					if (contents.empty) {
						propagate [
							contents += java.containers.CompilationUnit => [
								namespaces += #['test', 'testComponent']
								classifiers += java.classifiers.Class => [
									name = 'TestComponentImpl'
									makePublic()
									addModifier(ModifiersFactory.eINSTANCE.createFinal)
									members += java.members.Constructor => [
										name = 'TestComponentImpl'
										makePublic()
									]
								]
							]
						]
					}
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
				userInteraction.addNextSingleSelection(3 /* do nothing */ )
				resourceAt('src/test/renamedComponent/package-info'.java).propagate [
					contents += java.containers.Package => [
						namespaces += 'test'
						name = 'renamedComponent'
					]
				]

				resourceAt('src/test/testComponent/TestComponentImpl'.java).propagate [
					/* Inserting the compilation unit into the containing package is necessary to be consistent with
					 * the underlying VirtualModel, in which the reference is set. The JaMoPP metamodel does not ensure
					 * that this reference is consistently set. It depends on the order in which packages and compilation units
					 * are created and/or loaded.
					 */
					val originalPackage = Package.from(resourceAt('src/test/testComponent/package-info'.java))
					originalPackage.compilationUnits += CompilationUnit.from(it)
					it.moveTo('src/test/renamedComponent/RenamedComponentImpl'.java)
					CompilationUnit.from(it) => [
						namespaces.set(1, 'renamedComponent')
						name = 'RenamedComponentImpl'
						classifiers.get(0) => [
							name = 'RenamedComponentImpl'
							members.get(0).name = 'RenamedComponentImpl'
						]
					]
				]

				/* In the test view, the package may already be removed due to transitive change propagation,
				 * so trying to remove it again would result in an empty change (which is invalid to propagate).
				 * For the reference view created without change propagation, the package has to be removed manually.
				 */
				val originalPackage = resourceAt('src/test/testComponent/package-info'.java)
				if (!originalPackage.contents.empty) {
					originalPackage.propagate [
						contents.clear()
					]
				}
			]
		]
	}
}
