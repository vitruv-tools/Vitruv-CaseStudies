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
	
	private final ViewSelection preFilterSelection;
	
	private Collection<EObject> rootObjects;
	

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
			updateRootAndSelectedElements(filteredSelection);
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
		List<EObject> rootsForSelection = findRootsForSelectionInViewSource();
		Set<EObject> filteredElements = viewFilter.filterElements(rootsForSelection);
		ModifiableViewSelection elementViewSelection = new ElementViewSelection(filteredElements);
		filteredElements.forEach(it -> elementViewSelection.setSelected(it, true));
		return elementViewSelection;
	}
	
	
	public Collection<EObject> getRootObjects() {
		return Collections.unmodifiableCollection(rootObjects);
	}
	
	
	protected void updateRootAndSelectedElements(ModifiableViewSelection selection) {
		setRootObjects(selection.getSelectableElements());
		setSelection(selection);
	}


	public ViewSelection getPreFilterSelection() {
		return preFilterSelection;
	}
	
	
	public ViewSelection getSelection() {
		return super.getSelection();
	}


	protected void setRootObjects(Collection<EObject> rootObjects) {
		this.rootObjects = rootObjects;
	}

}
