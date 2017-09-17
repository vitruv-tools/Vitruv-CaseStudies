package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.emftext.language.java.members.ClassMethod;
import org.junit.Ignore;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*;

public class ResourceDemandingInternalBehaviorMappingTransformationTest extends Pcm2JavaTransformationTest {

	private static final String RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR_CLASS_METHOD = "resourceDemandingInternalBehaviourClassMethod";

	@Ignore
	@Test
	public void testCreateResourceDemandingInternalBehavior() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
		final BasicComponent bc1 = this.addBasicComponentAndSync(repo);

		this.getUserInteractor().addNextSelections(RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR_CLASS_METHOD);
		final ResourceDemandingInternalBehaviour resourceDemandingInternalBehaviour = this
				.createAndSyncResourceDemandingInternalBehavior(bc1,
						RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR_CLASS_METHOD);

		this.assertResourceDemandingInternalBehaviourCorrespondenceToMethod(resourceDemandingInternalBehaviour,
				RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR_CLASS_METHOD);
	}

	private void assertResourceDemandingInternalBehaviourCorrespondenceToMethod(
			final ResourceDemandingInternalBehaviour resourceDemandingInternalBehaviour,
			final String expectedMethodName) throws Throwable {
		final ClassMethod classMethod = claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(
				this.getCorrespondenceModel(), resourceDemandingInternalBehaviour, ClassMethod.class));
		assertEquals("The class method should have the same name as the user selected", classMethod.getName(),
				expectedMethodName);
	}

	private ResourceDemandingInternalBehaviour createAndSyncResourceDemandingInternalBehavior(
			final BasicComponent basicComponent, final String resourceDemandingInternalBehaviourName)
			throws IOException {
		final ResourceDemandingInternalBehaviour resourceDemandingInternalBehaviour = SeffFactory.eINSTANCE
				.createResourceDemandingInternalBehaviour();
		// resourceDemandingInternalBehaviour.setEntityName(resourceDemandingInternalBehaviourName);
		// basicComponent.getResourceDemandingInternalBehaviours__BasicComponent().add(resourceDemandingInternalBehaviour);
		this.saveAndSynchronizeChanges(basicComponent);
		return resourceDemandingInternalBehaviour;
	}
}