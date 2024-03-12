package tools.vitruv.applications.viewfilter.util.framework;

import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelector;

public interface FilteredViewSelector extends ViewSelector {

	View createFilteredView();
}
