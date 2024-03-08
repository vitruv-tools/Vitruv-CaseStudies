package tools.vitruv.application.viewfilter.informationview.internal;

import org.eclipse.emf.ecore.EObject;

/**
 * This transformator counts the {@link org.eclipse.uml2.Class} instances in the given models and 
 * returns the result in a {@link SingleInformation} object.
 */
public class CountUmlClassesTransformator extends CountElementsTransformator {

	private static final String NUMBER_OF_ELEMENTS = "Number of elements";

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
		return NUMBER_OF_ELEMENTS;
	}

}
