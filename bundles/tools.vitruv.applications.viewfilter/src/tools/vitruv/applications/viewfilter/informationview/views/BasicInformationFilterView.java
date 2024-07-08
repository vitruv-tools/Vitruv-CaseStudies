package tools.vitruv.applications.viewfilter.informationview.views;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.application.viewfilter.informationview.internal.InformationViewTransformator;
import tools.vitruv.applications.viewfilter.util.framework.impl.ViewCreatingViewType;
import tools.vitruv.applications.viewfilter.util.framework.selection.ElementViewSelection;
import tools.vitruv.applications.viewfilter.viewbuild.ViewFilter;
import tools.vitruv.applications.viewfilter.views.BasicFilterView;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.views.viewfilter.infostructure.model.infostructuremodel.InformationStructure;
import tools.vitruv.views.viewfilter.infostructure.model.infostructuremodel.InfostructuremodelFactory;
import tools.vitruv.views.viewfilter.infostructure.model.infostructuremodel.SingleInformation;

public class BasicInformationFilterView extends BasicFilterView {

	private final InformationViewTransformator infoTransformator;


	public BasicInformationFilterView(ViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType,
			ChangeableViewSource viewSource, ViewSelection selection, ViewFilter viewFilter, 
			InformationViewTransformator infoTransformator) {
		super(viewType, viewSource, selection, viewFilter);
		checkArgument(infoTransformator != null, "Transformator must be not null");
		this.infoTransformator = infoTransformator;
		update();
	}
	
	
	@Override
	protected void updateRootAndSelectedElements(ModifiableViewSelection selection) {
		if (selection == null) {
			throw new NullPointerException("selection is null");
		}
		if (infoTransformator == null) {
			//method has been called as part of constructor and before the info transformator could be set
			return;
		}
		InformationStructure informationStructure = InfostructuremodelFactory.eINSTANCE.createInformationStructure();
		EList<SingleInformation> singleinformationList = informationStructure.getSingleinformation();
		
		for(EObject root : selection.getSelectableElements()) {
			if (selection.isSelected(root)) {
				List<EObject> rootList = new ArrayList();
				rootList.add(root);
				SingleInformation transformResult = infoTransformator.transform(rootList);
				if (transformResult != null) {
					singleinformationList.add(transformResult);
				}
			}
		}
		
		List<EObject> selectionList = new ArrayList();
		selectionList.add(informationStructure);
		
		setRootObjects(selectionList);
		
		ElementViewSelection elementViewSelection = new ElementViewSelection(selectionList);
		elementViewSelection.setSelected(informationStructure, true);
		setSelection(elementViewSelection);
	}

}
