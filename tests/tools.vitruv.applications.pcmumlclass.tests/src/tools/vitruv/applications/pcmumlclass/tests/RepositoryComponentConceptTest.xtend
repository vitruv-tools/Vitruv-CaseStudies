package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.VisibilityKind
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import java.nio.file.Path
import static extension tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper.isPackageFor
import static tools.vitruv.applications.pcmumlclass.tests.PcmUmlElementEqualityValidation.*
import static extension tools.vitruv.applications.pcmumlclass.tests.PcmQueryUtil.*
import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*
/**
 * This test class tests the reactions and routines that are supposed to synchronize synchronize a pcm::RepositoryComponent with 
 * its corresponding uml::Package, uml::Class (implementation), and uml::Operation (constructor).
 * <br><br>
 * Related files: 
 * 		PcmRepositoryComponent.reactions,
 * 		UmlRepositoryComponentPackage.reactions,
 * 		UmlIPREClassReactions.reactions,
 * 		UmlIPREConstructorOperation.reactions
 */
class RepositoryComponentConceptTest extends PcmUmlClassApplicationTest {

	val COMPONENT_NAME = "testComponent"

	@Test
	def void testRepositoryComponentConcept_PCM() {
		initPCMRepository
		
		changePcmView[
			var pcmComponent = RepositoryFactory.eINSTANCE.createCompositeComponent
			pcmComponent.entityName = COMPONENT_NAME
			defaultPcmRepository.components__Repository += pcmComponent
		]
		
		validateUmlAndPcmPackagesView [
			val umlPackage = defaultUmlModel.claimPackage(PACKAGE_NAME)
			val pcmPackage = claimPcmRepository(PACKAGE_NAME_FIRST_UPPER)

			println("uml " + umlPackage.allOwnedElements)
			println("uml " + umlPackage.eContents)
			//assertNotNull(umlContractsPackage.ownedElements)
			assertNotNull(pcmPackage.eContents)

			assertElementsEqual(umlPackage, pcmPackage)
		]
	}

	@Test
	def void testRepositoryComponentConcept_UML() {
		
	}
}
