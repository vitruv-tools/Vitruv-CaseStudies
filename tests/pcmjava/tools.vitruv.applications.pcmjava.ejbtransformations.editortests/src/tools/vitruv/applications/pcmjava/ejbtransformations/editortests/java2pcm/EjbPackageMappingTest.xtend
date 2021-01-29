package tools.vitruv.applications.pcmjava.ejbtransformations.editortests.java2pcm

import org.palladiosimulator.pcm.repository.Repository

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertEquals

class EjbPackageMappingTest extends EjbJava2PcmTransformationTest {

	@Test
	def testCreatePackage() {
		// test
		super.addRepoContractsAndDatatypesPackage()

		// check: main package needs to correspond to a repository
		val correspondingRepo = this.correspondenceModel.getCorrespondingEObjectsByType(this.mainPackage, Repository).
			claimOne
		assertEquals(correspondingRepo.entityName, this.mainPackage.name,
			"Corresponding Repository has not the same name as the main package")
	}

}
