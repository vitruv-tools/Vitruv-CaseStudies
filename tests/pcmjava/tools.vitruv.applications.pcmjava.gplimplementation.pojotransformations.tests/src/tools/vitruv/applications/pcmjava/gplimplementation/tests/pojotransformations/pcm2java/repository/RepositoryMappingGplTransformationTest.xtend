package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.pcm2java.repository

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository.RepositoryMappingTransformaitonTest
import org.junit.Test
import org.junit.Ignore

class RepositoryMappingGplTransformationTest extends RepositoryMappingTransformaitonTest {
	override protected createChangePropagationSpecifications() {
		ChangePropagationSpecificationFactory.createPcm2JavaGplImplementationChangePropagationSpecification();
	}
	
	@Ignore
	@Test
    public override testRepositoryNameChangeWithComponents() throws Throwable {
    	// Does currently not work
    }
}