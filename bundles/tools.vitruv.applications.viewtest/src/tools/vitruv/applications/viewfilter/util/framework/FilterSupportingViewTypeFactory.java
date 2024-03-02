package tools.vitruv.applications.viewfilter.util.framework;

import org.eclipse.net4j.util.ImplementationError;

import tools.vitruv.applications.viewfilter.util.framework.impl.CountUmlClassesIdentityMappingViewType;
import tools.vitruv.applications.viewfilter.util.framework.impl.FilterSupportingIdentityMappingViewType;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;

public class FilterSupportingViewTypeFactory {
	
	public static ViewType<? extends ViewSelector> createFilterSupportingIdentityMappingViewType(String name) {
		return new FilterSupportingIdentityMappingViewType(name);
		
	}
	
	
	public static ViewType<? extends ViewSelector> createInformationFilterViewViewType(String name) {
		return new CountUmlClassesIdentityMappingViewType(name);
	}
}
