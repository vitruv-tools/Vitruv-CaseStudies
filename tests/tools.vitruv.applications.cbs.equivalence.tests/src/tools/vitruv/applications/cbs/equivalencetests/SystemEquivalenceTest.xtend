package tools.vitruv.applications.cbs.equivalencetests

import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import org.emftext.language.java.modifiers.ModifiersFactory
import org.junit.jupiter.api.TestFactory
import org.palladiosimulator.pcm.system.System
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName

import static extension tools.vitruv.applications.cbs.testutils.JavaCreators.*
import static extension tools.vitruv.applications.cbs.testutils.PcmCreators.*

@ReactionsEquivalenceTest
@InheritableDisplayName("systems")
class SystemEquivalenceTest {
	@TestFactory
	def creation(extension EquivalenceTestBuilder builder) {
		userInteractions [
			// TODO unify the interactions
			onTextInput [message.contains("path for the UML root model")].respondWith("model/")
			onTextInput [message.contains("name for the UML root model")].respondWith("model")
			onTextInput [message.contains("where to save the corresponding Uml-Model")].respondWith("model/model")
			onTextInput [message.contains("where to save the corresponding PCM model")].respondWith("model/Test")
			onMultipleChoiceSingleSelection [message.contains("a pcm::Repository or a pcm::System")].
				respondWithChoiceMatching[contains("System")]
		]

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
			resourceAt('src/test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'test'
				]
			]

			resourceAt('src/test/TestImpl'.java) => [
				/* In the test view, the implementation class may already be present due to transitive change propagation,
				 * so do not create it again. For the reference view created without change propagation, it has to be created.
				 */
				if (contents.empty) {
					propagate [
						contents += java.containers.CompilationUnit => [
							namespaces += #['test']
							classifiers += java.classifiers.Class => [
								name = 'TestImpl'
								makePublic()
								addModifier(ModifiersFactory.eINSTANCE.createFinal)
								members += java.members.Constructor => [
									name = 'TestImpl'
									makePublic()
								]
							]

						]
					]
				}
			]
		]

		inputVariantFor(java.metamodel, 'creating only a package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'test'
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
			userInteraction.addNextSingleSelection(2 /* do nothing */ )
			resourceAt('src/renamed/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'renamed'
				]
			]

			resourceAt('src/test/TestImpl'.java).propagate [
				/* Inserting the compilation unit into the containing package is necessary to be consistent with
				 * the underlying VirtualModel, in which the reference is set. The JaMoPP metamodel does not ensure
				 * that this reference is consistently set. It depends on the order in which packages and compilation units
				 * are created and/or loaded.
				 */
				val originalPackage = Package.from(resourceAt('src/test/package-info'.java))
				originalPackage.compilationUnits += CompilationUnit.from(it)
				moveTo('src/renamed/RenamedImpl'.java)
				CompilationUnit.from(it) => [
					namespaces.set(0, 'renamed')
					classifiers.get(0) => [
						name = 'RenamedImpl'
						members.get(0).name = 'RenamedImpl'
					]
				]
			]

			/* In the test view, the package may already be removed due to transitive change propagation,
			 * so trying to remove it again would result in an empty change (which is invalid to propagate).
			 * For the reference view created without change propagation, the package has to be removed manually.
			 */
			val originalPackage = resourceAt('src/test/package-info'.java)
			if (!originalPackage.contents.empty) {
				originalPackage.propagate [
					contents.clear()
				]
			}
		]

		return testsThatStepsAreEquivalent
	}
}
