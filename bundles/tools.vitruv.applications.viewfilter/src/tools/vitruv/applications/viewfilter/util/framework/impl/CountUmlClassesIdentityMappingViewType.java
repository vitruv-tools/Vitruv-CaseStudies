package tools.vitruv.applications.viewfilter.util.framework.impl;

import static com.google.common.base.Preconditions.checkArgument;

import tools.vitruv.applications.viewfilter.informationview.views.BasicInformationFilterView;
import tools.vitruv.applications.viewfilter.informationview.views.CountUmlClassesView;
import tools.vitruv.applications.viewfilter.util.framework.selectors.FilterSupportingViewElementSelectorImpl;
import tools.vitruv.change.atomic.hid.HierarchicalId;

public class CountUmlClassesIdentityMappingViewType extends FilterSupportingIdentityMappingViewType {

	public CountUmlClassesIdentityMappingViewType(String name) {
		super(name);
	}

	
	@Override
	public ModifiableView createView(FilterSupportingViewElementSelectorImpl<HierarchicalId> selector) {
		checkArgument(selector.getViewType() == this, "cannot create view with selector for different view type");		
		BasicInformationFilterView view = new CountUmlClassesView(selector.getViewType(), selector.getViewSource(), selector.getSelection(), selector.getViewFilter());
		return view;
	}
	
}
                