package tools.vitruv.applications.cbs.equivalencetesttemplates

import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import org.emftext.language.java.modifiers.ModifiersFactory
import org.junit.jupiter.api.TestFactory
import org.palladiosimulator.pcm.system.System
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder
import tools.vitruv.applications.cbs.testutils.junit.InheritableDisplayName

import static extension tools.vitruv.applications.cbs.testutils.JavaCreators.*
import static extension tools.vitruv.applications.cbs.testutils.PcmCreators.*

@InheritableDisplayName("systems")
abstract class SystemEquivalenceTemplate {

	@TestFactory
	def creation(extension EquivalenceTestBuilder builder) {
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
			val packageResource = resourceAt('src/test/package-info'.java)
			packageResource.startRecordingChanges
			packageResource => [
				contents += java.containers.Package => [
					name = 'test'
				]
			]
			packageResource.stopRecordingChanges

			val compilationUnitResource = resourceAt('src/test/TestImpl'.java)
			compilationUnitResource.startRecordingChanges
			compilationUnitResource => [
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
			compilationUnitResource.stopRecordingChanges
			propagate
		]

		inputVariantFor(java.metamodel, 'creating only a package') [ extension view |
			resourceAt('src/test/package-info'.java).propagate [
				contents += java.containers.Package => [
					name = 'test'
				]
			]
		].alsoCompareToMainStepOfSameMetamodel()

		inputVariantFor(java.metamodel, 'creating only a class') [ extension view |
			resourceAt('src/test/TestImpl'.java).propagate [
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
			val packageResource = resourceAt('src/test/package-info'.java)
			packageResource.startRecordingChanges
			packageResource => [
				moveTo('src/renamed/package-info'.java)
				Package.from(it).name = 'renamed'
			]
			packageResource.stopRecordingChanges

			val compilationUnitResource = resourceAt('src/test/TestImpl'.java)
			compilationUnitResource.startRecordingChanges
			compilationUnitResource => [
				moveTo('src/renamed/RenamedImpl'.java)
				CompilationUnit.from(it) => [
					namespaces.set(0, 'renamed')
					classifiers.get(0) => [
						name = 'RenamedImpl'
						members.get(0).name = 'RenamedImpl'
					]
				]
			]
			compilationUnitResource.stopRecordingChanges
			propagate
		]

		return testsThatStepsAreEquivalent
	}
}
