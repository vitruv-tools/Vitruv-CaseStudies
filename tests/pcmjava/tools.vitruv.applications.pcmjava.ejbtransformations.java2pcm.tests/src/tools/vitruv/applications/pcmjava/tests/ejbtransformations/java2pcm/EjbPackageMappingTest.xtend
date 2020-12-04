package tools.vitruv.applications.pcmjava.tests.ejbtransformations.java2pcm

import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository

import static org.junit.Assert.assertEquals

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class EjbPackageMappingTest extends EjbJava2PcmTransformationTest {
	
	@Test
	def testCreatePackage(){
		//test
		super.addRepoContractsAndDatatypesPackage()
				
		//check: main package needs to correspond to a repository
		val correspondingRepo = this.correspondenceModel.getCorrespondingEObjectsByType(this.mainPackage, Repository).claimOne
		assertEquals("Corresponding Repository has not the same name as the main package", correspondingRepo.entityName, this.mainPackage.name)
	}
	
}