package tools.vitruv.applications.viewfilter.viewbuild;


import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;

import tools.vitruv.applications.viewfilter.util.framework.FilterSupportingViewTypeFactory;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewTypeFactory;

public class UmlViewBuilder {
	
	private ViewSelector selector;
	
	
	private UmlViewBuilder(ViewProvider viewProvider) {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Model.class);
		createViewOfElements(viewProvider, "UML", rootTypes);
	}
	
	public static UmlViewBuilder buildUmlView(ViewProvider viewProvider) {
		return new UmlViewBuilder(viewProvider);
	}
	
	
	public UmlViewBuilder filterForTypeClass() {
		selector.getSelectableElements().stream()
			.filter(element -> element instanceof org.eclipse.uml2.uml.Class)
			.forEach(element -> selector.setSelected(element, true));
		return this;
	}
	
	

	
	
	
	
	
	
	
	private void removeAttributes(EObject object) {
		
		if (object instanceof Classifier) {
			Classifier currentClassifier = (Classifier) object;
			
			Classifier classifierCopy = EcoreUtil.copy(currentClassifier);
			EcoreUtil.remove(object);
			
			//nbruening Hier weitermachen
			currentClassifier.allAttributes();
			currentClassifier.getAttributes();
			
			
			selector.setSelected(object, true);
		}
	}
	
	/**
	 * Creates a view with the given name containing the provided root types (and
	 * its descendants).
	 */
	private void createViewOfElements(ViewProvider viewProvider, String viewName, Collection<Class<?>> rootTypes) {
		ViewSelector selector = viewProvider.createSelector(FilterSupportingViewTypeFactory.createFilterSupportingIdentityMappingViewType(viewName));	
			selector.getSelectableElements().stream()
				.filter(element -> rootTypes.stream().anyMatch(it -> it.isInstance(element)))
				.forEach(element -> selector.setSelected(element, true));	
	}

	
	private Collection<Class<?>> createCollectionOfRootTypes(Class<?> additionalRootType) {
		Collection<Class<?>> rootTypes = new LinkedList();
		rootTypes.add(additionalRootType);
		return rootTypes;
	}
	
}
