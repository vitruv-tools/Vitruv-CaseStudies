package tools.vitruv.applications.simulinkautosar.tests.util;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;

import edu.kit.ipd.sdq.metamodels.autosar.AtomicSwComponent;
import edu.kit.ipd.sdq.metamodels.autosar.AutoSARElement;
import edu.kit.ipd.sdq.metamodels.autosar.AutoSARModel;
import edu.kit.ipd.sdq.metamodels.autosar.CompositeSwComponent;
import edu.kit.ipd.sdq.metamodels.autosar.Port;
import edu.kit.ipd.sdq.metamodels.autosar.SwComponent;
import java.util.Objects;
import tools.vitruv.framework.views.View;

/*
 *
 * Utility Class for AutoSAR
 * Contains Methods to claim and find AutoSARElements of different types
 * The AutoSAR Element was added to the meta model for easier handling and derivation of the name attribute to all AutoSARElements
 *
 */
public final class AutoSARQueryUtil {

	private AutoSARQueryUtil() {
	}

	public static AutoSARModel claimAutoSARModel(View view, String name) {
		return claimOne(view.getRootObjects(AutoSARModel.class).stream()
				.filter(model -> Objects.equals(model.getName(), name))
				.toList());
	}

	public static <T extends AutoSARElement> SwComponent claimAutoSARElement(View view, Class<T> element,
			String elementName) {
		return claimOne(view.getRootObjects(AutoSARModel.class).stream()
				.flatMap(model -> model.getSwcomponent().stream())
				.filter(component -> Objects.equals(component.getName(), elementName))
				.toList());
	}

	public static <T extends AutoSARElement> CompositeSwComponent claimAutoSARCompositeSwComponent(View view,
			Class<T> element, String elementName) {
		return (CompositeSwComponent) claimOne(view.getRootObjects(AutoSARModel.class).stream()
				.flatMap(model -> model.getSwcomponent().stream())
				.filter(component -> Objects.equals(component.getName(), elementName))
				.toList());
	}

	public static AtomicSwComponent claimAutoSARBlockinComposite(CompositeSwComponent compositeComponent,
			String atomicComponentName) {
		return claimOne(compositeComponent.getAtomicswcomponent().stream()
				.filter(component -> Objects.equals(component.getName(), atomicComponentName))
				.toList());
	}

	public static Port claimAutoSARPort(SwComponent swComponent, String portname) {
		return claimOne(swComponent.getPort().stream()
				.filter(port -> Objects.equals(port.getName(), portname))
				.toList());
	}
}
