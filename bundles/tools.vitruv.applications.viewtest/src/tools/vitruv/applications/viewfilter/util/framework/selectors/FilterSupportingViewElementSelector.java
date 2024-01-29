package tools.vitruv.applications.viewfilter.util.framework.selectors;

import java.util.Collection;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.views.ViewSelector;

//TODO nbr add javadoc
public interface FilterSupportingViewElementSelector extends ViewSelector {

	//TODO nbr Remove this method (replaced by removeOwnedAttributesFromClasses
	@Deprecated
	void selectElementsOfRootType(Collection<Class<?>> rootTypes);
	
	void deselectElementsExceptForRootType(Collection<Class<?>> rootTypes);
	
	void addElementsToSelectionByLambda(Function<EObject, Boolean> filter);
	
	void removeOwnedAttributesFromClasses();
	
	
}
