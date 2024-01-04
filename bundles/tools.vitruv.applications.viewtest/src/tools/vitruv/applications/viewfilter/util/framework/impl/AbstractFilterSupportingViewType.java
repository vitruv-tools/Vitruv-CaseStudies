package tools.vitruv.applications.viewfilter.util.framework.impl;

import tools.vitruv.framework.views.ViewSelector;

public abstract class AbstractFilterSupportingViewType<S extends ViewSelector, Id> extends AbstractViewType<S, Id> implements FilteredViewCreatingViewType<S, Id> {

	public AbstractFilterSupportingViewType(String name) {
		super(name);
	}

	
	


}
