package tools.vitruv.applications.viewfilter.util.framework.impl;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import tools.vitruv.applications.viewfilter.util.framework.selection.ElementViewSelection;
import tools.vitruv.applications.viewfilter.viewbuild.ViewFilter;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;

public class BasicFilterView extends BasicView {
	
	private final ViewFilter viewFilter;

	protected BasicFilterView(FilteredViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType,
			ChangeableViewSource viewSource, ViewSelection selection, ViewFilter viewFilter) {
		super(viewType, viewSource, selection);
		this.viewFilter = viewFilter;
	}
	
	@Override
	public void modifyContents(Procedure1<? super ResourceSet> modificationFunction) {
		super.modifyContents(modificationFunction);
		filterSelectedElements();
	}
	
	
	private void filterSelectedElements() {
		if (viewFilter != null) {
			//If the viewFilter has already been set, use it..
			Set<EObject> filteredElements = viewFilter.filterElements(getRootObjects());
			setSelection(new ElementViewSelection(filteredElements));
		}
	}

}
