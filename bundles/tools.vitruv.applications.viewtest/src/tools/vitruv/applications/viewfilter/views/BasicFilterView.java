package tools.vitruv.applications.viewfilter.views;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import tools.vitruv.applications.viewfilter.util.framework.impl.BasicView;
import tools.vitruv.applications.viewfilter.util.framework.impl.FilteredViewCreatingViewType;
import tools.vitruv.applications.viewfilter.util.framework.selection.ElementViewSelection;
import tools.vitruv.applications.viewfilter.viewbuild.ViewFilter;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;

public class BasicFilterView extends BasicView {
	
	private final ViewFilter viewFilter;
	

	public BasicFilterView(FilteredViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType,
			ChangeableViewSource viewSource, ViewSelection selection, ViewFilter viewFilter) {
		super(viewType, viewSource, selection);
		this.viewFilter = viewFilter;
//		ModifiableViewSelection filteredSelection = filterElementsForSelection();
//		updateSelectedElements(filteredSelection);
		
	}
	
	@Override
	public void modifyContents(Procedure1<? super ResourceSet> modificationFunction) {
		super.modifyContents(modificationFunction);
		ModifiableViewSelection filteredSelection = null;
		if (viewFilter != null) {
			filteredSelection = filterElementsForSelection();	
		}
		updateSelectedElements(filteredSelection);
	}
	
	
	protected ModifiableViewSelection filterElementsForSelection() {
		//If the viewFilter has already been set, use it..
		Set<EObject> filteredElements = viewFilter.filterElements(getRootObjects(), getViewSource().getViewSourceModels());
		ModifiableViewSelection elementViewSelection = new ElementViewSelection(filteredElements);
		filteredElements.forEach(it -> elementViewSelection.setSelected(it, true));
		return elementViewSelection;
	}
	
	
	protected void updateSelectedElements(ModifiableViewSelection selection) {
		//TODO nbruening merge with filterElementsForSelection method?
		if (viewFilter != null) {
			setSelection(selection);
		}	
	}
	
	
	public Map<EObject, EObject> getMapOriginalRoot2RootStub() {
		return viewFilter != null ? viewFilter.getMapOriginalRoot2RootStub() : Collections.emptyMap();
	}

}
