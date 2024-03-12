package tools.vitruv.applications.viewfilter.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.CompositeContainerChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;
 
public class FilterChangeDerivingView extends ChangeDerivingView implements FilterableView {

	private Map<EObject, EObject> mapCopy2OriginalObject;

	private ResourceSet originalStateViewResourceSet;

	private HashMap<Resource, Resource> originalStateResourceMapping;

	protected FilterChangeDerivingView(BasicFilterView view,
			StateBasedChangeResolutionStrategy changeResolutionStrategy, Map<EObject, EObject> mapCopy2OriginalObject) {
		super(view, changeResolutionStrategy);
		this.mapCopy2OriginalObject = mapCopy2OriginalObject;
	}

	@Override
	protected void setupReferenceState() {
		originalStateViewResourceSet = new ResourceSetImpl();
		ResourceCopier.copyViewResources(view.getViewResourceSet().getResources(), originalStateViewResourceSet);
		originalStateResourceMapping = new HashMap();
		for (Resource resource : view.getViewResourceSet().getResources()) {
			Resource searchedResource = null;
			for (Resource originalStateResource : originalStateViewResourceSet.getResources()) {
				if (originalStateResource.getURI() == resource.getURI()) {
					searchedResource = originalStateResource;
					break;
				}
			}
			if (searchedResource != null) {
				originalStateResourceMapping.put(resource, searchedResource);
			} else {
				throw new NullPointerException();
			}
		}
	}

	public void commitChanges() {
		view.checkNotClosed();

		ArrayList changes = new ArrayList();
		Set<Resource> allResources = new HashSet(originalStateResourceMapping.keySet());
		allResources.addAll(view.getViewResourceSet().getResources()); // consider newly added resources
		List<Resource> pathmapResources = new ArrayList();
		for (Resource resource : allResources) {
			if (URIUtil.isPathmap(resource.getURI())) {
				pathmapResources.add(resource);
			}
		}
		Resource[] array = (Resource[]) allResources.stream().filter((Resource it) -> {
			return URIUtil.isPathmap(it.getURI());
		}).toArray();
		for (Resource changedResource : array) {
			VitruviusChange<HierarchicalId> change = generateChange(changedResource,
					originalStateResourceMapping.get(changedResource));
			changes.add(change);
		}
		CompositeContainerChange change = VitruviusChangeFactory.getInstance().createCompositeChange(changes);

		view.getViewType().commitViewChanges(this, change);
		view.setViewChanged(false);

		// super.getRootObjects();

		// ViewSource auf Super.RootObjects mappen; Änderungen von FilterModell über
		// Mapping auf ViewSource und
		// Mapping auf Super.RootObjects auf Super.RootObjects übertragen, anschließend
		// Super.CommitChanges() aufrufen.
		// Oder reicht das Übertragen auf die ViewSource?
	}

	private FilterableView getViewAsFilterableView() {
		return (FilterableView) view;
	}

	public ResourceSet getViewResourceSet() {
		return view.getViewResourceSet();
	}

	@Override
	public ViewSelection getPreFilterSelection() {
		return getViewAsFilterableView().getPreFilterSelection();
	}

	@Override
	public Map<EObject, EObject> getMapCopy2OriginalObject() {
		return mapCopy2OriginalObject;
	}

	@Override
	public ResourceSet getNonFilteredViewResourceSet() {
		return getViewAsFilterableView().getNonFilteredViewResourceSet();
	}
}
