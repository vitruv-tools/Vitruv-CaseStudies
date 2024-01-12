package tools.vitruv.applications.viewfilter.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.DynamicTest.stream;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Function;

import tools.vitruv.framework.views.View;
import tools.vitruv.applications.viewfilter.util.framework.FilterSupportingViewTypeFactory;
import tools.vitruv.applications.viewfilter.util.framework.impl.FilterSupportingIdentityMappingViewType;
import tools.vitruv.applications.viewfilter.util.framework.impl.ViewCreatingViewType;
import tools.vitruv.applications.viewfilter.util.framework.selectors.FilterSupportingViewElementSelector;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.testutils.TestViewFactory;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

public class ViewTestFactory extends TestViewFactory {
	
	private ViewProvider viewProvider;
	ViewType<? extends ViewSelector> viewType;

	public ViewTestFactory(ViewProvider viewProvider) {
		super(viewProvider);
		this.viewProvider = viewProvider;
	}
	
	
	public View createUmlView() {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Model.class);
		return createViewOfElements("UML", rootTypes);
	}
	
	private View createPcmView() {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Repository.class);
		rootTypes.add(RepositoryComponent.class);
		return createViewOfElements("PCM packages and components", rootTypes);
	}

	public View createUmlAndPcmClassesView() {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(createCollectionOfRootTypes(RepositoryComponent.class), Model.class);
		return createViewOfElements("UML and PCM components", rootTypes);
	}
	
	
	public View createFilteredUmlView(FirstTest test) {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Model.class);
		View view = createFilteredForNoAttributesViewOfElements("UML", rootTypes);
		view.getSelection().isViewObjectSelected(test.getClass1());
		view.getSelection().isViewObjectSelected(test.getClass2());
		return view;
	}
	
	public View createFilteredUmlView(BasicViewFilterTest test) {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Model.class);
		View view = createFilteredForNoAttributesViewOfElements("UML", rootTypes);
		view.getSelection().isViewObjectSelected(test.getClass1());
		view.getSelection().isViewObjectSelected(test.getClass2());
		return view;
	}
	
	public View createFilteredPcmView() {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Repository.class);
		return createFilteredForNoAttributesViewOfElements("PCM", rootTypes);
	}
	
	
	//--------------Boilerplate code -----------------//
	//
	
	public View createViewOfElements(String viewName, Collection<Class<?>> rootTypes) {
		ViewSelector selector = viewProvider.createSelector(ViewTypeFactory.createIdentityMappingViewType(viewName));
		selector.getSelectableElements().stream()
				.filter(element -> rootTypes.stream().anyMatch(it -> it.isInstance(element)))
				.forEach(element -> selector.setSelected(element, true));
//		if (!selector.getSelectableElements().isEmpty()) {
//			Object obj = (selector.getSelectableElements().toArray())[0];
//			boolean bla = obj instanceof Repository;
//		}
		
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
	
	
	public View createFilteredForNoAttributesViewOfElements(String viewName, Collection<Class<?>> rootTypes) {
		viewType = FilterSupportingViewTypeFactory.createFilterSupportingIdentityMappingViewType(viewName);
		FilterSupportingViewElementSelector selector = (FilterSupportingViewElementSelector) viewProvider.createSelector(viewType);
		Function<EObject, Boolean> function = (EObject object) -> hasNoAttribute(object, "niklasClass3");
		selector.addElementsToSelectionByLambda(function);
		selector.removeOwnedAttributesFromClasses();		

		View view = selector.createView();
		ViewSelection selection = view.getSelection();
		for (Object element : selector.getSelectableElements()) {
			if (element instanceof Class) {
				System.out.println(((Class) element).getName() + ": " + selection.isViewObjectSelected((EObject) element));
			} else {
				System.out.println(element.getClass() + ": " + selection.isViewObjectSelected((EObject) element));
			}
		}
		
		assertThat("view must not be null", view, not(equalTo(null)));
		return view;
	}
	
	
	private boolean hasInstanceName(EObject object, String name) {
		if (object instanceof org.palladiosimulator.pcm.core.entity.NamedElement) {
			if (name.equals(((org.palladiosimulator.pcm.core.entity.NamedElement) object).getEntityName())) {
				return true;
			}
		}
		return false;
	}

	
	
	private boolean hasNoAttribute(EObject object, String name) {
		if (object instanceof org.eclipse.uml2.uml.Class) {
			if (object instanceof NamedElement) {
				if (name.equals(((NamedElement) object).getName())) {
					return true;
				}
			}
			
		}
		return false;
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
