package tools.vitruv.applications.viewfilter.informationview.views;

import tools.vitruv.application.viewfilter.informationview.internal.CountUmlClassesTransformator;
import tools.vitruv.applications.viewfilter.util.framework.impl.FilteredViewCreatingViewType;
import tools.vitruv.applications.viewfilter.viewbuild.ViewFilter;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;

/**
 * Utility class for creating a {@link InformationView} which counts all uml2.Class objects 
 * in a model. Used a given {@link ViewFilter} object to filter the model for objects which are 
 * supposed to be taken into account
 */
public class CountUmlClassesView extends BasicInformationFilterView implements InformationView {

	/**
	 * Creates an {@link InformationView} object which counts all uml2.Class objects in a model
	 * and displays the result.
	 *  
	 * @param viewType The viewType for this {@link InformationView} object
	 * @param viewSource The viewSource for this {@link InformationView} object
	 * @param selection The selection for this {@link InformationView} object
	 * @param viewFilter The viewFilter used to filter objects which are supposed to displayed in the view.
	 */
	public CountUmlClassesView(FilteredViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType,
			ChangeableViewSource viewSource, ViewSelection selection, ViewFilter viewFilter) {
		super(viewType, viewSource, selection, viewFilter, new CountUmlClassesTransformator());
		
	}
}
