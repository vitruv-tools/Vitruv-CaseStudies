package tools.vitruv.applications.viewfilter.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
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
	
	private ViewSelection preFilterSelection;
	

	public BasicFilterView(FilteredViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType,
			ChangeableViewSource viewSource, ViewSelection selection, ViewFilter viewFilter) {
		super(viewType, viewSource, selection);
		this.viewFilter = viewFilter;
		preFilterSelection = selection;
		update();		
	}
	
	
	public void update() {
		if (viewFilter != null) {
			//only execute if viewFilter has already been set
			super.update();
		}
	}
	
	
	@Override
	public void modifyContents(Procedure1<? super ResourceSet> modificationFunction) {
		super.modifyContents(modificationFunction);
		if (viewFilter != null) {
			ModifiableViewSelection filteredSelection = filterElementsForSelection();
			updateSelectedElements(filteredSelection);
		}
		
	}
	
	
	private List<EObject> findRootsForSelectionInViewSource() {
		Collection<Resource> viewSources = getViewSource().getViewSourceModels();
		ViewSelection selection = getPreFilterSelection();
		List<Resource> resourcesWithSelectedElements = viewSources.stream()
				.filter(resource -> resource.getContents().stream().anyMatch(selection::isViewObjectSelected)).toList();
		List<EObject> roots = new ArrayList();
		for(Resource resource : resourcesWithSelectedElements) {
			roots.addAll(resource.getContents());
		}
		return roots;	
	}
	
	
	protected ModifiableViewSelection filterElementsForSelection() {
		//If the viewFilter has already been set, use it..
		List<EObject> rootsForSelection = findRootsForSelectionInViewSource();
		Set<EObject> filteredElements = viewFilter.filterElements(rootsForSelection);
		ModifiableViewSelection elementViewSelection = new ElementViewSelection(filteredElements);
		filteredElements.forEach(it -> elementViewSelection.setSelected(it, true));
		return elementViewSelection;
	}
	
	
	protected void updateSelectedElements(ModifiableViewSelection selection) {
		//TODO nbruening merge with filterElementsForSelection method?
		setSelection(selection);
	}
	
	
	public Map<EObject, EObject> getMapOriginalRoot2RootStub() {
		return viewFilter != null ? viewFilter.getMapOriginalRoot2RootStub() : Collections.emptyMap();
	}


	public ViewSelection getPreFilterSelection() {
		return preFilterSelection;
	}
	
	
	public ViewSelection getSelection() {
		return super.getSelection();
	}

}
