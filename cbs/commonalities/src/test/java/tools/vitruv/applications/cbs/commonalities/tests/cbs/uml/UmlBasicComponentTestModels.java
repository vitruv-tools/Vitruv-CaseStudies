package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import java.util.List;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.BasicComponentTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;

public class UmlBasicComponentTestModels extends UmlTestModelsBase implements BasicComponentTest.DomainModels {

	private static Package newUmlComponentPackage() {
		Package componentPackage = UMLFactory.eINSTANCE.createPackage();
		componentPackage.setName(Character.toLowerCase(COMPONENT_NAME.charAt(0)) + COMPONENT_NAME.substring(1));
		return componentPackage;
	}

	private static Class newUmlComponentClass() {
		Class componentClass = UMLFactory.eINSTANCE.createClass();
		componentClass.setName(COMPONENT_NAME + "Impl");
		componentClass.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		return componentClass;
	}

	public UmlBasicComponentTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	public DomainModel emptyBasicComponentCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();
			Package componentPackage = newUmlComponentPackage();
			componentPackage.getPackagedElements().add(newUmlComponentClass());
			umlRepositoryModel.getRepositoryPackage().getPackagedElements().add(componentPackage);
			return List.of(umlRepositoryModel.getModel());
		});
	}
}
