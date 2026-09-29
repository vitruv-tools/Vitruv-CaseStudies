package tools.vitruv.applications.simulinkautosar.tests.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.claimAutoSARBlockinComposite;
import static tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.claimAutoSARElement;
import static tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.claimAutoSARModel;
import static tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.claimAutoSARPort;
import static tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.claimSimuLinkBlockOfSubsystem;
import static tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.claimSimuLinkConnection;
import static tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.claimSimuLinkElement;
import static tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.claimSimuLinkModel;
import static tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.claimSimuLinkPort;

import edu.kit.ipd.sdq.metamodels.autosar.AssemblySwConnector;
import edu.kit.ipd.sdq.metamodels.autosar.AtomicSwComponent;
import edu.kit.ipd.sdq.metamodels.autosar.AutoSARElement;
import edu.kit.ipd.sdq.metamodels.autosar.CompositeSwComponent;
import edu.kit.ipd.sdq.metamodels.autosar.DelegationSwConnector;
import edu.kit.ipd.sdq.metamodels.autosar.ProvidedPort;
import edu.kit.ipd.sdq.metamodels.autosar.RequiredPort;
import edu.kit.ipd.sdq.metamodels.autosar.SwComponent;
import edu.kit.ipd.sdq.metamodels.simulink.Block;
import edu.kit.ipd.sdq.metamodels.simulink.InPort;
import edu.kit.ipd.sdq.metamodels.simulink.OutPort;
import edu.kit.ipd.sdq.metamodels.simulink.SimulinkElement;
import edu.kit.ipd.sdq.metamodels.simulink.SingleConnection;
import edu.kit.ipd.sdq.metamodels.simulink.SubSystem;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import tools.vitruv.framework.views.View;

/**
 * This class provides validations for the equal existence of classifiers of different types to exist
 * in both AutoSAR and SimuLink models.
 */
public class SimuLinkAutoSARClassifierEqualityValidation {

	/**
	 * Executes the given function on a view that contains both AutoSAR and SimuLink models.
	 */
	@FunctionalInterface
	public interface ViewExecutor {
		void execute(Consumer<View> viewApplication) throws Exception;
	}

	private final String autoSARModelName;
	private final ViewExecutor viewExecutor;

	public SimuLinkAutoSARClassifierEqualityValidation(String autoSARModelName, ViewExecutor viewExecutor) {
		this.autoSARModelName = autoSARModelName;
		this.viewExecutor = viewExecutor;
	}

	public void assertBlockWithNameInRootModel(String blockName) throws Exception {
		assertElementWithName(Block.class, SwComponent.class, blockName);
	}

	public void assertSubSystemtWithNameInRootModel(String compositeComponentName) throws Exception {
		assertElementWithName(SubSystem.class, CompositeSwComponent.class, compositeComponentName);
	}

	public void assertPortsInBlockOrComponent(String blockName) throws Exception {
		assertElementWithName(Block.class, SwComponent.class, blockName);
	}

	public void assertNoPortWithNameInComponent(String blockName, String portName) throws Exception {
		assertNoPortWithNameInComponentOrBlock(blockName, portName);
	}

	public void assertSwComponentWithNameInRootModel(String swComponentName) throws Exception {
		assertElementWithName(Block.class, SwComponent.class, swComponentName);
	}

	public void assertCompositeSwComponentWithNameInRootModel(String compositeComponentName) throws Exception {
		assertElementWithName(SubSystem.class, CompositeSwComponent.class, compositeComponentName);
	}

	/*
	 *
	 * Generic method to assert that no SimuLinkElement or AutoSAR Element exist
	 *
	 */
	public void assertNoElementWithNameInRootModel(String elememtName) throws Exception {
		viewExecutor.execute(view -> {
			assertThat("no Element in AutoSAR model with name " + elememtName + " is expected to exist",
					claimAutoSARModel(view, autoSARModelName).getSwcomponent().stream()
							.filter(component -> Objects.equals(component.getName(), elememtName))
							.collect(Collectors.toSet()),
					is(Collections.emptySet()));
			assertThat("no SimuLink Element with name " + elememtName + " is expected to exist",
					claimSimuLinkModel(view, autoSARModelName).getContains().stream()
							.filter(block -> Objects.equals(block.getName(), elememtName))
							.collect(Collectors.toSet()),
					is(Collections.emptySet()));
		});
	}

	private void assertNoPortWithNameInComponentOrBlock(String blockName, String portName) throws Exception {
		viewExecutor.execute(view -> {
			assertThat("no Port in AutoSAR Component with name " + portName + " is expected to exist",
					claimAutoSARModel(view, autoSARModelName).getSwcomponent().stream()
							.filter(component -> Objects.equals(component.getName(), blockName))
							.findFirst().orElseThrow()
							.getPort().stream()
							.filter(port -> Objects.equals(port.getName(), portName))
							.collect(Collectors.toSet()),
					is(Collections.emptySet()));
			assertThat("no SimuLink Element with name " + portName + " is expected to exist",
					claimSimuLinkModel(view, autoSARModelName).getContains().stream()
							.filter(block -> Objects.equals(block.getName(), blockName))
							.findFirst().orElseThrow()
							.getPorts().stream()
							.filter(port -> Objects.equals(port.getName(), portName))
							.collect(Collectors.toSet()),
					is(Collections.emptySet()));
		});
	}

	/*
	 *
	 * Generic method to assert that a SimuLinkElement and an AutoSAR are equal
	 *
	 */
	private void assertElementWithName(Class<? extends SimulinkElement> simulinkElement,
			Class<? extends AutoSARElement> autoSARElement, String name) throws Exception {
		viewExecutor.execute(view -> {
			Block simuLinkBlock = claimSimuLinkElement(view, simulinkElement, name);
			SwComponent autoSARComponent = claimAutoSARElement(view, autoSARElement, name);
			assertElementEquals(simuLinkBlock, autoSARComponent);
		});
	}

	public static void assertElementEquals(Block block, SwComponent component) {
		if (block instanceof SubSystem subsystem && component instanceof CompositeSwComponent compositeComponent) {
			assertSimuLinkSubsystemEqualsAutoSARCompositeComponent(subsystem, compositeComponent);
		} else if (block != null && component instanceof AtomicSwComponent atomicComponent) {
			assertSimuLinkBlockEqualsAutoSARSwComponent(block, atomicComponent);
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.asList(block, component));
		}
	}

	private static void assertSimuLinkSubsystemEqualsAutoSARCompositeComponent(SubSystem subsystem,
			CompositeSwComponent compositeComponent) {
		assertEquals(subsystem.getName(), compositeComponent.getName());
		assertSameSubBlocksOrComponents(subsystem, compositeComponent);
	}

	private static void assertSameSubBlocksOrComponents(SubSystem subsystem,
			CompositeSwComponent compositeComponent) {
		var simuLinkBlocks = subsystem.getSubBlocks();
		var autoSARComponents = compositeComponent.getAtomicswcomponent();

		assertEquals(autoSARComponents.size(), simuLinkBlocks.size());

		for (Block simuLinkBlock : simuLinkBlocks) {
			AtomicSwComponent autoSARComponent = claimAutoSARBlockinComposite(compositeComponent,
					simuLinkBlock.getName());
			assertSimuLinkBlockEqualsAutoSARSwComponent(simuLinkBlock, autoSARComponent);
		}
	}

	private static void assertSimuLinkBlockEqualsAutoSARSwComponent(Block simulinkElement,
			SwComponent autoSARElement) {
		assertEquals(simulinkElement.getName(), autoSARElement.getName());
		assertSamePortsInElement(simulinkElement, autoSARElement);
	}

	private static void assertSamePortsInElement(Block simulinkElement, SwComponent autoSARElement) {
		var simulinkPorts = simulinkElement.getPorts();
		var autosarPorts = autoSARElement.getPort();

		assertEquals(simulinkPorts.size(), autosarPorts.size());

		for (var simulinkPort : simulinkPorts) {
			var autosarPort = claimAutoSARPort(autoSARElement, simulinkPort.getName());

			if (simulinkPort instanceof InPort inPort) {
				assertTrue(autosarPort instanceof RequiredPort);
				assertSimuLinkPortEqualsAutoSARSwPort(inPort, (RequiredPort) autosarPort);
			} else if (simulinkPort instanceof OutPort) {
				assertTrue(autosarPort instanceof ProvidedPort);
			}
		}
	}

	private static void assertSimuLinkPortEqualsAutoSARSwPort(InPort inPort, RequiredPort requiredPort) {
		assertEquals(inPort.getName(), requiredPort.getName());
	}

	public void assertDelegationSwConnectorEqualsSingleConnection(String connectionName,
			String compositeComponentName, String addedOutPortBlockName, String atomicComponentName)
			throws Exception {
		viewExecutor.execute(view -> {
			var compositeComponent = (CompositeSwComponent) claimAutoSARElement(view, CompositeSwComponent.class,
					compositeComponentName);
			var delegationSwConnector = (DelegationSwConnector) compositeComponent.getSwconnector().stream()
					.filter(connector -> Objects.equals(connector.getName(), connectionName))
					.findFirst().orElseThrow();
			var singleConnection = (SingleConnection) claimSimuLinkConnection(view, connectionName);
			var subsystem = (SubSystem) claimSimuLinkElement(view, SubSystem.class, compositeComponentName);
			var addedOutPortBlock = claimSimuLinkBlockOfSubsystem(subsystem, addedOutPortBlockName);

			assertEquals(singleConnection.getName(), delegationSwConnector.getName());
			assertEquals(singleConnection.getOutport().getName(), delegationSwConnector.getInnerPort().getName());
			assertEquals(singleConnection.getInport().getName(),
					claimSimuLinkPort(addedOutPortBlock, singleConnection.getInport().getName()).getName());
		});
	}

	public void assertAssemblySwConnectorEqualsSingleConnection(String connectionName, String compositeComponentName)
			throws Exception {
		viewExecutor.execute(view -> {
			var compositeComponent = (CompositeSwComponent) claimAutoSARElement(view, CompositeSwComponent.class,
					compositeComponentName);
			var assemblySwConnector = (AssemblySwConnector) compositeComponent.getSwconnector().stream()
					.filter(connector -> Objects.equals(connector.getName(), connectionName))
					.findFirst().orElseThrow();
			var singleConnection = (SingleConnection) claimSimuLinkConnection(view, connectionName);

			assertEquals(singleConnection.getName(), assemblySwConnector.getName());
			assertEquals(singleConnection.getOutport().getName(), assemblySwConnector.getProvidedport().get(0).getName());
			assertEquals(singleConnection.getInport().getName(), assemblySwConnector.getRequiredport().get(0).getName());
		});
	}
}
