package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import java.io.IOException;

import org.emftext.language.java.members.ClassMethod;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceDemandingInternalBehaviorMappingTransformationTest extends Pcm2JavaTransformationTest {

	private static final String RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR_CLASS_METHOD = "resourceDemandingInternalBehaviourClassMethod";

	@Disabled
	@Test
	public void testCreateResourceDemandingInternalBehavior() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);
		final BasicComponent bc1 = this.addBasicComponentAndSync(repo);

		this.getUserInteraction().addNextTextInput(null);
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
		assertEquals(classMethod.getName(), expectedMethodName,
				"The class method should have the same name as the user selected");
	}

	private ResourceDemandingInternalBehaviour createAndSyncResourceDemandingInternalBehavior(
			final BasicComponent basicComponent, final String resourceDemandingInternalBehaviourName)
			throws IOException {
		final ResourceDemandingInternalBehaviour resourceDemandingInternalBehaviour = SeffFactory.eINSTANCE
				.createResourceDemandingInternalBehaviour();
		// resourceDemandingInternalBehaviour.setEntityName(resourceDemandingInternalBehaviourName);
		// basicComponent.getResourceDemandingInternalBehaviours__BasicComponent().add(resourceDemandingInternalBehaviour);
		propagate();
		return resourceDemandingInternalBehaviour;
	}
}