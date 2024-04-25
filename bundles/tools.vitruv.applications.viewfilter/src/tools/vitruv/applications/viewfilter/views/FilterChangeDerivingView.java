package tools.vitruv.applications.viewfilter.views;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;
 
public class FilterChangeDerivingView extends ChangeDerivingView implements FilterableView {

	private Map<EObject, EObject> mapCopy2OriginalObject;


	protected FilterChangeDerivingView(BasicFilterView view,
			StateBasedChangeResolutionStrategy changeResolutionStrategy, Map<EObject, EObject> mapCopy2OriginalObject) {
		super(view, changeResolutionStrategy);
		this.mapCopy2OriginalObject = mapCopy2OriginalObject;
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
	
	private FilterableView getViewAsFilterableView() {
		return (FilterableView) view;
	}
}
