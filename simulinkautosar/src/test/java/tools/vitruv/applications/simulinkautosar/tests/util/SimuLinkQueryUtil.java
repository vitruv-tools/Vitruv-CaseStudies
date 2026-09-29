package tools.vitruv.applications.simulinkautosar.tests.util;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;

import edu.kit.ipd.sdq.metamodels.simulink.Block;
import edu.kit.ipd.sdq.metamodels.simulink.Connection;
import edu.kit.ipd.sdq.metamodels.simulink.Port;
import edu.kit.ipd.sdq.metamodels.simulink.SimuLinkPackage;
import edu.kit.ipd.sdq.metamodels.simulink.SimulinkElement;
import edu.kit.ipd.sdq.metamodels.simulink.SimulinkModel;
import edu.kit.ipd.sdq.metamodels.simulink.SubSystem;
import java.util.List;
import java.util.Objects;
import org.eclipse.emf.ecore.EClassifier;
import tools.vitruv.framework.views.View;

/*
 *
 * Utility Class for Simulink
 * Contains Methods to claim and find SimuLinkElements of different types
 *
 */
public final class SimuLinkQueryUtil {

	private SimuLinkQueryUtil() {
	}

	/*
	 * Returns a SimuLinkModel filtered by name in the RootObjects of the view.
	 * If no model with the name is found an error is thrown by the claimOne
	 */
	public static SimulinkModel claimSimuLinkModel(View view, String modelname) {
		return claimOne(view.getRootObjects(SimulinkModel.class).stream()
				.filter(model -> Objects.equals(model.getName(), modelname))
				.toList());
	}

	public static List<EClassifier> getSimuLinkClassifiers(View view) {
		return view.getRootObjects(SimuLinkPackage.class).stream()
				.flatMap(simuLinkPackage -> simuLinkPackage.getEClassifiers().stream())
				.toList();
	}

	public static <T extends SimulinkElement> List<T> getSimuLinkElementsOfType(View view, Class<T> type) {
		return getSimuLinkClassifiers(view).stream()
				.filter(type::isInstance)
				.map(type::cast)
				.toList();
	}

	public static List<Block> getBlocksOfModel(View view, String elementName) {
		return view.getRootObjects(SimulinkModel.class).stream()
				.flatMap(model -> model.getContains().stream())
				.toList();
	}

	public static Block claimSimuLinkBlock(View view, String blockName) {
		return claimSimuLinkElement(view, Block.class, blockName);
	}

	public static <T extends SimulinkElement> Block claimSimuLinkElement(View view, Class<T> simuLinkType,
			String elementName) {
		return claimOne(getBlocksOfModel(view, elementName).stream()
				.filter(block -> Objects.equals(block.getName(), elementName))
				.toList());
	}

	public static Block claimSimuLinkBlockOfSubsystem(SubSystem subsystem, String blockname) {
		return claimOne(subsystem.getSubBlocks().stream()
				.filter(block -> Objects.equals(block.getName(), blockname))
				.toList());
	}

	public static Port claimSimuLinkPort(Block block, String portName) {
		return claimOne(block.getPorts().stream()
				.filter(port -> Objects.equals(port.getName(), portName))
				.toList());
	}

	public static Connection claimSimuLinkConnection(View view, String connectionName) {
		return claimOne(view.getRootObjects(SimulinkModel.class).stream()
				.flatMap(model -> model.getConnection().stream())
				.filter(connection -> Objects.equals(connection.getName(), connectionName))
				.toList());
	}
}
