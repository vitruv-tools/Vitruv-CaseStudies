package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.pcm2java.repository

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository.PcmParameterMappingTransformationTest
import tools.vitruv.framework.tests.util.TestUtil
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.resources.IProject
import tools.vitruv.framework.util.datatypes.VURI

class PcmParameterMappingGplTransformationTest extends PcmParameterMappingTransformationTest {
	override protected createChangePropagationSpecifications() {
		ChangePropagationSpecificationFactory.createPcm2JavaGplImplementationChangePropagationSpecification();
	}
	
	new() {
		setTestProjectCreator(projectName | TestUtil.createPlatformProject(projectName, true).getLocation().toFile());
	}
	
	protected def IProject getCurrentTestProject() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(getCurrentTestProjectFolder().getName());
	}
	
	private def String getPlatformModelPath(String modelPathWithinProject) {
		return this.getCurrentTestProject().getName() + "/" + modelPathWithinProject;
	}

	// We override the modelVuri getter, because we have to use platform URIs for the Java to PCM tests
	// because otherwise the JDT AST will not recognize the changes
	protected override VURI getModelVuri(String modelPathWithinProject) {
		return VURI.getInstance(getPlatformModelPath(modelPathWithinProject));
	}
}
