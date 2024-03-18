package tools.vitruv.applications.viewfilter.views;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.applications.viewfilter.util.framework.impl.ModifiableView;
import tools.vitruv.framework.views.ViewSelection;

/**
 * {@link Views} which implement this interface provide filter functionality. This interface 
 * therefore allows access on the selection of unfiltered models as well as on the unfiltered 
 * {@link ResourceSet}. Notice: To access the filtered models, one should use the common {@link ModifiableView}
 * or {@link View} interface. 
 * It also provides a mapping from an object in a filtered model to the object in the unfiltered 
 * model, as the objects in the filtered models might be copies of the original objects depending 
 * on the implementation.
 */
public interface FilterableView extends ModifiableView {

	/**
	 * Method returns the selection of models in this view before the first filtering. Can be used 
	 * to access the unfiltered models which are sources for the actual filtering and need to be 
	 * considered. 
	 * 
	 * @return A viewSelection of unfiltered models
	 */
	ViewSelection getPreFilterSelection();
	
	/**
	 * Provides a mapping from an object in a filtered model to the object in the unfiltered 
	 * model. The objects in the filtered models might be copies of the original objects depending 
	 * on the implementation.
	 * 
	 * @return The map containing the mapping
	 */
	Map<EObject, EObject> getMapCopy2OriginalObject();

	/**
	 * Method returns the {@link ResourceSet} containing all unfiltered models of this view.
	 * 
	 * @return The {@link ResourceSet} containing all unfiltered models of this view
	 */
	ResourceSet getNonFilteredViewResourceSet();
}
