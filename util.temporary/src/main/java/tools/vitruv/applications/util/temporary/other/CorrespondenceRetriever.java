package tools.vitruv.applications.util.temporary.other;

import org.eclipse.emf.ecore.EObject;

/**
 * To be used as a callback for passing correspondence access from Reactions
 * to helper classes.
 */
public interface CorrespondenceRetriever {
	public EObject retrieveCorrespondingElement(EObject sourceElement,
			Class<? extends EObject> correspondingElementType, String tag);
}
