package tools.vitruv.applications.viewfilter.util.framework.impl;

import static com.google.common.base.Preconditions.checkArgument;


import tools.vitruv.applications.viewfilter.util.framework.selectors.FilterSupportingViewElementSelectorImpl;
import tools.vitruv.applications.viewfilter.views.BasicInformationFilterView;
import tools.vitruv.change.atomic.hid.HierarchicalId;

public class AbstractingInformationFilterIdentityMappingViewType extends FilterSupportingIdentityMappingViewType {

	public AbstractingInformationFilterIdentityMappingViewType(String name) {
		super(name);
	}

	
	@Override
	public ModifiableView createView(FilterSupportingViewElementSelectorImpl<HierarchicalId> selector) {
		checkArgument(selector.getViewType() == this, "cannot create view with selector for different view type");		
		BasicInformationFilterView view = new BasicInformationFilterView(selector.getViewType(), selector.getViewSource(), selector.getSelection(), selector.getViewFilter());
		return view;
	}
	
}
                