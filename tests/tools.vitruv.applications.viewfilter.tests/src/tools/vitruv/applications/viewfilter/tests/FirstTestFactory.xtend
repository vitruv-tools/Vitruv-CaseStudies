package tools.vitruv.applications.viewfilter.tests

import tools.vitruv.testutils.TestViewFactory
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.eclipse.uml2.uml.Model
import tools.vitruv.framework.views.View
import tools.vitruv.framework.views.ViewSelector
import tools.vitruv.framework.views.ViewTypeFactory
import java.util.Collection
import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat
import tools.vitruv.framework.views.ViewProvider


class FirstTestFactory extends TestViewFactory {
	
	var ViewProvider viewProvider;
	
	
	new(ViewProvider viewProvider) {
		super(viewProvider)
		this.viewProvider = viewProvider;
	}
		
	
	def View createUmlView() {
		createViewOfElements("UML", #{Model})
	}
	

	
	
	def View createUmlAndPcmClassesView() {
		createViewOfElements("UML and PCM components", #{RepositoryComponent, Model})
	}
	
	
	def View createFilteredUmlView() {
		createFilteredViewOfElements("UML", #{Model})
	}
	
	
	
//-------- Boilerplate code -----------//Kakao

	
			/**
	 * Changes the UML view containing all UML models as root elements 
	 * according to the given modification function. 
	 * Records the performed changes, commits the recorded changes, and closes the view afterwards.
	 */
	def void changeUmlView((View)=>void modelModification) {
		changeViewRecordingChanges(createUmlView, modelModification)
	}

	def View createFilteredViewOfElements(String viewName, Collection<Class<?>> rootTypes) {
		val ViewSelector selector = viewProvider.createSelector(ViewTypeFactory.createIdentityMappingViewType(viewName));
		var filteredElements = selector.selectableElements.filter[ element | rootTypes.exists[it.isInstance(element)]]
		filteredElements.forEach[element | selector.setSelected(element, true)]
		var View view = selector.createView();
		assertThat("view must not be null", view, not(equalTo(null)));
		return view;
	}

}