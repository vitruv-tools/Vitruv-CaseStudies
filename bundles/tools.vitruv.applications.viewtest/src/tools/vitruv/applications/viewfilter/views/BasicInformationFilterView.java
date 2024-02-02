package tools.vitruv.applications.viewfilter.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import com.niklas.niklasproject.niklasdomain.InformationStructure;
import com.niklas.niklasproject.niklasdomain.NiklasdomainFactory;
import com.niklas.niklasproject.niklasdomain.SingleInformation;

import tools.vitruv.application.viewfilter.transformation.CountElementsTransformator;
import tools.vitruv.application.viewfilter.transformation.CountUmlClassesTransformator;
import tools.vitruv.applications.viewfilter.helpers.ViewFilterHelper;
import tools.vitruv.applications.viewfilter.util.framework.impl.FilteredViewCreatingViewType;
import tools.vitruv.applications.viewfilter.util.framework.selection.ElementViewSelection;
import tools.vitruv.applications.viewfilter.viewbuild.ViewFilter;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;

public class BasicInformationFilterView extends BasicFilterView {

	public BasicInformationFilterView(FilteredViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType,
			ChangeableViewSource viewSource, ViewSelection selection, ViewFilter viewFilter) {
		super(viewType, viewSource, selection, viewFilter);
	}
	
	
	@Override
	protected void updateRootAndSelectedElements(ModifiableViewSelection selection) {
		if (selection == null) {
			throw new NullPointerException("selection is null");
		}
		CountElementsTransformator transformator = new CountUmlClassesTransformator();
		InformationStructure informationStructure = NiklasdomainFactory.eINSTANCE.createInformationStructure();
		EList<SingleInformation> singleinformationList = informationStructure.getSingleinformation();
		
		for(EObject root : selection.getSelectableElements()) {
			if (selection.isSelected(root)) {
				SingleInformation transformResult = transformator.transform(root);
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
