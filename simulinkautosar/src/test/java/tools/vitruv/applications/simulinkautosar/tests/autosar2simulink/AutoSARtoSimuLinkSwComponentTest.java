package tools.vitruv.applications.simulinkautosar.tests.autosar2simulink;

import static tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.claimAutoSARCompositeSwComponent;
import static tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.claimAutoSARElement;

import edu.kit.ipd.sdq.metamodels.autosar.AtomicSwComponent;
import edu.kit.ipd.sdq.metamodels.autosar.CompositeSwComponent;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Test;

public class AutoSARtoSimuLinkSwComponentTest extends AbstractAutoSARToSimuLinkTest {

	private static final String DEFAULT_Component_NAME = "TestComponent";
	private static final String DEFAULT_COMPOSITE_COMPONENT_NAME = "TestCompositeComponent";

	/*
	 *
	 * Tests for AtomicSwComponents
	 *
	 *
	 */

	@Test
	public void testCreateComponent() throws Exception {
		createAtomicSWComponentInModel(DEFAULT_Component_NAME);
		equalityValidation.assertSwComponentWithNameInRootModel(DEFAULT_Component_NAME);
	}

	@Test
	public void testDeleteComponent() throws Exception {
		createAtomicSWComponentInModel(DEFAULT_Component_NAME);
		viewFactory.changeAutoSARView(view -> {
			EcoreUtil.delete(claimAutoSARElement(view, AtomicSwComponent.class, DEFAULT_Component_NAME));
		});
		equalityValidation.assertNoElementWithNameInRootModel(DEFAULT_Component_NAME);
	}

	/*
	 *
	 * Tests for CompositeSwComponents
	 *
	 *
	 */

	@Test
	public void testCreateCompositeComponent() throws Exception {
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME);
		createAtomicSWComponentInModel(DEFAULT_Component_NAME);
		viewFactory.changeAutoSARView(view -> {
			var atomicComponent = (AtomicSwComponent) claimAutoSARElement(view, AtomicSwComponent.class,
					DEFAULT_Component_NAME);
			claimAutoSARCompositeSwComponent(view, CompositeSwComponent.class, DEFAULT_COMPOSITE_COMPONENT_NAME)
					.getAtomicswcomponent().add(atomicComponent);
		});

		equalityValidation.assertCompositeSwComponentWithNameInRootModel(DEFAULT_COMPOSITE_COMPONENT_NAME);
	}

	@Test
	public void testDeleteCompositeComponent() throws Exception {
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME);
		viewFactory.changeAutoSARView(view -> {
			EcoreUtil.delete(claimAutoSARCompositeSwComponent(view, CompositeSwComponent.class,
					DEFAULT_COMPOSITE_COMPONENT_NAME));
		});
		equalityValidation.assertNoElementWithNameInRootModel(DEFAULT_COMPOSITE_COMPONENT_NAME);
	}

	@Test
	public void testDeleteCompositeComponentAndCheckContainedAtomicComponent() throws Exception {
		createCompositeSWComponentInModel(DEFAULT_COMPOSITE_COMPONENT_NAME);
		createAtomicSWComponentInModel(DEFAULT_Component_NAME);
		viewFactory.changeAutoSARView(view -> {
			var atomicComponent = (AtomicSwComponent) claimAutoSARElement(view, AtomicSwComponent.class,
					DEFAULT_Component_NAME);
			claimAutoSARCompositeSwComponent(view, CompositeSwComponent.class, DEFAULT_COMPOSITE_COMPONENT_NAME)
					.getAtomicswcomponent().add(atomicComponent);
		});
		viewFactory.changeAutoSARView(view -> {
			EcoreUtil.delete(claimAutoSARCompositeSwComponent(view, CompositeSwComponent.class,
					DEFAULT_COMPOSITE_COMPONENT_NAME));
		});
		equalityValidation.assertNoElementWithNameInRootModel(DEFAULT_COMPOSITE_COMPONENT_NAME);
		equalityValidation.assertNoElementWithNameInRootModel(DEFAULT_Component_NAME);
	}
}
