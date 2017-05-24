package tools.vitruv.applications.pcmjava.pojotransformations.demo

import org.eclipse.core.runtime.CoreException
import org.eclipse.core.resources.IProject
import tools.vitruv.framework.tests.util.TestUtil
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import java.util.Collections
import tools.vitruv.domains.pcm.PcmNamespace
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.userinteraction.impl.UserInteractor
import tools.vitruv.domains.emf.builder.VitruviusEmfBuilder
import tools.vitruv.domains.java.builder.VitruviusJavaBuilderApplicator
import tools.vitruv.domains.emf.builder.VitruviusEmfBuilderApplicator
import tools.vitruv.domains.java.builder.VitruviusJavaBuilder
import tools.vitruv.framework.monitorededitor.ProjectBuildUtils
import tools.vitruv.applications.pcmjava.util.PcmJavaRepositoryCreationUtil
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmChangePropagationSpecification
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain
import org.eclipse.core.resources.ResourcesPlugin

class JavaPcmProjectAndVsumGeneration {
	
	public def void createProjectAndVsum() {
		TuidManager.instance.reinitialize();
        val project = createTestProject("testProject");
        val virtualModel = createVirtualModel("testProjectVsum");
        virtualModel.userInteractor = new UserInteractor();
        // add PCM Java Builder to Project under test
		val VitruviusJavaBuilderApplicator pcmJavaBuilder = new VitruviusJavaBuilderApplicator();
		val VitruviusEmfBuilderApplicator emfBuilder = new VitruviusEmfBuilderApplicator();
		pcmJavaBuilder.addToProject(project, virtualModel.folder, Collections.EMPTY_LIST);
		emfBuilder.addToProject(project, virtualModel.folder, Collections.singletonList(PcmNamespace.REPOSITORY_FILE_EXTENSION));
		// build the project
		ProjectBuildUtils.issueIncrementalBuild(project, VitruviusJavaBuilder.BUILDER_ID);
		ProjectBuildUtils.issueIncrementalBuild(project, VitruviusEmfBuilder.BUILDER_ID);
	}
	
	private def InternalVirtualModel createVirtualModel(String vsumName) {
		val metamodels = this.createVitruvDomains();
		val project = ResourcesPlugin.workspace.root.getProject(vsumName);
		project.create(null);
    	project.open(null);
		val virtualModel = TestUtil.createVirtualModel(project.location.toFile, false, metamodels, createChangePropagationSpecifications());
		return virtualModel;
	}
	
	protected def Iterable<VitruvDomain> createVitruvDomains() {
		return PcmJavaRepositoryCreationUtil.createPcmJamoppMetamodels();
	}
	
	protected def Iterable<ChangePropagationSpecification> createChangePropagationSpecifications() {
		return #[new Java2PcmChangePropagationSpecification(), new Pcm2JavaChangePropagationSpecification()];
	}
	
	protected def IProject createTestProject(String projectName) throws CoreException {
        var project = TestUtil.getProjectByName(projectName);
        if (!project.exists()) {
            project = TestUtil.createPlatformProject(projectName, false);
        }
   		return project;
	}
	
}