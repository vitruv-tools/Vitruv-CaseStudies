package tools.vitruv.applications.viewfilter.views;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeBackwardsExecutionHelper;
import tools.vitruv.change.composite.description.VitruviusChangeResolver;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.framework.views.ViewSelection;

//TODO nbr: Hier nicht ChangeRecordingView überschreiben, sondern eigen implementieren und Sichtbarkeiten in ChangeRecordingView zurücksetzen
public class FilterChangeRecordingView extends ChangeRecordingView implements FilterableView, FilterChangeView {
	
	private Map<EObject, EObject> mapCopy2OriginalObject;
	private TransactionalChange<EObject> recordedChange = null;
	
	//private ResourceSet resourceSetFromBeginningOfRecording;

	protected FilterChangeRecordingView(BasicFilterView view, Map<EObject, EObject> mapCopy2OriginalObject) {
		super(view);
		this.mapCopy2OriginalObject = mapCopy2OriginalObject;
	}
	
	
	
	@Override
	public List<EChange<EObject>> setupChangeRecorder() {
		ResourceSet filteredModelsInResourceSet = view.getViewResourceSet();
		changeRecorder = new ChangeRecorder(filteredModelsInResourceSet);
        changeRecorder.addToRecording(filteredModelsInResourceSet);
        ResourceSet resourceSetCopy = new ResourceSetImpl();
        //Map<Resource, Resource> copyResourceMapping = ResourceCopier.copyViewResources(filteredModelsInResourceSet.getResources(), resourceSetCopy);
        //resourceSetFromBeginningOfRecording = resourceSetCopy;
        
        return changeRecorder.beginRecording();
	}
	
	
	private ResourceSet copyResourceSet(ResourceSet resourceSetToBeCopied) {
		ResourceSet resourceSetCopy = new ResourceSetImpl();
        ResourceCopier.copyViewResources(resourceSetToBeCopied.getResources(), resourceSetCopy);
        return resourceSetCopy;
	}
	
	
	//private Map<EObject, >
	
	
	@Override
	public void commitChanges() {
		view.checkNotClosed();
		recordedChange = changeRecorder.endRecording();
		
		ResourceSet filteredModelsInResourceSet = getViewResourceSet();
		ResourceSet unfilteredResourceSet = getNonFilteredViewResourceSet();
//		EObject class2 = filteredModelsInResourceSet.getResources().get(0).getContents().get(0).eContents().get(0);
//		EObject eObject = mapCopy2OriginalObject.get(class2);
		
		
		//TODO nbr: ich muss hier irgendwie an das view resourceSet kommen
		
		//VitruviusChangeResolver.forMappingFilteredObjects(getView().getFilteredModelsInResourceSet(), getView().getNonFilteredViewResourceSet(), mapCopy2OriginalObject);
		VitruviusChangeResolver<HierarchicalId> changeResolver = VitruviusChangeResolver.forHierarchicalIds(view.getViewResourceSet());		
		VitruviusChange<HierarchicalId> unresolvedChanges = changeResolver.assignIds(recordedChange);
		view.getViewType().commitViewChanges(this, unresolvedChanges);
		view.setViewChanged(false);
		changeRecorder.beginRecording();
	}
	
	
	
	private FilterableView getViewAsFilterableView() {
		return (FilterableView) view;
	}



	@Override
	public ViewSelection getPreFilterSelection() {
		return getViewAsFilterableView().getPreFilterSelection();
	}



	@Override
	public ResourceSet getViewResourceSet() {
		return view.getViewResourceSet();
	}
	
	
	public ResourceSet getFilteredModelsInResourceSetWithBackwardExecution() {
		ResourceSet resourceSet = getViewResourceSet();
		if (recordedChange != null) {
			//TODO nbr: What happens if changes have already been reverted?
			VitruviusChangeBackwardsExecutionHelper changeResolver = new VitruviusChangeBackwardsExecutionHelper(resourceSet);
			changeResolver.applyBackward(recordedChange);
		}
		return resourceSet;
	}
	
	
	@Override
	public ResourceSet getNonFilteredViewResourceSet() {
		return getViewAsFilterableView().getNonFilteredViewResourceSet();
	}



	@Override
	public Map<EObject, EObject> getMapCopy2OriginalObject() {
		return mapCopy2OriginalObject;
	}

}
