package tools.vitruv.applications.viewfilter.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

import tools.vitruv.applications.viewfilter.util.framework.FilterSupportingViewTypeFactory;
import tools.vitruv.applications.viewfilter.util.framework.selectors.FilterSupportingViewElementSelector;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.testutils.TestViewFactory;

public class FilterAndInfoViewTestFactory extends TestViewFactory {
	
	private ViewProvider viewProvider;
	ViewType<? extends ViewSelector> viewType;

	public FilterAndInfoViewTestFactory(ViewProvider viewProvider) {
		super(viewProvider);
		this.viewProvider = viewProvider;
	}
	
	
	public View createUmlView() {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Model.class);
		return createViewOfElements("UML", rootTypes);
	}
	
	public View createPcmView() {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Repository.class);
		rootTypes.add(RepositoryComponent.class);
		return createViewOfElements("PCM packages and components", rootTypes);
	}

	public View createUmlAndPcmClassesView() {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(createCollectionOfRootTypes(RepositoryComponent.class), Model.class);
		return createViewOfElements("UML and PCM components", rootTypes);
	}
	
	
	public View createFilteredUmlView(Function<EObject, Boolean> function) {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Model.class);
		return createFilteredForNoAttributesViewOfElements("UML", rootTypes, function);
	}
	
	public View createFilteredPcmView(Function<EObject, Boolean> filterFunction) {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Repository.class);
		return createFilteredViewOfElements("PCM", rootTypes, filterFunction);
	}
	
	
	public View createCountUmlElementsView(Function<EObject, Boolean> filterFunction) {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Model.class);
		return createCountUmlClassesInformationView("Information Page", rootTypes, filterFunction);
		
	}
	
	
	//--------------Boilerplate code -----------------//
	//
	
	public View createViewOfElements(String viewName, Collection<Class<?>> rootTypes) {
 		ViewSelector selector = viewProvider.createSelector(ViewTypeFactory.createIdentityMappingViewType(viewName));
		selector.getSelectableElements().stream()
				.filter(element -> rootTypes.stream().anyMatch(it -> it.isInstance(element)))
				.forEach(element -> selector.setSelected(element, true));
		
		View view = selector.createView();
		assertThat("view must not be null", view, not(equalTo(null)));
		return view;
	}
	
	
	public void changeUmlView(Consumer<CommittableView> modelModification) throws Exception {
		changeViewRecordingChanges(createUmlView(), modelModification);
	}
	
	/**
	 * Changes the PCM view containing all PCM packages and classes as root elements 
	 * according to the given modification function. 
	 * Records the performed changes, commits the recorded changes, and closes the view afterwards.
	 * @throws Exception 
	 */
	public void changePcmView(Consumer<CommittableView> modelModification) throws Exception {
		changeViewRecordingChanges(createPcmView(), modelModification);
	}
		
	
	
//-------------End of Boilerplate code----------------//	
	
	
	public View createCountUmlClassesInformationView(String viewName, Collection<Class<?>> rootTypes, Function<EObject, Boolean> filterFunction) {
		viewType = FilterSupportingViewTypeFactory.createCountUmlClassesInformationFilterViewViewType(viewName);
		FilterSupportingViewElementSelector selector = (FilterSupportingViewElementSelector) viewProvider.createSelector(viewType);
		selector.deselectElementsExceptForRootType(rootTypes);
		selector.filterModelElementsByLambda(filterFunction);
		selector.removeOwnedAttributesFromUmlClasses();

		View view = selector.createView();
		assertThat("view must not be null", view, not(equalTo(null)));
		return view;
	}
	
	
	public View createFilteredForNoAttributesViewOfElements(String viewName, Collection<Class<?>> rootTypes, Function<EObject, Boolean> function) {
		viewType = FilterSupportingViewTypeFactory.createFilterSupportingIdentityMappingViewType(viewName);
		FilterSupportingViewElementSelector selector = (FilterSupportingViewElementSelector) viewProvider.createSelector(viewType);
		selector.deselectElementsExceptForRootType(rootTypes);
		selector.filterModelElementsByLambda(function);
		selector.removeOwnedAttributesFromUmlClasses();		

		View view = selector.createView();		
		assertThat("view must not be null", view, not(equalTo(null)));
		return view;
	}
	
	
	private View createFilteredViewOfElements(String viewName, Collection<Class<?>> rootTypes, Function<EObject, Boolean> filterFunction) {
		viewType = FilterSupportingViewTypeFactory.createFilterSupportingIdentityMappingViewType(viewName);
		FilterSupportingViewElementSelector selector = (FilterSupportingViewElementSelector) viewProvider.createSelector(viewType);
		Function<EObject, Boolean> function = (EObject object) -> filterFunction.apply(object);
		selector.deselectElementsExceptForRootType(rootTypes);
		selector.filterModelElementsByLambda(function);	

		View view = selector.createView();		
		assertThat("view must not be null", view, not(equalTo(null)));
		return view;
	}
	
	
	private Collection<Class<?>> createCollectionOfRootTypes(Collection<Class<?>> currentCollection, Class<?> additionalRootType) {
		currentCollection.add(additionalRootType);
		return currentCollection;
	}
	
	private Collection<Class<?>> createCollectionOfRootTypes(Class<?> additionalRootType) {
		Collection<Class<?>> rootTypes = new LinkedList();
		rootTypes.add(additionalRootType);
		return rootTypes;
	}
}
