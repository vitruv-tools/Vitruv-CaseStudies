package tools.vitruv.applications.viewfilter.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.testutils.TestViewFactory;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

public class ViewTestFactory extends TestViewFactory {
	
	private ViewProvider viewProvider;

	public ViewTestFactory(ViewProvider viewProvider) {
		super(viewProvider);
		this.viewProvider = viewProvider;
	}
	
	
	public View createUmlView() {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Model.class);
		return createViewOfElements("UML", rootTypes);
	}

	public View createUmlAndPcmClassesView() {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(createCollectionOfRootTypes(RepositoryComponent.class), Model.class);
		return createViewOfElements("UML and PCM components", rootTypes);
	}
	
	
	public View createFilteredUmlView() {
		Collection<Class<?>> rootTypes = createCollectionOfRootTypes(Model.class);
		return createFilteredForNoAttributesViewOfElements("UML", rootTypes);
	}
	
	
	//--------------Boilerplate code -----------------//
	//
	
	public View createFilteredViewOfElements(String viewName, Collection<Class<?>> rootTypes) {
		ViewSelector selector = viewProvider.createSelector(ViewTypeFactory.createIdentityMappingViewType(viewName));
//		selector.getSelectableElements().stream()
//				.filter(element -> rootTypes.stream().anyMatch(it -> it.isInstance(element)))
//				.forEach(element -> selector.setSelected(element, true));
		selector.getSelectableElements().stream().forEach(element -> selector.setSelected(element, true));
		View view = selector.createView();
		assertThat("view must not be null", view, not(equalTo(null)));
		return view;
	}	
	
	
		/**
	* Changes the UML view containing all UML models as root elements 
	* according to the given modification function. 
	* Records the performed changes, commits the recorded changes, and closes the view afterwards.
	*/
	public void changeUmlView(Consumer<CommittableView> modelModification) throws Exception {
		changeViewRecordingChanges(createUmlView(), modelModification);
	}
		
	
	
//-------------End of Boilerplate code----------------//	
	
	
	public View createFilteredForNoAttributesViewOfElements(String viewName, Collection<Class<?>> rootTypes) {
		ViewSelector selector = viewProvider.createSelector(ViewTypeFactory.createIdentityMappingViewType(viewName));
		selector.getSelectableElements().stream()
				.filter(element -> rootTypes.stream().anyMatch(it -> it.isInstance(element)))
				.filter(element -> hasNoAttribute(element))
				.forEach(element -> selector.setSelected(element, true));
		//selector.getSelectableElements().stream().forEach(element -> selector.setSelected(element, true));
		View view = selector.createView();
		assertThat("view must not be null", view, not(equalTo(null)));
		return view;
	}
	
	
	private boolean hasNoAttribute(EObject object) {
		object.eAllContents();
		
		return true;
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
