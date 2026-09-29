package tools.vitruv.applications.simulinkautosar.tests.autosar2simulink;

import static tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.claimAutoSARCompositeSwComponent;
import static tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.claimAutoSARElement;
import static tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.claimAutoSARPort;

import edu.kit.ipd.sdq.metamodels.autosar.AtomicSwComponent;
import edu.kit.ipd.sdq.metamodels.autosar.AutoSARFactory;
import edu.kit.ipd.sdq.metamodels.autosar.CompositeSwComponent;
import edu.kit.ipd.sdq.metamodels.autosar.DelegationSwConnector;
import edu.kit.ipd.sdq.metamodels.autosar.ProvidedPort;
import edu.kit.ipd.sdq.metamodels.autosar.RequiredPort;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Test;

public class AutoSARtoSimuLinkPortTest extends AbstractAutoSARToSimuLinkTest {

	private static final String DEFAULT_Component_NAME = "TestComponent";
	private static final String DEFAULT_COMPOSITE_COMPONENT_NAME = "TestCompositeComponent";
	private static final String DEFAULT_REQUIRED_PORT_NAME = "TestRequiredPort";
	private static final String DEFAULT_PROVIDED_PORT_NAME = "TestProvidedPort";
	private static final String DEFAULT_DELAGATIONSWCONNECTOR_NAME = "DelegationConnection";
	private static final String DEFAULT_REQUIREDPORT_COMPOSITE_NAME = "TestName";
	private static final String DEFAULT_OUTPORTBLOCK_NAME = "OutPortBlock";

	/*
	 *
	 *
	 * Tests for Ports
	 *
	 *
	 */

	@Test
	public void testCreateRequiredPortinSwComponent() throws Exception {
		createAtomicSWComponentInModel(DEFAULT_Component_NAME);
		viewFactory.changeAutoSARView(view -> {
			var atomicComponent = (AtomicSwComponent) claimAutoSARElement(view, AtomicSwComponent.class,
					DEFAULT_Component_NAME);
			RequiredPort requiredPort = AutoSARFactory.eINSTANCE.createRequiredPort();
			requiredPort.setName(DEFAULT_REQUIRED_PORT_NAME);
			atomicComponent.getPort().add(requiredPort);
		});
		equalityValidation.assertPortsInBlockOrComponent(DEFAULT_Component_NAME);
	}

	@Test
	public void testCreateProvidedPortinSwComponent() throws Exception {
		createAtomicSWComponentInModel(DEFAULT_Component_NAME);
		viewFactory.changeAutoSARView(view -> {
			var atomicComponent = (AtomicSwComponent) claimAutoSARElement(view, AtomicSwComponent.class,
					DEFAULT_Component_NAME);
			ProvidedPort providedPort = AutoSARFactory.eINSTANCE.createProvidedPort();
			providedPort.setName(DEFAULT_PROVIDED_PORT_NAME);
			atomicComponent.getPort().add(providedPort);
		});
		equalityValidation.assertPortsInBlockOrComponent(DEFAULT_Component_NAME);
	}

	@Test
	public void testCreateRequiredPortinCompositeSwComponent() throws Exception {
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME);
		viewFactory.changeAutoSARView(view -> {
			var compositeComponent = claimAutoSARCompositeSwComponent(view, CompositeSwComponent.class,
					DEFAULT_COMPOSITE_COMPONENT_NAME);
			ProvidedPort providedPort = AutoSARFactory.eINSTANCE.createProvidedPort();
			providedPort.setName(DEFAULT_REQUIRED_PORT_NAME);
			compositeComponent.getPort().add(providedPort);
		});
		equalityValidation.assertPortsInBlockOrComponent(DEFAULT_COMPOSITE_COMPONENT_NAME);
	}

	@Test
	public void testCreateProvidedPortinCompositeSwComponent() throws Exception {
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME);
		viewFactory.changeAutoSARView(view -> {
			var compositeComponent = claimAutoSARCompositeSwComponent(view, CompositeSwComponent.class,
					DEFAULT_COMPOSITE_COMPONENT_NAME);
			ProvidedPort providedPort = AutoSARFactory.eINSTANCE.createProvidedPort();
			providedPort.setName(DEFAULT_PROVIDED_PORT_NAME);
			compositeComponent.getPort().add(providedPort);
		});
		equalityValidation.assertPortsInBlockOrComponent(DEFAULT_COMPOSITE_COMPONENT_NAME);
	}

	@Test
	public void testDeleteRequiredPortinSwComponent() throws Exception {
		createAtomicSWComponentInModel(DEFAULT_Component_NAME);
		viewFactory.changeAutoSARView(view -> {
			var atomicComponent = (AtomicSwComponent) claimAutoSARElement(view, AtomicSwComponent.class,
					DEFAULT_Component_NAME);
			RequiredPort requiredPort = AutoSARFactory.eINSTANCE.createRequiredPort();
			requiredPort.setName(DEFAULT_REQUIRED_PORT_NAME);
			atomicComponent.getPort().add(requiredPort);
		});

		equalityValidation.assertPortsInBlockOrComponent(DEFAULT_Component_NAME);

		viewFactory.changeAutoSARView(view -> {
			var atomicComponent = claimAutoSARElement(view, AtomicSwComponent.class, DEFAULT_Component_NAME);
			EcoreUtil.delete(claimAutoSARPort(atomicComponent, DEFAULT_REQUIRED_PORT_NAME));
		});

		equalityValidation.assertNoPortWithNameInComponent(DEFAULT_Component_NAME, DEFAULT_REQUIRED_PORT_NAME);
	}

	@Test
	public void testDeleteProvidedPortinSwComponent() throws Exception {
		createAtomicSWComponentInModel(DEFAULT_Component_NAME);
		viewFactory.changeAutoSARView(view -> {
			var atomicComponent = (AtomicSwComponent) claimAutoSARElement(view, AtomicSwComponent.class,
					DEFAULT_Component_NAME);
			ProvidedPort providedPort = AutoSARFactory.eINSTANCE.createProvidedPort();
			providedPort.setName(DEFAULT_PROVIDED_PORT_NAME);
			atomicComponent.getPort().add(providedPort);
		});

		equalityValidation.assertPortsInBlockOrComponent(DEFAULT_Component_NAME);

		viewFactory.changeAutoSARView(view -> {
			var atomicComponent = claimAutoSARElement(view, AtomicSwComponent.class, DEFAULT_Component_NAME);
			EcoreUtil.delete(claimAutoSARPort(atomicComponent, DEFAULT_PROVIDED_PORT_NAME));
		});

		equalityValidation.assertNoPortWithNameInComponent(DEFAULT_Component_NAME, DEFAULT_PROVIDED_PORT_NAME);
	}

	/*
	 *
	 * Test of SwConnectors
	 *
	 */

	@Test
	public void testCreateDelegationSwConnector() throws Exception {
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME);
		createAtomicSWComponentInModel(DEFAULT_Component_NAME);

		viewFactory.changeAutoSARView(view -> {
			var atomicComponent = (AtomicSwComponent) claimAutoSARElement(view, AtomicSwComponent.class,
					DEFAULT_Component_NAME);
			ProvidedPort atomicOutPort = AutoSARFactory.eINSTANCE.createProvidedPort();
			atomicOutPort.setName(DEFAULT_PROVIDED_PORT_NAME);

			atomicComponent.getPort().add(atomicOutPort);

			var compositeComponent = claimAutoSARCompositeSwComponent(view, CompositeSwComponent.class,
					DEFAULT_COMPOSITE_COMPONENT_NAME);

			ProvidedPort compositeOutPort = AutoSARFactory.eINSTANCE.createProvidedPort();
			compositeOutPort.setName(DEFAULT_REQUIREDPORT_COMPOSITE_NAME);

			compositeComponent.getPort().add(compositeOutPort);
			compositeComponent.getAtomicswcomponent().add(atomicComponent);

			DelegationSwConnector delegationSwConnector = AutoSARFactory.eINSTANCE.createDelegationSwConnector();
			delegationSwConnector.setName(DEFAULT_DELAGATIONSWCONNECTOR_NAME);
			delegationSwConnector.setInnerPort(atomicOutPort);
			delegationSwConnector.setOuterPort(compositeOutPort);
			compositeComponent.getSwconnector().add(delegationSwConnector);
		});
		equalityValidation.assertDelegationSwConnectorEqualsSingleConnection(DEFAULT_DELAGATIONSWCONNECTOR_NAME,
				DEFAULT_COMPOSITE_COMPONENT_NAME, DEFAULT_OUTPORTBLOCK_NAME, DEFAULT_Component_NAME);
	}
}
