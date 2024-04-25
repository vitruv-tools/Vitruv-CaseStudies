package tools.vitruv.applications.viewfilter.util.framework.selectors;

import java.util.Collection;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.views.ViewSelector;

/**
 * Instances of this interface provide various methods for filtering. Filtering is not only
 * restricted on the model-roots. Instead, the selector filters individual elements of the 
 * model. The model contained in a constructed {@link View} might not contain objects 
 * which equal to the original objects in the original model.
 * 
 * Example: 
 *  
 *  Root
 *  - Element_1
 *  - Element_2
 *  	- Child_Element_1
 *  
 *  The selector still does only select the model-root itself, but it can further filter 
 *  the model-elements, like Element_1 and Child_Element_1. So one could use an instance 
 *  of {@link FilterSupportingViewElementSelector} to select Root and than further filter
 *  for Element_1 and Child_Element_1. If the {@link FilterSupportingViewElementSelector}
 *  is then used to create a corresponding {@link View}, this {@link View} will contain 
 *  a Model-Root and only Element_1 and Child_Element_1 attached to the Model-Root.
 */
public interface FilterSupportingViewElementSelector extends ViewSelector {
	
	/**
	 * Method deselects all models which are not of one of the given rootTypes.
	 * Obviously, the method will remove whole models and not only certain elements 
	 * of one model, as all elements of a model are from the same root type.
	 * 
	 * @param rootTypes The rootTypes which are supposed to be removed
	 */
	void deselectElementsExceptForRootType(Collection<Class<?>> rootTypes);
	
	/**
	 * Filters all Model-Elements by the given function. Notice: This method won't 
	 * change the selection, but only modifies the filter used to filter Model-Elements.
	 * 
	 * @param filter The function which is supposed to be used for filtering
	 */
	void filterModelElementsByLambda(Function<EObject, Boolean> filter);
	
	/**
	 * Method filters out all attributes of {@link org.eclipse.uml2.uml.Class} instances.
	 * Won't change instances of other types.
	 */
	void removeOwnedAttributesFromUmlClasses();
	
	
}
