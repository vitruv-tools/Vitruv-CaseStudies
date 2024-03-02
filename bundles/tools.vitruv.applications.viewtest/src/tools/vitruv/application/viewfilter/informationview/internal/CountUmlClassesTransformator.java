package tools.vitruv.application.viewfilter.informationview.internal;

import org.eclipse.emf.ecore.EObject;

public class CountUmlClassesTransformator extends CountElementsTransformator {

	private static final String ANZAHL_ELEMENTE = "Anzahl Elemente";

	@Override
	protected boolean takeElementIntoAccount(EObject object) {
		if (object instanceof org.eclipse.uml2.uml.Class) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected String getTitle() {
		return ANZAHL_ELEMENTE;
	}

}
