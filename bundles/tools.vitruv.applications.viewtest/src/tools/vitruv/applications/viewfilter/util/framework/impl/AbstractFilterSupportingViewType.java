package tools.vitruv.applications.viewfilter.util.framework.impl;

import tools.vitruv.framework.views.ViewSelector;

/**
 * Objects of this type support building {@link View} objects with a filter. Filters can be used to filter 
 * certain elements of one ore multiple models represented by the View.
 * 
 * @param <S> The Type of ViewSelector used to select the elements included in the {@link View} which will be constructed by instances of {@link AbstractFilterSupportingViewType}
 * @param <Id> 
 */
public abstract class AbstractFilterSupportingViewType<S extends ViewSelector, Id> extends AbstractViewType<S, Id> implements FilteredViewCreatingViewType<S, Id> {

	public AbstractFilterSupportingViewType(String name) {
		super(name);
	}

	
	


}
