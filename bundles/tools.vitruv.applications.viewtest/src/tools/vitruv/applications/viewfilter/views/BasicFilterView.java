package tools.vitruv.applications.viewfilter.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.net4j.util.ImplementationError;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import tools.vitruv.applications.viewfilter.util.framework.impl.ViewCreatingViewType;
import tools.vitruv.applications.viewfilter.util.framework.selection.ElementViewSelection;
import tools.vitruv.applications.viewfilter.viewbuild.ViewFilter;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;

public class BasicFilterView extends AbstractBasicView implements FilterableView {
	
	private final ViewFilter viewFilter;
	
	private final ViewSelection preFilterSelection;
	
	private Collection<EObject> rootObjects;

	private ResourceSet filteredModelsResSet = null;
	

	public BasicFilterView(ViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType,
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
	
	
	@Override
	public ResourceSet getNonFilteredViewResourceSet() {
		return super.getViewResourceSet();
	}
	
	
	public ResourceSet getViewResourceSet() {
		if (filteredModelsResSet == null) {
			filteredModelsResSet = new ResourceSetImpl();
			Map<EObject, EObject> mapCopy2Original = viewFilter.getMapCopy2Original();
			Map<EObject, URI> rootObject2UriMapping = getRootObject2UriMapping();
			for (EObject copiedRootObject : getRootObjects()) {
				EObject originalObject = mapCopy2Original.get(copiedRootObject);
				filteredModelsResSet.createResource(rootObject2UriMapping.get(originalObject)).getContents().add(copiedRootObject);
			}
		}			
		
		return filteredModelsResSet;
	}
	
	
	public void setViewChanged(boolean value) {
		super.setViewChanged(value);
	}
	
	
	
	protected ModifiableViewSelection filterElementsForSelection() {
		//TODO nbr: Hat das für Probleme gesorgt, dass ich hier nicht mehr überprüfe, ob alle Elemente auch in selection sind?
//		List<EObject> rootsForSelection = findRootsForSelectionInViewSource(getViewSource().getViewSourceModels(););
		Collection<EObject> rootsForFiltering = super.getRootObjects();
		
		Set<EObject> filteredElements = viewFilter.filterElements(rootsForFiltering);
		ModifiableViewSelection elementViewSelection = new ElementViewSelection(filteredElements);
		filteredElements.forEach(it -> elementViewSelection.setSelected(it, true));
		return elementViewSelection;
	}
	
	
	public Collection<EObject> getRootObjects() {
		return Collections.unmodifiableCollection(rootObjects);
	}
	
	
	private Map<EObject, URI> getRootObject2UriMapping() {
		Map<EObject, URI> rootObject2UriMapping = new HashMap();
		
		for (Resource resource : getNonFilteredViewResourceSet().getResources()) {
			EList<EObject> contents = resource.getContents();
			for (EObject content : contents) {
				rootObject2UriMapping.put(content, resource.getURI());
			}
		}
		return rootObject2UriMapping;
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
	
	
	
    @Override
    public CommittableView withChangeDerivingTrait(StateBasedChangeResolutionStrategy changeResolutionStrategy) {
        checkNotClosed();
        return new FilterChangeDerivingView(this, changeResolutionStrategy, viewFilter.getMapCopy2Original());
    }
    
    
    @Override
    public CommittableView withChangeRecordingTrait() {
        checkNotClosed();
        return new FilterChangeRecordingView(this, viewFilter.getMapCopy2Original());
    }


	@Override
	public Map<EObject, EObject> getMapCopy2OriginalObject() {
		return viewFilter.getMapCopy2Original();
	}

}
