package tools.vitruv.applications.viewfilter.util.framework;

import tools.vitruv.applications.viewfilter.util.framework.impl.FilterSupportingIdentityMappingViewType;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;

public class FilterSupportingViewTypeFactory {
	
	public static ViewType<? extends ViewSelector> createFilterSupportingIdentityMappingViewType(String name) {
		return new FilterSupportingIdentityMappingViewType(name);
		
	}

}
